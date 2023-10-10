package dev.zakrzu.snake.graphics;

public class Sprite {
    
    public static final int SIZE = 16;
    private int size;
    private int xt, yt; // tile based coordinates
    private int[] pixels;

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
        this.size = size;
        pixels = new int[size * size];
        xt = x << 4;
        yt = y << 4;
        create(size);
    }

    public Sprite(int color) {
        pixels = new int[SIZE * SIZE];
        create(SIZE, color);
    }

    public void create(int size) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[x + y * size] = SpriteSheet.getPixels()[(x + xt) + (y + yt) * SpriteSheet.SIZE];
            }
        }
    }

    public void create(int size, int color) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[x + y * size] = color;
            }
            
        }
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getSize() {
        return size;
    }

}
