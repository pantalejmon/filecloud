
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.users;

import java.util.ArrayList;

/**
 *
 * @author janek
 */
public class ActiveUser extends User
{

    /**
     *
     * @param login
     * @param password
     */
    //public Session session;
    public ArrayList<Thread> watki = new ArrayList<Thread>();

    public ActiveUser(String login, String password)
    {
        super(login, password);
        //session = new Session(this);
    }
    
    
}
    

