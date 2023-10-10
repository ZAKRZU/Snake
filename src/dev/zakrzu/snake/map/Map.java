package dev.zakrzu.snake.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import dev.zakrzu.snake.entity.AppleEntity;
import dev.zakrzu.snake.entity.BodyEntity;
import dev.zakrzu.snake.entity.Entity;
import dev.zakrzu.snake.entity.PlayerEntity;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.map.tile.GrassTile;
import dev.zakrzu.snake.map.tile.Tile;

public class Map {
    
    public int width, height;
    public int[] tiles;
    public List<Entity> entities = new ArrayList<Entity>();;

    private String mapName;
    private int respawnX, respawnY;
    private int cameraX, cameraY;
    private boolean cameraFollowsPlayer = true;
    
    private Random random = new Random();

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width * height];
        generateLevel();
    }

    public Map(String path, String mapName) {
        this.mapName = mapName;
        loadMapFromFile(path);
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            Entity ent = entities.get(i);
            ent.update();
        }
    }

    public void setRespawn(int x, int y) {
        if (x > 0)
            respawnX = x;
        if (y > 0)
            respawnY = y;
    }

    public int getRespawnX() {
        return respawnX;
    }

    public int getRespawnY() {
        return respawnY;
    }

    public void setCamera(int x, int y) {
        if (x > 0)
            cameraX = x;
        if (y > 0)
            cameraY = y;
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraFollowsPlayer(Boolean b) {
        cameraFollowsPlayer = b;
    }

    public boolean getCameraFollowsPlayer() {
        return cameraFollowsPlayer;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

    private void loadMapFromFile(String path) {
        try {
            BufferedImage image = ImageIO.read(Map.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0xff000000) {
                if (random.nextInt(10) < 5) {
                    tiles[i] = 0xfe000000;
                }
            }
        }
    }

    public void generateApples() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        boolean notAllowed = true;
        PlayerEntity player = null;
        AppleEntity apple = null;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof PlayerEntity) player = (PlayerEntity) entities.get(i);
        }

        while(notAllowed) {
            boolean collision = false;
            x = random.nextInt(width);
            y = random.nextInt(height);
            apple = new AppleEntity(x << 4, y << 4);
            if (!(getTile(x, y) instanceof GrassTile))
                continue;
            if (player != null) {
                if (player.collisionWithEntity(0, 0, apple))
                    continue;
                for (int i = 0; i < entities.size(); i++) {
                    Entity e = entities.get(i);
                    if (e instanceof BodyEntity) {
                        if (((BodyEntity)e).collisionWithEntity(0, 0, apple)) {
                            collision = true;
                            continue;
                        }
                    }
                }
                if (collision)
                    continue;
            }
            notAllowed = false;
        }

        add(apple);
    }

    public void regenerateApple() {
        for (int i = 0; i < entities.size(); i++) {
            Entity ent = entities.get(i);
            if (ent instanceof AppleEntity) {
                remove(ent);
            }
        }
        generateApples();
    }

    public void generateLevel() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = random.nextInt(16777215);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.getWidth() + 15) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.getHeight() + 15) >> 4;
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                Tile tile = getTile(x, y);
                tile.render(x, y, screen);
  
                //screen.renderTile(x << 4, y << 4, getTileColor(x, y));
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }
    }

    public void add(Entity e) {
        entities.add(e);
        e.init(this);
        e.onWorldAdd();
    }

    public void remove(Entity e) {
        e.onWorldRemove();
        entities.remove(e);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.voidTile;
        int position = x + y * width;
        if (tiles[position] == 0xff081700) return Tile.fakeGrassTile;
        if (tiles[position] == 0xff000000) return Tile.grassTile;
        if (tiles[position] == 0xfe000000) return Tile.grass2Tile;
        if (tiles[position] == 0xfff80000) return Tile.cbbleTile;
        if (tiles[position] == 0xff170000) return Tile.rockTile;
        return Tile.voidTile;
    }

    public int getTileColor(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return 0;
        return tiles[x + y * width];
    }

}
