package dev.zakrzu.snake.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HighScoreDialog extends JDialog {
    
    private Score m_score;

    public HighScoreDialog(Score score) {
        m_score = score;
        setTitle("New score");
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField(3);

        JLabel scoreLabel = new JLabel("Score " + m_score.getScore());

        JButton addScoreButton = new JButton("Add score");
        addScoreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                m_score.setName(nameField.getText());
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

    public Score getScore() {
        return m_score;
    }

}
