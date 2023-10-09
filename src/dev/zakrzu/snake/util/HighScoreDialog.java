package dev.zakrzu.snake.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.zakrzu.snake.Game;

public class HighScoreDialog extends JDialog {
    
    public HighScoreDialog(Game game, Score score) {
        setTitle("New score");
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField(3);

        JLabel scoreLabel = new JLabel("Score " + score.getScore());

        JButton addScoreButton = new JButton("Add score");
        addScoreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                score.setName(nameField.getText());
                game.getHighScore().addNewScore(score);
                game.saveObject();
                dispose();
            }
            
        });
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(scoreLabel);
        panel.add(addScoreButton);

        add(panel);
        pack();
        setVisible(true);
    }

}
