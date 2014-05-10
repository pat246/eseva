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

import database.Company;
import frames.AddCompFrame;
import frames.BasicInfoPanel;
import frames.EditCompFrame;

public class ComboBoxHandler implements ActionListener, ItemListener {

    AddCompFrame                 mFrame;
    public static BasicInfoPanel m_basicPanel;

    public ComboBoxHandler() {
    }

    @Override
    public void itemStateChanged(ItemEvent event) {

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        System.out.println("action = " + action);
        JComboBox companylist = BasicInfoPanel.m_comboBox;
        String selectedCompany = (String) companylist.getSelectedItem();
        Company companyCred = Company.companies.get(selectedCompany);
        if (action.equalsIgnoreCase("Edit")) {
            EditCompFrame editCompFrame = new EditCompFrame();
            editCompFrame.setLocation(100, 200);
            editCompFrame.setSize(new Dimension(400, 400));
            editCompFrame.setVisible(true);
            editCompFrame.textCompName.setText(companyCred.getCompanyName());
            editCompFrame.textPassword.setText(companyCred.getCompanyPassword());
            editCompFrame.textUserId.setText(companyCred.getCompanyUserId());
            editCompFrame.textEmail.setText(companyCred.getCompanyEmail());
            editCompFrame.setCompnayId((companyCred.id));

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
        }
    }
}
