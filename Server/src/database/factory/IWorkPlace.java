package database.factory;

import model.WorkPlace;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IWorkPlace {
    void insert(WorkPlace obj);
    WorkPlace selectWorkPlace(int id) throws SQLException;
    void delete(int id);
    ArrayList<WorkPlace> findAll() throws SQLException;
}
