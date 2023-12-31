package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.map.Map;

public class Entity {
    
    protected int xp = 0, yp = 0;
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

    public void onWorldAdd() {
    }

    public void onWorldRemove() {
    }

    public int getX() {
        return xp;
    }

    public int getY() {
        return yp;
    }
}
