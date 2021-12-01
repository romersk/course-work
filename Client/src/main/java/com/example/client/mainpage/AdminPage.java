package com.example.client.mainpage;

import com.example.client.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPage {
    public Button buttBack;
    public Button buttPersonalInfo;
    public Button buttDataUsers;
    public Button buttWorkPlaces;
    public Button buttHistory;

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Data data = Data.getInstance();
        data.setUser(null);
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/hello-view.fxml"));
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

    public void toShowPersonalInfo(ActionEvent actionEvent) {
        buttPersonalInfo.getScene().getWindow().hide();
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

    public void toShowData(ActionEvent actionEvent) {
    }

    public void toManupWork(ActionEvent actionEvent) {
        buttWorkPlaces.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/work-place.fxml"));
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

    public void toShowHistory(ActionEvent actionEvent) {
    }
}
