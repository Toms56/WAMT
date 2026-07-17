package com.wamt.data.model;

public final class User {

    private final long id;
    private final String pseudo;

    public User(long id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public long getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }
}
