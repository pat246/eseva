package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import utils.DialogUtils;
import utils.ThreadSafeUtil;

public class Company {
	private String name;
	private String uid;
	private String pass;
	private String email;
	private String mobile;
	private String addr;
	private String contactPerson;
	public int id;
	public Date lastPassResetDate;
	private static String SELECT_ALL_QUERY = "select * from credentials";
	private static String SELECT_COMPANY_BY_UID = "select * from credentials where user_id = ?";
	private static String SELECT_COMPANY_BY_ID = "select * from credentials where id = ?";
	private static String UPDATE_LPRD_BY_UID = "update credentials set last_password_reset_date=? where user_id = ?";
	private static String UPDATE_PASSWORD = "update credentials set last_password_reset_date=?,password=? where user_id = ?";
	public static Map<String, Company> companies = new HashMap<String, Company>();
	public static Vector<String> compList = new Vector<String>();
	public static int PASSWORD_EXPIRY_DAYS_LIMIT = 3 * 30;
	public static int ONE_DAY_IN_MILISECONDS = 1 * 24 * 60 * 60 * 1000;
	public static int NO_OF_DAYS_BEFORE_TO_CHECK_PASSWORD = 10;

	public Company(String name, String uid, String pass) {
		this.name = name;
		this.uid = uid;
		this.pass = pass;
	}

	public Company(String name, String uid, String pass, String email) {
		this.name = name;
		this.uid = uid;
		this.pass = pass;
		this.email = email;
	}

	public static void initialize() throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();

		if (conn != null) {
			// companies.clear();
			compList.clear();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
				while (rs.next()) {
					String company = rs.getString("company_name");
					String userId = rs.getString("user_id");
					String password = rs.getString("password");
					String email = rs.getString("email");
					String mob = rs.getString("mobile");
					String cp = rs.getString("contact_person");
					String addr = rs.getString("address");
					Company c = new Company(company, userId, password, email);
					c.setMobile(mob);
					c.setContactPerson(cp);
					c.setAddr(addr);
					Timestamp lprdTS = rs.getTimestamp("last_password_reset_date");
					if (lprdTS != null) {
						Date lprd = new Date(lprdTS.getTime());
						c.setLastPassResetDate(lprd);
					}
					c.id = rs.getInt("id");
					if (companies.get(company) == null) {
						companies.put(company, c);
					} else {
						Company old = companies.get(company);
						old.deepCopy(c);
					}
					compList.add(company);
					Collections.sort(compList, new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							return o1.compareToIgnoreCase(o2);
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}
	}

	private void deepCopy(Company c) {
		setEmail(c.email);
		setLastPassResetDate(c.lastPassResetDate);
		setMobile(c.mobile);
		setName(c.name);
		setPass(c.pass);
		setUid(c.uid);
		setContactPerson(c.contactPerson);
		setAddr(c.addr);
	}

	public Date getLastPassResetDate() {
		return this.lastPassResetDate;
	}

	public void setLastPassResetDate(Date lastPassResetDate) {
		this.lastPassResetDate = lastPassResetDate;
	}

	public String getCompanyName() {
		return this.name;
	}

	public String getCompanyUserId() {
		return this.uid;
	}

	public String getCompanyPassword() {
		return this.pass;
	}

	public String getCompanyEmail() {
		return this.email;
	}

	public static Company getCompanyByUsername(String uid) throws SQLException {
		Company c = null;
		Connection conn = DBConnectionManager.getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SELECT_COMPANY_BY_UID);
			int i = 1;
			pstmt.setString(i++, uid);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String company = rs.getString("company_name");
				String userId = rs.getString("user_id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				c = new Company(company, userId, password, email);
				Timestamp lprdTS = rs.getTimestamp("last_password_reset_date");
				if (lprdTS != null) {
					Date lprd = new Date(lprdTS.getTime());
					c.setLastPassResetDate(lprd);
				}
				c.id = rs.getInt("id");
			}
		} finally {
			conn.close();
		}
		return c;
	}

	public static Company getCompanyById(int id) throws SQLException {
		Company c = null;
		Connection conn = DBConnectionManager.getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SELECT_COMPANY_BY_ID);
			int i = 1;
			pstmt.setInt(i++, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String company = rs.getString("company_name");
				String userId = rs.getString("user_id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				c = new Company(company, userId, password, email);
				Timestamp lprdTS = rs.getTimestamp("last_password_reset_date");
				if (lprdTS != null) {
					Date lprd = new Date(lprdTS.getTime());
					c.setLastPassResetDate(lprd);
				}
				c.id = rs.getInt("id");
			}
		} finally {
			conn.close();
		}
		return c;
	}

	public void updateLprd(Date lprd) throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_LPRD_BY_UID);
			int i = 1;
			Timestamp ts = new Timestamp(lprd.getTime());
			pstmt.setTimestamp(i++, ts);
			pstmt.setString(i++, this.uid);
			pstmt.executeUpdate();
		} finally {
			conn.close();
		}
	}

	public String generateNewPassword() {
		String number = getNumber(pass);
		String remainingStr = pass.substring(number.length());
		if ("1234567".equals(number)) {
			return "123456" + remainingStr;
		} else if ("123456".equals(number)) {
			return "1234567" + remainingStr;
		}
		if (StringUtils.isNotBlank(number)) {
			return (Integer.parseInt(number) + 1) + remainingStr;
		}
		String newPass = DigestUtils.sha1Hex(Calendar.getInstance().getTimeInMillis() + pass).substring(0, 5);
		return "1@" + newPass;
	}

	private String getNumber(String pass) {
		StringBuffer num = new StringBuffer();
		for (char c : pass.toCharArray()) {
			if (!(c >= '0' && c <= '9')) {
				break;
			}
			num.append(c);
		}
		return num.toString();
	}

	public void storeNewPassword(String newPassword) {
		this.pass = newPassword;
		this.lastPassResetDate = Calendar.getInstance().getTime();
		Connection conn = DBConnectionManager.getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_PASSWORD);
			int i = 1;
			Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
			pstmt.setTimestamp(i++, ts);
			pstmt.setString(i++, newPassword);
			pstmt.setString(i++, this.uid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLprdForDisplay() {
		return ThreadSafeUtil.getDateTime12HrsWithoutSecondsDotFormat(false, false).format(this.getLastPassResetDate());
	}

	public String getPhone() {
		return mobile;
	}

	public void setMobile(String phone) {
		this.mobile = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
