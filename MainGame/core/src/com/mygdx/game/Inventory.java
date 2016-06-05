package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Inventory {
    Constants.NameItem name;
    int quantity;
    int current;
    Texture texture;
    Sprite sprite;

    /*struct Grenade
    {
        Vector2f startPos;
        Vector2f distance;
        float startTime;
        bool isExploded;
    };
    */
    int GetMaxQuantity(Constants.NameItem name)
    {
        switch (name)
        {
            case DRINK:
                return 1;
            case PISTOL:
                return 12;
            case RIFLE:
                return 30;
            case AMMO:
                return 1;
            case KEY:
                return 1;
            case MIXTURE:
                return 1;
            case GRENADE:
                return 1;
            case SODA:
                return 10;
        }
        return 0;
    }


    void DeleteInventory(Vector<Inventory> inventory)
    {
        inventory.clear();
    }
}
