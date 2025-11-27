package barman;
import barman.Types.*;
import java.util.List;

public class AchieveContainsShakerIngredient extends Task{

    private final Shaker shaker;
    private final Ingredient ingredient;

    public AchieveContainsShakerIngredient(Shaker shaker, Ingredient ingredient) {

        super("AchieveContainsShakerIngredient", List.of(shaker.getName(),ingredient.getName()));
        this.shaker = shaker;
        this.ingredient = ingredient;

        DomainHelper helper = DomainHelper.getHelper();

        Shot shot = helper.getDefaultObject(Shot.class);
        Hand hand = helper.getDefaultObject(Hand.class);
        Level levelA = helper.getObject("level1", Level.class);
        Level levelB = helper.getObject("level2", Level.class);


        this.addMethod(new AddIngredientToEmptyShaker(shaker,ingredient, levelA, levelB, shot, hand));
        this.addMethod(new AddIngredientToUsedShaker(shaker,ingredient, levelA, levelB, shot, hand));
        this.addMethod(new AddIngredientToShakerNull(shaker,ingredient));
    }
}
