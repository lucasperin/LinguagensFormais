package gui;

import java.awt.EventQueue;

import javax.jws.Oneway;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;

public class OperationFrames extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private static JTextArea textArea;



	/**
	 * Create the frame.
	 */
	public OperationFrames() {
		createContents();
	}
	private void createContents() {
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPane_1(), BorderLayout.CENTER);
	}
	public static void print(String arg){
		textArea.setText(textArea.getText() +"\n" + arg );
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setViewportView(getTextArea_1());
		}
		return scrollPane;
	}
	private JTextArea getTextArea_1() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setText(">>>Operações<<<");
			textArea.setEditable(false);
		}
		return textArea;
	}
	
}
