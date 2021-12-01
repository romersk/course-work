package database.factory;

import model.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISalary {
    int insert(Salary obj);
    Salary selectSalary(int id) throws SQLException;
    void delete(int id);
    ArrayList<Salary> findAll() throws SQLException;
}
