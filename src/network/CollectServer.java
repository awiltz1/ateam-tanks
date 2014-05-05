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

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class CollectServer extends Thread
{
    private ServerSocket server = null;
    private GameServer s;
    private volatile boolean alive;

    public CollectServer(GameServer s, int port)
    {
        //port = 8887;
        this.alive = true;
        try {
            this.server = new ServerSocket(port);
            this.server.setSoTimeout(500);
        } catch ( IOException e ) {
            System.out.println ( "meh says the collectserver" ); }
        this.s = s;
        this.start();
    }
        
    public void run()
    {
        System.out.println("--- CollectServer started");
        while (this.alive)
        {
            Socket con = null;
            try {
                con = server.accept();
                new User(s, con);
            } catch (IOException e) {
                //System.out.println("failed user connection");
            }
        }
    }

    public void killingYou()
    {
        this.alive = false;
    }

}
