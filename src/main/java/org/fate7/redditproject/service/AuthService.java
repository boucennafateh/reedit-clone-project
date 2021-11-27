package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.AuthenticationResponse;
import org.fate7.redditproject.dto.LoginRequest;
import org.fate7.redditproject.dto.RegisterRequest;
import org.fate7.redditproject.exceptions.SpringRedditException;
import org.fate7.redditproject.model.NotificationEmail;
import org.fate7.redditproject.model.User;
import org.fate7.redditproject.model.VerificationToken;
import org.fate7.redditproject.repository.UserRepository;
import org.fate7.redditproject.repository.VerificationTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    @Transactional
    public void signUp(RegisterRequest registerRequest){

        User user = User.builder()
                .username(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();
        userRepository.save(user);
        String token = createVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please activate your account ! ",
                user.getEmail(), "Thank you for signing up for clone Reddit " +
                ", please click on the below URL to activate your account " +
                "http://localhost:8080/api/auth/accountVerification/" + token));



    }

    private String createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .build();
        verificationTokenRepository.save(verificationToken);
        return token;

    }

    @Transactional
    public void verifyToken(String token) {

        Optional<VerificationToken> verificationTokenOp = verificationTokenRepository.findByToken(token);
        VerificationToken verificationToken = verificationTokenOp.orElseThrow(() -> new SpringRedditException("Verification token does not exist .."));
        String userName = verificationToken.getUser().getUsername();
        Optional<User> userOp = userRepository.findByUsername(userName);
        User user = userOp.orElseThrow(() -> new SpringRedditException("Verification token is not affected to any user"));
        user.setEnabled(true);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, loginRequest.getUsername());

    }
}
