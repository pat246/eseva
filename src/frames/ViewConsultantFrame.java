package frames;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import database.Consultant;

public class ViewConsultantFrame extends JFrame {

    private JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewConsultantFrame frame = new ViewConsultantFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * 
     * @throws SQLException
     */
    public ViewConsultantFrame() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 898, 392);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        table = new JTable();
        table.setRowMargin(2);
        table.setRowHeight(20);
        List<Consultant> allConsu = Consultant.getAllConsultant();
        String[][] all = new String[allConsu.size()][6];
        for (int i = 0; i < allConsu.size(); i++) {
            Consultant consu = allConsu.get(i);
            all[i][0] = consu.getId() + "";
            all[i][1] = consu.getFullName();
            all[i][2] = consu.getEmail();
            all[i][3] = consu.getAddr();
            all[i][4] = consu.getContactNumbers();
            all[i][5] = consu.getComapny_name();
        }
        table.setModel(new DefaultTableModel(
            all,
            new String[] {
                "Sr. No", "Name", "Email", "Address", "Contacts", "Company name"
            }
        ) {
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(44);
        table.getColumnModel().getColumn(1).setPreferredWidth(168);
        table.getColumnModel().getColumn(2).setPreferredWidth(162);
        table.getColumnModel().getColumn(3).setPreferredWidth(352);
        table.getColumnModel().getColumn(4).setPreferredWidth(352);
        table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        table.setBounds(30, 12, 850, 200);
        contentPane.add(table);
    }

    @Override
    protected void processEvent(AWTEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.dispose();
            return;
        }
        super.processEvent(e);
    }
}
