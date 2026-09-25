public class CheatingViolation extends Violation {

    private String examType;
    private String assistanceType;

    public CheatingViolation(String violationId, String description, String dateReported, int severityLevel, String examType, String assistanceType) {
        super(violationId, description, dateReported, severityLevel);
        this.examType = examType;
        this.assistanceType = assistanceType;
    }

    public String getExamType() {
        return examType;
    }

    public String getAssistanceType() {
        return assistanceType;
    }
    public String calculatePenalty() {
        if (examType.equalsIgnoreCase("Major Exam")) {
            return "Failing grade for the subject and referral to the Student Discipline Office";
        }
        if (examType.equalsIgnoreCase("Long Quiz")) {
            return "Zero for the assessment and a written warning on record";
        }
        return "Zero for the activity and a formal advisory to the student";
    }
}
