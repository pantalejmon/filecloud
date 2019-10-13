/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import server.MainServer;
import server.controller.XMLController;
import remote.RemoteInterface;
import static server.MainServer.controller;
import static server.MainServer.gui;
import server.controller.ServerTreeViewController;
import server.model.files.FileTreeItem;
import server.model.users.ActiveUser;
import server.model.users.User;

/**
 *
 * @author STJ8WZ
 */
public class ServerConnectionController
        extends UnicastRemoteObject
        implements RemoteInterface {

    private ServerTreeViewController currentTreeView;

    public ServerConnectionController() throws RemoteException {
        super();
    }

    @Override
    public boolean searchUser(String login) throws RemoteException {
        File cloudMainDirectory = new File(MainServer.cloudPath);

        System.out.println("Server: searchUser(): looking for user - " + login);
        for (File temp : cloudMainDirectory.listFiles()) {

            if (temp.getName().equals(login)) {
                System.out.println("Server: searchUser(): user found");
                return true;
            }
        }

        System.out.println("Server: searchUser(): user not found");
        return false;
    }

    @Override
    public void createAccount(String login, String password)
            throws RemoteException,
            ParserConfigurationException,
            TransformerException {
        // creating user directory and XML

        // creating user config XML
        Document doc = XMLController.createXML();

        // add login
        XMLController.addElement(doc, "login", login);

        // add password
        XMLController.addElement(doc, "password", password);

        // make XML file
        File userDirectory = new File(MainServer.cloudPath + login);
        userDirectory.mkdir();
        File rootDirectory = new File(userDirectory.getPath() + "\\root");
        rootDirectory.mkdir();

        System.out.println("Server: User directory created: " + login);

        XMLController.saveXMLToFile(doc, login);

        System.out.println("Server: User created: " + login);
        controller.users.add(new User(login, password)); // Dodanie USERA

    }

    @Override
    public boolean authorisation(String login, String password)
            throws RemoteException,
            ParserConfigurationException,
            IOException,
            SAXException {

        String pass = null;
        User temp = new User(login, password);
        boolean flag_pass = false;

        for (User iterator : controller.users) {
            if (iterator.getLogin().equals(login)) {
                if (iterator.getPassword().equals(password)) {
                    flag_pass = true;
                    System.out.println("Server: authorizationHandler(): password accepted");
                    controller.active.add(new ActiveUser(login, password));

                } else {
                    System.out.println("Server: authorizationHandler(): wrong password");
                }
            }
        }

        return flag_pass;
    }

    @Override
    public String getCloudPath() throws RemoteException {
        return MainServer.cloudPath;
    }

    @Override
    public void Echo() throws RemoteException {
        System.out.print("Podlaczono klienta");
    }

    // option:
    // 2 - selected folder
    // 1 - previous folder (back)
    // 0 - first window (root folder)
    @Override
    public List<String> getArchivedFolder(String login, String currentPath, int option)
            throws RemoteException {

        List<String> foldersList = new ArrayList<String>();
        File dir = null;
        String RootPath = MainServer.cloudPath + login + "\\root";

        switch (option) {

            // start, root folder
            case 0:
                dir = new File(RootPath);

                foldersList.add("\\");

                for (File file : dir.listFiles()) {
                    foldersList.add(file.getName());
                }

                break;

            // previous folder
            case 1:
                dir = new File(RootPath + currentPath);

                if (dir.getPath().equals(RootPath)) {
                    foldersList = null;
                    System.out.println("ServerConnectionController -> \n\tgetLocalItemsArray(): you are in the root folder");
                    break;
                }

                Path tempPath = Paths.get(currentPath);
                Path previousPath = tempPath.getParent();

                String prevPath = previousPath.toString();

                File prevDir = new File(RootPath + prevPath);

                foldersList.add(prevPath);

                for (File file : prevDir.listFiles()) {
                    foldersList.add(file.getName());
                }

                break;

            // selected folder
            case 2:
                dir = new File(RootPath + currentPath);

                if (dir.isDirectory()) {
                    foldersList.add(currentPath);

                    for (File file : dir.listFiles()) {
                        foldersList.add(file.getName());
                    }

                }

                break;
        }

        return foldersList;
    }

    @Override
    public boolean uploadFile(String name, String login, byte[] data, int len, File fileToSend) throws RemoteException {
        ActiveUser temp = SearchActiveUser(login);
        if (temp != null) {
            Thread thread;
            thread = new Thread("Thread User:" + temp.getLogin() + temp.watki.size()) {
                @Override
                public void run() {
                    try {
                        Path f = Paths.get(MainServer.cloudPath + "\\" + login + "\\root" + name);
                        File file = f.toFile();
                        
                        try ( //f.toFile().mkdirs();
                                //f.toFile().createNewFile();
                                
                                FileOutputStream out = new FileOutputStream(file, false)) {
                            out.write(data, 0, len);
                            out.flush();
                            file.setLastModified(fileToSend.lastModified());
                        }
                        System.out.println("Odebrano plik: " + name);
                    } catch (IOException e) {
                    }
                }
            };

            thread.start();
            temp.watki.add(thread);
            // temp.watki.get(temp.watki.size()).start();
            return true;
        }
        return false;
    }

    @Override
    public byte[] downloadFile(String login, String path) throws RemoteException {

        FileInputStream in = null;

        String RootPath = MainServer.cloudPath + login + "\\root";
        File fileToSend = new File(RootPath + path);
        try {
            in = new FileInputStream(fileToSend);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] myData = new byte[(int) fileToSend.length()];
        int myLen;
        try {
            myLen = in.read(myData);
        } catch (IOException ex) {
            Logger.getLogger(ServerConnectionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myData;

    }

    @Override
    public boolean isDirectory(String login, String path) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.=======
    }

    @Override
    public boolean logout(String login) throws RemoteException {
        System.out.print("\nPróba wylogowania uzytkownika: " + login + "\n");
        for (ActiveUser usr : controller.active) {
            if (usr.getLogin().equals(login)) {

                // usr.session.flag = false;
                controller.active.remove(usr);
            }
            return true;
        }
        return false;
    }

    public ActiveUser SearchActiveUser(String Login) {
        for (ActiveUser usr : controller.active) {
            if (usr.getLogin().equals(Login)) {
                System.out.println("Znaleziono uzytkownika: " + usr.getLogin());
                return usr;
            }
        }
        System.out.println("Nie znaleziono użytkownika");
        return null;
    }

    @Override
    public boolean newFolder(String login, String path) throws RemoteException {
        String RootPath = MainServer.cloudPath + login + "\\root";
        File currentDir = new File(RootPath + "\\" + path);

        System.out.println("Directory to create: " + currentDir.getPath());
        if (!currentDir.exists()) {

            currentDir.mkdir();

            System.out.println("Server -> \n\tNew folder created");
            return true;

        } else {
            System.out.println("Server -> \n\tFolder exists");
            return false;
        }
    }

    @Override
    public boolean deleteFile(String login, String path) throws RemoteException {
        String RootPath = MainServer.cloudPath + login + "\\root";
        File file = new File(RootPath + "\\" + path);

        if (file.exists()) {
            file.delete();
            System.out.println("Deleted file: " + file.getPath());
            return true;
        } else {
            System.out.println("File doesn't exists");
            return false;
        }
    }

    @Override
    public boolean fileExist(String login, String path, File fileToSend) throws RemoteException {
        String RootPath = MainServer.cloudPath + login + "\\root";
        File fileCloud = new File(RootPath + "\\" + path);

        if (fileCloud.lastModified() >= fileToSend.lastModified()) {
            return true;
        } else {
            return false;
        }
    }

}
