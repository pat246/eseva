package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Consultant {
	private String fname;
	private String lname;
	private String addr;
	private String addr1;
	private String addr2;
	private String email;
	private String contactNumbers;
	private String comapny_name;
	private String pan;

	public int id;
	private static String SELECT_ALL_QUERY = "select * from consultants";
	private static String INSERT_QUERY = "insert into consultants (fname,lname,addr,addr1,addr2,contact_numbers,email,company_name,pan ) values (?,?,?,?,?,?,?,?,?)";

	public static List<Consultant> getAllConsultant() throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		List<Consultant> all = new ArrayList<Consultant>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
			while (rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String addr = rs.getString("addr");
				String addr1 = rs.getString("addr1");
				String addr2 = rs.getString("addr2");
				String email = rs.getString("email");
				String contacts = rs.getString("contact_numbers");
				String comapny_name = rs.getString("company_name");
				String pan = rs.getString("pan");
				int id = rs.getInt("id");
				Consultant consu = new Consultant(fname, lname, addr, contacts, comapny_name);
				consu.setEmail(email);
				consu.setAddr1(addr1);
				consu.setAddr2(addr2);
				consu.setId(id);
				consu.setComapny_name(comapny_name);
				consu.setPan(pan);
				all.add(consu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return all;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public static Vector<Consultant> getAllConsultantByFullName() throws SQLException {
		Vector<Consultant> names = new Vector<Consultant>();
		List<Consultant> all = getAllConsultant();
		for (Consultant consu : all) {
			names.add(consu);
		}
		return names;
	}

	public boolean addConsultant() throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		try {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY);
			int i = 1;
			pstmt.setString(i++, this.fname);
			pstmt.setString(i++, this.lname);
			pstmt.setString(i++, this.addr);
			pstmt.setString(i++, this.addr1);
			pstmt.setString(i++, this.addr2);
			pstmt.setString(i++, this.contactNumbers);
			pstmt.setString(i++, this.email);
			pstmt.setString(i++, this.comapny_name);
			pstmt.setString(i++, this.pan);
			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			conn.close();
		}
		return true;
	}

	public Consultant(String fname, String lname, String addr, String contactNumbers, String cname) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.addr = addr;
		this.contactNumbers = contactNumbers;
		this.comapny_name = cname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getContactNumbers() {
		return contactNumbers;
	}

	public void setContactNumbers(String contactNumbers) {
		this.contactNumbers = contactNumbers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fname + " " + this.lname;
	}

	@Override
	public String toString() {
		return this.getFullName();
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;

	}

	public String getComapny_name() {
		return comapny_name;
	}

	public void setComapny_name(String comapny_name) {
		this.comapny_name = comapny_name;
	}
}
