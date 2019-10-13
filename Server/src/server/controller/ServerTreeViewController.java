/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.model.files.FileTreeItem;
import java.io.File;
import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class ServerTreeViewController extends TreeView<String> implements Serializable {

    static final long serialVersionUID = 8L;

    private String currentPath;

    public ServerTreeViewController(FileTreeItem rootItem) {
        super(rootItem);
        this.currentPath = rootItem.getFile().getPath();

        this.showDirectoryContent();

        this.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                (MouseEvent event) -> {

                    handleMouseClickedOnItem(event);
                });

        this.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);

    }

    public final void handleMouseClickedOnItem(MouseEvent event) {

        // handler when clicked on FileTreeItem
        Node node = event.getPickResult().getIntersectedNode();
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            FileTreeItem selectedItem = (FileTreeItem) this.getSelectionModel().getSelectedItem();
            if (selectedItem.getFile() == null) {
                System.out.println("File is null");
            } else if (!(selectedItem.getFile().isDirectory())) {
                System.out.println("TreeViewController -> \n\thandleMouseClickedOnItem(): File is not a directory");
            } else {
                System.out.println(selectedItem.getFile().getName());

                this.currentPath = selectedItem.getFile().getPath();
                this.showDirectoryContent();
            }
        }
    }

    public final void showDirectoryContent() {

        try {
            System.out.println("TreeViewController -> \n\tshowDirectoryContent() for root: " + this.getRoot().getValue());

            this.setRoot(new FileTreeItem(currentPath));

            for (File childFile : ((FileTreeItem) this.getRoot()).getFile().listFiles()) {

                FileTreeItem childItem = new FileTreeItem(childFile.getPath(), childFile.getName());

                if (childFile.isDirectory()) {
                    childItem.setValue("[FOLDER] " + childItem.getValue());
                }

                ((FileTreeItem) this.getRoot()).getChildren().add(childItem);

            }

            ((FileTreeItem) this.getRoot()).setExpanded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ** GETTERS AND SETTERS ***
     */
    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

}
