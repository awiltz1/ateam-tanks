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

public class NetWorker<I,O>
{
    public DropBox<O> connect(DropBox<I> d, String hostname, int port)
    {
        DropBox<O> box = new FakeBox<O>();
        try {
            Socket con = null;
            con = new Socket(hostname, port);
            box = new NetCore<I,O>(con, d);
        } catch (IOException e) {
            System.out.println("Bad IP or something?");
            System.out.println("Connection Failed!");
        }
        return box;
    }

    public DropBox<O> disconnect()
    {
        return new FakeBox<O>();
    }

}
