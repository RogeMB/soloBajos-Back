package com.solobajos.solobajos.service;

import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.exception.EmptyUserListException;
import com.solobajos.solobajos.exception.PasswordNotMathException;
import com.solobajos.solobajos.exception.UserNotFoundException;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.model.UserRole;
import com.solobajos.solobajos.repository.UserRepository;
import com.solobajos.solobajos.search.specification.UserSpecificationBuilder;
import com.solobajos.solobajos.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final StorageService storageService;
    public User createUser(CreateUserDto createUserDto, EnumSet<UserRole> roles) {
        return userRepository.save(
            User.builder()
                    .username(createUserDto.username())
                    .fullName(createUserDto.fullName())
                    .email(createUserDto.email())
                    .password(passwordEncoder.encode(createUserDto.password()))
                    .roles(roles)
                    .build()
        );
    }

    public User createUserWithUserRole(CreateUserDto createUserRequest) {
        return createUser(createUserRequest, EnumSet.of(UserRole.USER));
    }

    public User createUserWithAdminRole(CreateUserDto createUserDto) {
        return createUser(createUserDto, EnumSet.of(UserRole.ADMIN));
    }

    public PageDto<UserResponse> findAllSearch(List<SearchCriteria> params, Pageable pageable){
        UserSpecificationBuilder userSpecificationBuilderBuilder = new UserSpecificationBuilder(params);

        Specification<User> spec = userSpecificationBuilderBuilder.build();
        Page<UserResponse> pageUserDto = userRepository.findAll(spec, pageable).map(UserResponse::fromUser);

        if(pageUserDto.isEmpty())
            throw new EmptyUserListException();

        return new PageDto<>(pageUserDto);
    }

    /*
    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            throw new EmptyUserListException();
        }
        return users;
    }
    */

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }
    /*
    public Optional<User> edit(User user) {
        User userfound = userRepository.findById(user.getId())
                .orElseThrow(()->new EntityNotFoundException("No user found with that id"));

        return userRepository.findById(userfound.getId())
                .map(u -> {
                    u.setAvatar(user.getAvatar());
                    u.setFullName(user.getFullName());
                    return userRepository.save(u);
                });
    }
    */

    public User editDetails(UUID id, EditUserDto editUserDto, MultipartFile file) {
        String filename = storageService.store(file);

        return userRepository.findById(id)
                .map(user -> {
                    user.setAvatar(filename);
                    user.setFullName(editUserDto.getFullName());
                    return userRepository.save(user);
                })
                .orElseThrow(() ->new EntityNotFoundException("No user with id: " + id));
    }


    public User editPassword(User user, ChangePasswordRequest changePasswordRequest) throws PasswordNotMathException {
        if(this.passwordMatch(user, changePasswordRequest.oldPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
                return userRepository.save(user);
            }
        throw new PasswordNotMathException();
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