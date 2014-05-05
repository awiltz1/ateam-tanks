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

import event.Event;
import java.util.concurrent.*;

public class ConcreteDropBox<T> extends Thread implements DropBox<T>
{

    private BlockingQueue<Event<T>> events;
    private volatile boolean alive;

    public ConcreteDropBox()
    {
        this.events = new LinkedBlockingDeque<Event<T>>();
        this.alive = true;
    }
    
    public void push(Event<T> ev)
    {
        try {
            this.events.put(ev);
        } catch (InterruptedException e) {}
    }

    public void run()
    {
        while(this.alive)
        {
            try {
                Event<T> event = this.events.poll(1, TimeUnit.SECONDS);
                if (event != null)
                {
                    event.handle((T) this);
                }
            } catch (InterruptedException e) {}
        }
        try {
            // This gives remaining outgoing events a chance to send
            // while not taking any new incoming events.
            sleep(500); 
        } catch (InterruptedException e) {}
    }

    public void killingYou()
    {
        this.alive = false;
    }

}
