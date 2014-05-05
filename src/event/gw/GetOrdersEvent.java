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

package event.gw;

import game.*;
import gameinterface.*;
import network.*;
import java.util.*;

public class GetOrdersEvent implements event.Event<GWindow>
{

    private SpriteList list;
    private int ID;
    private String playerName;

    public GetOrdersEvent(SpriteList ss, int ID, String pn)
    {
        this.list = ss;
        this.ID = ID;
        this.playerName = pn;
    }

    public void handle(GWindow win)
    {
        ArrayList<OrderQueue> orders = win.makeOrders(this.list, this.ID, this.playerName);
        win.toClient(new event.client.FwdUserEvent(new event.user.OrdersEvent(orders)));
    }

}
