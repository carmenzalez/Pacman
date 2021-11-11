package PacMan;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/*
Tamaño Bloque = 15
Mapa puntos = 26 * 30
Mapa Total = 30 * 40
*/

public class Board extends JPanel {

    public Board() {
        setSize(450, 600);
        setBackground(Color.BLACK);
    }

    public void drawMaze(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3f);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(stroke);
        g2d.drawLine(8, 83, 442, 83);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
    }

}
