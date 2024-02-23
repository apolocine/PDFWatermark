import javax.swing.*;

import org.bio.watermark.PdfWatermarkEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class PdfWatermarkApp extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputPdfField;
    private JTextField watermarkTextField;
    private JTextField watermarkImageField;
    private JTextField outputPdfField;

    public PdfWatermarkApp() {
        setTitle("PDF Watermark App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des composants Swing
        inputPdfField = new JTextField(20);
        watermarkTextField = new JTextField("CONFIDENTIAL", 20);
        watermarkImageField = new JTextField(20);
        outputPdfField = new JTextField(20);

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseInputPdf();
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
        add(new JLabel("Input PDF File:"));
        add(inputPdfField);
        add(browseButton);
        add(new JLabel("Text Watermark:"));
        add(watermarkTextField);
        add(new JLabel("Image Watermark Path:"));
        add(watermarkImageField);
        add(applyWatermarkButton);
        add(new JLabel("Output PDF File:"));
        add(outputPdfField);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void browseInputPdf() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            inputPdfField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void applyWatermark() {
        String inputPdfPath = inputPdfField.getText();
    	long interval = 15421;
		Random rnd = new Random(interval);
		long result = rnd.nextLong();
		String tmpOutputPdfPath = result + "output.pdf";
		
        String watermarkText = watermarkTextField.getText();
        String watermarkImagePath = watermarkImageField.getText();

        if (inputPdfPath.isEmpty() || watermarkText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide input PDF path and watermark text.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String outputPdfPath = PdfWatermarkEngine.addWaterMarkImage(inputPdfPath,tmpOutputPdfPath, watermarkText, watermarkImagePath);

        if (outputPdfPath != null) {
            outputPdfField.setText(outputPdfPath);
            JOptionPane.showMessageDialog(this, "Watermark applied successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to apply watermark.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PdfWatermarkApp());
    }
}
