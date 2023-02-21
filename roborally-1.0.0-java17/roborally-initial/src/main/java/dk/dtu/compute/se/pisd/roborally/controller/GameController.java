/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;
    private int playerTurns =0;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /*
         Board has an ArrayArray ([][]) called spaces of the Class Space.
         [x] pos and [y] pos.
         Each space can contain a player, can be found with 'board.getSpace(x, y).getPlayer();'
         The player variable in 'Space' is at default set to null. but '.setPlayer()' can be used to put a player there

      */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   1 - the current player should be moved to the given space
        //     (if it is free()
        //   2 - and the current player should be set to the player
        //     following the current player
        //   3 - the counter of moves in the game should be increased by one
        //     if the player is moved
        /*
            1 Current player can be retrieved from 'Board', an instance of which is found in this class.
            Current player can be retrieved with 'board.getCurrentPlayer()'
            'Space' has a set player and get player, if getPlayer return null it means that no player is at this spot.
            Set player already removes the player from the old spot.
         */
        if(space.getPlayer() == null) {
            space.setPlayer(board.getCurrentPlayer());
            board.nextTurn(); //
            playerTurns++;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet.
     * This method should eventually be removed.
     */
    public void notImplememted() {
        // XXX just for now to indicate that the actual method to be used by a handler
        //     is not yet implemented
    };

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null & targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }



}
