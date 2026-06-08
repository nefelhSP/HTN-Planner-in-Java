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
        List<Shot> allShots = helper.getAllItems(Shot.class);
        List<Level> allLevels = helper.getAllItems(Level.class);

        // Generates the methods for the task
                for (Shot shot : allShots) {
                    for (Level level1 : allLevels) {
                        Level level2 = null;
                        if (level1.getName().equals("level1")) {
                            for (Level level : allLevels) {
                                if (level.getName().equals("level2")) {
                                    level2 = level;
                                    break;
                                }
                            }
                        } else if (level1.getName().equals("level2")) {
                            for (Level level : allLevels) {
                                if (level.getName().equals("level3")) {
                                    level2 = level;
                                    break;
                                }
                            }
                        }
                        if (level2 != null) {
                            for (Hand hand : allHands) {

                                // The method AddIngredientToEmptyShaker requires a shaker, an ingredient, two levels, a shot and a hand
                                this.addMethod(new AddIngredientToEmptyShaker(shaker, ingredient, level1, level2, shot, hand, this));

                                // The method AddIngredientToUsedShaker requires a shaker, an ingredient, two levels, a shot and a hand
                                this.addMethod(new AddIngredientToUsedShaker(shaker, ingredient, level1, level2, shot, hand, this));
                            }
                        }
                    }
                }

        // The method AddIngredientToShakerNull requires a shaker and an ingredient
        this.addMethod(new AddIngredientToShakerNull(shaker, ingredient, this));
    }
}
