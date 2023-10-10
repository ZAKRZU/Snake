package dev.zakrzu.snake.map.tile;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class CbbleTile extends Tile{

    public CbbleTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int xt, int yt, Screen screen) {
        screen.renderTile(xt << 4, yt << 4, this);
    }

    public boolean solid() {
        return true;
    }
    
}
