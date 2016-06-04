package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 5, DRINK, sprite);
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 3, PISTOL, sprite);
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 2, AMMO, sprite);
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 1, RIFLE, sprite);
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 1, MIXTURE, sprite);
        GenerateLoot(lootList, objects, { WIDTH_MAP,HEIGHT_MAP }, 2, GRENADE, sprite);
    }


    void DrawLoot(SpriteBatch batch, Vector<Loot> lootList)
    {
        for (Loot item : lootList)
        {
            if (item.isDrawn == true)
                batch.draw(item.sprite);
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
        Loot loot;
        loot.name = item;
        loot.quantity = GetMaxQuantity(loot.name);
        loot.pos = new Vector2(x,y);
        loot.sprite = sprite;
        loot.sprite.setPosition(loot.pos);
        loot.sprite.setTextureRect(sf::IntRect(item.ordinal() * 32, 0, 32, 32));
        loot.isDrawn = true;
        return loot;
    }

    void GenerateLoot(Vector<Loot> lootList, Vector<Object> objects, Vector2 mapSize, int ItemsRemaining, NameItem  item, Sprite texture_items)
    {
        //TODO: MAKE FUNCTION MORE READABLE
        do
        {
            boolean needNewBlock = false;
            Vector2 newPos = { (float)((rand() % (int)mapSize.x) * STEP_TILE), (float)((rand() % (int)(mapSize.y)) * STEP_TILE) };

            TextureRegion lootRect = { newPos.x,newPos.y,texture_items.getGlobalBounds().height,texture_items.getGlobalBounds().height };
            boolean isIntersected = false;
            for (Object object :objects)
            {
                if (lootRect.intersects(object.rect))
                {
                    needNewBlock = true;
                    break;
                }
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
