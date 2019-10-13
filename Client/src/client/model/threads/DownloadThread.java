/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model.threads;

import client.MainClient;
import client.view.ClientOverview.ClientOverviewController;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author janek
 */
public class DownloadThread extends Thread
{

    private Path f;
    private FileOutputStream out;
    private MainClient mainApp;

    public DownloadThread(Path f, MainClient mainApp)
    {
        this.f = f;
        this.mainApp = mainApp;
        this.start();
    }

    @Override
    public void run()
    {

        try
        {
            out = new FileOutputStream(f.toFile(), false);
            out.write(this.mainApp.getConnectionController().getRemoteCloudController().
                    downloadFile(
                            this.mainApp.controller.userLogin.getText(),
                            this.mainApp.controller.archiveTreeView.getRoot().getValue() + "\\" + this.mainApp.controller.archiveTreeView.getSelectionModel().getSelectedItem().getValue()));

            out.flush();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(ClientOverviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
