import javax.swing.*;

public class SpaceInvaders extends JFrame {
    SpaceInvadersPanel game = new SpaceInvadersPanel();

    public SpaceInvaders() {
        super("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SpaceInvaders frame = new SpaceInvaders();
    }
}