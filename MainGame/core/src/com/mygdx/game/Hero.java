package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Hero {

    Vector2 pos;
    Texture texture;
    Sprite sprite;
    Constants.Direction dir;
    Constants.Direction dirLast;
    int slotNo;
    int nSlots;        //number of slots in inventory
    int health;
    float currentFrame;
    Constants.HeroState state;
    boolean isBeastAttack;
    float beastTimer;
    float lastAttackTime;
    boolean isAmmo;
    float lastReloadTime;
    boolean isReloading;
    int savedNeighbors;
    int maxNeighbors;
    boolean isWeaponSwitch;
    float shotLastTime;
    Inventory item;
    float damageResistance;

    boolean isSmashed;  //for boss
    float lastSmashTime;

    boolean isSoundShoot;
    boolean isSoundBeastAtttack;
    boolean isSoundLoot;
    boolean isSoundNpcSurvive;
    boolean isSoundTakeDamage;
    boolean isSoundEnemyExplosion;
    boolean isSoundNpcDeath;


    void create()
    {
        slotNo = 0;
        nSlots = 1;
        health = 100;
        Texture.loadFromFile("resources/images/png");
        sprite.setTexture(texture);
        //sprite.setTextureRect(IntRect(4, 4, 32, 32));
        dir = NONE;
        dirLast = DOWN;
        currentFrame = 0;
        sprite.setPosition(6 * 32, 12 * 32);  //start position
        state = NORMAL;
        isBeastAttack = false;
        isWeaponSwitch = false;
        lastAttackTime = 0;
        lastReloadTime = 0;
        savedNeighbors = 0;
        damageResistance = 1;
        isSmashed = false;

        isSoundBeastAtttack = false;
        isSoundLoot = false;
        isSoundNpcSurvive = false;
        isSoundShoot = false;
        isSoundTakeDamage = false;
        isSoundEnemyExplosion = false;
    };

    void updateDirection()
    {
        //update hero direction
        if (Keyboard::isKeyPressed(Keyboard::Up))
        {
            if (Keyboard::isKeyPressed(Keyboard::Right))  dir = UPRIGHT;
            else if (Keyboard::isKeyPressed(Keyboard::Left))  dir = UPLEFT;
            else dir = UP;
        }
        else if (Keyboard::isKeyPressed(Keyboard::Down))
        {
            if (Keyboard::isKeyPressed(Keyboard::Left)) dir = DOWNLEFT;
            else if (Keyboard::isKeyPressed(Keyboard::Right)) dir = DOWNRIGHT;
            else dir = DOWN;
        }
        else if (Keyboard::isKeyPressed(Keyboard::Left))
        {
            if (Keyboard::isKeyPressed(Keyboard::Down)) dir = DOWNLEFT;
            else dir = LEFT;
        }
        else if (Keyboard::isKeyPressed(Keyboard::Right))
        {
            if (Keyboard::isKeyPressed(Keyboard::Down)) dir = DOWNRIGHT;
            else dir = RIGHT;
        }
        else dir = NONE;
    };

    boolean IsInventorySwitch()
    {
        boolean isSwitch = false;
        if (isWeaponSwitch == false)
        {
            if (Keyboard::isKeyPressed(Keyboard::X))
            {
                isWeaponSwitch = true;
                slotNo += 1;
                if (slotNo >= nSlots) slotNo = 0;
                isSwitch = true;
            }
            else if (Keyboard::isKeyPressed(Keyboard::Z))
            {
                isWeaponSwitch = true;
                slotNo -= 1;
                if (slotNo < 0) slotNo = nSlots - 1;
                isSwitch = true;
            }
        }
        else if ((Keyboard::isKeyPressed(Keyboard::X) || Keyboard::isKeyPressed(Keyboard::Z)) == false)
        isWeaponSwitch = false;
        return isSwitch;
    };




    void UpdateHeroFrame(float time)
    {
        if (state == TRANSFORMING)
        {
            sprite.setTextureRect(IntRect(11 + 37 * (int)currentFrame, 484, 32, 45));
            currentFrame += 0.05f;

            if (currentFrame > 7)
            {
                state = BEAST;
                beastTimer = time;
                currentFrame = 0;
                damageResistance = HERO_BEAST_DAMAGE_RESISTANCE;
            }
        }
        else if (state == BEAST)
        {
            if (isBeastAttack)  //attacking beast animation
            {
                switch (dirLast)
                {
                    case UP:
                        sprite.setTextureRect(IntRect(355 + 40 * (int)currentFrame, 597, 34, 47));
                        break;
                    case UPRIGHT: case RIGHT: case DOWNRIGHT:
                    sprite.setTextureRect(IntRect(182 + 57 * (int)currentFrame, 598, 48, 52));
                    break;
                    case DOWN:
                        sprite.setTextureRect(IntRect(42 + 35 * (int)currentFrame, 595, 35, 54));
                        break;
                    case DOWNLEFT: case LEFT: case UPLEFT:
                    sprite.setTextureRect(IntRect(492 + 56 * (int)currentFrame, 601, 54, 52));
                    break;
                    case NONE:
                        break;
                }
                currentFrame += 0.2f;
                if (currentFrame > 2)
                {
                    //TODO: deal damage after this
                    isBeastAttack = false;
                    currentFrame = 0;
                }
            }
            else  //moving beast animation
            {
                switch (dir)
                {
                    case UP:
                        sprite.setTextureRect(IntRect(326 + 40 * (int)currentFrame, 537, 37, 47));
                        break;
                    case UPRIGHT: case RIGHT: case DOWNRIGHT:
                    //21 38
                    sprite.setTextureRect(IntRect(163 + 40 * (int)currentFrame, 537, 37, 47));
                    break;
                    case DOWN:
                        sprite.setTextureRect(IntRect(9 + 38 * (int)currentFrame, 537, 37, 47));
                        break;
                    case DOWNLEFT: case LEFT: case UPLEFT:
                    sprite.setTextureRect(IntRect(480 + 40 * (int)currentFrame, 537, 37, 47));
                    break;
                    case NONE:
                        if (dirLast == UP)
                        {
                            sprite.setTextureRect(IntRect(270 + 76, 485, 36, 46));
                        }
                        else if (dirLast == RIGHT)
                        {
                            sprite.setTextureRect(IntRect(270 + 39, 485, 36, 46));
                        }
                        else if (dirLast == DOWN)
                        {
                            sprite.setTextureRect(IntRect(270, 485, 36, 46));
                        }
                        else if (dirLast == LEFT)
                        {
                            sprite.setTextureRect(IntRect(270 + 114, 485, 36, 46));
                        }
                        break;
                }

                currentFrame += 0.2f;

                if (currentFrame > 3)
                {
                    currentFrame = 0;
                }
            }
        }
        else if (state == NORMAL)  //normal moving animation
        {
            switch (dir)
            {
                case UP:
                    sprite.setTextureRect(IntRect(105 + 22 * (int)currentFrame, 84, 21, 37));
                    break;
                case UPRIGHT: case RIGHT: case DOWNRIGHT:
                //21 38
                sprite.setTextureRect(IntRect(105 + 22 * (int)currentFrame, 44, 21, 37));
                break;
                case DOWN:
                    sprite.setTextureRect(IntRect(105 + 22 * (int)currentFrame, 4, 21, 37));
                    break;
                case DOWNLEFT: case LEFT: case UPLEFT:
                sprite.setTextureRect(IntRect(105 + 22 * (int)currentFrame, 124, 21, 37));
                break;
                case NONE:
                    if (dirLast == UP)
                    {
                        sprite.setTextureRect(IntRect(86, 85, 21, 36));
                    }
                    else if (dirLast == RIGHT)
                    {
                        sprite.setTextureRect(IntRect(86, 45, 21, 36));
                    }
                    else if (dirLast == DOWN)
                    {
                        sprite.setTextureRect(IntRect(86, 5, 21, 36));
                    }
                    else if (dirLast == LEFT)
                    {
                        sprite.setTextureRect(IntRect(86, 125, 21, 36));
                    }
                    break;
            }

            currentFrame += 0.2f;
            if (currentFrame > 4)
            {
                currentFrame = 0;
            }
        }
        else if (state == DAMAGED)
        {
            sprite.setTextureRect(IntRect(10 + 32 * (int)currentFrame, 179, 32, 45));
            currentFrame += 0.06f;
            if (currentFrame > 2)
            {
                currentFrame = 0;
                state = NORMAL;
            }
        }
        else if (state == SMASHED)
        {
            sprite.setTextureRect(IntRect(235, 299, 70, 51));
            currentFrame = 0;
            if (lastSmashTime + HERO_SMASH_DURATION < time)
            {
                state = NORMAL;
                isSmashed = false;
                //21.36
                //sprite.setPosition()
            }
        }
        if (dir != NONE)
        {
            dirLast = dir;  //update dirLast (for shooting)
            if (dir != dirLast)
            {
                currentFrame = 0;
            }
        }
    };

    void DrawHero(SpriteBatch batch, Sprite  hero){
        batch.draw(hero);
    };



    
}
