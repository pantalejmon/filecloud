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
public class UserWindow extends VBox
{
    
    
    public Label l2 = new Label("All");
    public ListView<String> all = new ListView<String>();

    public UserWindow()
    {
        //logged.getItems().add("Janek");
        //all.getItems().add("Janek");
        //all.getItems().add("Jacek");
        this.setVgrow(all, Priority.ALWAYS);
        this.getChildren().addAll( l2 , all);
    }
    
    
}
