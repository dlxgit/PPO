package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Andrey on 04.06.2016.
 */
public final class Constants {
    public static enum Direction
    {
        NONE,
        UP,
        UPRIGHT,
        RIGHT,
        DOWNRIGHT,
        DOWN,
        DOWNLEFT,
        LEFT,
        UPLEFT,
        FOLLOW
    };

    public static enum EnemyType
    {
        COMMON,
        AXE,
        MUMMY,
        FIRE,
    };

    public static enum BossState
    {
        SPAWNING,
        MOVING,
        STOMPING,
        SHOOTING,
        SHRINKING,
        DYING
    };

    public static enum BossEvent
    {
        MOVE_OUT,
        MOVE_TO,
        CHARGE,
        SHOOT,
        STOMP_FOR_ZOMBIES,
    };

    public static enum EnemyState
    {
        NOTSPAWNED,  //cant move (raising from the ground)
        ACTIVE,
        DEAD,      //cant move (health = 0)
        EXPLODED  //if animation has finished (for deleting from list)
    };

    public static enum HeroState
    {
        DAMAGED,
        SMASHED,
        NORMAL,
        TRANSFORMING,  //changing from normal to beast
        BEAST
    };

    public static enum ShotType
    {
        BULLET,
        USED_GRENADE
    };

    public static enum GameState
    {
        START_GAME,
        PLAY,
        LEVEL_FINISH,
        END_GAME,
        RESTART,
        FINISH,
    };

    public static enum NameItem
    {
        DRINK,
        PISTOL,
        RIFLE,
        AMMO,
        KEY,
        MIXTURE,
        GRENADE,
        SODA
    };

    public static enum NpcType
    {
        PHOTOGRAPHS,
        BABY,
        TEACHER,
        GIRL,
        DOG,
        SOLDIER,
        SEARCHER,
        COOK,
    };

    public static enum NpcState
    {
        LIVING,
        KILLED,
        SURVIVED,
    };


//spawn settings
    public static final float ZOMBIE_SPAWN_RADIUS_COLLISION = 80.f;


//map
    public static final Vector2 WINDOW_SIZE = new Vector2(800,600);
    public static final Vector2 TILEMAP_SIZE = new Vector2( 50,27);
    public static final int STEP_TILE = 64;
    public static final int STEP = 48;

    public static final int HEIGHT_MAP = 30;
    public static final int WIDTH_MAP = 76;
    public static final Vector2 LEVEL_BOSS_SIZE = new Vector2( 20, 12);

    public static final int LEVEL_BOSS_MAX_LOOT_QUANTITY = 2;

//boss
    public static final int BOSS_MAX_HEALTH = 10000;
    public static final float BOSS_STAGE_TIME = 10.f;

    public static final float STEP_BOSS = 3.f;
    public static final float STEP_BOSS_FOLLOW = 5.f;
    public static final float STEP_BOSS_CHARGE = 12.f;

    public static final float BOSS_DIR_CHANGE_TIME = 2.f;

    public static final float BOSS_EVENT_COME_SHOOT_DISTANCE = 300;


    public static final float BOSS_MILK_MAX_DISTANCE = 0;   //

    public static final float BOSS_SPAWN_ENEMY_INTERVAL = 1.f;   //time for 1 enemy spawn while stomping

    public static final float BOSS_FOLLOW_FOR_SHOOT_TIME = 7.f;


    public static final float BOSS_SHOT_COOLDOWN = 1.f;

    public static final float BOSS_SHOOTING_COOLDOWN = 0.3f;


