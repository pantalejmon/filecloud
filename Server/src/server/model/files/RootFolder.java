/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.files;

import java.util.ArrayList;
import server.model.users.User;

/**
 *
 * @author janek
 */
public class RootFolder 
{
    private User owner;
    private String name;
    private ArrayList<FileNode> children;

    public RootFolder(User usr)
    {
       owner = usr;
    }
    
    
    
    
}
