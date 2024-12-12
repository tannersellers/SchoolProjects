/**
 * Author: Tanner Sellers
 * Email: tanner.sellers10@okstate.edu
 * Date: 11/20/2024
 * Program: Student Social Network Graph
 * Description: This program represents a social network of students as an undirected graph.
 *              It provides functionalities to manage students, friendships, and perform operations
 *              such as removing friendships, deleting accounts, displaying friend circles,
 *              and calculating closeness centrality.
 * Features:
 * - Add students and their friendships from an input file.
 * - Perform operations like removing friendships, deleting students, and counting friends.
 * - Analyze the graph to find friend circles within a college and compute closeness centrality.
 *
 *
 *
 * Classes:
 * - Main: Handles input/output and program execution.
 * - Graph: Implements the graph structure using adjacency lists.
 * - Student: Represents individual student information.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter file name: "); // prompt user for file name
        String fileName = scnr.nextLine();

        // Max limit for students and friends
        int maxStudents = 1500;
        int maxFriendsPerStudent = 1500;

        // Create graph w/ specified limits
        Graph graph = new Graph(maxStudents, maxFriendsPerStudent);

        // Read input file and populate graph
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // skip header
            String line;
            int userInput;
            while((line = br.readLine()) != null) {

                // Extract student information from file
                String[] studentInfo = line.split("\t");
                long ID = Long.parseLong(studentInfo[0]);
                String firstName = studentInfo[1];
                String lastName = studentInfo[2];
                String college = studentInfo[3];
                String department = studentInfo[4];
                String email = studentInfo[5];
                int friendCount = Integer.parseInt(studentInfo[6]);

                // Create new Student object
                Student student = new Student(ID, firstName, lastName, college, department, email);
                graph.addStudent(student);

                // Add friendships based on friend ID
                for (int j = 7; j < 7 + friendCount; j++) {
                    long friendID = Long.parseLong(studentInfo[j]);
                    graph.addFriendship(ID, friendID);
                }
            }
            // Display graph statistics
            System.out.println("Input file is read successfully..");
            System.out.println("Total number of vertices in the graph: " + graph.getVertexCount());
            System.out.println("Total number of edges in the graph: " + graph.getEdgeCount());

            // Menu-driven part of program for user to select operation to perform
            while(true) {
                System.out.println();
                printMenu();
                userInput = scnr.nextInt(); // clear buffer

                switch (userInput) {
                    case 1: // Remove friendship
                        scnr.nextLine();
                        System.out.println("Enter the first student's first name: ");
                        String studentRemoveFirstName1 = scnr.nextLine();
                        System.out.println("Enter the first student's last name: ");
                        String studentRemoveLastName1 = scnr.nextLine();
                        System.out.println("Enter the second student's first name: ");
                        String studentRemoveFirstName2 = scnr.nextLine();
                        System.out.println("Enter the second student's last name: ");
                        String studentRemoveLastName2 = scnr.nextLine();
                        graph.removeFriendship(studentRemoveFirstName1, studentRemoveLastName1, studentRemoveFirstName2,
                                studentRemoveLastName2);
                        break;
                    case 2: // Delete account
                        scnr.nextLine();
                        System.out.println("Enter the first name of the student to be removed: ");
                        String studentToRemoveFirstName = scnr.nextLine();
                        System.out.println("Enter the last name of the student to be removed: ");
                        String studentToRemoveLastName = scnr.nextLine();
                        if(graph.removeStudent(studentToRemoveFirstName, studentToRemoveLastName)) {
                            System.out.println("Total number of vertices in the graph: " + graph.getVertexCount());
                            System.out.println("Total number of edges in the graph: " + graph.getEdgeCount());
                        };
                        break;
                    case 3: // Count friends
                        scnr.nextLine();
                        System.out.println("Enter the first name of the student to find number of friends: ");
                        String studentFirstName = scnr.nextLine();
                        System.out.println("Enter the last name of the student: ");
                        String studentLastName = scnr.nextLine();
                        graph.displayFriends(studentFirstName, studentLastName);
                        break;
                    case 4: // Display friend circles
                        scnr.nextLine();
                        System.out.println("Enter the college name to view its friend circles: ");
                        String college = scnr.nextLine();
                        graph.displayCircle(college);
                        break;

                    case 5: // Calculate closeness centrality
                        scnr.nextLine();
                        System.out.println("Enter the first name of the student to calculate closeness centrality: ");
                        String firstName = scnr.nextLine();
                        System.out.println("Enter the last name of the student: ");
                        String lastName = scnr.nextLine();
                        graph.calculateClosenessCentrality(firstName, lastName);
                        break;

                    case 6: // Exit program
                        System.exit(0);
                }
            }

        }
        catch(FileNotFoundException e) {
            System.out.println("File not found, try again.");

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Display menu options
    static void printMenu() {
        System.out.println("1. Remove friendship");
        System.out.println("2. Delete account");
        System.out.println("3. Count friends");
        System.out.println("4. Friends Circle");
        System.out.println("5. Closeness centrality");
        System.out.println("6. Exit");
    }
}