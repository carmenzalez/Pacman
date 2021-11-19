package pacman;

/**
 * Imports
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Stroke;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {

    // Tamany de la cel·la
    private int cell;

    // Imatges
    private Image cocoImg;
    private ImageIcon cocoP0;
    private ImageIcon cocoL1, cocoL2;
    private ImageIcon cocoR1, cocoR2;
    private ImageIcon cocoU1, cocoU2;
    private ImageIcon cocoD1, cocoD2;

    // Coco
    Coco coco;

    // Game board (Empty=0, Wall=1, Teleport=2, Special=3)
    private int board[][] = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
                     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
                     {1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1},
                     {1,1,1,1,0,1,0,0,0,0,0,0,0,1,0,1,1,1,1},
                     {1,1,1,1,0,1,0,3,3,3,3,3,0,1,0,1,1,1,1},
                     {2,0,0,0,0,0,0,3,0,0,0,3,0,0,0,0,0,0,2},
                     {1,1,1,1,0,1,0,3,3,3,3,3,0,1,0,1,1,1,1},
                     {1,1,1,1,0,1,0,0,0,0,0,0,0,1,0,1,1,1,1},
                     {1,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                     {1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1},
                     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
                     {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};;

    /**
     * Inicialització de paràmetres i variables
     */
    public Board() {
        setSize(608, 1024);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new Key());
        cell = 32;
        coco = new Coco();
        loadImages();
        setImage(cocoP0);
        //drawImage(cocoImg, coco.getX() * cell + 3, coco.getY() * cell + 3, this);
    }

    /**
     * Carrega les imatges
     */
    private void loadImages() {
        cocoP0 = new ImageIcon(getClass().getResource("../images/coco.png"));
        cocoL1 = new ImageIcon(getClass().getResource("../images/cocol1.png"));
        cocoL2 = new ImageIcon(getClass().getResource("../images/cocol2.png"));
        cocoR1 = new ImageIcon(getClass().getResource("../images/cocor1.png"));
        cocoR2 = new ImageIcon(getClass().getResource("../images/cocor2.png"));
        cocoU1 = new ImageIcon(getClass().getResource("../images/cocou1.png"));
        cocoU2 = new ImageIcon(getClass().getResource("../images/cocou2.png"));
        cocoD1 = new ImageIcon(getClass().getResource("../images/cocod1.png"));
        cocoD2 = new ImageIcon(getClass().getResource("../images/cocod2.png"));
    }

    /**
     * Selector d'imatge del coco
     */
    public void setImage(ImageIcon img) {
        cocoImg = img.getImage();
        repaint();
    }

    /**
     * Dibuixa el laberint del PacMan
     */
    public void drawMaze(Graphics g) {
        // Definim els paràmetres per a dibuixar
        Graphics2D g2d = (Graphics2D) g;
        Stroke stroke = new BasicStroke(2f);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(stroke);

        // Iteració del board per a dibuixar el laberint del PacMan
        for (int j = 0; j < 22; j++) {
            for (int i = 0; i < 19; i++) {
                if (board[j][i] == 1) {
                    g2d.drawRect(i * (cell), j * (cell), 32, 32);
                    g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f)); /* Opacitat */
                    g2d.fillRect(i * (cell), j * (cell), 32, 32);
                    g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
                }
            }
        }

        // Dibuixem la casa dels Ghost(s)
        g2d.drawRect(7*cell,9*cell, 5*cell, 3*cell);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f));
        g2d.fillRect(7*cell,9*cell, 5*cell, 3*cell);
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g2d.drawRect(7*cell+cell/2,9*cell+cell/2, 4*cell, 2*cell);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(7*cell+cell/2,9*cell+cell/2, 4*cell, 2*cell);

        // Dibuixem la porta de la casa dels Ghost(s)
        g2d.fillRect(9 * cell, 9 * cell, cell, cell / 2);
        g2d.setColor(Color.PINK);
        g2d.drawRect(9 * cell, 9 * cell, cell, cell / 2);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f));
        g2d.fillRect(9 * cell, 9 * cell, cell, cell / 2);
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));

    }

    /**
     * Canvia la posició del coco
     */
    public void move(int dir) {
        switch (dir) {
        case 1:
            coco.setX(coco.getX() - 3);
            break;
        case 2:
            coco.setX(coco.getX() + 3);
            break;
        case 3:
            coco.setY(coco.getY() - 3);
            break;
        case 4:
            coco.setY(coco.getY() + 3);
            break;
        }
    }

    public void addFruit() {

    }

    public class Key extends KeyAdapter {

        // Saber quan parar
        boolean stop = false;

        // Indicar posar una imatge o una altra de l'animació (boca oberta o mig oberta)
        boolean anim = true;

        // Comptador que indica el canvi d'imatge d'animació
        int count = 0;

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                new Thread() {
                    public void run() {
                        while(!stop) {
                            move(1);
                            if (anim) {
                                setImage(cocoL1);
                            } else {
                                setImage(cocoL2);
                            }
                            if (count > 3) {
                                anim = !anim;
                                count = 0;
                            }
                            count++;
                        }
                    }
                }.start();
            }

            if (key == KeyEvent.VK_RIGHT) {
                stop = true;
                move(2);
                if (anim) {
                    setImage(cocoR1);
                } else {
                    setImage(cocoR2);
                }
                if (count > 3) {
                    anim = !anim;
                    count = 0;
                }
                count++;
            }

            if (key == KeyEvent.VK_UP) {
                move(3);
                if (anim) {
                    setImage(cocoU1);
                } else {
                    setImage(cocoU2);
                }
                if (count > 3) {
                    anim = !anim;
                    count = 0;
                }
                count++;
            }

            if (key == KeyEvent.VK_DOWN) {
                move(4);
                if (anim) {
                    setImage(cocoD1);
                } else {
                    setImage(cocoD2);
                }
                if (count > 3) {
                    anim = !anim;
                    count = 0;
                }
                count++;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            

                    move(1);
                    if (anim) {
                        setImage(cocoL1);
                    } else {
                        setImage(cocoL2);
                    }
                    if (count > 3) {
                        anim = !anim;
                        count = 0;
                    }
                    count++;
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
        g.drawImage(cocoImg, coco.getX(), coco.getY(), this);
    }

}
