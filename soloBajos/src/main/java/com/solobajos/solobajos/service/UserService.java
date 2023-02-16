package com.solobajos.solobajos.service;

import com.solobajos.solobajos.dto.CreateUserDto;
import com.solobajos.solobajos.dto.EditUserDto;
import com.solobajos.solobajos.exception.EmptyUserListException;
import com.solobajos.solobajos.exception.UserNotFoundException;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.model.UserRole;
import com.solobajos.solobajos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(CreateUserDto createUserDto, EnumSet<UserRole> roles) {
        return userRepository.save(
            User.builder()
                    .username(createUserDto.username())
                    .password(passwordEncoder.encode(createUserDto.password()))
                    .avatar(createUserDto.avatar())
                    .fullName(createUserDto.fullName())
                    .roles(roles)
                    .build()  //email aquí y en el dto
        );
    }

    public User createUserWithUserRole(CreateUserDto createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserDto createUserDto) {
        return createUser(createUserDto, EnumSet.of(UserRole.ADMIN));
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            throw new EmptyUserListException();
        }
        return users;
    }
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    public Optional<User> edit(User user) {
        // El username no se puede editar
        // La contraseña se edita en otro método

        User userfound = userRepository.findById(user.getId())
                .orElseThrow(()->new EntityNotFoundException("No user found with that id"));

        return userRepository.findById(userfound.getId())
                .map(u -> {
                    u.setAvatar(user.getAvatar());
                    u.setFullName(user.getFullName());
                    return userRepository.save(u);
                });
    }

    // revisar este método
    public User editDetails(UUID id, EditUserDto editUserDto) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setAvatar(editUserDto.getAvatar());
                    user.setFullName(editUserDto.getFullname());
                    return userRepository.save(user);
                })
                .orElseThrow(() ->new EntityNotFoundException("No user with id: " + id));
    }


    public Optional<User> editPassword(UUID userId, String newPassword) {

        // Aquí no se realizan comprobaciones de seguridad. Tan solo se modifica

        return userRepository.findById(userId)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(u);
                }).or(Optional::empty);

    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(UUID id) {
        // Prevenimos errores al intentar borrar algo que no existe
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
    }

    public boolean passwordMatch(User user, String clearPassword) {
        return passwordEncoder.matches(clearPassword, user.getPassword());
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}