public class Student {

    // student info
    long studentID;
    int friendCount;
    String studentsFirstName;
    String studentsLastName;
    String studentCollege;
    String studentDepartment;
    String studentEmail;

    // Constructor to initialize new Student
    public Student(long studentID, String studentsFirstName, String studentsLastName, String studentCollege, String studentDepartment,
                  String studentEmail) {

        this.studentsFirstName = studentsFirstName;
        this.studentsLastName = studentsLastName;
        this.studentID = studentID;
        this.studentCollege = studentCollege;
        this.studentDepartment = studentDepartment;
        this.studentEmail = studentEmail;
        this.friendCount = 0;

    }

    /* Helper methods to get certain parts of student info */

    public long getID() {
        return studentID;
    }

    public String getStudentsFirstName() {
        return studentsFirstName;
    }

    public String getStudentsLastName() {
        return studentsLastName;
    }

    public String getStudentCollege() {
        return studentCollege;
    }


}
