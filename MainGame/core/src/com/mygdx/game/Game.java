package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */

public class Game {
    Hero hero;
    float time;
    //RenderWindow  window;
    OrthogonalTiledMapRenderer renderer;
    Viewport viewPort;
    OrthographicCamera camera;
    Vector<Inventory> inventoryList;
    Vector<Loot> lootList;
    Vector<Shot> shotList;
    Vector<Enemy> zombieList;
    Vector<Npc> npcList;
    Vector<Explosion> explosionList;
    Vector<Throwing> throwingList;

    Constants.GameState state;

    BitmapFont font;
    //Text text;
/*
    Sprites sprites;
    AudioResources audio;

    Texture explosionTexture;
    Sprite explosionSprite;
*/
    TiledMap lvl;
    Vector<MapObject> allObjects;
    Vector<MapObject> solidObjects;

    //MiniMap miniMap;

    int level;
    Boss boss;

    public static Sprites sprites;
    public static AssetManager manager;

    void create() {

        System.out.println("CREATE GAME");
        hero = new Hero();
        time = 0;
        //RenderWindow  window;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera.position.set(0,0,0);
        //viewPort = new FitViewport(3000, 1700, camera);

        viewPort = new FitViewport(900, 500, camera);
        viewPort.setScreenX(0);
        viewPort.setScreenY(0);
        inventoryList = new java.util.Vector<Inventory>();
        lootList = new Vector<Loot>();
        shotList = new Vector<Shot>();
        zombieList = new Vector<Enemy>();
        npcList = new Vector<Npc>();
        explosionList = new Vector<Explosion>();
        throwingList = new Vector<Throwing>();

        //Constants.GameState state = new Constants.GameState();

        font = new BitmapFont();
        lvl = new TiledMap();

        allObjects = new Vector<MapObject>();
        solidObjects = new Vector<MapObject>();
        level = 0;
        boss = new Boss();
        sprites = new Sprites();
        manager = new AssetManager();
        InitiazlizeSprites();

        InitializeInventory(sprites);
        InitializeHero();


        hero.item = inventoryList.get(0);
        hero.item.sprite.setRegion(32, 0, 32, 32);

        //InitializeView(view);
        //InitializeText(font,text);
        lvl = sprites.level0;

        renderer = new OrthogonalTiledMapRenderer(lvl, 2);
        renderer.setView(camera);
        level = 0;
        hero.maxNeighbors = Constants.MAX_NUMBER_OF_NEIGHBORS;
        //allObjects = lvl.;
        //solidObjects = lvl.GetObjects("solid");

        //ZombieSpawn(zombieList, time, 100, 100, sprites,COMMON);
        SpawnEnemy(100, 100, Constants.EnemyType.AXE);
        System.out.println("Init_Loot");
        InitializeLoot(sprites.items);
        System.out.println("Init_Loot_SUCCESS");
        InitializeNpc(sprites.npc);
        time = 0;
        state = Constants.GameState.PLAY;
        hero.pos = new Vector2(200, 200);
        hero.sprite.setPosition(viewPort.getScreenX() + viewPort.getScreenWidth() / 2, viewPort.getScreenY() + viewPort.getScreenHeight() / 2);
        //InitializeMinimap(miniMap, npcList, sprites);

        //TODO: comm
        //InitializeSoundResouces(audio);
        System.out.println("Init_GAME_SUCCESS");
    }


