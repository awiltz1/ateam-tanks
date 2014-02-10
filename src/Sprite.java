/*
 * This is a general class for anything that might
 * be placed, removed, or move or change in any way
 * on the field during play. The idea is that the main
 * game loop can iterate over a list of these and update
 * each one.
 * And then the graphical interface can iterate over the list
 * and display each one.
 *
 * So far only tanks use them, but the next obvious user would
 * be a bullet
 */

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;

public abstract class Sprite
{
    ArrayList<Sprite> sprites;
    protected Vector3D p;
    protected Direction d;
    protected Color color;

    public Sprite ( ArrayList<Sprite> sprites, Vector3D p, Direction d, Color c )
    {
        this.sprites = sprites;
        this.sprites.add ( this );

        this.p = new Vector3D ( p );
        this.d = new Direction ( d );
	this.color = c;
    }

    public abstract int update ();

    public abstract void paint(Graphics2D g);

    public Vector3D getPosition ()
    {
        return p;
    }
    public void setPosition ( Vector3D p )
    {
        this.p = p;
    }
    public Direction getDirection ()
    {
        return d;
    }
    public void setDirection ( Direction d )
    {
        this.d = d;
    }

    public void kill ()
    {
        sprites.remove ( this );
    }
}
