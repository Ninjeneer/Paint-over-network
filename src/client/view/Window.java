package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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
	private DrawZone drawZone;
	private Menu menu;
	private JPanel drawPan;
	private ToolBar toolBar;

	private Controler ctrl;

	public Window(Controler ctrl) {
		this.ctrl = ctrl;

		// propriétés de la fenêtre
		this.setLayout(new BorderLayout(15, 5));
		this.setSize(1000, 600);
		this.setTitle("Projet prog répartie");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// création du menu
		this.menu = new Menu(this);

		// création de la zone de tchat
		this.taTchat = new JTextArea();
		this.taTchat.setEditable(false);
		this.taTchat.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		this.taTchat.setWrapStyleWord(true);
		this.taTchat.setLineWrap(true);

		// création du champs de texte
		this.tfInput = new JTextField();
		this.tfInput.addActionListener(this);

		// création du bouton d'envoie
		this.btSend = new JButton("Envoyer");
		this.btSend.addActionListener(this);

		// création de la zone de dessin
		this.drawZone = new DrawZone(this.ctrl);
		this.drawZone.setPreferredSize(new Dimension(400, 600));

		JPanel inputContainer = new JPanel(new BorderLayout());
		inputContainer.add(this.tfInput);
		inputContainer.add(this.btSend, BorderLayout.EAST);

		JScrollPane sp = new JScrollPane(this.taTchat);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// mise en page de la zone de tchat
		JPanel tchatPan = new JPanel(new BorderLayout());
		tchatPan.add(sp);
		tchatPan.add(inputContainer, BorderLayout.SOUTH);

		// mise en page de la zone de dessin
		this.drawPan = new JPanel(new BorderLayout());
		drawPan.add(this.drawZone);
		this.toolBar = new ToolBar(this);
		drawPan.add(this.toolBar, BorderLayout.NORTH);

		// ajout des widgets
		this.add(this.menu, BorderLayout.NORTH);
		this.add(tchatPan);
		this.add(drawPan, BorderLayout.EAST);

		activateDrawZone(false);
		activateTchatZone(false);

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


	public Controler getControler() {
		return this.ctrl;
	}


	public DrawZone getDrawZone() {
		return this.drawZone;
	}

	/**
	 * Active et désactive la totalité de la zone de dessin
	 * @param b vrai pour activer
	 */
	public void activateDrawZone(boolean b) {
		this.toolBar.squareTool.setEnabled(b);
		this.toolBar.circleTool.setEnabled(b);
		this.toolBar.triangleTool.setEnabled(b);
		this.toolBar.colorChooser.setEnabled(b);
		this.toolBar.sizeSlider.setEnabled(b);
		this.toolBar.undo.setEnabled(b);
		this.drawZone.setEnabled(b);
	}

	/**
	 * Active et désactive la totalité de la zone de tchat
	 * @param b vrai pour activer
	 */
	public void activateTchatZone(boolean b) {
		this.taTchat.setEnabled(b);
		this.tfInput.setEnabled(b);
		this.btSend.setEnabled(b);
	}
}
