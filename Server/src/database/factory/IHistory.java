package database.factory;

import model.History;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IHistory {
    int insert(History obj);
    History selectHistoryById(int id) throws SQLException;
    void delete(int id);
    ArrayList<History> findAll() throws SQLException;
}
