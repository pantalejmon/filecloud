/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.bottom;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import static server.MainServer.primaryStage;

/**
 *
 * @author janek
 */
public class StatusBar extends HBox
{
   public Label ipinfo = new Label("Nr IP");
   public Separator s1 = new Separator(Orientation.VERTICAL);
   public Label ip = new Label("localhost");
   public Separator s2 = new Separator(Orientation.VERTICAL);

    public StatusBar()
    {
        //this.maxHeight(50);
        this.setMaxHeight(60);
        //this.prefHeightProperty().bind(primaryStage.heightProperty());
        this.getChildren().addAll(ipinfo,s1,ip,s2);
    }
   
   
}
