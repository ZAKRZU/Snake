package dev.zakrzu.snake;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dev.zakrzu.snake.entity.Camera;
import dev.zakrzu.snake.entity.PlayerEntity;
import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.SpriteSheet;
import dev.zakrzu.snake.input.InputHandler;
import dev.zakrzu.snake.map.Map;
import dev.zakrzu.snake.ui.MainMenuUI;
import dev.zakrzu.snake.ui.UI;
import dev.zakrzu.snake.util.HighScore;
import dev.zakrzu.snake.util.HighScoreDialog;
import dev.zakrzu.snake.util.Score;

public class Game extends Canvas implements Runnable {
    
    public final static String TITLE = "Snake";
    public final static int WIDTH = 300;
    public final static int HEIGHT = WIDTH / 16 * 10;
    public final static int SCALE = 2;

    private final static int RESPAWN_TIME = 30;

    public boolean m_paused = false;

    private Thread m_thread;
    private boolean m_running = false;
    private int m_canRespawn = RESPAWN_TIME;
    private BufferedImage m_image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] m_pixels = ((DataBufferInt) m_image.getRaster().getDataBuffer()).getData();
    private Screen m_screen;
    private Map m_map;
    private List<Map> m_mapList = new ArrayList<Map>();
    private Camera m_camera;
    private PlayerEntity m_player;
    private UI m_menu;
    private HighScore m_highScore;
    private HighScoreDialog m_highScoreDialog = null;
    private final String m_saveFileName = "highscores.dat";

    public InputHandler m_input;

    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        m_screen = new Screen(WIDTH, HEIGHT);
        m_input = new InputHandler();
        m_menu = new MainMenuUI(m_input, this);

        File highScoreSave = new File(m_saveFileName);
        if (highScoreSave.exists() && !highScoreSave.isDirectory()) {
            loadObject();
        } else {
            m_highScore = new HighScore();
        }

        new SpriteSheet("/sprites.png");
        prepareMaps();
        m_map = m_mapList.get(0);
        m_camera = new Camera(m_input);
        m_camera.setPosition((m_map.getCameraX() << 4) + 8, m_map.getCameraY() << 4);

        addKeyListener(m_input);
    }

    public void prepareMaps() {
        Map map1 = new Map("/maps/org_map.png", "Original Map");
        map1.setCameraFollowsPlayer(false);
        map1.setRespawn(10, 4);
        map1.setCamera(9, 6);
        Map map2 = new Map("/maps/map2.png", "Map 2 (small)");
        map2.setRespawn(12, 8);
        map2.setCamera(12, 8);
        Map map3 = new Map("/maps/map3.png", "Map 3 (Big)");
        map3.setRespawn(13, 8);
        map3.setCamera(13, 8);
        // Map mapDebug = new Map("/maps/testmap.png", "Test Map");
        // mapDebug.setRespawn(10, 4);
        // mapDebug.setCamera(10, 4);

        m_mapList.add(map1);
        m_mapList.add(map2);
        m_mapList.add(map3);
        //m_mapList.add(mapDebug);
    }

    public void startGame() {
        m_player = new PlayerEntity(m_map.getRespawnX(), m_map.getRespawnY(), m_input);
        if (m_map.getCameraFollowsPlayer()) {
            m_camera.follow(m_player);
        } else {
            m_camera.setPosition((m_map.getCameraX() << 4) + 8, m_map.getCameraY() << 4);
        }
        m_map.add(m_player);
        m_map.regenerateApple();
        m_menu = null;
    }

    public void stopGame() {
        m_map.remove(m_player);
        m_camera.follow(null);
        m_camera.setPosition((m_map.getCameraX() << 4) + 8, m_map.getCameraY() << 4);
        m_player = null;
        m_menu = new MainMenuUI(m_input, this);
    }

    public void changeMap(int mapNumber) {
        m_map = m_mapList.get(mapNumber);
    }

    public void loadObject() {
        try {
            FileInputStream f = new FileInputStream(new File(m_saveFileName));
            ObjectInputStream o = new ObjectInputStream(f);

            m_highScore = (HighScore)o.readObject();

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveObject() {
        try {
            FileOutputStream f = new FileOutputStream(new File(m_saveFileName));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(m_highScore);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeUI(UI ui) {
        m_menu = ui;
    }

    public void start() {
        if (m_running) return;
        m_running = true;

        m_thread = new Thread(this);
        m_thread.start();
        
    }

    public void stop() {
        if (!m_running) return;
        saveObject();
        m_running = false;
        System.exit(0);
        // ((JFrame)getParent().getParent().getParent().getParent()).dispose();
        // try {
        //     m_thread.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

    public List<Map> getMapList() {
        return m_mapList;
    }

    public HighScore getHighScore() {
        return m_highScore;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        int xScroll = m_camera.getX() - m_screen.getWidth() / 2;
        int yScroll = m_camera.getY() - m_screen.getHeight() / 2;

        m_screen.setGraphics(g);
        m_screen.clear();

        m_map.render(xScroll, yScroll, m_screen);
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            m_pixels[i] = m_screen.m_pixels[i];
        }
        g.drawImage(m_image, 0, 0, getWidth(), getHeight(), null);
        if (m_menu != null) m_menu.render(m_screen);
        if (m_paused) {
            m_screen.renderText("PAUSED", ((WIDTH * SCALE) / 2) - 64, ((HEIGHT * SCALE) / 2) - 12, 24, 1, 0xffffff);
        }
        if (m_player != null) {
            m_screen.renderText("Score: " + m_player.getScore(), 0, 0, 24, 1, 0xffffff);
            if (m_player.isDead()) {
                m_screen.renderText("GAME OVER", ((WIDTH * SCALE) / 2) - 86, ((HEIGHT * SCALE) / 2) - 12, 24, 1, 0xffffff);
                if (m_canRespawn == 0) {
                    m_screen.renderText("CLICK ANY KEY TO RESTART", ((WIDTH * SCALE) / 2) - 150, ((HEIGHT * SCALE) / 2) + 24, 18, 1, 0xffffff);
                }
            }
        }
        g.dispose();
        bs.show();
    }

    public void update() {
        m_input.update();
        if (m_menu != null) m_menu.update();
        //m_player.update();
        m_map.update();
        m_camera.update();
        if (m_player != null) {
            if (m_input.back) {
                stopGame();

            } else if (m_player.isDead()) {
                Score score = new Score("PLAYER", m_player.getScore());
                if (m_input.anyKey) {
                    if (m_highScoreDialog == null && m_highScore.canAddNewScore(score))
                        m_highScoreDialog = new HighScoreDialog(score);
                    else 
                        m_canRespawn = -1;
                }
                if (m_canRespawn == -1) {
                    m_input.releaseAll();
                    if (m_highScoreDialog != null)
                        m_highScore.addNewScore(m_highScoreDialog.getScore());
                    m_highScoreDialog = null;
                    m_canRespawn = RESPAWN_TIME;
                    m_map.remove(m_player);
                    m_player = new PlayerEntity(m_map.getRespawnX(), m_map.getRespawnY(), m_input);
                    if (m_map.getCameraFollowsPlayer()) {
                        m_camera.follow(m_player);
                    }
                    m_map.add(m_player);
                    saveObject();
                } else {
                    if (m_highScoreDialog != null) {
                        if (!m_highScoreDialog.isVisible()) {                            
                            m_canRespawn = -1;
                            m_input.releaseAll();
                        }
                    }
                    if (m_canRespawn > 0)
                        m_canRespawn--;
                }
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long lastTimer = System.currentTimeMillis();
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while(m_running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                if (!m_paused) update();
                updates++;
                delta--;
            }

            render();
            frames++;

            while(System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                //System.out.println("ups: " + updates + " frames: " + frames);
                frames = 0;
                updates = 0;
            }
        }
    }

}
