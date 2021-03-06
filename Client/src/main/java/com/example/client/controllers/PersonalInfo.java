package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Person;
import model.User;
import model.WorkPlace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PersonalInfo {
    public Button buttToBack;
    public Button buttReg;
    public TextField surname;
    public TextField name;
    public TextField address;
    public TextField login;
    public PasswordField password;
    public ComboBox<String> workPlace;
    public Label labelForSurname;
    public Label labelForName;
    public Label labelForAddress;
    public Label labelForWork;
    public Label labelForLogin;
    public Label labelForPass;
    private final String TEXT_REGEX = "[А-Яа-яё]{2,30}";

    public void toBack(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        buttToBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (data.getEditUser() == null) {
            if (Objects.equals(data.getUser().getRole(), "Admin")) {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
            } else {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
            }
        } else {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-control.fxml"));
        }
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void toSave(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (isFilled()) {
            Data data = Data.getInstance();
            User user = new User();
            if (data.getEditUser() == null) {
                user.setIdUser(data.getUser().getIdUser());
                user.setLogin(login.getText());
                user.setPassword(password.getText());
                user.setRole(data.getUser().getRole());

                data.getUser().setLogin(login.getText());
                data.getUser().setPassword(password.getText());

                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(data.getUser().getIdPerson());
            } else {
                user.setIdUser(data.getEditUser().getIdUser());
                user.setLogin(login.getText());
                user.setPassword(password.getText());
                user.setRole(data.getEditUser().getRole());

                data.getEditUser().setLogin(login.getText());
                data.getEditUser().setPassword(password.getText());

                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(data.getEditUser().getIdPerson());
            }

            Person person = (Person) data.getClient().getObject();
            person.setFirstName(name.getText());
            person.setLastName(surname.getText());
            person.setAddress(address.getText());

            data.getClient().writeInt(4);
            data.getClient().writeObject(workPlace.getValue());
            WorkPlace workPlace = (WorkPlace) data.getClient().getObject();
            person.setIdWorkPlace(workPlace.getIdWorkPlace());

            data.getClient().writeInt(7);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(person);
            data.getClient().writeObject(person.getIdPerson());
            data.getClient().writeObject(user.getIdUser());

            data.getClient().writeInt(7);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            data.getClient().writeObject(user.getIdPerson());
            data.getClient().writeObject(person.getIdPerson());
            this.toBack(actionEvent);
        }
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        if (data.getEditUser() == null) {
            login.setText(data.getUser().getLogin());
            password.setText(data.getUser().getPassword());
            data.getClient().writeInt(8);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(data.getUser().getIdPerson());

        } else {
            login.setText(data.getEditUser().getLogin());
            password.setText(data.getEditUser().getPassword());
            data.getClient().writeInt(8);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(data.getEditUser().getIdPerson());
        }
        Person person = (Person) data.getClient().getObject();
        surname.setText(person.getLastName());
        name.setText(person.getFirstName());
        address.setText(person.getAddress());

        data.getClient().writeInt(2);
        data.getClient().writeObject("WorkPlace");
        ArrayList<WorkPlace> list = (ArrayList<WorkPlace>) data.getClient().getObject();
        ObservableList<String> langs = FXCollections.observableArrayList();
        for (WorkPlace obj:
                list) {
            langs.add(obj.getName());
            if (obj.getIdWorkPlace() == person.getIdWorkPlace()) {
                workPlace.setValue(obj.getName());
            }
        }
        workPlace.setItems(langs);
    }

    private boolean isFilled() throws IOException, ClassNotFoundException {
        boolean answer = true;
        if (surname.getText().equals("") || !surname.getText().matches(TEXT_REGEX)) {
            labelForSurname.setText("Заполните поле");
            answer = false;
        } else {
            labelForSurname.setText("");
        }
        if (name.getText().equals("") || !name.getText().matches(TEXT_REGEX)) {
            labelForName.setText("Заполните поле");
            answer = false;
        } else {
            labelForName.setText("");
        }
        if (address.getText().equals("")) {
            labelForAddress.setText("Заполните поле");
            answer = false;
        } else {
            labelForAddress.setText("");
        }
        if (login.getText().equals("")) {
            labelForLogin.setText("Заполните поле");
            answer = false;
        } else  {
            Data data = Data.getInstance();
            data.getClient().writeInt(3);
            data.getClient().writeLine(login.getText());
            User user = (User) data.getClient().getObject();
            if ((user.getIdUser() > 0 && !data.getUser().getLogin().equals(user.getLogin()))
            && (!data.getEditUser().getLogin().equals(user.getLogin()))) {
                labelForLogin.setText("Никнейм занят");
                answer = false;
            } else  {
                labelForLogin.setText("");
            }
        }
        if (password.getText().equals("")) {
            labelForPass.setText("Заполните поле");
            answer = false;
        } else if (password.getText().length() < 6){
            labelForPass.setText("Пароль менее 6 символов");
            answer = false;
        } else  {
            labelForWork.setText("");
        }
        return answer;
    }
}
