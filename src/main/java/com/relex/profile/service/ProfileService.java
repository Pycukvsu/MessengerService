package com.relex.profile.service;

import com.relex.database.repository.UserRepository;
import com.relex.database.service.UserService;
import com.relex.exeptions.AppError;
import com.relex.profile.dtos.PasswordDto;
import com.relex.profile.dtos.UpdateProfileDto;
import com.relex.models.User;
import com.relex.profile.dtos.UserProfileDto;
import com.relex.security.utils.JwtTokenUtils;
import com.relex.security.verivication.sender.DefaultEmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProfileService {
    private UserService userService;
    private UserRepository userRepository;
    private DefaultEmailService defaultEmailService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> updateProfile(UpdateProfileDto updateProfileDto){
        User user = userRepository.findUserByMail(jwtTokenUtils.getMailFromSecurityContext());
        user.setNickname(updateProfileDto.getNickname());
        user.setName(updateProfileDto.getName());
        user.setSurname(updateProfileDto.getSurname());
        if (!user.getMail().equals(updateProfileDto.getNewMail())){
            user.setEnabled(false);
            user.setMail(updateProfileDto.getNewMail());
            userRepository.save(user);
            verifyMail(user);
        }else {
            userRepository.save(user);
        }
        return ResponseEntity.ok(new UserProfileDto(user.getNickname(), user.getName(), user.getSurname(), user.getMail()));
    }

    public void verifyMail(User user){
        UserDetails userDetails = userService.loadUserByUsername(user.getMail());
        String token = jwtTokenUtils.generateToken(userDetails);
        defaultEmailService.sendSimpleEmail(user, token);
    }

    public ResponseEntity<?> updatePassword(PasswordDto passwordDto){
        User user = userRepository.findUserByMail(jwtTokenUtils.getMailFromSecurityContext());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getMail(), passwordDto.getLastPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Старый пароль не подходит"), HttpStatus.BAD_REQUEST);
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmNewPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Пароль успешно изменён!");
    }

    public ResponseEntity<?> freezeProfileActivity(){
        User user = userRepository.findUserByMail(jwtTokenUtils.getMailFromSecurityContext());
        user.setActivity(false);
        userRepository.save(user);
        return ResponseEntity.ok("Активность аккаунта приостановлена");
    }

    public ResponseEntity<?> unfreezeTheProfile(){
        User user = userRepository.findUserByMail(jwtTokenUtils.getMailFromSecurityContext());
        user.setActivity(true);
        userRepository.save(user);
        return ResponseEntity.ok("Аккаунт восстановлен");
    }

    @Transactional
    public ResponseEntity<?> deleteProfile(){
        userRepository.deleteUserByMail(jwtTokenUtils.getMailFromSecurityContext());
        return ResponseEntity.ok("Аккаунт удален");
    }
}
