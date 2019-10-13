/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model.threads;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import client.MainClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author janek
 */
public class UploadThread extends Thread {

    private MainClient mainApp;
    private File fileToSend;
    private FileInputStream in;
    private byte[] myData;
    private int myLen;

    @Override
    public void run() {

        try {
            if (this.mainApp.getConnectionController().getRemoteCloudController().
                    fileExist(this.mainApp.controller.userLogin.getText(), this.mainApp.controller.archiveTreeView.getRoot().getValue() + fileToSend.getName(), fileToSend) == true) {
                System.out.println("File is up to date");
            } else {
                in = new FileInputStream(fileToSend);
                System.out.println("File size: " + fileToSend.length());
                myData = new byte[(int) fileToSend.length()];
                myLen = in.read(myData);
                while (myLen > 0) {

                    this.mainApp.getConnectionController().getRemoteCloudController().
                            uploadFile(this.mainApp.controller.archiveTreeView.getRoot().getValue() + fileToSend.getName(), this.mainApp.controller.userLogin.getText(), myData, myLen, fileToSend);
                    myLen = in.read(myData);
                }

                // refresh window
                System.out.println("Przeslano plik");
                Platform.runLater(()
                        -> {
                    this.mainApp.controller.refreshTrees();
                });
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(UploadThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UploadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UploadThread(File fileToSend, MainClient mainApp) {
        this.mainApp = mainApp;
        this.fileToSend = fileToSend;
        this.start();
    }

}
