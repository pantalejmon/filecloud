package client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class User {

    private StringProperty login;
    
    
    public User(String login) {
        this.login = new SimpleStringProperty(login);
    }
    
    //GETTERS & SETTERS

    public StringProperty getLogin() {
        return login;
    }

    public void setLogin(StringProperty login) {
        this.login = login;
    }
    
    
}
