package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import automato.*;

import expressaoRegular.ExpressionAutomatonBuilder;
import gui.Facade;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class REinsertWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtRe;
	private JTable mainWindowTable;
	private boolean left;
	static int numErGeradas = 1;

	/**
	 * Create the dialog.
	 */
	public REinsertWindow() {
		createContents();
	}

	public REinsertWindow(JTable table, boolean _left) {
		createContents();
		mainWindowTable = table;
		left = _left;
	}

	private void createContents() {
		setBounds(100, 100, 344, 109);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			txtRe = new JTextField();
			contentPanel.add(txtRe);
			txtRe.setColumns(20);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						Automato eab =  new ExpressionAutomatonBuilder(txtRe.getText());
						Facade.autLeft = eab;
						String name = "From ER " + numErGeradas++;
						Facade.salvos.put(name, eab);
						MainWindow.updateComboBox(name);
						eab.print();
						Facade.showTable(mainWindowTable, left);
						setVisible(false);
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}

}
