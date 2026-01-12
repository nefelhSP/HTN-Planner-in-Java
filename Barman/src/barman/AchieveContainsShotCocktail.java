package barman;

import barman.Types.*;
import java.util.List;

/**
 * AchieveContainsShotCocktail class that implements the Task interface.
 * It represents the task: AchieveContainsShotCocktail
 */
public class AchieveContainsShotCocktail extends Task {

    private final Shot shot;
    private final Cocktail cocktail;

    /**
     * Constructor for AchieveContainsShotCocktail
     * 
     * @param shot     The shot to be filled
     * @param cocktail The cocktail to be added
     */
    public AchieveContainsShotCocktail(Shot shot, Cocktail cocktail) {
        super("AchieveContainsShotCocktail", List.of(shot.getName(), cocktail.getName()));
        this.shot = shot;
        this.cocktail = cocktail;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Shaker> allShakers = helper.getAllShakers();
        List<Hand> allHands = helper.getAllHands();
        allHands.sort((hand1, hand2) -> hand1.getName().compareTo(hand2.getName())); // Sort ascending (left > right)

        // The method MakeAndPourCocktail requires a Shot, a Cocktail, a Shaker and a
        // Hand
        for (Shaker shaker : allShakers) {
            for (Hand hand : allHands) {
                this.addMethod(new MakeAndPourCocktail(shot, cocktail, shaker, hand, this));
            }
        }

        // The method MakeAndPourCocktailNull requires a Shot and a Cocktail
        this.addMethod(new MakeAndPourCocktailNull(shot, cocktail, this));
    }
}
