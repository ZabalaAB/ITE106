import java.util.Scanner;

public class StudentGradeCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of students: ");
        int numStudents = input.nextInt();
        input.nextLine(); 

        String[] studentNames = new String[numStudents];
        double[] averageScores = new double[numStudents];
        char[] letterGrades = new char[numStudents];

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter the name of student " + (i + 1) + ": ");
            studentNames[i] = input.nextLine();

            System.out.print("Enter the number of assignments for " + studentNames[i] + ": ");
            int numAssignments = input.nextInt();
            double totalScore = 0;

            //Assignment scores
            for (int j = 0; j < numAssignments; j++) {
                System.out.print("Enter score for assignment " + (j + 1) + ": ");
                double score = input.nextDouble();
                totalScore += score;
            }

            averageScores[i] = totalScore / numAssignments;

            // Calculation
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

            input.nextLine();
        }

        // Output
        System.out.println("\nStudent Grades:");
        for (int i = 0; i < numStudents; i++) {
            System.out.println(studentNames[i] + ": Average Score = " + averageScores[i] + ", Grade = " + letterGrades[i]);
        }

        double classTotal = 0;
        for (int i = 0; i < numStudents; i++) {
            classTotal += averageScores[i];
        }
        double classAverage = classTotal / numStudents;
        System.out.println("\nClass Average Score: " + classAverage);

        double highestScore = averageScores[0];
        double lowestScore = averageScores[0];
        for (int i = 1; i < numStudents; i++) {
            if (averageScores[i] > highestScore) {
                highestScore = averageScores[i];
            }
            if (averageScores[i] < lowestScore) {
                lowestScore = averageScores[i];
            }
        }
        System.out.println("Highest Average Score: " + highestScore);
        System.out.println("Lowest Average Score: " + lowestScore);

        input.close();
    }
}
