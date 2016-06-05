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



    public void SortNpcList(Vector<Npc> npcList)
    {
        for (Npc npc : npcList)
        {
            if (npc.state != Constants.NpcState.LIVING) {
                npcList.add(npc);
                npcList.remove(npc);
            }
        }
    }
}
