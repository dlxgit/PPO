package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Enemy {
    Constants.EnemyType type;
    Constants.EnemyState state;
    Vector2 pos;
    int health;
    Constants.Direction dir;
    Constants.Direction dirLast;
    boolean follow;
    float attack_time;
    Texture texture;
    Sprite sprite;
    boolean collision;
    float currentFrame;
    boolean isNear;
    float spawnTime;
    float dirChangeTime;
    boolean isAttack;
    boolean isAction;
    float spawnDelay;

    boolean isStop;
    float last_action_time;
    float actionTime;
    float actionDuration;

    float attackCooldown;



    void ComputeThrownItemPosition(Sprite sprite, Constants.Direction dir, float time, boolean isHero)
    {
        Vector2 startPos = new Vector2(sprite.getX(), sprite.getY());
    }

    Throwing CreateThrowing(Sprite object, Sprite target, String type,Sprite throwingSprite, float time)
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

    void SpawnEnemy(Vector<Enemy> zombieList, float time, int posX, int posY, Sprites sprites, final Constants.EnemyType type)
    {
        Enemy enemy = new Enemy();

        enemy.pos.x = posX;
        enemy.pos.y = posY;

        enemy.health = Constants.ZOMBIE_MAX_HP;

        //enemy.texture = texture_zombie;
        enemy.type = type;


        enemy.currentFrame = 0;
        enemy.attack_time = 0;
        enemy.dir = Constants.Direction.NONE;
        enemy.dirLast = Constants.Direction.NONE;
        enemy.follow = false;
        enemy.isNear = false;

        if (enemy.type == Constants.EnemyType.COMMON)
        {
            enemy.state = Constants.EnemyState.NOTSPAWNED;
            enemy.sprite = sprites.zombie;
            enemy.sprite.setRegion(5, 5, 30, 30);
            enemy.spawnDelay = 0;

        }
        else if (enemy.type == Constants.EnemyType.AXE)
        {
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

        enemy.sprite.setPosition((float)posX, (float)posY);
        zombieList.add(enemy);
    };

    void CheckNpcDeath(Vector<Npc> npcList, Enemy enemy)
    {
        Vector2 zombieCenter = Sprites.GetSpriteCenter(enemy.sprite);

        for (Npc npc : npcList)
        {
            if (npc.state == Constants.NpcState.LIVING)  //if loot.item.center contains heroSprite  -> add new item in inventory
            {
                Vector2 npcCenter = Sprites.GetSpriteCenter(npc.sprite);
                //cout << (Math.abs(zombieCenter.x - npcCenter.x)) << " CENTER " << Math.abs(zombieCenter.y - npcCenter.y) << endl;
                if ((Math.abs(zombieCenter.x - npcCenter.x) < 35) && (Math.abs(zombieCenter.y - npcCenter.y)) < 35)
                {
                    npc.state = Constants.NpcState.KILLED;
                }
            }
        }
    };

    Constants.Direction ComputeRandDir(Enemy enemy)
    {
        Constants.Direction dir = enemy.dir;

        switch (dir)
        {
            case UP:
                dir = Constants.Direction.DOWN;
                break;
            case RIGHT:
                dir = Constants.Direction.LEFT;
                break;
            case DOWN:
                dir = Constants.Direction.UP;
                break;
            case LEFT:
                dir = Constants.Direction.RIGHT;
                break;
        }
        return dir;
    };

    void CheckEnemyDir(Enemy enemy, float time)
    {
        if (enemy.follow == false)
        {
            if (enemy.dirChangeTime == 0)
            {
                //cout << " 1 IS " << endl;
                enemy.dir = ComputeRandDir(enemy);
            }
            if (time - enemy.dirChangeTime > Constants.ZOMBIE_DIR_CHANGE_TIME)
            {
                //cout << " 2 IS " << endl;
                enemy.dirChangeTime = 0;
            }
        }
    };

    void CheckEnemyFollow(Enemy enemy, Hero hero)
    {
        if (Math.abs(enemy.pos.x - hero.pos.x) > Constants.ZOMBIE_VISION_DISTANCE || Math.abs(enemy.pos.y - hero.pos.y) > Constants.ZOMBIE_VISION_DISTANCE)
        {
            enemy.follow = false;
        }
        if (enemy.follow == false)
        {
            if (Math.abs(enemy.pos.x - hero.pos.x) < Constants.ZOMBIE_VISION_DISTANCE && Math.abs(enemy.pos.y - hero.pos.y) < Constants.ZOMBIE_VISION_DISTANCE)
            {
                enemy.follow = true;
            }
        }
    };

    //should i store it in zombieList>? if i can just keep it here (return true)
    boolean IsEnemyNearHero(Hero hero, Enemy enemy)
    {
        //comparing distance between two nearest points of hero and zombie sprites    to define is zombie near or not
        //TODO: ref

        Vector2 zombieCenter = Sprites.GetSpriteCenter(enemy.sprite);
        Vector2 heroCenter = Sprites.GetSpriteCenter(hero.sprite);

        int dx_max = 20;
        int dy_max = 20;
        int distanceX = 30;
        int distanceY = 30;

        //if hero and zombie sprites are very close - allow Beast to attack
        if ((Math.abs(zombieCenter.x - heroCenter.x) < distanceX) && (Math.abs(zombieCenter.y - heroCenter.y) < distanceY))
        {
            return true;
        }

        switch (hero.dirLast)
        {
            case UP:
                if (Math.abs(heroCenter.x - zombieCenter.x) < dx_max && (heroCenter.y - zombieCenter.y >= 0 && heroCenter.y - zombieCenter.y < distanceY))
                {
                    return true;
                }
                break;
            case RIGHT: case UPRIGHT: case DOWNRIGHT:
            if ((zombieCenter.x - heroCenter.x >= 0 && zombieCenter.x - heroCenter.x < distanceX) && (Math.abs(heroCenter.y - zombieCenter.y) < dy_max))
            {
                return true;
            }
            break;
            case DOWN:
                if (Math.abs(zombieCenter.x - heroCenter.x) < dx_max && (zombieCenter.y - heroCenter.y >= 0 && zombieCenter.x - heroCenter.x < distanceY))
                {
                    return true;
                }
                break;
            case LEFT: case UPLEFT: case DOWNLEFT:
            if ((heroCenter.x - zombieCenter.x >= 0 && zombieCenter.x - heroCenter.x < distanceX) && (Math.abs(heroCenter.x - zombieCenter.x) < dy_max))
            {
                return true;
            }
            break;
        }
        return false;
    };

    void UpdateEnemyAttack(Hero hero, Enemy enemy, final float time)
    {
        //add correct xyHero xyZombie for detecting meleeAttack
        //TODO: change mechanics of zombie attack(depends on side from which it attacks), same for beast attack
        if ((Math.abs(enemy.pos.x - hero.pos.x) < 7) && (Math.abs(enemy.pos.y - hero.pos.y) < 7))
        {
            //attack
            if (enemy.attack_time < time - 1.5)
            {
                enemy.isAttack = true;
                hero.health -= (int)(Constants.ZOMBIE_DAMAGE * hero.damageResistance);
                if (hero.state != Constants.HeroState.BEAST)
                {
                    hero.state = Constants.HeroState.DAMAGED;
                    hero.isSoundTakeDamage = true;
                    hero.currentFrame = 0;
                }
                enemy.attack_time = time;
                //enemy.isNear = enemy.dir;  //for Beast melee attack
            }
        }
    };

    boolean IsIntersectWithOtherEnemy(Vector<Enemy> zombieList, int index)
    {
        int i = 0;
        for (Enemy enemy : zombieList)
        {
            if (i != index)
            {
                if (zombieList.get(index).sprite.getBoundingRectangle().overlaps(enemy.sprite.getBoundingRectangle()))
                {
                    //cout << "IND " << index << " i " << i << endl;
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    boolean IsIntersectWithHero(Sprite enemy, Sprite hero)
    {
        if (enemy.getBoundingRectangle().overlaps(hero.getBoundingRectangle()))
            return true;
        return false;
    }


    void UpdateEnemyFrame(Enemy enemy, float time)
    {
        if (enemy.type == Constants.EnemyType.COMMON)
        {
            if (enemy.state == Constants.EnemyState.DEAD)  //if zombie is exploding
            {
                enemy.sprite.setRegion(5 + 40 * (int)enemy.currentFrame, 344, 34, 48);
                enemy.currentFrame += 0.05f;
                if (enemy.currentFrame > 9)
                {
                    enemy.state = Constants.EnemyState.EXPLODED;
                }
            }
            if (enemy.state == Constants.EnemyState.NOTSPAWNED)  //if zombie is not spawned yet
            {
                enemy.sprite.setRegion(15 + 50 * (int)enemy.currentFrame, 12, 33, 51);

                if (enemy.currentFrame > 5)
                {
                    enemy.state = Constants.EnemyState.ACTIVE;
                    enemy.currentFrame = 0;
                }
                enemy.currentFrame += 0.04f;
            }
            else if (enemy.state == Constants.EnemyState.ACTIVE)
            {
                if (enemy.health < 1)
                {
                    enemy.state = Constants.EnemyState.DEAD;
                }
                else
                {
                    //sprite change for active moving zombies
                    switch (enemy.dir)
                    {
                        case UP:
                            enemy.sprite.setRegion(15 + 36 * (int)enemy.currentFrame, 84 + 59 + 59, 27, 49);
                            if (enemy.currentFrame > 4)
                            {
                                enemy.currentFrame = 0;
                            }
                            break;
                        case UPRIGHT: case RIGHT: case DOWNRIGHT:
                        enemy.sprite.setRegion(15 + 36 * (int)enemy.currentFrame, 84 + 59 * 3, 27, 49);
                        if (enemy.currentFrame > 2)
                        {
                            enemy.currentFrame = 0;
                        }
                        break;
                        case DOWN:
                            enemy.sprite.setRegion(15 + 36 * (int)enemy.currentFrame, 84, 27, 49);
                            if (enemy.currentFrame > 2)
                            {
                                enemy.currentFrame = 0;
                            }
                            break;
                        case DOWNLEFT: case LEFT: case UPLEFT:
                        enemy.sprite.setRegion(15 + 36 * (int)enemy.currentFrame, 84 + 59, 27, 49);
                        if (enemy.currentFrame > 2)
                        {
                            enemy.currentFrame = 0;
                        }
                        break;
                        case NONE:
                            //no need??
                            enemy.sprite.setRegion(190, 84, 27, 48);
                            if (enemy.currentFrame > 4)
                            {
                                enemy.currentFrame = 0;
                            }
                            break;
                    }
                }
            }
            enemy.currentFrame += 0.05f;
        }


        else if (enemy.type == Constants.EnemyType.AXE)
        {
            if (enemy.isAttack)
            {
                ComputeEnemyAttackFrame(enemy);
            }
            else
            {
                if (enemy.state == Constants.EnemyState.ACTIVE)
                {
                    if (enemy.health < 1)
                    {
                        enemy.state = Constants.EnemyState.DEAD;
                    }
                    else
                    {
                        //sprite change for active moving zombies


					/*
					10, 7, 22, 31
					10, 43, 22, 31
					10, 80, 22, 31
					10, 118, 22, 31
					*/

					/*
					5, 173, 24, 33
					5, 201, 24, 33
					5, 229, 24, 33
					5, 256, 24, 33
					*/


                        switch (enemy.dir)
                        {
                            case UP:
                                enemy.sprite.setRegion(10 + 22 * (int)enemy.currentFrame, 7, 21, 31);
                                if (enemy.currentFrame > 4)
                                {
                                    enemy.currentFrame = 0;
                                }
                                break;
                            case UPRIGHT: case RIGHT: case DOWNRIGHT:
                            enemy.sprite.setRegion(10 + 22 * (int)enemy.currentFrame, 118, 21, 31);
                            if (enemy.currentFrame > 2)
                            {
                                enemy.currentFrame = 0;
                            }
                            break;
                            case DOWN:
                                enemy.sprite.setRegion(10 + 22 * (int)enemy.currentFrame, 43, 21, 31);
                                if (enemy.currentFrame > 2)
                                {
                                    enemy.currentFrame = 0;
                                }
                                break;
                            case DOWNLEFT: case LEFT: case UPLEFT:
                            enemy.sprite.setRegion(10 + 22 * (int)enemy.currentFrame, 80, 21, 31);
                            if (enemy.currentFrame > 2)
                            {
                                enemy.currentFrame = 0;
                            }
                            break;
                            case NONE:
                                //no need??
                                enemy.sprite.setRegion(190, 84, 27, 48);
                                if (enemy.currentFrame > 4)
                                {
                                    enemy.currentFrame = 0;
                                }
                                break;
                        }
                    }
                }
                else if (enemy.state == Constants.EnemyState.DEAD)
                {
                    enemy.sprite.setRegion(5 + 29 * (int)enemy.currentFrame,232, 30, 38);
                    enemy.currentFrame += 0.03f;
                    if (enemy.currentFrame > 9)
                    {
                        enemy.state = Constants.EnemyState.EXPLODED;
                    }
                }
                else if (enemy.state == Constants.EnemyState.NOTSPAWNED)
                {
                    //cout << "TIMES " << time << " " << enemy.spawnTime << " " << enemy.spawnDelay << " " << endl;
                    if (enemy.spawnTime + enemy.spawnDelay > time)
                    {
                        enemy.state = Constants.EnemyState.ACTIVE;
                    }
                }
                enemy.currentFrame += 0.05f;
            }
        }
    };

    void ComputeEnemyAttackFrame(Enemy  enemy)
    {
        switch (enemy.dirLast)
        {
            case UP:
                enemy.sprite.setRegion(34, 174, 28, 27);
                if (enemy.currentFrame > 4)
                {
                    enemy.currentFrame = 0;
                }
                break;
            case UPRIGHT: case RIGHT: case DOWNRIGHT:
            enemy.sprite.setRegion(62, 173, 28, 27);
            if (enemy.currentFrame > 2)
            {
                enemy.currentFrame = 0;
            }
            break;
            case DOWN:
                enemy.sprite.setRegion(89, 173, 28, 27);
                if (enemy.currentFrame > 2)
                {
                    enemy.currentFrame = 0;
                }
                break;
            case DOWNLEFT: case LEFT: case UPLEFT:
            enemy.sprite.setRegion(5, 173, 28, 27);
            if (enemy.currentFrame > 2)
            {
                enemy.currentFrame = 0;
            }
            break;
            default:
                break;
        }
        enemy.currentFrame += 0.05f;

        if (enemy.currentFrame > 2)
        {
            enemy.isAttack = false;
        }
    }

    void ResetEnemySpawnTime(Vector<Enemy> zombieList, float  time)
    {
        for (Enemy enemy : zombieList)
        {
            enemy.spawnTime = time;
        }
    }

    void EnemyMoveRandom(Vector<Enemy> zombieList)  //not using
    {
        for (Enemy zombie : zombieList)
        {
            if (zombie.dir == Constants.Direction.NONE)
            {
                int rand_dir = 1 + (int)(Math.random() * 4);

                switch (rand_dir)
                {
                    case 1:
                        zombie.dir = Constants.Direction.UP;
                        break;
                    case 2:
                        zombie.dir = Constants.Direction.RIGHT;
                        break;
                    case 3:
                        zombie.dir = Constants.Direction.DOWN;
                        break;
                    case 4:
                        zombie.dir = Constants.Direction.LEFT;
                        break;
                }
            }
        }
    };


    void ComputeEnemyDirection(Enemy enemy, Vector2 heroPos)
    {
        //compute distance and dir
        Vector2 distance = new Vector2( Math.abs(heroPos.x - enemy.pos.x), Math.abs(heroPos.y - enemy.pos.y));
        if (distance.x > 5 || distance.y > 5)
        {
            //TODO: check left-right dir zombie sprite bug (almost)
            if ((distance.x > 3 && distance.y > 3) && (distance.x / distance.y > 0.9) && (distance.y / distance.x < 1.1))
            {
                if (heroPos.x >= enemy.pos.x && heroPos.y >= enemy.pos.y)
                    enemy.dir = Constants.Direction.DOWNRIGHT;
                else if (heroPos.x >= enemy.pos.x && heroPos.y < enemy.pos.y)
                    enemy.dir = Constants.Direction.UPRIGHT;
                else if (heroPos.x < enemy.pos.x && heroPos.y >= enemy.pos.y)
                    enemy.dir = Constants.Direction.DOWNLEFT;
                else if (heroPos.x < enemy.pos.x && heroPos.y < enemy.pos.y)
                    enemy.dir = Constants.Direction.UPLEFT;
            }
            else if (distance.x >= distance.y)
            {
                if (heroPos.x > enemy.pos.x)
                    enemy.dir = Constants.Direction.RIGHT;
                else
                    enemy.dir = Constants.Direction.LEFT;
            }
            else if (distance.x < distance.y)
            {
                if (heroPos.y > enemy.pos.y)
                    enemy.dir = Constants.Direction.DOWN;
                else
                    enemy.dir = Constants.Direction.UP;
            }
        }
    }

    void SpawnEnemyRandomly(Vector<Enemy> zombieList, Vector<Object> objects, int zombiesRemaining, float time, Sprites sprites)
    {
        //TODO: MAKE FUNCTION MORE READABLE
        do
        {
            boolean needNewBlock = false;

            Vector2 newPos = new Vector2((float)((int)(Math.random() * Constants.WIDTH_MAP) * Constants.STEP), (float)(((int)(Math.random() * Constants.HEIGHT_MAP) * Constants.STEP)));

            Rectangle lootRect = new Rectangle(newPos.x,newPos.y, Constants.ZOMBIE_SPAWN_RADIUS_COLLISION, Constants.ZOMBIE_SPAWN_RADIUS_COLLISION);
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
            if (!needNewBlock)
            {
                for (Enemy enemy : zombieList)
                if ((Math.abs(enemy.pos.x - newPos.x) < 100 && Math.abs(enemy.pos.y - newPos.y) < 100))
                {
                    needNewBlock = true;
                    break;
                }
                if (needNewBlock == false)
                {
                    //sprites.enemy.setPosition(newPos);
                    //ZombieSpawn(zombieList, time, newPos.x,newPos.y, sprite_zombie, COMMON);
                    if (time < 10)
                    {
                        SpawnEnemy(zombieList, time, (int)newPos.x, (int)newPos.y, sprites, Constants.EnemyType.COMMON);
                    }
                    else
                    {
                        Constants.EnemyType type = Constants.EnemyType.COMMON;
                        if (!zombieList.isEmpty())
                        {
                            if (zombieList.get(zombieList.size() - 1).type == Constants.EnemyType.AXE)
                                type = Constants.EnemyType.COMMON;
                            else type = Constants.EnemyType.AXE;
                        }
                        SpawnEnemy(zombieList, time, (int)newPos.x, (int)newPos.y, sprites, type);
                    }
                    //zombieList[zombieList.size() - 1].sprite = sprites.zombie;

                    zombiesRemaining -= 1;
                }
            }
        } while (zombiesRemaining > 0);
    }

    void CheckEnemyDir(float time, Enemy enemy)
    {
        if (enemy.follow == false)
        {
            if (enemy.dirChangeTime == 0)
            {
                //cout << " 1 IS " << endl;
                enemy.dir = ComputeRandDir(enemy);
            }
            if (time - enemy.dirChangeTime > Constants.ZOMBIE_DIR_CHANGE_TIME)
            {
                //cout << " 2 IS " << endl;
                enemy.dirChangeTime = 0;
            }
        }
    };

    void CheckEnemyExplosion(Vector<Explosion> explosionList, Vector<Enemy> zombieList)
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

    void DrawEnemies(SpriteBatch batch, Vector<Enemy> zombieList)
    {
        for (Enemy  enemy : zombieList)
        {
            enemy.sprite.draw(batch);
        }
    };

    void DeleteEnemyList(Vector<Enemy> zombies)
    {
        zombies.clear();
    }
}
