import java.util.ArrayList;

public class Organization {
    private String name;
    private String address;
    private ArrayList<Instructor> instructors;
    private ArrayList<Admin> admins;

    public Organization(String name, String address) {
        this.name = name;
        this.address = address;
        this.instructors = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    // Method to add an instructor to the organization
    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        System.out.println(instructor.getName() + " has been added to " + name + ".");
    }

    // Method to remove an instructor from the organization
    public void removeInstructor(Instructor instructor) {
        if (instructors.remove(instructor)) {
            System.out.println(instructor.getName() + " has been removed from " + name + ".");
        } else {
            System.out.println(instructor.getName() + " is not found in " + name + ".");
        }
    }

    // Method to view all instructors in the organization
    public void viewInstructors() {
        System.out.println("Instructors in " + name + ":");
        for (Instructor instructor : instructors) {
            System.out.println("- " + instructor.getName() + " (Specialization: " + instructor.getSpecialization() + ")");
        }
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
        System.out.println(admin.getName() + " has been added as an admin in " + name + ".");
    }

    public void removeAdmin(Admin admin) {
        if (admins.remove(admin)) {
            System.out.println(admin.getName() + " has been removed from " + name + ".");
        } else {
            System.out.println(admin.getName() + " is not found in " + name + ".");
        }
    }

    public void viewAdmins() {
        System.out.println("Admins in " + name + ":");
        for (Admin admin : admins) {
            System.out.println("- " + admin.getName());
        }
    }
}
