package database.factory;

import model.WorkPlace;

import java.util.ArrayList;

public interface IWorkPlace {
    void insert(WorkPlace obj);
    WorkPlace selectWorkPlace(WorkPlace obj);
    void delete(WorkPlace obj);
    ArrayList<WorkPlace> findAll();
}