    public void InitializeNpc(Sprite sprite_npc) {
        Npc npc = new Npc();
        npc.currentFrame = 0;
        npc.health = 20;
        npc.sprite = new Sprite(sprites.npc);
        npc.state = Constants.NpcState.LIVING;

        npc.type = Constants.NpcType.PHOTOGRAPHS;
        npc.pos = new Vector2(5 * Constants.STEP, 8 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.BABY;
        npc.pos = new Vector2(50 * Constants.STEP, 10 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.TEACHER;
        npc.pos = new Vector2(9 * Constants.STEP, 15 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.DOG;
        npc.pos = new Vector2(53 * Constants.STEP, 15 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.SOLDIER;
        npc.pos = new Vector2(28 * Constants.STEP, 23 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.SEARCHER;
        npc.pos = new Vector2(55 * Constants.STEP, 4 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.COOK;
        npc.pos = new Vector2(20 * Constants.STEP, 14 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);
        npc.type = Constants.NpcType.GIRL;
        npc.pos = new Vector2(15 * Constants.STEP, 6 * Constants.STEP);
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);
    }

    ;

    public void Draw(SpriteBatch batch) {
        renderer.render();
        DrawLoot(batch);
        DrawShots(batch, hero);
        DrawNpc(batch);
        DrawEnemies(batch);
        //DrawHero(batch);

        if (level == 1) {
            DrawBoss(batch, boss.sprite);
            DrawBossBar(batch, boss, new Vector2(viewPort.getScreenX() + viewPort.getScreenWidth() / 2, viewPort.getScreenY() + viewPort.getScreenHeight() / 2));
        }
        DrawThrowings(batch);
    }


    public void SpawnEnemyRandomly(int zombiesRemaining) {
        //TODO: MAKE FUNCTION MORE READABLE
        do {
            boolean needNewBlock = false;

            Vector2 newPos = new Vector2((float) ((int) (Math.random() * Constants.WIDTH_MAP) * Constants.STEP), (float) (((int) (Math.random() * Constants.HEIGHT_MAP) * Constants.STEP)));

            Rectangle lootRect = new Rectangle(newPos.x, newPos.y, Constants.ZOMBIE_SPAWN_RADIUS_COLLISION, Constants.ZOMBIE_SPAWN_RADIUS_COLLISION);
            //TODO: comm
            /*
            for (int i = 0; i < objects.size(); ++i)
            {
                if (lootRect.overlaps(objects.get(i).rect())
                {
                    needNewBlock = true;
                    break;
                }
            }
            */
            if (!needNewBlock) {
                for (Enemy enemy : zombieList)
                    if ((Math.abs(enemy.pos.x - newPos.x) < 100 && Math.abs(enemy.pos.y - newPos.y) < 100)) {
                        needNewBlock = true;
                        break;
                    }
                if (needNewBlock == false) {
                    //sprites.enemy.setPosition(newPos);
                    //ZombieSpawn(zombieList, time, newPos.x,newPos.y, sprite_zombie, COMMON);
                    if (time < 10) {
                        SpawnEnemy((int) newPos.x, (int) newPos.y, Constants.EnemyType.COMMON);
                    } else {
                        Constants.EnemyType type = Constants.EnemyType.COMMON;
                        if (!zombieList.isEmpty()) {
                            if (zombieList.get(zombieList.size() - 1).type == Constants.EnemyType.AXE)
                                type = Constants.EnemyType.COMMON;
                            else type = Constants.EnemyType.AXE;
                        }
                        SpawnEnemy((int) newPos.x, (int) newPos.y, type);
                    }
                    //zombieList[zombieList.size() - 1].sprite = sprites.zombie;

                    zombiesRemaining -= 1;
                }
            }
        } while (zombiesRemaining > 0);
    }

    public void SpawnEnemy(int posX, int posY, final Constants.EnemyType type) {
        Enemy enemy = new Enemy();
        enemy.pos = new Vector2(posX, posY);

        enemy.health = Constants.ZOMBIE_MAX_HP;

        //enemy.texture = texture_zombie;
        enemy.type = type;


        enemy.currentFrame = 0;
        enemy.attack_time = 0;
        enemy.dir = Constants.Direction.NONE;
        enemy.dirLast = Constants.Direction.NONE;
        enemy.follow = false;
        enemy.isNear = false;

        if (enemy.type == Constants.EnemyType.COMMON) {
            enemy.state = Constants.EnemyState.NOTSPAWNED;
            enemy.sprite = sprites.zombie;
            enemy.sprite.setRegion(5, 5, 30, 30);
            enemy.spawnDelay = 0;

        } else if (enemy.type == Constants.EnemyType.AXE) {
            enemy.state = Constants.EnemyState.NOTSPAWNED;
            enemy.sprite = sprites.axe_enemy;
            enemy.sprite.setRegion(29, 42, 22, 31);
            enemy.spawnDelay = Constants.ENEMY_AXE_SPAWN_DELAY;
            enemy.attackCooldown = Constants.ENEMY_AXE_ATTACK_COOLDOWN;
        }

        enemy.spawnTime = time;
        enemy.dirChangeTime = 0;

        enemy.isAttack = false;
        enemy.isAction = false;
        enemy.last_action_time = 0;

        enemy.sprite.setPosition((float) posX, (float) posY);
        zombieList.add(enemy);
    }

    ;

    public void InitializeHero() {
        hero.slotNo = 0;
        hero.nSlots = 1;
        hero.health = 100;
        hero.sprite = new Sprite();

        hero.sprite.setTexture(sprites.texture_hero);
        System.out.println("SPRITE_LOADEDDDDD");
        //sprite.setRegion(4, 4, 32, 32));
        hero.dir = Constants.Direction.NONE;
        hero.dirLast = Constants.Direction.DOWN;
        hero.currentFrame = 0;
        hero.sprite.setPosition(6 * 32, 12 * 32);  //start position
        hero.state = Constants.HeroState.NORMAL;
        hero.isBeastAttack = false;
        hero.isWeaponSwitch = false;
        hero.lastAttackTime = 0;
        hero.lastReloadTime = 0;
        hero.savedNeighbors = 0;
        hero.damageResistance = 1;
        hero.isSmashed = false;

        hero.isSoundBeastAtttack = false;
        hero.isSoundLoot = false;
        hero.isSoundNpcSurvive = false;
        hero.isSoundShoot = false;
        hero.isSoundTakeDamage = false;
        hero.isSoundEnemyExplosion = false;
    }

    ;

    void InitializeInventory(Sprites gameSprites) {
        Inventory inventory = new Inventory();

        inventory.name = Constants.NameItem.PISTOL;
        inventory.current = 0;
        inventory.quantity = 7;
        inventory.sprite = gameSprites.items;
        inventory.sprite.setRegion(32, 0, 32, 32);
        inventoryList.add(inventory);
    }


    public static void InitiazlizeSprites() {
        manager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));

        //enemies
        //common
        manager.load("images/zombie.png", Texture.class);
        manager.load("images/axe_enemy.png", Texture.class);
        manager.load("images/throwing_axe.png", Texture.class);
        manager.load("images/boss.png", Texture.class);
        manager.load("images/boss_bar.png", Texture.class);
        manager.load("images/boss_indicator.png", Texture.class);
        manager.load("images/shots.png", Texture.class);
        manager.load("images/items.png", Texture.class);
        manager.load("images/bar.png", Texture.class);
        manager.load("images/health.png", Texture.class);
        manager.load("images/Npcs.png", Texture.class);
        sprites.zombie = new Sprite();
        manager.load("images/grenade.png", Texture.class);

        manager.load("images/explosion.png", Texture.class);
        manager.load("images/marker_npc.png", Texture.class);
        manager.load("images/marker_hero.png", Texture.class);

        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("levels/level1.tmx", TiledMap.class);

        manager.load("levels/level0.tmx", TiledMap.class);
        //sprites.texture_zombie = texture_zombie;

        //axe
        manager.finishLoading();

        sprites.texture_axe_enemy = manager.get("images/axe_enemy.png", Texture.class);
        //sprites.texture_axe_enemy = texture_axe_enemy;
        sprites.axe_enemy = new Sprite();
        sprites.axe_enemy = new Sprite(sprites.texture_axe_enemy);

        //throwing_axe


        sprites.texture_throwing_axe = manager.get("images/throwing_axe.png");
        //sprites.texture_throwing_axe = texture_throwing_axe;
        sprites.throwing_axe = new Sprite();
        sprites.throwing_axe = new Sprite(sprites.texture_throwing_axe);

        //boss


        sprites.texture_boss = manager.get("images/boss.png");
        //sprites.texture_boss = texture_boss;
        sprites.boss = new Sprite();
        sprites.boss = new Sprite(sprites.texture_boss);

        //boss_bar

        sprites.texture_boss_bar = manager.get("images/boss_bar.png");
        //sprites.texture_boss_bar = texture_boss_bar;
        sprites.boss_bar = new Sprite();
        sprites.boss_bar = new Sprite(sprites.texture_boss_bar);
        //boss_indicator

        sprites.texture_boss_indicator = manager.get("images/boss_indicator.png");
        //sprites.texture_boss_indicator = texture_boss_indicator;
        sprites.boss_indicator = new Sprite();
        sprites.boss_indicator = new Sprite(sprites.texture_boss_indicator);
        //shot

        sprites.texture_shot = manager.get("images/shots.png");
        //sprites.texture_shot = texture_shot;
        sprites.shot = new Sprite();
        sprites.shot = new Sprite(sprites.texture_shot);
        //items

        sprites.texture_items = manager.get("images/items.png");
        //sprites.texture_items = texture_items;
        sprites.items = new Sprite();
        sprites.items = new Sprite(sprites.texture_items);
        //healthbar

        sprites.texture_bar = manager.get("images/bar.png");
        //sprites.texture_bar = texture_bar;
        sprites.bar = new Sprite();
        sprites.bar = new Sprite(sprites.texture_bar);
        //health

        sprites.texture_health = manager.get("images/health.png");
        //sprites.texture_health = texture_health;
        sprites.health = new Sprite();
        sprites.health = new Sprite(sprites.texture_health);
        //Npc

        sprites.texture_npc = manager.get("images/Npcs.png");
        //sprites.texture_npc = texture_npc;
        //sprites.npc = new Sprite();
        sprites.npc = new Sprite(sprites.texture_npc);
        //Grenade

        sprites.texture_grenade = manager.get("images/grenade.png");
        //sprites.texture_grenade = texture_grenade;
        sprites.grenade = new Sprite();
        sprites.grenade = new Sprite(sprites.texture_grenade);

        //Explosion

        sprites.texture_explosion = manager.get("images/explosion.png");
        //sprites.texture_explosion = texture_explosion;
        sprites.explosion = new Sprite();
        sprites.explosion = new Sprite(sprites.texture_explosion);
        //npc Minimap Marker


        sprites.texture_minimapMarkerNpc = manager.get("images/marker_npc.png");
        //sprites.texture_minimapMarkerNpc = texture_npc_marker;
        sprites.minimapMarkerNpc = new Sprite();
        sprites.minimapMarkerNpc = new Sprite(sprites.texture_minimapMarkerNpc);
        //hero Minimap Marker


        sprites.texture_minimapMarkerHero = manager.get("images/marker_hero.png");
        //sprites.texture_minimapMarkerHero = texture_hero_marker;
        sprites.minimapMarkerHero = new Sprite();
        sprites.minimapMarkerHero = new Sprite(sprites.texture_minimapMarkerHero);


        //sprites.level0 = new TiledMap();

        sprites.level0 = manager.get("levels/level0.tmx");
        sprites.level1 = new TiledMap();


        sprites.level1 = manager.get("levels/level1.tmx");


        while (!manager.update()) {
            System.out.println("Loaded: " + (int) (manager.getProgress() * 100) + "%");
        }

        sprites.texture_zombie = manager.get("images/zombie.png", Texture.class);
        sprites.zombie.setTexture(sprites.texture_zombie);
        sprites.axe_enemy = new Sprite(sprites.texture_axe_enemy);
        sprites.throwing_axe.setTexture(sprites.texture_throwing_axe);
        sprites.boss.setTexture(sprites.texture_boss);
        sprites.boss_bar.setTexture(sprites.texture_boss_bar);
        sprites.boss_indicator.setTexture(sprites.texture_boss_indicator);
        sprites.shot.setTexture(sprites.texture_shot);
        sprites.items.setTexture(sprites.texture_items);
        sprites.bar.setTexture(sprites.texture_bar);
        sprites.health.setTexture(sprites.texture_health);
        sprites.npc.setTexture(sprites.texture_npc);
        sprites.grenade.setTexture(sprites.texture_grenade);
        sprites.explosion.setTexture(sprites.texture_explosion);
        sprites.minimapMarkerNpc.setTexture(sprites.texture_minimapMarkerNpc);
        sprites.minimapMarkerHero.setTexture(sprites.texture_minimapMarkerHero);
    }

    public void AddNewShot(Constants.Direction dirLast, Sprite sprite_shot, Sprite sprite_grenade, Constants.ShotType shotType) //adding new shot in list
    {
        Shot shot = new Shot();
        switch (dirLast) {
            case UP:
                shot.dir = Constants.Direction.UP;
                break;
            case DOWN:
                shot.dir = Constants.Direction.DOWN;
                break;
            case UPLEFT:
            case LEFT:
            case DOWNLEFT:
                shot.dir = Constants.Direction.LEFT;
                break;
            case UPRIGHT:
            case RIGHT:
            case DOWNRIGHT:
                shot.dir = Constants.Direction.RIGHT;
                break;
        }

        Texture texture;

        if (shotType == Constants.ShotType.BULLET) {
            //shot.pos = hero.sprite.getPosition();
            shot.distance = 0;
            shot.sprite = sprite_shot;
            shot.type = shotType;
            shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY());
            switch (shot.dir) {
                case UP:
                    shot.sprite.setRegion(2, 5, 3, 7);
                    break;
                case RIGHT:
                    shot.sprite.setRegion(0, 0, 7, 3);
                    shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY() + 10.f);
                    break;
                case DOWN:
                    shot.sprite.setRegion(10, 5, 3, 7);
                    break;
                case LEFT:
                    shot.sprite.setRegion(8, 0, 7, 3);
                    shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY() + 10.f);
                    break;
            }
        } else {
            //shot.pos = hero.sprite.getPosition();
            shot.distance = 0;
            shot.sprite = sprite_grenade;
            shot.type = shotType;
            shot.startTime = time;
            Texture textureR;
            //AddNewShot(hero, sprite_shot, game);
            //shot.sprite.setPosition(hero.sprite.getPosition());
            shot.currentFrame = 0;
            shot.isExploded = false;
            shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY());
        }
        shot.pos = new Vector2(shot.sprite.getX(), shot.sprite.getY());

