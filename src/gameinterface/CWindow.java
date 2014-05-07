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

package gameinterface;

import game.*;
import network.*;
import event.*;

import java.util.*;

public class CWindow extends ConcreteDropBox<CWindow>
{

    DropBox<GameClient> client;
    ClientWindow win;

    public CWindow(GameClient c, ClientWindow win)
    {
        this.client = c;
        c.setCWin(this);
        this.win = win;
        this.start();
    }

    public void toClient(Event<GameClient> ev)
    {
        this.client.push(ev);
    }

    public ClientWindow getWin()
    {
        return this.win;
    }

    public void updateServerInfo(String info)
    {
        this.win.updateServerInfo(info);
    }

    public void newChatMessage(String sender, String status, String message)
    {
        this.win.newChatMessage(sender, status, message);
    }

}
