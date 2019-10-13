/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import server.MainServer;
import static server.MainServer.gui;
import static server.MainServer.primaryStage;
import server.controller.remote.ServerConnectionController;
import server.model.users.ActiveUser;
import server.model.users.User;

/**
 *
 * @author janek
 */
public class ServerController
{

    public ServerConnectionController ConnectionController;
    public ObservableList<User> users = FXCollections.observableArrayList();
    public ObservableList<ActiveUser> active = FXCollections.observableArrayList();

    public ServerController() throws ParserConfigurationException, SAXException, IOException
    {
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(300);

        
        System.setProperty("java.security.policy","java.policy");
        if (System.getSecurityManager() == null);
        {
            System.setSecurityManager(new SecurityManager());
        }

        try
        {
            ConnectionController = new ServerConnectionController();
            LocateRegistry.createRegistry(1099);

            System.out.print("\nUruchomiono serwer RMI\n");
            String ip = Inet4Address.getLocalHost().getHostAddress();
            System.out.println("Server ip address: " + Inet4Address.getLocalHost().getHostAddress());
            Platform.runLater(()->
            {
                gui.status.ip.setText(ip);
            });
            Naming.rebind("rmi://" + Inet4Address.getLocalHost().getHostAddress() + "/abc", ConnectionController);
            //Naming.rebind("rmi://localhost/abc", ConnectionController);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        this.createCloudFolder();
        String pass = null;
        File cloudMainDirectory = new File(MainServer.cloudPath);
        System.out.println(MainServer.cloudPath);
        for (File temp : cloudMainDirectory.listFiles())
        {

            File XMLFile = new File(MainServer.cloudPath + temp.getName() + "\\config.xml");
            DocumentBuilderFactory dbFactory;
            dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XMLFile);
            pass = doc.getElementsByTagName("password").item(0).getTextContent();
            if (temp.getName() != null && pass != null)
            {
                users.add(new User(temp.getName(), pass));
                Platform.runLater(() ->
                {
                    gui.center.users.all.getItems().add(temp.getName());
                });
            }
        }
        users.addListener(new ListChangeListener()
        {
            @Override
            public void onChanged(ListChangeListener.Change c)
            {
                Platform.runLater(() ->
                {
                    gui.center.users.all.getItems().clear();
                    for (User tmp : users)
                    {
                        gui.center.users.all.getItems().add(tmp.getLogin());
                    }
                });
            }

        });
        
        
        active.addListener(new ListChangeListener()
        {
            @Override
            public void onChanged(ListChangeListener.Change c)
            {
                Platform.runLater(() ->
                {
                    gui.center.logged.logged.getItems().clear();
                    for (User tmp : active)
                    {
                        gui.center.logged.logged.getItems().add(tmp.getLogin());
                    }
                });
            }

        });

    }

    private void createCloudFolder()
    {
        File folder = new File(MainServer.cloudPath);
        if (!folder.exists())
        {
            System.out.println("Server: createCloudFolder(): folder created");
            folder.mkdir();
        }
    }

    public void updateUsersBase() throws ParserConfigurationException, SAXException, IOException
    {
        String pass = null;
        File cloudMainDirectory = new File(MainServer.cloudPath);
        Platform.runLater(() ->
        {
            gui.center.users.all.getItems().clear();
        });

        for (File temp : cloudMainDirectory.listFiles())
        {

            File XMLFile = new File(MainServer.cloudPath + temp.getName() + "\\config.xml");
            DocumentBuilderFactory dbFactory;
            dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XMLFile);
            pass = doc.getElementsByTagName("password").item(0).getTextContent();
            if (temp.getName() != null && pass != null)
            {
                users.add(new User(temp.getName(), pass));
                Platform.runLater(() ->
                {
                    gui.center.users.all.getItems().add(temp.getName());
                });
            }
        }
    }

}
