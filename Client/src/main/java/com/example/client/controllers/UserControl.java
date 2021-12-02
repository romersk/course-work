package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Person;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UserControl {
    public Button buttBack;
    public Button buttAdd;
    public Button buttEdit;
    public Button buttDelete;
    public Button buttToFile;
    public TableView<UserPerson> table;
    public TableColumn<UserPerson, String> columnLogin;
    public TableColumn<UserPerson, String> columnPass;
    public TableColumn<UserPerson, String> columnSurname;
    public TableColumn<UserPerson, String> columnName;
    public TextField searchLogin;
    public Button buttToSearch;
    public Button buttToRefresh;

    private ArrayList<UserPerson> list;
    private DirectoryChooser directoryChooser;
    private int nowId = 0;
    private String nowName = "";
    private String nowSurname = "";
    private String nowLogin = "";
    private String nowPassword = "";
    private int nowIdPerson = 0;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        refreshTable();
        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);

        TableView.TableViewSelectionModel<UserPerson> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<UserPerson>() {

            @Override
            public void changed(ObservableValue<? extends UserPerson> observableValue, UserPerson shop, UserPerson t1) {
                if (t1 != null) {
                    nowId = t1.getId();
                    nowName = t1.getFirstName();
                    nowSurname = t1.getLastName();
                    nowLogin = t1.getLogin();
                    nowPassword = t1.getPassword();
                    nowIdPerson = t1.getIdPerson();
                }
            }
        });
    }

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
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

    public void toAdd(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        data.setEditUser(data.getUser());
        buttAdd.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/registration.fxml"));
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

    public void toEdit(ActionEvent actionEvent) {
        if (nowId != 0) {
            User user = new User();
            user.setIdUser(nowId);
            user.setRole("USER");
            user.setLogin(nowLogin);
            user.setPassword(nowPassword);
            user.setIdPerson(nowIdPerson);
            Data data = Data.getInstance();
            data.setEditUser(user);
            buttEdit.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/personal-info.fxml"));
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

    public void toDelete(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {
            Data data = Data.getInstance();
            data.getClient().writeInt(6);
            data.getClient().writeObject("User");
            data.getClient().writeObject(nowId);

            refreshTable();
        }
    }

    public void toSaveToFile(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(buttToFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/users.txt";
            try (FileWriter writer = new FileWriter(way, false)) {
                for (UserPerson obj :
                        list) {
                    writer.write(obj.toString());
                    writer.append('\n');
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Выберите путь расположения файла");

        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private void refreshTable() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.setEditUser(null);
        data.getClient().writeInt(2);
        data.getClient().writeObject("User");
        ArrayList<User> listOne = (ArrayList<User>) data.getClient().getObject();
        list = new ArrayList<>();
        for (User obj :
                listOne) {
            if (Objects.equals(obj.getRole(), "USER")) {
                UserPerson userPerson = new UserPerson();
                userPerson.setId(obj.getIdUser());
                userPerson.setLogin(obj.getLogin());
                userPerson.setPassword(obj.getPassword());
                userPerson.setIdPerson(obj.getIdPerson());

                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(userPerson.getIdPerson());
                Person person = (Person) data.getClient().getObject();
                userPerson.setFirstName(person.getFirstName());
                userPerson.setLastName(person.getLastName());

                list.add(userPerson);
            }
        }

        ObservableList<UserPerson> arrayList = FXCollections.observableArrayList(list);

        columnLogin.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("login"));
        columnPass.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("password"));
        columnName.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("firstName"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("lastName"));

        table.setItems(arrayList);
        nowLogin = "";
        nowPassword = "";
        nowId = 0;
        nowName = "";
        nowSurname = "";
        nowIdPerson = 0;
        searchLogin.clear();
    }

    public void toSearch(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (!Objects.equals(searchLogin.getText(), "")) {
            Data data = Data.getInstance();
            data.setEditUser(null);
            data.getClient().writeInt(3);
            data.getClient().writeObject(searchLogin.getText());
            User obj = (User) data.getClient().getObject();
            list = new ArrayList<>();
            if (Objects.equals(obj.getRole(), "USER")) {
                UserPerson userPerson = new UserPerson();
                userPerson.setId(obj.getIdUser());
                userPerson.setLogin(obj.getLogin());
                userPerson.setPassword(obj.getPassword());
                userPerson.setIdPerson(obj.getIdPerson());

                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(userPerson.getIdPerson());
                Person person = (Person) data.getClient().getObject();
                userPerson.setFirstName(person.getFirstName());
                userPerson.setLastName(person.getLastName());

                list.add(userPerson);
            }

            ObservableList<UserPerson> arrayList = FXCollections.observableArrayList(list);

            columnLogin.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("login"));
            columnPass.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("password"));
            columnName.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("firstName"));
            columnSurname.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("lastName"));

            table.setItems(arrayList);
            nowLogin = "";
            nowPassword = "";
            nowId = 0;
            nowName = "";
            nowSurname = "";
            nowIdPerson = 0;
        }
    }

    public void toRefresh(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        refreshTable();
    }
}
