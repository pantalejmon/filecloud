/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.model.TextColor;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Jacek Andrzej Steć
 */
public class WindowController {
    
    public static void printWarning(Text textField, String text, TextColor color) {

        textField.setText(text);

        switch (color) {
            case ACCEPT:
                textField.setFill(Color.GREEN);
                break;

            case ERROR:
                textField.setFill(Color.RED);
                break;
                
            case INFO:
                textField.setFill(Color.YELLOW);
                break;
        }
    }
}
