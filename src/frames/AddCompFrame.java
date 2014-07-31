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

public class AddCompFrame extends JFrame {

    public JTextField textCompName;
    public JTextField textEmailId;
    public JTextField textUserId;
    public JTextField textPassword;

    public AddCompFrame() {

        super("Add Company");
        JPanel contentPane = (JPanel) this.getContentPane();

        JLabel name = new JLabel("Company Name");
        textCompName = new JTextField();
        textEmailId = new JTextField();
        JLabel email = new JLabel("Email");
        JLabel userId = new JLabel("User ID");
        textUserId = new JTextField();
        JLabel pass = new JLabel("Password");
        textPassword = new JTextField();
        JButton add = new JButton("Add");

        contentPane.setLayout(null);
        contentPane.setBorder(BorderFactory.createEtchedBorder());
        contentPane.setBackground(new Color(204, 204, 204));
        addComponent(contentPane, name, 5, 10, 183, 18);
        addComponent(contentPane, textCompName, 140, 10, 183, 22);

        addComponent(contentPane, email, 5, 47, 183, 18);
        addComponent(contentPane, textEmailId, 140, 47, 183, 22);

        addComponent(contentPane, userId, 5, 84, 97, 18);
        addComponent(contentPane, textUserId, 140, 84, 183, 22);

        addComponent(contentPane, pass, 5, 121, 97, 18);
        addComponent(contentPane, textPassword, 140, 121, 183, 22);

        addComponent(contentPane, add, 140, 158, 90, 22);
        AddCompFrameHandler compFramehandler = new AddCompFrameHandler(this);
        add.addActionListener(compFramehandler);

    }

    private void addComponent(Container container, Component c, int x, int y, int width, int height) {
        c.setBounds(x + 20, y + 20, width, height);
        container.add(c);
    }
}