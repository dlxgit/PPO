package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

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



    void InitializeBoss(Boss boss, Sprites sprites, float time)
    {
        boss.sprite = sprites.boss;
        boss.spawnTime = time;
        boss.health = BOSS_MAX_HEALTH;
        boss.currentFrame = 0;
        boss.isEvent = false;
        boss.isAttack = false;
        boss.eventCount = 0;
        boss.spawnedEnemies = 0;
        boss.currentFrame = 0;
        boss.state = SPAWNING;

        boss.dir = LEFT;

        //boss.sprite.setRegion(11, 13, 106, 154));
        boss.sprite.setPosition(14 * 64, 5 * 64);

        boss.sprite.setRegion(11, 189, 106, 154);

        boss.lastSide = LEFT;
        boss.followForShootTime = 0;
        boss.isFollow = true;

        //
        boss.speed = STEP_BOSS;

        boss.eventCount = 0;
        boss.eventstartTime = time;

        boss.wasCharged = false;

        boss.bar = sprites.boss_bar;
        boss.bar.setPosition(BOSS_BAR_POSITION);
        boss.indicator = sprites.boss_indicator;
        boss.indicator.setPosition(BOSS_INDICATOR_POSITION);
        boss.isCommonZombie = true;
    }

    void UpdateBossFrame(Boss boss)
    {
        //11 383
        if (boss.eventType == STOMP_FOR_ZOMBIES)
        {
            if (boss.currentFrame > 4)
            {
                boss.currentFrame = 0;
            }
            if (boss.lastSide == LEFT)
            {
                boss.sprite.setRegion(11 + 105 * (int)boss.currentFrame, 13, 106, 154);
            }
            else
            {
                boss.sprite.setRegion(11 + 105 * (int)boss.currentFrame, 189, 106, 154);
            }
            boss.currentFrame += 0.1f;
        }
        else if (boss.eventType != SHOOT)
        {
            switch (boss.dir)
            {
                case UP: case DOWN:
                if (boss.lastSide == LEFT)
                {
                    boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 13, 106, 154);
                }
                else
                {
                    boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 189, 106, 154);
                }
                break;
                case UPRIGHT: case RIGHT: case DOWNRIGHT:
                boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 189, 106, 154);
                break;
                case DOWNLEFT: case LEFT: case UPLEFT:
                boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 13, 106, 154);
                break;
                default:
                    break;
            }
            boss.currentFrame += 0.07f;
            if (boss.eventType == CHARGE)
            {
                boss.currentFrame += 0.2f;
            }
            if (boss.currentFrame > 4)
            {
                boss.currentFrame = 0;
            }
        }
        else
        {
            if (boss.lastSide == LEFT)
            {
                boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 383, 106, 154);
            }
            else
            {
                boss.sprite.setRegion(11 + 105 * (int) boss.currentFrame, 572, 106, 154));
            }
            if (boss.currentFrame > 2 && boss.isAttack == false)
            {
                boss.isAttack = true;
            }
            boss.currentFrame += 0.2f;
            if (boss.currentFrame >= 3)
            {
                boss.currentFrame = 1;
            }
            //392 404 42 19*throwing.currentFrame
        }
    }

    boolean IsReadyToShoot(Boss boss, float time)
    {
        if (boss.eventType == SHOOT)
        {
            if (boss.lastShootTime + BOSS_SHOOTING_COOLDOWN < time)
            {
                return true;
            }
        }
        return false;
    }

    boolean IsBossNearMapCenter(Sprite boss)
    {
        Vector2 pos = GetSpriteCenter(boss);
        //cout << "BOSS POS " << pos.x << " " << pos.y << " TO " << LEVEL1_CENTER_POS.x << " " << LEVEL1_CENTER_POS.y << " CHECK " << abs(pos.x - LEVEL1_CENTER_POS.x + 32) << " " << abs(pos.y - LEVEL1_CENTER_POS.y + 32) << endl;

        if (Math.abs(pos.x - LEVEL1_CENTER_POS.x) <= 15.f && Math.abs(pos.y - LEVEL1_CENTER_POS.y) <= 15.f)
        {
            return true;
        }
        return false;
    }

    void ComputeBossDirection(Boss boss, TextureRegion rect, Sprite hero)
    {
        Vector2 heroPos = GetSpriteCenter(hero);
        //Vector2f bossPos = { rect.left + rect.width / 2.f, rect.top + rect.height / 2.f };
        Vector2 bossPos = GetSpriteCenter(boss.sprite);

        if (boss.eventType == STOMP_FOR_ZOMBIES)
        {
            boss.dir = NONE;
        }
        else if (boss.eventType == SHOOT)
        {
            boss.dir = NONE;
            if (heroPos.x <= bossPos.x)
            {
                boss.lastSide = LEFT;
            }
            else boss.lastSide = RIGHT;
        }
        else
        {
            if (boss.eventType == MOVE_OUT)
            {
                heroPos = LEVEL1_CENTER_POS;//{ 12 * 64, 6 * 64 };
            }

            Constants.Direction dirOld = boss.dir;
            Vector2 distance = new Vector2(Math.abs(heroPos.x - bossPos.x), Math.abs(heroPos.y - bossPos.y));

            if (distance.x > 5 || distance.y > 5)
            {
                //TODO: check left-right dir zombie sprite bug (almost)
                if ((distance.x > 3 && distance.y > 3) && (distance.x / distance.y > 0.9) && (distance.y / distance.x < 1.1))
                {
                    if (heroPos.x >= bossPos.x && heroPos.y >= bossPos.y)
                        boss.dir = DOWNRIGHT;
                    else if (heroPos.x >= bossPos.x && heroPos.y < bossPos.y)
                        boss.dir = UPRIGHT;
                    else if (heroPos.x < bossPos.x && heroPos.y >= bossPos.y)
                        boss.dir = DOWNLEFT;
                    else if (heroPos.x < bossPos.x && heroPos.y < bossPos.y)
                        boss.dir = UPLEFT;
                }
                else if (distance.x >= distance.y)
                {
                    if (heroPos.x > bossPos.x)
                        boss.dir = RIGHT;
                    else
                        boss.dir = LEFT;
                }
                else if (distance.x < distance.y)
                {
                    if (heroPos.y > bossPos.y)
                        boss.dir = DOWN;
                    else
                        boss.dir = UP;
                }
            }
            if ((boss.lastSide == LEFT) && (boss.dir == UPRIGHT || boss.dir == RIGHT || boss.dir == DOWNRIGHT))
            {
                boss.lastSide = RIGHT;
            }
            else if ((boss.lastSide == RIGHT) && (boss.dir == UPLEFT || boss.dir == LEFT || boss.dir == DOWNLEFT))
            {
                boss.lastSide = LEFT;
            }
        }
    }

    TextureRegion GetBossCollisionRect(Sprite sprite)
    {
        TextureRegion bounds = sprite.getGlobalBounds();
        return TextureRegion(bounds.left, bounds.top + 50.f, bounds.width, bounds.height - 50.f);
    }

    boolean IsReachedHero(Sprite hero, Sprite boss)
    {
        Vector2 heroPos = GetSpriteCenter(hero);
        Vector2 bossPos = GetSpriteCenter(boss);

        //cout << "DIFF " << bossPos.x - heroPos.x << " " << bossPos.y + 10.f - heroPos.y << endl;
        if (Math.abs(bossPos.x - heroPos.x) < 15.f && Math.abs(bossPos.y + 10.f - heroPos.y) < 15.f)
        {
            return true;
        }
        return false;
    }

    boolean IsFootRectIntersectWithHero(Sprite hero, TextureRegion boss)
    {
        if (hero.getGlobalBounds().intersects(boss))
            return true;
        return false;
    }

    boolean IsBossAbleToShoot(Sprite hero, Sprite boss)
    {
        Vector2 heroPos = GetSpriteCenter(hero);
        Vector2 bossPos = GetSpriteCenter(boss);

        //cout << "DIFF " << bossPos.x - heroPos.x << " " << bossPos.y + 10.f - heroPos.y << endl;
        //cout << "ABILITY " << bossPos.x - heroPos.x << " " << heroPos.y - bossPos.y << " " << endl;
        if (Math.abs(bossPos.x - heroPos.x) < 130.f && (heroPos.y - bossPos.y < 100.f) && (heroPos.y - bossPos.y >= 0))
        {
            return true;
        }
        return false;

    }

    void UpdateBossMoveSpeed(Boss boss)
    {
        if (boss.state == MOVING)
        {
            boss.speed = STEP_BOSS_FOLLOW;
        }
    }

