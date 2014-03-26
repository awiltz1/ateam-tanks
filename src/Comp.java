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

/**
 * A player class for a simple AI
 */

import java.awt.Color;
import java.util.ArrayList;
import java.lang.*;

public class Comp extends Player
{
	private SpriteList objects;
	private ArrayList<SimpleTank> opponent;
	public Comp(SpriteList objects, ArrayList<SimpleTank> tanks, ArrayList<SimpleTank> enemy, Color c) {
		super ("Computer", tanks, c);
		this.objects = objects;
		this.opponent = enemy;
	}
	// Gives orders to game
	public void giveOrders(int frameLimit) {
		OrderQueue orders = new OrderQueue();
		TurnOrder turn = this.turn();
		int f1 = turn.getFrames();
		int dir = turn.getDirection();
		System.out.println("Turn order: (" + f1 + " Frames, " + dir+ " Direction)");
		if (f1 > frameLimit) {
			orders.add(new TurnOrder(frameLimit, dir));
			this.ownedTanks.get(0).giveOrders(orders);
			return;
		}
		else {
			orders.add(turn);
			frameLimit = frameLimit - f1;
		}
		MoveOrder move = this.move();
		int f2 = move.getFrames();
		System.out.println("Move order: (" + f2 + " Frames, " + move.getDirection() + " Direction)");
		if (f2 > frameLimit) {
			orders.add(new MoveOrder(frameLimit, 1));
			this.ownedTanks.get(0).giveOrders(orders);
			return;
		}
		else {
			orders.add(move);
			frameLimit = frameLimit - f2;
		}
		if (frameLimit >= 15) {
			System.out.println("Fire!!");
			double theta = this.ownedTanks.get(0).getDirection().getTheta();
			System.out.println(theta);
			double handling = this.ownedTanks.get(0).getHandling();
			if (dir == 1) {
				theta += (f1+1)*handling;
			}
			else {
				theta -= (f1-1)*handling;
			}
			System.out.println(theta);
			orders.add(new ShootOrder(theta));
			orders.add(new ShootOrder(theta+1));
			orders.add(new ShootOrder(theta-5));
			orders.add(new ShootOrder(theta-3));
			orders.add(new ShootOrder(theta+4));
		}
		this.ownedTanks.get(0).giveOrders(orders);
		return;
	}
	// Any object added to map needs to be added
	public void addObject(Obstacle o) {
		this.objects.add(o);
	}
	public void remObject(Obstacle o) {
		this.objects.remove(o);
	}
	// Turns the tank toward enemy tank
	public TurnOrder turn() {
		double angle = this.diffAngle();
		int dir = -1;
		if (angle == 0) {
			return new TurnOrder(0, dir);
		}
		else if (angle < 0) {
			dir = 1;
			angle = -angle;
		}
		double handling = this.ownedTanks.get(0).getHandling();
		double frames = angle/handling;
		return new TurnOrder((int)frames + 1, dir);
	}
	// Moves the tank toward the enemy
	public MoveOrder move() {
		double dist = this.distance() - 100;
		int dir = 1;
		if (dist == 0) {
			return new MoveOrder(0, dir);
		}
		else if (dist < 0) {
			dir = -1;
			dist = - dist;
		}
		double speed = this.ownedTanks.get(0).getSpeed();
		double frames = dist/speed;
		return new MoveOrder((int)frames, dir);
	}
	// Returns the angle between both tanks
	public double diffAngle() {
		SimpleTank player = this.ownedTanks.get(0);
		SimpleTank enemy = this.opponent.get(0);
		Vector3D p = player.getPosition();
		Vector3D e = enemy.getPosition();
		double deltaX = e.getX() - p.getX();
		double deltaY = e.getY() - p.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		System.out.println(angle);
		if (angle < 0) {
			angle += 360;
		}
		System.out.println(angle);
		Direction temp = player.getDirection();
		double diff = temp.getTheta() - angle;
		System.out.println(temp.getTheta());
		System.out.println(diff);
		return diff;
	}
	// Finds the distance from the enemy
	public double distance() {
		SimpleTank player = this.ownedTanks.get(0);
		SimpleTank enemy = this.opponent.get(0);
		Vector3D p = player.getPosition();
		Vector3D e = enemy.getPosition();
		double deltaX = e.getX() - p.getX();
		double deltaY = e.getY() - p.getY();
		double temp = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		return Math.sqrt(temp);
	}
}
	
	
	