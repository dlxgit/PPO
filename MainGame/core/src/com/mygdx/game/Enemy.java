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



    public void ComputeThrownItemPosition(Sprite sprite, Constants.Direction dir, float time, boolean isHero)
    {
        Vector2 startPos = new Vector2(sprite.getX(), sprite.getY());
    }




    public void CheckNpcDeath(Vector<Npc> npcList, Enemy enemy)
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

    public Constants.Direction ComputeRandDir(Enemy enemy)
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

    public void CheckEnemyDir(Enemy enemy, float time)
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

    public void CheckEnemyFollow(Enemy enemy, Hero hero)
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
    public boolean IsEnemyNearHero(Hero hero, Enemy enemy)
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

    public void UpdateEnemyAttack(Hero hero, Enemy enemy, final float time)
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

    public boolean IsIntersectWithOtherEnemy(Vector<Enemy> zombieList, int index)
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

    public boolean IsIntersectWithHero(Sprite enemy, Sprite hero)
    {
        if (enemy.getBoundingRectangle().overlaps(hero.getBoundingRectangle()))
            return true;
        return false;
    }


    public void UpdateEnemyFrame(float time)
    {
        if (type == Constants.EnemyType.COMMON)
        {
            if (state == Constants.EnemyState.DEAD)  //if zombie is exploding
            {
                sprite.setRegion(5 + 40 * (int)currentFrame, 344, 34, 48);
                currentFrame += 0.05f;
                if (currentFrame > 9)
                {
                    state = Constants.EnemyState.EXPLODED;
                }
            }
            if (state == Constants.EnemyState.NOTSPAWNED)  //if zombie is not spawned yet
            {
                sprite.setRegion(15 + 50 * (int)currentFrame, 12, 33, 51);

                if (currentFrame > 5)
                {
                    state = Constants.EnemyState.ACTIVE;
                    currentFrame = 0;
                }
                currentFrame += 0.04f;
            }
            else if (state == Constants.EnemyState.ACTIVE)
            {
                if (health < 1)
                {
                    state = Constants.EnemyState.DEAD;
                }
                else
                {
                    //sprite change for active moving zombies
                    switch (dir)
                    {
                        case UP:
                            sprite.setRegion(15 + 36 * (int)currentFrame, 84 + 59 + 59, 27, 49);
                            if (currentFrame > 4)
                            {
                                currentFrame = 0;
                            }
                            break;
                        case UPRIGHT: case RIGHT: case DOWNRIGHT:
                        sprite.setRegion(15 + 36 * (int)currentFrame, 84 + 59 * 3, 27, 49);
                        if (currentFrame > 2)
                        {
                            currentFrame = 0;
                        }
                        break;
                        case DOWN:
                            sprite.setRegion(15 + 36 * (int)currentFrame, 84, 27, 49);
                            if (currentFrame > 2)
                            {
                                currentFrame = 0;
                            }
                            break;
                        case DOWNLEFT: case LEFT: case UPLEFT:
                        sprite.setRegion(15 + 36 * (int)currentFrame, 84 + 59, 27, 49);
                        if (currentFrame > 2)
                        {
                            currentFrame = 0;
                        }
                        break;
                        case NONE:
                            //no need??
                            sprite.setRegion(190, 84, 27, 48);
                            if (currentFrame > 4)
                            {
                                currentFrame = 0;
                            }
                            break;
                    }
                }
            }
            currentFrame += 0.05f;
        }


        else if (type == Constants.EnemyType.AXE)
        {
            if (isAttack)
            {
                ComputeEnemyAttackFrame();
            }
            else
            {
                if (state == Constants.EnemyState.ACTIVE)
                {
                    if (health < 1)
                    {
                        state = Constants.EnemyState.DEAD;
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


                        switch (dir)
                        {
                            case UP:
                                sprite.setRegion(10 + 22 * (int)currentFrame, 7, 21, 31);
                                if (currentFrame > 4)
                                {
                                    currentFrame = 0;
                                }
                                break;
                            case UPRIGHT: case RIGHT: case DOWNRIGHT:
                            sprite.setRegion(10 + 22 * (int)currentFrame, 118, 21, 31);
                            if (currentFrame > 2)
                            {
                                currentFrame = 0;
                            }
                            break;
                            case DOWN:
                                sprite.setRegion(10 + 22 * (int)currentFrame, 43, 21, 31);
                                if (currentFrame > 2)
                                {
                                    currentFrame = 0;
                                }
                                break;
                            case DOWNLEFT: case LEFT: case UPLEFT:
                            sprite.setRegion(10 + 22 * (int)currentFrame, 80, 21, 31);
                            if (currentFrame > 2)
                            {
                                currentFrame = 0;
                            }
                            break;
                            case NONE:
                                //no need??
                                sprite.setRegion(190, 84, 27, 48);
                                if (currentFrame > 4)
                                {
                                    currentFrame = 0;
                                }
                                break;
                        }
                    }
                }
                else if (state == Constants.EnemyState.DEAD)
                {
                    sprite.setRegion(5 + 29 * (int)currentFrame,232, 30, 38);
                    currentFrame += 0.03f;
                    if (currentFrame > 9)
                    {
                        state = Constants.EnemyState.EXPLODED;
                    }
                }
                else if (state == Constants.EnemyState.NOTSPAWNED)
                {
                    //cout << "TIMES " << time << " " << spawnTime << " " << spawnDelay << " " << endl;
                    if (spawnTime + spawnDelay > time)
                    {
                        state = Constants.EnemyState.ACTIVE;
                    }
                }
                currentFrame += 0.05f;
            }
        }
    };

    public void ComputeEnemyAttackFrame()
    {
        switch (dirLast)
        {
            case UP:
                sprite.setRegion(34, 174, 28, 27);
                if (currentFrame > 4)
                {
                    currentFrame = 0;
                }
                break;
            case UPRIGHT: case RIGHT: case DOWNRIGHT:
            sprite.setRegion(62, 173, 28, 27);
            if (currentFrame > 2)
            {
                currentFrame = 0;
            }
            break;
            case DOWN:
                sprite.setRegion(89, 173, 28, 27);
                if (currentFrame > 2)
                {
                    currentFrame = 0;
                }
                break;
            case DOWNLEFT: case LEFT: case UPLEFT:
            sprite.setRegion(5, 173, 28, 27);
            if (currentFrame > 2)
            {
                currentFrame = 0;
            }
            break;
            default:
                break;
        }
        currentFrame += 0.05f;

        if (currentFrame > 2)
        {
            isAttack = false;
        }
    }

    public void EnemyMoveRandom(Vector<Enemy> zombieList)  //not using
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


    public void ComputeEnemyDirection(Enemy enemy, Vector2 heroPos)
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


    public void CheckEnemyDir(float time, Enemy enemy)
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



    void DeleteEnemyList(Vector<Enemy> zombies)
    {
        zombies.clear();
    }
}
