package com.relex.security.service;

import com.relex.database.repository.UserRepository;
import com.relex.exeptions.AppError;
import com.relex.security.dtos.JwtRequest;
import com.relex.security.dtos.JwtResponse;
import com.relex.security.dtos.RegistrationUserDto;
import com.relex.security.dtos.UserDto;
import com.relex.security.utils.JwtTokenUtils;
import com.relex.security.verivication.sender.DefaultEmailService;
import com.relex.database.service.UserService;
import lombok.RequiredArgsConstructor;
import com.relex.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final DefaultEmailService defaultEmailService;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        if (!userService.checkActivity(authRequest.getMail())){
            return ResponseEntity.ok("Чтобы войти в профиль нужно разморозить аккаунт");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getMail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getMail());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByMail(registrationUserDto.getMail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с такой почтой уже существует"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByNickname(registrationUserDto.getNickname()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Такой Nickname уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(user.getMail());
        String token = jwtTokenUtils.generateToken(userDetails);
        defaultEmailService.sendSimpleEmail(user, token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> emailConfirmation(@RequestBody String token) {
        String mail = jwtTokenUtils.getMail(token);
        userService.updateEnable(mail);
        return ResponseEntity.ok("Почта подтверждена!");
    }

}
