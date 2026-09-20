public abstract class Person {

    private String personId;
    private String name;
    private String studEmail;

    public Person(String personId, String name, String studEmail) {
        this.personId = personId;
        this.name = name;
        this.studEmail = studEmail;
    }

    public String getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getStudEmail() {
        return studEmail;
    }

    public void setStudEmail(String e) {
        this.studEmail = e;
    }


    public abstract String getRole();

    @Override
    public String toString() {
        return personId + " | " + name + " | " + getRole();
    }
}