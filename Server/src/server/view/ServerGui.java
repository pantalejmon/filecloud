/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import static server.MainServer.primaryStage;
import server.view.center.CenterPanel;
import server.view.console.Console;
import server.view.top.ServerMenuBar;
import server.view.bottom.StatusBar;

/**
 *
 * @author janek
 */
public class ServerGui extends VBox
{
    public ServerMenuBar menu = new ServerMenuBar();
    public StatusBar status = new StatusBar();
    public CenterPanel center = new CenterPanel();
    public Separator s1 = new Separator(Orientation.HORIZONTAL);
    public Separator s2 = new Separator(Orientation.HORIZONTAL);
    public ServerGui()
    {
        this.getChildren().add(menu);
       this.getChildren().add(center);
       this.getChildren().add(s1);
       this.getChildren().add(status);
       this.getChildren().add(s2);
       
       this.setVgrow(center, Priority.ALWAYS);
       //this.setVgrow(status, Priority.ALWAYS);
       this.prefWidthProperty().bind(primaryStage.widthProperty());
       this.prefHeightProperty().bind(primaryStage.heightProperty());
       //center.prefHeightProperty().bind(this.heightProperty());
       status.prefHeightProperty().bind(this.heightProperty());
      // console.prefWidthProperty().bind(this.widthProperty());
        
    }
    
}
