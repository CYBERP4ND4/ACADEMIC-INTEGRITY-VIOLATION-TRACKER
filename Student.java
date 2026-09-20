public class Student extends Person {

    private String course;
    private int yearLevel;
    private int violationCount;

    public Student(String personId, String name, String studEmail, String course, int yearLevel) {
        super(personId, name, studEmail); 
        this.course = course;
        this.yearLevel = yearLevel;
        this.violationCount = 0;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String c) {
        this.course = c;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int y) {
        if (y < 1 || y > 5) {
            System.out.println("Year level must be between 1 and 5. Value ignored.");
            return;
        }
        this.yearLevel = y;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public void incrementViolationCount() {
        this.violationCount++;
    }

    @Override
    public String getRole() {
        return "Student";
    }
}