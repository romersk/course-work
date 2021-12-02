package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.HistoryType;
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
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HistoryTable {
    public Button buttBack;
    public TableView<HistoryType> tableHistory;
    public TableColumn<HistoryType, String> columnName;
    public TableColumn<HistoryType, String> columnSurname;
    public TableColumn<HistoryType, String> columnMethod;
    public TableColumn<HistoryType, Double> columnSize;
    public Button buttDel;
    public Button buttGraph;
    public ComboBox<String> allMethods;
    public Button buttFilter;
    public TextField sizeField;
    public Label label;

    private int nowId = 0;
    private ArrayList<HistoryType> list;
    private ArrayList<HistoryType> filterList = new ArrayList<>();

    public void toDelete(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {
            Data data = Data.getInstance();
            data.getClient().writeInt(6);
            data.getClient().writeObject("Salary");
            data.getClient().writeObject(nowId);

            refreshTable();
        }
    }

    public void toGraph(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        data.setDataForChart(list);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/graph.fxml"));
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

    public void toBack(ActionEvent actionEvent) {
        Data data = Data.getInstance();
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (Objects.equals(data.getUser().getRole(), "Admin")) {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/admin-page.fxml"));
        } else {
            fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
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

    public void toFilter(ActionEvent actionEvent) {
        if (sizeField.getText().equals("")){
            if (allMethods.getValue().equals("Все")) {
                filterList.clear();
                filterList = list;
            } else {
                filterList.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getMethod().equals(allMethods.getValue())) {
                        filterList.add(list.get(i));
                    }
                }
            }
        } else {
            filterList.clear();
            double size = 0;
            try {
                size = Integer.parseInt(sizeField.getText());
                label.setText("");
            } catch (NumberFormatException nfe) {
                label.setText("Введите целое число");
                return;
            }

            if (allMethods.getValue().equals("Все")) {
                for (HistoryType obj:
                     list) {
                    if (obj.getSize() >= size) {
                        filterList.add(obj);
                    }
                }
            } else {
                filterList.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getMethod().equals(allMethods.getValue()) && list.get(i).getSize() >= size) {
                        filterList.add(list.get(i));
                    }
                }
            }
            label.setText("");
        }
        ObservableList<HistoryType> arrayList = FXCollections.observableArrayList(filterList);

        tableHistory.setItems(arrayList);
        nowId = 0;
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        columnMethod.setCellValueFactory(new PropertyValueFactory<HistoryType, String>("method"));
        columnName.setCellValueFactory(new PropertyValueFactory<HistoryType, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<HistoryType, String>("surname"));
        columnSize.setCellValueFactory(new PropertyValueFactory<HistoryType, Double>("size"));
        refreshTable();

        ObservableList<String> langs = FXCollections.observableArrayList("Все", "Прямая сдельная",
                "Косвенно-сдельная", "Сдельно-премиальная", "Простая повременная", "Повременно-премиальная");
        allMethods.setItems(langs);
        allMethods.setValue("Все");

        TableView.TableViewSelectionModel<HistoryType> selectionModel = tableHistory.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<HistoryType>() {

            @Override
            public void changed(ObservableValue<? extends HistoryType> observableValue, HistoryType shop, HistoryType t1) {
                if (t1 != null) {
                    nowId = t1.getIdSalary();
                }
            }
        });
    }

    private void refreshTable() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        data.getClient().writeInt(2);
        data.getClient().writeObject("History");
        ArrayList<History> listOne = (ArrayList<History>) data.getClient().getObject();
        list = new ArrayList<>();
        for (History obj :
                listOne) {
            HistoryType historyType = new HistoryType();
            if (Objects.equals(data.getUser().getRole(), "USER") && obj.getIdUser() == data.getUser().getIdUser()) {
                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(data.getUser().getIdPerson());
                Person person = (Person) data.getClient().getObject();

                historyType.setName(person.getFirstName());
                historyType.setSurname(person.getLastName());
            } else {

                data.getClient().writeInt(8);
                data.getClient().writeObject("User");
                data.getClient().writeObject(obj.getIdUser());
                User user = (User) data.getClient().getObject();

                data.getClient().writeInt(8);
                data.getClient().writeObject("Person");
                data.getClient().writeObject(user.getIdPerson());
                Person person = (Person) data.getClient().getObject();

                historyType.setName(person.getFirstName());
                historyType.setSurname(person.getLastName());
            }

            data.getClient().writeInt(8);
            data.getClient().writeObject("Salary");
            data.getClient().writeObject(obj.getIdSalary());
            Salary salary = (Salary) data.getClient().getObject();

            historyType.setIdSalary(obj.getIdSalary());
            historyType.setMethod(salary.getMethod());
            historyType.setSize(salary.getSize());

            list.add(historyType);
        }

        ObservableList<HistoryType> arrayList = FXCollections.observableArrayList(list);

        tableHistory.setItems(arrayList);
        nowId = 0;
    }
}
