package dev.zakrzu.snake.graphics;

public class Sprite {
    
    public static final int SIZE = 16;
    private int m_size;
    private int m_x, m_y;
    private int[] m_pixels;

    public static Sprite grassSprite = new Sprite(0, 0, 16);
    public static Sprite grass2Sprite = new Sprite(3, 0, 16);
    public static Sprite fakeGrassSprite = new Sprite(5, 0, 16);
    public static Sprite cbblSprite = new Sprite(4, 0, 16);
    public static Sprite rockSprite = new Sprite(1, 0, 16);
    public static Sprite appleSprite = new Sprite(2, 0, 16);

    public static Sprite snakeHeadLSprite = new Sprite(0, 2, 16);
    public static Sprite snakeHeadRSprite = new Sprite(6, 2, 16);
    public static Sprite snakeHeadUSprite = new Sprite(7, 1, 16);
    public static Sprite snakeHeadDSprite = new Sprite(3, 3, 16);

    public static Sprite snakeBodyLSprite = new Sprite(1, 2, 16);
    public static Sprite snakeBodyRSprite = new Sprite(5, 2, 16);
    public static Sprite snakeBodyUSprite = new Sprite(7, 2, 16);
    public static Sprite snakeBodyDSprite = new Sprite(3, 2, 16);

    public static Sprite snakeTailLSprite = new Sprite(2, 2, 16);
    public static Sprite snakeTailRSprite = new Sprite(4, 2, 16);
    public static Sprite snakeTailUSprite = new Sprite(7, 3, 16);
    public static Sprite snakeTailDSprite = new Sprite(3, 1, 16);

    public static Sprite voidSprite = new Sprite(0);

    public Sprite(int x, int y, int size) {
        m_size = size;
        m_pixels = new int[size * size];
        m_x = x << 4;
        m_y = y << 4;
        create(size);
    }

    public Sprite(int color) {
        m_pixels = new int[SIZE * SIZE];
        create(SIZE, color);
    }

    public void create(int size) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                m_pixels[x + y * size] = SpriteSheet.getPixels()[(x + m_x) + (y + m_y) * SpriteSheet.SIZE];
            }
        }
    }

    public void create(int size, int color) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                m_pixels[x + y * size] = color;
            }
            
        }
    }

    public int[] getPixels() {
        return m_pixels;
    }

    public int getSize() {
        return m_size;
    }

}
