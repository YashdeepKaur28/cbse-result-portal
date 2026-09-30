public class Student {
    private int studentId;
    private String name;
    private String className;
    private double mathMarks;
    private double scienceMarks;
    private double englishMarks;
    private double hindiMarks;
    private double sstMarks;
    private double totalMarks;
    private double percentage;
    private String grade;

    public Student() {}

    public Student(int studentId, String name, String className,
                   double mathMarks, double scienceMarks, double englishMarks,
                   double hindiMarks, double sstMarks,
                   double totalMarks, double percentage, String grade) {
        this.studentId = studentId;
        this.name = name;
        this.className = className;
        this.mathMarks = mathMarks;
        this.scienceMarks = scienceMarks;
        this.englishMarks = englishMarks;
        this.hindiMarks = hindiMarks;
        this.sstMarks = sstMarks;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.grade = grade;
    }

    // Getters and Setters (omitted for brevity – include all)
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public double getMathMarks() { return mathMarks; }
    public void setMathMarks(double mathMarks) { this.mathMarks = mathMarks; }
    public double getScienceMarks() { return scienceMarks; }
    public void setScienceMarks(double scienceMarks) { this.scienceMarks = scienceMarks; }
    public double getEnglishMarks() { return englishMarks; }
    public void setEnglishMarks(double englishMarks) { this.englishMarks = englishMarks; }
    public double getHindiMarks() { return hindiMarks; }
    public void setHindiMarks(double hindiMarks) { this.hindiMarks = hindiMarks; }
    public double getSstMarks() { return sstMarks; }
    public void setSstMarks(double sstMarks) { this.sstMarks = sstMarks; }
    public double getTotalMarks() { return totalMarks; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}