package com.relex.database.service;

import com.relex.database.repository.UserRepository;
import com.relex.models.User;
import com.relex.security.dtos.RegistrationUserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public Long findIdByMail(String mail){
        return userRepository.findUserByMail(mail).getId();
    }

    public String findNicknameByMail(String mail){
        return userRepository.findUserByMail(mail).getNickname();
    }

    public User findUserByMail(String mail){
        return userRepository.findUserByMail(mail);
    }

    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = findByMail(mail).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", mail)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getMail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setNickname(registrationUserDto.getNickname());
        user.setName(registrationUserDto.getName());
        user.setSurname(registrationUserDto.getSurname());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setMail(registrationUserDto.getMail());
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public void updateEnable(String mail){
        User user = userRepository.findUserByMail(mail);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean checkActivity(String mail){
        User user = userRepository.findUserByMail(mail);
        return user.isActivity();
    }
}
