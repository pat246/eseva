package handlers;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import scrapers.EsevaResetPasswordScraper;
import utils.DialogUtils;
import database.Company;
import database.Consultant;
import frames.AddCompFrame;
import frames.AddConsultantFrame;
import frames.BasicInfoPanel;
import frames.BillGeneratorUIFram;
import frames.CompanyListFrame;
import frames.MenuFrame;
import frames.ViewConsultantFrame;

public class MyMenuHandler implements ActionListener, ItemListener {
	MenuFrame mFrame;

	public MyMenuHandler(MenuFrame frame) {
		this.mFrame = frame;
	}

	public void itemStateChanged(ItemEvent event) {
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action.equalsIgnoreCase("Add Company")) {
			AddCompFrame frame = new AddCompFrame();
			frame.setLocation(100, 200);
			frame.setSize(new Dimension(400, 400));
			frame.setVisible(true);
		} else if (action.equalsIgnoreCase("Exit")) {
			this.mFrame.dispose();
			System.exit(0);
		} else if ("Generate Bill".equalsIgnoreCase(action)) {
			Company companySelected = MenuFrame.BASIC_PANEL.getSelectedCompany();
			if (companySelected == null) {
				JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(), "Please select company first");
				return;
			}
			try {
				if (Consultant.getAllConsultant().size() == 0) {
					JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(), "Please add consultant first");
					return;
				}
			} catch (HeadlessException | SQLException e1) {
				e1.printStackTrace();
			}
			try {
				BillGeneratorUIFram window = new BillGeneratorUIFram();
				window.frmGenerateBill.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("Reset Password".equalsIgnoreCase(action)) {
			int proceed = JOptionPane.showConfirmDialog(mFrame, "Are you sure want to reset password?");
			if (JOptionPane.YES_OPTION != proceed) {
				return;
			}
			Company companySelected = MenuFrame.BASIC_PANEL.getSelectedCompany();
			if (companySelected == null) {
				JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(), "Please select company first");
				return;
			}
			performResetPassword(companySelected);

		} else if ((!action.equalsIgnoreCase("New Item")) && (action.endsWith("Print Companies"))) {
			CompanyListFrame CompanyListFrameyinvinfo = new CompanyListFrame();
			CompanyListFrameyinvinfo.setSize(new Dimension(1000, 1000));
			PrintableDocument.printComponent(CompanyListFrameyinvinfo.m_compListTab);
		} else if ("Add Cosultant".equalsIgnoreCase(action)) {
			AddConsultantFrame frame = new AddConsultantFrame();
			frame.setVisible(true);
		} else if ("View Cosultants".equalsIgnoreCase(action)) {
			ViewConsultantFrame frame;
			try {
				frame = new ViewConsultantFrame();
				frame.setVisible(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static void performResetPassword(Company companySelected) {

		DialogUtils.WAIT_DIALOG.setLocationRelativeTo(MenuFrame.BASIC_PANEL.getParentFrame());
		DialogUtils.waitLabel.setText(DialogUtils.RESETPW_MSG);
		DialogUtils.WAIT_DIALOG.setModal(true);
		String newPassword = companySelected.generateNewPassword();
		EsevaResetPasswordScraper scraper = new EsevaResetPasswordScraper(companySelected.getCompanyUserId(),
				companySelected.getCompanyPassword(), newPassword);
		scraper.start();
		DialogUtils.WAIT_DIALOG.setVisible(true);
		if (scraper.isSuccess()) {
			companySelected.storeNewPassword(newPassword);
			BasicInfoPanel.modifyUIData(companySelected);
			JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(),
					"Password reset successfully at esewa site");
		} else {
			JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(), "Password reset failed. "
					+ scraper.error);
		}
	}
}
