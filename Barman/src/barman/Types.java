package barman;

/**
 * The Types class is a class that represents the objects' types in the domain.
 * It uses nested classes to group objects based on the domain.
 */
public class Types {

    // Basic class for all objects in the domain
    public static abstract class Anything {
        protected String name;

        public Anything(String name) {
            this.name = name;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Anything anything = (Anything) o;
            return name.equals(anything.name);
        }
    }

    // Basic class for all objects of type Beverage: Ingredient, Cocktail
    public static abstract class Beverage extends Anything {
        public Beverage(String name) {
            super(name);
        }
    }

    // Basic class for all objects of type Container: Shot, Shaker
    public static abstract class Container extends Anything {
        public Container(String name) {
            super(name);
        }
    }

    public static class Ingredient extends Beverage {
        public Ingredient(String name) {
            super(name);
        }
    }

    public static class Cocktail extends Beverage {
        public Cocktail(String name) {
            super(name);
        }
    }

    public static class Shaker extends Container {
        public Shaker(String name) {
            super(name);
        }
    }

    public static class Shot extends Container {
        public Shot(String name) {
            super(name);
        }
    }

    // Dispenser that has a specific Ingredient
    public static class Dispenser extends Anything {
        private final Ingredient dispensedIngredient;

        public Dispenser(String name, Ingredient ingredient) {
            super(name);
            this.dispensedIngredient = ingredient;
        }

        public Ingredient getDispensedIngredient() {
            return dispensedIngredient;
        }
    }

    public static class Level extends Anything {
        public Level(String name) {
            super(name);
        }
    }

    public static class Hand extends Anything {
        public Hand(String side) {
            super(side);
        }
    }

}
