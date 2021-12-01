package server;

import database.MyDatabase;
import database.factory.IPerson;
import database.factory.IUser;
import database.factory.IWorkPlace;
import database.factory.impl.SqlPerson;
import database.factory.impl.SqlUser;
import database.factory.impl.SqlWorkPlace;
import model.Person;
import model.User;
import model.WorkPlace;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerWork {

    private ObjectInputStream in;
    private MyDatabase database;
    private ObjectOutputStream out;

    public ServerWork (ObjectInputStream in, MyDatabase database, ObjectOutputStream outputStream){
        this.in = in;
        this.database = database;
        this.out = outputStream;
    }

    public void getId (int idOperation) throws IOException, SQLException, ClassNotFoundException {
        switch(idOperation){
            case 1:
                signingIn();
                break;
            case 2:
                getWorkPlaces();
                break;
            case 3:
                findUserByLogin();
                break;
            case 4:
                getWorkPlaceByName();
                break;
            case 5:
                saveEntityAndGetId();
                break;
            case 6:
                deleteEntityById();
                break;
            case 7:
                updateEntity();
                break;
            case 8:
                getEnityById();
                break;
            default:
                break;
        }
    }

    private void deleteEntityById() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        int id = (int) in.readObject();
        switch (type) {
            case "WorkPlace":
            {
                IWorkPlace iWorkPlace = new SqlWorkPlace();
                iWorkPlace.delete(id);
                break;
            }
            default:
                break;
        }
    }


    private void getEnityById() throws IOException, ClassNotFoundException, SQLException{
        String type = (String) in.readObject();
        int id = (int) in.readObject();
        switch (type) {
            case "Person":
            {
                IPerson iPerson = new SqlPerson();
                Person person = iPerson.selectPerson(id);
                out.writeObject(person);
                break;
            }
            default:
                break;
        }
    }

    private void updateEntity() throws IOException, ClassNotFoundException, SQLException{
        String type = (String) in.readObject();
        switch (type) {
            case "User":
            {
                User user = (User) in.readObject();
                int idUser = (int) in.readObject();
                int idPerson = (int) in.readObject();
                user.setIdPerson(idPerson);
                IUser iUser = new SqlUser();
                iUser.update(user, idUser);
                break;
            }
            case "Person":
            {
                Person person = (Person) in.readObject();
                int idPerson = (int) in.readObject();
                int idUser = (int) in.readObject();
                person.setIdPerson(idPerson);
                person.setIdUser(idUser);
                IPerson iPerson = new SqlPerson();
                iPerson.update(person, idPerson);
                break;
            }
            case "WorkPlace":
            {
                WorkPlace workPlace = (WorkPlace) in.readObject();
                int idWork = (int) in.readObject();
                workPlace.setIdWorkPlace(idWork);
                IWorkPlace iWorkPlace = new SqlWorkPlace();
                iWorkPlace.update(workPlace, idWork);
                break;
            }
            default:
                break;
        }

    }

    private void saveEntityAndGetId() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User":
            {
                User user = (User) in.readObject();
                IUser iUser = new SqlUser();
                int id = iUser.insert(user);
                out.writeObject(id);
                break;
            }
            case "Person":
            {
                Person person = (Person) in.readObject();
                IPerson iPerson = new SqlPerson();
                int id = iPerson.insert(person);
                out.writeObject(id);
                break;
            }
            case "WorkPlace":
            {
                WorkPlace workPlace = (WorkPlace) in.readObject();
                IWorkPlace iWorkPlace = new SqlWorkPlace();
                int id = iWorkPlace.insert(workPlace);
                out.writeObject(id);
                break;
            }
            default:
                break;
        }


    }

    private void getWorkPlaceByName() throws IOException, ClassNotFoundException, SQLException {
        String name = (String) in.readObject();
        IWorkPlace iWorkPlace = new SqlWorkPlace();
        WorkPlace workPlace = iWorkPlace.selectWorkPlace(name);
        out.writeObject(workPlace);
    }

    private void findUserByLogin() throws IOException, ClassNotFoundException, SQLException {
        String login = (String) in.readObject();
        IUser iUser = new SqlUser();
        User user = iUser.selectUserByLogin(login);
        out.writeObject(user);
    }

    private void getWorkPlaces() throws SQLException, ClassNotFoundException, IOException {
        IWorkPlace iWorkPlace = new SqlWorkPlace();
        ArrayList<WorkPlace> list = iWorkPlace.findAll();
        out.writeObject(list);
    }

    private void signingIn() throws IOException, SQLException, ClassNotFoundException {
        String login = (String) in.readObject();
        String password = (String) in.readObject();

        IUser iUser = new SqlUser();
        User user = iUser.selectUser(login, password);

        out.writeObject(user);
    }
}
