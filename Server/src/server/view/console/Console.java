/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view.console;

import java.io.PrintStream;
import javafx.scene.control.TextArea;
import static server.MainServer.primaryStage;

/**
 *
 * @author janek
 */
public class Console extends TextArea
{

    /**
     *
     */
    public PrintStream standardOut;
    //public TextArea temp = new TextArea();
    
    /**
     *
     */
    public CustomOutputStream console = new CustomOutputStream(this);

    /**
     *
     */
    public Console()
    {
        super();
        PrintStream printStream = new PrintStream(console, true);
        standardOut = System.out;
        System.setOut(printStream);
       // System.setErr(printStream);
       this.autosize();
        this.prefWidthProperty().bind(primaryStage.widthProperty());
        

    }

}