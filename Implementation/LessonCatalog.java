import java.util.ArrayList;
import java.util.List;

class LessonCatalog {
    private List<Lesson> lessons;

    // Constructor
    public LessonCatalog() {
        this.lessons = new ArrayList<>();
    }

    // Add a lesson to the catalog
    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
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

    @Override
    public String toString() {
        return "LessonCatalog{" +
                "lessons=" + lessons +
                '}';
    }
}
