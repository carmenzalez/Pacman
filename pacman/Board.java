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
import javax.swing.Timer;

// MIRAR BOX LAYOUT PARA REDIMENSIONADO

public class Board extends JPanel implements ActionListener {

    // Tamany de la cel·la
    private int cell;

    // Tecla premuda
    private int key;

    // Indicar posar una imatge o una altra de l'animació (boca oberta o mig oberta)
    int anim = 0;

    // Imatges
    private Image cocoImg;
    private ImageIcon cocoP0;
    private ImageIcon cocoL1, cocoL2;
    private ImageIcon cocoR1, cocoR2;
    private ImageIcon cocoU1, cocoU2;
    private ImageIcon cocoD1, cocoD2;

    private Image redGhostImg;
    private ImageIcon redGhostL;
    private ImageIcon redGhostL1, redGhostL2;
    private ImageIcon redGhostR1, redGhostR2;
    private ImageIcon redGhostU1, redGhostU2;
    private ImageIcon redGhostD1, redGhostD2;

    private Image pinkGhostImg;
    private ImageIcon pinkGhostL;
    private ImageIcon pinkGhostR;
    private ImageIcon pinkGhostU;
    private ImageIcon pinkGhostD;

    private Image blueGhostImg;
    private ImageIcon blueGhostL;
    private ImageIcon blueGhostR;
    private ImageIcon blueGhostU;
    private ImageIcon blueGhostD;

    private Image orangeGhostImg;
    private ImageIcon orangeGhostL;
    private ImageIcon orangeGhostR;
    private ImageIcon orangeGhostU;
    private ImageIcon orangeGhostD;

    private ImageIcon ghostW;
    private ImageIcon ghostB;
    private ImageIcon ghostWB;

    private Image dotImg;
    private ImageIcon dotYes, dotNo;

    // Coco
    Entity coco;

    // Fantasmes
    Entity redGhost;
    Entity pinkGhost;
    Entity blueGhost;
    Entity orangeGhost;

    // Punts
    Entity dot;

    // Puntuació
    int score;

    // Timer per a moure el coco
    Timer timer = new Timer(20, this);

