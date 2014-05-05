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
import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import event.Event;

public class NetCore<I,O> implements DropBox<O>
{

    private BlockingQueue<Event<O>> toSend;
    private ReceiveThread<I> rec;
    private SendThread<O> sen;

    public NetCore(Socket c, DropBox<I> d)
    {
        try {
            c.setSoTimeout(200);
        } catch (SocketException e) {}
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(c.getOutputStream());
            out.flush();
            in = new ObjectInputStream(c.getInputStream());
        } catch (IOException e) {
            System.out.println("NetCore stream init failed :/");
        }
        this.toSend = new LinkedBlockingDeque<Event<O>>();
        this.rec = new ReceiveThread<I>(in, d);
        this.sen = new SendThread<O>(out, toSend);
    }

    public void killingYou()
    {
        this.rec.killingYou();
        this.sen.killingYou();
    }

    public void push(Event<O> ev)
    {
        try {
            this.toSend.put(ev);
        } catch (InterruptedException e) {}
    }

    private class ReceiveThread<I> extends Thread
    {

        ObjectInputStream in;
        DropBox<I> drop;
        volatile boolean alive;

        public ReceiveThread(ObjectInputStream i, DropBox<I> d)
        {
            this.alive = true;
            this.in = i;
            this.drop = d;
            this.start();
        }

        public void run()
        {
            while(this.alive)
            {
                try {
                    Event<I> ev = (Event<I>) in.readObject();
                    drop.push(ev);
                } catch (IOException e) {
                    //System.out.println("uh oh");
                } catch (ClassNotFoundException e) {}
                //System.out.println("REC THREAD still going");
            }
        }

        public void killingYou()
        {
            this.alive = false;
        }

    }

    private class SendThread<O> extends Thread
    {

        ObjectOutputStream out;
        BlockingQueue<Event<O>> toSend;
        volatile boolean alive;

        public SendThread(ObjectOutputStream o, BlockingQueue<Event<O>> q)
        {
            this.alive = true;
            this.out = o;
            this.toSend = q;
            this.start();
        }

        public void run()
        {
            while(!this.toSend.isEmpty() || this.alive)
            {
                try {
                    Event<O> ev = this.toSend.poll(500, TimeUnit.MICROSECONDS);
                    if(ev != null)
                    {
                        out.writeObject(ev);
                    }
                } catch (IOException e) {
                    System.out.println("netcore network failure?");
                } catch (InterruptedException e) {
                    System.out.println("netcore interrupt failure?"); }
                //System.out.println("SEND THREAD still going");
            }
        }

        public void killingYou()
        {
            this.alive = false;
        }

    }

}
