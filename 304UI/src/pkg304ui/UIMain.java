/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg304ui;

import pkg304ui.UI.UIFrame;

/**
 *
 * @author Daniel Lê
 */
public class UIMain {
    
    public static UIFrame UI;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UI = new UIFrame();
        UI.setVisible(true);
        setText("No Data");
    }
    
    public static void setText(String text){
        UI.RightText.setText(text);
    }
    
}
