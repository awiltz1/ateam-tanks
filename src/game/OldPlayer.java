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

import java.awt.Color;
import java.util.ArrayList;

//yanked straight from demo 1
//good times
public abstract class OldPlayer
{
    protected String playerName;
    private Color color;

    protected ArrayList<SimpleTank> ownedTanks;

    public OldPlayer ( String name, ArrayList<SimpleTank> tanks , Color c)
    {
        playerName = name;
        ownedTanks = tanks;
	color = c;
    }

    public abstract ArrayList<OrderQueue> giveOrders(int frameLimit);

    public boolean stillAlive ()
    {
        return ( ownedTanks.size() != 0 );
    }

    public String getName ()
    {
        return playerName;
    }
    public Color getColor ()
    {
        return color;
    }
}
