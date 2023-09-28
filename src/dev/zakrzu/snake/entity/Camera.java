package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.input.InputHandler;

public class Camera extends Entity {

    private InputHandler m_input;
    private boolean m_freeze = false;
    private LivingEntity m_followEntity = null;

    public Camera(InputHandler input) {
        m_input = input;
    }

    public Camera(int x, int y, InputHandler input) {
        m_input = input;
        m_x = x;
        m_y = y;
    }

    public void move(int xa, int ya) {
        if (m_followEntity == null) return;
        // if (xa != 0 && ya != 0) {
        //     move(xa, 0);
        //     move(0, ya);
        // }

        m_x += xa;
        m_y += ya;
    }

    public void moveFreezed(int xa, int ya) {
        m_x += xa;
        m_y += ya;
    }

    public void setPosition(int xa, int ya) {
        m_x = xa;
        m_y = ya;
    }
    
    public void update() {
        if (m_followEntity != null) {
            m_x = m_followEntity.getX() + 8;
            m_y = m_followEntity.getY() + 8;
            return;
        }
        int xa = 0;
        int ya = 0;
        
        if (m_input.up) ya--;
        if (m_input.down) ya++;
        if (m_input.left) xa--;
        if (m_input.right) xa++;

        move(xa, ya);
    }

    public void follow(LivingEntity e) {
        m_followEntity = e;
    }

    public void setFreeze(boolean freeze) {
        m_freeze = freeze;
    }

    public boolean isFreezed() {
        return m_freeze;
    }

}
