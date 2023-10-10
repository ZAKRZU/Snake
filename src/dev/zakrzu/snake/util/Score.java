package dev.zakrzu.snake.util;

import java.io.Serializable;

public class Score implements Serializable {
    
    private static final long serialVersionUID = 2L;
    private String playerName;
    private int score;

    public Score(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getName() {
        return playerName;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addPoints(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

}
