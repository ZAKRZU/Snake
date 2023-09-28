package dev.zakrzu.snake.map.tile;

import dev.zakrzu.snake.graphics.Screen;
import dev.zakrzu.snake.graphics.Sprite;

public class Tile {
    
    private Sprite m_sprite;

    public static Tile fakeGrassTile = new FakeGrassTile(Sprite.fakeGrassSprite);
    public static Tile grassTile = new GrassTile(Sprite.grassSprite);
    public static Tile grass2Tile = new GrassTile(Sprite.grass2Sprite);
    public static Tile rockTile = new RockTile(Sprite.rockSprite);
    public static Tile cbbleTile = new CbbleTile(Sprite.cbblSprite);

    public static Tile voidTile = new VoidTile(Sprite.voidSprite);

    public Tile(Sprite sprite) {
        m_sprite = sprite;
    }

    public Sprite getSprite() {
        return m_sprite;
    }

    public void render(int x, int y, Screen screen) {
    }

    public boolean solid() {
        return false;
    }

}
