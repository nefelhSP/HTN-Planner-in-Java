package barman;

import barman.Types.*;
import java.util.*;

/**
 * The Cleanup class represents the cleanup actions to bring the world state to
 * a clean state after decomposing each task.
 * It cleans the state preserving the decomposed tasks' goals.
 */
public class Cleanup {
    /**
     * Empty constructor for Cleanup
     */
    public Cleanup() {

    }

    /**
     * Generates a cleanup plan to bring the world state to a clean stat
     * 
     * @param currentState The current state of the world
     * @param goalShots    The shots that should not be cleaned
     * @return A list of cleanup actions
     */
    public List<Action> generateCleanupPlan(WorldState currentState, Set<Shot> goalShots) {

        List<Action> cleanupActions = new ArrayList<>();
        WorldState state = currentState.copyState();
        DomainHelper helper = DomainHelper.getHelper();

        // Get Hands, Shaker and Levels using DomainHelper
        List<Hand> hands = helper.getAllHands();
        if (hands.size() < 2) {
            throw new IllegalStateException("Not enough hands found in the domain.");
        }
        Hand leftHand = hands.get(0);
        Hand rightHand = hands.get(1);

        Shaker shaker = helper.getAllItems(Shaker.class).get(0);
        Level level1 = helper.getItem("level1", Level.class);
        Level level2 = helper.getItem("level2", Level.class);
        Level level3 = helper.getItem("level3", Level.class);

        // Determine the target level based on the current level
        Level shakerLevel = state.shakerLevel.get(shaker);
        Level targetLevel = null;
        if (shakerLevel != null) {
            if (shakerLevel.equals(level3)) {
                targetLevel = level2;
            } else if (shakerLevel.equals(level2)) {
                targetLevel = level1;
            }
        }

        // Put down anything that is being held
        // If the shaker is already in hand and dirty, clean it first before dropping
        if (!state.clean.contains(shaker)) {
            if (state.holding.get(leftHand) == shaker) {
                emptyShaker(state, cleanupActions, shaker, leftHand, targetLevel);
                if (state.empty.contains(shaker) && state.handEmpty.contains(rightHand)) {
                    cleanupActions.add(new clean_shaker(shaker, leftHand, rightHand));
                    state.clean.add(shaker);
                }
                cleanupActions.add(new drop(leftHand, shaker));
                state.holding.remove(leftHand);
                state.handEmpty.add(leftHand);
                state.onTable.add(shaker);
            } else if (state.holding.get(rightHand) == shaker) {
                emptyShaker(state, cleanupActions, shaker, rightHand, targetLevel);
                if (state.empty.contains(shaker) && state.handEmpty.contains(leftHand)) {
                    cleanupActions.add(new clean_shaker(shaker, rightHand, leftHand));
                    state.clean.add(shaker);
                }
                cleanupActions.add(new drop(rightHand, shaker));
                state.holding.remove(rightHand);
                state.handEmpty.add(rightHand);
                state.onTable.add(shaker);
            } else {
                dropAllHeldContainers(state, cleanupActions);
            }
        } else {
            dropAllHeldContainers(state, cleanupActions);
        }

        // Clean up the shaker
        cleanupShaker(state, cleanupActions, shaker, leftHand, rightHand, targetLevel);

        // Clean the dirty shots (except the goal containers)
        cleanupDirtyShots(state, cleanupActions, goalShots, leftHand, rightHand);

        return cleanupActions;
    }

    // Drops all containers that are currently being held
    private void dropAllHeldContainers(WorldState state, List<Action> cleanupActions) {
        Map<Hand, Container> heldItems = new HashMap<>(state.holding);

        // Iterate through each pair of the heldItems Map
        for (Map.Entry<Hand, Container> entry : heldItems.entrySet()) {
            Hand hand = entry.getKey();
            Container container = entry.getValue();

            cleanupActions.add(new drop(hand, container));

            state.holding.remove(hand);
            state.handEmpty.add(hand);
            state.onTable.add(container);
        }
    }

    // Cleans up the shaker by emptying it, cleaning it, and putting it down
    private void cleanupShaker(WorldState state, List<Action> cleanupActions, Shaker shaker, Hand leftHand,
            Hand rightHand, Level fallbackLevel) {

        if (shaker != null) {

            boolean needsCleanup = (!state.clean.contains(shaker) || !state.empty.contains(shaker)
                    || state.shaked.contains(shaker));

            if (!needsCleanup) {
                return;
            }

            // Grab the shaker if it's on the table
            if (state.onTable.contains(shaker)) {
                if (state.handEmpty.contains(leftHand)) {
                    cleanupActions.add(new grasp(leftHand, shaker));
                    state.holding.put(leftHand, shaker);
                    state.handEmpty.remove(leftHand);
                    state.onTable.remove(shaker);

                    // Empty the shaker if it has contents
                    emptyShaker(state, cleanupActions, shaker, leftHand, fallbackLevel);

                    // Clean the shaker
                    if (!state.clean.contains(shaker) && state.empty.contains(shaker)) {
                        if (state.holding.get(leftHand) == shaker && state.handEmpty.contains(rightHand)) {
                            cleanupActions.add(new clean_shaker(shaker, leftHand, rightHand));
                            state.clean.add(shaker);
                        }
                    }

                    // Drop the shaker
                    if (state.holding.get(leftHand) == shaker) {
                        cleanupActions.add(new drop(leftHand, shaker));
                        state.holding.remove(leftHand);
                        state.handEmpty.add(leftHand);
                        state.onTable.add(shaker);
                    }

                } else if (state.handEmpty.contains(rightHand)) {
                    cleanupActions.add(new grasp(rightHand, shaker));
                    state.holding.put(rightHand, shaker);
                    state.handEmpty.remove(rightHand);
                    state.onTable.remove(shaker);

                    // Empty the shaker if it has contents
                    emptyShaker(state, cleanupActions, shaker, rightHand, fallbackLevel);

                    // Clean the shaker
                    if (!state.clean.contains(shaker) && state.empty.contains(shaker)) {
                        if (state.holding.get(rightHand) == shaker && state.handEmpty.contains(leftHand)) {
                            cleanupActions.add(new clean_shaker(shaker, rightHand, leftHand));
                            state.clean.add(shaker);
                        }
                    }

                    // Drop the shaker
                    if (state.holding.get(rightHand) == shaker) {
                        cleanupActions.add(new drop(rightHand, shaker));
                        state.holding.remove(rightHand);
                        state.handEmpty.add(rightHand);
                        state.onTable.add(shaker);
                    }
                }
                return;
            }
        }
    }

