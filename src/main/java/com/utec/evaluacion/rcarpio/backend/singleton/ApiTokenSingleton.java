/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utec.evaluacion.rcarpio.backend.singleton;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Casa
 */
public class ApiTokenSingleton extends LinkedHashMap<String, String> {

    private static ApiTokenSingleton instance;
    private static final int MAX_SIZE = 100;

    private ApiTokenSingleton() {
    }

    public static ApiTokenSingleton getInstance() {
        if (instance == null) {
            instance = new ApiTokenSingleton();
        }
        return instance;
    }

    /**
     *
     * @param entry
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Entry<String, String> entry) {
        return this.size() > MAX_SIZE;
    }

    /**
     *
     * @param clientId
     * @return
     */
    public String generateToken(String clientId) {
        String token = RandomStringUtils.random(15, "abcdefghijklmnopqrstuvxyz1234567890");
        this.put(token, clientId);
        return token;
    }

}
