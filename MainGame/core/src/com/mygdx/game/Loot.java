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

    void InitializeLoot(Vector<Loot> lootList, Vector<Object> objects, Sprite sprite)
    {
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 5, Constants.NameItem.DRINK, sprite);
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 3, Constants.NameItem.PISTOL, sprite);
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 2, Constants.NameItem.AMMO, sprite);
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 1, Constants.NameItem.RIFLE, sprite);
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 1, Constants.NameItem.MIXTURE, sprite);
        GenerateLoot(lootList, objects, new Vector2(Constants.WIDTH_MAP,Constants.HEIGHT_MAP), 2, Constants.NameItem.GRENADE, sprite);
    }


    void DrawLoot(SpriteBatch batch, Vector<Loot> lootList)
    {
        for (Loot item : lootList)
        {
            if (item.isDrawn == true)
                item.sprite.draw(batch);
        }
    };

    int GetSlotIndexOfItem(Loot out, Vector<Inventory> inventoryList)
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

    Loot GetNewLootItem(Constants.NameItem item, Sprite sprite, float x, float y)
    {
        Loot loot = new Loot();
        loot.name = item;
        //TODO: comm
        //loot.quantity = GetMaxQuantity(loot.name);
        loot.pos = new Vector2(x,y);
        loot.sprite = sprite;
        loot.sprite.setPosition(loot.pos.x, loot.pos.y);
        loot.sprite.setRegion(item.ordinal() * 32, 0, 32, 32);
        loot.isDrawn = true;
        return loot;
    }

    void GenerateLoot(Vector<Loot> lootList, Vector<Object> objects, Vector2 mapSize, int ItemsRemaining, Constants.NameItem  item, Sprite texture_items)
    {
        //TODO: MAKE FUNCTION MORE READABLE
        do
        {
            boolean needNewBlock = false;
            Vector2 newPos = new Vector2((float)((int)(Math.random() * (int)mapSize.x) * Constants.STEP_TILE), (float)(((int)(Math.random() * (int)(mapSize.y)) * Constants.STEP_TILE)));

            Rectangle lootRect = new Rectangle( newPos.x,newPos.y,texture_items.getWidth(),texture_items.getHeight());
            boolean isIntersected = false;
            for (Object object :objects)
            {
                //TODO: comm
                /*if (lootRect.overlaps(object.rect()))
                {
                    needNewBlock = true;
                    break;
                }*/
            }
            if (needNewBlock)
            {
                continue;
            }
            for (Loot lootItem : lootList)
            if (Math.abs(lootItem.pos.x - newPos.x) < 100 && Math.abs(lootItem.pos.y - newPos.y) < 100)
            {
                needNewBlock = true;
                break;
            }
            if (needNewBlock == false)
            {
                Loot loot = GetNewLootItem(item, texture_items, newPos.x, newPos.y);
                lootList.add(loot);
                ItemsRemaining -= 1;
            }
        } while (ItemsRemaining > 0);
    }

    void DeleteLoot(Vector<Loot> loots)
    {
        loots.clear();
    }
}
