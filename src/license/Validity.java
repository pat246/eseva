package license;

import database.DBConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class Validity {
	public static int FIFTEEN_DAY_VALID = 15;
	public static int ONE_MONTH_VALID = 30;
	public static int TWO_MONTH_VALID = 60;
	public static int THREE_MONTH_VALID = 90;
	public static int ONE_DAY_VALID = 1;

	public Validity() {
	}

	public static Calendar storeUsageDate() throws Exception {
		Connection conn = DBConnectionManager.getMysqlConn();
		long now = Calendar.getInstance().getTimeInMillis();
		Crypto crypto = new Crypto();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from random");
		if (rs.next()) {
			String encryptedData = rs.getString(1);
			String decryptedData = crypto.decrypt(encryptedData);
			long startMs = Long.parseLong(decryptedData);
			Calendar startTime = Calendar.getInstance();
			startTime.setTimeInMillis(startMs);
			return startTime;
		}
		conn.close();
		return null;
	}
}
