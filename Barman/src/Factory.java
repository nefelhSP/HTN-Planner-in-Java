package barman;
import barman.Types.*;
import java.util.*;

public class Factory {

    public static Task createTask(String taskName, String[] params) {
        DomainHelper helper = DomainHelper.getHelper();

        switch (taskName) {
            case "AchieveContainsShakerIngredient":
                if (params.length < 2) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker s1 = helper.getObject(params[0], Shaker.class);
                Ingredient i1 = helper.getObject(params[1], Ingredient.class);
                return new AchieveContainsShakerIngredient(s1, i1);

            case "AchieveCleanShaker":
                if (params.length < 1) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker s2 = helper.getObject(params[0], Shaker.class);
                return new AchieveCleanShaker(s2);

            case "AchieveHandEmpty":
                if (params.length < 1) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Hand h1 = helper.getObject(params[0], Hand.class);
                return new AchieveHandEmpty(h1);

            case "AchieveContainsShotIngredient":
                if (params.length < 2) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot st1 = helper.getObject(params[0], Shot.class);
                Ingredient i2 = helper.getObject(params[1], Ingredient.class);
                return new AchieveContainsShotIngredient(st1, i2);

            case "AchieveContainsShakerCocktail":
                if (params.length < 2) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker s3 = helper.getObject(params[0], Shaker.class);
                Cocktail c1 = helper.getObject(params[1], Cocktail.class);
                return new AchieveContainsShakerCocktail(s3, c1);

            case "DoPourShakerToShot":
                if (params.length < 3) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shaker s4 = helper.getObject(params[0], Shaker.class);
                Shot st2 = helper.getObject(params[1], Shot.class);
                Cocktail c2 = helper.getObject(params[2], Cocktail.class);
                return new DoPourShakerToShot(s4, st2, c2);

            case "AchieveOnTable":
                if (params.length < 1) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Container co1 = helper.getObject(params[0], Container.class);
                return new AchieveOnTable(co1);

            case "AchieveHolding":
                if (params.length < 2) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Hand h2 = helper.getObject(params[0], Hand.class);
                Container co2 = helper.getObject(params[1], Container.class);
                return new AchieveHolding(h2, co2);

            case "AchieveCleanShot":
                if (params.length < 1) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot st3 = helper.getObject(params[0], Shot.class);
                return new AchieveCleanShot(st3);

            case "AchieveContainsShotCocktail":
                if (params.length < 2) throw new IllegalArgumentException("Missing arguments for " + taskName);
                Shot st4 = helper.getObject(params[0], Shot.class);
                Cocktail c3 = helper.getObject(params[1], Cocktail.class);
                return new AchieveContainsShotCocktail(st4, c3);

            default:
                System.err.println("Unknown Task: " + taskName);
                return null;
        }
    }

    public static Method createMethod(String methodName, String[] params) {
        DomainHelper helper = DomainHelper.getHelper();

        switch (methodName) {
            // (:method MakeAndPourCocktail
            //    :parameters (?x_0 - shot ?x_1 - cocktail ?x_2 - shaker ?x_3 - hand) ...)
            case "MakeAndPourCocktail":
                if (params.length < 4) throw new IllegalArgumentException("Missing args for " + methodName);
                Shot shot_mapc = helper.getObject(params[0], Shot.class);
                Cocktail cock_mapc = helper.getObject(params[1], Cocktail.class);
                Shaker shak_mapc = helper.getObject(params[2], Shaker.class);
                Hand hand_mapc = helper.getObject(params[3], Hand.class);
                return new MakeAndPourCocktail(shot_mapc, cock_mapc, shak_mapc, hand_mapc);

            case "MakeAndPourCocktailNull":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + methodName);
                Shot shot_null = helper.getObject(params[0], Shot.class);
                Cocktail cock_null = helper.getObject(params[1], Cocktail.class);
                return new MakeAndPourCocktailNull(shot_null, cock_null);

            // (:method MakeCocktail
            //    :parameters (?x_0 - shaker ?x_1 - cocktail ?x_2 - ingredient ?x_3 - hand ?x_4 - hand ?x_5 - ingredient) ...)
            case "MakeCocktail":
                if (params.length < 6) throw new IllegalArgumentException("Missing args for " + methodName);
                Shaker s_mc = helper.getObject(params[0], Shaker.class);
                Cocktail c_mc = helper.getObject(params[1], Cocktail.class);
                Ingredient i2_mc = helper.getObject(params[2], Ingredient.class);
                Hand h1_mc = helper.getObject(params[3], Hand.class);
                Hand h2_mc = helper.getObject(params[4], Hand.class);
                Ingredient i1_mc = helper.getObject(params[5], Ingredient.class);
                return new MakeCocktail(s_mc, c_mc, i2_mc, h1_mc, h2_mc, i1_mc);

            case "MakeCocktailNull":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + methodName);
                Shaker s_mcn = helper.getObject(params[0], Shaker.class);
                Cocktail c_mcn = helper.getObject(params[1], Cocktail.class);
                return new MakeCocktailNull(s_mcn, c_mcn);

