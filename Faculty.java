public class Faculty extends Person {

    private String department;
    private String position;

    public Faculty(String personId, String name, String studEmail, String department, String position) {
        super(personId, name, studEmail);
        this.department = department;
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String d) {
        this.department = d;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String p) {
        this.position = p;
    }

    @Override
    public String getRole() {
        return "Faculty";
    }
}