        //cout << "SHOTEEEEEE " << pos.x << " " << pos.y  << endl;
        shotList.add(shot);
    }

    ;


    void ProcessEvents() {


        hero.UpdateDirection();

        if (hero.IsInventorySwitch()) {
            hero.item = inventoryList.get(hero.slotNo);
        }
        CheckUsingItems(sprites.shot, sprites.grenade);

        //CheckWindowClose
        //if (Keyboard::isKeyPressed(Keyboard::Escape) || event.type == Event::Closed)
        //window.close();
    }


    void CheckEventNpc()
    {
        Vector2 heroCenter = sprites.GetSpriteCenter(hero.sprite);
        boolean needDeleteNpc = false;
        boolean isAnyNpcChanged = false;
        int i = 0;
        for (Npc npc : npcList)
        {
            needDeleteNpc = false;
            if (npc.state == Constants.NpcState.LIVING)
            {
                Vector2 npcCenter = sprites.GetSpriteCenter(npc.sprite);
                if ((Math.abs(npcCenter.x - heroCenter.x) < 35) && (Math.abs(npcCenter.y - heroCenter.y)) < 35)
                {
                    hero.isSoundNpcSurvive = true;
                    npc.state = Constants.NpcState.SURVIVED;
                    hero.savedNeighbors += 1;
                    needDeleteNpc = true;
                    isAnyNpcChanged = true;
                }
            }
            if (npc.health <= 0)
            {
                hero.isSoundNpcDeath = true;
                npc.state = Constants.NpcState.KILLED;
                isAnyNpcChanged = true;
            }
            if (npc.state == Constants.NpcState.KILLED && npc.currentFrame > 8)
                needDeleteNpc = true;

            if (needDeleteNpc) //deleting Npc from List
            {
                npcList.remove(npc);
                //DeleteNpcDot(miniMap, i);
            }
            if (isAnyNpcChanged)
            {
                npc.SortNpcList(npcList);
            }
        }
    };


    Vector2 GetInterfacePosition()
    {
        return new Vector2(viewPort.getScreenX() + viewPort.getScreenWidth() / 2 - 350,
                           viewPort.getScreenY() + viewPort.getScreenHeight() / 2  - 265);
    };


    void UpdateView(Sprite hero)  //UpdateCameraPosition
    {

        Vector2 temp = new Vector2(hero.getX(), hero.getY());

        if (temp.x < (float)(Constants.WINDOW_SIZE.x) / 2.f)
        temp.x = (float)(Constants.WINDOW_SIZE.x) / 2.f;
        else if (Constants.TILEMAP_SIZE.x * Constants.STEP_TILE - (float)(Constants.WINDOW_SIZE.x) / 2.f < temp.x)
        temp.x = (Constants.TILEMAP_SIZE.x * Constants.STEP_TILE - (float)(Constants.WINDOW_SIZE.x) / 2.f);

        if (temp.y < (float)(Constants.WINDOW_SIZE.y) / 2.f)
        temp.y = (float)(Constants.WINDOW_SIZE.y) / 2.f;
        else if (Constants.TILEMAP_SIZE.y * Constants.STEP_TILE - (float)(Constants.WINDOW_SIZE.y) / 2.f < temp.y)
        temp.y = (Constants.TILEMAP_SIZE.y * Constants.STEP_TILE - (float)(Constants.WINDOW_SIZE.y) / 2.f);

        viewPort.setScreenX((int)temp.x);
        viewPort.setScreenY((int) temp.y);
        //viewPort.update();
    };



    boolean IsShotCollision(Shot shot)  //check if need delete shot from list
    {
        //Vector2f shotCenter = GetSpriteCenter(shot.sprite);
        if (shot.distance > Constants.SHOT_MAX_DISTANCE)
        {
            return true;
        }
        else
        {
            Rectangle spriteRect = sprites.GetSpriteRect(shot.sprite);
            for(MapObject solid : solidObjects) {
                Rectangle rect = ((RectangleMapObject) solid).getRectangle();
                if (rect.overlaps(spriteRect)){
                    return true;
                }
            }
		/*
		for (size_t i = 0; i < objects.size(); ++i)
		{
			if (spriteRect.intersects(objects[i].rect))
			{
				return true;
			}
		}
		*/
        }

        for (Enemy enemy : zombieList)
        {
            if (enemy.sprite.getBoundingRectangle().overlaps(shot.sprite.getBoundingRectangle()))
            {
                enemy.health -= Constants.DMG_ITEM[hero.item.name.ordinal()];
                hero.isSoundEnemyExplosion = true;
                return true;
            }
        }
        return false;
    }

    void UpdateShots(Sprite sprite_explosion) //shots position update and delete if need
    {
        for (Shot shot : shotList)
        {
            //TODO: PART FUNCTION
            if (shot.type == Constants.ShotType.BULLET)
            {
                shot.pos = ComputeSpriteNewPosition(shot.sprite, shot.dir, Constants.STEP_SHOT);
                shot.distance += (int)(Constants.STEP_SHOT);


                boolean isDeleted = false;
                if (level == 1)
                {
                    if (boss.sprite.getBoundingRectangle().overlaps(shot.sprite.getBoundingRectangle()))
                    {
                        boss.health -= Constants.DMG_ITEM[hero.item.name.ordinal()];
                        shotList.remove(shot);
                        isDeleted = true;
                    }
                }
                if (isDeleted == false &&(IsShotCollision(shot)) ) //shot delete
                {
                    shotList.remove(shot);
                }
                else if (isDeleted == true)
                {
                    break;
                }

            }
            else if (shot.type == Constants.ShotType.USED_GRENADE)
            {
                switch (shot.dir)
                {
                    case UP:
                        shot.pos.y -= Constants.STEP_GRENADE;
                        break;
                    case DOWN:
                        shot.pos.y += Constants.STEP_GRENADE;
                        break;
                    case RIGHT:
                        shot.pos.x += Constants.STEP_GRENADE;
                        if ((time - shot.startTime) < (Constants.GRENADE_MAX_TIME / 2.f))
                    {
                        shot.pos.y -= 2;
                    }
                    else
                    {
                        shot.pos.y += 2;
                    }
                    break;
                    case LEFT:
                        shot.pos.x -= Constants.STEP_GRENADE;
                        if (time - shot.startTime < Constants.GRENADE_MAX_TIME / 2.f)
                        {
                            shot.pos.y -= 2;
                        }
                        else
                        {
                            shot.pos.y += 2;
                        }
                        break;
                }

                shot.sprite.setPosition(shot.pos.x, shot.pos.y);

                if (time - shot.startTime > Constants.GRENADE_MAX_TIME)
                {
                    shot.isExploded = true;

                    Explosion explosion = new Explosion();
                    explosion.sprite = sprite_explosion;
                    explosion.pos = new Vector2(shot.sprite.getX() - 125,shot.sprite.getY() - 70);
                    explosion.sprite.setPosition(explosion.pos.x, explosion.pos.y);
                    explosion.currentFrame = 0;
                    explosionList.add(explosion);

                    shotList.remove(shot);
                }
            }
        }
    }


    public boolean IsIntersectWithHero(Sprite enemy, Sprite hero)
    {
        if (enemy.getBoundingRectangle().overlaps(hero.getBoundingRectangle()))
            return true;
        return false;
    }


    void UpdateThrowings()
    {
        for (Throwing throwing: throwingList)
        {
            if (throwing.name != "milk" || (throwing.name == "milk" && throwing.currentFrame < 1))
            {
                throwing.UpdateThrowingPosition();
            }
            throwing.UpdateThrowingFrame();

            if (throwing.name == "milk")
            {
                if (throwing.IsMilkOnGround())
                {

                    if (throwing.currentFrame < 1)
                    {
                        throwing.currentFrame = 1;
                    }

                }
            }

            if (throwing.startTime + throwing.maxTime < time)
            {
                throwingList.remove(throwing);
            }
            else if (IsIntersectWithHero(throwing.sprite, hero.sprite))
            {
                //deal damage (action)
                if(throwing.name != "milk")
                    hero.health -= (int)(Constants.THROWING_AXE_DAMAGE * hero.damageResistance);
                else hero.health -= (int)(Constants.BOSS_MILK_DAMAGE * hero.damageResistance);
                throwingList.remove(throwing);
                hero.isSoundTakeDamage = true;
            }
            else if (IsCollisionWithMap(sprites.GetSpriteRect(throwing.sprite),throwing.dir,solidObjects))
            {
                throwingList.remove(throwing);
            }
        }
    }

    public void CheckEnemyExplosion()
    {
        for (Explosion explosion :explosionList)
        {
            if (explosion.currentFrame > 12)
            {
                for (Enemy zombie : zombieList)
                {
                    if (Math.abs(zombie.pos.x - (explosion.pos.x + 120)) < 120 && (Math.abs(zombie.pos.y - (explosion.pos.y + 70)) < 120))
                    {
                        zombie.health = 0;
                    }
                }
                explosionList.remove(explosion);

            }
        }
    }
    public void ComputeNpcFrame()
    {
        for (Npc npc : npcList)
        {
            if (npc.state == Constants.NpcState.LIVING)
            {
                switch (npc.type)
                {
                    case PHOTOGRAPHS:
                        if (npc.currentFrame > 12)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(2 + 41 * (int) npc.currentFrame, 350, 41, 50);
                        break;
                    case BABY:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 28 * (int) npc.currentFrame, 213, 23, 32);
                        break;
                    case TEACHER:
                        if (npc.currentFrame > 2)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(3 + 37 * (int) npc.currentFrame, 2, 37, 41);
                        break;
                    case GIRL:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(0 + 50 * (int) npc.currentFrame, 92, 50, 67);
                        npc.currentFrame += 0.08f;
                        break;
                    case DOG:
                        if (npc.currentFrame > 4)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(2 + 34 * (int) npc.currentFrame, 49, 31, 34);
                        break;
                    case SOLDIER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 36 * (int) npc.currentFrame, 252, 35, 46);
                        break;
                    case SEARCHER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 31 * (int) npc.currentFrame, 306, 29, 39);
                        break;
                    case COOK:
                        if (npc.currentFrame > 9)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(3 + 54 * (int) npc.currentFrame, 454, 52, 66);
                        break;
                }
            }
            else if (npc.state == Constants.NpcState.KILLED)
            {
                npc.sprite.setRegion(4 + 45 * (int) npc.currentFrame, 593, 53, 45);
                npc.currentFrame += 0.06f;
            }
            npc.currentFrame += 0.02f;
        }
    };


    void UpdateBossEvent(Boss boss, Hero hero, float time)
    {
        if (boss.eventType == Constants.BossEvent.MOVE_OUT)
        {
            if (boss.IsBossNearMapCenter(boss.sprite))//(boss.eventstartTime + 7.f < time)
            {
                boss.eventType = Constants.BossEvent.STOMP_FOR_ZOMBIES;
                if (boss.isCommonZombie == true)
                {
                    boss.isCommonZombie = false;
                }
                else boss.isCommonZombie = true;
                boss.spawnedEnemies = 0;
                boss.eventstartTime = time;
            }
        }
        else if (boss.eventType == Constants.BossEvent.MOVE_TO)
        {
            //cout << "A";

            if (boss.shootStartTime == -1)
            {
                if (boss.IsBossAbleToShoot(hero.sprite, boss.sprite))
                {
                    boss.eventType = Constants.BossEvent.SHOOT;
                    boss.eventstartTime = time;
                    boss.shootStartTime = time;
                    return;
                }
            }
            else if (boss.shootStartTime + Constants.BOSS_FOLLOW_FOR_SHOOT_TIME > time)
            {
                if (boss.IsBossAbleToShoot(hero.sprite, boss.sprite))
                {
                    boss.eventType = Constants.BossEvent.SHOOT;
                }
                return;
            }
            else if (boss.eventstartTime + 5.f < time)
            {
                //cout << "B " << time << endl;
                boss.eventType = Constants.BossEvent.MOVE_OUT;
                boss.wasCharged = false;
                boss.eventstartTime = time;
                return;
            }
            if (boss.wasCharged == false && boss.eventstartTime + 2.f < time)
            {
                boss.eventType = Constants.BossEvent.CHARGE;
                boss.eventstartTime = time;

                boss.wasCharged = true;
            }
        }
        else if (boss.eventType == Constants.BossEvent.STOMP_FOR_ZOMBIES)
        {
            if (boss.spawnedEnemies == 4) //(boss.eventstartTime + 2.5f < time)
            {
                boss.shootStartTime = -1;
                boss.eventType = Constants.BossEvent.MOVE_TO;
                boss.eventstartTime = time;
            }
        }
        else if (boss.eventType == Constants.BossEvent.SHOOT)
        {
            if (boss.eventstartTime + Constants.BOSS_FOLLOW_FOR_SHOOT_TIME < time)
            {
                boss.eventType = Constants.BossEvent.MOVE_OUT;
                boss.eventstartTime = time;
                return;
            }
            if (boss.IsBossAbleToShoot(hero.sprite, boss.sprite) == false)
            {
                boss.eventType = Constants.BossEvent.MOVE_TO;
            }
        }
        else if (boss.eventType == Constants.BossEvent.CHARGE)
        {
            if (boss.IsReachedHero(boss.sprite, hero.sprite))
            {
                hero.lastSmashTime = time;
                hero.isSmashed = true;
                hero.state = Constants.HeroState.SMASHED;
                boss.lastShootTime = time + 0.3f;
                hero.isSoundTakeDamage = true;
                hero.health -= (int)(Constants.BOSS_SMASH_DAMAGE);

                boss.eventType = Constants.BossEvent.SHOOT;
                boss.shootStartTime = time;
                boss.eventstartTime = time;
                return;
            }
            else if (boss.eventstartTime + 2.f < time)
            {
                //boss.wasCharged = false;
                boss.eventType = Constants.BossEvent.MOVE_TO;
                boss.eventstartTime = time;
            }
        }
    }

    void UpdateBossPosition()
    {
        Vector2 oldPos = new Vector2(boss.sprite.getX(), boss.sprite.getY());
        if (boss.eventType == Constants.BossEvent.CHARGE)
        {
            boss.speed = Constants.STEP_BOSS_CHARGE;
        }
        else if (boss.eventType == Constants.BossEvent.MOVE_OUT)
        {
            boss.speed = Constants.STEP_BOSS;
        }
        else if (boss.eventType == Constants.BossEvent.MOVE_TO)
        {
            boss.speed = Constants.STEP_BOSS_FOLLOW;
        }

        Vector2 pos = ComputeSpriteNewPosition(boss.sprite, boss.dir, boss.speed);

        boss.sprite.setPosition(pos.x, pos.y);

        Rectangle spriteRect = boss.GetBossCollisionRect(boss.sprite);

        if (IsCollisionWithMap(spriteRect, boss.dir, solidObjects))
        {
            boss.sprite.setPosition(oldPos.x, oldPos.y);
            if (pos.x != oldPos.x && pos.y != oldPos.y)
            {
                boss.sprite.setPosition(oldPos.x, pos.y);
                if (IsCollisionWithMap(spriteRect, boss.dir, solidObjects))
                {
                    boss.sprite.setPosition(pos.x, oldPos.y);
                    if (IsCollisionWithMap(spriteRect, boss.dir, solidObjects))
                    {
                        boss.sprite.setPosition(oldPos.x, oldPos.y);
                    }
                }
            }
        }
    }

    public Throwing CreateThrowing(Sprite object, Sprite target, String type,Sprite throwingSprite, float time)
    {
        Throwing throwing = new Throwing();

        Vector2 distance = Sprites.ComputeDistanceBetweenSprites(object, target);

        if (type == "axe")
        {
            throwing.maxTime = Constants.ENEMY_AXE_THROWING_MAX_TIME;
            throwing.cooldown = Constants.ENEMY_AXE_ACTION_COOLDOWN;
        }
        else if (type == "grenade")
        {

        }
        else if (type == "soda")
        {

        }
        else if (type == "milk")
        {
            throwing.maxTime = 3.f;
            throwing.cooldown = Constants.BOSS_SHOT_COOLDOWN;
        }

        throwing.name = type;

        float dist = (float)Math.sqrt(Math.pow(distance.x, 2) + Math.pow(distance.y, 2));

        if (distance.x >= distance.y)
        {
            throwing.stepPerLoop = new Vector2( 1, distance.y / distance.x);
            //coeff = sqrt(pow(dist, 2) - pow(distance.y, 2));
        }
        else
        {
            throwing.stepPerLoop = new Vector2(distance.x / distance.y, 1);
            //coeff = sqrt(pow(dist, 2) - pow(distance.x, 2));
        }

        throwing.stepPerLoop = new Vector2( throwing.stepPerLoop.x * Constants.ENEMY_AXE_THROWING_STEP_PER_LOOP, throwing.stepPerLoop.y * Constants.ENEMY_AXE_THROWING_STEP_PER_LOOP );

        if (object.getX() > target.getX())
        {
            throwing.stepPerLoop.x = -throwing.stepPerLoop.x;
        }
        if (object.getY() > target.getY())
        {
            throwing.stepPerLoop.y = -throwing.stepPerLoop.y;
        }

        //throwing.dir = dir;
        throwing.sprite = throwingSprite;

        throwing.startPos = new Vector2(object.getX(), object.getY());

        throwing.sprite.setPosition(throwing.startPos.x, throwing.startPos.y);
        throwing.startTime = time;
        throwing.currentFrame = 0;
        return throwing;
    }


    void UpdateBoss()
    {
        if (level == 1)
        {
            Rectangle collideRect = boss.GetBossCollisionRect(boss.sprite);

            Constants.BossEvent bossEvent = boss.eventType;
            UpdateBossEvent(boss, hero, time);
            if (bossEvent == Constants.BossEvent.STOMP_FOR_ZOMBIES)
            {
                if (boss.eventstartTime + 1 + boss.spawnedEnemies * Constants.BOSS_SPAWN_ENEMY_INTERVAL < time)
                {
                    Constants.EnemyType type;
                    if (boss.isCommonZombie)
                    {
                        type = Constants.EnemyType.COMMON;
                    }
                    else type = Constants.EnemyType.AXE;

                    SpawnEnemy((int)Constants.LEVEL1_ZOMBIE_SPAWN_SPOTS[boss.spawnedEnemies].x, (int)Constants.LEVEL1_ZOMBIE_SPAWN_SPOTS[boss.spawnedEnemies].y, type);
                    boss.spawnedEnemies += 1;
                }

            }
            boss.ComputeBossDirection(boss, collideRect, hero.sprite);
            UpdateBossPosition();

            //UpdateSpritePosition(boss.sprite, boss.dir, STEP_BOSS, solidObjects);
            boss.UpdateBossFrame();

            if (hero.state == Constants.HeroState.BEAST)
            {
                if (boss.IsFootRectIntersectWithHero(hero.sprite, boss.GetBossCollisionRect(boss.sprite)))
                {
                    if (hero.isBeastAttack && ((time - hero.lastAttackTime) > Constants.HERO_BEAST_ATTACK_TIME))
                    {
                        boss.health -= Constants.HERO_BEAST_DAMAGE;
                        hero.lastAttackTime = time;
                    }
                }
            }
            if (boss.eventType == Constants.BossEvent.SHOOT)
            {
                if (hero.isSmashed == true)
                {
                    //boss.lastShootTime = time;
                }
                else if (boss.IsReadyToShoot(boss, time))
            {
                boss.lastShootTime = time;
                Sprite milk = boss.sprite;
                milk.setRegion(392, 404, 42, 19);
                Throwing thr = CreateThrowing(boss.sprite, hero.sprite, "milk", milk, time);

                if (boss.lastSide == Constants.Direction.LEFT)
                {
                    thr.sprite.setPosition(boss.sprite.getX() - 5.f, boss.sprite.getY() + 75.f);
                }
                else thr.sprite.setPosition(boss.sprite.getX() + 78.f, boss.sprite.getY() + 71.f);

                throwingList.add(thr);
                boss.isAttack = false;
            }
            }

            if (hero.health <= 0)
            {
                state = Constants.GameState.END_GAME;
            }
            else if (boss.health <= 0)
        {
            state = Constants.GameState.FINISH;
        }
        }
    }


    public void ResetEnemySpawnTime(Vector<Enemy> zombieList, float  time)
    {
        for (Enemy enemy : zombieList)
        {
            enemy.spawnTime = time;
        }
    }


    void BeginEvent()  //process startGame events
    {
        Vector2 posView = new Vector2(viewPort.getScreenX(), viewPort.getScreenY());
/*
        game.text.setString("It's time to rescue neighbors!");
        game.text.setPosition(posView.x - 140, posView.y - 100);
        game.window->draw(game.text);

        game.text.setString("Press Enter to play");
        game.text.setPosition(posView.x - 100, posView.y - 40);
        */
        //game.window->draw(game.text);

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            //game.time = 0;
            ResetEnemySpawnTime(zombieList, time);
            //game.audio.menu.stop();
            state = Constants.GameState.PLAY;
            if (level == 0)
            {
                level = 1;

                //game.audio.level0.play();
            }
        }
    };
