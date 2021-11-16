package database.factory;

import model.History;

import java.util.ArrayList;

public interface IHistory {
    void insert(History obj);
    IHistory selectHistory(History obj);
    void delete(History obj);
    ArrayList<History> findAll();
}
