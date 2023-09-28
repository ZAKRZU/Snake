package dev.zakrzu.snake.map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import dev.zakrzu.snake.entity.AppleEntity;
import dev.zakrzu.snake.entity.Entity;
import dev.zakrzu.snake.entity.PlayerEntity;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.map.tile.GrassTile;
import dev.zakrzu.snake.map.tile.Tile;

public class Map {
    
    public int m_width, m_height;
    public int[] m_tiles;
    public List<Entity> m_entities = new ArrayList<Entity>();;

    private String m_mapName;
    private int m_respawnX, m_respawnY;
    private int m_cameraX, m_cameraY;
    private boolean m_cameraFollowsPlayer = true;
    
    private Random random = new Random();

    private final Random m_random = new Random();

    public Map(int width, int height) {
        m_width = width;
        m_height = height;
        m_tiles = new int[width * height];
        generateLevel();
    }

    public Map(String path, String mapName) {
        m_mapName = mapName;
        loadMapFromFile(path);
    }

    public void update() {
        for (int i = 0; i < m_entities.size(); i++) {
            Entity ent = m_entities.get(i);
            ent.update();
        }
    }

    public void setRespawn(int x, int y) {
        if (x > 0)
            m_respawnX = x;
        if (y > 0)
            m_respawnY = y;
    }

    public int getRespawnX() {
        return m_respawnX;
    }

    public int getRespawnY() {
        return m_respawnY;
    }

    public void setCamera(int x, int y) {
        if (x > 0)
            m_cameraX = x;
        if (y > 0)
            m_cameraY = y;
    }

    public int getCameraX() {
        return m_cameraX;
    }

    public int getCameraY() {
        return m_cameraY;
    }

    public void setCameraFollowsPlayer(Boolean b) {
        m_cameraFollowsPlayer = b;
    }

    public boolean getCameraFollowsPlayer() {
        return m_cameraFollowsPlayer;
    }

    public void setMapName(String mapName) {
        m_mapName = mapName;
    }

    public String getMapName() {
        return m_mapName;
    }

    private void loadMapFromFile(String path) {
        try {
            BufferedImage image = ImageIO.read(Map.class.getResource(path));
            m_width = image.getWidth();
            m_height = image.getHeight();
            m_tiles = new int[m_width * m_height];
            image.getRGB(0, 0, m_width, m_height, m_tiles, 0, m_width);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < m_tiles.length; i++) {
            if (m_tiles[i] == 0xff000000) {
                if (random.nextInt(10) < 5) {
                    m_tiles[i] = 0xfe000000;
                }
            }
        }
    }

    public void generateApples() {
        int x = random.nextInt(m_width);
        int y = random.nextInt(m_height);
        boolean notAllowed = true;
        PlayerEntity player = null;

        for (int i = 0; i < m_entities.size(); i++) {
            if (m_entities.get(i) instanceof PlayerEntity) player = (PlayerEntity) m_entities.get(i);
        }

        while(notAllowed) {
            x = random.nextInt(m_width);
            y = random.nextInt(m_height);
            if (!(getTile(x, y) instanceof GrassTile))
                continue;
            if (player != null) {
                if (collisionEntity2(x << 4, y << 4, player))
                    continue;
                if (player.collisionBodyChecker(x << 4, y << 4))
                    continue;
            }
            notAllowed = false;
        }

        AppleEntity apple = new AppleEntity(x << 4, y << 4);
        add(apple);
    }

    public void regenerateApple() {
        for (int i = 0; i < m_entities.size(); i++) {
            Entity ent = m_entities.get(i);
            if (ent instanceof AppleEntity) {
                remove(ent);
            }
        }
        generateApples();
    }

    public void generateLevel() {
        for (int i = 0; i < m_tiles.length; i++) {
            m_tiles[i] = m_random.nextInt(16777215);
        }
    }

    public List<Entity> getEntities() {
        return m_entities;
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

        for (int i = 0; i < m_entities.size(); i++) {
            m_entities.get(i).render(screen);
        }
    }

    public void add(Entity e) {
        m_entities.add(e);
        e.init(this);
    }

    public void remove(Entity e) {
        m_entities.remove(e);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= m_width || y < 0 || y >= m_height)
            return Tile.voidTile;
        int position = x + y * m_width;
        if (m_tiles[position] == 0xff081700) return Tile.fakeGrassTile;
        if (m_tiles[position] == 0xff000000) return Tile.grassTile;
        if (m_tiles[position] == 0xfe000000) return Tile.grass2Tile;
        if (m_tiles[position] == 0xfff80000) return Tile.cbbleTile;
        if (m_tiles[position] == 0xff170000) return Tile.rockTile;
        return Tile.voidTile;
    }

    public int getTileColor(int x, int y) {
        if (x < 0 || x >= m_width || y < 0 || y >= m_height)
            return 0;
        return m_tiles[x + y * m_width];
    }

    public Entity collisionEntity(int xa, int ya, Entity entity) {
        Entity ent = null;
        for (int i = 0; i < m_entities.size(); i++) {
            Entity body = m_entities.get(i);
            for (int j = 0; j < 4; j++) {
                int xt = ((entity.getX() + xa) + (j % 2 * 2) * 5);
                int yt = ((entity.getY() + ya) + (j / 2 * 2) * 2);
                if (body.getX() == xt && body.getY() == yt) ent = body;
            }

        }
        return ent;
    }

    public boolean collisionEntity2(int xa, int ya, Entity entity) {
        for (int j = 0; j < 4; j++) {
            int xt = ((xa) + (j % 2 * 2) * 5);
            int yt = ((ya) + (j / 2 * 2) * 2);
            if (entity.getX() == xt && entity.getY() == yt) return true;
        }
        return false;
    }

}
