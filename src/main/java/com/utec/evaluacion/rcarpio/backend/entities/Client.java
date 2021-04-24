/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utec.evaluacion.rcarpio.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Casa
 */
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "clientid")
    private String clientid;
    @Column(name = "clientsecret")
    private String clientsecret;

    public String getClientid() {
        return clientid;
    }

    public void setClientId(String clientid) {
        this.clientid = clientid;
    }

    public String getClientSecret() {
        return clientsecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientsecret = clientsecret;
    }

    @Override
    public String toString() {
        return "Client{" + "clientid=" + clientid + ", clientSecret=" + clientsecret + '}';
    }

}
