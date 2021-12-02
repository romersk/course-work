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

public class Registration {
    public Button buttToBack;
    public Button buttReg;
    public TextField surname;
    public TextField address;
    public TextField name;
    public TextField login;
    public PasswordField password;
    public ComboBox<String> workPlace;
    private final String TEXT_REGEX = "[А-Яа-яё]{2,30}";
    private final String ENG_TEXT = "[A-Za-z]{5,15}";
    public Label labelForSurname;
    public Label labelForName;
    public Label labelForAddress;
    public Label labelForWork;
    public Label labelForLogin;
    public Label labelForPass;

    public void toBack(ActionEvent actionEvent) {
        buttToBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Data data = Data.getInstance();
        if (data.getEditUser() == null) {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/hello-view.fxml"));
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

    public void toReg(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (isFilled()) {
            Data data = Data.getInstance();
            data.getClient().writeInt(4);
            data.getClient().writeLine(workPlace.getValue());
            WorkPlace workPlace = (WorkPlace) data.getClient().getObject();

            User user = new User();
            user.setLogin(login.getText());
            user.setPassword(password.getText());
            user.setRole("USER");
            data.getClient().writeInt(5);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            int idUser = (int) data.getClient().getObject();
            user.setIdUser(idUser);

            Person person = new Person();
            person.setFirstName(name.getText());
            person.setLastName(surname.getText());
            person.setAddress(address.getText());
            person.setIdUser(idUser);
            person.setIdWorkPlace(workPlace.getIdWorkPlace());
            data.getClient().writeInt(5);
            data.getClient().writeObject("Person");
            data.getClient().writeObject(person);
            int idPerson = (int) data.getClient().getObject();
            person.setIdPerson(idPerson);
            user.setIdPerson(idPerson);

            data.getClient().writeObject(7);
            data.getClient().writeObject("User");
            data.getClient().writeObject(user);
            data.getClient().writeObject(idUser);
            data.getClient().writeObject(idPerson);


            buttReg.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (data.getEditUser() == null) {
                fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
                data.setUser(user);
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
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.getClient().writeInt(2);
        data.getClient().writeObject("WorkPlace");

        ArrayList<WorkPlace> list = (ArrayList<WorkPlace>) data.getClient().getObject();
        ObservableList<String> langs = FXCollections.observableArrayList();
        for (WorkPlace obj:
             list) {
            langs.add(obj.getName());
        }
        workPlace.setItems(langs);
        workPlace.setValue("Место работы");
    }

    private boolean isFilled() throws IOException, ClassNotFoundException {
        boolean answer = true;
        if (surname.getText().equals("") || !surname.getText().matches(TEXT_REGEX)) {
            labelForSurname.setText("Заполните поле");
            answer = false;
        } else {
            labelForSurname.setText("");
        }
        if (name.getText().equals("") || !name.getText().matches(ENG_TEXT)) {
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
            if (user.getIdUser() > 0) {
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
        if (workPlace.getValue().equals("Место работы")) {
            labelForWork.setText("Не выбрано место работы");
            answer = false;
        } else {
            labelForWork.setText("");
        }
        return answer;
    }
}
