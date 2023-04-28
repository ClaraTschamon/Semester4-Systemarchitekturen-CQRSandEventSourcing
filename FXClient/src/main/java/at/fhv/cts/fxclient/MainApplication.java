package at.fhv.cts.fxclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class MainApplication extends Application {
    static Stage stage;
    Scene scene;
    Properties configProperties;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        loadMainView();
    }

    private void loadMainView() throws IOException {

        double width = Double.parseDouble(getConfigProperties().getProperty("window.width"));
        double height = Double.parseDouble(getConfigProperties().getProperty("window.height"));


        URL fxmlLocation = getClass().getResource("/main-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hotel Booking System");
        stage.setScene(scene);
        stage.show();
    }

    private Properties getConfigProperties() throws IOException {
        if (configProperties == null) {
            InputStream propertiesInput = MainApplication.class.getResourceAsStream("/config.properties");
            configProperties = new Properties();
            configProperties.load(propertiesInput);
        }
        return configProperties;
    }

    public static void main(String[] args) {
        launch();
    }
}
