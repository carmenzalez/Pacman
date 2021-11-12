package pacman;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Game extends JFrame {

    public Game() {
        init();
    }

    public void init() {
        setSize(435, 635);
        add(new Board());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game ex = new Game();
                ex.setVisible(true);
            }

        });
    }
}
