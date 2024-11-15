import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //*  TEMPORARY VALUES FOR TESTING
        Space space1 = new Space("Concordia", "pool", true, "montreal", 25);
        SpaceCatalog.addSpace(space1);

        Schedule schedule2 = new Schedule("02:00", "05:00", "2000-12-24", "2011-12-24", space1, "monday");
        ScheduleCatalog.addSchedule(schedule2);

        Space space2 = new Space("Mcgill", "tennis", false, "toronto", 40);
        SpaceCatalog.addSpace(space2);

        Lesson lesson2= new Lesson("tennis", schedule2, space2);
        LessonCatalog.addLesson(lesson2);

        ArrayList<String> cities2 = new ArrayList<>();

        cities2.add("mtl");
        cities2.add("toronto");

        Instructor instructor2 = new Instructor("coach", "john", "123", "514", cities2);
        UserCatalog.addUser(instructor2);

        Offering offer = new Offering("ping-pong", schedule2, space2, instructor2, true);

        Client client2 = new Client("peter", "123", 15);
        UserCatalog.addUser(client2);

        Admin admin = new Admin("admin", "qwert");
        UserCatalog.addUser(admin);
        //_________________________________________________________


        System.out.println("Do you have an account? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            User user =null;
            boolean signedIn = false;

            while(!signedIn){

                System.out.print("Username:");
                String name= scanner.nextLine();
    
                System.out.print("password:");
                String password= scanner.nextLine();

                if(UserCatalog.findUser(name, password)){
                    System.out.println("login successful");
                    signedIn = true;
                    user = UserCatalog.getUser(name, password);
                }else{
                    System.out.println("error finding user, try again.");
                }

            }

            boolean running = true;
            while (running) {

                if(user.getUserType() == "Admin"){
                    System.out.println("ADMIN / writer mode");
                    System.out.println("1. Create a new lesson");
                    System.out.println("2. view all lessons");
                    System.out.println("3. delete User");
                    System.out.println("4. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 
                    switch (choice) {
                        case 1:
                            System.out.println("create new lesson option chosen\nselect space id");
                            SpaceCatalog.printSpaces();
                            String id= scanner.nextLine();
                            if(SpaceCatalog.spaceExists(id)){
                                System.out.println("selected space id "+ id);
                                System.out.println("enter the activity");
                                String activity= scanner.nextLine();
                                ScheduleCatalog.printSpaceSchedule(id);
                                System.out.println("enter a schedule that cannot overlap with the ones mentionned");
                                System.out.println("enter start time in (HH:mm) format");
                                String startTime= scanner.nextLine();
                                System.out.println("enter end time in (HH:mm) format");
                                String endTime= scanner.nextLine();
                                System.out.println("enter  start date in (YYY-MM-DD) format");
                                String startDate= scanner.nextLine();
                                System.out.println("enter  end date in (YYY-MM-DD) format");
                                String endDate= scanner.nextLine();
                                System.out.println("enter day of the week ");
                                String day= scanner.nextLine();

                                Schedule schedule = new Schedule(startTime, endTime, startDate, endDate, SpaceCatalog.findSpace(id), day);
                                ScheduleCatalog.addSchedule(schedule);
                                System.out.println("creating lesson...");
                                Lesson lesson = new Lesson(activity, schedule, SpaceCatalog.findSpace(id));
                                LessonCatalog.addLesson(lesson);
                                System.out.println("new lesson details:");
                                lesson.printDetails();
                            }else{
                                System.out.println("space id does not exist");
                            }

                            break;
                        case 2:
                            LessonCatalog.viewLessons();
                            break;
                        case 3:
                            UserCatalog.viewUsers();
                            System.out.println("type the id of the user to delete: ");
                            String userId= scanner.nextLine();
                            admin.deleteAccount(userId);
                            System.out.println("updated list:");
                            UserCatalog.viewUsers();
                            break;

                        case 4:
                        running = false;
                        System.out.println("Exiting.");
                        break;

                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }else if(user.getUserType() == "Client" || user.getUserType() == "UnderageClient" ){
                    Client client = (Client) user;
                    System.out.println("Client / reader mode");
                    System.out.println("1. book an offering");
                    System.out.println("2. view offerings");
                    System.out.println("3. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    switch(choice){
                        case 1:
                            OfferingCatalog.viewOfferings();
                            System.out.println("type the offering id to select it");
                            String offeringId = scanner.nextLine();
                            Offering offering = OfferingCatalog.findOffering(offeringId);
                            client.addOffering(offering);
                        break;

                        case 2:
                            OfferingCatalog.viewOfferings();
                            break;

                        case 3:
                            running = false;
                            System.out.println("Exiting.");
                            break;

                        default:
                            System.out.println("Invalid option. Please try again.");

                    }

                } else if (user.getUserType() == "Instructor"){
                    Instructor instructor = (Instructor) user;
                    System.out.println("Instructor / writer mode");
                    System.out.println("1. select a lesson to instruct");
                    System.out.println("2. view my offerings");
                    System.out.println("3. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 
                    switch (choice) {
                    
                        case 1:
                            LessonCatalog.viewLessons();
                            System.out.println("select lesson by typing the id: ");
                            String lessonChoice = scanner.nextLine();
                            LessonCatalog.takeLesson(instructor, lessonChoice);
                            break;
                        
                        case 2:
                            instructor.viewAssignedOfferings();
                            break;

                        case 3:
                       running = false;
                       System.out.println("Exiting.");
                       break;

                       default:
                       System.out.println("Invalid option. Please try again.");

                }
 
            }
        }
        } else if (response.equals("no")) {
            boolean running = true;
            while (running) {
                System.out.println("Reader Mode");
                System.out.println("1. view all offerings");
                System.out.println("2. create account");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        OfferingCatalog.viewOfferings();
                        break;
                    case 2:
                        System.out.println("enter your name");
                        String username= scanner.nextLine();
                        System.out.println("enter password");
                        String password= scanner.nextLine();
                        System.out.println("register as client or instructor?");
                        String userType= scanner.nextLine();
                        if(userType.equals("client")){
                            System.out.println("enter your age");
                            int age= scanner.nextInt();
                            scanner.nextLine();
                            if(age < 18){
                                System.out.println("underage client detected, need supervisor to create account");
                                System.out.println("enter supervisor name");
                                String supervisorName= scanner.nextLine();
                                System.out.println("enter supervisor phone number");
                                String suervisorPhone= scanner.nextLine();
                                Client underageClient = new UnderageClient(username, password, supervisorName, suervisorPhone, age);
                                UserCatalog.addUser(underageClient);
                                break;
                            }
                            else{
                                Client client = new Client(username, password, age);
                                UserCatalog.addUser(client);
                            }
                        }else if(userType.equals("instructor")){
                            System.out.println("Enter your specialization:");
                            String specialty = scanner.nextLine();
                            System.out.println("Enter your phone number:");
                            String phoneNumber = scanner.nextLine();
                            System.out.println("Enter the cities you can instruct in (type 'done' when finished):");
                            ArrayList<String> cities = new ArrayList<>();
                            while (true) {
                                String city = scanner.nextLine();
                                if (city.equalsIgnoreCase("done")) {
                                    break; // Exit the loop
                                }
                                // Add the city to the list if it's not "done"
                                cities.add(city);
                            }
                            Instructor instructor = new Instructor(specialty, username, password, phoneNumber, cities);
                            UserCatalog.addUser(instructor);
                            System.out.println("Instructor created with cities: " + cities);
                        }
                        



                    case 3:
                        running = false;
                        System.out.println("Exiting.");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }

        scanner.close();
    }
}
