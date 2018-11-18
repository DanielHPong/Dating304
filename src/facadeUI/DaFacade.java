package facadeUI;
import java.util.List;

import pkg304data.*;

public interface DaFacade {
	public void login(String id);
	public void logout();
	public void getMatch();
	public void getMessage(String id);
	public void sendMessage(String id, String msg);
	public void buyPrem(String type, String cardType, String cardNo, String address);
	public void cancelPrem(String type);
	public void uploadImage(String url);
	public void deleteImage(String url);
	public void getImage();
	public void signUp(String email, String name, String gender, int pID);
	public void deactivate();
	public PaymentInfo viewPaymentInfo() throws Exception;
	public void showUserStats();
	public void showBuyRecords();
	public List<PremiumPackage> viewPrem() throws Exception;
	public List<PremiumPackage> myPremiums() throws Exception;
	public void addPaymentInfo(String cardType, String cardNo, String address);
	public void deletePaymentInfo();
}