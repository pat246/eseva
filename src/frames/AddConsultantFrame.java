package frames;

import handlers.ConsultantsBean;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;

public class AddConsultantFrame extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public JTextField         textField;
    public JTextField         textField_1;
    public JTextField         textField_2;
    public JTextField         textField_3;
    public AddConsultantFrame thisFrame;
    public JTextField         textField_4;
    public JTextField         textField_5;
    public JTextField         textField_6;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddConsultantFrame frame = new AddConsultantFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AddConsultantFrame() {
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setFont(new Font("DejaVu Serif", Font.PLAIN, 12));
        setTitle("Add New Consultant");
        thisFrame = this;
        setBounds(100, 100, 595, 520);
        getContentPane().setLayout(null);

        JLabel lblFirstName = new JLabel("First Name*");
        lblFirstName.setFont(new Font("Droid Serif", Font.BOLD, 14));
        lblFirstName.setBounds(50, 37, 95, 25);
        getContentPane().add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name*");
        lblLastName.setFont(new Font("Droid Serif", Font.BOLD, 14));
        lblLastName.setBounds(50, 98, 95, 25);
        getContentPane().add(lblLastName);

        JLabel lblAddress = new JLabel("Address*");
        lblAddress.setFont(new Font("Droid Serif", Font.BOLD, 14));
        lblAddress.setBounds(50, 163, 95, 25);
        getContentPane().add(lblAddress);

        JLabel label_2 = new JLabel("Contact numbers");
        label_2.setToolTipText("Enter contact numbers comma saparated");
        label_2.setFont(new Font("Droid Serif", Font.BOLD, 14));
        label_2.setBounds(50, 313, 146, 25);
        getContentPane().add(label_2);

        textField = new JTextField();
        textField.setBounds(226, 40, 237, 22);
        getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(226, 101, 237, 22);
        getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(226, 166, 237, 22);
        getContentPane().add(textField_2);

        textField_3 = new JTextField();
        textField_3.setToolTipText("Enter contact numbers comma saparated");
        textField_3.setColumns(10);
        textField_3.setBounds(226, 316, 237, 22);
        getContentPane().add(textField_3);

        JButton btnAdd = new JButton("Add");
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ConsultantsBean.addConsultant(thisFrame);
            }
        });
        btnAdd.setBounds(235, 438, 89, 25);
        getContentPane().add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thisFrame.dispose();
            }
        });
        btnCancel.setBounds(385, 438, 89, 25);
        getContentPane().add(btnCancel);

        JLabel label = new JLabel("Office Email");
        label.setToolTipText("Enter official email address");
        label.setFont(new Font("Droid Serif", Font.BOLD, 14));
        label.setBounds(50, 369, 146, 25);
        getContentPane().add(label);

        textField_4 = new JTextField();
        textField_4.setToolTipText("Enter official email address");
        textField_4.setColumns(10);
        textField_4.setBounds(226, 372, 237, 22);
        getContentPane().add(textField_4);

        JLabel label_1 = new JLabel("Address1*");
        label_1.setFont(new Font("Droid Serif", Font.BOLD, 14));
        label_1.setBounds(50, 212, 95, 25);
        getContentPane().add(label_1);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(226, 215, 237, 22);
        getContentPane().add(textField_5);

        JLabel label_3 = new JLabel("Address2*");
        label_3.setFont(new Font("Droid Serif", Font.BOLD, 14));
        label_3.setBounds(50, 259, 95, 25);
        getContentPane().add(label_3);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(226, 262, 237, 22);
        getContentPane().add(textField_6);

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
