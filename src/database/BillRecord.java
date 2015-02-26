package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillRecord {

	public static String TABLE = "bill_record";

	public static String INSERT = "insert into "
			+ TABLE
			+ " (serial_no, description,unit,price,line_total,consu_id,company_id,tag_name, bill_no) values (?,?,?,?,?,?,?,?,?)";

	public static String LAST_BILL_NO = "select max(bill_no) from " + TABLE;

	public static String VIEW_BILL_NO = "select * from bill_record where bill_no =?";

	public BillRecord(int unit, int price, int lineTotal, Consultant consu, Company comp, String description, String tag) {
		super();
		this.unit = unit;
		this.price = price;
		this.lineTotal = lineTotal;
		this.consu = consu;
		this.comp = comp;
		this.description = description;
		this.tag = tag;
	}

	public BillRecord() {
		// TODO Auto-generated constructor stub
	}

	private int id;
	private int billNo;
	private int unit;
	private int price;
	private int lineTotal;
	private int serialNo;
	private Consultant consu;
	private Company comp;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(int lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Consultant getConsu() {
		return consu;
	}

	public void setConsu(Consultant consu) {
		this.consu = consu;
	}

	public Company getComp() {
		return comp;
	}

	public void setComp(Company comp) {
		this.comp = comp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private String tag;

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public void add() throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		int index = 1;
		java.sql.PreparedStatement pstmt = conn.prepareStatement(INSERT);
		pstmt.setInt(index++, this.serialNo);
		pstmt.setString(index++, this.description);
		pstmt.setInt(index++, this.unit);
		pstmt.setInt(index++, this.price);
		pstmt.setInt(index++, this.lineTotal);
		pstmt.setInt(index++, this.consu.getId());
		pstmt.setInt(index++, this.comp.id);
		pstmt.setString(index++, this.tag);
		pstmt.setInt(index++, this.billNo);
		pstmt.execute();
		conn.close();
	}

	public static int getLastBillNo() throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(LAST_BILL_NO);
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 1;
		}
	}

	public static List<BillRecord> getBillByNo(int no) throws SQLException {
		Connection conn = DBConnectionManager.getMysqlConn();
		int index = 1;
		PreparedStatement pstmt = conn.prepareStatement(VIEW_BILL_NO);
		pstmt.setInt(index, no);
		ResultSet rs = pstmt.executeQuery();
		List<BillRecord> recs = new ArrayList<BillRecord>();
		while (rs.next()) {
			BillRecord rec = new BillRecord();
			rec.setUnit(rs.getInt("unit"));
			rec.setPrice(rs.getInt("price"));
			rec.setLineTotal(rs.getInt("line_total"));
			// rs.getInt("consu_id");
			rec.setComp(Company.getCompanyById(rs.getInt("company_id")));
			rec.setTag(rs.getString("tag_name"));
			// rs.getInt("consu_id");
			rec.setSerialNo(rs.getInt("serial_no"));
			rec.setDescription(rs.getString("description"));
			recs.add(rec);
		}
		return recs;
	}
}
