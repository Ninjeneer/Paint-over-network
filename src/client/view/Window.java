package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import client.controler.Controler;

public class Window extends JFrame implements ActionListener {

	private JTextArea taTchat;
	private JTextField tfInput;
	private JButton btSend;
	private Canvas drawZone;
	private Menu menu;

	private Controler ctrl;

	public Window(Controler ctrl) {
		this.ctrl = ctrl;

		// propriétés de la fenêtre
		this.setLayout(new BorderLayout(15, 5));
		this.setSize(1000, 600);
		this.setTitle("Idle Tchat");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// création des widgets
		this.menu = new Menu(this);

		this.taTchat = new JTextArea();
		this.taTchat.setEditable(false);
		this.taTchat.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		this.taTchat.setWrapStyleWord(true);
		this.taTchat.setLineWrap(true);

		this.tfInput = new JTextField();
		this.tfInput.addActionListener(this);

		this.btSend = new JButton("Envoyer");
		this.btSend.addActionListener(this);

		this.drawZone = new Canvas();
		this.drawZone.setPreferredSize(new Dimension(400, 600));

		JPanel inputContainer = new JPanel(new BorderLayout());
		inputContainer.add(this.tfInput);
		inputContainer.add(this.btSend, BorderLayout.EAST);

		JScrollPane sp = new JScrollPane(this.taTchat);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		JPanel tchatZone = new JPanel(new BorderLayout());
		tchatZone.add(sp);
		tchatZone.add(inputContainer, BorderLayout.SOUTH);

		// ajout des widgets
		this.add(this.menu, BorderLayout.NORTH);
		this.add(tchatZone);
		this.add(this.drawZone, BorderLayout.EAST);

		this.setVisible(true);
	}

	/**
	 * Traites les interactions IHM
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btSend || e.getSource() == this.tfInput) {
			if (!this.tfInput.getText().equals("")) {
				this.ctrl.getClientManager().sendMessage(this.tfInput.getText());
				this.taTchat.append(this.ctrl.getClientManager().getPseudo() + " : " + this.tfInput.getText() + "\n");
				this.tfInput.setText("");
			}
		}
	}

	/**
	 * Vide la zone de tchat
	 */
	public void clearTchat() {
		this.taTchat.setText("");
	}

	/**
	 * Ajoute un message à la zone de texte
	 *
	 * @param s message
	 */
	public void newMessage(String s) {
		this.taTchat.append(s + "\n");
		this.taTchat.setCaretPosition(this.taTchat.getDocument().getLength());
	}

	/**
	 * Retourne le controleur
	 *
	 * @return controleur
	 */
	public Controler getControler() {
		return this.ctrl;
	}
}
