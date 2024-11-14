import java.util.ArrayList;

public class SpaceCatalog {
    private static final ArrayList<Space> spaceCatalog = new ArrayList<>();

    // add a new space to space catalog
    public static void addSpace(Space space) {
        if (!spaceExists(space.getAddress())) {
            spaceCatalog.add(space);
            System.out.println("Space added: " + space.getAddress());
        } else {
            System.out.println("Space already exists: " + space.getAddress());
        }
    }


    //checks if space exists
    public static boolean spaceExists(String address) {
        for (Space space : spaceCatalog) {
            if (space.getAddress().equals(address)) {
                return true;
            }
        }
        System.out.println("space does not exist");
        return false;
    }

    //print all the sapces in the system
    public static void printSpaces() {
        if (spaceCatalog.isEmpty()) {
            System.out.println("No spaces available in the catalog.");
        } else {
            for (Space space : spaceCatalog) {
                System.out.println("Address: " + space.getAddress());
                System.out.println("Type: " + space.getType());
                System.out.println("City: " + space.getCity());
                System.out.println("Is Rented: " + space.isRented());
                System.out.println("Person Limit: " + space.getPersonLimit());
                System.out.println("---------------------------------");
            }
        }
    }


    public static Space findSpace(String spaceId) {
        for (Space space : spaceCatalog) {
            if (space.getSpaceId().equals(spaceId)) {
                return space;
            }
        }
        System.out.println("Space not found with id: " + spaceId);
        return null;
    }
}
