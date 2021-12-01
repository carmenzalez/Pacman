package pacman;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game extends JFrame { 
    
    public Game() {
        setTitle("PacMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625,800);
        add(new Board());
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
            }

        });
    }
}
