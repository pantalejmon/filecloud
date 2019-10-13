/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.center.UserWindow;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author janek
 */
public class ActiveUserWindow extends VBox
{
    
    public Label l1 = new Label("Logged");
    public ListView<String> logged = new ListView<String>();
    

    public ActiveUserWindow()
    {
        //logged.getItems().add("Janek");
        //all.getItems().add("Janek");
        //all.getItems().add("Jacek");
        this.setVgrow(logged, Priority.ALWAYS);
        this.getChildren().addAll(l1, logged);
    }
}
