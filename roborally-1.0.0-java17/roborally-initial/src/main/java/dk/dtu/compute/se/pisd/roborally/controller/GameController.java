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

    /**
     * Creates a gameController from a board
     * @param board a non null board.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /*
         Board has an ArrayArray ([][]) called spaces of the Class Space.
         [x] pos and [y] pos.
         Each space can contain a player, can be found with 'board.getSpace(x, y).getPlayer();'
         The player variable in 'Space' is at default set to null. but '.setPlayer()' can be used to put a player there

      */

    /**
     * Moves current player on the board after that the game progresses to the next turn
     * @param space
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
            board.setPlayerMoves(board.getPlayerMoves()+1);
            //System.out.println(board.getPlayerMoves());
            nextTurn();
        }
    }

    /*
nextTurn() was created by Toby, for the method in game Controller that needs to be able to switch to the next player.
it switches to the next turn, being the next in the players array. Unless it is at the end, then it goes to number 0.
This will change when we implement actual turn counter.
 */

    /**
     * Progresses the game to the next player.
     */
    public void nextTurn(){
        if(board.getPlayerNumber(board.getCurrentPlayer()) < board.getPlayersNumber()-1){
            //Next number player
            board.setCurrentPlayer(board.getPlayer((board.getPlayerNumber(board.getCurrentPlayer())+1)));
        }
        else{
            //player number 0
            board.setCurrentPlayer(board.getPlayer(0));
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet.
     * This method should eventually be removed.
     */
    public void notImplememted() {
        throw new UnsupportedOperationException("Not implemented");
        // XXX just for now to indicate that the actual method to be used by a handler
        //     is not yet implemented
    };

    /**
     * Moves one card from a source field to a target field, if the target field is empty and the source field contains a card.
     * @param source where you want to move the card from
     * @param target where you want to move the card to
     * @return true if card has been moved
     */
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
