/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.center;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import static server.MainServer.primaryStage;
import server.view.center.UserWindow.ActiveUserWindow;
import server.view.center.UserWindow.UserWindow;

/**
 *
 * @author janek
 */
public class CenterPanel extends HBox
{
    
    public UserWindow users = new UserWindow();
    public Separator sep1 = new Separator(Orientation.VERTICAL);
     public Separator sep2 = new Separator(Orientation.VERTICAL);
    public ActiveUserWindow logged = new ActiveUserWindow();
    public FileWindow files = new FileWindow();

    public CenterPanel()
    {
        this.setHgrow(files, Priority.ALWAYS);
        this.setHgrow(users, Priority.ALWAYS);
        this.setHgrow(logged, Priority.ALWAYS);
       
        
        this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.getChildren().add(users);
        this.getChildren().add(sep1);
        this.getChildren().add(logged);
        this.getChildren().add(sep2);

        this.getChildren().add(files);
        
        
       //this.prefHeightProperty().bind(primaryStage.heightProperty());
    }

    
    
    
}
