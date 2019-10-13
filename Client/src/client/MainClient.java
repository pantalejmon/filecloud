package client;

import client.controller.connection.ClientConnectionController;
import client.model.User;
import client.view.ClientLoginWindow.ClientLoginWindowController;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import client.view.ClientOverview.ClientOverviewController;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jan Jakubik & Jacek Steć
 */
public class MainClient extends Application
{

    private Stage primaryStage;
    private BorderPane rootLayout;

    private boolean logged;
    private User loggedUser;
    private String ip;

    public static ClientConnectionController ConnectionController = null;
    public ClientOverviewController controller;

    public MainClient() throws NotBoundException, MalformedURLException, RemoteException
    {

        this.logged = false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("File Cloud Client");
        System.out.println("Client: Uruchomiono program");
        System.setProperty("java.security.policy", "java.policy");
        if (System.getSecurityManager() == null);
        {
            System.setSecurityManager(new SecurityManager());
        }
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we)
            {
                System.out.println("Stage is closing");
                if (logged)
                {
                    try
                    {
                        ConnectionController.getRemoteCloudController().logout(loggedUser.getLogin().getValue());
                    }
                    catch (RemoteException ex)
                    {
                        Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Platform.exit();
                System.exit(0);
            }
        });
        showClientLoginWindow();
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    public void showClientOverview()
    {
        try
        {
            System.out.println("Client: MainClient: showClientOverview()");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClient.class.getResource("view/ClientOverview/ClientOverview.fxml"));
            BorderPane personOverview = (BorderPane) loader.load();

            Scene scene = new Scene(personOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
             scene.getStylesheets().add("Grafika.css");
            controller = loader.getController();
            controller.setMainApp(this);
            controller.getUserLogin().setText(this.loggedUser.getLogin().getValue());
            controller.setBasics();
            controller.setIP(this.ip);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showClientLoginWindow()
    {
        try
        {
            System.out.println("Client: MainClient: showClientLoginWindow()");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClient.class.getResource("view/ClientLoginWindow/ClientLoginWindow.fxml"));
            BorderPane personLoginWindow = (BorderPane) loader.load();

            Scene scene = new Scene(personLoginWindow);
            primaryStage.setScene(scene);
            primaryStage.show();

            ClientLoginWindowController controller = loader.getController();
            scene.getStylesheets().add("Grafika.css");
            controller.setMainApp(this);

        }
        catch (IOException e)
        {
            System.out.println(e.toString());
        }
    }

    // GETTERS AND SETTERS
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public BorderPane getRootLayout()
    {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout)
    {
        this.rootLayout = rootLayout;
    }

    public boolean isLogged()
    {
        return logged;
    }

    public void setLogged(boolean logged)
    {
        this.logged = logged;
    }

    public User getLoggedUser()
    {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser)
    {
        this.loggedUser = loggedUser;
    }

    public ClientConnectionController getConnectionController()
    {
        return ConnectionController;
    }

    public void setIp(String ip)
    {
        if (!ip.equals(""))
        {
            this.ip = ip;
        }
    }

    public void setConnectionController(ClientConnectionController ConnectionController)
    {
        this.ConnectionController = ConnectionController;
    }

}
