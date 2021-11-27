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

import java.lang.Math;

// MIRAR BOX LAYOUT PARA REDIMENSIONADO

public class Board extends JPanel implements ActionListener {

    // Tamany de la cel·la
    private int cell;

    // Tecla premuda
    private int key;

    private int rRight;

    // Imatges
    private Image cocoImg;
    private ImageIcon cocoP0;
    private ImageIcon cocoL;
    private ImageIcon cocoR;
    private ImageIcon cocoU;
    private ImageIcon cocoD;

    private Image redGhostImg;
    private ImageIcon redGhostL;
    private ImageIcon redGhostR;
    private ImageIcon redGhostU;
    private ImageIcon redGhostD;

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
    private ImageIcon dotV;

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

    // Timer fantasmes
    Timer gTimer = new Timer(60000, this);

    /**
     * Tauler del joc
     * 0: Cel·la amb punt
     * 1: Paret
     * 2: Teletransport
     * 8: Cel·la sense punt
     */
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
                     {2,0,0,0,0,8,8,1,0,0,0,1,8,8,0,0,0,0,2},
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
        setImage("coco", cocoP0);
        setImage("redGhost", redGhostL);
        setImage("blueGhost", blueGhostU);
        setImage("pinkGhost", pinkGhostU);
        setImage("orangeGhost", orangeGhostU);
        setImage("dot", dotV);

