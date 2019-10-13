package client.view.ClientLoginWindow;

import client.MainClient;
import client.view.WindowController;
import client.controller.connection.ClientConnectionController;
import client.model.TextColor;
import client.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author Jacek Andrzej Steć
 */
public class ClientLoginWindowController implements Initializable {

    private MainClient mainApp;

    @FXML
    private TextField ipTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text commonError;

    @FXML
    private Button loginButton;
    @FXML
    private Button createAccountButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        this.commonError.setText("");

        
        this.loginTextField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER:

                    WindowController.printWarning(commonError, "", TextColor.ACCEPT);

                    if (this.passwordField.getText().equals("")
                            | this.loginTextField.getText().equals("")) {

                        WindowController.printWarning(commonError, "Enter user login and password", TextColor.INFO);
                        break;
                    }

                    this.login_callback();

                    break;
            }
        });

        this.passwordField.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER:

                    WindowController.printWarning(commonError, "", TextColor.ACCEPT);

                    if (this.passwordField.getText().equals("")
                            | this.loginTextField.getText().equals("")) {

                        WindowController.printWarning(commonError, "Enter user login and password", TextColor.INFO);
                        break;
                    }

                    this.login_callback();

                    break;
            }
        });

        this.loginButton.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER:

                    WindowController.printWarning(commonError, "", TextColor.ACCEPT);

                    this.login_callback();
                    break;
            }
        });

        this.createAccountButton.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            switch (event.getCode()) {
                case ENTER: {

                    WindowController.printWarning(commonError, "", TextColor.ACCEPT);

                    try {
                        this.createAccount_callback();
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(ClientLoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerException ex) {
                        Logger.getLogger(ClientLoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }

        });
    }

    public void setMainApp(MainClient mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void login_callback() {

        String ip = null;
        try{
            if (this.mainApp.getConnectionController() == null && !this.ipTextField.getText().equals("")) {
                ip = this.ipTextField.getText();
                this.mainApp.setIp(ip);
                this.mainApp.setConnectionController(new ClientConnectionController(this.ipTextField.getText()));
            }
        } catch (Exception ex){
            this.mainApp.setConnectionController(null);
            ex.printStackTrace();
        }

        //looking for an account if exists in database...
        try {
            if (this.mainApp.getConnectionController().getRemoteCloudController().searchUser(this.loginTextField.getText())) {

                if (this.mainApp.getConnectionController().getRemoteCloudController().authorisation(this.loginTextField.getText(), this.passwordField.getText())) {
                    this.mainApp.setLogged(true);
                    this.mainApp.setLoggedUser(new User(this.loginTextField.getText()));
                    
                    this.mainApp.showClientOverview();
                    

                } else {
                    WindowController.printWarning(commonError, "Incorrect login or password", TextColor.INFO);
                }

            } else {
                WindowController.printWarning(commonError, "Incorrect login or password", TextColor.INFO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void createAccount_callback()
            throws TransformerConfigurationException,
            ParserConfigurationException,
            TransformerException {

        if (this.mainApp.getConnectionController() == null && !this.ipTextField.getText().equals("")) {
            this.mainApp.setConnectionController(new ClientConnectionController(this.ipTextField.getText()));
        }

        try {
            if (!this.mainApp.getConnectionController().getRemoteCloudController().searchUser(this.loginTextField.getText())) {

                this.mainApp.getConnectionController().getRemoteCloudController().createAccount(this.loginTextField.getText(), this.passwordField.getText());
                WindowController.printWarning(this.commonError, "User created", TextColor.INFO);

            } else {
                WindowController.printWarning(this.commonError, "User already exists", TextColor.INFO);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    private void close_callback() {
        
        Platform.exit();
    }

    public TextField getIpTextField() {
        return ipTextField;
    }

}
