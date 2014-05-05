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

public class RemotePlayer extends Player
{

    ArrayList<OrderQueue> orders;
    boolean ordersAreSet;

    public RemotePlayer(int id, String n)
    {
        super(id, n);
        this.ordersAreSet = false;
        this.orders = new ArrayList<OrderQueue>();
    }

    public ArrayList<OrderQueue> getOrders()
    {
        return this.orders;
    }

    public boolean areOrdersSet()
    {
        return this.ordersAreSet;
    }

    public void setOrders(ArrayList<OrderQueue> os)
    {
        this.orders = os;
        this.ordersAreSet = true;
    }

    public void clearOrders()
    {
        this.orders = new ArrayList<OrderQueue>();
        this.ordersAreSet = false;
    }

}
