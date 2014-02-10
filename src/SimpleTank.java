/*
 * A tank.
 *
 * Not sure why I called it SimpleTank -- I did not
 * have a more complicated form in mind
 * (it might get renamed to Tank)
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

class SimpleTank extends Sprite
{
    private double speed; //how far a frame of MoveOrder will move the tank
    private double handling; //how far a frame of TurnOrder will turn the tank
    private OrderQueue orders;

    public SimpleTank ( ArrayList<Sprite> sprites, Vector3D position, Direction direction, double speed, double handling, Color c )
    {
        super ( sprites, position, direction, c );
        this.speed = speed;
        this.handling = handling;
        OrderQueue orders = new OrderQueue ();
    }
    
    public void giveOrders ( OrderQueue newOrders )
    {
        orders = newOrders;
    }

    public double getSpeed ()
    {
        return speed;
    }
    public double getHandling ()
    {
        return handling;
    }

    public int update ()
    {
        orders.exec( this );
        return 0;
    }
    public void paint(Graphics2D g) {
        g.setColor(this.color);
        g.fillRect((int) this.p.getX (),(int) this.p.getY (), 10, 10);
    }
}
