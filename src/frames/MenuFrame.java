package frames;

import handlers.DetailedPanelChangeListener;
import handlers.MyMenuHandler;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class MenuFrame extends JFrame {

    public static BasicInfoPanel BASIC_PANEL;

    public MenuFrame() {
        super("E-Seva Shortcut  ©Prashant");
        Container contentPane = getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        JTabbedPane panel = new JTabbedPane(1);
        BASIC_PANEL = new BasicInfoPanel();
        BASIC_PANEL.setParentFrame(this);
        panel.addTab("Basic Info", BASIC_PANEL);
        DetailedInfoPanel detailInfoPanel = new DetailedInfoPanel();
        panel.add("Detailed Info", detailInfoPanel);
        DetailedPanelChangeListener changeListerner = new DetailedPanelChangeListener();
        panel.addChangeListener(changeListerner);
        add(panel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        JMenuItem company = new JMenuItem("Add Company");
        JMenuItem resetPassword = new JMenuItem("Reset Password");
        JMenuItem printComp = new JMenuItem("Print Companies");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(company);
        file.add(resetPassword);
        file.add(printComp);
        file.add(exit);

        MyMenuHandler menuHandler = new MyMenuHandler(this);
        company.addActionListener(menuHandler);
        printComp.addActionListener(menuHandler);
        resetPassword.addActionListener(menuHandler);
        exit.addActionListener(menuHandler);
        JMenu edit = new JMenu("Edit");
        menuBar.add(file);
        menuBar.add(edit);
    }
}
