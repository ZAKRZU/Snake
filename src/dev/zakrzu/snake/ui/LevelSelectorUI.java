package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;
import dev.zakrzu.snake.map.Map;

public class LevelSelectorUI extends UI {

    private int timer = 18;
    private String[] m_options = { "Back" };
    private int selected = 0;

    public LevelSelectorUI(InputHandler input, Game game) {
        super(input, game);
    }

    public void update() {
        if (timer > 0) timer--;
        if (m_input.down && timer == 0) {
            selected++;
            timer = 10;
        }
        if (m_input.up && timer == 0) {
            selected--;
            timer = 10;
        }

        if (m_input.back) {
            m_game.changeUI(new MainMenuUI(m_input, m_game));
        }

        if (selected < 0) selected = 0;
        if (selected > m_game.getMapList().size()) selected = m_game.getMapList().size();

        if (selected >= 0 && selected < m_game.getMapList().size()) {
            if (m_input.use && timer == 0) {
                m_game.changeMap(selected);
                m_game.startGame();
            }
        }

        if (selected == m_game.getMapList().size()) {
            if (m_input.use && timer == 0) {
                m_game.changeUI(new MainMenuUI(m_input, m_game));
            }
        }

    }

    public void render(Screen screen) {
        screen.renderText("Snake", 160 + 4, 30 + 4, 80, 1, 0);
        screen.renderText("Snake", 160, 30, 80, 1, 0xffffff);
        for (int i = 0; i <= m_game.getMapList().size(); i++) {
            if (i < m_game.getMapList().size()) {
                Map map = m_game.getMapList().get(i);
                if (selected == i) {
                    screen.renderText("> " + map.getMapName() + " <", 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText("> " + map.getMapName() + " <", 180, 120 + i * 40, 30, 1, 0xffffff);
                } else {
                    screen.renderText(map.getMapName(), 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText(map.getMapName(), 180, 120 + i * 40, 30, 1, 0xffffff);
                }
            } else {
                if (selected == i) {
                    screen.renderText("> " + m_options[0] + " <", 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText("> " + m_options[0] + " <", 180, 120 + i * 40, 30, 1, 0xffffff);
                } else {
                    screen.renderText(m_options[0], 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText(m_options[0], 180, 120 + i * 40, 30, 1, 0xffffff);
                }
            }
        }
    }
}
