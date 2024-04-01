package com.kazzak.externalservice.model;

public class Cliente {
    public String name;
    public String cpf;
    public String email;

    public String toJsonString() {
        return "{ \"nome\": \"" + name + "\", \"cpf\": \"" + cpf + "\", \"email\": \"" + email + "\" }";
    }
}