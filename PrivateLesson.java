class PrivateLesson extends Offering {
    public PrivateLesson(String activity, Timeslot timeslot, Space space, String id) {
        super(activity, timeslot, space, id);
    }

    @Override
    public String toString() {
        return "Private Lesson: " + getActivity() + " in " + getSpace() + " at " + getTimeslot();
    }
}
