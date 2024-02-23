package org.bio.watermark;

import org.junit.jupiter.api.Test;

class PdfWatermarkEngineTest {

	@Test
	void testMain() {

		String inputPdfPath = "input.pdf"; // Replace with your input PDF file
		String outputPdfPath = "output.pdf"; // Replace with your output PDF file
		String imagePath = "rose.png";
		String watermarkText = "CONFIDENTIAL";

		PdfWatermarkEngine.addWaterMarkImage(inputPdfPath, outputPdfPath, watermarkText, imagePath);

	}

}
