/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg304ui;

import java.util.List;

/**
 *
 * @author Daniel Lê
 */
public class UIUpdater {
    
    public static void error(String error) {
        UIMain.UI.errorDynamicLabel.setText("Error: "+error);
    }
    
    public static boolean login(String user, List matches) {
        UIMain.UI.currentUserDynamicLabel.setText(user);
        // UIMain.UI.matchesDropdown.setSelectedIndex(0);
        UIMain.UI.matchesDropdown.removeAllItems();
        for (Object match : matches) {
            String[] mat = ((String) match).split("\n");
            UIMain.UI.matchesDropdown.addItem(mat[1].substring(5));
        }
        return true;
    }
    
    public static boolean getMessages(List messages) {
        String output = "";
        for (Object message : messages) {
            output += message + "\n\n";
        }
        setText(output);
        return true;
    }
    
    public static boolean getMatches(List matches) {
        String output = "";
        for (Object match : matches) {
            output += match + "\n\n";
        }
        setText(output);
        return true;
    }
    
    public static void setText(String text) {
        UIMain.setText(text);
    }
    
}
