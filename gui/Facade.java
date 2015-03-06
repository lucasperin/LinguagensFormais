package gui;

import java.awt.TextField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import expressaoRegular.ExpressionAutomatonBuilder;

import automato.Automato;
import automato.Estado;
import automato.Transicao;

public class Facade {

	public static Automato autLeft, autRight;
	public static Map<String, Automato> salvos = new HashMap<>();

	public static boolean create(boolean left, JTable table) {
		// criando nome
		String _name = (String) table.getValueAt(0, 0);

		// criando e validando alfabeto
		ArrayList<String> _alphabet = new ArrayList<String>();
		for (int i = 1; i < table.getColumnCount(); i++) {
			if (table.getValueAt(0, i) != null) {
				if (((String) table.getModel().getValueAt(0, i)).length() > 0) {
					char simbol = ((String) table.getModel().getValueAt(0, i))
							.charAt(0);
					_alphabet.add(String.valueOf(simbol));
				}
			}
		}
		if (alphabetIsValid(_alphabet) == false) {
			return false;
		}

		// Criando estados
		ArrayList<Estado> _states = new ArrayList<Estado>();
		for (int i = 1; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 0) != null) {
				if (((String) table.getValueAt(i, 0)).length() > 0) {
					boolean isFinal = false;
					String name = (String) table.getValueAt(i, 0);
					if (name.charAt(0) == '*') {
						name = name.substring(1);
						isFinal = true;
					}
					_states.add(new Estado(isFinal, name));
				}
			}
		}
		if (statesAreValid(_states) == false) {
			return false;
		}

		// Criando transicoes
		for (int stateIndex = 1; stateIndex < table.getRowCount(); stateIndex++) {
			for (int simbolIndex = 1; simbolIndex < table.getColumnCount(); simbolIndex++) {
				if (table.getValueAt(stateIndex, simbolIndex) != null) {
					if (((String) table.getValueAt(stateIndex, simbolIndex))
							.length() > 0) {

						Estado destiny = null;
						String transitions = (String) table.getValueAt(
								stateIndex, simbolIndex);
						String destinyName;
						// if(transitions.length() > 3)
						if (transitions.contains(","))
							destinyName = transitions.substring(0,
									transitions.indexOf(","));
						else
							destinyName = transitions;

						destiny = getState(_states, destinyName);
						if (destiny == null)
							return false;

						_states.get(stateIndex - 1).adicionarTransicao(
								new Transicao(_alphabet.get(simbolIndex - 1),
										destiny));

						while (transitions.contains(",")) {
							transitions = transitions.substring(transitions
									.indexOf(",") + 1);

							if (transitions.contains(","))
								destinyName = transitions.substring(0,
										transitions.indexOf(","));
							else
								destinyName = transitions;

							destiny = getState(_states, destinyName);
							if (destiny == null)
								return false;

							_states.get(stateIndex - 1).adicionarTransicao(
									new Transicao(_alphabet
											.get(simbolIndex - 1), destiny));

						}
					}
				}
			}
		}
		if (left) {
			autLeft = new Automato(_states, _alphabet);
			salvos.put(_name, autLeft);
			MainWindow.updateComboBox(_name);
			showTable(table, true);
		} else {
			autRight = new Automato(_states, _alphabet);
			salvos.put(_name, autRight);
		}
		return true;
	}

	private static boolean alphabetIsValid(ArrayList<String> alphabet) {
		System.out.println(alphabet);
		return true;
	}

	private static boolean statesAreValid(ArrayList<Estado> states) {
		return true;
	}

	private static Estado getState(ArrayList<Estado> states, String name) {
		for (Estado s : states) {
			if (s.getNome().equals(name))
				return s;
		}
		return null;
	}

	public static void removeRight() {
		autRight = null;

	}

	public static void removeLeft() {
		autLeft = null;
	}

	public static void showTable(JTable mainWindowTable, boolean left) {
		Automato aut;
		if (left)
			aut = autLeft;
		else
			aut = autRight;

		// Criando mainWindowTable
		// String nomes = new String[aut.alphabet.size()];
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("States");
		for (String s : aut.getAlfabeto())
			model.addColumn("Simbol");
		model.addRow(new Object[aut.getAlfabeto().size()]);
		for (Estado s : aut.getEstados())
			model.addRow(new Object[aut.getAlfabeto().size()]);
		mainWindowTable.setModel(model);

		// set lable
		// if(left)
		// MainWindow.automatonName1.setText(aut.name);
		// else
		// MainWindow.automatonName2.setText(aut.name);
		//
		// //set name
		mainWindowTable.getModel().setValueAt("########", 0, 0);

		// set alphabet

		String temp;
		for (int i = 1; i <= aut.getAlfabeto().size(); i++) {

			temp = aut.getAlfabeto().get(i - 1) + "";

			mainWindowTable.getModel().setValueAt(temp, 0, i);
		}

		// set states & transitions
		for (int i = 1; i <= aut.getEstados().size(); i++) { // i - > estados
			if (aut.getEstados().get(i - 1).isEstadoFinal())
				temp = "*" + aut.getEstados().get(i - 1).getNome();
			else
				temp = aut.getEstados().get(i - 1).getNome();
			mainWindowTable.getModel().setValueAt(temp, i, 0);
			for (int j = 1; j <= aut.getAlfabeto().size(); j++) { // j ->
																	// simbolos
				temp = "";
				char simbolo = aut.getAlfabeto().get(j - 1).charAt(0);
				for (Transicao t : aut.getEstados().get(i - 1).getTransicoes())
					if (t.getSimbolo().compareTo(String.valueOf(simbolo)) == 0)
						temp += "," + t.getDestino().getNome();

				if (temp.length() > 0) {
					temp = temp.substring(1);
					mainWindowTable.getModel().setValueAt(temp, i, j);
				}

			}
		}
	}

	public static void usarDireita() {
		autLeft = autRight;
		autRight = null;
	}

	public static void determinizar(JTable table) {
		if (autLeft != null) {
			autRight = autLeft.copy();
			autRight.determinizar();
			showTable(table, false);
		}

	}

	public static void minimizar(JTable table) {
		if (autLeft != null) {
			autRight = autLeft.copy();
			autRight.minimizar();
			showTable(table, false);
		}
	}

	public static void reconhecer(JTextField textField) {
		if (autLeft != null) {
			ResultWindow result = null;
			if (autLeft.copy().reconhecer(textField.getText())) {
				result = new ResultWindow(
						"Sentença reconhecida com SUCESSO :)");
			} else {
				result = new ResultWindow("Sentença NÃO reconhecida :(");
			}

			result.show();
		}
	}

	public static void gerarSentencas(JTextField textField) {
		if (autLeft != null) {
			ResultWindow result = null;
			result = new ResultWindow(autLeft.copy().gerarSentencas(
					Integer.parseInt(textField.getText())).toString());
			result.show();
		}
	}

	public static void verificarEquivalencia(JTextField txtRe, JTextField txtRe2) {
		Automato A = new ExpressionAutomatonBuilder(txtRe.getText());
		Automato B = new ExpressionAutomatonBuilder(txtRe2.getText());
		ResultWindow result = null;
		
		if(A.ehEquivalente(B)){
				result = new ResultWindow("ERs SÃO equivalentes");
		} else {
			result = new ResultWindow("ERs NÃO são equivalentes");
		}
		result.show();
	}

	public static void trocarAutomato(JTable table, String name) {
		if (salvos.containsKey(name)) {
			autLeft = salvos.get(name);
			showTable(table, true);
		}
	}

}
