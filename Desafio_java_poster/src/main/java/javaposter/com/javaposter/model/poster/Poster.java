package javaposter.com.javaposter.model.poster;

import jakarta.persistence.*;
import javaposter.com.javaposter.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Poster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_topico")
    private Long id;

    private String autor;
    private String titulo;
    private boolean status;
    private String mensagem;
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "topico")
    private Topics topico;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private User user;

}
