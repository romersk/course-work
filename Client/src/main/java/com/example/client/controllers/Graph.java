package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.HistoryType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class Graph {
    public PieChart dataChart;
    public Label caption;

    @FXML
    public void initialize() {
        Data data = Data.getInstance();

        String methodOne = "Прямая сдельная";
        String methodTwo = "Косвенно-сдельная";
        String methodThree = "Сдельно-премиальная";
        String methodFour = "Простая повременная";
        String methodFive = "Повременно-премиальная";
        int countOne = 0, countTwo = 0, countThree = 0, countFour = 0, countFive = 0;

        for (HistoryType obj :
                data.getDataForChart()) {
            switch (obj.getMethod()) {
                case "Прямая сдельная": {
                    countOne++;
                    break;
                }
                case "Косвенно-сдельная": {
                    countTwo++;
                    break;
                }
                case "Сдельно-премиальная": {
                    countThree++;
                    break;
                }
                case "Простая повременная": {
                    countFour++;
                    break;
                }
                case "Повременно-премиальная": {
                    countFive++;
                    break;
                }
            }
        }

        PieChart.Data slice1 = new PieChart.Data(methodOne, countOne);
        PieChart.Data slice2 = new PieChart.Data(methodTwo, countTwo);
        PieChart.Data slice3 = new PieChart.Data(methodThree, countThree);
        PieChart.Data slice4 = new PieChart.Data(methodFour, countFour);
        PieChart.Data slice5 = new PieChart.Data(methodFive, countFive);

        dataChart.getData().add(slice1);
        dataChart.getData().add(slice2);
        dataChart.getData().add(slice3);
        dataChart.getData().add(slice4);
        dataChart.getData().add(slice5);

        dataChart.setLegendSide(Side.LEFT);
        dataChart.setLabelsVisible(true);
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 20 arial;");

        for (final PieChart.Data dataS : dataChart.getData()) {
            dataS.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());

                    caption.setText(String.valueOf(dataS.getPieValue()));
                    caption.setVisible(true);
                }
            });
            dataS.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setVisible(false);
                        }
                    });
        }
    }
}
