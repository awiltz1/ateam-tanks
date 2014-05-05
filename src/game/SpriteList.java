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

package game;

import java.util.*;
import java.io.*;
import gameinterface.*;

/**
 * This class is abstracts the workings of the sprite list.
 *
 * This was necessary because sprites can only be added to or removed
 * from the main sprite list at the beginning or end of each pass-through
 *
 * If the list is changed in any way during a 
 *
 *   for ( Sprite sprite : sprites )
 *
 * type block, the program crashes with an exception.
 *
 * This class handles postponing list alterations until they
 * are safe to perform.
 */

public class SpriteList implements Serializable
{
    private ArrayList<Sprite> sprites;
    private ArrayList<Sprite> toBeAdded;
    private ArrayList<Sprite> toBeRemoved;
    private int turnLimit;
    private int framesPerTurn;

    public SpriteList ()
    {
        this . sprites = new ArrayList<Sprite>();
        this . toBeAdded = new ArrayList<Sprite>();
        this . toBeRemoved = new ArrayList<Sprite>();
        this . turnLimit = 20;
        this . framesPerTurn = 100;
    }
    public SpriteList ( SpriteList other )
    {
        this . sprites = new ArrayList<Sprite>( other.sprites );
        this . toBeAdded = new ArrayList<Sprite>( other.toBeAdded );
        this . toBeRemoved = new ArrayList<Sprite>( other.toBeRemoved );
        this . turnLimit = 20;
        this . framesPerTurn = 100;
    }
    public SpriteList ( ArrayList<Sprite> initial )
    {
        this . sprites = initial;
        this . toBeAdded = new ArrayList<Sprite>();
        this . toBeRemoved = new ArrayList<Sprite>();
        this . turnLimit = 20;
        this . framesPerTurn = 100;
    }

    public int playerCount()
    {
        int count = 0;
        for (Sprite s : this.sprites)
        {
            int c = s.getPlayerID();
            if(c > count)
            {
                count = c;
            }
        }
        return count;
    }

    public int getFramesPerTurn()
    {
        return framesPerTurn;
    }

    public ArrayList<Sprite> getSprites ()
    {
        return sprites;
    }
    public void add ( Sprite newSprite )
    {
        this . toBeAdded . add ( newSprite );
    }
    public void remove ( Sprite deadSprite )
    {
        this . toBeRemoved . add ( deadSprite );
    }

    public SpriteList clone()
    {
        SpriteList output = new SpriteList();
        for (Sprite sprite : this.sprites)
        {
            output.add(sprite.clone());
        }
        output.settle();
        return output;
    }
    public void settle ()
    {
        for ( Sprite deadSprite : this . toBeRemoved )
        {
            boolean success = false;
            success = this . sprites . remove ( deadSprite );
            /*
            // Debugging prints
            if ( success )
            {
                System.out.println ( deadSprite . getClass () . getName () + " removed" );
            }
            else
            {
                System.out.println ( "REMOVE FAILED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
            }
            */
        }
        this . toBeRemoved = new ArrayList<Sprite>();

        for ( Sprite newSprite : this . toBeAdded )
        {
            this . sprites . add ( newSprite );
            //System.out.println ( newSprite . getClass () . getName () + " added" );
        }
        this . toBeAdded = new ArrayList<Sprite>();
    }

    public ArrayList<Sprite> getOwnedBy(int id)
    {
        ArrayList<Sprite> output = new ArrayList<Sprite>();
        for (Sprite sprite : this.sprites)
        {
            if (sprite.getPlayerID() == id)
            {
                output.add(sprite);
            }
        }
        return output;
    }

    public int runTurn()
    {
        return this.runTurn(new FakeDisplay());
    }

    public int runTurn(DisplaysGame display)
    {

        System.out.println("SpriteList is starting turn");

        boolean unfinishedBusiness = false; // used to check if bullets are still flying
        for (int f = 0; f < framesPerTurn || unfinishedBusiness; f++)
        {
            unfinishedBusiness = false;
            for (Sprite sprite : this.sprites)
            {
                if (sprite.update(this) == 1)
                {
                    unfinishedBusiness = true;
                }
            }
            this.settle();
            //System.out.println("update! frame #" + f );
            
            display.show(this); // delaying also happends in here
        }

        return 0;

    }

}
