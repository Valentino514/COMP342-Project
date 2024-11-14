import java.util.ArrayList;
import java.util.Scanner;

class LessonCatalog {
    private static final ArrayList<Lesson> lessonCatalog = new ArrayList<>();

    public static void createLesson(String activity, Schedule schedule, String spaceId, String lessonId){
        Space space = SpaceCatalog.findSpace(spaceId);
        boolean scheduleConflict = ScheduleCatalog.checkScheduleConflict(schedule, spaceId);
        
        if (space != null && !scheduleConflict) {
            // Space found, create the lesson
            Lesson newLesson = new Lesson(activity, schedule, space, lessonId);
            addLesson(newLesson);
            System.out.println("Lesson created successfully with space: " + space.getAddress());
        } else if(scheduleConflict){
            System.out.println("error creting lesson, schedule conflict detected in space "+ spaceId);
        }
         else {
            // Space not found
            System.out.println("Cannot create lesson. Space not found.");
        }
    }

    public static void takeLesson(Instructor instructor,String lessonId){
        Lesson lesson = findLessonById(lessonId);
        if(lesson == null){
            System.out.println("error, lesson not found in catalog");
            return;
        }
        String cityOfLesson = lesson.getSpace().getCity();
        if(instructor.getCities().contains(cityOfLesson)){
                System.out.println("would you like to make the lesson private? (1 client max) (Y/N)");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine().trim();  // Get user input and trim any surrounding whitespace
                boolean isPublic;
                if (userInput.equalsIgnoreCase("Y")) {
                    isPublic = false;
                    lesson.space.setPersonLimit(1);
                }else{
                    isPublic = true;
                }
                lesson.setIsOpen(false);
                OfferingCatalog.generateOffering(lesson.getActivity(),lesson.getSchedule(),lesson.getSpace(),lesson.getLessonId(),instructor,isPublic);
                scanner.close();

        }else{
            System.out.println("lesson located in city not in instructor's list of selected city");
        }

    }
    // Add a lesson to the catalog
    public static void addLesson(Lesson lesson) {
        if (!lessonCatalog.contains(lesson)) {
            lessonCatalog.add(lesson);
        }
        else{
            System.out.println("error: lesson already in Catalog");
        }
    }

    // Find a lesson by ID
    public static Lesson findLessonById(String lessonId) {
        for (Lesson lesson : lessonCatalog) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        System.out.println("lesson with id "+lessonId+ " not found");
        return null; // No lesson found with the given ID
    }

    // Get all lessons in the catalog
    public static ArrayList<Lesson> getAllLessons() {
        return lessonCatalog;
    }

    public static void viewLessons() {
        if (lessonCatalog.isEmpty()) {
            System.out.println("No lessons available in the catalog.");
            return;
        }

        // Iterate through the lessons and print their details
        System.out.println("open lessons for instructors:");
        for (Lesson lesson : lessonCatalog) {
            if (lesson.getIsOpen()){
            System.out.println("Lesson ID: " + lesson.getLessonId());
            System.out.println("Activity: " + lesson.getActivity());
            System.out.println("Schedule: " + lesson.getSchedule().getStartDate() + " - " + lesson.getSchedule().getEndDate());
            System.out.println("Space: " + lesson.getSpace().getAddress());
            System.out.println("------------");
            }
        }
    }
}
