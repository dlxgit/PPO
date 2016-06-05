package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;


import com.mygdx.game.Sprites;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Boss {
    Sprite sprite;
    Texture texture;
    Constants.Direction dir;
    Constants.Direction lastSide;
    int health;
    float currentFrame;

    Constants.BossState state;
    Constants.BossEvent eventType;

    float spawnTime;
    float eventstartTime;
    boolean isAttack;
    boolean isEvent;
    int eventCount;

    int shotCounter;

    boolean isFollow;

    float speed;

    int spawnedEnemies;

    boolean wasCharged;

    float followForShootTime;
    float shootStartTime;

    float lastShootTime;
    //BOSS_CHANGE_EVENT_TIME

    Sprite bar;
    Sprite indicator;

    boolean isCommonZombie;



    public void InitializeBoss(Boss boss, Sprites sprites, float time)
    {
        boss.sprite = sprites.boss;
        boss.spawnTime = time;
        boss.health = Constants.BOSS_MAX_HEALTH;
        boss.currentFrame = 0;
        boss.isEvent = false;
        boss.isAttack = false;
        boss.eventCount = 0;
        boss.spawnedEnemies = 0;
        boss.currentFrame = 0;
        boss.state = Constants.BossState.SPAWNING;

        boss.dir = Constants.Direction.LEFT;

        //boss.sprite.setRegion(11, 13, 106, 154));
        boss.sprite.setPosition(14 * 64, 5 * 64);

        boss.sprite.setRegion(11, 189, 106, 154);

        boss.lastSide = Constants.Direction.LEFT;
        boss.followForShootTime = 0;
        boss.isFollow = true;

        //
        boss.speed = Constants.STEP_BOSS;

        boss.eventCount = 0;
        boss.eventstartTime = time;

        boss.wasCharged = false;

        boss.bar = sprites.boss_bar;
        boss.bar.setPosition(Constants.BOSS_BAR_POSITION.x, Constants.BOSS_BAR_POSITION.y);
        boss.indicator = sprites.boss_indicator;
        boss.indicator.setPosition(Constants.BOSS_INDICATOR_POSITION.x,Constants.BOSS_INDICATOR_POSITION.y);
        boss.isCommonZombie = true;
    }

    public void UpdateBossFrame()
    {
        //11 383
        if (eventType == Constants.BossEvent.STOMP_FOR_ZOMBIES)
        {
            if (currentFrame > 4)
            {
                currentFrame = 0;
            }
            if (lastSide == Constants.Direction.LEFT)
            {
                sprite.setRegion(11 + 105 * (int)currentFrame, 13, 106, 154);
            }
            else
            {
                sprite.setRegion(11 + 105 * (int)currentFrame, 189, 106, 154);
            }
            currentFrame += 0.1f;
        }
        else if (eventType != Constants.BossEvent.SHOOT)
        {
            switch (dir)
            {
                case UP: case DOWN:
                if (lastSide == Constants.Direction.LEFT)
                {
                    sprite.setRegion(11 + 105 * (int) currentFrame, 13, 106, 154);
                }
                else
                {
                    sprite.setRegion(11 + 105 * (int) currentFrame, 189, 106, 154);
                }
                break;
                case UPRIGHT: case RIGHT: case DOWNRIGHT:
                sprite.setRegion(11 + 105 * (int) currentFrame, 189, 106, 154);
                break;
                case DOWNLEFT: case LEFT: case UPLEFT:
                sprite.setRegion(11 + 105 * (int) currentFrame, 13, 106, 154);
                break;
                default:
                    break;
            }
            currentFrame += 0.07f;
            if (eventType == Constants.BossEvent.CHARGE)
            {
                currentFrame += 0.2f;
            }
            if (currentFrame > 4)
            {
                currentFrame = 0;
            }
        }
        else
        {
            if (lastSide == Constants.Direction.LEFT)
            {
                sprite.setRegion(11 + 105 * (int) currentFrame, 383, 106, 154);
            }
            else
            {
                sprite.setRegion(11 + 105 * (int) currentFrame, 572, 106, 154);
            }
            if (currentFrame > 2 && isAttack == false)
            {
                isAttack = true;
            }
            currentFrame += 0.2f;
            if (currentFrame >= 3)
            {
                currentFrame = 1;
            }
            //392 404 42 19*throwing.currentFrame
        }
    }

    public boolean IsReadyToShoot(Boss boss, float time)
    {
        if (boss.eventType == Constants.BossEvent.SHOOT)
        {
            if (boss.lastShootTime + Constants.BOSS_SHOOTING_COOLDOWN < time)
            {
                return true;
            }
        }
        return false;
    }

    public boolean IsBossNearMapCenter(Sprite boss)
    {
        Vector2 pos = new Vector2(Sprites.GetSpriteCenter(boss));
        //cout << "BOSS POS " << pos.x << " " << pos.y << " TO " << LEVEL1_CENTER_POS.x << " " << LEVEL1_CENTER_POS.y << " CHECK " << abs(pos.x - LEVEL1_CENTER_POS.x + 32) << " " << abs(pos.y - LEVEL1_CENTER_POS.y + 32) << endl;

        if (Math.abs(pos.x - Constants.LEVEL1_CENTER_POS.x) <= 15.f && Math.abs(pos.y - Constants.LEVEL1_CENTER_POS.y) <= 15.f)
        {
            return true;
        }
        return false;
    }



    public void ComputeBossDirection(Boss boss, Rectangle rect, Sprite hero)
    {
        Vector2 heroPos = Sprites.GetSpriteCenter(hero);
        //Vector2f bossPos = { rect.left + rect.width / 2.f, rect.top + rect.height / 2.f };
        Vector2 bossPos = Sprites.GetSpriteCenter(boss.sprite);

        if (boss.eventType == Constants.BossEvent.STOMP_FOR_ZOMBIES)
        {
            boss.dir = Constants.Direction.NONE;
        }
        else if (boss.eventType == Constants.BossEvent.SHOOT)
        {
            boss.dir = Constants.Direction.NONE;
            if (heroPos.x <= bossPos.x)
            {
                boss.lastSide = Constants.Direction.LEFT;
            }
            else boss.lastSide = Constants.Direction.RIGHT;
        }
        else
        {
            if (boss.eventType == Constants.BossEvent.MOVE_OUT)
            {
                heroPos = Constants.LEVEL1_CENTER_POS;//{ 12 * 64, 6 * 64 };
            }

            Constants.Direction dirOld = boss.dir;
            Vector2 distance = new Vector2(Math.abs(heroPos.x - bossPos.x), Math.abs(heroPos.y - bossPos.y));

            if (distance.x > 5 || distance.y > 5)
            {
                //TODO: check left-right dir zombie sprite bug (almost)
                if ((distance.x > 3 && distance.y > 3) && (distance.x / distance.y > 0.9) && (distance.y / distance.x < 1.1))
                {
                    if (heroPos.x >= bossPos.x && heroPos.y >= bossPos.y)
                        boss.dir = Constants.Direction.DOWNRIGHT;
                    else if (heroPos.x >= bossPos.x && heroPos.y < bossPos.y)
                        boss.dir = Constants.Direction.UPRIGHT;
                    else if (heroPos.x < bossPos.x && heroPos.y >= bossPos.y)
                        boss.dir = Constants.Direction.DOWNLEFT;
                    else if (heroPos.x < bossPos.x && heroPos.y < bossPos.y)
                        boss.dir = Constants.Direction.UPLEFT;
                }
                else if (distance.x >= distance.y)
                {
                    if (heroPos.x > bossPos.x)
                        boss.dir = Constants.Direction.RIGHT;
                    else
                        boss.dir = Constants.Direction.LEFT;
                }
                else if (distance.x < distance.y)
                {
                    if (heroPos.y > bossPos.y)
                        boss.dir = Constants.Direction.DOWN;
                    else
                        boss.dir = Constants.Direction.UP;
                }
            }
            if ((boss.lastSide == Constants.Direction.LEFT) && (boss.dir == Constants.Direction.UPRIGHT || boss.dir == Constants.Direction.RIGHT || boss.dir == Constants.Direction.DOWNRIGHT))
            {
                boss.lastSide = Constants.Direction.RIGHT;
            }
            else if ((boss.lastSide == Constants.Direction.RIGHT) && (boss.dir == Constants.Direction.UPLEFT || boss.dir == Constants.Direction.LEFT || boss.dir == Constants.Direction.DOWNLEFT))
            {
                boss.lastSide = Constants.Direction.LEFT;
            }
        }
    }

    public Rectangle GetBossCollisionRect(Sprite sprite)
    {
        Rectangle bounds = sprite.getBoundingRectangle();
        return new Rectangle(bounds.getX(), bounds.getY() + 50.f, bounds.width, bounds.height - 50.f);
    }

    public boolean IsReachedHero(Sprite hero, Sprite boss)
    {
        Vector2 heroPos = Sprites.GetSpriteCenter(hero);
        Vector2 bossPos = Sprites.GetSpriteCenter(boss);

        //cout << "DIFF " << bossPos.x - heroPos.x << " " << bossPos.y + 10.f - heroPos.y << endl;
        if (Math.abs(bossPos.x - heroPos.x) < 15.f && Math.abs(bossPos.y + 10.f - heroPos.y) < 15.f)
        {
            return true;
        }
        return false;
    }

    public boolean IsFootRectIntersectWithHero(Sprite hero, Rectangle boss)
    {
        if (hero.getBoundingRectangle().overlaps(boss))
            return true;
        return false;
    }

    public boolean IsBossAbleToShoot(Sprite hero, Sprite boss)
    {
        Vector2 heroPos = Sprites.GetSpriteCenter(hero);
        Vector2 bossPos = Sprites.GetSpriteCenter(boss);

        //cout << "DIFF " << bossPos.x - heroPos.x << " " << bossPos.y + 10.f - heroPos.y << endl;
        //cout << "ABILITY " << bossPos.x - heroPos.x << " " << heroPos.y - bossPos.y << " " << endl;
        if (Math.abs(bossPos.x - heroPos.x) < 130.f && (heroPos.y - bossPos.y < 100.f) && (heroPos.y - bossPos.y >= 0))
        {
            return true;
        }
        return false;

    }

    public void UpdateBossMoveSpeed(Boss boss)
    {
        if (boss.state == Constants.BossState.MOVING)
        {
            boss.speed = Constants.STEP_BOSS_FOLLOW;
        }
    }

/*
	if (enemyList.size() > 0: boss.eventType = MOVE_TO
*/

    public void CheckBossExplosion(Vector<Explosion> explosionList, int level)
    {
        if (level == 1)
        {
            Vector2 bossPos = Sprites.GetSpriteCenter(sprite);

            for (Explosion explosion : explosionList)
            {
                if (explosion.currentFrame > 12)
                {
                    if (Math.abs(bossPos.x - (explosion.pos.x + 120)) < 120 && (Math.abs(bossPos.y - (explosion.pos.y + 70)) < 120))
                        health -= Constants.DMG_ITEM[6];
                }
            }
        }
    }
}
