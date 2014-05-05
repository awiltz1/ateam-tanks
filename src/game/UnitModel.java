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

/*
 * A model of a tank.
 *
 * More specifically, a model of a player-controlled unit. This
 * class is likely to be pulled into a heirarchy of units and
 * tanks and things later on, but for now it is its own independant
 * class that just happens to copy a lot of SimpleTank's code.
 * Oh well.
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.Color;

public class UnitModel
{
    private double speed;
    private double handling;
    private Color color;
    private Vector3D position;
    private Direction direction;

    public UnitModel ()
    {
        this . speed = 5;
        this . handling = 5;
        this . color = Color.green;
        this . position = new Vector3D ( 0, 0, 0 );
        this . direction = new Direction ( 0, 0 );
    }
    public UnitModel ( Sprite tank )
    {
        this . speed = 5;
        this . handling = 5;
        this . color = Color.green;
        this . position = new Vector3D ( tank . getPosition () );
        this . direction = new Direction ( tank . getDirection () );
    }
    public UnitModel(UnitModel other)
    {
        this . speed = 5;
        this . handling = 5;
        this . color = Color.green;
        this . position = new Vector3D ( other . getPosition () );
        this . direction = new Direction ( other . getDirection () );
    }

    public double getSpeed ()
    {
        return speed;
    }
    public double getHandling ()
    {
        return handling;
    }

    public Vector3D getPosition ()
    {
        return new Vector3D ( this . position );
    }

    public void setPosition ( Vector3D other )
    {
        this . position = new Vector3D ( other );
    }

    public Direction getDirection ()
    {
        return new Direction ( this . direction );
    }

    public void setDirection ( Direction other )
    {
        this . direction = new Direction ( other );
    }

    public void paint ( Graphics2D g )
    {
        double radius = 10;
        g.setColor(this.color);
        g.draw(Sprite.getCircle(position.getX(),position.getY(),radius));
        double direction=this.direction.getTheta()*Math.PI/180;
        g.draw(new Line2D.Double(position.getX(),position.getY(),position.getX()+Math.cos(direction)*radius,position.getY()+Math.sin(direction)*radius));
    }
}
