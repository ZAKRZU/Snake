package dev.zakrzu.snake.util;

import java.io.Serializable;

public class Score implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String m_playerName;
    private int m_score;

    public Score(String playerName, int score) {
        m_playerName = playerName;
        m_score = score;
    }

    public String getName() {
        return m_playerName;
    }

    public void setName(String playerName) {
        m_playerName = playerName;
    }

    public int getScore() {
        return m_score;
    }

}
