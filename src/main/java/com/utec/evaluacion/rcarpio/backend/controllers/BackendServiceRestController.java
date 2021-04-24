/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utec.evaluacion.rcarpio.backend.controllers;

import com.google.gson.JsonObject;
import com.utec.evaluacion.rcarpio.backend.entities.Client;
import com.utec.evaluacion.rcarpio.backend.repository.ClientRepository;
import com.utec.evaluacion.rcarpio.backend.singleton.ApiTokenSingleton;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Casa
 */
@RestController
public class BackendServiceRestController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        JsonObject response = new JsonObject();
        try {
            response.addProperty("database", "success");
            response.addProperty("disk", "success");
            response.addProperty("network", "success");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param cliendId
     * @param clientSecret
     * @return
     */
    @GetMapping("/client")
    public ResponseEntity<String> client(@RequestParam(name = "cliendId") String cliendId, @RequestParam(name = "clientSecret") String clientSecret) {
        JsonObject response = new JsonObject();
        try {
            System.out.println("cliendId:" + cliendId);
            System.out.println("clientSecret:" + clientSecret);
            Client client = new Client();
            client.setClientId(cliendId);
            client.setClientSecret(clientSecret);
            clientRepository.save(client);
            response.addProperty("status", "200");
            response.addProperty("message", "success");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/token")
    public ResponseEntity<String> token(HttpServletRequest request) {
        JsonObject response = new JsonObject();
        try {
            String clientId = request.getHeader("clientId");
            Optional<Client> opClient = clientRepository.findById(clientId);
            if (opClient.isPresent()) {
                String token = ApiTokenSingleton.getInstance().generateToken(clientId);
                response.addProperty("token", token);
                System.out.println("*** Token:" + token);
            } else {
                response.addProperty("status", 500);
                response.addProperty("message", "internal error");
            }
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/introspect")
    public ResponseEntity<String> introspect(HttpServletRequest request) {
        JsonObject response = new JsonObject();
        try {
            String token = request.getHeader("token");
            if (ApiTokenSingleton.getInstance().containsKey(token)) {
                String clientId = ApiTokenSingleton.getInstance().get(token);
                response.addProperty("status", 200);
                response.addProperty("message", "success");
                response.addProperty("clientId", clientId);
            } else {
                response.addProperty("status", 400);
                response.addProperty("message", "error");
            }
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
