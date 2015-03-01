package frames;

import handlers.BillGenerator;
import handlers.ViewBillHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class ViewBillsFrame extends JFrame {
	private JTextField textField;
	public JTable table;
	public JLabel lblBlk;
	public static ViewBillsFrame VIEW_BILL_FRAME;

	public ViewBillsFrame() throws HeadlessException {
		super("View Bills");
		MenuBars.MENU_FRAME.setEnabled(false);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				MenuBars.MENU_FRAME.setEnabled(true);
			}
		});

		String[] columns = { "Sr. No", "Description", "Units", "Price ", "Line total" };

		Object[][] data = {

		};

		table = new JTable(new DefaultTableModel(data, columns));
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
		table.setBorder(border);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		getContentPane().setLayout(
				new MigLayout("", "[154px][39px][104px][40px][109px][24px][251px]", "[30px][28px][][][368px]"));

		JLabel lblHeading = new JLabel("Bill");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		this.getContentPane().add(lblHeading, "cell 0 3,growx,aligny top");

		JLabel lblCompany = new JLabel("Company :");
		getContentPane().add(lblCompany, "cell 2 3");

		lblBlk = new JLabel("blk");
		getContentPane().add(lblBlk, "cell 4 3");
		this.getContentPane().add(scrollPane, "cell 0 4 7 1,grow");

		JLabel lblEnterBillNumber = new JLabel("Enter Bill Number: ");
		getContentPane().add(lblEnterBillNumber, "cell 0 0,alignx right,aligny center");

		textField = new JTextField();
		getContentPane().add(textField, "cell 2 0,growx,aligny top");
		textField.setColumns(10);

		JButton btnView = new JButton("View");
		btnView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ViewBillHandler.viewBill();
			}
		});
		getContentPane().add(btnView, "cell 4 0,growx,aligny bottom");

		JButton btnDownload = new JButton("Download");
		btnDownload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					List<String[]> value = ViewBillHandler.getBillRecordForViewBillFrame();
					if (value != null && value.size() > 0) {
						BillGenerator.generateBill(ViewBillsFrame.VIEW_BILL_FRAME, value);
						JOptionPane.showMessageDialog(ViewBillsFrame.VIEW_BILL_FRAME, "Bill generated successfully");
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(btnDownload, "cell 6 0,alignx left,aligny bottom");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(721, 418);
		this.setVisible(true);
		this.VIEW_BILL_FRAME = this;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

}
