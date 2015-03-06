package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.Facade;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;

public class MainWindow {

	private JFrame frmAutomatos;
	private JMenuBar menuBar;
	private JMenu mnVisualisar;
	public static JLabel automatonName1;
	public static JLabel automatonName2;
	private JCheckBox chckbxOperaes;
	public OperationFrames operations;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JButton newAutomaton_1;
	private JButton generate_1;
	private JButton determinize_1;
	private JTextField textField_1;
	private JButton btnRecognize_1;
	private AddAutomaton addAutomaton;
	private REequivalenceWindow equivalenceWindow;
	private JTable table_1;
	private JTable table_2;
	private JButton remove_1;
	private JButton minimize_1;
	private JTextField textField_3;
	private JLabel lblint;
	private HelpWindow help;
	private JCheckBoxMenuItem chckbxmntmHelp;
	private JButton btnRe;
	private REinsertWindow addAutomatonER;
	private JButton remover_direita;
	private JButton usar;
	private JButton verificarEquivalencia;
	private static JComboBox<String> comboBox = null;


	/**
	 * Create the application.
	 */
	public MainWindow() {
		operations = new OperationFrames();
		operations.setVisible(false);
		help = new HelpWindow();
		help.setVisible(false);
		initialize();
		frmAutomatos.setVisible(true);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAutomatos = new JFrame();
		frmAutomatos.setResizable(false);
		frmAutomatos.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmAutomatos.setBackground(Color.GRAY);
		frmAutomatos.setTitle("Automatos");
		frmAutomatos.setBounds(100, 100, 600, 600);
		frmAutomatos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAutomatos.setJMenuBar(getMenuBar());
		frmAutomatos.getContentPane().setLayout(null);
		frmAutomatos.getContentPane().add(getLblNewLabel());
		frmAutomatos.getContentPane().add(getLblAutomato());
		frmAutomatos.getContentPane().add(getScrollPane());
		frmAutomatos.getContentPane().add(getScrollPane_1());
		frmAutomatos.getContentPane().add(getButton_2());
		frmAutomatos.getContentPane().add(getButton_1_1());
		frmAutomatos.getContentPane().add(getButton_2_1());
		frmAutomatos.getContentPane().add(getTextField_1());
		frmAutomatos.getContentPane().add(getBtnRecognize_1());
		frmAutomatos.getContentPane().add(getRemove_1());
		frmAutomatos.getContentPane().add(getMinimize_1());
		frmAutomatos.getContentPane().add(getTextField_3());
		frmAutomatos.getContentPane().add(getLblint());
		frmAutomatos.getContentPane().add(getBtnRe());
		frmAutomatos.getContentPane().add(getRemoverDireita());
		frmAutomatos.getContentPane().add(getUsar());
		frmAutomatos.getContentPane().add(getButtonVerificarEquivalencia());
		frmAutomatos.getContentPane().add(getComboBox());
		
		JLabel lblAutomatosSalvos = new JLabel("Automatos salvos");
		lblAutomatosSalvos.setBounds(342, 458, 148, 15);
		frmAutomatos.getContentPane().add(lblAutomatosSalvos);
	

	}
	
	private JComboBox<String> getComboBox(){
		if(comboBox == null){
		
			comboBox = new JComboBox<String>();
			comboBox.setBounds(339, 480, 165, 24);
			comboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = (String)comboBox.getSelectedItem();
					Facade.trocarAutomato(table_1, name);
				}
			});
			updateComboBox("");
		}
		return comboBox;
	}
	
	public static void updateComboBox(String name){
		if(comboBox != null){
			comboBox.removeAllItems();
			System.out.println(Facade.salvos.keySet());
			for (Iterator<String> iterator = Facade.salvos.keySet().iterator(); iterator
					.hasNext();) {
				String s = iterator.next();
				comboBox.addItem(s);
			}
			if(!name.isEmpty()){
				comboBox.setSelectedItem(name);
			}
		}
	}
	
	private JButton getUsar(){
		if (usar == null) {
			usar = new JButton("<< Usar");
			usar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(Facade.autRight != null){
						table_1.setModel(table_2.getModel());
						table_2.setModel(new DefaultTableModel());
						Facade.usarDireita();
					}
					
				}
			});
			usar.setBounds(390, 278, 100, 25);
		}
		return usar;
	}

	private JButton getRemoverDireita() {
		if (remover_direita == null) {
			remover_direita = new JButton("X");
			remover_direita.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					table_2.setModel(new DefaultTableModel());
					Facade.removeRight();
					automatonName1.setText("Automato 2");
				}
			});
			remover_direita.setBounds(310, 278, 75, 25);
		}
		return remover_direita;
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnVisualisar());
		}
		return menuBar;
	}
	private JMenu getMnVisualisar() {
		if (mnVisualisar == null) {
			mnVisualisar = new JMenu("Menu");
			mnVisualisar.add(getChckbxOperaes());
			mnVisualisar.add(getChckbxmntmHelp());
		}
		return mnVisualisar;
	}
	private JLabel getLblNewLabel() {
		if (automatonName1 == null) {
			automatonName1 = new JLabel("Automato 1");
			automatonName1.setBounds(125, 0, 165, 15);
		}
		return automatonName1;
	}
	private JLabel getLblAutomato() {
		if (automatonName2 == null) {
			automatonName2 = new JLabel("Automato 2");
			automatonName2.setBounds(418, 0, 166, 15);
		}
		return automatonName2;
	}
	private JCheckBox getChckbxOperaes() {
		if (chckbxOperaes == null) {
			chckbxOperaes = new JCheckBox("Operations");
			chckbxOperaes.addItemListener(new ItemListener() {
				@SuppressWarnings({ "deprecation" })
				public void itemStateChanged(ItemEvent e) {
					if(chckbxOperaes.isSelected())
					{
						operations.show(true);
					}
					else
					{
						operations.show(false);
					}
				}
			});
		}
		return chckbxOperaes;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 12, 280, 259);
			scrollPane.setViewportView(getTable_1());
			//scrollPane.setViewportView(getTable_2());
		}
		return scrollPane;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(308, 12, 276, 259);
			scrollPane_1.setViewportView(getTable_2());
