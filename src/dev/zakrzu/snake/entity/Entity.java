package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.map.Map;

public class Entity {
    
    protected int m_x = 0, m_y = 0;
    // private int m_health = 100;
    protected Map map;
    // private boolean m_hurt = false;

    public void update() {
    }

    public void render(Screen screen) {
    }

    public void remove() {
        map.remove(this);
    }

    public final void init(Map map) {
        this.map = map;
    }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }
}
