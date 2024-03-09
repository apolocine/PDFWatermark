package org.bio.watermark;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


/**
 * Dr Hamid MADANI mailto: drmdh@msn.com
 */
public class PdfWatermarkEngine {
	public static void main(String[] args) {

		String inputPdfPath = "input.pdf"; // Replace with your input PDF file
		String outputPdfPath = "output.pdf"; // Replace with your output PDF file
		String imagePath = "rose.png";
		String watermarkText = "CONFIDENTIAL";

		addWaterMarkImage(inputPdfPath, outputPdfPath, watermarkText, imagePath);
	}

	public static String addWatermark(String inputPdfPath, String outputPdfPath, String watermarkText,
			String imagePath) {
		// addWatermark( inputPdfPath, watermarkText, imagePath);
		try {
			PdfReader reader = new PdfReader(inputPdfPath);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPdfPath));

			int pageCount = reader.getNumberOfPages();
			BaseFont font = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);

			for (int i = 1; i <= pageCount; i++) {
				PdfContentByte content = stamper.getUnderContent(i);

				// Add text watermark
				content.beginText();
				content.setFontAndSize(font, 50);
				content.setColorFill(BaseColor.LIGHT_GRAY);
				content.showTextAligned(Element.ALIGN_CENTER, watermarkText, 300, 400, 45);
				content.endText();

				if (imagePath != null && new File(imagePath).isFile()) {
					try {
						// Add image watermark
						Image watermarkImage = Image.getInstance(imagePath);
						watermarkImage.setAbsolutePosition(200, 300);
						content.addImage(watermarkImage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputPdfPath;
	}

	public static String addWaterMarkImage(String inputPdfPath, String outputPdfPath, String watermarkText,
			String imagePath) {

		// Obtenez le répertoire du fichier original
		String originalFileDir = inputPdfPath.substring(0, inputPdfPath.lastIndexOf(File.separator));
	
		// Générez un nom de fichier pour la copie modifiée
		//File originalFile = new File(inputPdfPath);
		// String modifiedFileName = "hmd_" + originalFile.getName();

		File modifiedFile = new File(originalFileDir, outputPdfPath);

		return addWatermark(inputPdfPath, modifiedFile.getAbsolutePath(), watermarkText, imagePath);

	}

	
	

	public static String  convertTextToPdf(String inputTextFile, String outputPdfFile) throws IOException {
		// Obtenez le répertoire du fichier original
		String originalFileDir = inputTextFile.substring(0, inputTextFile.lastIndexOf(File.separator));
		File modifiedFile = new File(originalFileDir, outputPdfFile);
		
	        try (
	        		
	        		
	        	BufferedReader reader = new BufferedReader(new FileReader(inputTextFile));
	             PdfDocument pdfDoc = new PdfDocument(new PdfWriter(modifiedFile.getAbsoluteFile()));
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
	        
	        return modifiedFile.getAbsolutePath();
	    }
	   
	 
}
