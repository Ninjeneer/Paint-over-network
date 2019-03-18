package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
	private JList<String> listClient;
	private JButton btSend;
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
		this.listClient = new JList<String>();
		this.listClient.setPreferredSize(new Dimension(150, 600));
		this.btSend = new JButton("Envoyer");
		this.btSend.addActionListener(this);

		JPanel inputContainer = new JPanel(new BorderLayout());
		inputContainer.add(this.tfInput);
		inputContainer.add(this.btSend, BorderLayout.EAST);
		
		JScrollPane sp = new JScrollPane(this.taTchat);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// ajout des widgets
		this.add(this.menu, BorderLayout.NORTH);
		this.add(sp);
		this.add(inputContainer, BorderLayout.SOUTH);
		this.add(this.listClient, BorderLayout.EAST);

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
	 * Met à jour la liste des clients connectés
	 * 
	 * @param clientList liste des clients connectés
	 */
	public void updateConnected(ArrayList<String> clientList) {
		Collections.sort(clientList);
		String[] connectedList = new String[clientList.size()];
		clientList.toArray(connectedList);

		this.listClient.setListData(connectedList);
	}

	/**
	 * Vide la zone de tchat
	 */
	public void clearTchat() {
		this.taTchat.setText("");
		this.listClient.setListData(new String[] {});
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
