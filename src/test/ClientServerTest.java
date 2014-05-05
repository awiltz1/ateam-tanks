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

package test;

import game.*;
import network.*;
import event.*;
import gameinterface.*;

public class ClientServerTest extends Thread
{
    public static void main(String[] args)
    {

        System.out.println("----- Client-Server Test -----");

        GameServer server = new GameServer(3, 8887);

        GameClient c1 = new GameClient("joe", 8887);
        GameClient c2 = new GameClient("nick", 8887);
        GameClient c3 = new GameClient("nick", 8887);
        GameClient c4 = new GameClient("roxy", 8887);
        GameClient c5 = new GameClient("c5", 8887);
        GameClient c6 = new GameClient("sollux", 8887);

        //Things without delays between them tend to happen
        //out of order, but that is expected what with all the
        //event passing that goes into each Join or Part
        //
        //Either way, nothing crashes!
        try {
            c1.push(new event.client.JoinServerEvent("localhost"));
            c2.push(new event.client.JoinServerEvent("localhost"));
            c3.push(new event.client.JoinServerEvent("localhost"));
            c4.push(new event.client.JoinServerEvent("localhost"));
            c5.push(new event.client.JoinServerEvent("localhost"));
            sleep(1000);

            server.push( new event.server.AnnouncementReqEvent("just checking in"));
            //sleep(1000);
            c2.push(new event.client.PartServerEvent("reason"));
            //sleep(1000);
            c6.push(new event.client.JoinServerEvent("localhost"));
            sleep(1000);
            c3.push(new event.client.JoinServerEvent("localhost"));
            server.push(new event.server.AnnouncementReqEvent("just checking in again"));
            c1.push(new event.client.ShutdownEvent());
            //sleep(2000);
            sleep(1000);
            server.push(new event.server.ShutdownEvent("killin you"));
            sleep(1000);
            c1.push(new event.client.ShutdownEvent());
            c2.push(new event.client.ShutdownEvent());
            c3.push(new event.client.ShutdownEvent());
            c4.push(new event.client.ShutdownEvent());
            c5.push(new event.client.ShutdownEvent());
            c6.push(new event.client.ShutdownEvent());
            sleep(1000);
        } catch (InterruptedException e) {}

        System.out.println("----- Tests complete -----");
    }

}
