package com.dariotintore.tesi.leaderboard.utils;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public class ResponseHelper {

    public static ResponseEntity<JSONObject> buildOkResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put("status", 200);
        response.put("message", message);
        response.put("timestamp", date);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<JSONObject> buildOkResponse(List data){
      JSONObject response = new JSONObject();
      Date date = new Date();
      response.put("status",200);
      response.put("data",data);
      response.put("timestamp",date);
      return ResponseEntity.ok(response);
    }

    public static ResponseEntity<JSONObject> buildOkResponse(JSONObject body, String message) {
        Date date = new Date();
        body.put("status", 200);
        body.put("message", message);
        body.put("timestamp", date);
        return ResponseEntity.ok(body);
    }


    public static ResponseEntity<JSONObject> buildOkResponse(String message, String token) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put("status", 200);
        response.put("message", message);
        response.put("token", token);
        response.put("timestamp", date);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<JSONObject> buildBadRequestResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put("status", 400);
        response.put("message", message);
        response.put("timestamp", date);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static ResponseEntity<JSONObject> buildForbiddenResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put("status", 403);
        response.put("message", message);
        response.put("timestamp", date);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    public static ResponseEntity<JSONObject> buildNotFoundResponse(String message) {
        JSONObject response = new JSONObject();
        Date date = new Date();
        response.put("status", 404);
        response.put("message", message);
        response.put("timestamp", date);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
