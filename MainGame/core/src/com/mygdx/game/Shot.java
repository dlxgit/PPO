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

    void AddNewShot(Vector<Shot>  shotList, Constants.Direction dirLast, Vector2 pos, float time, Sprite sprite_shot, Sprite sprite_grenade, ShotType shotType) //adding new shot in list
    {
        Shot shot;
        switch (dirLast)
        {
            case UP:
                shot.dir = UP;
                break;
            case DOWN:
                shot.dir = DOWN;
                break;
            case UPLEFT: case LEFT: case DOWNLEFT:
            shot.dir = LEFT;
            break;
            case UPRIGHT: case RIGHT: case DOWNRIGHT:
            shot.dir = RIGHT;
            break;
        }

        Texture texture;

        if (shotType == BULLET)
        {
            //shot.pos = hero.sprite.getPosition();
            shot.distance = 0;
            shot.sprite = sprite_shot;
            shot.type = shotType;
            shot.sprite.setPosition(pos.x, pos.y);
            switch (shot.dir)
            {
                case UP:
                    shot.sprite.setRegion(2, 5, 3, 7);
                    break;
                case RIGHT:
                    shot.sprite.setRegion(0, 0, 7, 3);
                    shot.sprite.setPosition(pos.x, pos.y + 10.f);
                    break;
                case DOWN:
                    shot.sprite.setRegion(10, 5, 3, 7);
                    break;
                case LEFT:
                    shot.sprite.setRegion(8, 0, 7, 3);
                    shot.sprite.setPosition(pos.x, pos.y + 10.f);
                    break;
            }
        }
        else
        {
            //shot.pos = hero.sprite.getPosition();
            shot.distance = 0;
            shot.sprite = sprite_grenade;
            shot.type = shotType;
            shot.startTime = time;
            Texture texture;
            //AddNewShot(hero, sprite_shot, game);
            //shot.sprite.setPosition(hero.sprite.getPosition());
            shot.currentFrame = 0;
            shot.isExploded = false;
            shot.sprite.setPosition(pos.x, pos.y);
        }
        shot.pos = shot.sprite.getPosition();

        //cout << "SHOTEEEEEE " << pos.x << " " << pos.y  << endl;
        shotList.add(shot);
    };

    void DeleteShots(Vector<Shot> shots)
    {
        shots.clear();
    }
}
