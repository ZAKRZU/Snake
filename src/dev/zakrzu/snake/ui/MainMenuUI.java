package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;

public class MainMenuUI extends UI {

    private int timer = 18;
    private String[] m_options = { "> Play <", "High Scores", "Exit" };
    private int selected = 0;

    public MainMenuUI(InputHandler input, Game game) {
        super(input, game);
    }

    public void update() {
        if (timer > 0) timer--;
        if (m_input.down && selected < m_options.length && timer == 0) {
            selected++;
            timer = 10;
        }
        if (m_input.up && selected > 0 && timer == 0) {
            selected--;
            timer = 10;
        }

        if (selected < 0) selected = 0;
        if (selected > 2) selected = 2;

        if (selected == 0) {
            m_options[selected] = "> Play <";
            if (m_input.use && timer == 0) {
                m_game.changeUI(new LevelSelectorUI(m_input, m_game));
            }
        } else {
            m_options[0] = "Play";
        }

        if (selected == 1) {
            m_options[selected] = "> High Scores <";
            if (m_input.use && timer == 0) {
                m_game.changeUI(new HighScoresUI(m_input, m_game));
            }
        } else {
            m_options[1] = "High Scores";
        }

        if (selected == 2) {
            m_options[selected] = "> Exit <";
            if (m_input.use && timer == 0) {
                m_game.stop();
            }
        } else {
            m_options[2] = "Exit";
        }

    }

    public void render(Screen screen) {
        screen.renderText("Snake", 160 + 4, 30 + 4, 80, 1, 0);
        screen.renderText("Snake", 160, 30, 80, 1, 0xffffff);
        for (int i = 0; i < m_options.length; i++) {
            screen.renderText(m_options[i], 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
            screen.renderText(m_options[i], 180, 120 + i * 40, 30, 1, 0xffffff);
        }
    }
    
}
