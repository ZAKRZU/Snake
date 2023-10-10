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
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

public class Game extends Canvas implements Runnable {

    public final static String TITLE = "Snake";
    public final static int WIDTH = 300;
    public final static int HEIGHT = WIDTH / 16 * 10;
    public final static int SCALE = 2;

    public boolean paused = false;

    private Thread thread;
    private boolean running = false;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private Screen screen;
    private Map map;
    private List<Map> mapList = new ArrayList<Map>();
    private Camera camera;
    private PlayerEntity player;
    private UI menu;
    private HighScore highScore;
    private HighScoreDialog highScoreDialog = null;
    private static final String SAVE_FILE_NAME = "highscores.dat";

    public InputHandler input;

    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(WIDTH, HEIGHT);
        input = new InputHandler();
        menu = new MainMenuUI(input, this);

        File highScoreSave = new File(SAVE_FILE_NAME);
        if (highScoreSave.exists() && !highScoreSave.isDirectory()) {
            loadObject();
        } else {
            highScore = new HighScore();
        }

        new SpriteSheet("/sprites.png");
        prepareMaps();
        map = mapList.get(0);
        camera = new Camera(input);
        camera.setPosition((map.getCameraX() << 4) + 8, map.getCameraY() << 4);

        addKeyListener(input);
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

        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        // mapList.add(mapDebug);
    }

    public void startGame() {
        player = new PlayerEntity(map.getRespawnX(), map.getRespawnY(), input);
        if (map.getCameraFollowsPlayer()) {
            camera.follow(player);
        } else {
            camera.setPosition((map.getCameraX() << 4) + 8, map.getCameraY() << 4);
        }
        map.add(player);
        map.regenerateApple();
        menu = null;
    }

    public void stopGame() {
        map.remove(player);
        camera.follow(null);
        camera.setPosition((map.getCameraX() << 4) + 8, map.getCameraY() << 4);
        player = null;
        menu = new MainMenuUI(input, this);
    }

    public void changeMap(int mapNumber) {
        map = mapList.get(mapNumber);
    }

    public void loadObject() {
        try {
            FileInputStream f = new FileInputStream(new File(SAVE_FILE_NAME));
            ObjectInputStream o = new ObjectInputStream(f);

            highScore = (HighScore) o.readObject();

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidClassException e) {
            System.out.println("File that contains high scores is incompatible with current version and cannot be loaded!");
            System.out.println("Backup of old scores has been created under " + SAVE_FILE_NAME + ".bak");
            System.out.println(e.getMessage());
            File oldFile = new File(SAVE_FILE_NAME);
            try {
                Files.copy(oldFile.toPath(), (new File(SAVE_FILE_NAME + ".bak")).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            highScore = new HighScore();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveObject() {
        try {
            FileOutputStream f = new FileOutputStream(new File(SAVE_FILE_NAME));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(highScore);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeUI(UI ui) {
        menu = ui;
    }

    public void start() {
        if (running) {
            return;
        }

        running = true;

        thread = new Thread(this);
        thread.start();

    }

    public void stop() {
        if (!running) {
            return;
        }
        saveObject();
        running = false;
        System.exit(0);
        // ((JFrame)getParent().getParent().getParent().getParent()).dispose();
        // try {
        // m_thread.join();
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public List<Map> getMapList() {
        return mapList;
    }

    public HighScore getHighScore() {
        return highScore;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        int xScroll = camera.getX() - screen.getWidth() / 2;
        int yScroll = camera.getY() - screen.getHeight() / 2;

        screen.setGraphics(g);
        screen.clear();

        map.render(xScroll, yScroll, screen);
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        if (menu != null) {
            menu.render(screen);
        }
        if (paused) {
            screen.renderText("PAUSED",
                    ((WIDTH * SCALE) / 2) - 64,
                    ((HEIGHT * SCALE) / 2) - 12,
                    24, 1, 0xffffff);
        }
        if (player != null) {
            screen.renderText("Score: " + player.getScore().getScore(), 0, 0, 24, 1, 0xffffff);
            if (player.isDead()) {
                screen.renderText("GAME OVER",
                        ((WIDTH * SCALE) / 2) - 86,
                        ((HEIGHT * SCALE) / 2) - 12,
                        24, 1, 0xffffff);
                if (player.canRespawn()) {
                    screen.renderText("CLICK ANY KEY TO RESTART", ((WIDTH * SCALE) / 2) - 150,
                            ((HEIGHT * SCALE) / 2) + 24, 18, 1, 0xffffff);
                }
            }
        }
        g.dispose();
        bs.show();
    }

    public void update() {
        input.update();
        if (menu != null) {
            menu.update();
        }

        map.update();
        camera.update();
        if (player != null) {
            if (input.back) {
                if (highScoreDialog != null) {
                    highScoreDialog.dispose();
                    highScoreDialog = null;
                }
                stopGame();

            } else if (player.isDead()) {
                if (highScoreDialog == null) {
                    if (highScore.canAddNewScore(player.getScore())) {
                        highScoreDialog = new HighScoreDialog(this, player.getScore());
                        return;
                    }
                } else {
                    if (highScoreDialog.isVisible()) {
                        return;
                    }
                }

                if (!input.anyKey) {
                    return;
                }


                if (!player.canRespawn()) {
                    return;
                }

                input.releaseAll();
                highScoreDialog = null;
                map.remove(player);
                player = new PlayerEntity(map.getRespawnX(), map.getRespawnY(), input);
                if (map.getCameraFollowsPlayer()) {
                    camera.follow(player);
                }
                map.add(player);
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
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                if (!paused) {
                    update();
                }
                updates++;
                delta--;
            }

            render();
            frames++;

            while (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                // System.out.println("ups: " + updates + " frames: " + frames);
                frames = 0;
                updates = 0;
            }
        }
    }

}
