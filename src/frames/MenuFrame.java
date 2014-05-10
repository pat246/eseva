package frames;

import handlers.MyMenuHandler;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;

public class MenuFrame extends JFrame {
	public MenuFrame() {

		super("E-Seva Shortcut  ©Prashant");
		Container contentPane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		// make panels
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		BasicInfoPanel basicPanel = new BasicInfoPanel();
		panel.addTab("Basic Info", basicPanel);
		panel.add("Detailed Info", new InventoryInfo());
		this.add(panel);
		// make menubar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		JMenuItem company = new JMenuItem("Add Company");
		JMenuItem newItem = new JMenuItem("New Item");
		JMenuItem printComp = new JMenuItem("Print Companies");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(company);
		file.add(newItem);
		file.add(printComp);
		file.add(exit);
		MyMenuHandler menuHandler = new MyMenuHandler(this);
		company.addActionListener(menuHandler);
		newItem.addActionListener(menuHandler);
		printComp.addActionListener(menuHandler);
		exit.addActionListener(menuHandler);
		JMenu edit = new JMenu("Edit");
		menuBar.add(file);
		menuBar.add(edit);
	}
}
