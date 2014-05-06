
/**
 * Copyright 2014 A-Team Games
 *
 * This file is part of ateam-tanks.
 *
 *    ateam-tanks is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    ateam-tanks is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with ateam-tanks.  If not, see <http://www.gnu.org/licenses/>.
 */

package game;

import java.util.ArrayList;

public class AIPlayer extends Player
{

    public AIPlayer(int id, String n)
    {
        super(id, n);
    }

    public ArrayList<OrderQueue> getOrders(SpriteList sprites)
    {
        System.out.println("running AI " + this.getName());
        ArrayList<Obstacle> objects = new ArrayList<Obstacle>();
        ArrayList<SimpleTank> ownedTanks = new ArrayList<SimpleTank>();
        SimpleTank enemy = null;
        for(Sprite s : sprites.getSprites())
        {
            if(s.playerID == -5)
            {
                objects.add((Obstacle) s);
            }
            else if(s.playerID == 0) {}
            else if(s.playerID == this.ID())
            {
                ownedTanks.add((SimpleTank) s);
            }
            else
            {
                enemy = (SimpleTank) s;
            }
        }
        if(enemy != null && ownedTanks.size() > 0)
        {
            AICore ai = new AICore(objects, ownedTanks, enemy);
            return ai.giveOrders(sprites.getFramesPerTurn());
        }
        else
        {
            return new ArrayList<OrderQueue>();
        }
    }

    public boolean areOrdersSet()
    {
        return true;
    }

    public void setOrders(ArrayList<OrderQueue> os) {}

    public void clearOrders() {}

    public String getName()
    {
        return this.name;
    }

    public int ID()
    {
        return this.id;
    }

}
