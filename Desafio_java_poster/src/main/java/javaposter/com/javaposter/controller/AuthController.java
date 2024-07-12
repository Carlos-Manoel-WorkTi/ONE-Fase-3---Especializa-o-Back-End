package javaposter.com.javaposter.controller;

import jakarta.validation.Valid;
import javaposter.com.javaposter.DTOs.DateAuthDTOs;
import javaposter.com.javaposter.DTOs.RegisterRequestDTO;
import javaposter.com.javaposter.DTOs.ResponseOk;
import javaposter.com.javaposter.model.user.User;
import javaposter.com.javaposter.repository.PosterRepository;
import javaposter.com.javaposter.repository.UserRepository;
import javaposter.com.javaposter.service.authentication.TkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TkService tkService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ResponseOk> login(@RequestBody @Valid DateAuthDTOs date){

        User user = this.userRepository.findByEmail(date.email()).orElseThrow(() -> new RuntimeException("User do not found"));
        if (passwordEncoder.matches(date.senha(),user.getPassword())){

            String token = this.tkService.generateToken(user);
            return  ResponseEntity.ok(new ResponseOk(user.getName(),user.getEmail(),token));
        }
        return ResponseEntity.badRequest().build();
    }



    @PostMapping("/register")
    public ResponseEntity<ResponseOk> login(@RequestBody @Valid RegisterRequestDTO date){
        Optional<User> userok = this.userRepository.findByEmail(date.email());

        if (userok.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(date.senha()));
            newUser.setEmail(date.email());
            newUser.setName(date.nome());

            this.userRepository.save(newUser);

            String token = this.tkService.generateToken(newUser);
            return  ResponseEntity.ok(new ResponseOk(newUser.getName(),newUser.getEmail(),token));
        }

        return ResponseEntity.badRequest().build();
    }
}



