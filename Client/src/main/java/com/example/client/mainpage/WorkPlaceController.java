package com.example.client.mainpage;

import com.example.client.Data;
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
import model.WorkPlace;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WorkPlaceController {
    public TableView<WorkPlace> tableWorkPlace;
    public TableColumn<WorkPlace,String> columnName;
    public TableColumn<WorkPlace,String> columnAddress;
    public Button buttBack;
    public Label labelName;
    public Button buttAdd;
    public Button buttEdit;
    public Button buttDelete;
    public Button buttToFile;
    public TextField name;
    public TextField address;
    public Label labelAddress;

    private ArrayList<WorkPlace> list;
    private DirectoryChooser directoryChooser;
    private int nowId = 0;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        refreshTable();
        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);

        TableView.TableViewSelectionModel<WorkPlace> selectionModel = tableWorkPlace.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<WorkPlace>() {

            @Override
            public void changed(ObservableValue<? extends WorkPlace> observableValue, WorkPlace shop, WorkPlace t1) {
                if (t1 != null) {
                    nowId = t1.getIdWorkPlace();
                    name.setText(t1.getName());
                    address.setText(t1.getAddress());
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

    public void toAdd(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String nameStr = name.getText();
        String addressString = address.getText();
        Data data = Data.getInstance();

        int countRequired = 0;
        if (nameStr.equals("")) {
            labelName.setText("Необходимо заполнить");
            countRequired++;
        } else {
            data.getClient().writeInt(4);
            data.getClient().writeObject(nameStr);
            WorkPlace workPlace = (WorkPlace) data.getClient().getObject();
            if (workPlace.getIdWorkPlace() > 0) {
                labelName.setText("Такая компания существует");
                countRequired++;
            }
        }
        if (addressString.equals("")) {
            labelAddress.setText("Необходимо заполнить");
            countRequired++;
        }
        if (countRequired > 0) {
            return;
        }

        labelName.setText("");
        labelAddress.setText("");

        WorkPlace workPlace = new WorkPlace();
        workPlace.setAddress(addressString);
        workPlace.setName(nameStr);


        data.getClient().writeInt(5);
        data.getClient().writeObject("WorkPlace");
        data.getClient().writeObject(workPlace);
        int idWorkPlace = (int) data.getClient().getObject();
        workPlace.setIdWorkPlace(idWorkPlace);

        refreshTable();
    }

    public void toEdit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {

            String nameStr = name.getText();
            String addressString = address.getText();
            WorkPlace workPlace = new WorkPlace();
            workPlace.setIdWorkPlace(nowId);
            workPlace.setName(nameStr);
            workPlace.setAddress(addressString);

            Data data = Data.getInstance();
            data.getClient().writeInt(7);
            data.getClient().writeObject("WorkPlace");
            data.getClient().writeObject(workPlace);
            data.getClient().writeObject(workPlace.getIdWorkPlace());

            refreshTable();
        }
    }

    public void toDelete(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (nowId != 0) {
            Data data = Data.getInstance();
            data.getClient().writeInt(6);
            data.getClient().writeObject("WorkPlace");
            data.getClient().writeObject(nowId);

            refreshTable();
        }
    }

    public void toSaveToFile(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(buttToFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/workPlaces.txt";
            try(FileWriter writer = new FileWriter(way, false))
            {
                for (WorkPlace obj:
                     list) {
                    writer.write(obj.toString());
                    writer.append('\n');
                }
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        } else {
            name.setText(null);
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
        data.getClient().writeInt(2);
        list = (ArrayList<WorkPlace>) data.getClient().getObject();
        ObservableList<WorkPlace> listOne = FXCollections.observableArrayList(list);

        columnName.setCellValueFactory(new PropertyValueFactory<WorkPlace, String>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<WorkPlace, String>("address"));

        tableWorkPlace.setItems(listOne);
        name.clear();
        address.clear();
        nowId = 0;
    }
}
