package client.view.ClientOverview;

import client.MainClient;
import client.controller.ClientTreeViewController;
import client.model.FileTreeItem;
import client.model.threads.DownloadThread;
import client.model.threads.UploadThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 *
 *
 * @author Jacek Andrzej Steć
 */
public class ClientOverviewController implements Initializable {

    private MainClient mainApp;

    @FXML
    private StackPane localStackPane;
    @FXML
    private StackPane archiveStackPane;

    @FXML
    private TreeView<String> localTreeView;
    @FXML
    public TreeView<String> archiveTreeView;

    @FXML
    private Text ipnumber;
    @FXML
    private Text status;
    @FXML
    public Text userLogin;
    @FXML
    private TextField newLocalFolderInput;
    @FXML
    private TextField newArchiveFolderInput;

    @FXML
    private Button newLocalFolderButton;
    @FXML
    private Button newArchiveFolderButton;

    /**
     * Initializes the controller class.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("ClientOverviewController -> \n\tinitialize()");
        
    }

    /*
     * BUTTONS 
     */
    @FXML
    private void buttonClick_callback(MouseEvent mouseEvent) {

        // pierdoly
        System.out.println(this.getClass().getEnclosingClass().toString());
        System.out.println(this.getClass().getEnclosingMethod().toString());
    }

    @FXML
    private void close_callback() {
        System.out.println("ClientOverviewController -> \n\tclose_callback()");
        Platform.exit();
    }

    @FXML
    private void logout_callback() throws RemoteException {

        System.out.println("ClientOverviewController -> \n\tlogout_callback()");
        // Clearing ClientOverviewWindow
        this.mainApp.getConnectionController().getRemoteCloudController().logout(userLogin.getText());
        this.mainApp.setLogged(false);
        this.mainApp.setLoggedUser(null);

        this.mainApp.showClientLoginWindow();

    }

    @FXML
    private void backLocal_callback() {

        System.out.println("ClientOverviewController -> \n\tback()");

        String newPath = ((FileTreeItem) this.localTreeView.getRoot()).getFile().getParent();
        System.out.println("new path: " + newPath);
        ((ClientTreeViewController) this.localTreeView).showLocalDirectoryContent(newPath);

    }

