package frames;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utils.DialogUtils;
import database.Company;

public class MenuBars extends JApplet {
    private static final long serialVersionUID = 1L;

    public void init() {
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

    public void paint(Graphics g) {
    }
}
