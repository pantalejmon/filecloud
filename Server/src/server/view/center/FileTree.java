/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.center;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import server.MainServer;
import static server.MainServer.primaryStage;

/**
 *
 * @author janek
 */
public class FileTree extends Pane {

    public File parent;
    public ArrayList<File> UserFiles = new ArrayList<File>();
    public FileTreeItem root;
    public TreeView tree;

    public FileTree() {
        parent = new File("../OPA_Server_Cloud");
        
     

        
        
        
        if (!parent.exists())
        {
            System.out.println("Server: createCloudFolder(): folder created");
            parent.mkdir();
        }
       UserFiles = new ArrayList<File>(Arrays.asList(parent.listFiles()));
        //childrenList = parentFile.listFiles();
        FileTreeItem rootItem = new FileTreeItem(parent.getName(), parent);

        rootItem.setExpanded(true);

        for (File childFile : UserFiles) {
            FileTreeItem item = new FileTreeItem(childFile.getName(), childFile);
            updateItemUserList(item);
            rootItem.getChildren().add(item);
        }
        tree = new TreeView<>(rootItem);
        System.out.println("rootItem: " + rootItem.getFile().getPath());
        //this.prefWidthProperty().bind(primaryStage.widthProperty());
      // this.prefHeightProperty().bind(primaryStage.heightProperty());
        this.getChildren().add(tree);

    }

    private void updateItemUserList(FileTreeItem itemDirectory) {

        if (itemDirectory.getFile().isDirectory()) {
            File[] childrenList = itemDirectory.getFile().listFiles();

            itemDirectory.setExpanded(false);

            for (File childFile : childrenList) {
                FileTreeItem item = new FileTreeItem(childFile.getName(), childFile);

                updateItemUserList(item);

                itemDirectory.getChildren().add(item);
            }
        }
    }

}
