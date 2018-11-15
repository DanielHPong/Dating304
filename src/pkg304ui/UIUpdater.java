/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg304ui;

import java.util.ArrayList;

/**
 *
 * @author Daniel Lê
 */
public class UIUpdater {
    
    public static boolean login(String user, ArrayList matches) {
        UIMain.UI.currentUserDynamicLabel.setText(user);
        UIMain.UI.matchesDropdown.setSelectedIndex(0);
        UIMain.UI.matchesDropdown.removeAllItems();
        for (Object match : matches) {
            UIMain.UI.matchesDropdown.addItem((String)match);
        }
        return true;
    }
    
}
