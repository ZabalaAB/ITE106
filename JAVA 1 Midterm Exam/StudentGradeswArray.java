import java.util.Scanner;

public class StudentGradeswArray {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of students: ");
        int numStudents = input.nextInt();
        System.out.print("Enter the number of assignments: ");
        int numAssignments = input.nextInt();

        String[] studentNames = new String[numStudents];
        int[][] grades = new int[numStudents][numAssignments];

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter name for student " + (i + 1) + ": ");
            studentNames[i] = input.next();

            for (int j = 0; j < numAssignments; j++) {
                System.out.print("Enter grade for assignment " + (j + 1) + ": ");
                grades[i][j] = input.nextInt();
            }
        }

        double[] averageScores = new double[numStudents];
        char[] letterGrades = new char[numStudents];

        for (int i = 0;i < numStudents; i++) {
            int total = 0;

            for (int j = 0; j < numAssignments; j++) {
                total += grades[i][j];
            }

            averageScores[i] = (double) total / numAssignments;

            if (averageScores[i] >= 90) {
                letterGrades[i] = 'A';
            } else if (averageScores[i] >= 80) {
                letterGrades[i] = 'B';
            } else if (averageScores[i] >= 70) {
                letterGrades[i] = 'C';
            } else if (averageScores[i] >= 60) {
                letterGrades[i] = 'D';
            } else {
                letterGrades[i] = 'F';
            }
        }

        System.out.println("\nStudent Grades:");
        System.out.println("------------------------------------------------------");
        System.out.printf("%-15s", "Name");
        for (int j = 0; j < numAssignments; j++) {
            System.out.printf("%-10s", "Asgn" + (j + 1));
        }
        System.out.printf("%-10s%-10s\n", "Average", "Grade");
        System.out.println("------------------------------------------------------");

        for (int i = 0; i < numStudents; i++) {
            System.out.printf("%-15s", studentNames[i]); 
            for (int j = 0; j < numAssignments; j++) {
                System.out.printf("%-10d", grades[i][j]);
            }
            System.out.printf("%-10.2f%-10c\n", averageScores[i], letterGrades[i]);
        }

        double highestScore = averageScores[0];
        double lowestScore = averageScores[0];
        String highestStudent = studentNames[0];
        String lowestStudent = studentNames[0];

        for (int i = 1; i < numStudents; i++) {
            if (averageScores[i] > highestScore) {
                highestScore = averageScores[i];
                highestStudent = studentNames[i];
            }
            if (averageScores[i] < lowestScore) {
                lowestScore = averageScores[i];
                lowestStudent = studentNames[i];
            }
        }

        System.out.println("\nHighest Average: " + highestStudent + " (" + highestScore + ")");
        System.out.println("Lowest Average: " + lowestStudent + " (" + lowestScore + ")");

        for (int i = 0; i < numStudents - 1; i++) {
            for (int j = 0; j < numStudents - i - 1; j++) {
                if (averageScores[j] < averageScores[j + 1]) {
                   
                    double tempScore = averageScores[j];
                    averageScores[j] = averageScores[j + 1];
                    averageScores[j + 1] = tempScore;

                    String tempName = studentNames[j];
                    studentNames[j] = studentNames[j + 1];
                    studentNames[j + 1] = tempName;

                    char tempGrade = letterGrades[j];
                    letterGrades[j] = letterGrades[j + 1];
                    letterGrades[j + 1] = tempGrade;

                    int[] tempGrades = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = tempGrades;
                }
            }
        }

        System.out.println("\nSorted by Average Score:");
        System.out.println("------------------------------------------------------");
        System.out.printf("%-15s", "Name");
        for (int j = 0; j < numAssignments; j++) {
            System.out.printf("%-10s", "Asgn" + (j + 1));
        }
        System.out.printf("%-10s%-10s\n", "Average", "Grade");
        System.out.println("------------------------------------------------------");

        for (int i = 0; i < numStudents; i++) {
            System.out.printf("%-15s", studentNames[i]);
            for (int j = 0; j < numAssignments; j++) {
                System.out.printf("%-10d", grades[i][j]);
            }
            System.out.printf("%-10.2f%-10c\n", averageScores[i], letterGrades[i]);
        }

        input.close();
    }
}
