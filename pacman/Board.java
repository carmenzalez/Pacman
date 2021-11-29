package pacman;

/**
 * Imports
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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

    // Imatges
    private Image cocoImg;
    private ImageIcon cocoP0;
    private ImageIcon cocoL;
    private ImageIcon cocoR;
    private ImageIcon cocoU;
    private ImageIcon cocoD;
    private ImageIcon cocoF;

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

    //private ImageIcon ghostB;
    private ImageIcon ghostWB;

    private Image dotImg;
    private ImageIcon dotV;

    private Image dotBigImg;
    private ImageIcon dotBigV;

    private Image messageImg;
    private ImageIcon ready, gameOver;

    // Coco
    Entity coco;

    // Fantasmes
    Entity redGhost;
    Entity pinkGhost;
    Entity blueGhost;
    Entity orangeGhost;

    // Punts
    Entity dot;
    Entity dotB1, dotB2, dotB3, dotB4;

    // Ready, GameOver
    Entity message;

    // Puntuació
    int score;

    // Timer per a moure el coco
    Timer timer = new Timer(20, this);

    // Timer fantasmes
    Timer timerF = new Timer(10000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    });

    // Tipus de lletra
    private final Font smallfont = new Font("Helvetica", Font.BOLD, 30);

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
        bConfig();
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
        score = 0;

        timerF.setRepeats(false);
    }

    public void bConfig() {
        coco = new Entity("coco");
        redGhost = new Entity("red");
        pinkGhost = new Entity("pink");
        blueGhost = new Entity("blue");
        orangeGhost = new Entity("orange");
        dotB1 = new Entity("dotBig", 1, 2);
        dotB2 = new Entity("dotBig", 17, 2);
        dotB3 = new Entity("dotBig", 1, 16);
        dotB4 = new Entity("dotBig", 17, 16);
        message = new Entity("message", 7, 12);

        loadImages();
        setImage("coco", cocoP0);
        setImage("redGhost", redGhostL);
        setImage("blueGhost", blueGhostU);
        setImage("pinkGhost", pinkGhostU);
        setImage("orangeGhost", orangeGhostU);
        setImage("dot", dotV);
        setImage("dotBig", dotBigV);
        setImage("message", ready);
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
        cocoF = new ImageIcon(getClass().getResource("../images/cocoF.gif"));

        // Fantasma Vermell
        redGhostL = new ImageIcon(getClass().getResource("../images/redGhostL.gif"));
        redGhostR = new ImageIcon(getClass().getResource("../images/redGhostR.gif"));
        redGhostU = new ImageIcon(getClass().getResource("../images/redGhostU.gif"));
        redGhostD = new ImageIcon(getClass().getResource("../images/redGhostD.gif"));

        // Fantasma Rosa
        pinkGhostL = new ImageIcon(getClass().getResource("../images/pinkGhostL.gif"));
        pinkGhostR = new ImageIcon(getClass().getResource("../images/pinkGhostR.gif"));
        pinkGhostU = new ImageIcon(getClass().getResource("../images/pinkGhostU.gif"));
        pinkGhostD = new ImageIcon(getClass().getResource("../images/pinkGhostD.gif"));

        // Fantasma Blau
        blueGhostL = new ImageIcon(getClass().getResource("../images/blueGhostL.gif"));
        blueGhostR = new ImageIcon(getClass().getResource("../images/blueGhostR.gif"));
        blueGhostU = new ImageIcon(getClass().getResource("../images/blueGhostU.gif"));
        blueGhostD = new ImageIcon(getClass().getResource("../images/blueGhostD.gif"));

        // Fantasma Taronja
        orangeGhostL = new ImageIcon(getClass().getResource("../images/orangeGhostL.gif"));
        orangeGhostR = new ImageIcon(getClass().getResource("../images/orangeGhostR.gif"));
        orangeGhostU = new ImageIcon(getClass().getResource("../images/orangeGhostU.gif"));
        orangeGhostD = new ImageIcon(getClass().getResource("../images/orangeGhostD.gif"));

        // Fantasma Dèbil
        //ghostB = new ImageIcon(getClass().getResource("../images/ghostB.gif"));
        ghostWB = new ImageIcon(getClass().getResource("../images/ghostWB.gif"));

        // Punts
        dotV = new ImageIcon(getClass().getResource("../images/dot.png"));
        dotBigV = new ImageIcon(getClass().getResource("../images/dotBig.png"));

        // Missatges
        ready = new ImageIcon(getClass().getResource("../images/ready.png"));
        gameOver = new ImageIcon(getClass().getResource("../images/gameover.png"));
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
            
            case "dotBig":
                dotBigImg = img.getImage();
                break;

            case "message":
                messageImg = img.getImage();
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
                    dot = new Entity("dot",i,j);
                    g.drawImage(dotImg, dot.getX(), dot.getY(), this);
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
        int x = coco.getX();
        int y = coco.getY();
        switch (dir) {
            case 1:
                if (((board[(y + 20)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 0)
                || (board[(y + 20)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 8) {
                    coco.setX(x - 3);
                    if(board[y/cell][(x - 3)/cell] == 0 && (x - 3)%cell <= 16) {
                        board[y/cell][x/cell] = 8;
                    }
                }
                break;
            case 2:
                if (((board[(y + 20)/cell][(x + 29)/cell] | board[y/cell][(x + 29)/cell]) == 0)
                || (board[(y + 20)/cell][(x + 29)/cell] | board[y/cell][(x + 29)/cell]) == 8) {
                    coco.setX(x + 3);
                    if(board[y/cell][(x + 3)/cell] == 0 && ((x + 3)%cell) <= 16) {
                        board[y/cell][x/cell] = 8;
                    }
                }
                break;
            case 3:
                if (((board[(y - 3)/cell][(x + 20)/cell] | board[(y - 3)/cell][x/cell]) == 0)
                || (board[(y - 3)/cell][(x + 20)/cell] | board[(y - 3)/cell][x/cell]) == 8) {
                    coco.setY(y - 3);
                    if(board[(y - 3)/cell][x/cell] == 0 && (y - 3)%cell <= 16) {
                        board[y/cell][x/cell] = 8;
                    }
                }
                break;
            case 4:
                if (((board[(y + 29)/cell][(x + 20)/cell] | board[(y + 29)/cell][x/cell]) == 0)
                || (board[(y + 29)/cell][(x + 20)/cell] | board[(y + 29)/cell][x/cell]) == 8) {
                    coco.setY(y + 3);
                    if(board[(y + 3)/cell][x/cell] == 0 && (y + 3)%cell <= 16) {
                        board[y/cell][x/cell] = 8;
                    }
                }
                break;
        }
    }

    public void moveRed() {
        int x = redGhost.getX();
        int y = redGhost.getY();
        int calX = coco.getX() - redGhost.getX();
        int calY = coco.getY() - redGhost.getY();
        if (Math.abs(calX) <= Math.abs(calY)) {
            if (calY <= 0) {
                if (((board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 0)
                || (board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 8) {
                    redGhost.setY(y - 3);
                    setImage("redGhost", redGhostU);
                } else if (calX <= 0) {
                    if (((board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 0)
                    || (board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 8) {
                        redGhost.setX(x - 3);
                        setImage("redGhost", redGhostL);
                    }
                } else if (calX > 0) {
                    if (((board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 0)
                    || (board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 8) {
                        redGhost.setX(x + 3);
                        setImage("redGhost", redGhostR);
                    }
                }
            } else {
                if (((board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 0)
                || (board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 8) {
                    redGhost.setY(y + 3);
                    setImage("redGhost", redGhostD);
                } else if (calX <= 0) {
                    if (((board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 0)
                    || (board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 8) {
                        redGhost.setX(x - 3);
                        setImage("redGhost", redGhostL);
                    }
                } else if (calX > 0) {
                    if (((board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 0)
                    || (board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 8) {
                        redGhost.setX(x + 3);
                        setImage("redGhost", redGhostR);
                    }
                }
            }
        } else {
            if (calX <= 0) {
                if (((board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 0)
                || (board[(y + 21)/cell][(x - 3)/cell] | board[y/cell][(x - 3)/cell]) == 8) {
                    redGhost.setX(x - 3);
                    setImage("redGhost", redGhostL);
                } else if (calY <= 0) {
                    if (((board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 0)
                    ||(board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 8) {
                        redGhost.setY(y - 3);
                        setImage("redGhost", redGhostU);
                    }
                } else if (calY > 0) {
                    if (((board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 0)
                    || (board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 8) {
                        redGhost.setY(y + 3);
                        setImage("redGhost", redGhostD);
                    }
                }
            } else {
                if (((board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 0)
                || (board[(y + 21)/cell][(x + 31)/cell] | board[y/cell][(x + 31)/cell]) == 8) {
                    redGhost.setX(x + 3);
                    setImage("redGhost", redGhostR);
                } else if (calY <= 0) {
                    if (((board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 0)
                    || (board[(y - 3)/cell][(x + 21)/cell] | board[(y - 3)/cell][x/cell]) == 8) {
                        redGhost.setY(y - 3);
                        setImage("redGhost", redGhostU);
                    }
                } else if (calY > 0) {
                    if (((board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 0)
                    || (board[(y + 31)/cell][(x + 21)/cell] | board[(y + 31)/cell][x/cell]) == 8) {
                        redGhost.setY(y + 3);
                        setImage("redGhost", redGhostD);
                    }
                }
            }
        }
        if (timerF.isRunning()) {
            setImage("redGhost", ghostWB);
        }
    }

    /**
     * Llegeix quan es prem una tecla
     */
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

    /**
     * Configuració quan el coco es mor
     */
    /*public void reset() {
        coco.setX(9*32+3);
        coco.setY(16*32+3);
        setImage("coco", cocoP0);
    }*/

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

        // Fantasma Vermell t'atrapa
        if ((redGhost.getY()/cell == coco.getY()/cell) && (redGhost.getX()/cell == coco.getX()/cell)) {
            if (timerF.isRunning()) {
                redGhost.setX(9*32+3);
                redGhost.setY(8*32+3);
                timerF.stop();
            } else {
                setImage("coco", cocoF);
                timer.stop();
                bConfig();
            }
        }

        if ((dotB1.getY()/cell == coco.getY()/cell) && (dotB1.getX()/cell == coco.getX()/cell)) {
            timerF.start();
        } else if ((dotB2.getY()/cell == coco.getY()/cell) && (dotB2.getX()/cell == coco.getX()/cell)) {
            timerF.start();
        } else if ((dotB3.getY()/cell == coco.getY()/cell) && (dotB3.getX()/cell == coco.getX()/cell)) {
            timerF.start();
        } else if ((dotB4.getY()/cell == coco.getY()/cell) && (dotB4.getX()/cell == coco.getX()/cell)) {
            timerF.start();
        }
    }

    /**
     * Calcular puntuació
     */
    private int getScore(){
        int sc = this.score - 26*10;
            for (int j = 0; j < 22; j++) {
                for (int i = 0; i < 19; i++) {
                    if(board[j][i] == 8)
                        sc = sc + 10;
                }
            }
        return sc;
    }
    
    /**
     * Dibuixar puntuació
     */
    private void drawScore(Graphics g) {
        String s;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(smallfont);
        g2d.setColor(Color.WHITE);
        s = "Score: " + getScore();
        g2d.drawString(s, 1 * 32, 23 * 32 + 3);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawMaze(g);
        drawScore(g);
        if (!timer.isRunning()) {
            g.drawImage(messageImg, message.getX(), message.getY(), this);
        }
        g.drawImage(dotBigImg, dotB1.getX(), dotB2.getY(), this);
        g.drawImage(dotBigImg, dotB2.getX(), dotB2.getY(), this);
        g.drawImage(dotBigImg, dotB3.getX(), dotB3.getY(), this);
        g.drawImage(dotBigImg, dotB4.getX(), dotB4.getY(), this);
        g.drawImage(redGhostImg, redGhost.getX(), redGhost.getY(), this);
        g.drawImage(pinkGhostImg, pinkGhost.getX(), pinkGhost.getY(), this);
        g.drawImage(blueGhostImg, blueGhost.getX(), blueGhost.getY(), this);
        g.drawImage(orangeGhostImg, orangeGhost.getX(), orangeGhost.getY(), this);
        g.drawImage(cocoImg, coco.getX(), coco.getY(), this);
    }

}
