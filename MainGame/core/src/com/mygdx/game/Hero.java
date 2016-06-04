package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        ;
        sprite.setTexture(Sprites.texture_hero);
        //sprite.setRegion(4, 4, 32, 32));
        dir = Constants.Direction.NONE;
        dirLast = Constants.Direction.DOWN;
        currentFrame = 0;
        sprite.setPosition(6 * 32, 12 * 32);  //start position
        state = Constants.HeroState.NORMAL;
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
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))  dir = Constants.Direction.UPRIGHT;
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  dir = Constants.Direction.UPLEFT;
            else dir = Constants.Direction.UP;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dir = Constants.Direction.DOWNLEFT;
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dir = Constants.Direction.DOWNRIGHT;
            else dir = Constants.Direction.DOWN;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dir = Constants.Direction.DOWNLEFT;
            else dir = Constants.Direction.LEFT;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dir = Constants.Direction.DOWNRIGHT;
            else dir = Constants.Direction.RIGHT;
        }
        else dir = Constants.Direction.NONE;
    };

    boolean IsInventorySwitch()
    {
        boolean isSwitch = false;
        if (isWeaponSwitch == false)
        {
            if (Gdx.input.isKeyPressed(Input.Keys.X))
            {
                isWeaponSwitch = true;
                slotNo += 1;
                if (slotNo >= nSlots) slotNo = 0;
                isSwitch = true;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.Z))
            {
                isWeaponSwitch = true;
                slotNo -= 1;
                if (slotNo < 0) slotNo = nSlots - 1;
                isSwitch = true;
            }
        }
        else if ((Gdx.input.isKeyPressed(Input.Keys.X) || Gdx.input.isKeyPressed(Input.Keys.Z)) == false)
        isWeaponSwitch = false;
        return isSwitch;
    };


    void UpdateHeroFrame(float time)
    {
        if (state == Constants.HeroState.TRANSFORMING)
        {
            sprite.setRegion(11 + 37 * (int) currentFrame, 484, 32, 45);
            currentFrame += 0.05f;

            if (currentFrame > 7)
            {
                state = Constants.HeroState.BEAST;
                beastTimer = time;
                currentFrame = 0;
                damageResistance = Constants.HERO_BEAST_DAMAGE_RESISTANCE;
            }
        }
        else if (state == Constants.HeroState.BEAST)
        {
            if (isBeastAttack)  //attacking beast animation
            {
                switch (dirLast)
                {
                    case UP:
                        sprite.setRegion(355 + 40 * (int) currentFrame, 597, 34, 47);
                        break;
                    case UPRIGHT: case RIGHT: case DOWNRIGHT:
                    sprite.setRegion(182 + 57 * (int) currentFrame, 598, 48, 52);
                    break;
                    case DOWN:
                        sprite.setRegion(42 + 35 * (int) currentFrame, 595, 35, 54);
                        break;
                    case DOWNLEFT: case LEFT: case UPLEFT:
                    sprite.setRegion(492 + 56 * (int) currentFrame, 601, 54, 52);
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
                        sprite.setRegion(326 + 40 * (int) currentFrame, 537, 37, 47);
                        break;
                    case UPRIGHT: case RIGHT: case DOWNRIGHT:
                    //21 38
                    sprite.setRegion(163 + 40 * (int) currentFrame, 537, 37, 47);
                    break;
                    case DOWN:
                        sprite.setRegion(9 + 38 * (int) currentFrame, 537, 37, 47);
                        break;
                    case DOWNLEFT: case LEFT: case UPLEFT:
                    sprite.setRegion(480 + 40 * (int) currentFrame, 537, 37, 47);
                    break;
                    case NONE:
                        if (dirLast == Constants.Direction.UP)
                        {
                            sprite.setRegion(270 + 76, 485, 36, 46);
                        }
                        else if (dirLast == Constants.Direction.RIGHT)
                        {
                            sprite.setRegion(270 + 39, 485, 36, 46);
                        }
                        else if (dirLast == Constants.Direction.DOWN)
                        {
                            sprite.setRegion(270, 485, 36, 46);
                        }
                        else if (dirLast == Constants.Direction.LEFT)
                        {
                            sprite.setRegion(270 + 114, 485, 36, 46);
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
        else if (state == Constants.HeroState.NORMAL)  //normal moving animation
        {
            switch (dir)
            {
                case UP:
                    sprite.setRegion(105 + 22 * (int) currentFrame, 84, 21, 37);
                    break;
                case UPRIGHT: case RIGHT: case DOWNRIGHT:
                //21 38
                sprite.setRegion(105 + 22 * (int) currentFrame, 44, 21, 37);
                break;
                case DOWN:
                    sprite.setRegion(105 + 22 * (int) currentFrame, 4, 21, 37);
                    break;
                case DOWNLEFT: case LEFT: case UPLEFT:
                sprite.setRegion(105 + 22 * (int) currentFrame, 124, 21, 37);
                break;
                case NONE:
                    if (dirLast == Constants.Direction.UP)
                    {
                        sprite.setRegion(86, 85, 21, 36);
                    }
                    else if (dirLast == Constants.Direction.RIGHT)
                    {
                        sprite.setRegion(86, 45, 21, 36);
                    }
                    else if (dirLast == Constants.Direction.DOWN)
                    {
                        sprite.setRegion(86, 5, 21, 36);
                    }
                    else if (dirLast == Constants.Direction.LEFT)
                    {
                        sprite.setRegion(86, 125, 21, 36);
                    }
                    break;
            }

            currentFrame += 0.2f;
            if (currentFrame > 4)
            {
                currentFrame = 0;
            }
        }
        else if (state == Constants.HeroState.DAMAGED)
        {
            sprite.setRegion(10 + 32 * (int) currentFrame, 179, 32, 45);
            currentFrame += 0.06f;
            if (currentFrame > 2)
            {
                currentFrame = 0;
                state = Constants.HeroState.NORMAL;
            }
        }
        else if (state == Constants.HeroState.SMASHED)
        {
            sprite.setRegion(235, 299, 70, 51);
            currentFrame = 0;
            if (lastSmashTime + Constants.HERO_SMASH_DURATION < time)
            {
                state = Constants.HeroState.NORMAL;
                isSmashed = false;
                //21.36
                //sprite.setPosition()
            }
        }
        if (dir != Constants.Direction.NONE)
        {
            dirLast = dir;  //update dirLast (for shooting)
            if (dir != dirLast)
            {
                currentFrame = 0;
            }
        }
    };

    void DrawHero(SpriteBatch batch, Sprite  hero){
        hero.draw(batch);
    };



    
}
