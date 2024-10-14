package com.dariotintore.tesi.userservice.utils;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ResponseHelper {

    private ResponseHelper(){
        // empty
    }

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String ERROR = "error_code";

    public static ResponseEntity<JSONObject> buildOkResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(STATUS, 200);
        response.put(MESSAGE, message);
        response.put(TIMESTAMP, date);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<JSONObject> buildOkResponse(JSONObject body, String message) {
        Date date = new Date();
        body.put(STATUS, 200);
        body.put(MESSAGE, message);
        body.put(TIMESTAMP, date);
        return ResponseEntity.ok(body);
    }


    public static ResponseEntity<JSONObject> buildOkResponse(String message, String token) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(STATUS, 200);
        response.put(MESSAGE, message);
        response.put("token", token);
        response.put(TIMESTAMP, date);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<JSONObject> buildBadRequestResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(STATUS, 400);
        response.put(MESSAGE, message);
        response.put(TIMESTAMP, date);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<JSONObject> buildPersonalizedResponse(String message, int error){
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(ERROR, error);
        response.put(MESSAGE, message);
        response.put(TIMESTAMP, date);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    public static ResponseEntity<JSONObject> buildForbiddenResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(STATUS, 403);
        response.put(MESSAGE, message);
        response.put(TIMESTAMP, date);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    public static ResponseEntity<JSONObject> buildNotFoundResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put(STATUS, 404);
        response.put(MESSAGE, message);
        response.put(TIMESTAMP, date);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}