package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Explosion {
    Vector2 pos;
    float currentFrame;
    Sprite sprite;

    void DeleteExplosionList(Vector<Explosion> explosions)
    {
        explosions.clear();
    }
}

