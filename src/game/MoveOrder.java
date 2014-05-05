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

/**
 * An order class for moving forward and backward
 *
 * each frame, it moves tank by <code>tank.getSpeed()()</code> units.
 */

import java.awt.*;
import java.awt.geom.*;
public class MoveOrder extends Order
{
    private int direction; // 1 for forward, -1 for back
    /**
     * @param frames number of frames to turn
     * @param either be 1 to move forward or -1 move backward
     */
    public MoveOrder ( int frames, int direction )
    {
        super ( frames );
        this.direction = direction;
    }
    public MoveOrder clone()
    {
        return new MoveOrder(this.frames, this.direction);
    }
    public int getDirection(){
        return direction;
    }

    public void walk ( UnitModel model, Graphics2D g )
    {
        //move the model and save its start and end positions
        Vector3D oldP = model . getPosition ();
        model.setPosition(new Vector3D(model.getPosition(), new Vector3D(model.getSpeed() * (double)direction * this.frames, model.getDirection())));
        Vector3D newP = model . getPosition ();
        //paint a line from start to end
        g . setColor ( Color . white );
        g . draw ( new Line2D . Double ( oldP.getX(), oldP.getY(), newP.getX(), newP.getY() ) );
        //there is no return value because the model was passed by reference
    }

    public void execSpecific ( SpriteList sprites, SimpleTank tank )
    {
        Vector3D oldPosition=tank.getPosition();
        Vector3D newPosition=new Vector3D ( tank.getPosition(), new Vector3D ( tank.getSpeed() * ( double ) direction, tank.getDirection()) );
        tank.setPosition (newPosition);
        if(!tank.getAllCollisions(sprites).isEmpty()){
            tank.setPosition(oldPosition);
        }
    }
}
