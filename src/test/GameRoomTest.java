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

public class GameRoomTest extends Thread
{
    public static void main(String[] args)
    {

        System.out.println("----- GameRoom Test -----");
        System.out.println(mkList().playerCount());
        try {
            sleep(500);
        } catch (InterruptedException e) {}

        GameServer server = new GameServer(10, 8889);

        GameClient c1 = new GameClient("joe", 8889);
        GameClient c2 = new GameClient("nick", 8889);
        GameClient c3 = new GameClient("dave", 8889);
        GameClient c4 = new GameClient("roxy", 8889);
        GameClient c5 = new GameClient("c5", 8889);
        GameClient c6 = new GameClient("sollux", 8889);

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
            sleep(50);

            server.push( new event.server.AnnouncementReqEvent("just checking in"));
            //sleep(50);
            c1.push(new event.client.FwdUserEvent(new event.user.CreateRoomEvent("Test-Room", mkList())));
            sleep(50);
            c3.push(new event.client.FwdUserEvent(new event.user.CreateRoomEvent("Test-Room", mkList())));
            c2.push(new event.client.PartServerEvent("reason"));
            sleep(50);
            c6.push(new event.client.JoinServerEvent("localhost"));
            c6.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("wrongroom")));
            c4.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("Test-Room")));
            sleep(50);
            c2.push(new event.client.JoinServerEvent("localhost"));
            sleep(50);
            server.push(new event.server.AnnouncementReqEvent("just checking in again"));
            c6.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("Test-Room")));
            c5.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("Test-Room")));
            c3.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("Test-Room")));
            c2.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("Test-Room")));
            sleep(50);
            c5.push(new event.client.FwdUserEvent(new event.user.PartRoomEvent("just testing the events")));
            sleep(50);
            c4.push(new event.client.FwdUserEvent(new event.user.StartGameEvent()));
            sleep(50);
            c1.push(new event.client.FwdUserEvent(new event.user.StartGameEvent()));
            sleep(5000);
            c1.push(new event.client.ShutdownEvent());
            sleep(2000);
            c3.push(new event.client.FwdUserEvent(new event.user.CreateRoomEvent("New-Test-Room", mkList())));
            sleep(50);
            c5.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("New-Test-Room")));
            c6.push(new event.client.FwdUserEvent(new event.user.JoinRoomEvent("New-Test-Room")));
            c3.push(new event.client.FwdUserEvent(new event.user.StartGameEvent()));
            sleep(3000);
            c5.push(new event.client.FwdUserEvent(new event.user.PartRoomEvent("testing AI replace")));
            sleep(12000);
            server.push(new event.server.ShutdownEvent("killin you"));
            sleep(50);
            c1.push(new event.client.ShutdownEvent());
            c2.push(new event.client.ShutdownEvent());
            c3.push(new event.client.ShutdownEvent());
            c4.push(new event.client.ShutdownEvent());
            c5.push(new event.client.ShutdownEvent());
            c6.push(new event.client.ShutdownEvent());
            sleep(50);
        } catch (InterruptedException e) {}

        System.out.println("----- Tests complete -----");
    }

    public static SpriteList mkList()
    {
        SpriteList init = new SpriteList();
        init.add(new SimpleTank(new Vector3D(-150, 20, 10), new Direction(0, 0), 1, 1));
        init.add(new SimpleTank(new Vector3D(-150, -20, 10), new Direction(0, 0), 1, 2));

        init.add(new SimpleTank(new Vector3D(150, 20, 10), new Direction(0, 0), 2, 1));
        init.add(new SimpleTank(new Vector3D(150, -20, 10), new Direction(0, 0), 2, 2));

        init.add(new SimpleTank(new Vector3D(20, -150, 10), new Direction(0, 0), 3, 1));
        init.add(new SimpleTank(new Vector3D(-20, -150, 10), new Direction(0, 0), 3, 2));

        init.add(new SimpleTank(new Vector3D(20, 150, 10), new Direction(0, 0), 4, 1));
        init.add(new SimpleTank(new Vector3D(-20, 150, 10), new Direction(0, 0), 4, 2));

        init.add(new Obstacle(new Vector3D(0, 0, 0), new Direction(0, 0), 27));

        init.add(new Obstacle(new Vector3D(120, 120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(120, -120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(-120, 120, 0), new Direction(0, 0), 17));
        init.add(new Obstacle(new Vector3D(-120, -120, 0), new Direction(0, 0), 17));

        init.add(new Obstacle(new Vector3D(-88, 40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-88, -40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(88, 40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(88, -40, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(40, 88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-40, 88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(40, -88, 0), new Direction(0, 0), 15));
        init.add(new Obstacle(new Vector3D(-40, -88, 0), new Direction(0, 0), 15));

        init.settle();

        return init;
    }

}
