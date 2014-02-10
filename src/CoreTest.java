/*
 * An initial test of the class structures using a set of defined orders.
 *
 * No graphics -- the tank position is just printed to the screen after each frame.
 */

import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;

public class CoreTest
{
    public static void main ( String args[] )
    {
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        SimpleTank tank1 = new SimpleTank ( sprites, new Vector3D ( 94, 20 , 0 ), new Direction ( 0 ), 20, 5, Color.green );

        OrderQueue q = new OrderQueue();
        q.add ( new MoveOrder ( 10, 1 ) );
        q.add ( new TurnOrder ( 10, 1 ) );
        q.add ( new MoveOrder ( 20, 1 ) );
        q.add ( new MoveOrder ( 5, -1 ) );

        tank1.giveOrders ( q );
	
	SimpleTank tank2 = new SimpleTank(sprites, new Vector3D(600, 100, 0), new Direction(0), 20, 5, Color.black);
	SimpleTank tank3 = new SimpleTank(sprites, new Vector3D(100, 600, 0), new Direction(0), 20, 5, Color.yellow);

        JFrame frame = new JFrame("A-Tanks");
	frame.setSize(900,700);
	frame.add(new Game(sprites));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	for ( int i = 0; i < 200; i ++ )
        {
            for ( Sprite sprite : sprites )
            {
                sprite.update();
                System.out.println ( sprite.getPosition().toString() + " -- " + sprite.getDirection().toString() );
            }
        }
    }
}
