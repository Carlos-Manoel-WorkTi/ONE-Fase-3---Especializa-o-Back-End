package javaposter.com.javaposter.controller;

import jakarta.validation.Valid;
import javaposter.com.javaposter.DTOs.Poster.*;
import javaposter.com.javaposter.model.user.User;
import javaposter.com.javaposter.repository.PosterRepository;
import javaposter.com.javaposter.repository.UserRepository;
import javaposter.com.javaposter.service.Poster.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private PosterService posterService;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }

    @PostMapping("/setMessage")
    public Res_MessageDTO setMessage(@RequestBody @Valid Req_MessageDTO msg){

        User user = userRepository.findByEmail(msg.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com esse email "));

        posterService.salve(user, msg.name(), msg.title(), msg.msg(), msg.topic(), true);

        return new Res_MessageDTO(user.getName(),user.getEmail(), true);

    }

    @PostMapping("/getAllMessage")
    public List<Res_All_MessagesDTO> getMessage(@RequestBody @Valid Req_get_All_Message email) {

        Optional<User> optUser = userRepository.findByEmail(email.email());

        if (optUser.isPresent()) {
            User user = optUser.get();
            return posterService.getAllMessages(user);
        } else {
            throw new RuntimeException("Usuário não encontrado para o email: " + email);
        }
    }
    @DeleteMapping("/topics/{id}")
    public ResponseEntity getMessagesById(@PathVariable Long id) {

       return posterService.deleteMessageById(id);

    }
    @PutMapping("/topics/{id}")
    public ResponseEntity<Res_UpdateDTO> updateMessagesById(@PathVariable Long id,@RequestBody Req_UpdateDTO req) {
        System.out.println("----------"+req);
        return posterService.updateMessageById(id,req);

    }
}



