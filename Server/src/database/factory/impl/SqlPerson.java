package database.factory.impl;

import database.MyDatabase;
import database.factory.IPerson;
import model.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlPerson implements IPerson {

    private static SqlPerson instance;
    private final MyDatabase dbConnection;

    public SqlPerson() throws SQLException, ClassNotFoundException {
        dbConnection = MyDatabase.getInstance();
    }

    public static synchronized SqlPerson getInstance()
            throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new SqlPerson();
        }
        return instance;
    }

    @Override
    public void insert(Person obj) {
        String str = "INSERT INTO person (id_user, id_place, first_name, last_name, address) VALUES("
                + obj.getIdUser() + "," + obj.getIdWorkPlace() + ",'" + obj.getFirstName() + "','"
                + obj.getLastName() + "','" + obj.getAddress() + "')";
        dbConnection.insert(str);
    }

    @Override
    public void update(Person obj, int id) {
        String str = "UPDATE person SET person.id_user="
                + obj.getIdUser()
                + ", person.id_place="
                + obj.getIdWorkPlace()
                + ", person.first_name='"
                + obj.getFirstName()
                + "', person.last_name='"
                + obj.getLastName()
                + "', person.address='"
                + obj.getAddress()
                + "'  WHERE person.id=" + id;
        dbConnection.update(str);
    }

    @Override
    public Person selectPerson(int id) throws SQLException {
        String str = "SELECT * FROM person WHERE id = " + id;
        ArrayList<String[]> result = dbConnection.select(str);
        Person person = new Person();
        for (String[] items: result){
            person.setIdPerson(Integer.parseInt(items[0]));
            person.setIdUser(Integer.parseInt(items[1]));
            person.setIdWorkPlace(Integer.parseInt(items[2]));
            person.setFirstName(items[3]);
            person.setLastName(items[4]);
            person.setAddress(items[5]);
        }
        return person;
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM person WHERE id = " + id;
        dbConnection.delete(str);
    }

    @Override
    public ArrayList<Person> findAll() throws SQLException {
        String str = "SELECT * FROM person";
        ArrayList<String[]> result = dbConnection.select(str);
        ArrayList<Person> people = new ArrayList<>();
        for (String[] items: result){
            Person person = new Person();
            person.setIdPerson(Integer.parseInt(items[0]));
            person.setIdUser(Integer.parseInt(items[1]));
            person.setIdWorkPlace(Integer.parseInt(items[2]));
            person.setFirstName(items[3]);
            person.setLastName(items[4]);
            person.setAddress(items[5]);
            people.add(person);
        }
        return people;
    }
}
