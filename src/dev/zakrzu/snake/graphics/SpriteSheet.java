package dev.zakrzu.snake.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    
    public static final int SIZE = 128;
    private static int[] pixels;

    public SpriteSheet(String path) {
        loadSheet(path);
    }

    public static int[] getPixels() {
        return pixels;
    }

    public void loadSheet(String path) {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int width = image.getWidth();
            int height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
