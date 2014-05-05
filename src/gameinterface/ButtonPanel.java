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
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.*;


public class ButtonPanel extends JPanel
{

    private JFrame frame;
    final CWindow win;
    private JButton connect;
    private JButton quit;
    private JButton create;
    private JButton join;
    private JButton disconnect;
    private JButton start;

    public ButtonPanel(final JFrame frame, final CWindow win)
    {
        this.frame = frame;
        this.setLayout(new FlowLayout());
        this.win = win;

        this.connect = new JButton("Connect");
        this.connect.setPreferredSize(new Dimension(128, 40));
        this.add(this.connect);
        this.connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hostname = askForString("Enter Server Hostname:", "localhost");
                win.toClient(new event.client.JoinServerEvent(hostname));
            }
        } );

        this.disconnect = new JButton("Disconnect");
        this.disconnect.setPreferredSize(new Dimension(128, 40));
        this.add(this.disconnect);
        this.disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                win.toClient(new event.client.PartServerEvent("The disconnect button was pressed"));
            }
        } );

        this.quit = new JButton("Quit");
        this.quit.setPreferredSize(new Dimension(128, 40));
        this.add(this.quit);
        this.quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                win.toClient(new event.client.ShutdownEvent());
            }
        } );

        this.create = new JButton("Create Game");
        this.create.setPreferredSize(new Dimension(128, 40));
        this.add(this.create);
        this.create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String gamename = askForString("Enter Game Name:", "");
                win.toClient(new event.client.FwdUserEvent(new event.user.CreateRoomEvent(gamename, SpriteListGen.mkList())));
            }
        } );
        
        this.join = new JButton("Join Game");
        this.join.setPreferredSize(new Dimension(128, 40));
        this.add(this.join);
        this.join.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String gamename = askForString("Enter Game Name:", "");
                win.toClient(new event.client.FwdUserEvent(new event.user.JoinRoomEvent(gamename)));
            }
        } );

        this.start = new JButton("Start Game");
        this.start.setPreferredSize(new Dimension(128, 40));
        this.add(this.start);
        this.start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                win.toClient(new event.client.FwdUserEvent(new event.user.StartGameEvent()));
            }
        } );

        this.setVisible(true);
    }

    final String askForString(final String prompt, final String suggestion)
    {
        Object[] possibilities = null;
        String s = (String)JOptionPane.showInputDialog(this.frame, prompt, 
                            "Info, pls",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            suggestion);
        if ((s != null) && (s.length() > 0))
        {
            return s;
        }
        else return askForString("No seriously, " + prompt, suggestion);
    }
}
