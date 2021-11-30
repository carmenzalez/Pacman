package pacman;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;

public class Game extends JFrame { //Ficar implements ActionListener i deixar el codi bonic

    Board board;

    public Game() {
        setTitle("PacMan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,1024);
        add(new Board(), SwingConstants.CENTER);
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
