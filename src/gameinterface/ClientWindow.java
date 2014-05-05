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

package gameinterface;

import game.*;
import network.*;
import event.*;

import java.util.*;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class ClientWindow extends JFrame
{

    //CWindow cwin;
    //DemoPanel demoPanel;


    public ClientWindow(int port)
    {
        super("ateam-tanks");
        //get name
        String playerName = askHumanForName();
        //set stuff
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setLayout(new BorderLayout());
        DemoPanel demoPanel = new DemoPanel();
        demoPanel.setPreferredSize(new Dimension(700,700));
        GameClient c1 = new GameClient(playerName, port);
        RealWindow win = new RealWindow(c1, demoPanel);
        CWindow otherWin = new CWindow(c1, this);
        this.add(demoPanel, BorderLayout.CENTER);
        ButtonPanel buttons = new ButtonPanel(this, otherWin);
        this.add(buttons, BorderLayout.PAGE_END);
        this.setVisible(true);
    }

    private String askHumanForName()
    {
        Object[] possibilities = null;
        String s = (String)JOptionPane.showInputDialog(this, "Enter your name:", 
                            "Name Entry",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "typety type");
        if ((s != null) && (s.length() > 0))
        {
            return s;
        }
        else return askHumanForName();
    }


}
