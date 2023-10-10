package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class AppleEntity extends Entity {
    
    private Sprite sprite = Sprite.appleSprite;

    public AppleEntity() {
        System.out.println("WARNING: Created apple at X:0 Y:0 (can be off the map!)");
    }

    public AppleEntity(int x, int y) {
        xp = x;
        yp = y;
    }

    public void render(Screen screen) {
        screen.renderTile(xp, yp, sprite);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
