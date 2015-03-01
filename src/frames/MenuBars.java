package frames;

import database.Company;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import license.Validity;
import utils.DialogUtils;

public class MenuBars extends JApplet {
	private static final long serialVersionUID = 1L;
	public static JFrame MENU_FRAME;

	public MenuBars() {
	}

	public void init() {
		try {
			Company.initialize();
			DialogUtils.init();
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JFrame frame = new MenuFrame();
					MENU_FRAME = frame;
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
		int validity = Validity.THREE_MONTH_VALID;
		Calendar startDate = Validity.storeUsageDate();

		Calendar now = Calendar.getInstance();
		if (startDate != null) {
			startDate.add(5, validity);
			if (!now.before(startDate)) {
				JOptionPane
						.showMessageDialog(this, "Software validity has expired, please contact Madhavi Enterprises");
				System.exit(0);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Software validity has expired, please contact Madhavi Enterprises");
			System.exit(0);
		}
		verifyMacId();
	}

	private void verifyMacId() throws Exception {
		try {
			String command = "ipconfig /all";
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
			Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");
			String val;
			do {
				Matcher mm = null;
				do {
					String line = inn.readLine();
					if (line == null) {
						break;
					}
					mm = pattern.matcher(line);
				} while (!mm.matches());
				val = mm.group(1);
				String baba = "94-DE-80-54-70-CC\000";
				String solanki = "54-42-49-67-DD-6B";
				String magdum = "24-B6-FD-2B-67-27";
				String patil = "00-13-D3-D4-5A-44";
				String malage = "00-13-20-A1-48-2C";
				String raviPPP = "00-53-45-00-00-00";
				if (baba.trim().equals(val)) {
					return;
				}
				if (solanki.trim().equals(val)) {
					return;
				}
				if (magdum.trim().equals(val)) {
					return;
				}
				if (patil.trim().equals(val)) {
					return;
				}
				if (malage.trim().equals(val)) {
					return;
				}
				if (raviPPP.trim().equals(val)) {
					return;
				}
				if ("28-37-37-22-E4-3C\000".equals(val)) {
					return;
				}
			} while (!"00-23-5A-9B-85-2D".equals(val));

			JOptionPane.showMessageDialog(this,
					"Sorry, this software works with registered users only.Please contact Madhavi Enterprises.");
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Please connect internet to start this software.");
			System.exit(0);
		}
	}

	public void paint(Graphics g) {
	}

	public static void main(String[] args) {
		MenuBars applet = new MenuBars();
		applet.init();
	}
}
