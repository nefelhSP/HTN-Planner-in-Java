package barman;

import java.util.List;
import barman.Types.*;

/**
 * AchieveContainsShakerCocktail class that implements the Task interface.
 * It represents the task: AchieveContainsShakerCocktail
 */
public class AchieveContainsShakerCocktail extends Task {

    private final Shaker shaker;
    private final Cocktail cocktail;

    /**
     * Constructor for AchieveContainsShakerCocktail
     * 
     * @param shaker   The shaker to be filled
     * @param cocktail The cocktail to be added
     */
    public AchieveContainsShakerCocktail(Shaker shaker, Cocktail cocktail) {
        super("AchieveContainsShakerCocktail", List.of(shaker.getName(), cocktail.getName()));
        this.shaker = shaker;
        this.cocktail = cocktail;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Hand> allHands = helper.getAllHands();
        List<Ingredient> allIngredients = helper.getAllIngredients();

        // The method MakeCocktail requires two hands, two ingredients, a cocktail and a
        // shaker
        for (Hand hand1 : allHands) {
            for (Hand hand2 : allHands) {
                if (hand1.equals(hand2))
                    continue;

                for (Ingredient ingredient1 : allIngredients) {
                    for (Ingredient ingredient2 : allIngredients) {
                        if (ingredient1.equals(ingredient2))
                            continue;

                        this.addMethod(
                                new MakeCocktail(shaker, cocktail, ingredient2, hand1, hand2, ingredient1, this));
                    }
                }
            }
        }
        // The method MakeCocktailNull requires a shaker and a cocktail
        this.addMethod(new MakeCocktailNull(shaker, cocktail, this));
    }
}
