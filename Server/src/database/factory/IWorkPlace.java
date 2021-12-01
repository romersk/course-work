package database.factory;

import model.WorkPlace;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IWorkPlace {
    int insert(WorkPlace obj);
    void update(WorkPlace obj, int id);
    WorkPlace selectWorkPlace(String name) throws SQLException;
    void delete(int id);
    ArrayList<WorkPlace> findAll() throws SQLException;
}
