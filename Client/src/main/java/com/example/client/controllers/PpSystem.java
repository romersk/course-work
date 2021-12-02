package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.History;
import model.Salary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PpSystem {
    public Button buttBack;
    public TextField fieldOne;
    public TextField fieldTwo;
    public TextField result;
    public Button buttToSave;
    public Label labelTwo;
    public Label labelOne;
    public TextField fieldThree;
    public Label labelThree;

    private double fieldDOne = 0, fieldDTwo = 0, fieldDThree = 0;

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Data data = Data.getInstance();
        fxmlLoader.setLocation(getClass().getResource("/com/example/client/user-page.fxml"));
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

    public void toCalculate(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        result.setText("");
        boolean isFull = true;
        if (fieldOne.getText().equals("")) {
            isFull = false;
            labelOne.setText("Введите число");
        } else {
            labelOne.setText("");
        }
        if (fieldTwo.getText().equals("")) {
            isFull = false;
            labelTwo.setText("Введите число");
        } else {
            labelTwo.setText("");
        }
        if (fieldThree.getText().equals("")) {
            isFull = false;
            labelThree.setText("Введите число");
        } else {
            labelThree.setText("");
        }

        try {
            fieldDOne = Double.parseDouble(fieldOne.getText());
            labelOne.setText("");
        } catch (NumberFormatException nfe) {
            labelOne.setText("Введите число");
            isFull = false;
        }

        try {
            fieldDTwo = Integer.parseInt(fieldTwo.getText());
            labelTwo.setText("");
        } catch (NumberFormatException nfe) {
            labelTwo.setText("Введите целое число");
            isFull = false;
        }

        try {
            fieldDThree = Double.parseDouble(fieldThree.getText());
            labelThree.setText("");
        } catch (NumberFormatException nfe) {
            labelThree.setText("Введите целое число");
            isFull = false;
        }

        if (!isFull)
            return;
        labelTwo.setText("");
        labelOne.setText("");
        labelThree.setText("");

        double size = (double) Math.round((double) fieldDOne/fieldDThree * fieldDTwo * 100) / 100;
        Salary salary = new Salary();
        salary.setMethod("Косвенно-сдельная");
        salary.setSize(size);

        Data data = Data.getInstance();
        data.getClient().writeInt(5);
        data.getClient().writeObject("Salary");
        data.getClient().writeObject(salary);
        int idSalary = (int) data.getClient().getObject();
        salary.setIdSalary(idSalary);

        History history = new History();
        history.setIdUser(data.getUser().getIdUser());
        history.setIdSalary(idSalary);

        data.getClient().writeInt(5);
        data.getClient().writeObject("History");
        data.getClient().writeObject(history);
        int idHistory = (int) data.getClient().getObject();

        result.setText(String.valueOf(size));
    }

    public void toSaveFile(ActionEvent actionEvent) {
        if (!result.getText().equals("")) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configuringDirectoryChooser(directoryChooser);
            File dir = directoryChooser.showDialog(buttToSave.getScene().getWindow());
            if (dir != null) {
                Data data = Data.getInstance();
                String way = dir.getAbsolutePath() + "/" + data.getUser().getLogin() + "-IP.txt";
                try(FileWriter writer = new FileWriter(way, false))
                {
                    writer.write("Часовая тарифная ставка вспомогательная рабочего, руб равна " + String.valueOf(fieldDOne) + "\n");
                    writer.write("Объем произведенной продукции, ед. равен " + String.valueOf(fieldDTwo) + "\n");
                    writer.write("Часовая норма выработки основных рабочих равна " + fieldThree.getText() + "\n");
                    writer.write("Фактический заработок равен " + result.getText());
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Выберите путь расположения файла");

        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
