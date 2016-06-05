package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Andrey on 05.06.2016.
 */
public final class Sprites {
    public Sprite hero;
    public Sprite zombie;
    public Sprite items;
    public Sprite npc;
    public Sprite shot;
    public Sprite grenade;
    public Sprite map;
    public Sprite health;
    public Sprite bar;
    public Sprite explosion;
    public Sprite minimapMarkerNpc;
    public Sprite minimapMarkerHero;
    public Sprite axe_enemy;
    public Sprite mummy_enemy;
    public Sprite throwing_axe;
    public Sprite boss;
    public Sprite boss_bar;
    public Sprite boss_indicator;

    public Texture texture_hero;
    public Texture texture_zombie;
    public Texture texture_items;
    public Texture texture_npc;
    public Texture texture_shot;
    public Texture texture_grenade;
    public Texture texture_map;
    public Texture texture_health;
    public Texture texture_bar;
    public Texture texture_explosion;

    public Texture texture_minimapMarkerNpc;
    public Texture texture_minimapMarkerHero;

    public Texture texture_axe_enemy;
    public Texture texture_mummy_enemy;

    public Texture texture_throwing_axe;

    public Texture texture_boss;
    public Texture texture_boss_bar;
    public Texture texture_boss_indicator;

    public TiledMap level0;
    public TiledMap level1;


    public static Vector2 GetSpriteCenter(Sprite sprite)
    {
        return new Vector2(sprite.getX() + sprite.getWidth()/ 2.f,sprite.getY() + sprite.getHeight() / 2.f);
    }

    public static Rectangle GetSpriteRect(Sprite sprite)
    {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }


    public static Vector2 ComputeDistanceBetweenSprites(Sprite s1, Sprite s2)
    {
        return new Vector2(Math.abs(s1.getX() - s2.getX()), Math.abs(s1.getY() - s2.getY()));
    }
}
