package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AddAutomaton extends JFrame {

	private JPanel contentPane;
	private JButton okButton;
	private JButton cancelButton;
	private JTable table;
	private JTable mainWindowTable;
	private boolean left;
	private JScrollPane scrollPane_1;
	private JButton btnNewButton;
	private JButton btnsimb;
	private JButton btnstate;
	private JButton btnsimb_1;
	private static int serial = 1;
	

	public AddAutomaton() {
		createContents();
	}
	public AddAutomaton(JTable table, boolean _left) {
		createContents();
		mainWindowTable = table;
		left = _left;
	}
	private void createContents() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getOkButton());
		contentPane.add(getCancelButton());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(337, 0, 186, 314);
		contentPane.add(scrollPane);
		
		JTextPane txtpnEstadosQQ = new JTextPane();
		txtpnEstadosQQ.setText("*INSTRUÇÕES*\n\n*Antes de apertar OK clique fora da tabela ou aperte enter!*\n\nEstados:\nq0, q1 .. q14\nO primeiro estado será sempre o inicial\n*qn para estados finais\n\nSimbolos:\na, b, c, ..., 1, 2, 3 ... (tamanho 1)\n\nTransições (exemplos):\nq1\nq2,q3,... (sem espaços)\nq3,q2 (ERRADO! sempre ordenado de forma crescente) \n&-transições funcionam apenar para a função \"recognize\"\n");
		scrollPane.setViewportView(txtpnEstadosQQ);
		contentPane.add(getScrollPane_1());
		contentPane.add(getBtnNewButton());
		contentPane.add(getBtnsimb());
		contentPane.add(getBtnstate());
		contentPane.add(getBtnsimb_1());
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("Ok");
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					boolean ok = false;
						ok =Facade.create(left, table);
					if(ok){
						Facade.showTable(mainWindowTable, left);
						table = null;
						setVisible(false);
						serial++;
					}
					
				}
			});
			okButton.setBounds(217, 252, 108, 25);
		}
		return okButton;
	}
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancel");
			cancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
				}
			});
			cancelButton.setBounds(217, 289, 108, 25);
		}
		return cancelButton;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
					{"Aut" + serial, "a"},
					{"q0", null},
					{null, null},
					{null, null},
					{null, null},
				},
				new String[] {
					"States", "simbol"
				}
			));
		}
		return table;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(12, 0, 313, 240);
			scrollPane_1.setViewportView(getTable());
		}
		return scrollPane_1;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("+State");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					((DefaultTableModel) table.getModel()).addRow(new Object[table.getColumnCount()-1]);
				}
			});
			btnNewButton.setBounds(12, 252, 95, 25);
		}
		return btnNewButton;
	}
	private JButton getBtnsimb() {
		if (btnsimb == null) {
			btnsimb = new JButton("+Simb");
			btnsimb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					((DefaultTableModel) table.getModel()).addColumn("Simbol");
				}
			});
			btnsimb.setBounds(111, 252, 94, 25);
		}
		return btnsimb;
	}
	private JButton getBtnstate() {
		if (btnstate == null) {
			btnstate = new JButton("-State");
			btnstate.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(table.getRowCount()>2)
						((DefaultTableModel) table.getModel()).removeRow(table.getRowCount()-1);
				}

			});
			btnstate.setBounds(12, 289, 95, 25);
		}
		return btnstate;
	}
	private JButton getBtnsimb_1() {
		if (btnsimb_1 == null) {
			btnsimb_1 = new JButton("-Simb");
			btnsimb_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(table.getColumnCount()>2)
						((DefaultTableModel) table.getModel()).setColumnCount(table.getColumnCount()-1);
				}
			});
			btnsimb_1.setBounds(110, 289, 95, 25);
		}
		return btnsimb_1;
	}
}
