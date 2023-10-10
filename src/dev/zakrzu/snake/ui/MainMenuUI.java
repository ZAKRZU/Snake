package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;

public class MainMenuUI extends UI {

    private int timer = 18;
    private String[] options = { "> Play <", "High Scores", "Exit" };
    private int selected = 0;

    public MainMenuUI(InputHandler input, Game game) {
        super(input, game);
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

        if (selected < 0) selected = 0;
        if (selected > 2) selected = 2;

        if (selected == 0) {
            options[selected] = "> Play <";
            if (input.use && timer == 0) {
                game.changeUI(new LevelSelectorUI(input, game));
            }
        } else {
            options[0] = "Play";
        }

        if (selected == 1) {
            options[selected] = "> High Scores <";
            if (input.use && timer == 0) {
                game.changeUI(new HighScoresUI(input, game));
            }
        } else {
            options[1] = "High Scores";
        }

        if (selected == 2) {
            options[selected] = "> Exit <";
            if (input.use && timer == 0) {
                game.stop();
            }
        } else {
            options[2] = "Exit";
        }

    }

    public void render(Screen screen) {
        screen.renderText("Snake", 160 + 4, 30 + 4, 80, 1, 0);
        screen.renderText("Snake", 160, 30, 80, 1, 0xffffff);
        for (int i = 0; i < options.length; i++) {
            screen.renderText(options[i], 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
            screen.renderText(options[i], 180, 120 + i * 40, 30, 1, 0xffffff);
        }
    }
    
}
