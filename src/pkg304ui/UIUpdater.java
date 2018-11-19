/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg304ui;

import facadeUI.UserManager;
import java.util.List;
import pkg304data.PaymentInfo;

/**
 *
 * @author Daniel Lê
 */
public class UIUpdater {
    
    public static void error(String error) {
        UIMain.UI.errorTextArea.setText("Error: "+error);
    }
    
    public static boolean login(String user, List matches) {
        UserManager userManager = UserManager.getInstance();
        UIMain.UI.currentUserDynamicLabel.setText(user);
        // UIMain.UI.matchesDropdown.setSelectedIndex(0);
        UIMain.UI.matchesDropdown.removeAllItems();
        for (Object match : matches) {
            String[] mat = ((String) match).split("\n");
            UIMain.UI.matchesDropdown.addItem(mat[1].substring(5));
        }
        UIMain.UI.LogoutButton.setEnabled(true);
        UIMain.UI.getMessagesButton.setEnabled(true);
        UIMain.UI.sendMessageButton.setEnabled(true);
        UIMain.UI.sendMessageTextField.setEnabled(true);
        try {
            if (userManager.viewPaymentInfo() != null) {
                UIMain.UI.buyPremiumButton.setEnabled(true);
                UIMain.UI.cancelPremiumButton.setEnabled(true);
            }
        } catch (Exception e) {
            return false;
        }
        PaymentInfo paymentInfo = null;
        try {
            paymentInfo = userManager.viewPaymentInfo();
        } catch (Exception e) {
            return false;
        }
        if (paymentInfo == null) {
            UIMain.UI.addPaymentInfoButton.setEnabled(true);
        }
        UIMain.UI.getImageButton.setEnabled(true);
        UIMain.UI.uploadImageButton.setEnabled(true);
        UIMain.UI.deleteImageButton.setEnabled(true);
        UIMain.UI.getBrokenMatchesButton.setEnabled(true);
        return true;
    }
    
    public static boolean logout() {
        setText("");
        UIMain.UI.currentUserDynamicLabel.setText("");
        UIMain.UI.matchesDropdown.removeAllItems();
        UIMain.UI.LogoutButton.setEnabled(false);
        UIMain.UI.getMessagesButton.setEnabled(false);
        UIMain.UI.sendMessageButton.setEnabled(false);
        UIMain.UI.sendMessageTextField.setEnabled(false);
        UIMain.UI.buyPremiumButton.setEnabled(false);
        UIMain.UI.cancelPremiumButton.setEnabled(false);
        UIMain.UI.addPaymentInfoButton.setEnabled(false);
        UIMain.UI.getImageButton.setEnabled(false);
        UIMain.UI.uploadImageButton.setEnabled(false);
        UIMain.UI.deleteImageButton.setEnabled(false);
        UIMain.UI.getBrokenMatchesButton.setEnabled(false);
        return true;
    }
    
    public static boolean getMessages(List messages) {
        String output = "";
        for (Object message : messages) {
            output += ((String) message) + "\n\n";
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
    
    public static boolean updateMatches(List matches) {
        UIMain.UI.matchesDropdown.removeAllItems();
        for (Object match : matches) {
            String[] mat = ((String) match).split("\n");
            UIMain.UI.matchesDropdown.addItem(mat[1].substring(5));
        }
        return true;
    }
    
    public static void setText(String text) {
        UIMain.setText(text);
    }
    
}
