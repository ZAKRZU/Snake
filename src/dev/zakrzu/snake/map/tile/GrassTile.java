package dev.zakrzu.snake.map.tile;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class GrassTile extends Tile {

    public GrassTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int xt, int yt, Screen screen) {
        screen.renderTile(xt << 4, yt << 4, this);
    }
    
}