/*
    void EndGameEvent(Game & game)
    {
        Vector2f posView = game.view.getCenter();

        game.text.setString("GAME OVER");
        game.text.setPosition(posView.x - 40, posView.y - 100);
        game.window->draw(game.text);
        CheckWindowClose(game);

        game.audio.level0.stop();
        game.audio.beastAttackSound.stop();
        game.audio.lootSound.stop();
        game.audio.npcSurviveSound.stop();
        game.audio.npcDeathSound.stop();
        game.audio.shotSound.stop();
        game.audio.takenDamageSound.stop();
        game.audio.enemyExplosionSound.stop();
        game.audio.menu.play();
        if (Keyboard::isKeyPressed(Keyboard::Return))
        {
            delete((&game)->hero);
            //delete((&game)->window);
            DeleteExplosionList((&game)->explosionList);
            DeleteLoot((&game)->lootList);
            DeleteNpcList((&game)->npcList);
            DeleteShots((&game)->shotList);
            DeleteEnemyList((&game)->zombieList);
            DeleteInventory((&game)->inventoryList);
            DeleteMinimap(game.miniMap);
            DeleteThrowings(game.throwingList);
            //delete((&game));


            game.hero = new Hero();
            //InitiazlizeSprites(game.sprites);
            InitializeInventory(game.inventoryList, game.sprites);
            InitializeHero(*game.hero);
            if (game.level == 1)
            {
                game.hero->maxNeighbors = 0;
            }
            else game.hero->maxNeighbors = MAX_NUMBER_OF_NEIGHBORS;

            game.hero->item = game.inventoryList[0];
            game.hero->item.sprite.setTextureRect(IntRect(32, 0, 32, 32));
            //game.window = new RenderWindow(VideoMode(W_WIDTH, W_HEIGHT), "Shoot and Run");
            //InitializeView(game.view);
            //InitializeText(game.font, game.text);
            //game.lvl.LoadFromFile("level0.tmx");
            //game.solidObjects = game.lvl.GetObjects("solid");

            SpawnEnemy(game.zombieList, game.time, 100, 100, game.sprites, COMMON);
            //InitializeLoot(game.lootList, game.allObjects, game.sprites.items);
            if(game.level == 0)
                InitializeNpc(game.npcList, game.sprites.npc);
            InitializeMinimap(game.miniMap, game.npcList, game.sprites);
            game.time = 0;
            game.state = PLAY;

            game.audio.menu.stop();
            if (game.level == 1)
            {
                game.audio.level1.play();
            }
            else game.audio.level0.play();
            //game.hero->sprite.setPosition(game.view.getCenter());
        }
    };

    void LevelFinishEvent(Game & game)
    {
        Vector2f posView = game.view.getCenter();

        game.text.setString("LEVEL HAS FINISHED!");
        game.text.setPosition(posView.x - 100, posView.y - 100);
        game.window->draw(game.text);
        CheckWindowClose(game);
        game.audio.level0.stop();
        if (Keyboard::isKeyPressed(Keyboard::Return))
        {
            delete((&game)->hero);
            //delete((&game)->window);
            DeleteExplosionList((&game)->explosionList);
            DeleteLoot((&game)->lootList);
            DeleteNpcList((&game)->npcList);
            DeleteShots((&game)->shotList);
            DeleteEnemyList((&game)->zombieList);
            DeleteInventory((&game)->inventoryList);
            DeleteMinimap(game.miniMap);
            DeleteThrowings(game.throwingList);

            game.hero = new Hero();
            InitializeHero(*game.hero);
            InitializeInventory(game.inventoryList, game.sprites);
            game.hero->item = game.inventoryList[0];
            game.time = 0;
            game.hero->sprite.setPosition(2 * 64 + 10, 8 * 64);
            game.hero->maxNeighbors = 0;
            game.level = 1;

            game.lvl = game.sprites.level1;
            game.allObjects = game.lvl.GetAllObjects();
            game.solidObjects = game.lvl.GetObjects("solid");

            //SpawnEnemy(game.zombieList, game.time, 100, 100, game.sprites, COMMON);
            //InitializeLoot(game.lootList, game.allObjects, game.sprites.items);
            //InitializeNpc(game.npcList, game.sprites.npc);
            InitializeMinimap(game.miniMap, game.npcList, game.sprites);

            InitializeBoss(game.boss, game.sprites, game.time);

            game.audio.level1.play();

            game.state = PLAY;
        }
    };

    void GameFinishEvent(Game & game)
    {
        Vector2f posView = game.view.getCenter();

        game.text.setString("You won. Wanna try again?(Enter) \nQuit(Esc)");
        game.text.setPosition(posView.x - 100, posView.y - 100);
        game.window->draw(game.text);
        CheckWindowClose(game);
        if (Keyboard::isKeyPressed(Keyboard::Return))
        {
            delete((&game)->hero);
            DeleteExplosionList((&game)->explosionList);
            DeleteLoot((&game)->lootList);
            DeleteNpcList((&game)->npcList);
            DeleteShots((&game)->shotList);
            DeleteEnemyList((&game)->zombieList);
            DeleteInventory((&game)->inventoryList);
            DeleteMinimap(game.miniMap);
            DeleteThrowings(game.throwingList);


            game.hero = new Hero();
            InitiazlizeSprites(game.sprites);
            InitializeInventory(game.inventoryList, game.sprites);
            InitializeHero(*game.hero);
            game.hero->item = game.inventoryList[0];
            game.hero->item.sprite.setTextureRect(IntRect(32, 0, 32, 32));
            InitializeView(game.view);
            InitializeText(game.font, game.text);
            game.lvl = game.sprites.level0;
            game.level = 0;
            game.hero->maxNeighbors = MAX_NUMBER_OF_NEIGHBORS;
            game.allObjects = game.lvl.GetAllObjects();
            game.solidObjects = game.lvl.GetObjects("solid");

            SpawnEnemy(game.zombieList, game.time, 100, 100, game.sprites, AXE);
            InitializeLoot(game.lootList, game.allObjects, game.sprites.items);
            InitializeNpc(game.npcList, game.sprites.npc);
            game.time = 0;
            game.state = START_GAME;
            //game.hero->sprite.setPosition(game.view.getCenter());
            InitializeMinimap(game.miniMap, game.npcList, game.sprites);
            InitializeSoundResouces(game.audio);
        }
    }*/


    void UpdateGame(SpriteBatch batch) {
        switch (state) {
            case START_GAME:
                BeginEvent();
                break;
            case END_GAME:
                //EndGameEvent(*game);
                break;
            case LEVEL_FINISH:
                //LevelFinishEvent(*game);
                break;
            case FINISH:
                //GameFinishEvent(*game);
                break;
            case PLAY:
                //TODO: spawn zombie at definite time (and change SpawnZombie func (spawn only near hero))


                //camera.update();
                //renderer.setView(camera);
                CheckSpawnEnemyAndLoot();

                ProcessEvents();

                //UpdateHero();
                UpdateInventory();
                CheckEventNpc();
                UpdateView(hero.sprite);
                UpdateShots(sprites.explosion);

                UpdateEnemies();
                UpdateThrowings();


                boss.CheckBossExplosion(explosionList, level);
                CheckEnemyExplosion();

                CheckLoot(sprites.items);
                UpdateInventory();

                ComputeNpcFrame();

                //UpdateMinimap(hero.sprite);


                UpdateBoss();
        }
    }

    void CheckHeroBeastDamage(Enemy enemy)  //killing zombie by melee attack
    {
        if (hero.isBeastAttack && ((time - hero.lastAttackTime) > Constants.HERO_BEAST_ATTACK_TIME))
        {
            enemy.health -= Constants.HERO_BEAST_DAMAGE;
            hero.lastAttackTime = time;
        }
    }

    void UpdateEnemies()
    {
        Vector2 heroPos = new Vector2(hero.sprite.getX(), hero.sprite.getY());
        int index = 0;
        for (Enemy zombie : zombieList)
        {
            //float di = sqrt(pow(dx,2) + pow(dy,2));  //distance
            if (zombie.state == Constants.EnemyState.ACTIVE)
            {
                zombie.CheckEnemyFollow(zombie, hero);
                if (zombie.follow)
                {
                    zombie.ComputeEnemyDirection(zombie, heroPos);  //direction relatively to hero
                    zombie.CheckNpcDeath(npcList, zombie);
                }
                else
                {
                    zombie.CheckEnemyDir(zombie, time);
                }

                //CheckCollisionWithMap(zombie.sprite,zombie.dirLast,STEP_ZOMBIE_ACTIVE, objects);
                zombie.UpdateEnemyAttack(hero, zombie, time);

                if (hero.state == Constants.HeroState.BEAST)
                {
                    if (zombie.IsEnemyNearHero(hero, zombie))
                    {
                        CheckHeroBeastDamage(zombie);
                    }
                }
                if (heroPos.x != zombie.pos.x || heroPos.y != zombie.pos.y)
                {
                    if (zombie.follow)
                    {
                        //ZombieUpdatePosition(zombie);
                        //Vector2f newPos;
                        Vector2 oldPosition = new Vector2(zombie.sprite.getX(), zombie.sprite.getY());
                        if (zombie.follow == true)
                        {
                            UpdateSpritePosition(zombie.sprite, zombie.dir, Constants.STEP_ZOMBIE_ACTIVE);
                            //cout << "1     1" << endl;
                        }
                        else
                        {
                            UpdateSpritePosition(zombie.sprite, zombie.dir, Constants.STEP_ZOMBIE);
                            //cout << "2      2" << endl;
                        }
                        if (zombie.IsIntersectWithOtherEnemy(zombieList, index))
                        {
                            zombie.sprite.setPosition(oldPosition.x, oldPosition.y);
                        }
                        if (zombie.type == Constants.EnemyType.AXE)
                        {
                            if (zombie.IsIntersectWithHero(zombie.sprite, hero.sprite))
                            {
                                //zombie.pos = oldPosition;
                                zombie.sprite.setPosition(oldPosition.x, oldPosition.y);
                                if (time > zombie.attack_time + zombie.attackCooldown)
                                {
                                    zombie.currentFrame = 0;
                                    zombie.isAttack = true;
                                    zombie.attack_time = time;
                                    hero.health -= (int)(Constants.ENEMY_AXE_ATTACK_DAMAGE * hero.damageResistance);
                                }
                            }
                            if (zombie.last_action_time + Constants.ENEMY_AXE_ACTION_COOLDOWN < time)
                            {
                                Vector2 spriteDistance = sprites.ComputeDistanceBetweenSprites(zombie.sprite, hero.sprite);
                                zombie.last_action_time = time;
                                Throwing throwing = new Throwing();

                                throwingList.add(CreateThrowing(zombie.sprite, hero.sprite, "axe", sprites.throwing_axe, time));

                            }
                        }
                        zombie.pos = new Vector2(zombie.sprite.getX(), zombie.sprite.getY());
                    }
                }
            }

            zombie.UpdateEnemyFrame(time);
            //UpdateZombiePosition(i);  TODO: make it for all zombies, not jsut for following ones
            //zombie.isAttack = false;
            if (zombie.dir != zombie.dirLast)
            {
                //zombie.currentFrame = 0;
                zombie.dirLast = zombie.dir;
            }
            if (zombie.state == Constants.EnemyState.EXPLODED)  //deleting
            {
                zombieList.remove(zombie);
            }
            else
            {
                index++;
            }
        }
    }
    
    Vector2 ComputeSpriteNewPosition(Sprite sprite, Constants.Direction dir, final float speed) {
        Vector2 pos = new Vector2(sprite.getX(), sprite.getY());
        switch (dir) {
            case UP:
                pos.y -= speed;
                break;
            case UPRIGHT:
                pos.x += (Constants.DIAGONAL_STEP * speed);
                pos.y -= (Constants.DIAGONAL_STEP * speed);
                break;
            case RIGHT:
                pos.x += speed;
                break;
            case DOWNRIGHT:
                pos.x += (Constants.DIAGONAL_STEP * speed);
                pos.y += (Constants.DIAGONAL_STEP * speed);
                break;
            case DOWN:
                pos.y += speed;
                break;
            case DOWNLEFT:
                pos.x -= (Constants.DIAGONAL_STEP * speed);
                pos.y += (Constants.DIAGONAL_STEP * speed);
                break;
            case LEFT:
                pos.x -= speed;
                break;
            case UPLEFT:
                pos.x -= (Constants.DIAGONAL_STEP * speed);
                pos.y -= (Constants.DIAGONAL_STEP * speed);
                break;
            case NONE:
                break;
            default:
                break;
        }
        return pos;
    }

    boolean IsCollisionWithMap(Rectangle spriteRect, Constants.Direction dir, Vector<MapObject> objects) {
        for (int i = 0; i < objects.size(); ++i) {
            Rectangle rect = ((RectangleMapObject) objects.get(i)).getRectangle();
            if (spriteRect.overlaps(rect)) {
                return true;
            }
        }
        return false;
    }

    ;


    void UpdateSpritePosition(Sprite sprite, Constants.Direction dir, final float speed) {
        Vector2 oldPos = new Vector2(sprite.getX(), sprite.getY());
        Vector2 pos = ComputeSpriteNewPosition(sprite, dir, speed);

        sprite.setPosition(pos.x, pos.y);

        Rectangle spriteRect = sprites.GetSpriteRect(sprite);

        if (IsCollisionWithMap(spriteRect, dir, solidObjects)) {
            sprite.setPosition(oldPos.x, oldPos.y);
            if (pos.x != oldPos.x && pos.y != oldPos.y) {
                sprite.setPosition(oldPos.x, pos.y);
                if (IsCollisionWithMap(spriteRect, dir, solidObjects)) {
                    sprite.setPosition(pos.x, oldPos.y);
                    if (IsCollisionWithMap(spriteRect, dir, solidObjects)) {
                        sprite.setPosition(oldPos.x, oldPos.y);
                    }
                }
            }
        }
    }

    void UpdateHero() //position + collision + sprite
    {
        Vector2 pos = new Vector2(hero.sprite.getX(), hero.sprite.getY());
        Vector2 oldPos = pos;
        //TODO: CHECK BEAST MOVE SPEED (after changing func (07.01.16))
        if (hero.state == Constants.HeroState.NORMAL || hero.state == Constants.HeroState.BEAST) {
            UpdateSpritePosition(hero.sprite, hero.dir, Constants.STEP_HERO);
            hero.pos = new Vector2(hero.sprite.getX(), hero.sprite.getY());
        }
        if (hero.state == Constants.HeroState.BEAST) {
            if (time - hero.beastTimer > Constants.BEAST_MAX_TIME) {
                hero.state = Constants.HeroState.NORMAL;
                hero.currentFrame = 0;
                hero.beastTimer = 0;
                hero.damageResistance = 1;
            }
        }
        hero.UpdateHeroFrame(time);
        if ((inventoryList.get(hero.slotNo).quantity > 0 && inventoryList.get(hero.slotNo).current == 0 && (inventoryList.get(hero.slotNo).name == Constants.NameItem.PISTOL || inventoryList.get(hero.slotNo).name == Constants.NameItem.RIFLE))) {
            if (hero.isReloading == false) {
                hero.lastReloadTime = time;
                hero.isReloading = true;
            }
        }
        if (hero.health < 0) {
            hero.health = 0;
        }
    }

    void CheckSpawnEnemyAndLoot() {
        if ((level == 0 && lootList.size() < 5) || (level == 1 && (lootList.size() < Constants.LEVEL_BOSS_MAX_LOOT_QUANTITY))) {
            int itemNo = (int) Math.random() * 5;
            Constants.NameItem itm = Constants.NameItem.DRINK;
            switch (itemNo) {
                case 0:
                    itm = Constants.NameItem.DRINK;
                    break;
                case 1:
                    itm = Constants.NameItem.PISTOL;
                    break;
                case 2:
                    itm = Constants.NameItem.RIFLE;
                    break;
                case 3:
                    itm = Constants.NameItem.MIXTURE;
                    break;
                case 4:
                    itm = Constants.NameItem.GRENADE;
                    break;
            }
            if (level == 0) {
                GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 1, itm.ordinal());
            } else
                GenerateLoot(new Vector2(Constants.LEVEL_BOSS_SIZE.x - 1, Constants.LEVEL_BOSS_SIZE.y - 1), 1, itm.ordinal());
        }
        if (level == 0 && zombieList.size() < 20) {
            SpawnEnemyRandomly(1);
        }
    }

    void UpdateInventory() {
        //update items
        if (inventoryList.get(hero.slotNo).current == 0 && time - hero.lastReloadTime > Constants.WEAPON_RELOAD_TIME) {
            hero.lastReloadTime = time;
            hero.isReloading = false;
            if (inventoryList.get(hero.slotNo).quantity >= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()]) {
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).quantity -= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()];
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current = Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()];
            } else {
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current = inventoryList.get(hero.slotNo).quantity;
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).quantity = 0;
            }
            if (inventoryList.get(hero.slotNo).quantity <= 0) {
                inventoryList.get(hero.slotNo).quantity = 0;
            }
        }
    }

    ;

    void CheckUsingItems(Sprite sprite_shot, Sprite sprite_grenade) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (hero.state == Constants.HeroState.BEAST) {
                if (time - hero.lastAttackTime > Constants.HERO_BEAST_ATTACK_TIME) {
                    //lastAttackTime = time;  (reminder)
                    hero.isSoundBeastAtttack = true;
                    hero.isBeastAttack = true;
                    hero.currentFrame = 0;
                }
            } else if (hero.state == Constants.HeroState.NORMAL && inventoryList.get(hero.slotNo).current > 0) {
                System.out.println("ATTACK");
                if (time > hero.shotLastTime + Constants.ITEM_REUSE_COOLDOWN[inventoryList.get(hero.slotNo).name.ordinal()]) {
                    hero.shotLastTime = time;
                    inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current -= 1;
                    if (inventoryList.get(hero.slotNo).name == Constants.NameItem.PISTOL || inventoryList.get(hero.slotNo).name == Constants.NameItem.RIFLE) {
                        if (Gdx.input.isKeyPressed(Input.Keys.A))
                            hero.dirLast = Constants.Direction.LEFT;
                        if (Gdx.input.isKeyPressed(Input.Keys.S))
                            hero.dirLast = Constants.Direction.DOWN;
                        if (Gdx.input.isKeyPressed(Input.Keys.D))
                            hero.dirLast = Constants.Direction.RIGHT;
                        if (Gdx.input.isKeyPressed(Input.Keys.W))
                            hero.dirLast = Constants.Direction.UP;
                        hero.isSoundShoot = true;

                        AddNewShot(hero.dirLast, sprite_shot, sprite_grenade, Constants.ShotType.BULLET);
                    } else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.DRINK) {
                        hero.health += Constants.HP_PER_DRINK;
                        if (hero.health > 100) {
                            hero.health = 100;
                        }
                    } else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.MIXTURE) {
                        hero.state = Constants.HeroState.TRANSFORMING;
                        hero.dir = Constants.Direction.NONE;
                        hero.dirLast = Constants.Direction.DOWN;
                    } else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.GRENADE) {
                        AddNewShot(hero.dirLast, sprite_shot, sprite_grenade, Constants.ShotType.USED_GRENADE);
                    }
                }
            }
        }
    }

    ;

    //TODO: comm
    /*

    void DrawText(SpriteBatch batch, View view, Text text)
    {
        Vector2 posView = GetInterfacePosition(view);  //zametka

        text.setPosition(posView.x + 40, posView.y + 40);

        //currentItem << (iCurrent.item);
        //std::ostringstream toStringCurrent;

        //std::ostringstream toStringQuantity;


        text.setString(to_string(inventoryList.get(hero.slotNo).current) + "/" + to_string(inventoryList.get(hero.slotNo).quantity) + " " + Constants.ITEM_NAMES[inventoryList.get(hero.slotNo).name]);
        text.draw(batch);
        if (hero.isReloading)
        {
            text.setPosition(posView.x + 40, posView.y + 70);
            if (inventoryList.get(hero.slotNo).name == Constants.NameItem.PISTOL || inventoryList.get(hero.slotNo).name == Constants.NameItem.RIFLE)
            {
                text.setString("reloading");
            }
            else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.GRENADE)
            {
                //text.setString("pulling");
            }
            else
            {
                //text.setString("opening");
            }
            text.draw(batch);
        }

        if (maxNeighbors > 0)
        {
            text.setString("rescued: " + to_string(savedNeighbors));
            text.setPosition(posView.x + 5, posView.y + 100);
            text.draw(batch);

            int remaining = MAX_NUMBER_OF_NEIGHBORS - savedNeighbors;
            text.setString("remaining: " + to_string(remaining));
            text.setPosition(posView.x + 5, posView.y + 120);
            text.draw(batch);
        }
        else
        {
            text.setString("Kill Boss");
            text.setPosition(posView.x + 5, posView.y + 120);
            text.draw(batch);
        }
    }
*/
    Inventory GetNewInventoryItem(Loot loot, Sprite items) {
        Inventory inventory = new Inventory();
        inventory.name = loot.name;
        inventory.quantity = loot.quantity;
        inventory.current = 0;
        inventory.sprite = items;
        inventory.sprite.setRegion(loot.name.ordinal() * 32, 0, 32, 32);
        return inventory;
    }


    void CheckLoot(Sprite items) {
        float x = hero.sprite.getX();
        float y = hero.sprite.getY();

        boolean isItemAlreadyIn = false;

        for (Loot lootItem : lootList) {
            if (lootItem.isDrawn == true)  //if loot.item.center contains heroSprite  . add new item in inventory
            {
                Vector2 itemCenter = new Vector2();
                itemCenter.x = lootItem.pos.x + lootItem.sprite.getWidth() / 2;
                itemCenter.y = lootItem.pos.y + lootItem.sprite.getHeight() / 2;
                if (hero.sprite.getBoundingRectangle().contains(itemCenter)) {
                    hero.isSoundLoot = true;
                    if (lootItem.name != Constants.NameItem.AMMO)  //any item that we can take
                    {
                        //check if this item exists in inventory, and if so - upload it
                        //TODO: comm
                        int itemIndex = 1;//GetSlotIndexOfItem(lootItem, inventoryList);
                        if (itemIndex >= 0) {
                            inventoryList.set(itemIndex, inventoryList.get(itemIndex)).quantity += lootItem.quantity;
                            lootItem.isDrawn = false;
                            isItemAlreadyIn = true;
                        } else //(isItemAlreadyIn == false) //adding new item to inventory List
                        {
                            Inventory inventory = GetNewInventoryItem(lootItem, items);
                            inventoryList.add(inventory);

                            lootItem.isDrawn = false;
                            isItemAlreadyIn = true;
                            hero.nSlots += 1;
                        }
                    } else {
                        lootItem.isDrawn = false;
                        int nWeaponAmmoAdded = 0;
                        while (nWeaponAmmoAdded < Constants.AMMO_PACKS) {
                            for (Inventory itm : inventoryList) {
                                if (itm.name != Constants.NameItem.MIXTURE && itm.name != Constants.NameItem.DRINK) {
                                    itm.quantity += Constants.MAX_AMMO[itm.name.ordinal()];
                                    nWeaponAmmoAdded += 1;
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                lootList.remove(lootItem);
            }
        }
    }

    public void DrawBossBar(SpriteBatch batch, Boss boss, Vector2 viewPos) {
        if (boss.health > 0) {
            boss.indicator.setRegion(0, 0, 246 * (int) ((float) (boss.health) / (float) Constants.BOSS_MAX_HEALTH), 8);
        }
        boss.bar.setPosition(Constants.BOSS_BAR_POSITION.x + viewPos.x - Constants.WINDOW_SIZE.x / 2.f, Constants.BOSS_BAR_POSITION.y + viewPos.y - Constants.WINDOW_SIZE.y / 2.f);
        boss.indicator.setPosition(Constants.BOSS_INDICATOR_POSITION.x + viewPos.x - Constants.WINDOW_SIZE.x / 2.f, Constants.BOSS_INDICATOR_POSITION.y + viewPos.y - Constants.WINDOW_SIZE.y / 2.f);
        boss.bar.draw(batch);
        boss.indicator.draw(batch);
    }

    void DrawBoss(SpriteBatch batch, Sprite boss) {
        boss.draw(batch);
    }

    public void DrawEnemies(SpriteBatch batch) {
        for (Enemy enemy : zombieList) {
            enemy.sprite.draw(batch);
        }
    }

    public void DrawHero(SpriteBatch batch) {
        hero.sprite.draw(batch);
    }

    ;

    public void DrawNpc(SpriteBatch batch) {
        for (Npc npc : npcList) {
            npc.sprite.draw(batch);
        }
    }

    ;

    public void DeleteNpcList(Vector<Npc> npcs) {
        npcs.clear();
    }

    public void DeleteShots(Vector<Shot> shots) {
        shots.clear();
    }

    public void DrawThrowings(SpriteBatch batch) {
        for (Throwing throwing : throwingList) {
            throwing.sprite.draw(batch);
        }
    }


    void DrawShots(SpriteBatch batch, Hero hero) {
        for (Shot shot : shotList) {
            shot.sprite.setPosition(shot.pos.x, shot.pos.y);
            shot.sprite.draw(batch);
        }
        for (Explosion explosion : explosionList) {
            explosion.sprite.setRegion(0 + 250 * (int) (explosion.currentFrame), 0, 250, 140);
            explosion.currentFrame += 0.7f;
            explosion.sprite.draw(batch);
        }
    }

    ;

    public void DrawLoot(SpriteBatch batch) {
        if (lootList.isEmpty()) {
            System.out.print("zz");
        } else {
            System.out.print("aa");
        }

        for (Loot item : lootList) {
            if (item.isDrawn == true)
                item.sprite.draw(batch);
        }
    }

    ;

    public void DeleteThrowings(Vector<Throwing> throwingList) {
        throwingList.clear();
    }

    Loot GetNewLoot(final int nPic, float x, float y, Sprite spr) {
        Loot loot = new Loot();
        loot.name = Constants.NameItem.values()[nPic];
        //TODO: comm
        //loot.quantity = GetMaxQuantity(loot.name);
        loot.pos = new Vector2(x, y);
        loot.sprite = new Sprite();
        loot.sprite.setTexture(manager.get("images/items.png", Texture.class));
        //sprite.setTexture(tex);
        loot.sprite.setPosition(x, y);
        //TODO: comm
        loot.isDrawn = true;
        return loot;
    }

    public void GenerateLoot(Vector2 mapSize, int ItemsRemaining, final int nPic) {
        do {
            boolean needNewBlock = false;
            Vector2 newPos = new Vector2((float) ((int) (Math.random() * (int) mapSize.x) * Constants.STEP_TILE), (float) (((int) (Math.random() * (int) (mapSize.y)) * Constants.STEP_TILE)));

            Rectangle lootRect = new Rectangle(newPos.x, newPos.y, sprites.items.getWidth(), sprites.items.getHeight());
            boolean isIntersected = false;
            for (MapObject object : allObjects) {
                //TODO: comm
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                if (lootRect.overlaps(rect))
                {
                    needNewBlock = true;
                    break;
                }
            }
            if (needNewBlock) {
                continue;
            }

            //System.out.print(ItemsRemaining);

            //System.out.println(lootList.size());

            for (Loot lootItem : lootList) {
                //if (Math.abs(lootItem.pos.x - newPos.x) < 100 && Math.abs(lootItem.pos.y - newPos.y) < 100) {

                if ((Math.abs(lootItem.pos.x - newPos.x) < 100 && Math.abs(lootItem.pos.y - newPos.y) < 100)) {
                    needNewBlock = true;
                    break;
                }
            }
            if (needNewBlock == false) {
                //TODO: comm
                //loot.sprite.setRegion(nPic * 32, 0, 32, 32);
                lootList.add(GetNewLoot(nPic, newPos.x, newPos.y, sprites.items));
                ItemsRemaining -= 1;
            }
        } while (ItemsRemaining > 0);
    }


    public void InitializeLoot(Sprite sprite) {
        System.out.println("GenerateLoot1ST");
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 5, Constants.NameItem.DRINK.ordinal());
        System.out.println("GenerateLoot2ND");
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 3, Constants.NameItem.PISTOL.ordinal());
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 2, Constants.NameItem.AMMO.ordinal());
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 1, Constants.NameItem.RIFLE.ordinal());
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 1, Constants.NameItem.MIXTURE.ordinal());
        GenerateLoot(new Vector2(Constants.WIDTH_MAP, Constants.HEIGHT_MAP), 2, Constants.NameItem.GRENADE.ordinal());
    }
}
