package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Andrey on 04.06.2016.
 */
public class Npc {
    Vector2 pos;
    Texture texture;
    Sprite sprite;
    int health;
    Constants.NpcState state;
    float currentFrame;
    Constants.NpcType type;
    void InitializeNpc(Vector<Npc> npcList, Sprite sprite_npc)
    {
        Npc npc;
        npc.currentFrame = 0;
        npc.health = 20;
        npc.sprite = sprite_npc;
        npc.state = LIVING;

        npc.type = PHOTOGRAPHS;
        npc.pos = { 5 * STEP, 8 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = BABY;
        npc.pos = { 50 * STEP, 10 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = TEACHER;
        npc.pos = { 9 * STEP, 15 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = DOG;
        npc.pos = { 53 * STEP, 15 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = SOLDIER;
        npc.pos = { 28 * STEP, 23 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = SEARCHER;
        npc.pos = { 55 * STEP, 4 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);

        npc.type = COOK;
        npc.pos = { 20 * STEP, 14 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);
        npc.type = GIRL;
        npc.pos = { 15 * STEP, 6 * STEP };
        npc.sprite.setPosition(npc.pos);
        npcList.add(npc);
    };


    void ComputeNpcFrame(Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            if (npc.state == LIVING)
            {
                switch (npc.type)
                {
                    case PHOTOGRAPHS:
                        if (npc.currentFrame > 12)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(2 + 41 * (int)npc.currentFrame, 350, 41, 50));
                        break;
                    case BABY:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(4 + 28 * (int)npc.currentFrame, 213, 23, 32));
                        break;
                    case TEACHER:
                        if (npc.currentFrame > 2)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(3 + 37 * (int)npc.currentFrame, 2, 37, 41));
                        break;
                    case GIRL:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(0 + 50 * (int)npc.currentFrame, 92, 50, 67));
                        npc.currentFrame += 0.08f;
                        break;
                    case DOG:
                        if (npc.currentFrame > 4)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(2 + 34 * (int)npc.currentFrame, 49, 31, 34));
                        break;
                    case SOLDIER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(4 + 36 * (int)npc.currentFrame, 252, 35, 46));
                        break;
                    case SEARCHER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(4 + 31 * (int)npc.currentFrame, 306, 29, 39));
                        break;
                    case COOK:
                        if (npc.currentFrame > 9)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setTextureRect(IntRect(3 + 54 * (int)npc.currentFrame, 454, 52, 66));
                        break;
                }
            }
            else if (npc.state == KILLED)
            {
                npc.sprite.setTextureRect(IntRect(4 + 45 * (int)npc.currentFrame, 593, 53, 45));
                npc.currentFrame += 0.06f;
            }
            npc.currentFrame += 0.02f;
        }
    };

    void SortNpcList(Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            if (npc.state != LIVING) {
                npcList.add(npc);
                npcList.remove(npc);
            }
        }
    }

    void DrawNpc(SpriteBatch batch, Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            batch.draw(npc.sprite);
        }
    };

    void DeleteNpcList(Vector<Npc> npcs) {
        npcs.clear();
    }
}
