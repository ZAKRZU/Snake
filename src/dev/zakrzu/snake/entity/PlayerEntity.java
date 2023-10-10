package dev.zakrzu.snake.entity;

import java.util.ArrayList;
import java.util.List;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;
import dev.zakrzu.snake.input.InputHandler;
import dev.zakrzu.snake.util.Score;

public class PlayerEntity extends LivingEntity {
    
    private static final int MOVE_TICK = 21;
    private static final int RESPAWN_TICK = 30;

    private Sprite spriteHead = Sprite.snakeHeadRSprite;
    private List<BodyEntity> body = new ArrayList<BodyEntity>();
    private InputHandler input;
    private int px, py;
    private int dir = 1;
    private int lastDir = dir;
    private Score score = null;
    private int updates = MOVE_TICK;
    private int delay = 15;
    private int respawn = RESPAWN_TICK;
    private boolean extendBody = false;
    private boolean isDead = false;

    public PlayerEntity(InputHandler input) {
        this.input = input;
    }

    public PlayerEntity(int x, int y,InputHandler input) {
        this.input = input;
        xp = x << 4;
        yp = y << 4;
    }

    public void onWorldAdd() {
        score = new Score("Player", 0);
        BodyEntity bEnt1 = new BodyEntity(xp - (16), yp, this);
        BodyEntity bEnt2 = new BodyEntity(xp - (2 * 16), yp, this);
        body.add(bEnt1);
        body.add(bEnt2);
        map.add(bEnt1);
        map.add(bEnt2);
    }

    public void onWorldRemove() {
        for (int i = 0; i < body.size(); i++) {
            map.remove(body.get(i));
        }
    }

    public int getDirection() {
        return dir;
    }

    public Score getScore() {
        return score;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean canRespawn() {
        return respawn == 0;
    }

    public void update() {
        if (delay > 0) {delay--; return;}
        if (isDead) {
            if (respawn > 0) {
                respawn--;
            }
            return;
        }
        updates--;

        int xa = 0;
        int ya = 0;
        
        if (input.up && lastDir != 2) ya--;
        if (input.down && lastDir != 0) ya++;
        if (input.left && lastDir != 1) xa--;
        if (input.right && lastDir != 3) xa++;

        if ((xa != 0 || ya != 0)) {
            px = xa;
            py = ya;
        }
        if (xa > 0) dir = 1;
        if (xa < 0) dir = 3;
        if (ya > 0) dir = 2;
        if (ya < 0) dir = 0;

        if (updates == 0) {
            updates = MOVE_TICK;
            lastDir = dir;
            if (collsision((px * 16), (py * 16))) {isDead = true; delay = 15; return;}
            if (collisionWithBody((px * 16), (py * 16))) {isDead = true; delay = 15; return;}
            if (extendBody) {
                BodyEntity l = body.get(body.size() - 1);
                BodyEntity n = new BodyEntity(l.getX(), l.getY(), l);
                extendBody = false;
                body.add(n);
                map.add(n);
            }

            for (int i = 0; i < body.size(); i++) {
                BodyEntity bodyTmp = body.get(i);
                int bx = 0;
                int by = 0;
                if (i == 0) {
                    bx = xp - bodyTmp.getX();
                    by = yp - bodyTmp.getY();
                } else {
                    BodyEntity body2 = body.get(i - 1);
                    bx = body2.getOldX() - bodyTmp.getX();
                    by = body2.getOldY() - bodyTmp.getY();
                }
                if ((bodyTmp.getX() + bx == xp || bodyTmp.getY() + by == yp) && (px == 0 && py == 0)) break;

                bodyTmp.move(bx, by);
            }
            move((px * 16), (py * 16));

            for (int i = 0; i < map.getEntities().size(); i++) {
                Entity e = map.getEntities().get(i);
                if (e instanceof AppleEntity) {
                    if (this.collisionWithEntity(0, 0, e)) {
                        ((AppleEntity) e).remove();
                        map.generateApples();
                        extendBody = true;
                        score.addPoints(1);
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
        if (dir == 0) {
            spriteHead = Sprite.snakeHeadUSprite;
        }

        if (dir == 1) {
            spriteHead = Sprite.snakeHeadRSprite;
        }
        
        if (dir == 2) {
            spriteHead = Sprite.snakeHeadDSprite;
        }
        
        if (dir == 3) {
            spriteHead = Sprite.snakeHeadLSprite;
        }

        screen.renderTile(xp, yp, spriteHead);
        for (int i = 0; i < body.size(); i++) {
            if (i == body.size() - 1)
            body.get(i).render(screen, true);
            else
            body.get(i).render(screen, false);
        }
    }

}
