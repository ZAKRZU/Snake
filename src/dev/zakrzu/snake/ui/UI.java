package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;

public class UI {
    
    protected InputHandler input;
    protected Game game;
    
    public UI(InputHandler input) {
        this.input = input;
    }

    public UI(InputHandler input, Game game) {
        this.input = input;
        this.game = game;
    }

    public void update() {
    }

    public void render(Screen screen)  {
    }

}
