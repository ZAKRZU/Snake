package dev.zakrzu.snake.entity;

import dev.zakrzu.snake.input.InputHandler;

public class Camera extends Entity {

    private InputHandler input;
    private boolean freeze = false;
    private LivingEntity followEntity = null;

    public Camera(InputHandler input) {
        this.input = input;
    }

    public Camera(int x, int y, InputHandler input) {
        this.input = input;
        xp = x;
        yp = y;
    }

    public void move(int xa, int ya) {
        if (followEntity == null) return;
        // if (xa != 0 && ya != 0) {
        //     move(xa, 0);
        //     move(0, ya);
        // }

        xp += xa;
        yp += ya;
    }

    public void moveFreezed(int xa, int ya) {
        xp += xa;
        yp += ya;
    }

    public void setPosition(int xa, int ya) {
        xp = xa;
        yp = ya;
    }
    
    public void update() {
        if (followEntity != null) {
            xp = followEntity.getX() + 8;
            yp = followEntity.getY() + 8;
            return;
        }
        int xa = 0;
        int ya = 0;
        
        if (input.up) ya--;
        if (input.down) ya++;
        if (input.left) xa--;
        if (input.right) xa++;

        move(xa, ya);
    }

    public void follow(LivingEntity e) {
        followEntity = e;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public boolean isFreezed() {
        return freeze;
    }

}
