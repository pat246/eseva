package frames;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import license.Validity;
import utils.DialogUtils;
import database.Company;

public class MenuBars extends JApplet {
	private static final long serialVersionUID = 1L;

	public void init() {
		try {
			checkForValidity();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(0);
			return;
		}
		try {
			Company.initialize();
			DialogUtils.init();
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JFrame frame = new MenuFrame();
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent windowEvent) {
							System.exit(0);
						}
					});
					frame.setExtendedState(6);
					Dimension dim = new Dimension(500, 500);
					frame.setMinimumSize(dim);
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

	private void checkForValidity() throws Exception {

		int validity = Validity.TWO_MONTH_VALID;
		Calendar startDate = Validity.storeUsageDate();

		Calendar now = Calendar.getInstance();
		if (startDate != null) {
			startDate.add(Calendar.DAY_OF_MONTH, validity);
			if (!now.before(startDate)) {
				JOptionPane
						.showMessageDialog(this, "Software validity has expired, please contact Madhavi Enterprises");
				System.exit(0);
			}
		}

	}

	public void paint(Graphics g) {
	}
}
