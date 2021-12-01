package database.factory.impl;

import database.MyDatabase;
import database.factory.ISalary;
import model.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSalary implements ISalary {

    private static SqlSalary instance;
    private final MyDatabase dbConnection;

    public SqlSalary() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlSalary getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlSalary();
        }
        return instance;
    }

    @Override
    public void insert(Salary obj) {
        String str = "INSERT INTO salary (size, method) VALUES("
                + obj.getSize() + ",'" + obj.getMethod() + "')";
        dbConnection.insert(str);
    }

    @Override
    public Salary selectSalary(int id) throws SQLException {
        String str = "SELECT * FROM salary WHERE id=" + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Salary salary = new Salary();
        for (String[] items: result){
            salary.setIdSalary(Integer.parseInt(items[0]));
            salary.setSize(Double.parseDouble(items[1]));
            salary.setMethod(items[2]);
        }
        return salary;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM salary WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Salary> findAll() throws SQLException {
        String str = "SELECT * FROM salary";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Salary> list = new ArrayList<>();
        for (String[] items: result){
            Salary salary = new Salary();
            salary.setIdSalary(Integer.parseInt(items[0]));
            salary.setSize(Double.parseDouble(items[1]));
            salary.setMethod(items[2]);
            list.add(salary);
        }
        return list;
    }
}