            // (:method AddIngredientToEmptyShaker ...)
            case "AddIngredientToEmptyShaker":
                if (params.length < 6) throw new IllegalArgumentException("Missing args for " + methodName);
                // Parsing parameters: shaker, ingredient, level, level, shot, hand
                Shaker s_aie = helper.getObject(params[0], Shaker.class);
                Ingredient i_aie = helper.getObject(params[1], Ingredient.class);
                Level l1_aie = helper.getObject(params[2], Level.class);
                Level l2_aie = helper.getObject(params[3], Level.class);
                Shot sh_aie = helper.getObject(params[4], Shot.class);
                Hand h_aie = helper.getObject(params[5], Hand.class);
                return new AddIngredientToEmptyShaker(s_aie, i_aie, l1_aie, l2_aie, sh_aie, h_aie);

            case "AddIngredientToUsedShaker":
                if (params.length < 6) throw new IllegalArgumentException("Missing args for " + methodName);
                Shaker s_aiu = helper.getObject(params[0], Shaker.class);
                Ingredient i_aiu = helper.getObject(params[1], Ingredient.class);
                Level l1_aiu = helper.getObject(params[2], Level.class);
                Level l2_aiu = helper.getObject(params[3], Level.class);
                Shot sh_aiu = helper.getObject(params[4], Shot.class);
                Hand h_aiu = helper.getObject(params[5], Hand.class);
                return new AddIngredientToUsedShaker(s_aiu, i_aiu, l1_aiu, l2_aiu, sh_aiu, h_aiu);

            // (:method AddIngredientToShot ...)
            case "AddIngredientToShot":
                if (params.length < 5) throw new IllegalArgumentException("Missing args for " + methodName);
                Shot shot_ais = helper.getObject(params[0], Shot.class);
                Ingredient ing_ais = helper.getObject(params[1], Ingredient.class);
                Dispenser disp_ais = helper.getObject(params[2], Dispenser.class);
                Hand h1_ais = helper.getObject(params[3], Hand.class);
                Hand h2_ais = helper.getObject(params[4], Hand.class);
                return new AddIngredientToShot(shot_ais, ing_ais, disp_ais, h1_ais, h2_ais);

            case "CleanFullShot":
                if (params.length < 4) throw new IllegalArgumentException("Missing args for " + methodName);
                Shot s_cfs = helper.getObject(params[0], Shot.class);
                Hand h1_cfs = helper.getObject(params[1], Hand.class);
                Beverage b_cfs = helper.getObject(params[2], Beverage.class);
                Hand h2_cfs = helper.getObject(params[3], Hand.class);
                return new CleanFullShot(s_cfs, h1_cfs, b_cfs, h2_cfs);

            case "CleanEmptyShot":
                if (params.length < 4) throw new IllegalArgumentException("Missing args for " + methodName);
                Shot s_ces = helper.getObject(params[0], Shot.class);
                Hand h1_ces = helper.getObject(params[1], Hand.class);
                Beverage b_ces = helper.getObject(params[2], Beverage.class);
                Hand h2_ces = helper.getObject(params[3], Hand.class);
                return new CleanEmptyShot(s_ces, h1_ces, b_ces, h2_ces);

