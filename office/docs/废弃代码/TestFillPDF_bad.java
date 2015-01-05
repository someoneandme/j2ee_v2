package com.pugwoo.practice.office.itext;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

/**
 * 2014年12月18日 14:18:17
 * 
 * 这里例子演示了将一个有form的pdf文件填充好数据，
 * 
 * 小问题：TODO 填充后的pdf文件仍然可以编辑。
 * 
 * 【这个例子太复杂了】
 */
public class TestFillPDF_bad implements PdfPCellEvent {
 
    /** The resulting PDF. */
    public static final String RESULT1 = "c:/text_fields.pdf";
    /** The resulting PDF. */
    public static final String RESULT2 = "c:/text_filled.pdf";
    
    /** The text field index of a TextField that needs to be added to a cell. */
    protected int tf;
 
    /**
     * Creates a cell event that will add a text field to a cell.
     * @param tf a text field index.
     */
    public TestFillPDF_bad(int tf) {
        this.tf = tf;
    }
 
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     */
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        form.setField("text_1", "Bruno Lowagie");
        form.setFieldProperty("text_2", "fflags", 0, null);
        form.setFieldProperty("text_2", "bordercolor", Color.RED, null);
        form.setField("text_2", "bruno");
        form.setFieldProperty("text_3", "clrfflags", TextField.PASSWORD, null);
        form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
        form.setField("text_3", "12345678", "xxxxxxxx");
        form.setFieldProperty("text_4", "textsize", new Float(12), null);
        form.regenerateField("text_4");
        stamper.close();
        reader.close();
    }
 
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public void createPdf(String filename) throws DocumentException, IOException {
    	// step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        PdfPCell cell;
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{ 1, 2 });
 
        table.addCell("Name:");
        cell = new PdfPCell();
        cell.setCellEvent(new TestFillPDF_bad(1));
        table.addCell(cell);
 
        table.addCell("Loginname:");
        cell = new PdfPCell();
        cell.setCellEvent(new TestFillPDF_bad(2));
        table.addCell(cell);
 
        table.addCell("Password:");
        cell = new PdfPCell();
        cell.setCellEvent(new TestFillPDF_bad(3));
        table.addCell(cell);
 
        table.addCell("Reason:");
        cell = new PdfPCell();
        cell.setCellEvent(new TestFillPDF_bad(4));
        cell.setFixedHeight(60);
        table.addCell(cell);
 
        document.add(table);
        // step 5
        document.close();
 
    }
 
    /**
     * Creates and adds a text field that will be added to a cell.
     * @see com.itextpdf.text.pdf.PdfPCellEvent#cellLayout(com.itextpdf.text.pdf.PdfPCell,
     *      com.itextpdf.text.Rectangle, com.itextpdf.text.pdf.PdfContentByte[])
     */
    public void cellLayout(PdfPCell cell, Rectangle rectangle, PdfContentByte[] canvases) {
        PdfWriter writer = canvases[0].getPdfWriter();
        TextField text = new TextField(writer, rectangle,
                String.format("text_%s", tf));
        text.setBackgroundColor(new GrayColor(0.75f));
        switch(tf) {
        case 1:
            text.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
            text.setText("Enter your name here...");
            text.setFontSize(0);
            text.setAlignment(Element.ALIGN_CENTER);
            text.setOptions(TextField.REQUIRED);
            break;
        case 2:
            text.setMaxCharacterLength(8);
            text.setOptions(TextField.COMB);
            text.setBorderStyle(PdfBorderDictionary.STYLE_SOLID);
            text.setBorderColor(Color.BLUE);
            text.setBorderWidth(2);
            break;
        case 3:
            text.setBorderStyle(PdfBorderDictionary.STYLE_INSET);
            text.setOptions(TextField.PASSWORD);
            text.setVisibility(TextField.VISIBLE_BUT_DOES_NOT_PRINT);
            break;
        case 4:
            text.setBorderStyle(PdfBorderDictionary.STYLE_DASHED);
            text.setBorderColor(Color.RED);
            text.setBorderWidth(2);
            text.setFontSize(8);
            text.setText(
                "Enter the reason why you want to win a free accreditation for the Foobar Film Festival");
            text.setOptions(TextField.MULTILINE | TextField.REQUIRED);
            break;
        }
        try {
            PdfFormField field = text.getTextField();
            if (tf == 3) {
                field.setUserName("Choose a password");
            }
            writer.addAnnotation(field);
        }
        catch(IOException ioe) {
            throw new ExceptionConverter(ioe);
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
 
    /**
     * Main method
     * @param args no arguments needed
     * @throws IOException
     * @throws DocumentException
     */
    public static void main(String[] args) throws DocumentException, IOException {
    	TestFillPDF_bad example = new TestFillPDF_bad(0);
        example.createPdf(RESULT1);
        example.manipulatePdf(RESULT1, RESULT2);
    }
}
