package javaposter.com.javaposter.repository;

import javaposter.com.javaposter.model.poster.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PosterRepository extends JpaRepository<Poster, Long> {
    Optional<Poster> findById(Long id);

    @Query("SELECT p FROM Poster p WHERE p.user.id = :userId")
    List<Poster> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Poster p WHERE p.id = :id")
    void deletePosterById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Poster p SET p.mensagem = :msg, p.titulo = :titulo, p.dataCriacao = CURRENT_TIMESTAMP WHERE p.id = :id")
    void updateMessageById(@Param("id") Long id, @Param("msg") String msg, @Param("titulo") String titulo);

}
