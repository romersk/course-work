package com.example.client.mainpage;

import com.example.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloController {
    public Button enterButton;
    public Button registrationButton;
    public PasswordField password;
    public TextField login;
    public Label toHelp;

    private Client client;
    public void toEnter(ActionEvent actionEvent) {
        if (login.getText().equals("") || password.getText().equals("")) {
            toHelp.setText("Введены не все данные");
        }

        toHelp.setText("");
    }

    public void toRegistration(ActionEvent actionEvent) {
    }

    @FXML
    public void initialize() throws UnknownHostException {
        InetAddress address = InetAddress.getByName(null);
        client = new Client(address, 8020);
        System.out.println("Connected to Server");
    }
}