    // Timer per a animar el coco
    Timer mov = new Timer(90, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (key == KeyEvent.VK_LEFT) {
                if (cocoImg == cocoL1.getImage()) {
                    setImage(cocoL2);
                } else {
                    setImage(cocoL1);
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (cocoImg == cocoR1.getImage()) {
                    setImage(cocoR2);
                } else {
                    setImage(cocoR1);
                }
            } else if (key == KeyEvent.VK_UP) {
                if (cocoImg == cocoU1.getImage()) {
                    setImage(cocoU2);
                } else {
                    setImage(cocoU1);
                }
            } else if (key == KeyEvent.VK_DOWN) {
                if (cocoImg == cocoD1.getImage()) {
                    setImage(cocoD2);
                } else {
                    setImage(cocoD1);
                }
            }
        }
    });

    // Game board (Dot=0, Wall=1, Teleport=2, Special=3, Empty=8)
    private int board[][] = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,0,1},
                     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
                     {1,1,1,1,0,1,1,1,8,1,8,1,1,1,0,1,1,1,1},
                     {1,1,1,1,0,1,8,8,8,8,8,8,8,1,0,1,1,1,1},
                     {1,1,1,1,0,1,8,1,1,1,1,1,8,1,0,1,1,1,1},
                     {2,8,8,8,0,8,8,1,0,0,0,1,8,8,0,8,8,8,2},
                     {1,1,1,1,0,1,8,1,1,1,1,1,8,1,0,1,1,1,1},
                     {1,1,1,1,0,1,8,8,8,8,8,8,8,1,0,1,1,1,1},
                     {1,1,1,1,0,1,8,1,1,1,1,1,8,1,0,1,1,1,1},
                     {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                     {1,0,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,0,1},
                     {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                     {1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,1},
                     {1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,1},
                     {1,0,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1},
                     {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                     {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};;

    public Board() {
        config();
        initVar();
        addKeyListener(new Key());
    }

    /**
     * Configurem el Board (JPanel)
     */
    public void config() {
        setSize(608, 704);
        setBackground(Color.BLACK);
        setFocusable(true);
        mov.start();
    }

    /**
     * Definim les variables necessaries
     */
    public void initVar() {
        cell = 32;
        coco = new Entity("coco");
        redGhost = new Entity("red");
        pinkGhost = new Entity("pink");
        blueGhost = new Entity("blue");
        orangeGhost = new Entity("orange");

        loadImages();
        setImage(cocoP0);
        redGhostImg = redGhostL.getImage();
        pinkGhostImg = pinkGhostL.getImage();
        blueGhostImg = blueGhostL.getImage();
        orangeGhostImg = orangeGhostL.getImage();
        dotImg = dotYes.getImage();

        score = 0;
    }

    /**
     * Carrega les imatges
     */
    private void loadImages() {
        // Coco
        cocoP0 = new ImageIcon(getClass().getResource("../images/coco.png"));
        cocoL1 = new ImageIcon(getClass().getResource("../images/cocol1.png"));
        cocoL2 = new ImageIcon(getClass().getResource("../images/cocol2.png"));
        cocoR1 = new ImageIcon(getClass().getResource("../images/cocor1.png"));
        cocoR2 = new ImageIcon(getClass().getResource("../images/cocor2.png"));
        cocoU1 = new ImageIcon(getClass().getResource("../images/cocou1.png"));
        cocoU2 = new ImageIcon(getClass().getResource("../images/cocou2.png"));
        cocoD1 = new ImageIcon(getClass().getResource("../images/cocod1.png"));
        cocoD2 = new ImageIcon(getClass().getResource("../images/cocod2.png"));

        // Red Ghost
        redGhostL = new ImageIcon(getClass().getResource("../images/coco.png"));
        redGhostL2 = new ImageIcon(getClass().getResource("../images/cocol2.png"));
        redGhostR1 = new ImageIcon(getClass().getResource("../images/cocor1.png"));
        redGhostR2 = new ImageIcon(getClass().getResource("../images/cocor2.png"));
        redGhostU1 = new ImageIcon(getClass().getResource("../images/cocou1.png"));
        redGhostU2 = new ImageIcon(getClass().getResource("../images/cocou2.png"));
        redGhostD1 = new ImageIcon(getClass().getResource("../images/cocod1.png"));
        redGhostD2 = new ImageIcon(getClass().getResource("../images/cocod2.png"));

        // Pink Ghost
        pinkGhostL = new ImageIcon(getClass().getResource("../images/coco.png"));

        // Blue Ghost
        blueGhostL = new ImageIcon(getClass().getResource("../images/coco.png"));

        // Orange Ghost
        orangeGhostL = new ImageIcon(getClass().getResource("../images/coco.png"));

        // Punts
        dotYes = new ImageIcon(getClass().getResource("../images/dot.png"));
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
                } else if (board[j][i] == 0) {
                    dot = new Entity(i,j);
                    g.drawImage(dotImg, dot.getX(), dot.getY(), this);
                    score = score + 10;

                }
            }
        }

        /*// Dibuixem la casa dels Ghost(s)
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
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));*/

    }

    /**
     * Canvia la posició del coco
     */
    public void move(int dir) {
        switch (dir) {
            case 1:
                //nextProbPos = ((coco.getX() - 12)/cell)%19;
                if (board[(coco.getY()/cell)%22][((coco.getX() - 3)/cell)%19] != 0 && board[(coco.getY()/cell)%22][((coco.getX() - 3)/cell)%19] != 8) {
                    timer.stop();
                } else {
                    coco.setX(coco.getX() - 3);
                    board[(coco.getY()/cell)%22][((coco.getX() - 3)/cell)%19] = 8;
                }
                break;
            case 2:
                if (board[(coco.getY()/cell)%22][((coco.getX() + 3 + 26)/cell)%19] != 0) {
                    timer.stop();
                } else {
                    coco.setX(coco.getX() + 3);
                }
                break;
            case 3:
                if (board[((coco.getY() - 3)/cell)%22][(coco.getX()/cell)%19] != 0) {
                    timer.stop();
                } else {
                    coco.setY(coco.getY() - 3);
                }
                break;
            case 4:
                if (board[((coco.getY() + 3 + 26)/cell)%22][(coco.getX()/cell)%19] != 0) {
                    timer.stop();
                } else {
                    coco.setY(coco.getY() + 3);
                }
                break;
        }
    }

    public void addFruit() {

    }

    public class Key extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT ||
            key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                timer.restart();
            } else {
                timer.stop();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (key == KeyEvent.VK_LEFT) {
            move(1);
        }

        if (key == KeyEvent.VK_RIGHT) {
            move(2);
        }

        if (key == KeyEvent.VK_UP) {
            move(3);
        }

        if (key == KeyEvent.VK_DOWN) {
            move(4);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
        g.drawImage(cocoImg, coco.getX(), coco.getY(), this);
        g.drawImage(redGhostImg, redGhost.getX(), redGhost.getY(), this);
        g.drawImage(pinkGhostImg, pinkGhost.getX(), pinkGhost.getY(), this);
        g.drawImage(blueGhostImg, blueGhost.getX(), blueGhost.getY(), this);
        g.drawImage(orangeGhostImg, orangeGhost.getX(), orangeGhost.getY(), this);
    }

}
