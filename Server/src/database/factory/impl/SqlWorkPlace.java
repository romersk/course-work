package database.factory.impl;

import database.MyDatabase;
import database.factory.IWorkPlace;
import model.WorkPlace;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlWorkPlace implements IWorkPlace {

    private static SqlWorkPlace instance;
    private final MyDatabase dbConnection;

    public SqlWorkPlace() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlWorkPlace getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlWorkPlace();
        }
        return instance;
    }


    @Override
    public int insert(WorkPlace obj) {
        String str = "INSERT INTO work_place (name, address) VALUES('"
                + obj.getName() + "','" + obj.getAddress() + "') RETURNING id";
        ArrayList<String[]> result = dbConnection.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void update(WorkPlace obj, int id) {
        String str = "UPDATE work_place SET work_place.name='"
                + obj.getName()
                + "', work_place.address='"
                + obj.getAddress()
                + "'  WHERE work_place.id=" + obj.getIdWorkPlace();
        dbConnection.update(str);
    }

    @Override
    public WorkPlace selectWorkPlace(String name) throws SQLException {
        String str = "SELECT * FROM work_place WHERE name='" + name +"'";
        ArrayList<String[]> result = dbConnection.select(str);
        WorkPlace workPlace = new WorkPlace();
        for (String[] items: result){
            workPlace.setIdWorkPlace(Integer.parseInt(items[0]));
            workPlace.setName(items[1]);
            workPlace.setAddress(items[2]);
        }
        return workPlace;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM work_place WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<WorkPlace> findAll() throws SQLException {
        String str = "SELECT * FROM work_place";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<WorkPlace> workPlaces = new ArrayList<>();
        for (String[] items: result){
            WorkPlace workPlace = new WorkPlace();
            workPlace.setIdWorkPlace(Integer.parseInt(items[0]));
            workPlace.setName(items[1]);
            workPlace.setAddress(items[2]);
            workPlaces.add(workPlace);
        }
        return workPlaces;
    }
}
