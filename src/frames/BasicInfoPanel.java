package frames;

import handlers.ComboBoxHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.StyleConstants;

import database.Company;

public class BasicInfoPanel extends JPanel {

    public static JComboBox m_comboBox;
    public JLabel           nameText;
    public JLabel           uidText;
    public JLabel           passText;
    public JLabel           emailText;

    public BasicInfoPanel() {
        super(new BorderLayout());
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(900, 700));
        JLabel compName = new JLabel("Select Company");
        addComponent(compName, 5, 5, 170, 18);
        JComboBox companyList = new JComboBox(Company.compList);
        addComponent(companyList, 150, 5, 200, 18);
        if (Company.compList.size() > 0) {
            companyList.setSelectedIndex(-1);
        }
        ComboBoxHandler comboHandler = new ComboBoxHandler();
        companyList.addActionListener(comboHandler);
        comboHandler.m_basicPanel = this;
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
        Font f = new Font("myfont", StyleConstants.ALIGN_CENTER, 25);
        nameText = new JLabel("");
        nameText.setFont(f);
        JLabel uid = new JLabel("User Id");
        uidText = new JLabel("");
        uidText.setFont(f);
        JLabel pass = new JLabel("Password");
        passText = new JLabel("");
        passText.setFont(f);
        JLabel email = new JLabel("Email");
        emailText = new JLabel("");
        emailText.setFont(f);

        addComponent(name, 100, 110, 280, 18);
        addComponent(nameText, 235, 110 - 5, 400, 30);
        addComponent(copyUserIdButton, 0, 170, 70, 18);
        addComponent(uid, 100, 170, 280, 18);
        addComponent(uidText, 235, 170 - 5, 400, 30);
        addComponent(copyPasswordIdButton, 0, 230, 70, 18);
        addComponent(pass, 100, 230, 280, 18);
        addComponent(passText, 235, 230 - 5, 400, 30);
        addComponent(copyEmaildButton, 0, 290, 70, 18);
        addComponent(email, 100, 290, 280, 18);
        addComponent(emailText, 235, 290 - 5, 400, 30);

        addComponent(new JLabel(), 100, 400, 280, 30);// hack to add hidden
                                                      // component

        editCompany.addActionListener(comboHandler);
        shortcut.addActionListener(comboHandler);
        fxshortcut.addActionListener(comboHandler);
        copyUserIdButton.addActionListener(comboHandler);
        copyPasswordIdButton.addActionListener(comboHandler);
        copyEmaildButton.addActionListener(comboHandler);
    }

    private void addComponent(Component c, int x, int y, int width, int height) {
        c.setBounds(x + 50, y + 100, width, height);
        this.add(c);
    }
}
