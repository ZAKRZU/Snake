package dev.zakrzu.snake.ui;

import dev.zakrzu.snake.Game;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.input.InputHandler;

public class UI {
    
    protected InputHandler m_input;
    protected Game m_game;
    
    public UI(InputHandler input) {
        m_input = input;
    }

    public UI(InputHandler input, Game game) {
        m_input = input;
        m_game = game;
    }

    public void update() {
    }

    public void render(Screen screen)  {
    }

}
