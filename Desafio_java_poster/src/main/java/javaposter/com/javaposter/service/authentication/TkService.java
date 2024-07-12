package javaposter.com.javaposter.service.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import javaposter.com.javaposter.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TkService {
    @Value("${api.security.token.secret}")
    private String KEY;

    public String generateToken( User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);

            return JWT.create().
                    withIssuer("javaposter")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);


        }catch (JWTCreationException e){
             throw new RuntimeException("Error , eu fiz isso");
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarTK(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);

            return JWT.require(algorithm).
                    withIssuer("javaposter")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTCreationException e){
            return null;
        }
    }

}
