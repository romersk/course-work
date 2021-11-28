package database.factory.impl;

import database.MyDatabase;
import database.factory.IHistory;
import model.History;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlHistory implements IHistory {

    private static SqlHistory instance;
    private final MyDatabase dbConnection;

    private SqlHistory() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlHistory getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlHistory();
        }
        return instance;
    }

    @Override
    public void insert(History obj) {
        String str = "INSERT INTO history (id_user, id_salary) VALUES(" + obj.getIdUser()
                + ", " + obj.getIdSalary() + ")";
        dbConnection.insert(str);
    }

    @Override
    public History selectHistoryById(int id) throws SQLException {
        String str = "SELECT * FROM history WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        History history = new History();
        for (String[] items: result){
            history.setIdHistory(Integer.parseInt(items[0]));
            history.setIdUser(Integer.parseInt(items[1]));
            history.setIdSalary(Integer.parseInt(items[2]));
        }
        return history;
    }


    @Override
    public void delete(int id) {
        String str = "DELETE FROM history WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<History> findAll() throws SQLException {
        String str = "SELECT * FROM history";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<History> allHistory = new ArrayList<>();
        for (String[] items: result){
            History history = new History();
            history.setIdHistory(Integer.parseInt(items[0]));
            history.setIdUser(Integer.parseInt(items[1]));
            history.setIdSalary(Integer.parseInt(items[2]));
            allHistory.add(history);
        }
        return allHistory;
    }
}
