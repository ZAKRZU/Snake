package dev.zakrzu.snake.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.zakrzu.snake.map.tile.Tile;

public class Screen {
    
    private int m_width, m_height;
    public int[] m_pixels;
    private int TILE_SIZE = 16;
    private Graphics m_graphics;
    private int m_xOffset, m_yOffset;

    public Screen(int width, int height) {
        m_width = width;
        m_height = height;
        m_pixels = new int[width * height];
    }

    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }

    public void setGraphics(Graphics graphics) {
        m_graphics = graphics;
    }

    public void clear() {
        for (int i = 0; i < m_pixels.length; i++) {
            m_pixels[i] = 0;
        }
    }

    public void renderTile(int xp, int yp, int color) {
        xp -= m_xOffset;
        yp -= m_yOffset;
        for (int y = 0; y < TILE_SIZE; y++) {
            int yt = y + yp;
            for (int x = 0; x < TILE_SIZE; x++) {
                int xt = x + xp;
                if (xt < -TILE_SIZE || xt >= m_width || yt < -TILE_SIZE || yt >= m_height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                m_pixels[xt + yt * m_width] = color;
            }
        }
    }

    public void renderTile(int xp, int yp, Tile tile) {
        xp -= m_xOffset;
        yp -= m_yOffset;
        for (int y = 0; y < tile.getSprite().getSize(); y++) {
            int yt = y + yp;
            for (int x = 0; x < tile.getSprite().getSize(); x++) {
                int xt = x + xp;
                if (xt < -tile.getSprite().getSize() || xt >= m_width || yt < -tile.getSprite().getSize() || yt >= m_height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                int color = tile.getSprite().getPixels()[x + y * tile.getSprite().getSize()];
                if (color != 0xffff00ff && color != 0xff7f007f) {
                    m_pixels[xt + yt * m_width] = color;
                }
            }
        }
    }

    public void renderTile(int xp, int yp, Sprite sprite) {
        xp -= m_xOffset;
        yp -= m_yOffset;
        for (int y = 0; y < sprite.getSize(); y++) {
            int yt = y + yp;
            for (int x = 0; x < sprite.getSize(); x++) {
                int xt = x + xp;
                if (xt < -sprite.getSize() || xt >= m_width || yt < -sprite.getSize() || yt >= m_height) break;
                if (xt < 0) xt = 0;
                if (yt < 0) yt = 0;
                int color = sprite.getPixels()[x + y * sprite.getSize()];
                if (color != 0xffff00ff && color != 0xff7f007f) {
                    m_pixels[xt + yt * m_width] = color;
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
        m_graphics.setColor(c);
        m_graphics.setFont(font);
        m_graphics.drawString(text, x, y + size);
    }

    public void setOffset(int xOffset, int yOffset) {
        m_xOffset = xOffset;
        m_yOffset = yOffset;
    }
}
