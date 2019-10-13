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
public class Folder extends FileNode
{
    protected ArrayList<FileNode> children;
    private User owner;
    private String name;
    public Folder(String name, User owner)
    {
        this.name = name;
        this.owner = owner;
    }
    
}
