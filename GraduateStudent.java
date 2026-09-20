public class GraduateStudent extends Student {

    private String thesisAdviser;
    private String researchProgram;

    public GraduateStudent(String personId, String name, String studEmail, String course, int yearLevel, String thesisAdviser, String researchProgram) {
        super(personId, name, studEmail, course, yearLevel); 
        this.thesisAdviser = thesisAdviser;
        this.researchProgram = researchProgram;
    }

    public String getThesisAdviser() {
        return thesisAdviser;
    }

    public void setThesisAdviser(String a) {
        this.thesisAdviser = a;
    }

    public String getResearchProgram() {
        return researchProgram;
    }

    @Override
    public String getRole() {
        return "Graduate Student";
    }
}