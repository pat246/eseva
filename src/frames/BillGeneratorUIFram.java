package frames;

import handlers.BillGenerator;
import handlers.ConsulatantComboBoxHandler;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;

import database.Consultant;
import java.awt.Checkbox;

public class BillGeneratorUIFram {

	public static int POINTER = 0;
	public static JTextField[][] textFieldsArr;
	public static JLabel[] labelArr;
	private static JTextField GRAND_TOTAL_TEXT;
	public static JComboBox CONSULTANT_COMBO_BOX;

	public JFrame frmGenerateBill;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	public static Checkbox checkbox;
	public static Checkbox checkbox_1;
	public static JTextField textField_21;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BillGeneratorUIFram window = new BillGeneratorUIFram();
					window.frmGenerateBill.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws SQLException
	 */
	public BillGeneratorUIFram() throws SQLException {
		initialize();
		initArray();
	}

	private void initArray() {
		JTextField[][] fields = { { textField_10, textField_15, textField_1, textField_2 },
				{ textField_11, textField_16, textField, textField_3 },
				{ textField_12, textField_17, textField_4, textField_5 },
				{ textField_13, textField_18, textField_6, textField_7 },
				{ textField_14, textField_19, textField_8, textField_9 } };
		JLabel[] labels = { label_1, label_2, label_3, label_4, label_5 };
		labelArr = labels;
		textFieldsArr = fields;
		BillGeneratorUIFram.GRAND_TOTAL_TEXT = textField_20;
		BillGeneratorUIFram.GRAND_TOTAL_TEXT.setText("0");
		for (int i = 0; i < textFieldsArr.length; i++) {
			FocusListener l = new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					JTextField field = (JTextField) e.getComponent();
					int rowIdx = Integer.parseInt(field.getName());
					String priceStr = BillGeneratorUIFram.textFieldsArr[rowIdx][2].getText();
					String unitStr = BillGeneratorUIFram.textFieldsArr[rowIdx][1].getText();
					int units = 0;
					int price = 0;
					try {
						units = Integer.parseInt(unitStr);
						price = Integer.parseInt(priceStr);
					} catch (Exception ex) {
						return;
					}
					if (StringUtils.isNotBlank(priceStr) && StringUtils.isNotBlank(unitStr)) {
						int total = units * price;
						BillGeneratorUIFram.textFieldsArr[rowIdx][3].setText(total + "");
						BillGeneratorUIFram.GRAND_TOTAL_TEXT.setText(BillGeneratorUIFram.calculateGrandTot() + "");
					}
				}
			};
			textFieldsArr[i][1].setName(i + "");
			textFieldsArr[i][2].setName(i + "");
			textFieldsArr[i][1].addFocusListener(l);
			textFieldsArr[i][2].addFocusListener(l);
		}
	}

	public static int calculateGrandTot() {
		int tot = 0;
		for (int i = 0; i < textFieldsArr.length; i++) {
			if (!textFieldsArr[i][0].isVisible()) {
				continue;
			}
			String priceStr = BillGeneratorUIFram.textFieldsArr[i][2].getText();
			String unitStr = BillGeneratorUIFram.textFieldsArr[i][1].getText();

			if (StringUtils.isNotBlank(priceStr) && StringUtils.isNotBlank(unitStr)) {
				tot += (Integer.parseInt(unitStr) * Integer.parseInt(priceStr));
			}
		}
		return tot;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {
		frmGenerateBill = new JFrame() {
			@Override
			protected void processEvent(AWTEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSING) {
					this.dispose();
					return;
				}
				super.processEvent(e);
			}
		};
		frmGenerateBill.setFont(UIManager.getFont("TitledBorder.font"));
		frmGenerateBill.setTitle("Generate Bill");
		frmGenerateBill.setIconImage(Toolkit.getDefaultToolkit().getImage(
				BillGeneratorUIFram.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmGenerateBill.setBounds(100, 100, 1061, 575);
		frmGenerateBill.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGenerateBill.getContentPane().setLayout(null);

		JLabel lblSrNo = new JLabel("Sr. No");
		lblSrNo.setFont(new Font("Serif", Font.BOLD, 13));
		lblSrNo.setBounds(24, 12, 57, 25);
		frmGenerateBill.getContentPane().add(lblSrNo);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Serif", Font.BOLD, 13));
		lblDescription.setBounds(99, 12, 99, 25);
		frmGenerateBill.getContentPane().add(lblDescription);

		JLabel lblLineTotal = new JLabel("Price");
		lblLineTotal.setFont(new Font("Serif", Font.BOLD, 13));
		lblLineTotal.setBounds(499, 12, 44, 25);
		frmGenerateBill.getContentPane().add(lblLineTotal);

		JLabel lblLineTotal_1 = new JLabel("Line Total");
		lblLineTotal_1.setFont(new Font("Serif", Font.BOLD, 13));
		lblLineTotal_1.setBounds(602, 12, 82, 25);
		frmGenerateBill.getContentPane().add(lblLineTotal_1);

		JSeparator separator = new JSeparator();
		separator.setBounds(376, 32, 1, 2);
		frmGenerateBill.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(365, 35, 1, 2);
		frmGenerateBill.getContentPane().add(separator_1);

		textField_1 = new JTextField();
		textField_1.setBounds(499, 56, 57, 27);
		frmGenerateBill.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(590, 56, 122, 27);
		frmGenerateBill.getContentPane().add(textField_2);

		JButton btnAddMore = new JButton("+ Add More");
		btnAddMore.addMouseListener(new MouseAdapter() {
			private Component button;

			@Override
			public void mouseClicked(MouseEvent e) {
				button = e.getComponent();
				if (POINTER == 3) {
					e.getComponent().setVisible(false);
				}
				addOrRemoveRow(true);
			}

			private void addOrRemoveRow(boolean add) {
				if (add) {
					POINTER++;
					JTextField[] row = BillGeneratorUIFram.textFieldsArr[POINTER];
					for (JTextField cell : row) {
						cell.setVisible(true);
					}
					labelArr[POINTER].setVisible(true);
				}
			}
		});
		btnAddMore.setSelectedIcon(new ImageIcon(BillGeneratorUIFram.class
				.getResource("/com/sun/java/swing/plaf/motif/icons/Error.gif")));
		btnAddMore.setToolTipText("Adds more entries");
		btnAddMore.setBounds(731, 49, 122, 27);
		frmGenerateBill.getContentPane().add(btnAddMore);

		JButton btnGenerateBill = new JButton("Generate Bill");
		btnGenerateBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BillGenerator.generateBill(frmGenerateBill, null);
					JOptionPane.showMessageDialog(frmGenerateBill, "Bill generated successfully");
					frmGenerateBill.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGenerateBill.setToolTipText("Generate Bill in pdf");
		btnGenerateBill.setBounds(99, 447, 141, 27);
		frmGenerateBill.getContentPane().add(btnGenerateBill);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmGenerateBill.dispose();
			}
		});
		btnCancel.setBounds(407, 447, 100, 27);
		frmGenerateBill.getContentPane().add(btnCancel);

		textField = new JTextField();
		textField.setVisible(false);
		textField.setColumns(10);
		textField.setBounds(499, 115, 57, 27);
		frmGenerateBill.getContentPane().add(textField);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setVisible(false);
		textField_3.setColumns(10);
		textField_3.setBounds(590, 115, 122, 27);
		frmGenerateBill.getContentPane().add(textField_3);

		textField_4 = new JTextField();
		textField_4.setVisible(false);
		textField_4.setColumns(10);
		textField_4.setBounds(499, 167, 57, 27);
		frmGenerateBill.getContentPane().add(textField_4);

		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setVisible(false);
		textField_5.setColumns(10);
		textField_5.setBounds(590, 167, 122, 27);
		frmGenerateBill.getContentPane().add(textField_5);

		textField_6 = new JTextField();
		textField_6.setVisible(false);
		textField_6.setColumns(10);
		textField_6.setBounds(499, 226, 57, 27);
		frmGenerateBill.getContentPane().add(textField_6);

		textField_7 = new JTextField();
		textField_7.setEditable(false);
		textField_7.setVisible(false);
		textField_7.setColumns(10);
		textField_7.setBounds(590, 226, 122, 27);
		frmGenerateBill.getContentPane().add(textField_7);

		textField_8 = new JTextField();
		textField_8.setVisible(false);
		textField_8.setColumns(10);
		textField_8.setBounds(499, 285, 57, 27);
		frmGenerateBill.getContentPane().add(textField_8);

		textField_9 = new JTextField();
		textField_9.setEditable(false);
		textField_9.setVisible(false);
		textField_9.setColumns(10);
		textField_9.setBounds(590, 285, 122, 27);
		frmGenerateBill.getContentPane().add(textField_9);

		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(99, 56, 278, 27);
		frmGenerateBill.getContentPane().add(textField_10);

		textField_11 = new JTextField();
		textField_11.setVisible(false);
		textField_11.setColumns(10);
		textField_11.setBounds(99, 115, 278, 27);
		frmGenerateBill.getContentPane().add(textField_11);

		textField_12 = new JTextField();
		textField_12.setVisible(false);
		textField_12.setColumns(10);
		textField_12.setBounds(99, 167, 278, 27);
		frmGenerateBill.getContentPane().add(textField_12);

		textField_13 = new JTextField();
		textField_13.setVisible(false);
		textField_13.setColumns(10);
		textField_13.setBounds(99, 226, 278, 27);
		frmGenerateBill.getContentPane().add(textField_13);

		textField_14 = new JTextField();
		textField_14.setVisible(false);
		textField_14.setColumns(10);
		textField_14.setBounds(99, 285, 278, 27);
		frmGenerateBill.getContentPane().add(textField_14);

		JLabel label = new JLabel("Units");
		label.setFont(new Font("Serif", Font.BOLD, 13));
		label.setBounds(417, 12, 57, 25);
		frmGenerateBill.getContentPane().add(label);

		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBounds(417, 56, 57, 27);
		frmGenerateBill.getContentPane().add(textField_15);

		textField_16 = new JTextField();
		textField_16.setVisible(false);
		textField_16.setColumns(10);
		textField_16.setBounds(417, 115, 57, 27);
		frmGenerateBill.getContentPane().add(textField_16);

		textField_17 = new JTextField();
		textField_17.setVisible(false);
		textField_17.setColumns(10);
		textField_17.setBounds(417, 167, 57, 27);
		frmGenerateBill.getContentPane().add(textField_17);

		textField_18 = new JTextField();
		textField_18.setVisible(false);
		textField_18.setColumns(10);
		textField_18.setBounds(417, 226, 57, 27);
		frmGenerateBill.getContentPane().add(textField_18);

		textField_19 = new JTextField();
		textField_19.setVisible(false);
		textField_19.setColumns(10);
		textField_19.setBounds(417, 285, 57, 27);
		frmGenerateBill.getContentPane().add(textField_19);

		textField_20 = new JTextField();
		textField_20.setEditable(false);
		textField_20.setColumns(10);
		textField_20.setBounds(590, 352, 122, 27);
		frmGenerateBill.getContentPane().add(textField_20);

		label_1 = new JLabel("1");
		label_1.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
		label_1.setBounds(34, 55, 31, 15);
		frmGenerateBill.getContentPane().add(label_1);

		label_2 = new JLabel("2");
		label_2.setVisible(false);
		label_2.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
		label_2.setBounds(37, 108, 31, 15);
		frmGenerateBill.getContentPane().add(label_2);

		label_3 = new JLabel("3");
		label_3.setVisible(false);
		label_3.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
		label_3.setBounds(37, 166, 31, 15);
		frmGenerateBill.getContentPane().add(label_3);

		label_4 = new JLabel("4");
		label_4.setVisible(false);
		label_4.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
		label_4.setBounds(37, 225, 31, 15);
		frmGenerateBill.getContentPane().add(label_4);

		label_5 = new JLabel("5");
		label_5.setVisible(false);
		label_5.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
		label_5.setBounds(34, 284, 34, 15);
		frmGenerateBill.getContentPane().add(label_5);

		JButton btnSendMail = new JButton("Send Mail");
		btnSendMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BillGenerator.generateAndSendMail(frmGenerateBill);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSendMail.setBounds(266, 447, 115, 27);
		frmGenerateBill.getContentPane().add(btnSendMail);

		JLabel lblGrandTotal = new JLabel("Grand Total");
		lblGrandTotal.setFont(new Font("DejaVu Sans", Font.BOLD, 13));
		lblGrandTotal.setBounds(480, 348, 92, 21);
		frmGenerateBill.getContentPane().add(lblGrandTotal);

		JLabel lblConsultant = new JLabel("Consultant");
		lblConsultant.setBounds(570, 453, 92, 15);
		frmGenerateBill.getContentPane().add(lblConsultant);

		CONSULTANT_COMBO_BOX = new JComboBox(Consultant.getAllConsultantByFullName());
		CONSULTANT_COMBO_BOX.setBounds(674, 445, 167, 30);
		CONSULTANT_COMBO_BOX.addActionListener(new ConsulatantComboBoxHandler());
		frmGenerateBill.getContentPane().add(CONSULTANT_COMBO_BOX);

		checkbox = new Checkbox("PAN Card");
		checkbox.setBounds(99, 405, 128, 23);
		frmGenerateBill.getContentPane().add(checkbox);

		checkbox_1 = new Checkbox("Signature tag");
		checkbox_1.setBounds(266, 405, 128, 23);
		frmGenerateBill.getContentPane().add(checkbox_1);
		
		textField_21 = new JTextField();
		textField_21.setToolTipText("Enter bill date");
		textField_21.setBounds(99, 355, 115, 20);
		frmGenerateBill.getContentPane().add(textField_21);
		textField_21.setColumns(10);
		
		JLabel lblDate = new JLabel("Bill Date");
		lblDate.setBounds(35, 358, 46, 14);
		frmGenerateBill.getContentPane().add(lblDate);
	}
}
