package barman;
import java.util.List;
import barman.Types.*;

public class AchieveContainsShakerCocktail extends Task{

    private final Shaker shaker;
    private final Cocktail cocktail;

    public AchieveContainsShakerCocktail(Shaker shaker, Cocktail cocktail) {

        super("AchieveContainsShakerCocktail", List.of(shaker.getName(),cocktail.getName()));
        this.shaker = shaker;
        this.cocktail = cocktail;

        DomainHelper helper = DomainHelper.getHelper();

        Ingredient ingredientA = helper.getCocktailIngredient(cocktail, true);
        Ingredient ingredientB = helper.getCocktailIngredient(cocktail, false);
        Hand handA = helper.getDefaultObject(Hand.class);
        Hand handB = helper.getDefaultObject(Hand.class);

        this.addMethod(new MakeCocktail(shaker,cocktail,ingredientA,handA,handB,ingredientB));
        this.addMethod(new MakeCocktailNull(shaker,cocktail));
    }

}
