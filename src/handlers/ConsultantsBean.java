package handlers;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.Consultant;
import frames.AddConsultantFrame;

public class ConsultantsBean {

	public static void addConsultant(AddConsultantFrame thisFrame) {

		String fname = thisFrame.textField.getText();
		String lname = thisFrame.textField_1.getText();
		String addr = thisFrame.textField_2.getText();
		String addr1 = thisFrame.textField_5.getText();
		String addr2 = thisFrame.textField_6.getText();
		String cname = thisFrame.textField_7.getText();
		String contacts = thisFrame.textField_3.getText();
		String email = thisFrame.textField_4.getText();
		String pan = thisFrame.textField_8.getText();
		Consultant consu = new Consultant(fname, lname, addr, contacts, cname);
		consu.setEmail(email);
		consu.setAddr1(addr1);
		consu.setAddr2(addr2);
		consu.setPan(pan);
		boolean success = false;
		try {
			success = consu.addConsultant();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = "Consultant added successfully";
		if (!success) {
			message = "Failed to Add";
		}
		JOptionPane.showMessageDialog(thisFrame, message);
		if (success) {
			thisFrame.dispose();
		}
	}
}
