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

import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.awt.Color;

public class GameServer extends Thread
{

    ArrayList<Player> players;
    SpriteList sprites;
    CollectServer collector;
    int numRemotePlayers;
    int numLocalPlayers;
    int frameLimit;

    public GameServer (int r, int l)
    {
        this(r, l, new SpriteList());
    }
    public GameServer (int r, int l, SpriteList init)
    {
        this.frameLimit = 100;
        this.numRemotePlayers = r;
        this.numLocalPlayers = l;
        this.players = new ArrayList<Player>();
        this.sprites = init;
        this.collector = new CollectServer(r);
    }

    public void run ()
    {
        collector.start();
        int i = 1; // counter for remote and local players
        ArrayList<RemotePlayer> clientPlayers = collector.getClients();

        // creates players for all the sockets and gives them a demo unit
        for (RemotePlayer player : clientPlayers)
        {
            players.add(player);
            //sprites.add(new SimpleTank(new Vector3D(10+i*30,20,10), new Direction(0,0), i, 1));
            System.out.println("client player " + i + " registered");
            i++;
        }
        //Finishes up by generating any desired local players (probably AIs)
        while (i <= this.numRemotePlayers + this.numLocalPlayers)
        {
            players.add(new LocalPlayer(i));
            //sprites.add(new SimpleTank(new Vector3D(10+i*30,20,10), new Direction(0,0), i, 1));
            System.out.println("local player " + i + " generated");
            i++;
        }
        sprites.settle(); // settles the spritelist

        System.out.println("Starting game with " + players.size() + " players.");

        // give players the preliminary state to order on
        for (Player p : this.players)
        {
            p.giveSettledGameState(sprites.clone());
        }
        while (this.players.size() > 1)
        {
            // get orders from players
            for (Player p : this.players)
            {
                System.out.println("Waiting on " + p.getName() + " for orders");
                // getOrders() will hang until the player has a set of orders ready
                ArrayList<OrderQueue> os = p.getOrders();
                System.out.println("orders got by gameserver");
                ArrayList<Sprite> ss = this.sprites.getOwnedBy(p.ID());
                matchOrders(os, ss);
                System.out.println("escaped match");
                System.out.println("Orders recieved from " + p.getName());
            }
            // give each player the runnable list
            for (Player p : this.players)
            {
                // this will also hang for each until the list has been sent
                p.giveGameState(sprites.clone());
            }
            // run the list through the turn
            this.sprites.runTurn();
            // give each player the canonical ran list
            for (Player p : this.players)
            {
                p.giveSettledGameState(sprites.clone());
            }
            // players then go about writing new orders
            // based on the updated sprite list and this
            // thread hangs again while it waits for those
            // orders to be ready
        }

        // end conditions
        if (this.players.size() == 1)
        {
            System.out.println(" *** " + players.get(0).getName() + " has won! *** ");
        }
        else
        {
            System.out.println(" *** In war, nobody wins. True fact. *** ");
        }
    }

    private void matchOrders(ArrayList<OrderQueue> os, ArrayList<Sprite> ss)
    {
        for (Sprite sprite : ss)
        {
            // all are blanked first in case they dont get a new queue
            sprite.giveOrders(new OrderQueue());
        }
        System.out.println("a");
        for (OrderQueue q : os)
        {
            for (Sprite sprite : ss)
            {
                if (sprite.uid().equals(q.uid()))
                {
                    sprite.giveOrders(q);
                }
            }
        }
        System.out.println("b");
    }

    private void clearDeadPlayers()
    {
        for (Player p : this.players)
        {
            boolean dead = true;
            for (Sprite s : this.sprites.getSprites())
            {
                if (s.getPlayerID() == p.ID())
                {
                    dead = false;
                }
            }
            if (dead)
            {
                System.out.println(p.getName() + " has been destroyed.");
                this.players.remove(p);
                p.kill();
            }
        }
    }
}
