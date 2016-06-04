package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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



    public void AddNewShot(Constants.Direction dirLast, Sprite sprite_shot, Sprite sprite_grenade, Constants.ShotType shotType) //adding new shot in list
    {
        Shot shot = new Shot();
        switch (dirLast)
        {
            case UP:
                shot.dir = Constants.Direction.UP;
                break;
            case DOWN:
                shot.dir = Constants.Direction.DOWN;
                break;
            case UPLEFT: case LEFT: case DOWNLEFT:
            shot.dir = Constants.Direction.LEFT;
            break;
            case UPRIGHT: case RIGHT: case DOWNRIGHT:
            shot.dir = Constants.Direction.RIGHT;
            break;
        }

        Texture texture;

        if (shotType == Constants.ShotType.BULLET)
        {
            //shot.pos = hero.sprite.getPosition();
            shot.distance = 0;
            shot.sprite = sprite_shot;
            shot.type = shotType;
            shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY());
            switch (shot.dir)
            {
                case UP:
                    shot.sprite.setRegion(2, 5, 3, 7);
                    break;
                case RIGHT:
                    shot.sprite.setRegion(0, 0, 7, 3);
                    shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY()  + 10.f);
                    break;
                case DOWN:
                    shot.sprite.setRegion(10, 5, 3, 7);
                    break;
                case LEFT:
                    shot.sprite.setRegion(8, 0, 7, 3);
                    shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY()+ 10.f);
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
            Texture textureR;
            //AddNewShot(hero, sprite_shot, game);
            //shot.sprite.setPosition(hero.sprite.getPosition());
            shot.currentFrame = 0;
            shot.isExploded = false;
            shot.sprite.setPosition(hero.sprite.getX(), hero.sprite.getY());
        }
        shot.pos = new Vector2(shot.sprite.getX(), shot.sprite.getY());

        //cout << "SHOTEEEEEE " << pos.x << " " << pos.y  << endl;
        shotList.add(shot);
    };

    void UpdateInventory()
    {
        //update items
        if (inventoryList.get(hero.slotNo).current == 0 && time - hero.lastReloadTime > Constants.WEAPON_RELOAD_TIME)
        {
            hero.lastReloadTime = time;
            hero.isReloading = false;
            if (inventoryList.get(hero.slotNo).quantity >= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()])
            {
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).quantity -= Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()];
                inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current = Constants.MAX_AMMO[inventoryList.get(hero.slotNo).name.ordinal()];
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
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.D)|| Gdx.input.isKeyPressed(Input.Keys.W))
        {
            if (hero.state == Constants.HeroState.BEAST)
            {
                if (time - hero.lastAttackTime > Constants.HERO_BEAST_ATTACK_TIME)
                {
                    //lastAttackTime = game.time;  (reminder)
                    hero.isSoundBeastAtttack = true;
                    hero.isBeastAttack = true;
                    hero.currentFrame = 0;
                }
            }
            else if (hero.state == Constants.HeroState.NORMAL && inventoryList.get(hero.slotNo).current > 0)
            {
                if (time > hero.shotLastTime + Constants.ITEM_REUSE_COOLDOWN[inventoryList.get(hero.slotNo).name.ordinal()])
                {
                    hero.shotLastTime = time;
                    inventoryList.set(hero.slotNo, inventoryList.get(hero.slotNo)).current -= 1;
                    if (inventoryList.get(hero.slotNo).name == Constants.NameItem.PISTOL || inventoryList.get(hero.slotNo).name == Constants.NameItem.RIFLE)
                    {
                        if (Gdx.input.isKeyPressed(Input.Keys.A)) hero.dirLast = Constants.Direction.LEFT;
                        if (Gdx.input.isKeyPressed(Input.Keys.S)) hero.dirLast = Constants.Direction.DOWN;
                        if (Gdx.input.isKeyPressed(Input.Keys.D)) hero.dirLast = Constants.Direction.RIGHT;
                        if (Gdx.input.isKeyPressed(Input.Keys.W)) hero.dirLast = Constants.Direction.UP;
                        hero.isSoundShoot = true;

                        AddNewShot(hero.dirLast, sprite_shot, sprite_grenade, Constants.ShotType.BULLET);
                    }
                    else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.DRINK)
                    {
                        hero.health += Constants.HP_PER_DRINK;
                        if (hero.health > 100)
                        {
                            hero.health = 100;
                        }
                    }
                    else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.MIXTURE)
                    {
                        hero.state = Constants.HeroState.TRANSFORMING;
                        hero.dir = Constants.Direction.NONE;
                        hero.dirLast = Constants.Direction.DOWN;
                    }
                    else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.GRENADE)
                    {
                        AddNewShot(hero.dirLast, sprite_shot, sprite_grenade, Constants.ShotType.USED_GRENADE);
                    }
                }
            }
        }
    };

    //TODO: comm
    /*

    void DrawText(SpriteBatch batch, View view, Text text)
    {
        Vector2 posView = GetInterfacePosition(view);  //zametka

        text.setPosition(posView.x + 40, posView.y + 40);

        //currentItem << (iCurrent.item);
        //std::ostringstream toStringCurrent;

        //std::ostringstream toStringQuantity;


        text.setString(to_string(inventoryList.get(hero.slotNo).current) + "/" + to_string(inventoryList.get(hero.slotNo).quantity) + " " + Constants.ITEM_NAMES[inventoryList.get(hero.slotNo).name]);
        text.draw(batch);
        if (hero.isReloading)
        {
            text.setPosition(posView.x + 40, posView.y + 70);
            if (inventoryList.get(hero.slotNo).name == Constants.NameItem.PISTOL || inventoryList.get(hero.slotNo).name == Constants.NameItem.RIFLE)
            {
                text.setString("reloading");
            }
            else if (inventoryList.get(hero.slotNo).name == Constants.NameItem.GRENADE)
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
*/
    Inventory GetNewInventoryItem(Loot loot, Sprite items)
    {
        Inventory inventory = new Inventory();
        inventory.name = loot.name;
        inventory.quantity = loot.quantity;
        inventory.current = 0;
        inventory.sprite = items;
        inventory.sprite.setRegion(loot.name.ordinal() * 32, 0, 32, 32);
        return inventory;
    }


    void CheckLoot(Sprite items)
    {
        float x = hero.sprite.getX();
        float y = hero.sprite.getY();

        boolean isItemAlreadyIn = false;

        for (Loot lootItem : lootList)
        {
            if (lootItem.isDrawn == true)  //if loot.item.center contains heroSprite  -> add new item in inventory
            {
                Vector2 itemCenter = new Vector2();
                itemCenter.x = lootItem.pos.x + lootItem.sprite.getWidth() / 2;
                itemCenter.y = lootItem.pos.y + lootItem.sprite.getHeight() / 2;
                if (hero.sprite.getBoundingRectangle().contains(itemCenter))
                {
                    hero.isSoundLoot = true;
                    if (lootItem.name != Constants.NameItem.AMMO)  //any item that we can take
                    {
                        //check if this item exists in inventory, and if so - upload it
                        //TODO: comm
                        int itemIndex = 1;//GetSlotIndexOfItem(lootItem, inventoryList);
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
                        while (nWeaponAmmoAdded < Constants.AMMO_PACKS)
                        {
                            for (Inventory itm : inventoryList)
                            {
                                if (itm.name != Constants.NameItem.MIXTURE && itm.name != Constants.NameItem.DRINK)
                                {
                                    itm.quantity += Constants.MAX_AMMO[itm.name.ordinal()];
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
