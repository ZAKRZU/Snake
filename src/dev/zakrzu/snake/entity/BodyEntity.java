package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class BodyEntity extends LivingEntity {

    private Sprite snakeSprite;
    private int px, py;
    private int m_dir;

    public BodyEntity(int x, int y, PlayerEntity player) {
        m_x = x;
        m_y = y;
        m_dir = player.getDirection();
    }

    public BodyEntity(int x, int y, BodyEntity body) {
        m_x = x;
        m_y = y;
        m_dir = body.getDirection();
    }

    public void setDirection(int dir) {
        m_dir = dir;
    }

    public int getDirection() {
        return m_dir;
    }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }

    public int getOldX() {
        return px;
    }

    public int getOldY() {
        return py;
    }

    public void render(Screen screen, boolean isLast) {
        if (m_dir == 0) {
            if (isLast)
            snakeSprite = Sprite.snakeTailUSprite;
            else
            snakeSprite = Sprite.snakeBodyUSprite;
        }

        if (m_dir == 1) {
            if (isLast)
            snakeSprite = Sprite.snakeTailRSprite;
            else
            snakeSprite = Sprite.snakeBodyRSprite;
        }
        
        if (m_dir == 2) {
            if (isLast)
            snakeSprite = Sprite.snakeTailDSprite;
            else
            snakeSprite = Sprite.snakeBodyDSprite;
        }
        
        if (m_dir == 3) {
            if (isLast)
            snakeSprite = Sprite.snakeTailLSprite;
            else
            snakeSprite = Sprite.snakeBodyLSprite;
        }
        screen.renderTile(m_x, m_y, snakeSprite);
    }

    public void move(int xa, int ya) {
        px = m_x;
        py = m_y;

        if (xa > 0) m_dir = 1;
        if (xa < 0) m_dir = 3;
        if (ya > 0) m_dir = 2;
        if (ya < 0) m_dir = 0;

        m_x += xa;
        m_y += ya;
    }

}
