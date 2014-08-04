package frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import database.Company;

public class CompanyListFrame extends JFrame {

    public JTable m_compListTab;

    public CompanyListFrame() {
        String[][] data = new String[1000][7];
        for (int i = 0; i < Company.compList.size(); i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = Company.compList.get(i);
            data[i][2] = "";
            data[i][3] = "";
            data[i][4] = "";
            data[i][5] = "";
            data[i][6] = "";
        }
        String[] fields = new String[7];
        fields[0] = "Sr no.";
        fields[1] = "Company Name";
        fields[2] = "Account 1";
        fields[3] = "Account 2";
        fields[4] = "Account 10";
        fields[5] = "Account 21";
        fields[6] = "Account 22";
        JPanel panel = new JPanel();
        JTable tab = new JTable(data, fields);
        this.add(tab);
        // addComponent(tab, 5, 30, 700, 500);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JTableHeader header = tab.getTableHeader();
        JScrollPane pane = new JScrollPane(tab);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        panel.add(pane);
        add(panel);
        setSize(500, 150);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        tab.getColumnModel().getColumn(1).setPreferredWidth(130);
        tab.getColumnModel().getColumn(0).setPreferredWidth(30);

        m_compListTab = tab;
    }
}
