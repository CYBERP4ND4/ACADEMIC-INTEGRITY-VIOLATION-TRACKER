public interface Resolvable {

    void resolveCase(String remarks);

    void escalateCase();

    String getResolutionStatus();
}