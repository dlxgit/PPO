package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Throwing {
    Texture texture;
    Sprite sprite;
    String name;
    Constants.Direction dir;
    Vector2 stepPerLoop;
    Vector2 startPos;
    float startTime;
    float maxTime;
    float cooldown;
    //float maxDistance;
    //float currentTime;
    boolean isEnd;
    float currentFrame;

    Vector2 endPos;



    public void UpdateThrowingFrame()
    {
        if (name != "milk")
        {
            sprite.setRegion(0 + 15 * (int)currentFrame, 0, 16, 16);
            currentFrame += 0.6f;
            if (currentFrame > 4)
            {
                currentFrame = 0;
            }
        }
    }

    public boolean IsMilkOnGround()
    {
        Vector2 throwPos = new Vector2(sprite.getX(), sprite.getY());
        if (Math.abs(throwPos.x - endPos.x) < 10 && Math.abs(throwPos.y - endPos.y) < 10)
        {
            return true;
        }
        return false;
    }

    public void UpdateThrowingPosition()
    {
        Vector2 pos = new Vector2(sprite.getX(), sprite.getY());
        sprite.setPosition(pos.x + stepPerLoop.x, pos.y + stepPerLoop.y);
    }
}
