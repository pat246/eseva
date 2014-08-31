package handlers;

import java.awt.Color;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import utils.ThreadSafeUtil;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import database.Company;

import frames.BasicInfoPanel;
import frames.BillGeneratorUIFram;
import frames.MenuFrame;

public class BillGenerator {

    public static Font fontHelvetica8Normal  = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
    public static Font fontHelvetica10Normal = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
    public static Font fontHelvetica8Bold    = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
    public static Font fontHelvetica10Bold   = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);

    public static void generateBill(JFrame frmGenerateBill) throws MalformedURLException, IOException,
            DocumentException {
        FileDialog fDialog = new FileDialog(frmGenerateBill, "Save", FileDialog.SAVE);
        fDialog.setVisible(true);
        String path = fDialog.getDirectory() + fDialog.getFile();
        path += ".pdf";
        System.out.println(path);
        File file = new File(path);

        FileOutputStream pdfData = new FileOutputStream(file);
        Document document = new Document(PageSize.A4, 25, 25, 15, 15);
        PdfWriter.getInstance(document, pdfData);
        document.open();

        String today = ThreadSafeUtil.getSimpleDateFormat(false, false).format(Calendar.getInstance().getTime());
        Paragraph date = new Paragraph("Date: " + today, fontHelvetica8Normal);
        date.setAlignment(Rectangle.ALIGN_RIGHT);
        document.add(date);

        document.add(getRowSpacer());
        Table table_top = new Table(3);
        table_top.setWidths(new int[] { 100, 600, 200 });
        table_top.setPadding(3);
        table_top.setWidth(100);
        table_top.setBorderWidth(0);
        table_top.setBorderColor(new Color(255, 255, 255));

        Image imgGif = Image.getInstance("/home/prashant/projects/eseva/trunk/images/me_logo.jpg");
        imgGif.setAlignment(Image.LEFT | Image.TEXTWRAP);
        Cell c_img = new Cell();
        c_img.add(imgGif);
        c_img.setBorderColor(new Color(255, 255, 255));
        c_img.setBorder(0);
        c_img.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        table_top.addCell(c_img);

        Paragraph viaAddPg = new Paragraph(getTopAddressParagraph());
        if (viaAddPg != null) {
            Cell c_cell = new Cell();
            c_cell.add(viaAddPg);
            c_cell.setBorderColor(new Color(255, 255, 255));
            c_cell.setBorder(0);
            Cell c_cell1 = new Cell();
            c_cell1.add(getToAddressParagraph());
            c_cell1.setBorderColor(new Color(255, 255, 255));
            c_cell1.setBorder(0);
            table_top.addCell(c_cell);
            table_top.addCell(c_cell1);

            document.add(table_top);
        }

        Chunk invoiceChukn = new Chunk("CASH/CREDIT MEMO", fontHelvetica10Bold);
        invoiceChukn.setUnderline(0.5f, -1f);
        Paragraph invoicePg = new Paragraph();
        invoicePg.add(invoiceChukn);
        invoicePg.setAlignment(Rectangle.ALIGN_CENTER);
        document.add(invoicePg);

        document.add(getDataTable());
        document.add(getRowSpacer());

        Paragraph thanks = new Paragraph("Thank You", fontHelvetica10Normal);
        thanks.setAlignment(Rectangle.ALIGN_LEFT);
        document.add(thanks);

        document.close();
    }

    public static Paragraph getTopAddressParagraph() {
        Paragraph viaAddPg = new Paragraph("Sunanda Anandrao Thorat", fontHelvetica8Bold);
        viaAddPg.add(new Phrase("\nPlt No 70, Shushrusha Nagar", fontHelvetica8Normal));
        viaAddPg.add(new Phrase("\nDeokar Panand, Kolhapur", fontHelvetica8Normal));
        viaAddPg.add(new Phrase("\nPhone: 9422402749", fontHelvetica8Normal));
        viaAddPg.add(new Phrase("\nEmail: sthoratepfo@gmail.com", fontHelvetica8Normal));
        return viaAddPg;
    }

    public static Paragraph getToAddressParagraph() {
        Company company = MenuFrame.BASIC_PANEL.getSelectedCompany();

        Paragraph viaAddPg = new Paragraph("To,", fontHelvetica8Bold);
        viaAddPg.add(new Phrase("\n" + company.getCompanyName(), fontHelvetica8Normal));
        viaAddPg.add(new Phrase("\nAddress", fontHelvetica8Normal));
        viaAddPg.add(new Phrase("\n" + company.getPhone(), fontHelvetica8Normal));
        return viaAddPg;
    }

    public static Table getRowSpacer() throws BadElementException {
        Table table_top = new Table(1, 2);
        table_top.setBorder(0);
        table_top.setBorderWidth(0);
        table_top.setBorderColor(new Color(255, 255, 255));
        Cell cell = new Cell();
        cell.setBorderColor(new Color(255, 255, 255));
        cell.setBorder(0);
        table_top.addCell(cell);
        return table_top;
    }

    public static Table getDataTable() throws DocumentException {
        Table table = new Table(5, 10);
        table.setWidths(new int[] { 100, 500, 100, 100, 200 });
        table.setPadding(3);
        table.setWidth(100);
        table.setBorderWidth(0);
        table.setBorderColor(new Color(255, 255, 255));

        String[] headers = { "Sr. No", "Description", "Units", "Price", "Line Total" };
        for (String header : headers) {
            table.addCell(new Phrase(header, fontHelvetica8Normal));
        }
        JTextField[][] fieldArr = BillGeneratorUIFram.textFieldsArr;
        int srNo = 1;
        int tot = 0;
        for (JTextField[] field : fieldArr) {
            JTextField desc = field[0];
            JTextField units = field[1];
            JTextField unitPrice = field[2];
            if (StringUtils.isNotBlank(units.getText()) && StringUtils.isNotBlank(unitPrice.getText())) {
                int total = (Integer.parseInt(units.getText()) * Integer.parseInt(unitPrice.getText()));
                tot += total;
                table.addCell(new Phrase(srNo + "", fontHelvetica8Normal));
                table.addCell(new Phrase(desc.getText(), fontHelvetica8Normal));
                table.addCell(new Phrase(units.getText(), fontHelvetica8Normal));
                table.addCell(new Phrase(unitPrice.getText(), fontHelvetica8Normal));
                table.addCell(new Phrase(total + "", fontHelvetica8Normal));
                srNo++;
            }
        }
        Cell total = new Cell(new Phrase(tot + "", fontHelvetica8Normal));
        Cell totalTxt = new Cell(new Phrase("Total", fontHelvetica8Normal));
        totalTxt.setColspan(4);
        table.addCell(totalTxt);
        table.addCell(total);
        return table;
    }
}
