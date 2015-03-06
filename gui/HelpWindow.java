package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class HelpWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea txtrinstruesantesDe;

	public HelpWindow() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTxtrinstruesantesDe());
		}
		return scrollPane;
	}
	private JTextArea getTxtrinstruesantesDe() {
		if (txtrinstruesantesDe == null) {
			txtrinstruesantesDe = new JTextArea();
			txtrinstruesantesDe.setFont(new Font("Dialog", Font.BOLD, 12));
			txtrinstruesantesDe.setText("*INSTRUÇÕES*\n\n*Antes de apertar OK clique fora da tabela ou aperte enter!*\n\nEstados:\nq0, q1 .. q14..qn\nO primeiro estado será sempre o inicial\n*qn para estados finais\n\nSimbolos:\na, b, c, ..., 1, 2, 3 ... (tamanho 1)\n\nTransições (exemplos):\nq1\nq2,q3,... (sem espaços)\nq3,q2 (ERRADO! sempre ordenado de forma crescente) \n");
			txtrinstruesantesDe.setEnabled(false);
			txtrinstruesantesDe.setEditable(false);
		}
		return txtrinstruesantesDe;
	}
}