    // Empties the shaker if it contains something
    private void emptyShaker(WorldState state, List<Action> cleanupActions, Shaker shaker, Hand hand,
            Level fallbackLevel) {

        boolean hasSomethingInside = (!state.empty.contains(shaker) || state.shaked.contains(shaker));
        if (!hasSomethingInside) {
            return;
        }

        Set<Beverage> contents = state.contains.get(shaker);
        if (contents == null || contents.isEmpty()) {
            return;
        }

        for (Beverage beverage : new HashSet<>(contents)) {
            if (!(beverage instanceof Cocktail)) {
                continue;
            }

            Cocktail cocktail = (Cocktail) beverage;

            Level currentLevel = state.shakerLevel.get(shaker);
            Level emptyLevel = state.shakerEmptyLevel.get(shaker);

            if ((currentLevel != null) && (emptyLevel != null) && (hand != null)) {
                cleanupActions.add(new empty_shaker(hand, shaker, cocktail, currentLevel, emptyLevel));

                state.shakerLevel.put(shaker, emptyLevel);
                state.empty.add(shaker);
                state.unshaked.add(shaker);
                state.shaked.remove(shaker);
                contents.remove(beverage);
            }
            break;
        }
    }

    // Cleans up all dirty shots (except goal ones)
    private void cleanupDirtyShots(WorldState state, List<Action> cleanupActions, Set<Shot> goalContainers,
            Hand leftHand, Hand rightHand) {

        DomainHelper helper = DomainHelper.getHelper();
        List<Shot> shots = helper.getAllItems(Shot.class);

        for (Shot shot : shots) {
            // Skip if the current shot is part of a previous goal
            if (goalContainers.contains(shot)) {
                continue;
            }

            // Skip if the shot is already clean
            if (state.clean.contains(shot)) {
                continue;
            }

            Beverage usedBeverage = state.used.get(shot);
            if (usedBeverage == null) {
                continue;
            }

            if (!state.onTable.contains(shot)) {
                continue;
            }

            if (state.handEmpty.contains(leftHand)) {
                // Grasp
                cleanupActions.add(new grasp(leftHand, shot));
                state.holding.put(leftHand, shot);
                state.handEmpty.remove(leftHand);
                state.onTable.remove(shot);

                // Empty
                Set<Beverage> contents = state.contains.get(shot);
                if (contents != null && !contents.isEmpty()) {
                    for (Beverage beverage : new HashSet<>(contents)) {
                        cleanupActions.add(new empty_shot(leftHand, shot, beverage));
                        contents.remove(beverage);
                    }
                    state.empty.add(shot);
                }

                // Clean
                if (state.empty.contains(shot) && state.handEmpty.contains(rightHand)) {
                    cleanupActions.add(new clean_shot(shot, usedBeverage, leftHand, rightHand));
                    state.clean.add(shot);
                    state.used.remove(shot);
                }

                // Drop
                cleanupActions.add(new drop(leftHand, shot));
                state.holding.remove(leftHand);
                state.handEmpty.add(leftHand);
                state.onTable.add(shot);

            } else if (state.handEmpty.contains(rightHand)) {
                // Grasp
                cleanupActions.add(new grasp(rightHand, shot));
                state.holding.put(rightHand, shot);
                state.handEmpty.remove(rightHand);
                state.onTable.remove(shot);

                // Empty
                Set<Beverage> contents = state.contains.get(shot);
                if (contents != null && !contents.isEmpty()) {
                    for (Beverage bev : new HashSet<>(contents)) {
                        cleanupActions.add(new empty_shot(rightHand, shot, bev));
                        contents.remove(bev);
                    }
                    state.empty.add(shot);
                }

                // Clean
                if (state.empty.contains(shot) && state.handEmpty.contains(leftHand)) {
                    cleanupActions.add(new clean_shot(shot, usedBeverage, rightHand, leftHand));
                    state.clean.add(shot);
                    state.used.remove(shot);
                }

                // Drop
                cleanupActions.add(new drop(rightHand, shot));
                state.holding.remove(rightHand);
                state.handEmpty.add(rightHand);
                state.onTable.add(shot);
            }
        }
    }
}