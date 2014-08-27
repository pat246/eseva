package frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import components.CompanyDataComponents;

@SuppressWarnings("serial")
public class DetailedInfoPanel extends JPanel {

    private JTable dataTable;

    public DetailedInfoPanel() {
        setLayout(new BorderLayout());
        this.addCompanyComponentLabels();
    }

    @SuppressWarnings("serial")
    public void addCompanyComponentLabels() {
        dataTable = new JTable(CompanyDataComponents.values().length, 2) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        int row = 0;
        for (CompanyDataComponents data : CompanyDataComponents.values()) {
            dataTable.setValueAt(data.label, row, 0);
            row++;
        }
        dataTable.getColumn("A").setMaxWidth(200);
        dataTable.getColumn("A").setMinWidth(200);
        dataTable.setRowHeight(20);
        Font font = new Font(Font.SERIF, Font.PLAIN, 14);
        dataTable.setFont(font);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        this.addComponent(scrollPane, 0, 0, 800, 120);
        addEmptyComponent();
    }

    public void addComponent(Component c, int x, int y, int width, int height) {
        c.setBounds(x, y, width, height);
        add(c);
    }

    public void addEmptyComponent() {
        addComponent(new JLabel(""), 0, 0, 10, 10);
    }

    public void fillCompanyInfo() {
        if (MenuFrame.BASIC_PANEL.getSelectedCompany() != null) {
            CompanyDataComponents.company = MenuFrame.BASIC_PANEL.getSelectedCompany();
            int row = 0;
            for (CompanyDataComponents comp : CompanyDataComponents.values())
                dataTable.setValueAt(comp.getValue(), row++, 1);
        }
    }
}
