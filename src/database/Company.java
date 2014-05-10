package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Company {

    private String                     name;
    private String                     uid;
    private String                     pass;
    private String                     email;
    public int                         id;
    private static String              SELECT_ALL_QUERY = "select * from credentials";
    public static Map<String, Company> companies        = new HashMap<String, Company>();
    public static Vector<String>       compList         = new Vector<String>();

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
        String company, userId, password, email;
        if (conn != null) {
            Statement stmt;
            companies.clear();
            compList.clear();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
                while (rs.next()) {
                    company = rs.getString("company_name");
                    userId = rs.getString("user_id");
                    password = rs.getString("password");
                    email = rs.getString("email");
                    Company c = new Company(company, userId, password, email);
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
}