    public static final Vector2 LEVEL1_CENTER_POS = new Vector2(12 * 64, 6 * 64);
    public static final Vector2 LEVEL1_ZOMBIE_SPAWN_SPOTS[] = new Vector2[]{new Vector2(11 * 64, 5 * 64), new Vector2( 13 * 64, 5 * 64), new Vector2(11 * 64, 7 * 64), new Vector2(13 * 64, 7 * 64)};

//boss_bar
public static final Vector2 BOSS_BAR_POSITION = new Vector2((WINDOW_SIZE.x / 2) - 132, 40.f);
public static final Vector2 BOSS_INDICATOR_POSITION = new Vector2(WINDOW_SIZE.x / 2 - 132 + 9, 46.f);


//minimap
public static final Vector2 MINIMAP_START_POS = new Vector2(500.f,100.f);
public static final float MINIMAP_DISTANCE_SCALE = 5.f;
public static final float MINIMAP_CIRCLE_RADIUS = 50.f;

//movespeed of objects
public static final float STEP_HERO = 5.f;
public static final float STEP_HERO_BEAST = 7.f;
public static final float STEP_ZOMBIE = 2.f;
public static final float STEP_ZOMBIE_ACTIVE = 3.f;
public static final float STEP_SHOT = 12.f;
public static final float STEP_GRENADE = 5.f;
public static final float STEP_ENEMY_AXE = 1.f;


//coefficients
public static final float ZOMBIE_INCREASE_RATE_AXE = 2;


//enemies
public static final int ENEMY_AXE_ATTACK_DAMAGE = 20;
public static final float ENEMY_AXE_SPAWN_DELAY = 3.f;

public static final float ENEMY_AXE_ATTACK_COOLDOWN = 3.f;
public static final float ENEMY_AXE_ACTION_COOLDOWN = 3.f;
public static final float ENEMY_AXE_THROWING_MAX_TIME = 2.f;
public static final float ENEMY_AXE_THROWING_SPEED = 5.f;
public static final float ENEMY_AXE_CHARGE_TIME = 3.f;
public static final float ENEMY_AXE_CHARGE_COOLDOWN = 8;
public static final float ENEMY_AXE_CHARGE_SPEED_INCREASE_PER_LOOP = 0.2f;

public static final float ENEMY_AXE_THROWING_STEP_PER_LOOP = 5;

//damage
public static final float BOSS_SMASH_DAMAGE = 30.f;
public static final float ZOMBIE_DAMAGE = 30.f;
public static final int DMG_ITEM[] = new int[]{0, 100, 100, 0, 0, 0, 200, 100};
public static final int HERO_BEAST_DAMAGE = 150;

public static final float THROWING_AXE_DAMAGE = 15.f;
public static final float BOSS_MILK_DAMAGE = 7.f;

public static final float HERO_BEAST_DAMAGE_RESISTANCE = 0.2f;


//distance
public static final int SHOT_MAX_DISTANCE = 400;
public static final int ZOMBIE_VISION_DISTANCE = 300;

//health of objects
public static final int ZOMBIE_MAX_HP = 100;
public static final int HP_PER_DRINK = 40;
public static final int NPC_MAX_HEALTH = 50;

//time
public static final float ITEM_REUSE_COOLDOWN[] = new float[]{ 0.35f, 0.35f,0.15f,0.15f,0.15f,0.15f, 0.1f };
public static final int BEAST_MAX_TIME = 12;
public static final float HERO_BEAST_ATTACK_TIME = 0.3f;

//heroTime
public static final float HERO_SMASH_DURATION = 1.f;


//const float WEAPON_RELOAD_TIME = 1.5f;
public static final float WEAPON_RELOAD_TIME = 0.1f;

public static final int TIME_GAME_STEP = 10;
public static final float SCREEN_UPDATE_TIME = 15.f;
//const float SCREEN_UPDATE_TIME = 150.f;
public static final float GRENADE_MAX_TIME = 1.f;  //throwtime
public static final int ZOMBIE_DIR_CHANGE_TIME = 5;

//item quantity settings
public static final int AMMO_PACKS = 4;
public static final int MAX_AMMO[] = new int[]{ 1,12,30,1,1,1,1,1 };

//counts
public static final int MAX_NUMBER_OF_NEIGHBORS = 8;

//math
public static final float DIAGONAL_STEP = 0.66f;  //

//names
public static final String ITEM_NAMES[] = new String[]{ "drink", "pistol", "rifle", "ammo", "key", "mixture", "grenade", "soda" };
}
