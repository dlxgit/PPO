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
    public static Sprite hero;
    public static Sprite zombie;
    public static Sprite items;
    public static Sprite npc;
    public static Sprite shot;
    public static Sprite grenade;
    public static Sprite map;
    public static Sprite health;
    public static Sprite bar;
    public static Sprite explosion;
    public static Sprite minimapMarkerNpc;
    public static Sprite minimapMarkerHero;

    public static Sprite axe_enemy;
    public static Sprite mummy_enemy;

    public static Sprite throwing_axe;

    public static Sprite boss;
    public static Sprite boss_bar;
    public static Sprite boss_indicator;


    public static Texture texture_hero;
    public static Texture texture_zombie;
    public static Texture texture_items;
    public static Texture texture_npc;
    public static Texture texture_shot;
    public static Texture texture_grenade;
    public static Texture texture_map;
    public static Texture texture_health;
    public static Texture texture_bar;
    public static Texture texture_explosion;

    public static Texture texture_minimapMarkerNpc;
    public static Texture texture_minimapMarkerHero;

    public static Texture texture_axe_enemy;
    public static Texture texture_mummy_enemy;

    public static Texture texture_throwing_axe;

    public static Texture texture_boss;
    public static Texture texture_boss_bar;
    public static Texture texture_boss_indicator;

    public static TiledMap level0;
    public static TiledMap level1;


    public static Vector2 GetSpriteCenter(Sprite sprite)
    {
        return new Vector2(sprite.getX() + sprite.getWidth()/ 2.f,sprite.getY() + sprite.getHeight() / 2.f);
    }

    public static Rectangle GetSpriteRect(Sprite sprite)
    {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public static void InitiazlizeSprites(AssetManager manager, Sprites sprites)
    {
        //enemies
        //common
        Texture texture_zombie;
        texture_zombie = manager.get("resources/images/zombie.png", Texture.class);
        sprites.texture_zombie = texture_zombie;
        sprites.zombie.setTexture(sprites.texture_zombie);
        //axe
        Texture texture_axe_enemy;
        texture_axe_enemy = manager.get("resources/images/axe_enemy.png");
        sprites.texture_axe_enemy = texture_axe_enemy;
        sprites.axe_enemy.setTexture(sprites.texture_axe_enemy);

        //throwing_axe
        Texture texture_throwing_axe;
        texture_throwing_axe = manager.get("resources/images/throwing_axe.png");
        sprites.texture_throwing_axe = texture_throwing_axe;
        sprites.throwing_axe.setTexture(sprites.texture_throwing_axe);

        //boss
        Texture texture_boss;
        texture_boss = manager.get("resources/images/boss.png");
        sprites.texture_boss = texture_boss;
        sprites.boss.setTexture(sprites.texture_boss);

        //boss_bar
        Texture texture_boss_bar;
        texture_boss_bar = manager.get("resources/images/boss_bar.png");
        sprites.texture_boss_bar = texture_boss_bar;
        sprites.boss_bar.setTexture(sprites.texture_boss_bar);
        //boss_indicator
        Texture texture_boss_indicator;
        texture_boss_indicator = manager.get("resources/images/boss_indicator.png");
        sprites.texture_boss_indicator = texture_boss_indicator;
        sprites.boss_indicator.setTexture(sprites.texture_boss_indicator);

        //shot
        Texture texture_shot;
        texture_shot = manager.get("resources/images/shots.png");
        sprites.texture_shot = texture_shot;
        sprites.shot.setTexture(sprites.texture_shot);
        //items
        Texture texture_items;
        texture_items = manager.get("resources/images/items.png");
        sprites.texture_items = texture_items;
        sprites.items.setTexture(sprites.texture_items);
        //healthbar
        Texture texture_bar;
        texture_bar = manager.get("resources/images/bar.png");
        sprites.texture_bar = texture_bar;
        sprites.bar.setTexture(sprites.texture_bar);
        //health
        Texture texture_health;
        texture_health = manager.get("resources/images/health.png");
        sprites.texture_health = texture_health;
        sprites.health.setTexture(sprites.texture_health);
        //Npc
        Texture texture_npc;
        texture_npc = manager.get("resources/images/npcs.png");
        sprites.texture_npc = texture_npc;
        sprites.npc.setTexture(sprites.texture_npc);
        //Grenade
        Texture texture_grenade;
        texture_grenade = manager.get("resources/images/grenade.png");
        sprites.texture_grenade = texture_grenade;
        sprites.grenade.setTexture(sprites.texture_grenade);

        //Explosion
        Texture texture_explosion;
        texture_explosion = manager.get("resources/images/explosion.png");
        sprites.texture_explosion = texture_explosion;
        sprites.explosion.setTexture(sprites.texture_explosion);
        //npc Minimap Marker
        Texture texture_npc_marker;
        texture_npc_marker = manager.get("resources/images/marker_npc.png");
        sprites.texture_minimapMarkerNpc = texture_npc_marker;
        sprites.minimapMarkerNpc.setTexture(sprites.texture_minimapMarkerNpc);
        //hero Minimap Marker
        Texture texture_hero_marker;
        texture_hero_marker = manager.get("resources/images/marker_hero.png");
        sprites.texture_minimapMarkerHero = texture_hero_marker;
        sprites.minimapMarkerHero.setTexture(sprites.texture_minimapMarkerHero);


        sprites.level0 = manager.get("level0.tmx");
        sprites.level1 = manager.get("level1.tmx");
    }

    public static Vector2 ComputeDistanceBetweenSprites(Sprite s1, Sprite s2)
    {
        return new Vector2(Math.abs(s1.getX() - s2.getX()), Math.abs(s1.getY() - s2.getY()));
    }
}
