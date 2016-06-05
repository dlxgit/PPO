package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */

public class Loot {
    Constants.NameItem name;
    int quantity;
    Vector2 pos;
    Texture texture;
    Sprite sprite;
    boolean isDrawn;

    public Vector2 GetPosition()
    {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public int GetSlotIndexOfItem(Loot out, Vector<Inventory> inventoryList)
    {
        int index = 0;
        for (Inventory in : inventoryList)
        {
            if (in.name == out.name)
            {
                return index;
            }
            index++;
        }
        return -1;
    }

    public void DeleteLoot(Vector<Loot> loots)
    {
        loots.clear();
    }
}
