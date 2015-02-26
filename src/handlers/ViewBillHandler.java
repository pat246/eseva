package handlers;

import java.awt.Container;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;

import database.BillRecord;
import database.Company;
import frames.MenuFrame;
import frames.ViewBillsFrame;

public class ViewBillHandler {

	public static int noofrow = 0;

	public static void viewBill() {

		ViewBillsFrame frame = ViewBillsFrame.VIEW_BILL_FRAME;
		String billNo = frame.getTextField().getText();
		if (StringUtils.isBlank(billNo)) {
			JOptionPane.showMessageDialog(frame, "Please enter bill number");
			return;
		}
		int billNum = -1;
		try {
			billNum = Integer.parseInt(billNo);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Please enter valid number");
			return;
		}
		Company company = MenuFrame.BASIC_PANEL.getSelectedCompany();
		try {
			List<BillRecord> billRec = BillRecord.getBillByNo(billNum);
			DefaultTableModel model = (DefaultTableModel) frame.table.getModel();
			int rowCount = model.getRowCount();
			// Remove rows one by one from the end of the table
			for (int i = rowCount - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			if (billRec == null || billRec.size() == 0) {
				JOptionPane.showMessageDialog(frame, "No bill found. Please verify the bill number again!");
				// Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				return;
			}
			noofrow = 0;
			int tot = 0;
			for (BillRecord rec : billRec) {
				model.addRow(new Object[] { rec.getSerialNo(), rec.getDescription(), rec.getUnit(), rec.getPrice(),
						rec.getLineTotal() });
				tot += rec.getLineTotal();
				noofrow++;
			}
			model.addRow(new Object[] { "", "", "", "", "" });
			model.addRow(new Object[] { "Total", "", "", "", tot });
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
