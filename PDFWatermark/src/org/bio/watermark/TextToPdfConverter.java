package org.bio.watermark;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextToPdfConverter {

    public static void main(String[] args) {
        String inputTextFile = "chemin/vers/votre/fichier.txt";
        String outputPdfFile = "chemin/vers/votre/fichier.pdf";

        try {
            convertTextToPdf(inputTextFile, outputPdfFile);
            System.out.println("Conversion réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertTextToPdf(String inputTextFile, String outputPdfFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputTextFile));
             PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputPdfFile));
             Document document = new Document(pdfDoc)) {

            PdfFont font = PdfFontFactory.createFont();
            Paragraph paragraph;

            String line;
            while ((line = reader.readLine()) != null) {
                paragraph = new Paragraph(line)
                        .setFont(font)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.BLACK);
                document.add(paragraph);
            }
        }
    }
}
