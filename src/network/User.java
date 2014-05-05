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

package network;

import game.*;
import gameinterface.*;

import java.io.*;
import java.net.*;
import event.Event;
import java.util.*;

public class User extends ConcreteDropBox<User>
{

    private String name;
    private DropBox<GameClient> client;
    private GameServer server;
    private DropBox<Room> room;
    private boolean registeredWithServer;

    public User(GameServer s, Socket c)
    {
        this.registeredWithServer = false;
        this.name = "un-initialized";
        this.server = s;
        this.client = new NetCore<User,GameClient>(c, this);
        this.room = s.getLobby();
        this.start();
    }

    public void toClient(Event<GameClient> ev)
    {
        this.client.push(ev);
    }

    public void toRoom(Event<Room> ev)
    {
        this.room.push(ev);
    }

    public void toServer(Event<GameServer> ev)
    {
        this.server.push(ev);
    }

    public void register()
    {
        this.registeredWithServer = true;
    }

    public void tryRoom(String name)
    {
        Room room = this.server.getRoom(name);
        if (room != null)
        {
            room.push(new event.room.JoinReqEvent(this));
        }
        else
        {
            toClient(new event.client.ChatEvent("Server", "private", "that rooom does not exist"));
        }
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public void resetRoom()
    {
        this.room = this.server.getLobby();
    }

    public void setPlayerName(String name)
    {
        this.name = name;
    }

    public void shutdown(String reason)
    {
        this.toClient(new event.client.PartServerEvent(reason));
        this.toRoom(new event.room.PartEvent(this.getPlayerName(), reason));
        if(this.registeredWithServer)
        {
            this.toServer(new event.server.PartEvent(this.getPlayerName(), reason));
        }
        this.killingYou();
        try {
            sleep(100);
        } catch (InterruptedException e) {}
        this.client.killingYou();
        //System.out.println(this.name + " User has killed its netcore");
    }

    public String getPlayerName()
    {
        return this.name;
    }

}
