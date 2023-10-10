package dev.zakrzu.snake.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighScore implements Serializable {
    
    private static final long serialVersionUID = 2L;
    private static final int TOP = 5;

    private List<Score> scoreList = new ArrayList<Score>();

    public HighScore() {
    }

    public void addNewScore(Score score) {
        scoreList.add(score);
        sortScores();
        cleanOldScores();
    }

    public boolean canAddNewScore(Score score) {
        if (score.getScore() <= 0)
            return false;
        if (scoreList.size() > 0) {
            if (score.getScore() < scoreList.get(0).getScore() && scoreList.size() >= TOP) return false;
        }
        return true;
    }

    public void cleanOldScores() {
        if (scoreList.size() <= TOP) return;
        List<Score> topScores = getTopScores();
        scoreList = topScores;
        sortScores();
    }

    public List<Score> getAllScores() {
        return scoreList;
    }

    public List<Score> getTopScores() {
        List<Score> scores = new ArrayList<Score>();

        int startPoint = scoreList.size() - 1;
        int stopPoint = scoreList.size() - TOP;
        if (stopPoint < 1) {
            stopPoint = 0;
        }

        for (int i = startPoint; i >= stopPoint; i--) {
            scores.add(scoreList.get(i));
        }

        return scores;
    }

    private void swap(Score[] arr, int i, int j) {
        Score temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int partition(Score[] arr, int low, int high) {
        int pivot = arr[high].getScore();

        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {
            if (arr[j].getScore() < pivot) {
                i++;
                
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    private void quickSort(Score[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }

    }

    public void sortScores() {
        Score arr2[] = scoreList.toArray(new Score[0]);
        quickSort(arr2, 0, arr2.length - 1);
        scoreList.clear();
        for (int i = 0; i < arr2.length; i++) {
            scoreList.add(arr2[i]);
        }
    }

}
