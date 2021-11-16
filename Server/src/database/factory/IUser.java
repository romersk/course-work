package database.factory;

import model.User;

import java.util.ArrayList;

public interface IUser {
    void update(User obj);
    void insert(User obj);
    User selectUser(User obj);
    void delete(User obj);
    ArrayList<User> findAll();
}
