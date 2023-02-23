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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.view.BoardView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    public final int width;

    public final int height;

    public final String boardName;

    private Integer gameId;
    private int playerMoves = 0;

    private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;

    /**
     *
    Constructor of the board that takes width, height and a boardName
    It sets the values and creates new spaces at all the spots, it differs depending on the size of the board.
    it also adds the space to the spaces array-array with x and y value
    it also sets the stepmode to false.
     */
    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        playerMoves = 0;
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        this.stepMode = false;
    }
    /**
     *
    creates a default board with width and height. Uses the previous method to do so.
     */
    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }
    /*
    Returns the game ID
     */
    public Integer getGameId() {
        return gameId;
    }
    /*
    sets the game ID to the int in parameters. It requires that the gameID is null.
    if the current id is not null, then if the gameId of the board is already the parameter value then nothing happens.
    In other cases then an exception is thrown saying that you cannot assign a new id to the board.
     */
    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    /*
    Returns the space object at this board with the parameters x and y.
     */
    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }
    /*
        Returns the amount of players on the current board. The size of the PLayers arrayList
    */
    public int getPlayersNumber() {
        return players.size();
    }
    /*
        adds a player to the players arrayList with the parameter Player.
        it doesnt add the player to the list if it is already there.
        It requires that the player's board is this one.

        notifyChange() is an update method in designpatterns.observer.Subject
     */
    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }
    /*
    returns the player Object of the int parameter unless the int is below 0 or higher than the player arrayList size.
    otherwise it returns null
     */
    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }
    /*
    returns the Player object of the player who's current turn it is.
     */
    public Player getCurrentPlayer() {
        return current;
    }
    /*
    sets the current player as long as the player arrayList contains this player.
        notifyChange() is an update method in designpatterns.observer.Subject
     */
    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }


    /*
    Returns phase
     */
    public Phase getPhase() {
        return phase;
    }

    /*
    sets the phase with a Phase parameter
            notifyChange() is an update method in designpatterns.observer.Subject

     */
    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    /*
    returns step integer
    */
    public int getStep() {
        return step;
    }
    /*
       sets step integer, but only if it is not the same as it was before and then
        notifyChange() is an update method in designpatterns.observer.Subject
       */
    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }
    /*
       returns isStepMode boolean
       */
    public boolean isStepMode() {
        return stepMode;
    }
    /*
          sets setStepMode boolean, but only if it is not the same as it was before and then
        notifyChange() is an update method in designpatterns.observer.Subject
          */
    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }
    /*
          if the player in the parameters has this board as its board, then it gets the index of that player in the Player arrayList
          if it is not the right board, it returns -1.
    */
    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }

        return getSpace(x, y);
    }

    public String getStatusMessage() {

        // TODO Assignment V1: this string could eventually be refined
        //      The status line should show more information based on
        //      situation; for now, introduce a counter to the Board,
        //      which is counted up every time a player makes a move; the
        //      status line should show the current player and the number
        //      of the current move!
        return "Player = " + getCurrentPlayer().getName() + " |   Turns: " + playerMoves;

    }

    // TODO Assignment V1: add a counter along with a getter and a setter, so the
    //      state the board (game) contains the number of moves, which then can
    //      be used to extend the status message including the number of
    public int getPlayerMoves(){
        return playerMoves;
    }
    public void setPlayerMoves(int moves){
        playerMoves = moves;
    }
}