/*
	if (enemyList.size() > 0: boss.eventType = MOVE_TO
*/

    void CheckBossExplosion(Boss boss, Vector<Explosion> explosionList, int level)
    {
        if (level == 1)
        {
            Vector2 bossPos = GetSpriteCenter(boss.sprite);

            for (Explosion explosion : explosionList)
            {
                if (explosion.currentFrame > 12)
                {
                    if (Math.abs(bossPos.x - (explosion.pos.x + 120)) < 120 && (Math.abs(bossPos.y - (explosion.pos.y + 70)) < 120))
                        boss.health -= DMG_ITEM[6];
                }
            }
        }
    }

    void DrawBossBar(SpriteBatch batch, Boss boss, Vector2 viewPos)
    {
        if (boss.health > 0)
        {
            boss.indicator.setRegion(0, 0, 246 * (int) ((float) (boss.health) / (float) BOSS_MAX_HEALTH), 8);
        }
        boss.bar.setPosition(BOSS_BAR_POSITION.x + viewPos.x - WINDOW_SIZE.x / 2.f, BOSS_BAR_POSITION.y + viewPos.y - WINDOW_SIZE.y / 2.f);
        boss.indicator.setPosition(BOSS_INDICATOR_POSITION.x + viewPos.x - WINDOW_SIZE.x / 2.f, BOSS_INDICATOR_POSITION.y + viewPos.y - WINDOW_SIZE.y / 2.f);
        boss.bar.draw(batch);
        boss.indicator.draw(batch);
    }

    void DrawBoss(SpriteBatch batch, Sprite boss) {
        boss.draw(batch);
    }
}
