package handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import database.Company;
import database.DBConnectionManager;
import frames.AddCompFrame;
import frames.BasicInfoPanel;
import frames.EditCompFrame;

public class AddCompFrameHandler implements ActionListener, ItemListener {

    AddCompFrame          m_AddCompFrame;
    EditCompFrame         m_EditCompFrame;

    private static String UPDATE_QUERY = "UPDATE credentials SET company_name=?,user_id=?,password=?, email=?, mobile=? where id = ?";

    public AddCompFrameHandler(AddCompFrame frame) {
        this.m_AddCompFrame = frame;
    }

    public AddCompFrameHandler(EditCompFrame editCompFrame) {
        this.m_EditCompFrame = editCompFrame;
    }

    @Override
    public void itemStateChanged(ItemEvent event) {

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        // Add new company action using addcompfram
        if (action.equalsIgnoreCase("Add")) {
            String name = this.m_AddCompFrame.textCompName.getText();
            String uid = this.m_AddCompFrame.textUserId.getText();
            String pass = this.m_AddCompFrame.textPassword.getText();
            String email = this.m_AddCompFrame.textEmailId.getText();
            String mob = this.m_AddCompFrame.textMobile.getText();

            String INSERT_QUERY = "INSERT INTO credentials (company_name,user_id,password, email, mobile) VALUES (?,?,?,?,?)";
            Connection conn = DBConnectionManager.getMysqlConn();
            if (conn != null) {
                PreparedStatement pstmt = null;
                int cnt = 1;
                try {
                    pstmt = conn.prepareStatement(INSERT_QUERY);
                    pstmt.setString(cnt++, name);
                    pstmt.setString(cnt++, uid);
                    pstmt.setString(cnt++, pass);
                    pstmt.setString(cnt++, email);
                    pstmt.setString(cnt++, mob);
                    int rs = pstmt.executeUpdate();

                    if (rs > 0) {
                        JOptionPane.showMessageDialog(m_AddCompFrame, "Company data added Successfully");
                        Company.initialize();
                        BasicInfoPanel.m_comboBox.updateUI();
                        m_AddCompFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(m_AddCompFrame, "Failed ot add company data");
                        BasicInfoPanel.m_comboBox.updateUI();
                        m_AddCompFrame.dispose();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        // Update company action using editcompframe
        else if (action.equalsIgnoreCase("Save")) {

            String name = this.m_EditCompFrame.textCompName.getText();
            String uid = this.m_EditCompFrame.textUserId.getText();
            String pass = this.m_EditCompFrame.textPassword.getText();
            String email = this.m_EditCompFrame.textEmail.getText();
            String mob = this.m_EditCompFrame.textMobile.getText();
            int companyId = m_EditCompFrame.getCompanyId();
            if (companyId == -1) {
                try {
                    throw new Exception("Error occured while saving Company details " + name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // String UPDATE_QUERY = "UPDATE credentials SET company_name='" +
            // name + "',user_id='" + uid + "',password='"
            // + pass + "', email='" + email + "' where id =" + companyId;
            Connection conn = DBConnectionManager.getMysqlConn();
            if (conn != null) {
                PreparedStatement pstmt = null;
                int cnt = 1;
                try {
                    pstmt = conn.prepareStatement(UPDATE_QUERY);
                    pstmt.setString(cnt++, name);
                    pstmt.setString(cnt++, uid);
                    pstmt.setString(cnt++, pass);
                    pstmt.setString(cnt++, email);
                    pstmt.setString(cnt++, mob);
                    pstmt.setInt(cnt++, companyId);
                    int rs = pstmt.executeUpdate();

                    if (rs > 0) {
                        JOptionPane.showMessageDialog(m_EditCompFrame, "Company data updated Successfully");
                    } else {
                        JOptionPane.showMessageDialog(m_EditCompFrame, "Failed to update company");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Company.initialize();
                        if (ComboBoxHandler.m_basicPanel != null) {
                            ComboBoxHandler.m_basicPanel.nameText.setText(name);
                            ComboBoxHandler.m_basicPanel.uidText.setText(uid);
                            ComboBoxHandler.m_basicPanel.passText.setText(pass);
                            ComboBoxHandler.m_basicPanel.emailText.setText(email);
                        }
                        m_EditCompFrame.dispose();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        } else if (action.equalsIgnoreCase("delete")) {
            int companyId = m_EditCompFrame.getCompanyId();
            if (companyId == -1) {
                try {
                    throw new Exception("Error occured while deleting Company details ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String DELETE_QUERY = "delete from credentials  where id =" + companyId;
            Connection conn = DBConnectionManager.getMysqlConn();
            if (conn != null) {
                Statement stmt;
                try {
                    stmt = conn.createStatement();
                    int rs = stmt.executeUpdate(DELETE_QUERY);

                    if (rs > 0) {
                        JOptionPane.showMessageDialog(m_AddCompFrame, "Company deleted Successfully");
                        Company.initialize();
                        m_EditCompFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(m_AddCompFrame, "Failed to delete the company!");
                        m_EditCompFrame.dispose();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
