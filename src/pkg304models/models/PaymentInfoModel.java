package pkg304models.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pkg304data.PaymentInfo;
import pkg304models.GenericModel;

public class PaymentInfoModel extends GenericModel<PaymentInfo> {

	public PaymentInfoModel(Connection con) {
		super(con);
	}

	@Override
	public List<PaymentInfo> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
