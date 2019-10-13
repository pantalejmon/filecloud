/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.top;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import static server.MainServer.primaryStage;

/**
 *
 * @author janek
 */
public class ServerMenuBar extends MenuBar
{
    public Menu File = new Menu("File");
    public Menu Edit = new Menu("Edit");
    public Menu Connection = new Menu("Connection");
    public Menu Memory = new Menu("Memory");
    public Menu Help = new Menu("Help");
    
    public MenuItem adduser = new MenuItem("Add User");
    public MenuItem remuser = new MenuItem("Remove User");
    public MenuItem changeroot = new MenuItem("Change root folder");
    public MenuItem help = new MenuItem("Help");
    public MenuItem close = new MenuItem("Exit");
           

    public ServerMenuBar()
    {
        super();
        this.prefWidthProperty().bind(primaryStage.widthProperty());
        File.getItems().addAll(changeroot, close);
        Edit.getItems().addAll(adduser, remuser);
        
        
        
        
        this.getMenus().addAll(File, Edit, Connection, Memory, Help);
    }
    
    
}
