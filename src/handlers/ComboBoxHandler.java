package handlers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import utils.DialogUtils;
import utils.ThreadSafeUtil;
import database.Company;
import frames.AddCompFrame;
import frames.BasicInfoPanel;
import frames.EditCompFrame;

public class ComboBoxHandler implements ActionListener, ItemListener {
    AddCompFrame                 mFrame;
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
        if (action.equalsIgnoreCase("Edit")) {
            EditCompFrame editCompFrame = new EditCompFrame();
            editCompFrame.setLocation(100, 200);
            editCompFrame.setSize(new Dimension(400, 400));
            editCompFrame.setVisible(true);
            editCompFrame.textCompName.setText(companyCred.getCompanyName());
            editCompFrame.textPassword.setText(companyCred.getCompanyPassword());
            editCompFrame.textUserId.setText(companyCred.getCompanyUserId());
            editCompFrame.textEmail.setText(companyCred.getCompanyEmail());
            editCompFrame.setCompnayId(companyCred.id);
        } else if (action.equalsIgnoreCase("Open ESEVA")) {
            EcrNavigation ecrNav = new EcrNavigation();
            ecrNav.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
        } else if (action.equalsIgnoreCase("Open ESEVA FX")) {
            EcrNavigation ecrNav = new EcrNavigation();
            ecrNav.m_browser = "FX";
            ecrNav.doLogin(companyCred.getCompanyUserId(), companyCred.getCompanyPassword());
        } else if (action.equals("comboBoxChanged")) {
            if (m_basicPanel != null) {
                m_basicPanel.nameText.setText(companyCred.getCompanyName());
                m_basicPanel.uidText.setText(companyCred.getCompanyUserId());
                m_basicPanel.passText.setText(companyCred.getCompanyPassword());
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
            String dateStr = ThreadSafeUtil.getDateTime12HrsWithoutSecondsDotFormat(false, false).format(
                    company.getLastPassResetDate());
            m_basicPanel.hasLastPasswordResetDate = true;
            m_basicPanel.lastPasswordResetDate.setText(dateStr);
            m_basicPanel.remove(BasicInfoPanel.UPDATE_LPRD);
            m_basicPanel.addComponent(m_basicPanel.lastPasswordResetDate, 320, 290 + BasicInfoPanel.Y_POSITION_DIFF,
                    180, 18);
            m_basicPanel.addEmptyComponent();
        } else {
            m_basicPanel.remove(m_basicPanel.lastPasswordResetDate);
            m_basicPanel.addComponent(BasicInfoPanel.UPDATE_LPRD, 320, 290 + BasicInfoPanel.Y_POSITION_DIFF, 80, 18);
            m_basicPanel.addEmptyComponent();
        }
    }
}
