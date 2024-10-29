class GroupLesson extends Offering {
    public GroupLesson(String activity, Timeslot timeslot, Space space, String id) {
        super(activity, timeslot, space, id);
        makeOfferingPublic(); // Automatically set as public
    }

    @Override
    public String toString() {
        return "Public Lesson: " + getActivity() + " in " + getSpace() + " at " + getTimeslot();
    }
}
