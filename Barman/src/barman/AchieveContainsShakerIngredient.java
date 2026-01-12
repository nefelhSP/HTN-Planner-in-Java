package barman;

import barman.Types.*;
import java.util.*;

/**
 * AchieveContainsShakerIngredient class that implements the Task interface.
 * It represents the task: AchieveContainsShakerIngredient
 */
public class AchieveContainsShakerIngredient extends Task {

    private final Shaker shaker;
    private final Ingredient ingredient;

    /**
     * Constructor for AchieveContainsShakerIngredient
     * 
     * @param shaker     The shaker to be filled
     * @param ingredient The ingredient to be added
     */
    public AchieveContainsShakerIngredient(Shaker shaker, Ingredient ingredient) {
        super("AchieveContainsShakerIngredient", List.of(shaker.getName(), ingredient.getName()));
        this.shaker = shaker;
        this.ingredient = ingredient;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Hand> allHands = helper.getAllHands();
        allHands.sort((hand1, hand2) -> hand1.getName().compareTo(hand2.getName()));
        List<Shot> allShots = helper.getAllShots();
        List<Level> allLevels = helper.getAllLevels();

        // Get the current state
        WorldState state = helper.getCurrentState();
        // Get the next relation
        Map<Level, Level> nextMap = state.next;
        for (Hand hand : allHands) {
            for (Hand otherHand : allHands) {
                if (hand.equals(otherHand))
                    continue;

                for (Shot shot : allShots) {
                    for (Level level1 : allLevels) {
                        // Only use valid next(level1 -> level2 -> level3)
                        Level level2 = nextMap.get(level1);
                        if (level2 != null) {
                            // The method AddIngredientToEmptyShaker requires a shaker, an ingredient, two
                            // levels, a shot and two hands
                            this.addMethod(
                                    new AddIngredientToEmptyShaker(shaker, ingredient, level1, level2, shot, hand,
                                            otherHand, this));

                            // The method AddIngredientToUsedShaker requires a shaker, an ingredient, two
                            // levels, a shot and two hands
                            this.addMethod(
                                    new AddIngredientToUsedShaker(shaker, ingredient, level1, level2, shot, hand,
                                            otherHand, this));
                        }
                    }
                }
            }
        }

        // The method AddIngredientToShakerNull requires a shaker and an ingredient
        this.addMethod(new AddIngredientToShakerNull(shaker, ingredient, this));
    }
}
