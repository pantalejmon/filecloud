/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.users;

/**
 *
 * @author janek
 */
public class Session extends Thread
{
    public boolean flag;
    public ActiveUser owner;

    public Session(ActiveUser o)
    {
        owner = o;
        flag = true;
        this.start();
    }
    
    
    
    @Override
    public void run()
    {
        System.out.print("Uruchomiono sesje użytkownika" + owner.getLogin());
        while(flag)
        {
            
        }
        
    }
    
}
