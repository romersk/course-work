package database.factory.impl;

import database.MyDatabase;
import database.factory.IUser;
import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlUser implements IUser {

    private static SqlUser instance;
    private final MyDatabase dbConnection;

    public SqlUser() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlUser getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlUser();
        }
        return instance;
    }

    @Override
    public void update(User obj, int id) {

    }

    @Override
    public void insert(User obj) {
        String str = "INSERT INTO user_type (id_person, login, password, role) VALUES("
                + obj.getIdPerson() + ",'" + obj.getLogin() + "','"
                + obj.getPassword() + "','" + obj.getRole() + "')";
        dbConnection.insert(str);
    }

    @Override
    public User selectUser(String login, String password) throws SQLException {
        String str = "SELECT * FROM user_type WHERE login='" + login
                + "' AND pass='" + password + "'";
        ArrayList<String[]> result = dbConnection.select(str);
        User user = new User();
        for (String[] items: result){
            user.setIdUser(Integer.parseInt(items[0]));
            user.setIdPerson(Integer.parseInt(items[1]));
            user.setLogin(items[2]);
            user.setPassword(items[3]);
            user.setRole(items[4]);
        }
        return user;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM user_type WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<User> findAll() throws SQLException {
        String str = "SELECT * FROM user_type";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<User> users = new ArrayList<>();
        for (String[] items: result){
            User user = new User();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setIdPerson(Integer.parseInt(items[1]));
            user.setLogin(items[2]);
            user.setPassword(items[3]);
            user.setRole(items[4]);
            users.add(user);
        }
        return users;
    }
}
