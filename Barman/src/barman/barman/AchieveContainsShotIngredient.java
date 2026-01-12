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
    private final Hand preferredHand;

    /**
     * Constructor for AchieveContainsShotIngredient
     * 
     * @param shot          The shot to be filled
     * @param ingredient    The ingredient to be added
     * @param preferredHand The preferred hand to be used
     */
    public AchieveContainsShotIngredient(Shot shot, Ingredient ingredient, Hand preferredHand) {
        super("AchieveContainsShotIngredient", List.of(shot.getName(), ingredient.getName()));
        this.shot = shot;
        this.ingredient = ingredient;
        this.preferredHand = preferredHand;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        // Helper to access 'dispenses' relation
        WorldState state = helper.getCurrentState();
        Map<Dispenser, Ingredient> dispensesMap = state.dispenses;

        List<Dispenser> allDispensers = helper.getAllDispensers();
        List<Hand> allHands = helper.getAllHands();

        // Filter dispensers that dispense the WRONG ingredient
        for (Dispenser dispenser : allDispensers) {
            Ingredient dispensed = dispensesMap.get(dispenser);
            if (dispensed == null || !dispensed.equals(ingredient))
                continue;

            for (Hand hand1 : allHands) { // Hand acting
                for (Hand hand2 : allHands) { // Hand holding
                    if (hand1.equals(hand2))
                        continue;
                    if (preferredHand != null && !hand2.equals(preferredHand))
                        continue;

                    // The method AddIngredientToShot requires a shot, an ingredient, a dispenser
                    // and
                    // two hands
                    this.addMethod(new AddIngredientToShot(shot, ingredient, dispenser, hand1, hand2, this));
                }
            }
        }

        // The method AddIngredientToShotNull requires a shot and an ingredient
        this.addMethod(new AddIngredientToShotNull(shot, ingredient, this));
    }
}
