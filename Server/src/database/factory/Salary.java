package database.factory;

import java.util.ArrayList;

public interface Salary {
    void insert(Salary obj);
    Salary selectPerson(Salary obj);
    void delete(Salary obj);
    ArrayList<Salary> findAll();
}
