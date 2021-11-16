package database.factory;

import model.Person;
import model.User;

import java.util.ArrayList;

public interface IPerson {
    void insert(Person obj);
    Person selectPerson(User obj);
    void delete(Person obj);
    ArrayList<Person> findAll();
}
