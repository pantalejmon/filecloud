/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.files;

import java.io.File;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class FileTreeItem extends TreeItem<String> {

    private final File file;
    
    public FileTreeItem(String childPath, String childName){
        super(childName);
        this.file = new File(childPath);
    }
    
    public FileTreeItem(String rootPath){
        super(rootPath);
        file = new File(rootPath);
   
    }

    // GETTERS & SETTERS
    public File getFile() {
        return file;
    }
}
