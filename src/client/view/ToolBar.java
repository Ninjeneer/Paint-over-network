package client.view;

import client.model.DrawTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {

    private Window w;
    private JButton squareTool;
    private JButton circleTool;
    private JButton colorChooser;

    public ToolBar(Window w) {
        this.w = w;
        this.setLayout(new GridLayout(1, 3));

        this.squareTool = new JButton("Carré");
        this.circleTool = new JButton("Cercle");
        this.colorChooser = new JButton("Couleur...");

        this.squareTool.addActionListener(this);
        this.circleTool.addActionListener(this);
        this.colorChooser.addActionListener(this);

        this.add(this.squareTool);
        this.add(this.circleTool);
        this.add(this.colorChooser);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.colorChooser) {
            this.w.getControler().setDrawColor(JColorChooser.showDialog(this, "Choisissez la couleur de votre pinceau", Color.BLACK));

            if (this.w.getControler().getDrawColor() != null)
                this.colorChooser.setBackground(this.w.getControler().getDrawColor());
        }

        if (actionEvent.getSource() == this.squareTool) {
            this.w.getControler().setTool(DrawTool.SQUARE);
            this.squareTool.setEnabled(false);
            this.circleTool.setEnabled(true);
        }

        if (actionEvent.getSource() == this.circleTool) {
            this.w.getControler().setTool(DrawTool.CIRCLE);
            this.circleTool.setEnabled(false);
            this.squareTool.setEnabled(true);
        }
    }
}
