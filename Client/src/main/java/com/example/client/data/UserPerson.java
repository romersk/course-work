package com.example.client.data;

public class UserPerson {
    private int id;
    private String login;
    private String password;
    private int idPerson;
    private String lastName;
    private String firstName;

    public UserPerson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "{ id=" + id +
                ", idPerson=" + idPerson +
                ", login=" + login +
                ", password=" + password +
                ", lastName=" + lastName +
                ", firstName=" + firstName + " }";
    }
}
