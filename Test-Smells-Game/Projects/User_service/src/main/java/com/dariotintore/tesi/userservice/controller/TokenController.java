package com.dariotintore.tesi.userservice.controller;

import com.dariotintore.tesi.userservice.service.TokenService;
import com.dariotintore.tesi.userservice.utils.ResponseHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The type Token controller.
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * Generate token string.
     *
     * @param body the body
     * @return the string
     */
    @PostMapping("/")
    public String generateToken(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return tokenService.generateToken(email);
    }

    /**
     * Validate token response entity.
     *
     * @param body the body
     * @return the response entity
     */
    @PostMapping("/validate")
    public ResponseEntity<JSONObject> validateToken(@RequestBody JSONObject body) {
        String email = (String) body.get("email");
        String token = (String) body.get("token");
        if(tokenService.validateToken(email,token)){
            return ResponseHelper.buildOkResponse("OK");
        }else{
            return ResponseHelper.buildBadRequestResponse("NO");
        }
    }

}
