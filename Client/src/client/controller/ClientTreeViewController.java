/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.MainClient;
import client.model.FileTreeItem;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class ClientTreeViewController extends TreeView<String> implements Serializable {

    static final long serialVersionUID = 8L;

    private String userLogin;

    public ClientTreeViewController(FileTreeItem rootItem) {
        super(rootItem);

        /*try {
            this.rootIcon = new ImageView(new Image(new FileInputStream("attachments\\open.ico")));
            this.folderIcon = new ImageView (new Image(new FileInputStream("attachments\\close.ico")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        this.showLocalDirectoryContent(rootItem.getValue());

        this.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                (MouseEvent event) -> {

                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            try {
                                mouseClickHandler_Local(event);
                            } catch (IOException ex) {
                                Logger.getLogger(ClientTreeViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

        this.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);

    }

    public ClientTreeViewController(List<String> importedRootFromServer) {

        super(new TreeItem<String>(importedRootFromServer.get(0)));

        this.showArchiveDirectoryContent(importedRootFromServer);

        this.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                (MouseEvent event) -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            mouseClickHandler_Archive(event);
                        }
                    }

                });

        this.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);

    }

    public final void mouseClickHandler_Local(MouseEvent event)
            throws IOException {

        // handler when clicked on FileTreeItem
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            FileTreeItem selectedItem = (FileTreeItem) this.getSelectionModel().getSelectedItem();
            if (selectedItem.getFile() == null) {
                System.out.println("File is null");
            } else if (!(selectedItem.getFile().isDirectory())) {
                System.out.println("Client -> \n\t Opening file: " + selectedItem.getFile().getName());
                Desktop.getDesktop().open(selectedItem.getFile());
            } else {
                System.out.println(selectedItem.getFile().getName());

                this.showLocalDirectoryContent(selectedItem.getFile().getPath());
            }
        }
    }

    public final void mouseClickHandler_Archive(MouseEvent event) {

        // handler when clicked on FileTreeItem
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            TreeItem<String> selectedItem = this.getSelectionModel().getSelectedItem();

            if (!selectedItem.equals(this.getRoot())) {
                try {
                    this.showArchiveDirectoryContent(MainClient.ConnectionController.getRemoteCloudController().getArchivedFolder(this.userLogin, getRoot().getValue() + selectedItem.getValue() + "\\", 2));

                } catch (RemoteException ex) {
                    Logger.getLogger(ClientTreeViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public final void showLocalCurrentDirectoryContent() {
        System.out.println("ClientTreeViewController -> \n\tshowLocalDirectoryContent() for: " + this.getRoot().getValue());
        this.setRoot(new FileTreeItem(this.getRoot().getValue()));
        this.getRoot().setGraphic(new ImageView(new Image("open.png")));
        for (File childFile : ((FileTreeItem) this.getRoot()).getFile().listFiles()) {
            
            FileTreeItem childItem = new FileTreeItem(childFile.getPath(), childFile.getName());
            
            if (childFile.isDirectory()) {
                childItem.setGraphic(new ImageView(new Image("close.png")));
            }
            
            ((FileTreeItem) this.getRoot()).getChildren().add(childItem);
            
        }
        ((FileTreeItem) this.getRoot()).setExpanded(true);
    }

    public final void showLocalDirectoryContent(String newPath) {

        try {
            System.out.println("ClientTreeViewController -> \n\tshowLocalDirectoryContent() for: " + newPath);

            this.setRoot(new FileTreeItem(newPath));
            this.getRoot().setGraphic(new ImageView(new Image("open.png")));
            for (File childFile : ((FileTreeItem) this.getRoot()).getFile().listFiles()) {

                FileTreeItem childItem = new FileTreeItem(childFile.getPath(), childFile.getName());

                if (childFile.isDirectory()) {
                    childItem.setGraphic(new ImageView(new Image("close.png")));
                }

                ((FileTreeItem) this.getRoot()).getChildren().add(childItem);

            }

            ((FileTreeItem) this.getRoot()).setExpanded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void showArchiveDirectoryContent(List<String> importedRootFromServer) {

        try {
            System.out.println("ClientTreeViewController -> \n\tshowArchiveDirectoryContent(): " + importedRootFromServer.get(0));

            if (importedRootFromServer == null) {
                System.out.println("ClientTreeViewController -> \n\tshowArchiveDirectoryContent() -> \n\t\timportedRootFromServer: null");
                return;
            }

            this.setRoot(new TreeItem<String>(importedRootFromServer.get(0)));
            this.getRoot().setGraphic(new ImageView(new Image("open.png")));
            this.getRoot().setExpanded(true);

            for (int i = 1; i < importedRootFromServer.size(); i++) {
                TreeItem<String> item = new TreeItem<String>(importedRootFromServer.get(i));

                int index = importedRootFromServer.get(i).lastIndexOf('.');
                if (index == -1) {
                    //String extension = importedRootFromServer.get(i).substring(index + 1);
                    //System.out.println("Extension: <<" + extension + ">>");
                    item.setGraphic(new ImageView(new Image("close.png")));

                }

                this.getRoot().getChildren().add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void showArchiveCurrentDirectoryContent() {
        try {
            this.showArchiveDirectoryContent(MainClient.ConnectionController.getRemoteCloudController().
                    getArchivedFolder(this.userLogin, getRoot().getValue(), 2));

        } catch (RemoteException ex) {
            Logger.getLogger(ClientTreeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    /**
     * ** GETTERS AND SETTERS ***
     */

}
