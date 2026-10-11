import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final CaseTracker tracker = new CaseTracker();
    private static Student[] students;
    private static Faculty[] faculty;

    public static void main(String[] args) {
        loadSampleRecords();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Select an option: ", 1, 8);
            System.out.println();
            switch (choice) {
                case 1: fileCase(); break;
                case 2: attachViolation(); break;
                case 3: assignHandler(); break;
                case 4: resolveCase(); break;
                case 5: escalateCase(); break;
                case 6: searchCase(); break;
                case 7: summaryReport(); break;
                case 8:
                    running = false;
                    System.out.println("Exiting. Goodbye.");
                    break;
            }
            System.out.println();
        }
    }

    private static void loadSampleRecords() {
        students = new Student[] {
            new GraduateStudent("21-0456", "Rowena Cruz", "rcruz@hau.edu.ph",
                    "MS Computer Science", 2, "Dr. Maria Santos", "Software Engineering"),
            new Student("23-1102", "Miguel Reyes", "mreyes@hau.edu.ph", "BS Computer Science", 3),
            new Student("24-0388", "Angela Dizon", "adizon@hau.edu.ph", "BS Information Technology", 2),
            new Student("22-0719", "Paolo Mendoza", "pmendoza@hau.edu.ph", "BS Cybersecurity", 4)
        };
        faculty = new Faculty[] {
            new Faculty("F-1016", "Joseph Esquivel", "jesquivel@hau.edu.ph", "School of Computing", "Graduate School Coordinator"),
            new Faculty("F-1041", "Chris Almocera", "calmocera@hau.edu.ph", "School of Computing", "Information Technology Program Chairperson"),
            new Faculty("F-1042", "Carisma Caro", "ccaro@hau.edu.ph", "School of Computing", "Computer Science Program Chairperson"),
            new Faculty("F-1043", "Everly Chua", "echua@hau.edu.ph", "School of Computing", "Cybersecurity Program Chairperson"),
            new Faculty("F-1077", "Mary Jane Rabena", "mrabena@hau.edu.ph", "School of Computing", "Instructor"),
            new Faculty("F-1015", "Marlon Tayag", "mtayag@hau.edu.ph", "School of Computing", "Dean")
        };
    }

    private static void printMenu() {
        System.out.println("==============================================");
        System.out.println("ACADEMIC INTEGRITY VIOLATION TRACKER");
        System.out.println("Holy Angel University - School of Computing");
        System.out.println("==============================================");
        System.out.println("1. File a case          2. Attach a violation   3. Assign a handler");
        System.out.println("4. Resolve a case       5. Escalate a case      6. Search a case");
        System.out.println("7. Summary report       8. Exit");
    }

    private static void fileCase() {
        String caseId = readText("Case ID: ");
        Student s = findStudent(readText("Student ID: "));
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("  -> " + s.getName() + ", " + s.getRole() + ", " + s.getCourse());
        String date = readText("Date filed (YYYY-MM-DD): ");
        try {
            tracker.addCase(new Case(caseId, s, date));
            System.out.println("Case " + caseId + " filed. Handler not yet assigned.");
        } catch (DuplicateCaseException e) {
            System.out.println("Case rejected: " + e.getMessage());
        } finally {
            System.out.println("Cases on record: " + tracker.getCaseCount());
        }
    }

    private static void attachViolation() {
        Case c = tracker.searchCaseById(readText("Case ID: "));
        if (c == null) {
            System.out.println("Case not found.");
            return;
        }
        int category = readInt("Category [1] Plagiarism [2] Cheating: ", 1, 2);
        String violationId = readText("Violation ID: ");
        String description = readText("Description: ");
        String date = readText("Date reported (YYYY-MM-DD): ");
        int severity = readInt("Severity (1-5): ", 1, 5);

        Violation v;
        if (category == 1) {
            double similarity = readDouble("Similarity (%): ", 0.0, 100.0);
            v = new PlagiarismViolation(violationId, description, date, severity, similarity);
        } else {
            String examType = readText("Exam type (Quiz / Long Quiz / Major Exam): ");
            String assistance = readText("Assistance type: ");
            v = new CheatingViolation(violationId, description, date, severity, examType, assistance);
        }

        Student s = c.getReportedStudent();
        try {
            c.addViolation(v);
            System.out.println("Violation " + violationId + " attached. Recommended penalty:");
            System.out.println(v.calculatePenalty());
            System.out.println("Violation count for " + s.getName() + " is now " + s.getViolationCount() + ".");
        } catch (InvalidViolationException e) {
            System.out.println("Violation rejected: " + e.getMessage());
            System.out.println("Violation count for " + s.getName() + " is still " + s.getViolationCount() + ".");
        }
    }

    private static void assignHandler() {
        Case c = tracker.searchCaseById(readText("Case ID: "));
        if (c == null) {
            System.out.println("Case not found.");
            return;
        }
        Faculty f = findFaculty(readText("Faculty ID: "));
        if (f == null) {
            System.out.println("Faculty member not found.");
            return;
        }
        System.out.println("  -> " + f.getName() + ", " + f.getPosition());
        c.assignHandler(f);
        if (c.getHandledBy() == f) {
            System.out.println("Case " + c.getCaseId() + " assigned to " + f.getName() + ".");
        }
    }

    private static void resolveCase() {
        Case c = tracker.searchCaseById(readText("Case ID: "));
        if (c == null) {
            System.out.println("Case not found.");
            return;
        }
        String remarks = readText("Remarks: ");
        c.resolveCase(remarks);
        if (c.getResolutionStatus().equals("Resolved")) {
            System.out.println("Case " + c.getCaseId() + " resolved.");
        } else {
            System.out.println("Status of " + c.getCaseId() + " remains: " + c.getResolutionStatus());
        }
    }

    private static void escalateCase() {
        Case c = tracker.searchCaseById(readText("Case ID: "));
        if (c == null) {
            System.out.println("Case not found.");
            return;
        }
        c.escalateCase();
        System.out.println("Status of " + c.getCaseId() + " is now: " + c.getResolutionStatus());
    }

    private static void searchCase() {
        Case c = tracker.searchCaseById(readText("Case ID: "));
        if (c == null) {
            System.out.println("Case not found.");
            return;
        }
        System.out.print(c);
        System.out.println("Total violations on record for " + c.getReportedStudent().getName()
                + ": " + c.getReportedStudent().getViolationCount());
    }

    private static void summaryReport() {
        String filter = readText("Status filter (Open / Escalated / Resolved, blank for all): ");
        String report;
        String heading;
        if (filter.isEmpty()) {
            report = tracker.generateSummaryReport();
            heading = "all cases";
        } else {
            report = tracker.generateSummaryReport(filter);
            heading = "status: " + filter;
        }
        System.out.println("--- SUMMARY REPORT (" + heading + ") ---");
        if (report.isEmpty()) {
            System.out.println("No matching cases.");
        } else {
            System.out.print(report);
        }
        System.out.println("Total cases tracked: " + tracker.getCaseCount());
    }

    private static Student findStudent(String id) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].getPersonId().equalsIgnoreCase(id)) {
                return students[i];
            }
        }
        return null;
    }

    private static Faculty findFaculty(String id) {
        for (int i = 0; i < faculty.length; i++) {
            if (faculty[i].getPersonId().equalsIgnoreCase(id)) {
                return faculty[i];
            }
        }
        return null;
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        if (!in.hasNextLine()) {
            System.out.println();
            System.exit(0);
        }
        return in.nextLine().trim();
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(readText(prompt));
                if (value < min || value > max) {
                    System.out.println("Enter a number from " + min + " to " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            try {
                double value = Double.parseDouble(readText(prompt));
                if (value < min || value > max) {
                    System.out.println("Enter a number from " + min + " to " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry. Please enter a number.");
            }
        }
    }
}