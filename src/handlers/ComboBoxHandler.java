package handlers;

import database.Company;
import frames.AddCompFrame;
import frames.BasicInfoPanel;
import frames.EditCompFrame;
import frames.MenuFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import scrapers.EsevaScraper;
import utils.DialogUtils;
import utils.ThreadSafeUtil;

public class ComboBoxHandler implements ActionListener, ItemListener {
	AddCompFrame mFrame;
	public static BasicInfoPanel m_basicPanel;

	public ComboBoxHandler() {
	}

	public void itemStateChanged(ItemEvent event) {
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		System.out.println("action = " + action);
		JComboBox companylist = BasicInfoPanel.m_comboBox;
		String selectedCompany = (String) companylist.getSelectedItem();
		Company companyCred = (Company) Company.companies.get(selectedCompany);
		MenuFrame.BASIC_PANEL.setSelectedCompany(companyCred);
		if (action.equalsIgnoreCase("Edit")) {
			if (MenuFrame.BASIC_PANEL.getSelectedCompany() == null) {
				JOptionPane.showMessageDialog(this.mFrame, "Please select company first");
				return;
			}
			EditCompFrame editCompFrame = new EditCompFrame();
			editCompFrame.setLocation(100, 200);
			editCompFrame.setSize(new Dimension(400, 400));
			editCompFrame.setVisible(true);
			editCompFrame.textCompName.setText(companyCred.getCompanyName());
			editCompFrame.textPassword.setText(companyCred.getCompanyPassword());
			editCompFrame.textUserId.setText(companyCred.getCompanyUserId());
			editCompFrame.textEmail.setText(companyCred.getCompanyEmail());
			editCompFrame.textMobile.setText(companyCred.getMobile());
			editCompFrame.textContactPerson.setText(companyCred.getContactPerson());
			editCompFrame.textAddr.setText(companyCred.getAddr());
			editCompFrame.setCompnayId(companyCred.id);
		} else if (action.equalsIgnoreCase("IE")) {
			if (MenuFrame.BASIC_PANEL.getSelectedCompany() == null) {
				JOptionPane.showMessageDialog(this.mFrame, "Please select company first");
				return;
			}
			EcrNavigation ecrNav = new EcrNavigation();
			ecrNav.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
		} else if (action.equalsIgnoreCase("Firefox")) {
			if (MenuFrame.BASIC_PANEL.getSelectedCompany() == null) {
				JOptionPane.showMessageDialog(this.mFrame, "Please select company first");
				return;
			}
			EcrNavigation ecrNav = new EcrNavigation();
			ecrNav.m_browser = "FX";
			ecrNav.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
		} else if (action.equalsIgnoreCase("Chrome")) {
			if (MenuFrame.BASIC_PANEL.getSelectedCompany() == null) {
				JOptionPane.showMessageDialog(this.mFrame, "Please select company first");
				return;
			}
			EcrNavigation ecrNav = new EcrNavigation();
			ecrNav.m_browser = "CHROME";
			ecrNav.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
		} else if (action.equals("comboBoxChanged")) {
			if (m_basicPanel != null) {
				m_basicPanel.nameText.setText(companyCred.getCompanyName());
				m_basicPanel.uidText.setText(companyCred.getCompanyUserId());
				m_basicPanel.passText.setText(companyCred.getCompanyPassword());
				String astr = "";
				for (int i = 0; i < companyCred.getCompanyPassword().length(); i++) {
					astr = astr + "*";
				}
				m_basicPanel.astericPass.setText(astr);
				m_basicPanel.emailText.setText(companyCred.getCompanyEmail());
				m_basicPanel.isCompanySelected = true;
				setLastPasswordResetInfo(companyCred);
			}
		} else if ("CopyUID".equals(action)) {
			String uid = m_basicPanel.uidText.getText();
			StringSelection stringSelection = new StringSelection(uid);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
		} else if ("CopyPassword".equals(action)) {
			String uid = m_basicPanel.passText.getText();
			StringSelection stringSelection = new StringSelection(uid);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
		} else if ("copyEmail".equals(action)) {
			String uid = m_basicPanel.emailText.getText();
			StringSelection stringSelection = new StringSelection(uid);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
		} else if ("Check".equals(action)) {
			UpdateLprt t = new UpdateLprt();
			t.setCompanyCred(companyCred);
			t.setM_basicPanel(m_basicPanel);
			t.start();
			DialogUtils.WAIT_DIALOG.setLocationRelativeTo(m_basicPanel.getParentFrame());
			DialogUtils.WAIT_DIALOG.setModal(true);
			DialogUtils.waitLabel.setText(DialogUtils.LPRD_MSG);
			DialogUtils.WAIT_DIALOG.setVisible(true);

			DialogUtils.WAIT_DIALOG.setVisible(false);
			if (t.lprd != null) {
				JOptionPane.showMessageDialog(m_basicPanel.getParentFrame(), "Success");
			} else {
				JOptionPane.showMessageDialog(m_basicPanel.getParentFrame(), "Failed to capture");
			}
		}
	}

