package com.javagyan.gameoflife.util;


import java.util.List;

import com.javagyan.gameoflife.model.Universe;
import com.javagyan.gameoflife.service.GameOfLifeService;

/**
 * Singleton class that starts the play.
 *
 * @author Sanjeev Kumar
 *
 */
public final class GameOfLifePlayer {

    /** singleton instance. */
    private static GameOfLifePlayer instance;

    /** A private Constructor prevents any other class from instantiating. */
    private GameOfLifePlayer() {
    }

    /**
     * Instantiate GameOfLifePlayer.
     *
     * @return instance
     */
    public static synchronized GameOfLifePlayer getInstance() {
        if (instance == null) {
            instance = new GameOfLifePlayer();
        }
        return instance;
    }

    /**
     * Delegate the play to GameOfLifeService.
     *
     * @param seed
     * @return list
     */
    public List<Universe> playGameOfLife(final Universe seed) {
        GameOfLifeService gol = new GameOfLifeService();
        return gol.play(seed);
    }
}
