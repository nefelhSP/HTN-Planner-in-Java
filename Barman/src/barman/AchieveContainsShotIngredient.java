package barman;

import barman.Types.*;
import java.util.*;

/**
 * AchieveContainsShotIngredient class that implements the Task interface.
 * It represents the task: AchieveContainsShotIngredient
 */
public class AchieveContainsShotIngredient extends Task {

    private final Shot shot;
    private final Ingredient ingredient;

    /**
     * Constructor for AchieveContainsShotIngredient
     * @param shot          The shot to be filled
     * @param ingredient    The ingredient to be added
     */
    public AchieveContainsShotIngredient(Shot shot, Ingredient ingredient) {
        super("AchieveContainsShotIngredient", List.of(shot.getName(), ingredient.getName()));
        this.shot = shot;
        this.ingredient = ingredient;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        // Helper to access 'dispenses' relation
        WorldState state = helper.getCurrentState();
        Map<Dispenser, Ingredient> dispensesMap = state.dispenses;
        List<Dispenser> allDispensers = helper.getAllItems(Dispenser.class);
        List<Hand> allHands = helper.getAllHands();

        // Skip dispensers that do not dispense the wanted ingredient
        for (Dispenser dispenser : allDispensers) {
            Ingredient dispensed = dispensesMap.get(dispenser);
            if (dispensed == null || !dispensed.equals(ingredient))
                continue;

            for (Hand hand : allHands) {
                for (Hand otherHand : allHands) {
                    if (hand.equals(otherHand))
                        continue;
                    // The method AddIngredientToShot requires a shot, an ingredient, a dispenser and two hands
                    this.addMethod(new AddIngredientToShot(shot, ingredient, dispenser, hand, otherHand, this));
                }
            }
        }

        // The method AddIngredientToShotNull requires a shot and an ingredient
        this.addMethod(new AddIngredientToShotNull(shot, ingredient, this));
    }
}
