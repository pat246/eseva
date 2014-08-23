package frames;

import handlers.ComboBoxHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.Company;

@SuppressWarnings("serial")
public class BasicInfoPanel extends JPanel {
    public static JComboBox m_comboBox;
    public JLabel           nameText;
    public JLabel           uidText;
    public JLabel           passText;
    public JLabel           lastPasswordResetDate;
    public JLabel           emailText;
    public static JButton   UPDATE_LPRD              = new JButton("Check");
    public static int       Y_POSITION_DIFF          = 60;
    public boolean          hasLastPasswordResetDate = false;
    public boolean          isCompanySelected        = false;
    public JFrame           parentFrame;
    private Company         selectedCompany;

    public JFrame getParentFrame() {
        return this.parentFrame;
    }

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public BasicInfoPanel() {
        super(new BorderLayout());
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(1300, 700));
        JLabel selectCompany = new JLabel("Select Company");
        addComponent(selectCompany, 5, 5, 170, 18);
        JComboBox companyList = new JComboBox(Company.compList);
        addComponent(companyList, 150, 5, 200, 18);
        if (Company.compList.size() > 0) {
            companyList.setSelectedIndex(-1);
        }
        ComboBoxHandler comboHandler = new ComboBoxHandler();
        companyList.addActionListener(comboHandler);
        ComboBoxHandler.m_basicPanel = this;
        m_comboBox = companyList;
        JButton editCompany = new JButton("Edit");
        JButton copyUserIdButton = new JButton("Copy");
        JButton copyPasswordIdButton = new JButton("Copy");
        JButton copyEmaildButton = new JButton("Copy");
        copyUserIdButton.setActionCommand("CopyUID");
        copyPasswordIdButton.setActionCommand("CopyPassword");
        copyEmaildButton.setActionCommand("copyEmail");
        JButton shortcut = new JButton("Open ESEVA");
        JButton fxshortcut = new JButton("Open ESEVA FX");
        addComponent(editCompany, 370, 5, 120, 18);
        addComponent(shortcut, 500, 5, 150, 18);
        addComponent(fxshortcut, 650, 5, 150, 18);
        JLabel name = new JLabel("Company Name");
        JLabel lpro = new JLabel("Last password reset on");
        Font f = new Font("myfont", 1, 25);
        this.nameText = new JLabel("");
        this.nameText.setFont(f);
        JLabel uid = new JLabel("User Id");
        this.uidText = new JLabel("");
        this.uidText.setFont(f);
        JLabel pass = new JLabel("Password");
        this.passText = new JLabel("");
        this.passText.setFont(f);
        JLabel email = new JLabel("Email");
        this.emailText = new JLabel("");
        this.emailText.setFont(f);
        this.lastPasswordResetDate = new JLabel("");

        addComponent(name, 100, 110, 280, 18);
        addComponent(this.nameText, 235, 105, 400, 30);
        addComponent(copyUserIdButton, 0, 170, 70, 18);
        addComponent(uid, 100, 170, 280, 18);
        addComponent(this.uidText, 235, 165, 400, 30);
        addComponent(copyPasswordIdButton, 0, 230, 70, 18);
        addComponent(pass, 100, 230, 280, 18);
        addComponent(this.passText, 235, 225, 400, 30);
        addComponent(copyEmaildButton, 0, 290, 70, 18);
        addComponent(email, 100, 290, 280, 18);
        addComponent(this.emailText, 235, 285, 400, 30);
        addComponent(lpro, 100, 290 + Y_POSITION_DIFF, 280, 18);
        addEmptyComponent();

        editCompany.addActionListener(comboHandler);
        shortcut.addActionListener(comboHandler);
        fxshortcut.addActionListener(comboHandler);
        copyUserIdButton.addActionListener(comboHandler);
        copyPasswordIdButton.addActionListener(comboHandler);
        copyEmaildButton.addActionListener(comboHandler);
        UPDATE_LPRD.addActionListener(comboHandler);
    }

    public void addComponent(Component c, int x, int y, int width, int height) {
        c.setBounds(x + 100, y + 50, width, height);
        add(c);
    }

    public void addEmptyComponent() {
        addComponent(new JLabel(""), 500, 500, 280, 30);
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    /* make changes to display ui if any data is changed at backend */
    public static void modifyUIData(Company selectedCompany) {
        MenuFrame.BASIC_PANEL.passText.setText(selectedCompany.getPass());
        MenuFrame.BASIC_PANEL.lastPasswordResetDate.setText(selectedCompany.getLprdForDisplay());
    }
}
