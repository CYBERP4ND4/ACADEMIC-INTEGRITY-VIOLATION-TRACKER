public class PlagiarismViolation extends Violation {

    private double similarityPercentage;

    public PlagiarismViolation(String violationId, String description, String dateReported, int severityLevel, double similarityPercentage) {
        super(violationId, description, dateReported, severityLevel);
        setSimilarityPercentage(similarityPercentage);
    }

    public double getSimilarityPercentage() {
        return similarityPercentage;
    }

    public void setSimilarityPercentage(double s) {
        if (s < 0.0 || s > 100.0) {
            System.out.println("Similarity percentage must be between 0 and 100. Value ignored.");
            return;
        }
        this.similarityPercentage = s;
    }

    @Override
    public String calculatePenalty() {
        if (similarityPercentage >= 60.0) {
            return "Failing grade for the requirement and referral to the Student Discipline Office";
        }
        if (similarityPercentage >= 30.0) {
            return "Zero for the requirement and a written warning on record";
        }
        return "Mandatory rewrite with correct citation and a formal advisory";
    }
}
