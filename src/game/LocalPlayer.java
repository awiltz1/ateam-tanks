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

/*
 * This class is a super-class for all players that
 * do not interact with a seperate client over a
 * network.
 *
 * This would likely include an AI or several, and
 * a local playing interface
 *
 * This can also be instantiated itself as a dummy
 */
public class LocalPlayer extends Player
{
    
    public LocalPlayer(int id, String n)
    {
        super(id, n);
    }

    public ArrayList<OrderQueue> getOrders()
    {
        return new ArrayList<OrderQueue>();
    }
    
    public void giveGameState(SpriteList s) {/*nothing*/}

    public void giveSettledGameState(SpriteList s) {/*nothing*/}

    protected String askForPlayerName()
    {
        return "hello i am " + this.id + "-bot";
    }
}
