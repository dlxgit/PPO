package com.mygdx.game;

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



    void UpdateThrowingFrame(Throwing throwing)
    {
        if (throwing.name != "milk")
        {
            throwing.sprite.setTextureRect(IntRect(0 + 15 * (int)throwing.currentFrame, 0, 16, 16));
            throwing.currentFrame += 0.6f;
            if (throwing.currentFrame > 4)
            {
                throwing.currentFrame = 0;
            }
        }
    }

    boolean IsMilkOnGround(Throwing throwing)
    {
        Vector2 throwPos = throwing.sprite.getPosition();
        if (Math.abs(throwPos.x - throwing.endPos.x) < 10 && Math.abs(throwPos.y - throwing.endPos.y) < 10)
        {
            return true;
        }
        return false;
    }

    void UpdateThrowingPosition(Throwing throwing)
    {
        Vector2 pos = throwing.sprite.getPosition();
        throwing.sprite.setPosition(pos.x + throwing.stepPerLoop.x, pos.y + throwing.stepPerLoop.y);
    }

    void DrawThrowings(SpriteBatch batch, Vector<Throwing> throwingList)
    {
        for (Throwing throwing : throwingList)
        {
            batch.draw(throwing.sprite);
        }
    }

    void DeleteThrowings(Vector<Throwing> throwingList) {
        throwingList.clear();
    }
}