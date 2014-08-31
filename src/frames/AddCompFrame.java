package frames;

import handlers.AddCompFrameHandler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddCompFrame extends JFrame {

    public JTextField textCompName;
    public JTextField textEmailId;
    public JTextField textUserId;
    public JTextField textPassword;
    public JTextField textMobile;
    public JTextField textAddr;
    public JTextField textContactPerson;
    public static int COL_SPACE = 20;

    public AddCompFrame() {

        super("Add Company");
        JPanel contentPane = (JPanel) this.getContentPane();

        JLabel name = new JLabel("Company Name");
        name.setToolTipText("Name of the company");
        textCompName = new JTextField();
        textEmailId = new JTextField();
        JLabel email = new JLabel("Email");
        JLabel userId = new JLabel("User ID");
        textUserId = new JTextField();
        JLabel pass = new JLabel("Password");
        textPassword = new JTextField();
        JLabel mob = new JLabel("Mobile");
        textMobile = new JTextField();
        JLabel addr = new JLabel("Address");
        textAddr = new JTextField();
        JButton add = new JButton("Add");
        JLabel cp = new JLabel("Contact person");
        textContactPerson = new JTextField();

        contentPane.setLayout(null);
        contentPane.setBorder(BorderFactory.createEtchedBorder());
        contentPane.setBackground(new Color(204, 204, 204));
        int i = 1;
        addComponent(i++, contentPane, name, 5, 10, 183, 18);
        addComponent(i++, contentPane, textCompName, 140, 10, 183, 22);

        addComponent(i++, contentPane, email, 5, 47, 183, 18);
        addComponent(i++, contentPane, textEmailId, 140, 47, 183, 22);

        addComponent(i++, contentPane, userId, 5, 84, 97, 18);
        addComponent(i++, contentPane, textUserId, 140, 84, 183, 22);

        addComponent(i++, contentPane, pass, 5, 121, 97, 18);
        addComponent(i++, contentPane, textPassword, 140, 121, 183, 22);

        addComponent(i++, contentPane, mob, 5, 158, 97, 18);
        addComponent(i++, contentPane, textMobile, 140, 158, 183, 22);

        addComponent(i++, contentPane, cp, 5, 195, 110, 18);
        addComponent(i++, contentPane, textContactPerson, 140, 195, 183, 22);
        
        addComponent(i++, contentPane, addr, 5, 232, 110, 18);
        addComponent(i++, contentPane, textAddr, 140, 232, 183, 22);

        addComponent(i++, contentPane, add, 140, 269, 90, 22);
        AddCompFrameHandler compFramehandler = new AddCompFrameHandler(this);
        add.addActionListener(compFramehandler);

    }

    private void addComponent(int i, Container container, Component c, int x, int y, int width, int height) {
        if (i % 2 == 0) {
            x += COL_SPACE;
        }
        c.setBounds(x + 20, y + 20, width, height);
        container.add(c);
    }
}
