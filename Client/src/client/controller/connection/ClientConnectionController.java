/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller.connection;

import remote.RemoteInterface;
import java.rmi.Naming;
import java.rmi.Remote;

/**
 *
 * @author janek
 */
public class ClientConnectionController implements Remote {

    private RemoteInterface RemoteCloudController = null;
    private Remote remote;

    public ClientConnectionController(String serverIP) {
        
        try {
            remote = Naming.lookup("rmi://" + serverIP + "/abc"); // Szukanie nazwy

            if (remote instanceof RemoteInterface) {
                System.out.println("Client: podlaczono do serwera");

                RemoteCloudController = (RemoteInterface) remote; // podpiecie serwera

            } else {
                System.out.println("Client: Nie podlaczono do serwera"); // Debug
            }
            RemoteCloudController.Echo();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public RemoteInterface getRemoteCloudController() {
        return RemoteCloudController;
    }

    public void setRemoteCloudController(RemoteInterface RemoteCloudController) {
        this.RemoteCloudController = RemoteCloudController;
    }

    public Remote getRemote() {
        return remote;
    }

    public void setRemote(Remote remote) {
        this.remote = remote;
    }
    
    
}
