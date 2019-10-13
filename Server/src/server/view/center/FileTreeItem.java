/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.center;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class FileTreeItem extends TreeItem<String>
{

    private final File file;
    private boolean wasOpen;

    public FileTreeItem(String name, File file)
    {
        super(name);
        this.file = file;
        this.wasOpen = false;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, ((t) ->
        {
            try
            {
                Desktop.getDesktop().open(this.getFile());
            }
            catch (IOException ex)
            {
                Logger.getLogger(FileTreeItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));
        
        
        
        
    }

    // GETTERS & SETTERS
    public File getFile()
    {
        return file;
    }

    public void setWasOpen(boolean wasOpen)
    {
        this.wasOpen = wasOpen;
    }

    public boolean getWasOpen()
    {
        return wasOpen;
    }

}
