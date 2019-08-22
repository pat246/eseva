package handlers;

import java.awt.Color;
import java.awt.FileDialog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import transporter.EmailTranporter;
import utils.Constants;
import utils.NumberToWords;
import utils.ThreadSafeUtil;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import database.BillRecord;
import database.Company;
import database.Consultant;
import frames.BillGeneratorUIFram;
import frames.MenuFrame;

public class BillGenerator {

	public static Font fontHelvetica8Normal = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
	public static Font fontHelvetica10Normal = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
	public static Font fontHelvetica11Normal = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL);
	public static Font fontHelvetica8Bold = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
	public static Font fontHelvetica10Bold = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	public static Font fontHelvetica11Bold = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
	public static String BILL_NO = "N.A.";

	public static void generateBill(JFrame frmGenerateBill, List<String[]> billRecords) throws MalformedURLException,
			IOException, DocumentException, SQLException {
		FileDialog fDialog = new FileDialog(frmGenerateBill, "Save", FileDialog.SAVE);
		fDialog.setVisible(true);

		String path = fDialog.getDirectory() + fDialog.getFile();
		path += ".pdf";
		File file = new File(path);
		if (billRecords == null || billRecords.size() == 0) {
			BILL_NO = "" + storeBill();
		}
		ByteArrayOutputStream stream = getBillFileOpStream(billRecords);
		FileOutputStream fileStream = new FileOutputStream(file);
		fileStream.write(stream.toByteArray());
		fileStream.close();
		stream.close();
	}

	private static int storeBill() throws SQLException {
		JTextField[][] fieldArr = BillGeneratorUIFram.textFieldsArr;
		int srNo = 1;
		List<BillRecord> records = new ArrayList<BillRecord>();
		Consultant consu = (Consultant) BillGeneratorUIFram.CONSULTANT_COMBO_BOX.getSelectedItem();
		Company company = MenuFrame.BASIC_PANEL.getSelectedCompany();
		int billNo = BillRecord.getLastBillNo() + 1;
		for (JTextField[] field : fieldArr) {
			JTextField desc = field[0];
			JTextField units = field[1];
			JTextField unitPrice = field[2];
			if (StringUtils.isNotBlank(units.getText()) && StringUtils.isNotBlank(unitPrice.getText())) {
				int intunits = Integer.parseInt(units.getText());
				int intunitprice = Integer.parseInt(unitPrice.getText());
				int total = (intunits * intunitprice);
				BillRecord rec = new BillRecord(intunits, intunitprice, total, consu, company, desc.getText(), "");
				rec.setSerialNo(srNo);
				rec.setBillNo(billNo);
				rec.add();
				records.add(rec);
				srNo++;
			}
		}
		return billNo;
	}

	private static ByteArrayOutputStream getBillFileOpStream(List<String[]> billRecords) throws DocumentException,
			MalformedURLException, IOException, SQLException {

		Document document = new Document(PageSize.A4, 25, 25, 15, 15);
		ByteArrayOutputStream pdfData = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, pdfData);
		document.open();

		String today = ThreadSafeUtil.getSimpleDateFormat(false, false).format(Calendar.getInstance().getTime());
		if(BillGeneratorUIFram.textField_21.getText() != null) {
			today = BillGeneratorUIFram.textField_21.getText(); 
		}
		Paragraph date = new Paragraph("Date: " + today, fontHelvetica8Normal);
		date.setAlignment(Rectangle.ALIGN_RIGHT);
		document.add(date);

		Paragraph billNo = new Paragraph("Bill No.: " + BILL_NO, fontHelvetica11Normal);
		billNo.setAlignment(Rectangle.ALIGN_RIGHT);
		document.add(billNo);
		Consultant consu = null;
		document.add(getRowSpacer());
		if (billRecords == null) {
			consu = (Consultant) BillGeneratorUIFram.CONSULTANT_COMBO_BOX.getSelectedItem();
		} else {
			BillRecord billRec = ViewBillHandler.getAnyRecordFromPresentWindow();
			BILL_NO = billRec.getBillNo() + "";
			consu = billRec.getConsu();
		}

		Table table_top = new Table(2, 2);
		table_top.setWidths(new int[] { 650, 250 });
		table_top.setPadding(3);
		table_top.setWidth(100);
		table_top.setBorderWidth(0);
		table_top.setBorderColor(new Color(255, 255, 255));

		String path = Constants.CONTEXT_ROOT_PATH + "/images/me_logo.jpg";

		File file = new File(path);
		if (file.exists()) {
			Image imgGif = Image.getInstance(path);
			imgGif.setAlignment(Image.LEFT | Image.TEXTWRAP);
			Cell c_img = new Cell();
			c_img.add(imgGif);
			c_img.setBorderColor(new Color(255, 255, 255));
			c_img.setBorder(0);
			c_img.setColspan(2);
			// c_img.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);

			table_top.addCell(c_img);
		} else {
			Chunk chunk = new Chunk(consu.getComapny_name(), fontHelvetica11Bold);
			Cell c_img = new Cell();
			c_img.add(chunk);
			c_img.setBorderColor(new Color(255, 255, 255));
			c_img.setBorder(0);
			c_img.setColspan(2);
			// c_img.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);

			table_top.addCell(c_img);
		}

		Paragraph viaAddPg = new Paragraph(getTopAddressParagraph(billRecords));
		if (viaAddPg != null) {
			Cell c_cell = new Cell();
			c_cell.add(viaAddPg);
			c_cell.setBorderColor(new Color(255, 255, 255));
			c_cell.setBorder(0);
			Cell c_cell1 = new Cell();
			c_cell1.add(getToAddressParagraph(billRecords));
			c_cell1.setBorderColor(new Color(255, 255, 255));
			c_cell1.setBorder(0);
			table_top.addCell(c_cell);
			table_top.addCell(c_cell1);

			document.add(table_top);
		}

		Chunk invoiceChukn = new Chunk("CASH/CREDIT MEMO", fontHelvetica10Bold);
		invoiceChukn.setUnderline(0.5f, -1f);
		Paragraph invoicePg = new Paragraph();
		invoicePg.add(invoiceChukn);
		invoicePg.setAlignment(Rectangle.ALIGN_CENTER);
		document.add(invoicePg);

		document.add(getDataTable(billRecords));
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());

		Paragraph thanks = new Paragraph("Thank You", fontHelvetica10Normal);
		thanks.setAlignment(Rectangle.ALIGN_LEFT);
		document.add(thanks);
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());
		document.add(getRowSpacer());

		Paragraph sign = new Paragraph("Signature", fontHelvetica10Normal);
		sign.setAlignment(Rectangle.ALIGN_RIGHT);
		if (billRecords == null && BillGeneratorUIFram.checkbox_1.getState()) {
			document.add(sign);
		}

		document.close();
		return pdfData;
	}

	public static Paragraph getTopAddressParagraph(List<String[]> billRecords) throws SQLException {
		Consultant consu = null;
		if (billRecords == null) {
			consu = (Consultant) BillGeneratorUIFram.CONSULTANT_COMBO_BOX.getSelectedItem();
		} else {
			consu = ViewBillHandler.getAnyRecordFromPresentWindow().getConsu();
		}
		Paragraph viaAddPg = new Paragraph(consu.getFullName(), fontHelvetica8Bold);
		viaAddPg.add(new Phrase("\n" + consu.getAddr(), fontHelvetica8Normal));
		if (StringUtils.isNotBlank(consu.getAddr1())) {
			viaAddPg.add(new Phrase("\n" + consu.getAddr1(), fontHelvetica8Normal));
		}
		if (StringUtils.isNotBlank(consu.getAddr2())) {
			viaAddPg.add(new Phrase("\n" + consu.getAddr2(), fontHelvetica8Normal));
		}
		viaAddPg.add(new Phrase("\nPhone: " + consu.getContactNumbers(), fontHelvetica8Normal));
		viaAddPg.add(new Phrase("\nEmail: " + consu.getEmail(), fontHelvetica8Normal));
		if (billRecords != null || BillGeneratorUIFram.checkbox.getState()) {
			viaAddPg.add(new Phrase("\nPAN No.: " + StringUtils.defaultString(consu.getPan(), ""), fontHelvetica8Normal));
		}
		return viaAddPg;
	}

	public static Paragraph getToAddressParagraph(List<String[]> billRecords) throws SQLException {
		Company company = null;
		if (billRecords == null) {
			company = MenuFrame.BASIC_PANEL.getSelectedCompany();
		} else {
			company = ViewBillHandler.getAnyRecordFromPresentWindow().getComp();
		}

		Paragraph viaAddPg = new Paragraph("To,", fontHelvetica8Bold);
		viaAddPg.add(new Phrase("\n" + company.getCompanyName(), fontHelvetica8Normal));
		viaAddPg.add(new Phrase("\n" + company.getAddr(), fontHelvetica8Normal));
		viaAddPg.add(new Phrase("\n" + company.getPhone(), fontHelvetica8Normal));
		return viaAddPg;
	}

	public static Table getRowSpacer() throws BadElementException {
		Table table_top = new Table(1, 2);
		table_top.setBorder(0);
		table_top.setBorderWidth(0);
		table_top.setBorderColor(new Color(255, 255, 255));
		Cell cell = new Cell();
		cell.setBorderColor(new Color(255, 255, 255));
		cell.setBorder(0);
		table_top.addCell(cell);
		return table_top;
	}

	public static int addDataToTable(Table table, List<String[]> billRecords) throws BadElementException {
		JTextField[][] fieldArr = BillGeneratorUIFram.textFieldsArr;
		int srNo = 1;
		int tot = 0;

		if (billRecords == null || billRecords.size() == 0) {
			List<String[]> billRecordsArr = new ArrayList<String[]>();

			for (JTextField[] field : fieldArr) {
				JTextField desc = field[0];
				JTextField units = field[1];
				JTextField unitPrice = field[2];
				if (StringUtils.isNotBlank(units.getText()) && StringUtils.isNotBlank(unitPrice.getText())) {
					int total = (Integer.parseInt(units.getText()) * Integer.parseInt(unitPrice.getText()));
					String[] strArr = new String[5];
					strArr[1] = desc.getText();
					strArr[2] = units.getText();
					strArr[3] = unitPrice.getText();
					strArr[4] = total + "";
					strArr[0] = srNo + "";
					srNo++;
					billRecordsArr.add(strArr);
				}
			}
			billRecords = billRecordsArr; // when download bill caled from
											// generate bill frame
		}

		for (String[] record : billRecords) {
			int i = 0;
			table.addCell(new Phrase(record[i++], fontHelvetica8Normal));
			table.addCell(new Phrase(record[i++], fontHelvetica8Normal));
			table.addCell(new Phrase(record[i++], fontHelvetica8Normal));
			table.addCell(new Phrase(record[i++], fontHelvetica8Normal));
			table.addCell(new Phrase(record[i++], fontHelvetica8Normal));
			tot += Integer.parseInt(record[4]);
			srNo++;
		}
		return tot;
	}

	public static Table getDataTable(List<String[]> bllRecords) throws DocumentException {
		Table table = new Table(5, 10);
		table.setWidths(new int[] { 100, 500, 100, 100, 200 });
		table.setPadding(3);
		table.setWidth(100);
		table.setBorderWidth(0);
		table.setBorderColor(new Color(255, 255, 255));

		String[] headers = { "Sr. No", "Description", "Units", "Price", "Line Total" };
		for (String header : headers) {
			table.addCell(new Phrase(header, fontHelvetica8Normal));
		}
		JTextField[][] fieldArr = BillGeneratorUIFram.textFieldsArr;
		int tot = addDataToTable(table, bllRecords);
		Cell spaceCell = new Cell(new Phrase("\n\n\n\n\n\n\n\n", fontHelvetica8Normal));
		spaceCell.setColspan(5);
		spaceCell.setRowspan(5);
		table.addCell(spaceCell);
		String[] result = new String[1];
		result[0] = "";
		NumberToWords.ConvertNumberToText(tot, result);
		Cell total = new Cell(new Phrase("INR " + tot + " ( Rupees " + result[0] + " Only)", fontHelvetica8Normal));

		Cell totalTxt = new Cell(new Phrase("Total", fontHelvetica8Normal));
		totalTxt.setColspan(4);
		table.addCell(totalTxt);
		table.addCell(total);
		return table;
	}

	public static void generateAndSendMail(JFrame frmGenerateBill) {
		Consultant consu = (Consultant) BillGeneratorUIFram.CONSULTANT_COMBO_BOX.getSelectedItem();
		Company company = MenuFrame.BASIC_PANEL.getSelectedCompany();
		JPasswordField pwd = new JPasswordField(20);
		JLabel lable = new JLabel("Please enter password of " + consu.getEmail() + " account.");
		JPanel panel = new JPanel();
		panel.add(lable);
		panel.add(pwd);

		int action = JOptionPane.showConfirmDialog(frmGenerateBill, panel, "Enter password",
				JOptionPane.OK_CANCEL_OPTION);
		if (action != JOptionPane.OK_OPTION) {
			return;
		}
		char[] passwordCharArr = pwd.getPassword();
		String password = new String(passwordCharArr);
		String subject = "Your bill is generated";
		String message = getMessage(company);
		try {
			if (StringUtils.isNotBlank(password)) {
				EmailTranporter.setPASSWORD(password);
				EmailTranporter.setUSERNAME(consu.getEmail().trim());
				ByteArrayOutputStream stream = getBillFileOpStream(null);
				String date = ThreadSafeUtil.getddMMyyyyDateFormat(false, false).format(
						Calendar.getInstance().getTime());
				File[] files = new File[1];
				File file = new File("Bill_" + date + ".pdf");
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(stream.toByteArray());
				files[0] = file;
				EmailTranporter tranporter = new EmailTranporter(company.getEmail(), subject, message, null, files);
				tranporter.sendEmailWithAttachments();
				fos.close();
				stream.close();
				JOptionPane.showMessageDialog(frmGenerateBill, "Bill sent successfully to " + company.getEmail());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmGenerateBill, "Failed to send email");
			e.printStackTrace();
		}
	}

	private static String getMessage(Company company) {
		StringBuffer sBuf = new StringBuffer();
		Consultant consu = (Consultant) BillGeneratorUIFram.CONSULTANT_COMBO_BOX.getSelectedItem();
		sBuf.append("Dear Mr./Ms. ").append(company.getContactPerson()).append(",<br/>");
		sBuf.append("Your consultation bill has been generated.<br/> ").append("Please see attached document.")
				.append("<br/>");
		sBuf.append("<br/><br/><br/>Thank you<br/>");
		sBuf.append(consu.getFullName() + "<br/>");
		sBuf.append("P.F. Consultant, Kolhapur");
		return sBuf.toString();
	}

}
