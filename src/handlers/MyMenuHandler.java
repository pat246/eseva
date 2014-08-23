package handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import scrapers.EsevaResetPasswordScraper;
import utils.DialogUtils;
import database.Company;
import frames.AddCompFrame;
import frames.BasicInfoPanel;
import frames.CompanyListFrame;
import frames.MenuFrame;

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
        } else if ("Reset Password".equalsIgnoreCase(action)) {
            Company companySelected = MenuFrame.BASIC_PANEL.getSelectedCompany();
            if (companySelected == null) {
                JOptionPane.showMessageDialog(MenuFrame.BASIC_PANEL.getParentFrame(), "Please select company first");
                return;
            }
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

        } else if ((!action.equalsIgnoreCase("New Item")) && (action.endsWith("Print Companies"))) {
            CompanyListFrame CompanyListFrameyinvinfo = new CompanyListFrame();
            CompanyListFrameyinvinfo.setSize(new Dimension(1000, 1000));
            PrintableDocument.printComponent(CompanyListFrameyinvinfo.m_compListTab);
        }
    }
}
