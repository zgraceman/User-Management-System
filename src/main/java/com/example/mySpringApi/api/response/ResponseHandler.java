package com.example.mySpringApi.api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to generate a standard HTTP response for the API.
 * It creates a structured response containing status, message, and data.
 * By using this class, we can ensure consistency across all API endpoints
 * and make it easier for clients to parse the response.
 */
public class ResponseHandler {

    /**
     * Generates a structured HTTP response with a message, status, and a response object.
     *
     * @param message     the message that should be conveyed to the client. This could be
     *                    something like "User fetched successfully" or "Error fetching user".
     * @param status      the HTTP status of the response. This is used by the client to
     *                    quickly understand if the request was successful or not.
     * @param responseObj the actual data object that the client requested. This could be
     *                    anything from a single User object to a list of Users or even a
     *                    more complex object. In case of an error, this might be null.
     *
     * @return a ResponseEntity containing a Map with the message, status and responseObj.
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("message", message);
        map.put("data", responseObj);
        return new ResponseEntity<>(map,status);
    }
}
