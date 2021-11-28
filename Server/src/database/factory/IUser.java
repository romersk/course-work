package database.factory;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUser {
    void update(User obj, int id);
    void insert(User obj);
    User selectUser(String login, String password) throws SQLException;
    void delete(int id);
    ArrayList<User> findAll() throws SQLException;
}
