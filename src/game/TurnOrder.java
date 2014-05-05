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
 * An order for turning the tank
 *
 * each frame, it turns tank by <code>tank.getHandling()</code> angle.
 */

import java.awt.*;
import java.awt.geom.*;
public class TurnOrder extends Order
{
    private int direction; // 1 is clockwise, -1 is counter-clockwise

    /**
     * @param frames number of frames to turn
     * @param direction either be 1 to turn clockwisely or -1 to turn counter-clockwisely
     */
    public TurnOrder ( int frames, int direction )
    {
        super ( frames );
        this.direction = direction;
    }

    public TurnOrder clone()
    {
        return new TurnOrder(this.frames, this.direction);
    }

    public int getDirection(){
        return direction;
    }

    public void walk ( UnitModel model, Graphics2D g )
    {
        model.setDirection ( new Direction ( model.getDirection().getTheta() + direction * model.getHandling () * this.frames ) );
        double radius = 5;
        g.setColor(Color.green);
        g.draw(Sprite.getCircle(model.getPosition().getX(),model.getPosition().getY(),radius));
        double dir=model.getDirection().getTheta()*Math.PI/180;
        g.draw(new Line2D.Double(model.getPosition().getX(),model.getPosition().getY(),model.getPosition().getX()+Math.cos(dir)*radius,model.getPosition().getY()+Math.sin(dir)*radius));
        //No painting is necessary for a turn.
        //Either the next move line or the end orientation of the model will demonstrate
        //the effects of the turn.
    }

    @Override
    protected void execSpecific ( SpriteList sprites, SimpleTank tank )
    {
        tank.setDirection ( new Direction ( tank.getDirection().getTheta() + direction * tank.getHandling () ) );
    }
}
