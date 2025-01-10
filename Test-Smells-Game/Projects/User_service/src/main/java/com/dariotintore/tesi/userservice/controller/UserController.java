package com.dariotintore.tesi.userservice.controller;

import com.dariotintore.tesi.userservice.dto.user.AuthUserDTO;
import com.dariotintore.tesi.userservice.dto.user.MissionStatusDTO;
import com.dariotintore.tesi.userservice.dto.user.UserInfoDTO;
import com.dariotintore.tesi.userservice.dto.user.UserModelDTO;
import com.dariotintore.tesi.userservice.entity.MissionStatus;
import com.dariotintore.tesi.userservice.entity.User;
import com.dariotintore.tesi.userservice.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for user operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping("/")
    public List<UserModelDTO> getAllUsers() {
        return userService.getAllUsersDTO();
    }

    /**
     * Save user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity<JSONObject> saveUser(@RequestBody AuthUserDTO user) {
        return userService.saveUser(user);
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @GetMapping("/{id}")
    public Optional<UserModelDTO> getUserById(@PathVariable Long id) {
        return userService.getUserDTOById(id);
    }

    /**
     * Delete user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @DeleteMapping("/")
    public ResponseEntity<JSONObject> deleteUser(@RequestBody AuthUserDTO user) {
        return userService.deleteUserById(user);
    }

    /**
     * Login response entity.
     *
     * @param user the user
     * @return the authentication entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JSONObject> login(@RequestBody AuthUserDTO user) {
        return userService.login(user);
    }

    /**
     * Get user data user info dto.
     *
     * @param body the body
     * @return the user info dto
     */
    @PostMapping("/userData")
    public UserInfoDTO getUserData(@RequestBody JSONObject body){
        return userService.getUserData(body);
    }

    /**
     * Get user exp.
     *
     * @param id the user id
     * @return the user exp
     */
    @GetMapping("/{id}/exp")
    public int getUserExp(@PathVariable Long id) {
        return userService.getUserExp(id);
    }
    
    /**
     * Update user exp.
     *
     * @param user user DTO with updated exp
     * @return HTTP response
     */
    @PostMapping("/updateExp")
    public ResponseEntity<Map<String, String>> updateUserExp(@RequestBody AuthUserDTO user) {
        try {
            userService.updateUserExp(user);  
            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error updating user exp"));
        }
    }

    /**
     * Get user missions status.
     *
     * @param id the user id
     * @return the user exp
     */
    @GetMapping("/{id}/missions")
    public List<MissionStatus> getUserMissionsStatus(@PathVariable Long id) {
        return userService.getUserMissionsStatus(id);
    }

    /**
     * Update user mission status.
     *
     * @param missionStatusDTO mission DTO with updated steps' mission
     * @return HTTP response
     */
    @PutMapping("/missions")
    public ResponseEntity<Object> updateUserMissionStatus(@RequestBody MissionStatusDTO missionStatusDTO) {
        User user = userService.updateUserMissionStatus(missionStatusDTO);

        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user mission status");
    }
}
