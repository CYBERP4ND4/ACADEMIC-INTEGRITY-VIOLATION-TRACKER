public class Case implements Resolvable {

    private static final int MAX_VIOLATIONS = 10;

    private String caseId;
    private Student reportedStudent;
    private Faculty handledBy;
    private Violation[] violations;
    private int violationCount;
    private String status;
    private String dateFiled;
    private String resolutionRemarks;

    public Case(String caseId, Student reportedStudent, String dateFiled) {
        this(caseId, reportedStudent, null, dateFiled);
    }

    public Case(String caseId, Student reportedStudent, Faculty handledBy, String dateFiled) {
        this.caseId = caseId;
        this.reportedStudent = reportedStudent;
        this.handledBy = handledBy;
        this.dateFiled = dateFiled;
        this.violations = new Violation[MAX_VIOLATIONS];
        this.violationCount = 0;
        this.status = "Open";
        this.resolutionRemarks = "";
    }

    public void addViolation(Violation v) throws InvalidViolationException {
        if (v == null) {
            throw new InvalidViolationException("No violation was supplied.");
        }
        if (v.getDescription() == null || v.getDescription().trim().isEmpty()) {
            throw new InvalidViolationException("Violation " + v.getViolationId()
                    + " cannot be attached without a description.");
        }
        for (int i = 0; i < violationCount; i++) {
            if (violations[i].getViolationId().equals(v.getViolationId())) {
                throw new InvalidViolationException("Violation " + v.getViolationId()
                        + " is already attached to case " + caseId + ".");
            }
        }
        if (violationCount == violations.length) {
            throw new InvalidViolationException("Case " + caseId
                    + " has reached the maximum of " + violations.length + " violations.");
        }
        violations[violationCount] = v;
        violationCount++;
        reportedStudent.incrementViolationCount();
    }

    public Violation[] getViolations() {
        Violation[] copy = new Violation[violationCount];
        System.arraycopy(violations, 0, copy, 0, violationCount);
        return copy;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public void assignHandler(Faculty f) {
        if (status.equals("Resolved")) {
            System.out.println("Case " + caseId + " is already closed. Handler cannot be reassigned.");
            return;
        }
        this.handledBy = f;
    }

    @Override
    public void resolveCase(String remarks) {
        if (status.equals("Resolved")) {
            System.out.println("Case " + caseId + " is already closed.");
            return;
        }
        if (remarks == null || remarks.trim().isEmpty()) {
            System.out.println("Case not resolved: remarks are required to close a case.");
            return;
        }
        if (handledBy == null) {
            System.out.println("Case not resolved: assign a handler to case " + caseId + " first.");
            return;
        }
        this.resolutionRemarks = remarks.trim();
        this.status = "Resolved";
    }

    @Override
    public void escalateCase() {
        if (status.equals("Resolved")) {
            System.out.println("Case " + caseId + " is already closed and cannot be escalated.");
            return;
        }
        this.status = "Escalated";
    }

    @Override
    public String getResolutionStatus() {
        return status;
    }

    public String getCaseId() {
        return caseId;
    }

    public Student getReportedStudent() {
        return reportedStudent;
    }

    public Faculty getHandledBy() {
        return handledBy;
    }

    public String getDateFiled() {
        return dateFiled;
    }

    public String getResolutionRemarks() {
        return resolutionRemarks;
    }

    public String summarisePenalties() {
        String out = "";
        for (int i = 0; i < violationCount; i++) {
            Violation v = violations[i];
            out += "  " + v.getViolationId() + " (" + v.getClass().getSimpleName() + ") severity "
                    + v.getSeverityLevel() + " -> " + v.calculatePenalty() + "\n";
        }
        return out;
    }

    @Override
    public String toString() {
        String handler = (handledBy == null) ? "Unassigned" : handledBy.getName();
        String out = caseId + " | " + reportedStudent.getName()
                + " (" + reportedStudent.getRole() + ") | Handler: " + handler + "\n"
                + "| Filed " + dateFiled + " | Status: " + status
                + " | Violations: " + violationCount + "\n";
        out += summarisePenalties();
        if (!resolutionRemarks.isEmpty()) {
            out += "  Remarks: " + resolutionRemarks + "\n";
        }
        return out;
    }
}