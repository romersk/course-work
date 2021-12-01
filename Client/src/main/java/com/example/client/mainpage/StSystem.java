package com.example.client.mainpage;

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

public class StSystem {
    public Button buttBack;
    public TextField fieldOne;
    public TextField fieldTwo;
    public TextField result;
    public Button buttToSave;
    public Label labelTwo;
    public Label labelOne;

    private double size;
    private double fieldDOne = 0, fieldDTwo = 0;
    private DirectoryChooser directoryChooser;

    public void toBack(ActionEvent actionEvent) {
        buttBack.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
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

        try {
            fieldDOne = Double.parseDouble(fieldOne.getText());
            labelOne.setText("");
        } catch (NumberFormatException nfe) {
            labelOne.setText("Введите число");
            isFull = false;
        }

        try {
            fieldDTwo = Double.parseDouble(fieldTwo.getText());
            labelTwo.setText("");
        } catch (NumberFormatException nfe) {
            labelTwo.setText("Введите число");
            isFull = false;
        }

        if (!isFull)
            return;
        labelTwo.setText("");
        labelOne.setText("");

        size = (double) Math.round(fieldDOne * fieldDTwo * 100) / 100;
        Salary salary = new Salary();
        salary.setMethod("Простая повременная");
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
            directoryChooser = new DirectoryChooser();
            configuringDirectoryChooser(directoryChooser);
            File dir = directoryChooser.showDialog(buttToSave.getScene().getWindow());
            if (dir != null) {
                Data data = Data.getInstance();
                String way = dir.getAbsolutePath() + "/" + data.getUser().getLogin() + "-ST.txt";
                try(FileWriter writer = new FileWriter(way, false))
                {
                    writer.write("Часовая тарифная ставка, руб. равна " + String.valueOf(fieldDOne) + "\n");
                    writer.write("Количество часов, отработанных работником равно " + String.valueOf(fieldDTwo) + "\n");
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
