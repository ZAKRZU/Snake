package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;
import dev.zakrzu.snake.map.Map;

public class LevelSelectorUI extends UI {

    private int timer = 18;
    private String[] options = { "Back" };
    private int selected = 0;

    public LevelSelectorUI(InputHandler input, Game game) {
        super(input, game);
    }

    public void update() {
        if (timer > 0) timer--;
        if (input.down && timer == 0) {
            selected++;
            timer = 10;
        }
        if (input.up && timer == 0) {
            selected--;
            timer = 10;
        }

        if (input.back) {
            game.changeUI(new MainMenuUI(input, game));
        }

        if (selected < 0) selected = 0;
        if (selected > game.getMapList().size()) selected = game.getMapList().size();

        if (selected >= 0 && selected < game.getMapList().size()) {
            if (input.use && timer == 0) {
                game.changeMap(selected);
                game.startGame();
            }
        }

        if (selected == game.getMapList().size()) {
            if (input.use && timer == 0) {
                game.changeUI(new MainMenuUI(input, game));
            }
        }

    }

    public void render(Screen screen) {
        screen.renderText("Snake", 160 + 4, 30 + 4, 80, 1, 0);
        screen.renderText("Snake", 160, 30, 80, 1, 0xffffff);
        for (int i = 0; i <= game.getMapList().size(); i++) {
            if (i < game.getMapList().size()) {
                Map map = game.getMapList().get(i);
                if (selected == i) {
                    screen.renderText("> " + map.getMapName() + " <", 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText("> " + map.getMapName() + " <", 180, 120 + i * 40, 30, 1, 0xffffff);
                } else {
                    screen.renderText(map.getMapName(), 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText(map.getMapName(), 180, 120 + i * 40, 30, 1, 0xffffff);
                }
            } else {
                if (selected == i) {
                    screen.renderText("> " + options[0] + " <", 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText("> " + options[0] + " <", 180, 120 + i * 40, 30, 1, 0xffffff);
                } else {
                    screen.renderText(options[0], 180 + 3, 120 + i * 40 + 3, 30, 1, 0);
                    screen.renderText(options[0], 180, 120 + i * 40, 30, 1, 0xffffff);
                }
            }
        }
    }
}
