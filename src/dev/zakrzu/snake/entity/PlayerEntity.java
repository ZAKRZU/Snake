package dev.zakrzu.snake.entity;

import java.util.ArrayList;
import java.util.List;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;
import dev.zakrzu.snake.input.InputHandler;

public class PlayerEntity extends LivingEntity {
    
    private static final int MOVE_TICK = 21;

    private Sprite m_spriteHead = Sprite.snakeHeadRSprite;
    private List<BodyEntity> m_body = new ArrayList<BodyEntity>();
    private InputHandler m_input;
    private int px, py;
    private int m_dir = 1;
    private int m_lastDir = m_dir;
    private int m_score = 0;
    private int m_updates = MOVE_TICK;
    private int m_delay = 15;
    private boolean m_extend = false;
    private boolean m_isDead = false;

    public PlayerEntity(InputHandler input) {
        m_input = input;
    }

    public PlayerEntity(int x, int y,InputHandler input) {
        m_input = input;
        m_x = x << 4;
        m_y = y << 4;
    }

    public void onWorldAdd() {
        BodyEntity bEnt1 = new BodyEntity(m_x - (16), m_y, this);
        BodyEntity bEnt2 = new BodyEntity(m_x - (2 * 16), m_y, this);
        m_body.add(bEnt1);
        m_body.add(bEnt2);
        map.add(bEnt1);
        map.add(bEnt2);
    }

    public int getDirection() {
        return m_dir;
    }

    public int getScore() {
        return m_score;
    }

    public boolean isDead() {
        return m_isDead;
    }

    public void update() {
        if (m_delay > 0) {m_delay--; return;}
        if (m_isDead) return;
        m_updates--;

        int xa = 0;
        int ya = 0;
        
        if (m_input.up && m_lastDir != 2) ya--;
        if (m_input.down && m_lastDir != 0) ya++;
        if (m_input.left && m_lastDir != 1) xa--;
        if (m_input.right && m_lastDir != 3) xa++;

        if ((xa != 0 || ya != 0)) {
            px = xa;
            py = ya;
        }
        if (xa > 0) m_dir = 1;
        if (xa < 0) m_dir = 3;
        if (ya > 0) m_dir = 2;
        if (ya < 0) m_dir = 0;

        if (m_updates == 0) {
            m_updates = MOVE_TICK;
            m_lastDir = m_dir;
            if (collsision((px * 16), (py * 16))) {m_isDead = true; m_delay = 15; return;}
            if (collisionWithBody((px * 16), (py * 16))) {m_isDead = true; m_delay = 15; return;}
            if (m_extend) {
                BodyEntity l = m_body.get(m_body.size() - 1);
                BodyEntity n = new BodyEntity(l.getX(), l.getY(), l);
                m_extend = false;
                m_body.add(n);
                map.add(n);
            }

            for (int i = 0; i < m_body.size(); i++) {
                BodyEntity body = m_body.get(i);
                int bx = 0;
                int by = 0;
                if (i == 0) {
                    bx = m_x - body.getX();
                    by = m_y - body.getY();
                } else {
                    BodyEntity body2 = m_body.get(i - 1);
                    bx = body2.getOldX() - body.getX();
                    by = body2.getOldY() - body.getY();
                }
                if ((body.getX() + bx == m_x || body.getY() + by == m_y) && (px == 0 && py == 0)) break;

                body.move(bx, by);
            }
            move((px * 16), (py * 16));

            for (int i = 0; i < map.getEntities().size(); i++) {
                Entity e = map.getEntities().get(i);
                if (e instanceof AppleEntity) {
                    if (this.collisionWithEntity(0, 0, e)) {
                        ((AppleEntity) e).remove();
                        map.generateApples();
                        m_extend = true;
                        m_score++;
                    }
                }
            }
        }
    }

    public boolean collisionWithBody(int xa, int ya) {
        boolean solid = false;
        for (int i = 0; i < map.getEntities().size(); i++) {
            Entity ent = map.getEntities().get(i);
            if (ent instanceof BodyEntity) {
                if (collisionWithEntity(xa, ya, ent))
                    solid = true;
            }
        }
        return solid;
    }

    public void render(Screen screen) {
        if (m_dir == 0) {
            m_spriteHead = Sprite.snakeHeadUSprite;
        }

        if (m_dir == 1) {
            m_spriteHead = Sprite.snakeHeadRSprite;
        }
        
        if (m_dir == 2) {
            m_spriteHead = Sprite.snakeHeadDSprite;
        }
        
        if (m_dir == 3) {
            m_spriteHead = Sprite.snakeHeadLSprite;
        }

        screen.renderTile(m_x, m_y, m_spriteHead);
        for (int i = 0; i < m_body.size(); i++) {
            if (i == m_body.size() - 1)
            m_body.get(i).render(screen, true);
            else
            m_body.get(i).render(screen, false);
        }
    }

}
