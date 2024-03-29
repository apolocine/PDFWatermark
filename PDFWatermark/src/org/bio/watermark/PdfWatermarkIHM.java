package org.bio.watermark;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 * Dr Hamid MADANI mailto: drmdh@msn.com
 */
public class PdfWatermarkIHM extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputTextField;
	private JTextField inputPdfField;
	private JTextField watermarkTextField;
	private JTextField watermarkImageField;
	private JTextField outputPdfField;

	public PdfWatermarkIHM() {
		setTitle("PDF Watermark App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Text field
		inputTextField= new JTextField(20);
		// Création des composants Swing
		inputPdfField = new JTextField(20);
		watermarkTextField = new JTextField("CONFIDENTIAL", 20);
		watermarkImageField = new JTextField(20);
		outputPdfField = new JTextField(20);

		JButton browseTextButton = new JButton("Browse Txt");
		browseTextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseInputTextFile();
			}
		});
		
		JButton convert2PdftButton = new JButton("Convert2Pdft");
		convert2PdftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				convert2Pdf() ;
			}
		});
		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseInputPdf();
			}
		});

		JButton photoButton = new JButton("Browse Image");
		photoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browseInputPhoto();
			}
		});

		JButton openPdfButton = new JButton("open PDF");
		openPdfButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openWaterMarkedPDF();
			}

		});
		JButton applyWatermarkButton = new JButton("Apply Watermark");
		applyWatermarkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applyWatermark();
			}
		});

		// Ajout des composants à la fenêtre
		add(new JLabel("Text File:"));
		add(inputTextField);
		add(browseTextButton);
		add(convert2PdftButton);
		add(new JLabel("Input PDF File:"));
		add(inputPdfField);
		add(browseButton);
		add(new JLabel("Text Watermark:"));
		add(watermarkTextField);
		add(new JLabel("Image Watermark Path:"));
		add(watermarkImageField);
		add(photoButton);
		add(new JLabel("####################"));
		add(applyWatermarkButton);
		add(new JLabel("Output PDF File:"));
		add(outputPdfField);
		add(new JLabel("Open  PDF File:"));
		add(openPdfButton);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	private void browseInputTextFile() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			inputTextField.setText(selectedFile.getAbsolutePath());
		}
	}
	
	
	private void browseInputPdf() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			inputPdfField.setText(selectedFile.getAbsolutePath());
		}
	}

	private void browseInputPhoto() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			watermarkImageField.setText(selectedFile.getAbsolutePath());
		}
	}

	private void openWaterMarkedPDF() {

		String filePath = outputPdfField.getText();
		openBrowseFile(filePath);

	}

	// Spécifiez le chemin du fichier PDF à ouvrir
	public static void openBrowseFile(String filePath) {

		// Vérifiez si Desktop est pris en charge sur la plate-forme actuelle
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			try {
				// Ouvrez le fichier PDF avec l'application par défaut
				desktop.open(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Desktop n'est pas pris en charge sur cette plate-forme.");
		}
	}
	private void convert2Pdf() {
		String inputTextPath = inputTextField.getText();
		long interval = 21;
		Random rnd = new Random(interval);
		long result = rnd.nextLong();
		String tmpOutputPdfPath = result + "input.pdf";
		try {
			
			String inputTextPathC = PdfWatermarkEngine.convertTextToPdf(inputTextPath, tmpOutputPdfPath);
			inputPdfField.setText(inputTextPathC);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void applyWatermark() {
		String inputPdfPath = inputPdfField.getText();
		long interval = 21;
		Random rnd = new Random(interval);
		long result = rnd.nextLong();
		String tmpOutputPdfPath = result + "output.pdf";
		String watermarkText = watermarkTextField.getText();
		String watermarkImagePath = watermarkImageField.getText();

		if (inputPdfPath.isEmpty() || watermarkText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please provide input PDF path and watermark text.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String outputPdfPath = PdfWatermarkEngine.addWaterMarkImage(inputPdfPath, tmpOutputPdfPath, watermarkText,
				watermarkImagePath);

		if (outputPdfPath != null) {
			outputPdfField.setText(outputPdfPath);
			JOptionPane.showMessageDialog(this, "Watermark applied successfully.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Failed to apply watermark.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PdfWatermarkIHM());
	}
}
