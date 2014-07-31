package handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import frames.AddCompFrame;
import frames.CompanyListFrame;
import frames.InventoryInfo;
import frames.MenuFrame;


public class MyMenuHandler implements ActionListener, ItemListener {

    MenuFrame mFrame;

    public MyMenuHandler(MenuFrame frame) {
        this.mFrame = frame;
    }

    @Override
    public void itemStateChanged(ItemEvent event) {

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        if (action.equalsIgnoreCase("Add Company")) {
            AddCompFrame frame = new AddCompFrame();
            frame.setLocation(100, 200);
            frame.setSize(new Dimension(400, 400));
            frame.setVisible(true);
        } else if (action.equalsIgnoreCase("Exit")) {
            mFrame.dispose();
        } else if (action.equalsIgnoreCase("New Item")) {
        } else if (action.endsWith("Print Companies")) {
            CompanyListFrame CompanyListFrameyinvinfo = new CompanyListFrame();
            CompanyListFrameyinvinfo.setSize(new Dimension(1000, 1000));
            PrintableDocument.printComponent(CompanyListFrameyinvinfo.m_compListTab);
        }
    }

}