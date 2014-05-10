package frames;

import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import database.Company;

/*<APPLET CODE = "test/MenuBars" WIDTH = "500" HEIGHT = "400"></APPLET>*/
public class MenuBars extends JApplet {

	public void init() {
		try {
			Company.initialize();
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new MenuFrame();
					frame.setSize(new Dimension(1000, 1000));
					frame.setVisible(true);
				}

			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {

	}

}