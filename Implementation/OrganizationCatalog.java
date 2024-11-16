import java.util.ArrayList;

public class OrganizationCatalog {
    private ArrayList<Organization> organizations;

    // constructor
    public OrganizationCatalog() {
        this.organizations = new ArrayList<>();
    }

    //add an organization
    public void addOrganization(Organization organization) {
        organizations.add(organization);
        System.out.println(organization.getName() + " has been added to the catalog.");
    }

    //remove an organization
    public void removeOrganization(Organization organization) {
        if (organizations.remove(organization)) {
            System.out.println(organization.getName() + " has been removed from the catalog.");
        } else {
            System.out.println(organization.getName() + " is not found in the catalog.");
        }
    }

    //view all organization
    public void viewOrganizations() {
        System.out.println("Organizations in the Catalog:");
        for (Organization organization : organizations) {
            System.out.println("- " + organization.getName() + " (Address: " + organization.getAddress() + ")");
        }
    }

    //get the list of organizations
    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }
}

