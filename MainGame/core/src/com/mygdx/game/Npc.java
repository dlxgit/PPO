package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
        Npc npc = new Npc();
        npc.currentFrame = 0;
        npc.health = 20;
        npc.sprite = sprite_npc;
        npc.state = Constants.NpcState.LIVING;

        npc.type = Constants.NpcType.PHOTOGRAPHS;
        npc.pos = new Vector2( 5 * Constants.STEP, 8 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.BABY;
        npc.pos = new Vector2( 50 * Constants.STEP, 10 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.TEACHER;
        npc.pos = new Vector2( 9 * Constants.STEP, 15 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.DOG;
        npc.pos = new Vector2( 53 * Constants.STEP, 15 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.SOLDIER;
        npc.pos = new Vector2( 28 * Constants.STEP, 23 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.SEARCHER;
        npc.pos = new Vector2( 55 * Constants.STEP, 4 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);

        npc.type = Constants.NpcType.COOK;
        npc.pos = new Vector2( 20 * Constants.STEP, 14 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);
        npc.type = Constants.NpcType.GIRL;
        npc.pos = new Vector2( 15 * Constants.STEP, 6 * Constants.STEP );
        npc.sprite.setPosition(npc.pos.x, npc.pos.y);
        npcList.add(npc);
    };


    void ComputeNpcFrame(Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            if (npc.state == Constants.NpcState.LIVING)
            {
                switch (npc.type)
                {
                    case PHOTOGRAPHS:
                        if (npc.currentFrame > 12)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(2 + 41 * (int) npc.currentFrame, 350, 41, 50);
                        break;
                    case BABY:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 28 * (int) npc.currentFrame, 213, 23, 32);
                        break;
                    case TEACHER:
                        if (npc.currentFrame > 2)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(3 + 37 * (int) npc.currentFrame, 2, 37, 41);
                        break;
                    case GIRL:
                        if (npc.currentFrame > 7)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(0 + 50 * (int) npc.currentFrame, 92, 50, 67);
                        npc.currentFrame += 0.08f;
                        break;
                    case DOG:
                        if (npc.currentFrame > 4)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(2 + 34 * (int) npc.currentFrame, 49, 31, 34);
                        break;
                    case SOLDIER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 36 * (int) npc.currentFrame, 252, 35, 46);
                        break;
                    case SEARCHER:
                        if (npc.currentFrame > 3)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(4 + 31 * (int) npc.currentFrame, 306, 29, 39);
                        break;
                    case COOK:
                        if (npc.currentFrame > 9)
                        {
                            npc.currentFrame = 0;
                        }
                        npc.sprite.setRegion(3 + 54 * (int) npc.currentFrame, 454, 52, 66);
                        break;
                }
            }
            else if (npc.state == Constants.NpcState.KILLED)
            {
                npc.sprite.setRegion(4 + 45 * (int) npc.currentFrame, 593, 53, 45);
                npc.currentFrame += 0.06f;
            }
            npc.currentFrame += 0.02f;
        }
    };

    void SortNpcList(Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            if (npc.state != Constants.NpcState.LIVING) {
                npcList.add(npc);
                npcList.remove(npc);
            }
        }
    }

    void DrawNpc(SpriteBatch batch, Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            npc.sprite.draw(batch);
        }
    };

    void DeleteNpcList(Vector<Npc> npcs) {
        npcs.clear();
    }
}
