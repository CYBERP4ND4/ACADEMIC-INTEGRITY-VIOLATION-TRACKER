public abstract class Violation {

    private String violationId;
    private String description;
    private String dateReported;
    private int severityLevel;

    public Violation(String violationId, String description, String dateReported, int severityLevel) {
        this.violationId = violationId;
        this.description = description;
        this.dateReported = dateReported;
        setSeverityLevel(severityLevel);
    }

    public String getViolationId() {
        return violationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public String getDateReported() {
        return dateReported;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(int l) {
        if (l < 1 || l > 5) {
            System.out.println("Severity level must be between 1 and 5. Value ignored.");
            return;
        }
        this.severityLevel = l;
    }

    public abstract String calculatePenalty();

    public String toString() {
        return violationId + " | " + getClass().getSimpleName() + " | Severity " + severityLevel
                + " | " + description + " -> " + calculatePenalty();
    }
}
