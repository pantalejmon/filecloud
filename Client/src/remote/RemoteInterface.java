/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import client.controller.ClientTreeViewController;
import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author STJ8WZ
 */
public interface RemoteInterface extends Remote {

    String getCloudPath()
            throws RemoteException;

    boolean authorisation(String login, String password)
            throws RemoteException,
            ParserConfigurationException,
            IOException,
            SAXException;

    boolean searchUser(String login)
            throws RemoteException;

    void createAccount(String login, String pasword)
            throws RemoteException,
            ParserConfigurationException,
            TransformerException;

    /**
     *
     * @param login
     * @param path
     * @param option
     * 2 - selected folder
     * 1 - previous folder (back)
     * 0 - first window (root folder)
     * 
     * @return
     * @throws RemoteException
     */
    List<String> getArchivedFolder(String login, String path, int option)
            throws RemoteException;

    void Echo() throws RemoteException;
    
    /**
     *
     * @param name
     * @param login
     * @param data
     * @param len
     * @return
     * @throws RemoteException
     */
    boolean uploadFile(String name, String login, byte[] data, int len, File fileToSend) throws RemoteException;
    
    byte[] downloadFile(String login, String path) throws RemoteException;
    
    /**
     *
     * @param login
     * @param path
     * @return
     * @throws java.rmi.RemoteException
     */
    
    boolean isDirectory(String login, String path) throws RemoteException;

    boolean logout(String login) throws RemoteException; 
    
    boolean newFolder(String login, String path) throws RemoteException;
    
    boolean deleteFile(String login, String path) throws RemoteException;
    
    boolean fileExist(String login, String path, File fileToSend) throws RemoteException;    
}
