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

import org.apache.commons.lang.StringUtils;

import utils.ThreadSafeUtil;

public class Company {
    private String                     name;
    private String                     uid;
    private String                     pass;
    private String                     email;
    public int                         id;
    public Date                        lastPassResetDate;
    private static String              SELECT_ALL_QUERY      = "select * from credentials";
    private static String              SELECT_COMPANY_BY_UID = "select * from credentials where user_id = ?";
    private static String              UPDATE_LPRD_BY_UID    = "update credentials set last_password_reset_date=? where user_id = ?";
    private static String              UPDATE_PASSWORD       = "update credentials set last_password_reset_date=?,password=? where user_id = ?";
    public static Map<String, Company> companies             = new HashMap<String, Company>();
    public static Vector<String>       compList              = new Vector<String>();

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
            companies.clear();
            compList.clear();
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
                while (rs.next()) {
                    String company = rs.getString("company_name");
                    String userId = rs.getString("user_id");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    Company c = new Company(company, userId, password, email);
                    Timestamp lprdTS = rs.getTimestamp("last_password_reset_date");
                    if (lprdTS != null) {
                        Date lprd = new Date(lprdTS.getTime());
                        c.setLastPassResetDate(lprd);
                    }
                    c.id = rs.getInt("id");
                    companies.put(company, c);
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
        if (StringUtils.isNotBlank(number)) {
            return (Integer.parseInt(number) + 1) + remainingStr;
        }
        return "1" + pass;
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
}
