package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.Font;

public class ResultWindow extends JDialog {
	private JButton okButton;
	private JScrollPane scrollPane;
	private JTextPane textPane;

	public ResultWindow(String text) {
		createContents();
		textPane.setText(text);
	}
	private void createContents() {
		setBounds(100, 100, 454, 155);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						disable();
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			buttonPane.add(okButton);
		}
		{
			scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				textPane = new JTextPane();
				textPane.setFont(new Font("Dialog", Font.BOLD, 12));
				textPane.setEnabled(false);
				textPane.setEditable(false);
				scrollPane.setViewportView(textPane);
			}
		}
	}

}
