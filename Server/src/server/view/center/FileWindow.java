/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.center;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import static server.MainServer.primaryStage;

/**
 *
 * @author janek
 */
public class FileWindow extends VBox      
{
    public Label l1 = new Label("Files");
    public FileTree filetree = new FileTree();
   // public Label l2 = new Label("Administration");
   // public TabPane administration = new TabPane();

    public FileWindow()
    {
        //this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.setVgrow(filetree, Priority.ALWAYS);
        this.getChildren().addAll(l1, filetree);
    }
    
    
}
