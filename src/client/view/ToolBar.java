package client.view;

import client.model.DrawTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener, ChangeListener {

    private Window w;
    JButton squareTool;
    JButton circleTool;
    JButton colorChooser;
    JSlider sizeSlider;

    public ToolBar(Window w) {
        this.w = w;
        this.setLayout(new GridLayout(2, 1));
        JPanel toolsContainer = new JPanel(new GridLayout(1, 3));

        this.squareTool = new JButton("Carré");
        this.circleTool = new JButton("Cercle");
        this.circleTool.setEnabled(false);
        this.colorChooser = new JButton("Couleur...");

        this.squareTool.addActionListener(this);
        this.circleTool.addActionListener(this);
        this.colorChooser.addActionListener(this);

        this.sizeSlider = new JSlider(1, 100);
        this.sizeSlider.setValue(10);
        this.sizeSlider.addChangeListener(this);
        this.w.getControler().setDrawSize(this.sizeSlider.getValue());

        toolsContainer.add(this.squareTool);
        toolsContainer.add(this.circleTool);
        toolsContainer.add(this.colorChooser);

        this.add(toolsContainer);
        this.add(this.sizeSlider);
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

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        if (changeEvent.getSource() == this.sizeSlider)
            this.w.getControler().setDrawSize(this.sizeSlider.getValue());
    }
}
