package dev.zakrzu.snake.entity;

public class LivingEntity extends Entity {

    public void move(int xa, int ya) {
        // if (xa != 0 && ya != 0) {
        //     move(xa, 0);
        //     move(0, ya);
        // }

        m_x += xa;
        m_y += ya;
    }

    public boolean collsision(int xa, int ya) {
        boolean solid = false;
        for (int i = 0; i < 4; i++) {
            int xt = ((m_x + xa) + (i % 2 * 2) * 5) >> 4;
            int yt = ((m_y + ya) + (i / 2 * 2 - 4) * 2) >> 4;
            if (map.getTile(xt, yt + 1).solid()) solid = true;
        }
        return solid;
    }

    public boolean collisionWithEntity(int xa, int ya, Entity entity) {
        boolean collided = false;
        int x = m_x + xa;
        int y = m_y + ya;
        int xb = x + 15;
        int yb = y + 15;
        if ((x >= entity.getX() && x <= entity.getX() + 15 ||
        xb >= entity.getX() && xb <= entity.getX() + 15) &&
        (y >= entity.getY() && y <= entity.getY() + 15 ||
        yb >= entity.getY() && y <= entity.getY() + 15))
            collided = true;
        return collided;
    }

}