        score = 0;
    }

    /**
     * Carrega les imatges
     */
    private void loadImages() {
        // Coco
        cocoP0 = new ImageIcon(getClass().getResource("../images/coco.png"));
        cocoL = new ImageIcon(getClass().getResource("../images/cocoL.gif"));
        cocoR = new ImageIcon(getClass().getResource("../images/cocoR.gif"));
        cocoU = new ImageIcon(getClass().getResource("../images/cocoU.gif"));
        cocoD = new ImageIcon(getClass().getResource("../images/cocoD.gif"));

        // Red Ghost
        redGhostL = new ImageIcon(getClass().getResource("../images/redGhostL.gif"));
        redGhostR = new ImageIcon(getClass().getResource("../images/redGhostR.gif"));
        redGhostU = new ImageIcon(getClass().getResource("../images/redGhostU.gif"));
        redGhostD = new ImageIcon(getClass().getResource("../images/redGhostD.gif"));

        // Pink Ghost
        pinkGhostL = new ImageIcon(getClass().getResource("../images/pinkGhostL.gif"));
        pinkGhostR = new ImageIcon(getClass().getResource("../images/pinkGhostR.gif"));
        pinkGhostU = new ImageIcon(getClass().getResource("../images/pinkGhostU.gif"));
        pinkGhostD = new ImageIcon(getClass().getResource("../images/pinkGhostD.gif"));

        // Blue Ghost
        blueGhostL = new ImageIcon(getClass().getResource("../images/blueGhostL.gif"));
        blueGhostR = new ImageIcon(getClass().getResource("../images/blueGhostR.gif"));
        blueGhostU = new ImageIcon(getClass().getResource("../images/blueGhostU.gif"));
        blueGhostD = new ImageIcon(getClass().getResource("../images/blueGhostD.gif"));

        // Orange Ghost
        orangeGhostL = new ImageIcon(getClass().getResource("../images/orangeGhostL.gif"));
        orangeGhostR = new ImageIcon(getClass().getResource("../images/orangeGhostR.gif"));
        orangeGhostU = new ImageIcon(getClass().getResource("../images/orangeGhostU.gif"));
        orangeGhostD = new ImageIcon(getClass().getResource("../images/orangeGhostD.gif"));

        // Punts
        dotV = new ImageIcon(getClass().getResource("../images/dot.png"));
    }

    /**
     * Selector d'imatge d'una entitat per a crear la seva animació
     */
    public void setImage(String entity, ImageIcon img) {
        switch (entity) {
            case "coco":
                cocoImg = img.getImage();
                break;

            case "redGhost":
                redGhostImg = img.getImage();
                break;

            case "blueGhost":
                blueGhostImg = img.getImage();
                break;

            case "pinkGhost":
                pinkGhostImg = img.getImage();
                break;

            case "orangeGhost":
                orangeGhostImg = img.getImage();
                break;

            case "dot":
                dotImg = img.getImage();
                break;
        }
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
    }

    /**
     * Canvia la posició del coco
     * 0: Cel·la amb punt
     * 3: Píxels que es mou el coco per cada tic del rellotge, per comprobar que hi ha on es mou (a dalt i esquerra)
     * 8: Cel·la sense punt
     * 16: Meitat d'una cel·la, per fer desapareixer el punt quan passi el coco pel mig de la cel·la
     * 20: Tamany del coco menys 6, per a deixar marge al canviar la direcció
     * 29: Tamany del coco + 3, per comprobar que hi ha on es mou (a baix i dreta)
     */
    public void move(int dir) {
        switch (dir) {
            case 1:
                if (((board[(coco.getY()+20)/cell][(coco.getX() - 3)/cell] | board[(coco.getY())/cell][(coco.getX() - 3)/cell]) == 0)
                || (board[(coco.getY()+20)/cell][(coco.getX() - 3)/cell] | board[(coco.getY())/cell][(coco.getX() - 3)/cell]) == 8) {
                    coco.setX(coco.getX() - 3);
                    if(board[coco.getY()/cell][(coco.getX() - 3)/cell] == 0 && ((coco.getX() - 3)%cell) <= 16) {
                        board[coco.getY()/cell][coco.getX()/cell] = 8;
                    }
                }
                break;
            case 2:
                if (((board[(coco.getY()+20)/cell][(coco.getX() + 29)/cell] | board[(coco.getY())/cell][(coco.getX() + 29)/cell]) == 0)
                || (board[(coco.getY()+20)/cell][(coco.getX() + 29)/cell] | board[(coco.getY())/cell][(coco.getX() + 29)/cell]) == 8) {
                    coco.setX(coco.getX() + 3);
                    if(board[coco.getY()/cell][(coco.getX() + 3)/cell] == 0 && ((coco.getX() + 3)%cell) <= 16) {
                        board[coco.getY()/cell][coco.getX()/cell] = 8;
                    }
                }
                break;
            case 3:
                if (((board[(coco.getY() - 3)/cell][(coco.getX()+20)/cell] | board[(coco.getY() - 3)/cell][(coco.getX())/cell]) == 0)
                || (board[(coco.getY() - 3)/cell][(coco.getX()+20)/cell] | board[(coco.getY() - 3)/cell][(coco.getX())/cell]) == 8) {
                    coco.setY(coco.getY() - 3);
                    if(board[(coco.getY() - 3)/cell][coco.getX()/cell] == 0 && ((coco.getY() - 3)%cell) <= 16) {
                        board[coco.getY()/cell][coco.getX()/cell] = 8;
                    }
                }
                break;
            case 4:
                if (((board[(coco.getY() + 29)/cell][(coco.getX()+20)/cell] | board[(coco.getY() + 29)/cell][(coco.getX())/cell]) == 0)
                || (board[(coco.getY() + 29)/cell][(coco.getX()+20)/cell] | board[(coco.getY() + 29)/cell][(coco.getX())/cell]) == 8) {
                    coco.setY(coco.getY() + 3);
                    if(board[(coco.getY() + 3)/cell][coco.getX()/cell] == 0 && ((coco.getY() + 3)%cell) <= 16) {
                        board[coco.getY()/cell][coco.getX()/cell] = 8;
                    }
                }
                break;
        }
    }

    public void moveRed() {
        int calX = coco.getX() - redGhost.getX();
        int calY = coco.getY() - redGhost.getY();
        if (Math.abs(calX) <= Math.abs(calY)) {
            System.out.println("y");
            if (calY <= 0) {
                if ((board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                    redGhost.setY(redGhost.getY() - 3);
                    setImage("redGhost", redGhostU);
                } else if (calX <= 0) {
                    if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() - 3)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() - 3)/cell)%19]) != 1) {
                        redGhost.setX(redGhost.getX() - 3);
                        setImage("redGhost", redGhostL);
                    }
                } else if (calX > 0) {
                    if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19]) != 1) {
                        redGhost.setX(redGhost.getX() + 3);
                        setImage("redGhost", redGhostR);
                    }
                }
            } else {
                if ((board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                    redGhost.setY(redGhost.getY() + 3);
                    setImage("redGhost", redGhostD);
                } else if (calX <= 0) {
                    if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() - 3)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() - 3)/cell)%19]) != 1) {
                        redGhost.setX(redGhost.getX() - 3);
                        setImage("redGhost", redGhostL);
                    }
                } else if (calX > 0) {
                    if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19]) != 1) {
                        redGhost.setX(redGhost.getX() + 3);
                        setImage("redGhost", redGhostR);
                    }
                }
            }
        } else {
            System.out.println("x");
            if (calX <= 0) {
                if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() - 3)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() - 3)/cell)%19]) != 1) {
                    redGhost.setX(redGhost.getX() - 3);
                    setImage("redGhost", redGhostL);
                } else if (calY <= 0) {
                    if ((board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                        redGhost.setY(redGhost.getY() - 3);
                        setImage("redGhost", redGhostU);
                    }
                } else if (calY > 0) {
                    if ((board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                        redGhost.setY(redGhost.getY() + 3);
                        setImage("redGhost", redGhostD);
                    }
                }
            } else {
                if ((board[((redGhost.getY()+21)/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19] | board[((redGhost.getY())/cell)%22][((redGhost.getX() + 3 + 28)/cell)%19]) != 1) {
                    redGhost.setX(redGhost.getX() + 3);
                    setImage("redGhost", redGhostR);
                } else if (calY <= 0) {
                    if ((board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() - 3)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                        redGhost.setY(redGhost.getY() - 3);
                        setImage("redGhost", redGhostU);
                    }
                } else if (calY > 0) {
                    if ((board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX()+21)/cell)%19] | board[((redGhost.getY() + 3 + 28)/cell)%22][((redGhost.getX())/cell)%19]) != 1) {
                        redGhost.setY(redGhost.getY() + 3);
                        setImage("redGhost", redGhostD);
                    }
                }
            }
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
            setImage("coco", cocoL);
        }

        if (key == KeyEvent.VK_RIGHT) {
            move(2);
            setImage("coco", cocoR);
        }

        if (key == KeyEvent.VK_UP) {
            move(3);
            setImage("coco", cocoU);
        }

        if (key == KeyEvent.VK_DOWN) {
            move(4);
            setImage("coco", cocoD);
        }

        // Fantasma Vermell
        moveRed();

        // Fantasma Blau
        //moveB();

        if ((redGhost.getY()/cell == coco.getY()/cell) && (redGhost.getX()/cell == coco.getX()/cell)) {
            timer.stop();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
        g.drawImage(redGhostImg, redGhost.getX(), redGhost.getY(), this);
        g.drawImage(pinkGhostImg, pinkGhost.getX(), pinkGhost.getY(), this);
        g.drawImage(blueGhostImg, blueGhost.getX(), blueGhost.getY(), this);
        g.drawImage(orangeGhostImg, orangeGhost.getX(), orangeGhost.getY(), this);
        g.drawImage(cocoImg, coco.getX(), coco.getY(), this);
    }

}
