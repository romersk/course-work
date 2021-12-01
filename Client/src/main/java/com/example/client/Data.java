package com.example.client;

import model.User;

public final class Data {

    private User user;
    private Client client;

    private final static Data instance = new Data();

    private Data() {}

    public static Data getInstance() {
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
