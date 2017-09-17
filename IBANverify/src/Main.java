import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 334, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		ArrayList<String> ar = new ArrayList<String>();

		Button button = new Button("Validate from file");
		button.setActionCommand("Validate from file");
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				fd.setVisible(true);
				String filename = fd.getFile();
				String path = fd.getDirectory();
				String origFile = path + filename;
				if (filename == null) {
					JOptionPane.showConfirmDialog(null, "You didn't chose file", "Message", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				} else {
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(origFile));
						String sCurrentLine;

						while ((sCurrentLine = br.readLine()) != null) {

							if (CheckIBAN.ibanTest(sCurrentLine)) {
								ar.add(sCurrentLine + " true");
								System.out.println(sCurrentLine);
							} else {
								ar.add(sCurrentLine + " false");
							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {

							if (br != null)
								br.close();

						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					// write result to file
					FileWriter writer = null;
					try {
						writer = new FileWriter(path + filename.replaceFirst("[.][^.]+$", "") + ".out");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (String str : ar) {
						try {
							writer.write(str + " \r\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		button.setBounds(180, 72, 130, 27);
		frame.getContentPane().add(button);

		TextField tEnteredSWIFT = new TextField();
		tEnteredSWIFT.setFont(new Font("Arial", Font.PLAIN, 16));
		tEnteredSWIFT.setBounds(43, 28, 267, 27);
		frame.getContentPane().add(tEnteredSWIFT);

		Button button_1 = new Button("Validate from txt");
		button_1.setActionCommand("Validate from txt");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!tEnteredSWIFT.getText().isEmpty()) {
					if (CheckIBAN.ibanTest(tEnteredSWIFT.getText())) {
						JOptionPane.showConfirmDialog(null, "IBAN is correct", "Message", JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showConfirmDialog(null, "IBAN is incorrect", "Message", JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE);
						;
					}
				} else {
					JOptionPane.showConfirmDialog(null, "IBAN is empty", "Message", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}

			}
		});
		button_1.setFont(new Font("Arial", Font.PLAIN, 16));
		button_1.setBounds(43, 72, 131, 27);
		frame.getContentPane().add(button_1);
	}
}
