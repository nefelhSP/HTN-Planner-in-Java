package barman;
import barman.Types.*;
import java.util.List;

public class AchieveContainsShotIngredient extends Task{

    private final Shot shot;
    private final Ingredient ingredient;

    public AchieveContainsShotIngredient(Shot shot, Ingredient ingredient) {

        super("AchieveContainsShotIngredient", List.of(shot.getName(),ingredient.getName()));
        this.shot = shot;
        this.ingredient = ingredient;

        DomainHelper helper = DomainHelper.getHelper();
        Dispenser dispenser = helper.getDefaultObject(Dispenser.class);
        Hand handA = helper.getDefaultObject(Hand.class);
        Hand handB = helper.getDefaultObject(Hand.class);

        this.addMethod(new AddIngredientToShot(shot, ingredient, dispenser, handA, handB));
        this.addMethod(new AddIngredientToShotNull(shot, ingredient));
    }
}


