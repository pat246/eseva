package frames;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryInfo extends JPanel {

    public InventoryInfo() {
        JLabel compName = new JLabel("Select Company");
        addComponent(compName, 5, 5, 170, 18);
    }

    private void addComponent(Component c, int x, int y, int width, int height) {
        c.setBounds(x + 50, y + 100, width, height);
        this.add(c);
    }
}
