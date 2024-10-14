package com.dariotintore.tesi.userservice.service;

import com.dariotintore.tesi.userservice.dto.user.AuthUserDTO;
import com.dariotintore.tesi.userservice.dto.user.UserInfoDTO;
import com.dariotintore.tesi.userservice.dto.user.UserModelDTO;
import com.dariotintore.tesi.userservice.entity.User;
import com.dariotintore.tesi.userservice.repository.UserRepository;
import com.dariotintore.tesi.userservice.utils.ResponseHelper;
import com.dariotintore.tesi.userservice.utils.UserCheck;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<JSONObject> saveUser(AuthUserDTO userDTO) {
        // CHECK PRE-REGISTRATION
        if (userRepository.doesUserExist(userDTO.getEmail()) > 0)
            return ResponseHelper.buildPersonalizedResponse("Email already used", 401);
        if (!UserCheck.isPasswordLongEnough(userDTO.getPassword()))
            return ResponseHelper.buildBadRequestResponse("Password too short");
        if(userRepository.doesUserNameExist(userDTO.getUsername()) > 0)
            return ResponseHelper.buildPersonalizedResponse("Username already used", 402);
        // REGISTRATION
        userDTO.setPassword(UserCheck.passwordEncrypter(userDTO.getPassword()));

        userRepository.save(User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .userName(userDTO.getUsername())
                .exp(0)  
                .build()
        );
        return ResponseHelper.buildOkResponse("User created");
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<JSONObject> deleteUserById(AuthUserDTO user) {
        if (checkCredentials(user.getEmail(),user.getPassword())) {
            userRepository.deleteById(userRepository.getIdByEmail(user.getEmail()));
            return ResponseHelper.buildOkResponse("User deleted");
        } else {
            return ResponseHelper.buildNotFoundResponse("User not found");
        }
    }

    public boolean checkCredentials(String email, String password){
        if (userRepository.doesUserExist(email) > 0) {
            String encryptedPassword = userRepository.getEncryptedPasswordFromEmail(email, password);
            // Check password validity
            return UserCheck.checkPassword(password, encryptedPassword);
        }
        return false;
    }

    public ResponseEntity<JSONObject> login(AuthUserDTO user) {
        if (userRepository.doesUserExist(user.getEmail()) > 0) {
            String encryptedPassword = userRepository.getEncryptedPasswordFromEmail(user.getEmail(), user.getPassword());
            if (UserCheck.checkPassword(user.getPassword(), encryptedPassword)) { // Check password validity
                String token = getToken(user.getEmail());
                return ResponseHelper.buildOkResponse("Login effettuato", token);
            }
        }
        return ResponseHelper.buildPersonalizedResponse("Email o password errata",401);
    }

    public String getToken(String email) {
        return tokenService.generateToken(email);
    }

    public List<UserModelDTO> getAllUsersDTO() {
        List<User> allUsers = this.getAllUsers();
        List<UserModelDTO> result = new ArrayList<>();
        allUsers.forEach(user -> result.add(this.modelMapper.map(user,UserModelDTO.class)));
        return result;
    }

    public Optional<UserModelDTO> getUserDTOById(Long id) {
        UserModelDTO userDTO = this.modelMapper.map(this.findUserById(id), UserModelDTO.class);
        return Optional.ofNullable(userDTO);
    }

    public UserInfoDTO getUserData(JSONObject body) {
        String token = (String) body.get("token");
        String email = (String) body.get("email");
        if (tokenService.validateToken(email, token)){
            Optional<User> optionalUser = userRepository.getUserByEmail(email);
            if(optionalUser.isPresent()){
                User u = optionalUser.get();
                return UserInfoDTO.builder()
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .token(token)
                        .email(email)
                        .userId(u.getUserId())
                        .userName(u.getUserName())
                        .exp(u.getExp())  
                        .build();
            }
        }
        return null;
    }

    public int getUserExp(Long userId) {
        Optional<User> user = findUserById(userId);
        return user.map(User::getExp).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    public void updateUserExp(AuthUserDTO userDTO) {
        Optional<User> optionalUser = userRepository.getUserByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setExp(userDTO.getExp());  
            userRepository.save(user); //  
        } else {
            throw new RuntimeException("User not found!");
        }
    }
}
