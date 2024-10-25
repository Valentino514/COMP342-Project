import java.util.ArrayList;

public class SpaceCatalog {
    private ArrayList<Space> spaces = new ArrayList<>();

    // add a new space to space catalog
    public void addSpace(Space space) {
        if (!spaceExists(space.getAddress())) {
            spaces.add(space);
            System.out.println("Space added: " + space.getAddress());
        } else {
            System.out.println("Space already exists: " + space.getAddress());
        }
    }

    //checks if space exists
    public boolean spaceExists(String address) {
        for (Space space : spaces) {
            if (space.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }
}
