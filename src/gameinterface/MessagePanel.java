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
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;


public class MessagePanel extends JPanel
{

    private JFrame frame;
    final CWindow win;
    JTextArea serverInfo;
    JTextArea chat;

    public MessagePanel(final JFrame frame, final CWindow win)
    {
        this.frame = frame;
        this.setLayout(new GridLayout(0, 2));
        this.win = win;

        this.serverInfo = new JTextArea(5, 20);
        //this.serverInfo.setPreferredSize(new Dimension(80,50));
        this.serverInfo.setEditable(false);
        this.add(this.serverInfo);

        this.chat = new JTextArea(5, 20);
        //this.chat.setPreferredSize(new Dimension(80,50));
        this.chat.setEditable(false);
        this.add(this.chat);

        this.setPreferredSize(new Dimension(1000, 300));
        this.setVisible(true);
    }

    public void updateServerInfo(String info)
    {
        this.serverInfo.setText(info);
    }

    public void newChatMessage(String message)
    {
        System.out.println(message);
        this.chat.insert(message + "\n", 0);
    }

}
