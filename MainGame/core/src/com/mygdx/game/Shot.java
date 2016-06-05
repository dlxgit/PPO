package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Shot {
    Vector2 pos;
    int distance;
    Constants.Direction dir;
    Sprite sprite;
    Constants.ShotType type;
    boolean isExploded;
    float startTime;
    float currentFrame;


}
