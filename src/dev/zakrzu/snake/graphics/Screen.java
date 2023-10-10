package dev.zakrzu.snake.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.zakrzu.snake.map.tile.Tile;

public class Screen {
    
    private int width, height;
    public int[] pixels;
    private int TILE_SIZE = 16;
    private Graphics graphics;
    private int xOffset, yOffset;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void renderTile(int xp, int yp, int color) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < TILE_SIZE; y++) {
            int yt = y + yp;
            for (int x = 0; x < TILE_SIZE; x++) {
                int xt = x + xp;
                if (xt < -TILE_SIZE || xt >= width || yt < -TILE_SIZE || yt >= height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                pixels[xt + yt * width] = color;
            }
        }
    }

    public void renderTile(int xp, int yp, Tile tile) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < tile.getSprite().getSize(); y++) {
            int yt = y + yp;
            for (int x = 0; x < tile.getSprite().getSize(); x++) {
                int xt = x + xp;
                if (xt < -tile.getSprite().getSize() || xt >= width || yt < -tile.getSprite().getSize() || yt >= height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                int color = tile.getSprite().getPixels()[x + y * tile.getSprite().getSize()];
                if (color != 0xffff00ff && color != 0xff7f007f) {
                    pixels[xt + yt * width] = color;
                }
            }
        }
    }

    public void renderTile(int xp, int yp, Sprite sprite) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < sprite.getSize(); y++) {
            int yt = y + yp;
            for (int x = 0; x < sprite.getSize(); x++) {
                int xt = x + xp;
                if (xt < -sprite.getSize() || xt >= width || yt < -sprite.getSize() || yt >= height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                int color = sprite.getPixels()[x + y * sprite.getSize()];
                if (color != 0xffff00ff && color != 0xff7f007f) {
                    pixels[xt + yt * width] = color;
                }
            }
        }
    }

    public void renderText(String text, int x, int y, int size, int style, int color) {
        int r = (color & 0xff0000) >> 16;
        int g = (color & 0x00ff00) >> 8;
        int b = (color & 0x0000ff);
        Color c = new Color(r, g, b);
        Font font = new Font("Verdana", style, size);
        graphics.setColor(c);
        graphics.setFont(font);
        graphics.drawString(text, x, y + size);
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
