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

import java.util.ArrayList;

public interface ReadsMaps
{
    /**
     * Takes the name of a text file and generates a list of
     * sprites from it.
     *
     * A sample map is in the maps folder.
     * Maps are a list of lines describing sprites in the format:
     *     
     *     <sprite-type>,<x-position>,<y-position>,<z-position>,<radius(size)>
     *
     * It would be easiest to walk through a map file line by line
     * and split each line by commas, then cast each string
     * according to what it's supposed to represent.
     */
    public SpriteList readMap ( String nameOfMapFile );
}
