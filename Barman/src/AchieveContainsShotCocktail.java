package barman;
import barman.Types.*;
import java.util.List;

public class AchieveContainsShotCocktail extends Task{

    private final Shot shot;
    private final Cocktail cocktail;

    public AchieveContainsShotCocktail(Shot shot, Cocktail cocktail){
        super("AchieveContainsShotCocktail", List.of(shot.getName(), cocktail.getName()));
        this.shot = shot;
        this.cocktail = cocktail;

        DomainHelper helper = DomainHelper.getHelper();
        Shaker shaker = helper.getObject("shaker1", Shaker.class);
        Hand hand = helper.getRandomEmptyHand();
        this.addMethod(new MakeAndPourCocktail(shot, cocktail, shaker, hand));
        this.addMethod(new MakeAndPourCocktailNull(shot, cocktail));
    }
}