    @FXML
    private void backArchive_callback() {

        try {

            Path newPath = Paths.get(this.archiveTreeView.getRoot().getValue()).getParent();
            ((ClientTreeViewController) this.archiveTreeView).showArchiveDirectoryContent(
                    this.mainApp.getConnectionController().getRemoteCloudController().getArchivedFolder(
                            this.userLogin.getText(),
                            newPath.toString(),
                            2));

        } catch (RemoteException ex) {

            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void uploadFiles() throws FileNotFoundException, IOException {
        /*
        File fileToSend = new File(this.localTreeView.getRoot().getValue() + "\\" + this.localTreeView.getSelectionModel().getSelectedItem().getValue());
        FileInputStream in = new FileInputStream(fileToSend);
        System.out.println("File size: " + fileToSend.length());
        byte[] myData = new byte[(int) fileToSend.length()];
        int myLen = in.read(myData);
        while (myLen > 0) {

            this.mainApp.getConnectionController().getRemoteCloudController().
                    uploadFile(this.archiveTreeView.getRoot().getValue() + fileToSend.getName(), this.userLogin.getText(), myData, myLen);
            myLen = in.read(myData);
        }
        */
        new UploadThread(new File(this.localTreeView.getRoot().getValue() + "\\" + this.localTreeView.getSelectionModel().getSelectedItem().getValue()),this.mainApp);
        // refresh window
        System.out.println("Przeslano plik");

    }

    @FXML
    private void downloadFiles() {
        System.out.println("Download button clicked");
       /* FileOutputStream out = null;
        try {
            Path f = Paths.get(
                    this.localTreeView.getRoot().getValue() + "\\"
                    + this.archiveTreeView.getSelectionModel().getSelectedItem().getValue());
            out = new FileOutputStream(f.toFile(), false);
            out.write(this.mainApp.getConnectionController().getRemoteCloudController().
                    downloadFile(
                            this.userLogin.getText(),
                            this.archiveTreeView.getRoot().getValue() + "\\" + this.archiveTreeView.getSelectionModel().getSelectedItem().getValue()));

            out.flush();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
       
       new DownloadThread(Paths.get(
                    this.localTreeView.getRoot().getValue() + "\\"
                    + this.archiveTreeView.getSelectionModel().getSelectedItem().getValue()), this.mainApp);
    }

    @FXML
    private void newFolderLocal_callback() {

        this.newLocalFolderInput.setVisible(true);
        this.newLocalFolderButton.setVisible(false);
        this.newLocalFolderInput.setText("");
        this.newLocalFolderInput.setOnKeyPressed((KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER:
                    String newFolderName = this.newLocalFolderInput.getText();

                    File currentDir = new File(this.localTreeView.getRoot().getValue() + "\\" + newFolderName);

                    System.out.println("Directory to create: " + currentDir.getPath());
                    if (!currentDir.exists()) {
                        if (currentDir.mkdir()) {
                            currentDir.mkdir();
                            ((ClientTreeViewController) localTreeView).showLocalCurrentDirectoryContent();
                            System.out.println("ClientOverviewController -> \n\tNew folder created");
                            this.newLocalFolderInput.setVisible(false);
                            this.newLocalFolderButton.setVisible(true);
                        }
                    } else {
                        System.out.println("ClientOverviewController -> \n\tFolder exists");
                    }
                    break;

                case ESCAPE:
                    this.newLocalFolderInput.setVisible(false);
                    this.newLocalFolderButton.setVisible(true);
                    break;

            }

        });
    }

    @FXML
    private void newFolderArchive_callback() {

        this.newArchiveFolderInput.setVisible(true);
        this.newArchiveFolderButton.setVisible(false);
        this.newArchiveFolderInput.setText("");
        this.newArchiveFolderInput.setOnKeyPressed((KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER:
                    String newFolderName = this.newArchiveFolderInput.getText();
                     {
                        try {
                            if (this.mainApp.getConnectionController().getRemoteCloudController().
                                    newFolder(this.userLogin.getText(), this.archiveTreeView.getRoot().getValue() + "\\" + newFolderName)) {
                                this.newArchiveFolderInput.setVisible(false);
                                this.newArchiveFolderButton.setVisible(true);
                                
                                ((ClientTreeViewController) this.archiveTreeView).showArchiveCurrentDirectoryContent();
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.newArchiveFolderInput.setVisible(false);
                    this.newArchiveFolderButton.setVisible(true);
                    break;

                case ESCAPE:
                    this.newArchiveFolderInput.setVisible(false);
                    this.newArchiveFolderButton.setVisible(true);
                    break;

            }

        });

    }

    @FXML
    private void deleteFolderLocal_callback() {
        if (!this.localTreeView.getSelectionModel().isEmpty()) {

            String fileToDelete = this.localTreeView.getSelectionModel().getSelectedItem().getValue();

            File file = new File(this.localTreeView.getRoot().getValue() + "\\" + fileToDelete);

            if (file.exists()) {
                file.delete();
                System.out.println("Deleted local file: " + fileToDelete);
            }

            this.refreshTrees();

        } else {
            System.out.println("Delete local file: no files selected");
        }
    }

    @FXML
    private void deleteFolderArchive_callback() {
        if (!this.archiveTreeView.getSelectionModel().isEmpty()) {

            String fileToDelete = this.archiveTreeView.getSelectionModel().getSelectedItem().getValue();

            String filePathToDelete = this.archiveTreeView.getRoot().getValue() + "\\" + fileToDelete;
            
            try {
                if(this.mainApp.getConnectionController().getRemoteCloudController().
                        deleteFile(this.userLogin.getText(), filePathToDelete));
            } catch (RemoteException ex) {
                Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.refreshTrees();

        } else {
            System.out.println("Delete local file: no files selected");
        }
    }

    @FXML
    public void refreshTrees() {
        ((ClientTreeViewController) this.localTreeView).showLocalCurrentDirectoryContent();

        ((ClientTreeViewController) this.archiveTreeView).showArchiveCurrentDirectoryContent();
    }

    /**
     * ** END OF BUTTONS***
     */
    /* 
     * GETTERS AND SETTERS
     * OTHER METHODS
     */
    public StackPane getLocalStackPane() {
        return localStackPane;
    }

    public StackPane getArchiveStackPane() {
        return archiveStackPane;
    }

    public Text getUserLogin() {
        return userLogin;
    }

    public void setMainApp(MainClient mainApp) throws RemoteException {
        this.mainApp = mainApp;
    }

    public void setBasics() {
        try {
            // SETTING LOCAL TREE VIEW
            this.localTreeView = new ClientTreeViewController(new FileTreeItem(System.getProperty("user.dir")));
            this.localStackPane.getChildren().add(this.localTreeView);

            // SETTING ARCHIVE TREE VIEW
            // false - flag to get startup tree view items
            // true - flag to get other tree view items
            this.archiveTreeView = (TreeView<String>) new ClientTreeViewController(
                    this.mainApp.getConnectionController().
                            getRemoteCloudController().getArchivedFolder(this.userLogin.getText(), null, 0));

            this.status.setText("Online");
            ((ClientTreeViewController) this.archiveTreeView).setUserLogin(this.userLogin.getText());

            this.archiveStackPane.getChildren().add(this.archiveTreeView);

        } catch (RemoteException ex) {
            Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIP(String ip) {
        ipnumber.setText(ip);
    }

    /**
     * ** END OF METHODS ***
     */
}