//			scrollPane_1.setViewportView(getTable_1());
		}
		return scrollPane_1;
	}
	private JButton getButton_2() {
		if (newAutomaton_1 == null) {
			newAutomaton_1 = new JButton("New");
			newAutomaton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(addAutomaton == null || addAutomaton.isVisible() == false){
						 addAutomaton = new AddAutomaton(table_1, true);
						 addAutomaton.show(true);
					}
				}
			});
			newAutomaton_1.setBounds(12, 278, 75, 25);
		}
		return newAutomaton_1;
	}
	private JButton getButton_1_1() {
		if (generate_1 == null) {
			generate_1 = new JButton("generate");
			generate_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Facade.gerarSentencas(textField_3);
				}
			});
			generate_1.setBounds(12, 443, 121, 25);
		}
		return generate_1;
	}
	
	//TODO
	private JButton getButtonVerificarEquivalencia() {
		if ( verificarEquivalencia == null) {
			verificarEquivalencia = new JButton("equivalencia de ERs");
			verificarEquivalencia.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(equivalenceWindow == null || equivalenceWindow.isVisible() == false){
						equivalenceWindow = new REequivalenceWindow();
						equivalenceWindow.show(true);
					}
				}
			});
			verificarEquivalencia.setBounds(12, 480, 200, 25);
		}
		return verificarEquivalencia;
	}
	
	
	private JButton getButton_2_1() {
		if (determinize_1 == null) {
			determinize_1 = new JButton("Determinize");
			determinize_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Facade.determinizar(table_2);
				}
			});
			determinize_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			determinize_1.setBounds(12, 315, 120, 25);
		}
		return determinize_1;
	}
	private JTextField getTextField_1() {
		if (textField_1 == null) {
			textField_1 = new JTextField();
			textField_1.setBounds(12, 352, 178, 19);
			textField_1.setColumns(10);
		}
		return textField_1;
	}
	private JButton getBtnRecognize_1() {
		if (btnRecognize_1 == null) {
			btnRecognize_1 = new JButton("recognize");
			btnRecognize_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Facade.reconhecer(textField_1);
				}
			});
			btnRecognize_1.setBounds(13, 375, 120, 25);
		}
		return btnRecognize_1;
	}
	private JTable getTable_1() {
		if (table_1 == null) {
			table_1 = new JTable();
			table_1.setEnabled(false);
		}
		return table_1;
	}
	private JTable getTable_2() {
		if (table_2 == null) {
			table_2 = new JTable();
			table_2.setEnabled(false);
		}
		return table_2;
	}
	private JButton getRemove_1() {
		if (remove_1 == null) {
			remove_1 = new JButton("X");
			remove_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					table_1.setModel(new DefaultTableModel());
					Facade.removeLeft();
					automatonName1.setText("Automato 1");
				}
			});
			remove_1.setBounds(99, 278, 75, 25);
		}
		return remove_1;
	}
	private JButton getMinimize_1() {
		if (minimize_1 == null) {
			minimize_1 = new JButton("Minimize");
                        minimize_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Facade.minimizar(table_2);
				}
			});
			minimize_1.setBounds(145, 315, 120, 25);
		}
		return minimize_1;
	}
	private JTextField getTextField_3() {
		if (textField_3 == null) {
			textField_3 = new JTextField();
			textField_3.setToolTipText("preencher apenas com numeros!");
			textField_3.setBounds(24, 412, 63, 19);
			textField_3.setColumns(10);
		}
		return textField_3;
	}
	private JLabel getLblint() {
		if (lblint == null) {
			lblint = new JLabel("(int)");
			lblint.setBounds(87, 416, 70, 15);
		}
		return lblint;
	}
	private JCheckBoxMenuItem getChckbxmntmHelp() {
		if (chckbxmntmHelp == null) {
			chckbxmntmHelp = new JCheckBoxMenuItem("Help");
			chckbxmntmHelp.addItemListener(new ItemListener() {
				@SuppressWarnings({ "deprecation" })
				public void itemStateChanged(ItemEvent e) {
					if(chckbxmntmHelp.isSelected())
					{
						help.show(true);
					}
					else
					{
						help.show(false);
					}
				}
			});
		}
		return chckbxmntmHelp;
	}
	private JButton getBtnRe() {
		if (btnRe == null) {
			btnRe = new JButton("RE");
			btnRe.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(addAutomatonER == null || addAutomatonER.isVisible() == false){
						 addAutomatonER = new REinsertWindow(table_1, true);
						 addAutomatonER.show(true);
					}
				}
			});
			btnRe.setBounds(186, 278, 79, 25);
		}
		return btnRe;
	}
}
