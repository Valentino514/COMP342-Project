import java.util.ArrayList;
import java.util.List;

public class LessonCatalog {
    private List<Lesson> lessons;

    // Constructor
    public LessonCatalog() {
        this.lessons = new ArrayList<>();
    }

    // Add a lesson to the catalog
    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        } else {
            System.out.println("Lesson with ID " + lesson.getId() + " already exists.");
        }
    }

    // Remove a lesson from the catalog by ID
    public void removeLessonById(String lessonId) {
        Lesson lessonToRemove = findLessonById(lessonId);
        if (lessonToRemove != null) {
            lessons.remove(lessonToRemove);
            System.out.println("Lesson with ID " + lessonId + " removed.");
        } else {
            System.out.println("Lesson with ID " + lessonId + " not found.");
        }
    }

    // Find a lesson by ID
    public Lesson findLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getId().equals(lessonId)) {
                return lesson;
            }
        }
        return null; // No lesson found with the given ID
    }

    // Get all lessons in the catalog
    public List<Lesson> getAllLessons() {
        return new ArrayList<>(lessons);
    }

    // Get all public lessons (lessons with isPublic set to true)
    public List<Lesson> getPublicLessons() {
        List<Lesson> publicLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getIsPublic()) {
                publicLessons.add(lesson);
            }
        }
        return publicLessons;
    }

    // Get all lessons for a specific activity (e.g., Yoga, Swimming)
    public List<Lesson> getLessonsByActivity(String activity) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getActivity().equalsIgnoreCase(activity)) {
                filteredLessons.add(lesson);
            }
        }
        return filteredLessons;
    }

    // Get the number of lessons in the catalog
    public int getLessonCount() {
        return lessons.size();
    }

    @Override
    public String toString() {
        return "LessonCatalog{" +
                "lessons=" + lessons +
                '}';
    }
}
