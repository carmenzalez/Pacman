package pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

/*
Tamaño Bloque = cell
Mapa puntos = 28 * 31
Mapa Total = 30 * 41
*/

public class Board extends JPanel {

    //Cell size (15 pixels) 
    private int cell = 15;

    //Images
    private ImageIcon cocoX;
    private ImageIcon cocoL1, cocoL2;
    private ImageIcon cocoR1, cocoR2;
    private ImageIcon cocoU1, cocoU2;
    private ImageIcon cocoD1, cocoD2;

    //Coco
    JLabel coco;

    //Game board (Empty=0, Wall=1, Teleport=2, Special=3)
    private int board[][] = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                     {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                     {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
                     {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
                     {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
                     {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
                     {0,0,0,0,0,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,0,0,0,0,0},
                     {0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0},
                     {0,0,0,0,0,1,0,1,1,0,1,1,1,3,3,1,1,1,0,1,1,0,1,0,0,0,0,0},
                     {1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1},
                     {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
                     {1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1},
                     {0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0},
                     {0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0},
                     {0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0},
                     {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                     {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                     {1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
                     {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
                     {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
                     {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
                     {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
                     {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};;

    public Board() {
        setSize(450,615);
        setBackground(Color.BLACK);
        loadImages();

        /**
         * Prova afegir coco
         * Veure com queda
         **/
        coco = new JLabel(cocoX);
        add(coco);
        setLayout(null);
        coco.setBounds(9*cell,15*cell,15,15);
    }

    private void loadImages() {
        //try {
            cocoX = new ImageIcon(getClass().getResource("../images/coco.png"));
            /*cocoX = new ImageIcon(ImageIO.read(new File("../images/coco.png")));
            cocoL1 = new ImageIcon(ImageIO.read(new File("../images/cocol1.png")));
            cocoL2 = new ImageIcon(ImageIO.read(new File("../images/cocol2.png")));
            cocoR1 = new ImageIcon(ImageIO.read(new File("../images/cocor1.png")));
            cocoR2 = new ImageIcon(ImageIO.read(new File("../images/cocor2.png")));
            cocoU1 = new ImageIcon(ImageIO.read(new File("../images/cocou1.png")));
            cocoU2 = new ImageIcon(ImageIO.read(new File("../images/cocou2.png")));
            cocoD1 = new ImageIcon(ImageIO.read(new File("../images/cocod1.png")));
            cocoD2 = new ImageIcon(ImageIO.read(new File("../images/cocod2.png")));*/
        /*} catch (IOException ex) {
            System.out.println("One or more images have failed to load, please try again");
        }*/
    }


    public void drawMaze(Graphics g) {
        int aux = 0;
        boolean first = true;
        Graphics2D g2d = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3f);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(stroke);
        for (int i=0; i<31; i++) {
            for (int j=0; j<28; j++) {
                /*g2d.setColor(Color.WHITE);
                g2d.drawLine(j*cell+8,i*cell+8,j*cell+8,i*cell+8);
                g2d.setColor(Color.BLUE);*/
                if (board[i][j]==1 && first) {
                    aux = j;
                    first = false;
                }
                if (board[i][j]==0 && !first) {
                    g2d.drawLine(aux*cell+8,i*cell+8,(j-1)*cell+8,i*cell+8);
                    first = true;
                }
                if (board[i][j]==1 && j == 27) {
                    g2d.drawLine(aux*cell+8,i*cell+8,j*cell+8,i*cell+8);
                    first = true;
                }
            }
        }
        for (int j=0; j<28; j++) {
            for (int i=0; i<31; i++) {
                if (board[i][j]==1 && first) {
                    aux = i;
                    first = false;
                }
                if (board[i][j]==0 && !first) {
                    g2d.drawLine(j*cell+8,aux*cell+8,j*cell+8,(i-1)*cell+8);
                    first = true;
                }
                if (board[i][j]==1 && i == 30) {
                    g2d.drawLine(j*cell+8,aux*cell+8,j*cell+8,i*cell+8);
                    first = true;
                }
            }
        }
        g2d.setColor(Color.PINK);
        g2d.drawLine(13*cell+8,12*cell+8,14*cell+8,12*cell+8);
        g2d.drawLine(13*cell+8,14*cell+8,13*cell+8,14*cell+8);
    }

    public void addFruit() {

    }



    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
    }

}
