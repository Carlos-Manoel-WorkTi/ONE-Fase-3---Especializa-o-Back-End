package javaposter.com.javaposter.service.Poster;

import javaposter.com.javaposter.DTOs.Poster.Req_UpdateDTO;
import javaposter.com.javaposter.DTOs.Poster.Res_All_MessagesDTO;
import javaposter.com.javaposter.DTOs.Poster.Res_DeleteDTO;
import javaposter.com.javaposter.DTOs.Poster.Res_UpdateDTO;
import javaposter.com.javaposter.model.poster.Topics;
import javaposter.com.javaposter.model.user.User;
import javaposter.com.javaposter.repository.PosterRepository;
import javaposter.com.javaposter.model.poster.Poster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PosterService {
    @Autowired
    private PosterRepository posterRepository;

    public Poster getPosterById(Long id) {
        Optional<Poster> poster = posterRepository.findById(id);
        return poster.orElse(null);
    }

    public void salve(User user,String name, String title, String msg, String topic, boolean status){
        Poster poster = new Poster();

        poster.setUser(user);
        poster.setAutor(name);
        poster.setTitulo(title);
        poster.setStatus(status);
        poster.setMensagem(msg);
        poster.setTopico(Topics.valueOf(topic));
        poster.setDataCriacao(LocalDateTime.now());

        posterRepository.save(poster);
    }

    public List<Res_All_MessagesDTO> getAllMessages(User user) {

            List<Poster> posters = posterRepository.findAllByUserId(user.getId());

            return posters.stream()
                    .map(poster -> new Res_All_MessagesDTO(poster.getId(),poster.getAutor(),poster.getMensagem(),poster.getDataCriacao()))
                    .collect(Collectors.toList());


    }


    public ResponseEntity deleteMessageById(Long id) {
        try {
            if (posterRepository.existsById(id)) {
                posterRepository.deletePosterById(id);
                return ResponseEntity.ok().build();
            } else {
                return  ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Res_UpdateDTO> updateMessageById(Long id,Req_UpdateDTO req) {

        try {
            if (req.titulo().isEmpty() || req.msg().isEmpty()) {
                throw new DataIntegrityViolationException("Fields 'msg' and 'titulo' must not be null");
            }
            if (posterRepository.existsById(id)) {

                posterRepository.updateMessageById(id,req.msg(),req.titulo());
                return ResponseEntity.ok(new Res_UpdateDTO(id, true, "Update operation succeeded"));

            } else {
                return ResponseEntity.ok(new Res_UpdateDTO(id, false, "Entity not found")) ;
            }
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.ok(new Res_UpdateDTO(id, false, "Delete operation failed: " + e.getMessage()));
        }
    }
}
