package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Game{

    Hero hero;
    float time;
    //RenderWindow  window;
    Viewport viewPort;
    Vector<Inventory> inventoryList;
    Vector<Loot> lootList;
    Vector<Shot> shotList;
    Vector<Enemy> zombieList;
    Vector<Npc> npcList;
    Vector<Explosion> explosionList;
    Vector<Throwing> throwingList;

    Constants.GameState state;

    BitmapFont font;
    //Text text;
/*
    Sprites sprites;
    AudioResources audio;

    Texture explosionTexture;
    Sprite explosionSprite;
*/
    TiledMap lvl;
    Vector<MapObjects> allObjects;
    Vector<MapObjects> solidObjects;

    //MiniMap miniMap;

    int level;
    Boss boss;

    void UpdateInventory()
    {
        //update items
        if (inventoryList.get(hero.slotNo).current == 0 && time - hero.lastReloadTime > hero.WEAPON_RELOAD_TIME)
        {
            hero.lastReloadTime = time;
            hero.isReloading = false;
            if (inventoryList.get(hero.slotNo).quantity >= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name])
            {
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).quantity -= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name];
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current = Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name];
            }
            else
            {
                inventoryList.set(hero.slotNo,inventoryList.get(hero.slotNo)).current = inventoryList.get(hero.slotNo).quantity;
                inventoryList.set(hero.slotNo,inventoryList.get(hero.slotNo)).quantity = 0;
            }
            if (inventoryList.get(hero.slotNo).quantity <= 0)
            {
                inventoryList.get(hero.slotNo).quantity = 0;
            }
        }
    };

    void CheckUsingItems(Sprite sprite_shot, Sprite sprite_grenade)
    {
        if (Keyboard::isKeyPressed(Keyboard::A) || Keyboard::isKeyPressed(Keyboard::S) || Keyboard::isKeyPressed(Keyboard::D)|| Keyboard::isKeyPressed(Keyboard::W))
        {
            if (state == BEAST)
            {
                if (time - lastAttackTime > HERO_BEAST_ATTACK_TIME)
                {
                    //lastAttackTime = game.time;  (reminder)
                    hero.isSoundBeastAtttack = true;
                    hero.isBeastAttack = true;
                    hero.currentFrame = 0;
                }
            }
            else if (state == NORMAL && inventoryList.get(hero.slotNo).current > 0)
            {
                if (time > shotLastTime + ITEM_REUSE_COOLDOWN[inventoryList.get(hero.slotNo).name])
                {
                    shotLastTime = time;
                    inventoryList[slotNo].current -= 1;
                    if (inventoryList.get(hero.slotNo).name == PISTOL || inventoryList.get(hero.slotNo).name == RIFLE)
                    {
                        if (Keyboard::isKeyPressed(Keyboard::A)) dirLast = LEFT;
                        if (Keyboard::isKeyPressed(Keyboard::S)) dirLast = DOWN;
                        if (Keyboard::isKeyPressed(Keyboard::D)) dirLast = RIGHT;
                        if (Keyboard::isKeyPressed(Keyboard::W)) dirLast = UP;
                        hero.isSoundShoot = true;
                        AddNewShot(shotList, dirLast, pos, time, sprite_shot, sprite_grenade, BULLET);
                    }
                    else if (inventoryList.get(hero.slotNo).name == DRINK)
                    {
                        hero.health += HP_PER_DRINK;
                        if (health > 100)
                        {
                            health = 100;
                        }
                    }
                    else if (inventoryList.get(hero.slotNo).name == MIXTURE)
                    {
                        state = TRANSFORMING;
                        hero.dir = NONE;
                        hero.dirLast = DOWN;
                    }
                    else if (inventoryList[slotNo].name == GRENADE)
                    {
                        AddNewShot(shotList, dirLast, pos, time, sprite_shot, sprite_grenade, USED_GRENADE);
                    }
                }
            }
        }
    };

    void DrawText(SpriteBatch batch, View view, Text text)
    {
        Vector2 posView = GetInterfacePosition(view);  //zametka

        text.setPosition(posView.x + 40, posView.y + 40);

        //currentItem << (iCurrent.item);
        //std::ostringstream toStringCurrent;

        //std::ostringstream toStringQuantity;


        text.setString(to_string(inventoryList.get(hero.slotNo).current) + "/" + to_string(inventoryList.get(hero.slotNo).quantity) + " " + ITEM_NAMES[inventoryList.get(hero.slotNo).name]);
        .draw(batch)(text);
        if (hero.isReloading)
        {
            text.setPosition(posView.x + 40, posView.y + 70);
            if (inventoryList.get(hero.slotNo).name == PISTOL || inventoryList.get(hero.slotNo).name == RIFLE)
            {
                text.setString("reloading");
            }
            else if (inventoryList.get(hero.slotNo).name == GRENADE)
            {
                //text.setString("pulling");
            }
            else
            {
                //text.setString("opening");
            }
            text.draw(batch);
        }

        if (maxNeighbors > 0)
        {
            text.setString("rescued: " + to_string(savedNeighbors));
            text.setPosition(posView.x + 5, posView.y + 100);
            text.draw(batch);

            int remaining = MAX_NUMBER_OF_NEIGHBORS - savedNeighbors;
            text.setString("remaining: " + to_string(remaining));
            text.setPosition(posView.x + 5, posView.y + 120);
            text.draw(batch);
        }
        else
        {
            text.setString("Kill Boss");
            text.setPosition(posView.x + 5, posView.y + 120);
            text.draw(batch);
        }
    }

    Inventory GetNewInventoryItem(Loot loot, Sprite items)
    {
        Inventory inventory;
        inventory.name = loot.name;
        inventory.quantity = loot.quantity;
        inventory.current = 0;
        inventory.sprite = items;
        inventory.sprite.setRegion(loot.name.ordinal() * 32, 0, 32, 32));
        return inventory;
    }


    void CheckLoot(Sprite items)
    {
        float x = hero.sprite.getX();
        float y = hero.sprite.getPosition().y;

        boolean isItemAlreadyIn = false;

        for (Loot lootItem : lootList)
        {
            if (lootItem.isDrawn == true)  //if loot.item.center contains heroSprite  -> add new item in inventory
            {
                Vector2 itemCenter = new Vector2();
                itemCenter.x = lootItem.pos.x + lootItem.sprite.getGlobalBounds().width / 2;
                itemCenter.y = lootItem.pos.y + lootItem.sprite.getGlobalBounds().height / 2;
                if (hero.sprite.getGlobalBounds().contains(itemCenter))
                {
                    hero.isSoundLoot = true;
                    if (lootItem.name != AMMO)  //any item that we can take
                    {
                        //check if this item exists in inventory, and if so - upload it
                        int itemIndex = GetSlotIndexOfItem(lootItem, inventoryList);
                        if (itemIndex >= 0)
                        {
                            inventoryList.set(itemIndex, inventoryList.get(itemIndex)).quantity += lootItem.quantity;
                            lootItem.isDrawn = false;
                            isItemAlreadyIn = true;
                        }
                        else //(isItemAlreadyIn == false) //adding new item to inventory List
                        {
                            Inventory inventory = GetNewInventoryItem(lootItem, items);
                            inventoryList.add(inventory);

                            lootItem.isDrawn = false;
                            isItemAlreadyIn = true;
                            hero.nSlots += 1;
                        }
                    }
                    else
                    {
                        lootItem.isDrawn = false;
                        int nWeaponAmmoAdded = 0;
                        while (nWeaponAmmoAdded < AMMO_PACKS)
                        {
                            for (Inventory itm : inventoryList)
                            {
                                if (itm.name != MIXTURE && itm.name != KEY && itm.name != DRINK)
                                {
                                    itm.quantity += Constants.MAX_AMMO[itm.name];
                                    nWeaponAmmoAdded += 1;
                                }
                            }
                            break;
                        }
                    }
                }
            }
            else
            {
                lootList.remove(lootItem);
            }
        }
    }
}