	private void setLastPasswordResetInfo(Company company) {
		if (company.getLastPassResetDate() != null) {
			m_basicPanel.hasLastPasswordResetDate = true;
			m_basicPanel.lastPasswordResetDate.setText(company.getLprdForDisplay());
			m_basicPanel.remove(BasicInfoPanel.UPDATE_LPRD);
			m_basicPanel.addComponent(m_basicPanel.lastPasswordResetDate, 320, 290 + BasicInfoPanel.Y_POSITION_DIFF,
					180, 18);
			m_basicPanel.addEmptyComponent();
		} else {
			m_basicPanel.remove(m_basicPanel.lastPasswordResetDate);
			m_basicPanel.addComponent(BasicInfoPanel.UPDATE_LPRD, 320, 290 + BasicInfoPanel.Y_POSITION_DIFF, 80, 18);
			m_basicPanel.addEmptyComponent();
		}
		if (company.getLastPassResetDate() == null) {
			return;
		}
		Calendar now = Calendar.getInstance();
		Calendar tempTime = Calendar.getInstance();
		tempTime.setTime(company.getLastPassResetDate());
		tempTime.add(5, Company.PASSWORD_EXPIRY_DAYS_LIMIT);
		int daysRemaining = (int) ((now.getTimeInMillis() - tempTime.getTimeInMillis()) / Company.ONE_DAY_IN_MILISECONDS);
		if (daysRemaining > 0) {
			JOptionPane.showMessageDialog(this.mFrame,
					"Password has expired! Please login to esewa site and reset the password");
		} else if (Math.abs(daysRemaining) < Company.NO_OF_DAYS_BEFORE_TO_CHECK_PASSWORD) {
			String message = "Password will expire within next " + Company.NO_OF_DAYS_BEFORE_TO_CHECK_PASSWORD
					+ " days. Do you want to reset password automatically?";
			int selectedOption = JOptionPane.showConfirmDialog(this.mFrame, message);
			if (selectedOption == 0) {
				MyMenuHandler.performResetPassword(company);
			}
		}
	}
}

class UpdateLprt extends Thread {
	Company companyCred;
	BasicInfoPanel m_basicPanel;
	Date lprd;

	public Date getLprd() {
		return lprd;
	}

	public void setLprd(Date lprd) {
		this.lprd = lprd;
	}

	public Company getCompanyCred() {
		return companyCred;
	}

	public void setCompanyCred(Company companyCred) {
		this.companyCred = companyCred;
	}

	public BasicInfoPanel getM_basicPanel() {
		return m_basicPanel;
	}

	public void setM_basicPanel(BasicInfoPanel m_basicPanel) {
		this.m_basicPanel = m_basicPanel;
	}

	public void setCreds() {
	}

	public void run() {
		EsevaScraper scraper = new EsevaScraper();
		try {
			Thread.sleep(2000L);
			scraper.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
			lprd = scraper.getLprd();
			if (lprd != null) {
				companyCred.updateLprd(lprd);
				String lprdStr = ThreadSafeUtil.getDateTime12HrsWithoutSecondsDotFormat(false, false).format(lprd);
				m_basicPanel.lastPasswordResetDate.setText(lprdStr);
				m_basicPanel.remove(BasicInfoPanel.UPDATE_LPRD);
				m_basicPanel.addComponent(m_basicPanel.lastPasswordResetDate, 320,
						290 + BasicInfoPanel.Y_POSITION_DIFF, 180, 18);
				m_basicPanel.addEmptyComponent();
				Company.initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scraper.doLogout();
			DialogUtils.WAIT_DIALOG.setVisible(false);
		}

	}
}
