public class CaseTracker {

    private static final int MAX_CASES = 100;

    private Case[] cases;
    private int caseCount;

    public CaseTracker() {
        this.cases = new Case[MAX_CASES];
        this.caseCount = 0;
    }

    public void addCase(Case c) throws DuplicateCaseException {
        for (int i = 0; i < caseCount; i++) {
            if (cases[i].getCaseId().equalsIgnoreCase(c.getCaseId())) {
                throw new DuplicateCaseException("Case " + c.getCaseId() + " is already being tracked.");
            }
        }
        if (caseCount == cases.length) {
            throw new DuplicateCaseException("The register is full. Maximum of "
                    + cases.length + " cases reached.");
        }
        cases[caseCount] = c;
        caseCount++;
    }

    public Case searchCaseById(String id) {
        for (int i = 0; i < caseCount; i++) {
            if (cases[i].getCaseId().equalsIgnoreCase(id)) {
                return cases[i];
            }
        }
        return null;
    }

    public Case[] getAllCases() {
        Case[] copy = new Case[caseCount];
        System.arraycopy(cases, 0, copy, 0, caseCount);
        return copy;
    }

    public String generateSummaryReport() {
        return generateSummaryReport(null);
    }

    public String generateSummaryReport(String status) {
        String out = "";
        for (int i = 0; i < caseCount; i++) {
            if (status == null || cases[i].getResolutionStatus().equalsIgnoreCase(status)) {
                out += cases[i].toString() + "\n";
            }
        }
        return out;
    }

    public int getCaseCount() {
        return caseCount;
    }
}
