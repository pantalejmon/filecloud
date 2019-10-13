/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.controller.ServerController;
import server.view.ServerGui;

/**
 *
 * @author Jan Jakubik & Jacek Steć
 */
public class MainServer extends Application {

    public static ServerGui gui;
    public static ServerController controller;
    public static Stage primaryStage;

    public static String cloudPath;

    @Override
    public void start(Stage stage) throws Exception {
        this.setCloudPath();
        System.setProperty("java.security.policy", "java.policy");
        
        this.primaryStage = stage;
        gui = new ServerGui();
        controller = new ServerController();

        primaryStage.setTitle("File Cloud Server");
        Group root = new Group();
        root.getChildren().add(gui);
        Scene scene = new Scene(root);
        System.out.print("\nUruchomiono program\n");
        primaryStage.setScene(scene);
        stage.sizeToScene();
        primaryStage.show();
       // gui.prefWidthProperty().bind(primaryStage.widthProperty());
       //gui.prefHeightProperty().bind(primaryStage.heightProperty());
        //scene.getStylesheets().add("Grafika.css");
        gui.getStylesheets().add("Grafika.css");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                System.out.println("Stage is closing");
                
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void setCloudPath(){
        File temp = new File(System.getProperty("user.dir"));
        
        this.cloudPath = temp.getParent() + "\\OPA_Server_Cloud\\";
    }
}