            case "CleanFullShaker":
                if (params.length < 6) throw new IllegalArgumentException("Missing args for " + methodName);
                Shaker s_cfsh = helper.getObject(params[0], Shaker.class);
                Level l1_cfsh = helper.getObject(params[1], Level.class);
                Cocktail c_cfsh = helper.getObject(params[2], Cocktail.class);
                Hand h1_cfsh = helper.getObject(params[3], Hand.class);
                Hand h2_cfsh = helper.getObject(params[4], Hand.class);
                Level l2_cfsh = helper.getObject(params[5], Level.class);
                return new CleanFullShaker(s_cfsh, l1_cfsh, c_cfsh, h1_cfsh, h2_cfsh, l2_cfsh);

            case "CleanEmptyShaker":
                if (params.length < 3) throw new IllegalArgumentException("Missing args for " + methodName);
                Shaker s_cesh = helper.getObject(params[0], Shaker.class);
                Hand h1_cesh = helper.getObject(params[1], Hand.class);
                Hand h2_cesh = helper.getObject(params[2], Hand.class);
                return new CleanEmptyShaker(s_cesh, h1_cesh, h2_cesh);

            case "PickUp":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + methodName);
                Hand h_pu = helper.getObject(params[0], Hand.class);
                Container c_pu = helper.getObject(params[1], Container.class);
                return new PickUp(h_pu, c_pu);

            case "PutDown":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + methodName);
                Container c_pd = helper.getObject(params[0], Container.class);
                Hand h_pd = helper.getObject(params[1], Hand.class);
                return new PutDown(c_pd, h_pd);

            default:
                System.err.println("Unknown Method: " + methodName);
                return null;
        }
    }

    // --- ACTION CREATION ---
    public static Action createAction(String actionName, String[] params) {
        DomainHelper helper = DomainHelper.getHelper();

        switch (actionName) {
            case "clean-shot":
                if (params.length < 4) throw new IllegalArgumentException("Missing args for " + actionName);
                Shot s_cs = helper.getObject(params[0], Shot.class);
                Beverage b_cs = helper.getObject(params[1], Beverage.class);
                Hand h1_cs = helper.getObject(params[2], Hand.class);
                Hand h2_cs = helper.getObject(params[3], Hand.class);
                return new clean_shot(s_cs, b_cs, h1_cs, h2_cs);

            case "clean-shaker":
                if (params.length < 3) throw new IllegalArgumentException("Missing args for " + actionName);
                Shaker s_csh = helper.getObject(params[0], Shaker.class);
                Hand h1_csh = helper.getObject(params[1], Hand.class);
                Hand h2_csh = helper.getObject(params[2], Hand.class);
                return new clean_shaker(s_csh, h1_csh, h2_csh);

            case "grasp":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + actionName);
                Hand h_g = helper.getObject(params[0], Hand.class);
                Container c_g = helper.getObject(params[1], Container.class);
                return new grasp(h_g, c_g);

            case "drop":
                if (params.length < 2) throw new IllegalArgumentException("Missing args for " + actionName);
                Hand h_d = helper.getObject(params[0], Hand.class);
                Container c_d = helper.getObject(params[1], Container.class);
                return new drop(h_d, c_d);

            case "fill-shot":
                // shot, ingredient, hand, hand, dispenser
                if (params.length < 5) throw new IllegalArgumentException("Missing args for " + actionName);
                Shot s_fs = helper.getObject(params[0], Shot.class);
                Ingredient i_fs = helper.getObject(params[1], Ingredient.class);
                Hand h1_fs = helper.getObject(params[2], Hand.class);
                Hand h2_fs = helper.getObject(params[3], Hand.class);
                Dispenser d_fs = helper.getObject(params[4], Dispenser.class);
                return new fill_shot(s_fs, i_fs, h1_fs, h2_fs, d_fs);

            case "shake":
                if (params.length < 6) throw new IllegalArgumentException("Missing args for " + actionName);
                Cocktail c_sh = helper.getObject(params[0], Cocktail.class);
                Ingredient i1_sh = helper.getObject(params[1], Ingredient.class);
                Ingredient i2_sh = helper.getObject(params[2], Ingredient.class);
                Shaker s_sh = helper.getObject(params[3], Shaker.class);
                Hand h1_sh = helper.getObject(params[4], Hand.class);
                Hand h2_sh = helper.getObject(params[5], Hand.class);
                return new shake(c_sh, i1_sh, i2_sh, s_sh, h1_sh, h2_sh);

            // Add other Actions (pour-shaker-to-shot, etc.)

            default:
                System.err.println("Unknown Action: " + actionName);
                return null;
        }
    }
}