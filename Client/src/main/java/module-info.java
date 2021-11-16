module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
            
                        
    opens com.example.client to javafx.fxml;
    exports com.example.client;
    exports com.example.client.mainpage;
    opens com.example.client.mainpage to javafx.fxml;
}