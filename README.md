# slotify4j
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/924211db7498454d9ff17f62a74f3a23)](https://app.codacy.com/app/sta-ger/slotify4j?utm_source=github.com&utm_medium=referral&utm_content=sta-ger/slotify4j&utm_campaign=Badge_Grade_Dashboard)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/a69e6e98142e484991c57fac795c1100)](https://www.codacy.com/app/sta-ger/slotify4j?utm_source=github.com&utm_medium=referral&utm_content=sta-ger/slotify4j&utm_campaign=Badge_Coverage)
[![Build Status](https://travis-ci.org/sta-ger/slotify4j.svg?branch=master)](https://travis-ci.org/sta-ger/slotify4j)
[![Download](https://api.bintray.com/packages/sta-ger/maven/slotify4j/images/download.svg)](https://bintray.com/sta-ger/maven/slotify4j/_latestVersion)

A server-side video slot game logic framework for Java.

[slotify.js](https://github.com/sta-ger/slotify.js) - JavaScript/TypeScript version.

## Usage

### Game Session

Simple casino game logic.

```java
import slotify4j.session.*;

import java.util.Arrays;

public class Game {

    public static void main(String[] args) {
        GameSessionConfig config = DefaultGameSessionConfig.builder()
                .withAvailableBets(new long[]{10, 20, 30})
                .withCreditsAmount(5000)
                .build();

        GameSession session = new GameSessionImpl(config);
        System.out.println(Arrays.toString(session.getAvailableBets())); // [10, 20, 30]
        System.out.println(session.getBet()); // 10
        System.out.println(session.getCreditsAmount()); // 5000

        session.setBet(20);
        System.out.println(session.getBet()); // 20

        try {
            session.play();
            System.out.println(session.getCreditsAmount()); // 4980
        } catch (UnableToPlayException e) {
            // Not enough credits to play
        }
    }

}


```

Video slot game logic.

```java
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.*;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;

import java.util.Arrays;

public class Game {

    public static void main(String[] args) {
        // Create a configuration for 5x4 video slot game with symbols "A", "K", "Q", "J", "10", "9",
        ReelGameSessionConfig config = new DefaultReelGameSessionConfig()
                .builder()
                .withAvailableItems(new String[]{"A", "K", "Q", "J", "10", "9"})
                .withReelsNumber(5)
                .withReelsItemsNumber(4)
                .build();
        ReelGameSession session = new ReelGameSessionImpl(
                config, new ReelGameSessionReelsControllerImpl(config), new ReelGameSessionWinCalculatorImpl(config)
        );

        // Calculated winning amount for 3 symbols "A" with bet value of 10 coins
        System.out.println(session.getPaytable().getWinningAmountForItem("A", 3, 10));

        // Reels number (columns)
        System.out.println(session.getReelsItemsNumber());

        // Number of items on each reel (rows)
        System.out.println(session.getReelsNumber());

        // Distributions of symbols on reels (probabilities)
        Arrays.stream(session.getReelsItemsSequences()).forEach(
                itemsForReel -> System.out.println(Arrays.toString(itemsForReel))
        );

        // Play until any winning
        while (session.getWinningAmount() == 0) {
            // Play game round
            try {
                session.play();
            } catch (UnableToPlayException e) {
                // Not enough credits to play
            }
        }


        // Combination of symbols on reels after play
        Arrays.stream(session.getReelsItems()).forEach(
                itemsForReel -> System.out.println(Arrays.toString(itemsForReel))
        );

        // Total winning amount if there where any winning combinations at last round
        System.out.println(session.getWinningAmount());

        // Winning lines data
        session.getWinningLines().forEach((lineId, lineData) -> {
            // Winning coins amount for line
            System.out.println(lineData.getWinningAmount());

            // Winning item id
            System.out.println(lineData.getItemId());

            // X positions of winning items on reels
            System.out.println(Arrays.toString(lineData.getItemsPositions()));

            // Y positions of winning items on reels
            System.out.println(Arrays.toString(lineData.getDirection()));
        });

        // Winning scatter symbols data
        System.out.println(session.getWinningScatters());


    }

}
```

### Simulation

Simple way to run a lot of game rounds and calculate Return To Player percentage.

```java
import slotify4j.session.UnableToPlayException;
import slotify4j.session.videogames.reelgames.DefaultReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSession;
import slotify4j.session.videogames.reelgames.ReelGameSessionConfig;
import slotify4j.session.videogames.reelgames.ReelGameSessionImpl;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsController;
import slotify4j.session.videogames.reelgames.reelscontroller.ReelGameSessionReelsControllerImpl;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculator;
import slotify4j.session.videogames.reelgames.wincalculator.ReelGameSessionWinCalculatorImpl;
import slotify4j.simulation.DefaultGameSessionSimulationConfig;
import slotify4j.simulation.GameSessionSimulation;
import slotify4j.simulation.GameSessionSimulationConfig;
import slotify4j.simulation.GameSessionSimulationImpl;

public class Game {

    public static void main(String[] args) {
        ReelGameSessionConfig sessionConfig = new DefaultReelGameSessionConfig();
        sessionConfig.setReelsItemsSequences(new String[][]{
                {"J", "9", "Q", "10", "A", "S", "K"},
                {"K", "S", "10", "A", "9", "Q", "J"},
                {"J", "Q", "10", "9", "S", "A", "K"},
                {"Q", "10", "9", "S", "K", "A", "J"},
                {"Q", "A", "J", "10", "9", "S", "K"},
        });
        ReelGameSessionReelsController reelsController = new ReelGameSessionReelsControllerImpl(sessionConfig);
        ReelGameSessionWinCalculator winningCalculator = new ReelGameSessionWinCalculatorImpl(sessionConfig);
        ReelGameSession session = new ReelGameSessionImpl(sessionConfig, reelsController, winningCalculator);
        GameSessionSimulationConfig simulationConfig = new DefaultGameSessionSimulationConfig();
        simulationConfig.setNumberOfRounds(10000);
        GameSessionSimulation simulation = new GameSessionSimulationImpl(session, simulationConfig);

        simulation.setBeforePlayCallback(() -> {
            System.out.println("Before play");

            // Prevent out of credits case
            session.setCreditsAmount(100000);
        });
        simulation.setAfterPlayCallback(() -> System.out.println("After play"));
        simulation.setOnFinishedCallback(() -> System.out.println("Simulation finished"));

        try {
            // 10000 rounds will be played
            simulation.run();
        } catch (UnableToPlayException e) {
            // Not enough credits to play
        }

        // Rtp for current session (about 50-60% with symbols distributions specified earlier at session config)
        System.out.println(simulation.getRtp());

    }

} 
```
