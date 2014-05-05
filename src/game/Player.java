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

public class Player
{

    protected int id;
    protected String name;

    public Player(int id, String n)
    {
        this.id = id;
        this.name = n;
    }

    public ArrayList<OrderQueue> getOrders()
    {
        ArrayList<OrderQueue> os = new ArrayList<OrderQueue>();
        OrderQueue o = new OrderQueue();
        MoveOrder ord = new MoveOrder(10, 1);
        o.add(ord);
        os.add(o);
        return os;
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
