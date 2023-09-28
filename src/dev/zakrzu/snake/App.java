package dev.zakrzu.snake;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle(Game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }
}
