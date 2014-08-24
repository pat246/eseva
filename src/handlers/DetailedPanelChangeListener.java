package handlers;

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import frames.DetailedInfoPanel;

public class DetailedPanelChangeListener implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
       
        if (e.getSource() instanceof JTabbedPane) {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            Component component = pane.getSelectedComponent();
            if (component instanceof DetailedInfoPanel) {
                DetailedInfoPanel detaildInfoPanel = (DetailedInfoPanel) component;
                detaildInfoPanel.fillCompanyInfo();
            }
        }
    }

}
