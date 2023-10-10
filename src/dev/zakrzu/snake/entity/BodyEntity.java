package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class BodyEntity extends LivingEntity {

    private Sprite snakeSprite;
    private int px, py;
    private int dir;

    public BodyEntity(int x, int y, PlayerEntity player) {
        xp = x;
        yp = y;
        dir = player.getDirection();
    }

    public BodyEntity(int x, int y, BodyEntity body) {
        xp = x;
        yp = y;
        dir = body.getDirection();
    }

    public void setDirection(int dir) {
        this.dir = dir;
    }

    public int getDirection() {
        return dir;
    }

    public int getX() {
        return xp;
    }

    public int getY() {
        return yp;
    }

    public int getOldX() {
        return px;
    }

    public int getOldY() {
        return py;
    }

    public void render(Screen screen, boolean isLast) {
        if (dir == 0) {
            if (isLast)
                snakeSprite = Sprite.snakeTailUSprite;
            else
                snakeSprite = Sprite.snakeBodyUSprite;
        }

        if (dir == 1) {
            if (isLast)
                snakeSprite = Sprite.snakeTailRSprite;
            else
                snakeSprite = Sprite.snakeBodyRSprite;
        }
        
        if (dir == 2) {
            if (isLast)
                snakeSprite = Sprite.snakeTailDSprite;
            else
                snakeSprite = Sprite.snakeBodyDSprite;
        }
        
        if (dir == 3) {
            if (isLast)
                snakeSprite = Sprite.snakeTailLSprite;
            else
                snakeSprite = Sprite.snakeBodyLSprite;
        }
        screen.renderTile(xp, yp, snakeSprite);
    }

    public void move(int xa, int ya) {
        px = xp;
        py = yp;

        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;

        xp += xa;
        yp += ya;
    }

}
