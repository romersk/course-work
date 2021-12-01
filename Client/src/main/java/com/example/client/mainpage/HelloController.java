package com.example.client.mainpage;

import com.example.client.Client;
import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class HelloController {
    @FXML
    public Button enterButton;
    @FXML
    public Button registrationButton;
    @FXML
    public PasswordField password;
    @FXML
    public TextField login;
    @FXML
    public Label toHelp;
    @FXML
    public Label isAccountExist;

    @FXML
    public void toEnter(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (login.getText().equals("") || password.getText().equals("")) {
            toHelp.setText("Введены не все данные");
        }

        Data data = Data.getInstance();
        data.getClient().writeInt(1);
        data.getClient().writeLine(login.getText());
        data.getClient().writeLine(password.getText());
        User user = (User) data.getClient().getObject();

        if (user.getIdUser() == 0) {
            isAccountExist.setText("Такого пользователя не существует");
        } else {
            data.setUser(user);
            enterButton.getScene().getWindow().hide();
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

        toHelp.setText("");
    }

    @FXML
    public void toRegistration(ActionEvent actionEvent) {
        registrationButton.getScene().getWindow().hide();
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

    @FXML
    public void initialize() throws UnknownHostException {
        InetAddress address = InetAddress.getByName(null);
        Data data = Data.getInstance();
        if (data.getClient() == null) {
            Client client = new Client(address, 8020);
            data.setClient(client);
            System.out.println("Connected to Server");
        }
    }
}