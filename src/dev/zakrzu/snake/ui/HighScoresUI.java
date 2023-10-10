package dev.zakrzu.snake.ui;

import java.util.List;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;
import dev.zakrzu.snake.util.HighScore;
import dev.zakrzu.snake.util.Score;

public class HighScoresUI extends UI {

    private int timer = 18;
    private String[] options = { "Back" };
    private HighScore highScore;
    private int selected = 0;

    public HighScoresUI(InputHandler input, Game game) {
        super(input, game);
        highScore = game.getHighScore();
    }
    
    public void update() {
        if (timer > 0) timer--;
        if (input.down && selected < options.length && timer == 0) {
            selected++;
            timer = 10;
        }
        if (input.up && selected > 0 && timer == 0) {
            selected--;
            timer = 10;
        }
        
        if (input.back) {
            game.changeUI(new MainMenuUI(input, game));
        }

        if (selected < 0) selected = 0;
        if (selected > 0) selected = 0;

        if (selected == 0) {
            options[selected] = "> Back <";
            if (input.use && timer == 0) {
                game.changeUI(new MainMenuUI(input, game));
            }
        } else {
            options[0] = "Back";
        }

        if (selected == 0 && input.use && timer == 0) {
            
        }
    }

    public void render(Screen screen) {
        screen.renderText("Snake", 160 + 4, 30 + 4, 80, 1, 0);
        screen.renderText("Snake", 160, 30, 80, 1, 0xffffff);
        List<Score> scoreList = highScore.getTopScores();
        for (int i = 0; i < scoreList.size(); i++) {
            screen.renderText(Integer.toString(scoreList.get(i).getScore()), 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
            screen.renderText(Integer.toString(scoreList.get(i).getScore()), 180, 120 + i * 40, 30, 1, 0xffffff);

            screen.renderText(scoreList.get(i).getName(), 250 + 3, 120 + i * 40 + 3, 30, 1, 0);
            screen.renderText(scoreList.get(i).getName(), 250, 120 + i * 40, 30, 1, 0xffffff);
        }

        screen.renderText("> Back <", 180 + 3, 120 + 5 * 40 + 3, 30, 1, 0);
        screen.renderText("> Back <", 180, 120 + 5 * 40, 30, 1, 0xffffff);
    }

}
