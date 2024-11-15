import java.util.ArrayList;

public class OrganizationCatalog {
    private ArrayList<Organization> organizations;

    // constructor
    public OrganizationCatalog() {
        this.organizations = new ArrayList<>();
    }

    // Method to add an organization to the catalog
    public void addOrganization(Organization organization) {
        organizations.add(organization);
        System.out.println(organization.getName() + " has been added to the catalog.");
    }

    // Method to remove an organization from the catalog
    public void removeOrganization(Organization organization) {
        if (organizations.remove(organization)) {
            System.out.println(organization.getName() + " has been removed from the catalog.");
        } else {
            System.out.println(organization.getName() + " is not found in the catalog.");
        }
    }

    // Method to view all organizations in the catalog
    public void viewOrganizations() {
        System.out.println("Organizations in the Catalog:");
        for (Organization organization : organizations) {
            System.out.println("- " + organization.getName() + " (Address: " + organization.getAddress() + ")");
        }
    }

    // Method to get the list of organizations
    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }
}

