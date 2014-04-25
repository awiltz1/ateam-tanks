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

import java.util.*;
import java.util.concurrent.*;

/* The GameServer is intended to handle connections and disconnections and
 * create and start games, all in the form of responses to "events"
 */
public class GameServer extends Thread implements DropBox<ServerEvent>
{
    
    Map<String,User> users;
    BlockingQueue<ServerEvent> events;
    int userCapacity;

    public GameServer(int userCapacity, int port)
    {
        this.users = new HashMap<String,User>();
        this.userCapacity = userCapacity;
        this.events = new LinkedBlockingDeque<ServerEvent>();
        new CollectServer(this, port);
        this.start();
    }

    public void run()
    {
        while(true)
        {
            try {
                ServerEvent ev = this.events.take();
                System.out.println("Server is handling event of type " + ev.getClass().getName());
                ev.handle(this);
            } catch (InterruptedException e) {}
        }
    }

    //TODO switch DropBox to abstract class to get rid of these push methods
    public void push(ServerEvent ev)
    {
        try {
            events.put(ev);
        } catch (InterruptedException e) {}
    }

    public Set<String> getUserNames()
    {
        return this.users.keySet();
    }

    public void announce(String announcement)
    {
        for(String uname : this.users.keySet())
        {
            this.users.get(uname).push(new ClientEventUserEvent(new ChatClientEvent("Server", "public", announcement)));
        }
    }

    public void removeUser(String name, String reason)
    {
        this.users.get(name).push(new DisconnectionUserEvent(reason));
        this.users.remove(name);
    }

    public void toUser(String name, UserEvent ev)
    {
        this.users.get(name).push(ev);
    }

    public boolean addUser(User u)
    {
        if(this.users.containsKey(u.getPlayerName()))
        {
            u.push(new DisconnectionUserEvent("Username already in use"));
            return false;
        }
        else if(this.users.size() == this.userCapacity)
        {
            u.push(new DisconnectionUserEvent("Server is full"));
            return false;
        }
        else
        {
            this.users.put(u.getPlayerName(), u);
            System.out.println(u.getPlayerName() + " has joined!!");
            return true;
        }
    }

    public int getUserCapacity()
    {
        return this.userCapacity;
    }

}
