public class Graph {
    private Student[] students; // Array to store students
    private int[][] adjacencyList; // Adjacency list to represent friendship between students
    private int[] listSizes; // Array to track number of friends of each student
    private int studentCount; // Variable to track number of students

    // Constructor to initialize the graph w/ given max number
    public Graph(int maxStudents, int maxFriendsPerStudent) {
        students = new Student[maxStudents];
        adjacencyList = new int[maxStudents][maxFriendsPerStudent];
        listSizes = new int[maxStudents];
        this.studentCount = 0;
    }

    // Method to add student to graph
    public void addStudent(Student student) {
        students[studentCount++] = student; // Add student to array and increment studentCount
    }

    // Method to add friendship between two students using their IDs
    public void addFriendship(long id1, long id2) {
        int index1 = findStudentIndexByID(id1); // find index of student 1
        int index2 = findStudentIndexByID(id2); // find index of student 2
        if(index1 != -1 && index2 != -1) { // check that both students are found
            adjacencyList[index1][listSizes[index1]++] = index2; // add student 2 as friend of student 1
            adjacencyList[index2][listSizes[index2]++] = index1; // add student 1 as friend of student 2
        }
    }

    // Method to remove a friendship between two students using their first and last names
    public void removeFriendship(String studentName1, String studentNameLast1, String studentName2, String studentNameLast2) {
        int index1 = -1;
        int index2 = -1;

        // Find index of first student based on names
        for(int i = 0; i < studentCount; i++) {
            if (students[i].getStudentsFirstName().equals(studentName1) && students[i].getStudentsLastName().equals(studentNameLast1)) {
                index1 = i;
                break;
            }
        }
        // Find index of second student based on names
        for(int j = 0; j < studentCount; j++) {
            if(students[j].getStudentsFirstName().equals(studentName2) && students[j].getStudentsLastName().equals(studentNameLast2)) {
                index2 = j;
                break;
            }
        }

        // Handle case where students not found
        if(index1 == -1 && index2 == -1) {
            System.out.println("Sorry..");
            System.out.println(studentName1 + " and " + studentName2 + " not found!");
            return;
        }
        else if(index1 == -1) {
            System.out.println("Sorry..");
            System.out.println(studentName1 + " not found!");
            return;
        }
        else if(index2 == -1) {
            System.out.println("Sorry..");
            System.out.println(studentName2 + " not found!");
            return;
        }

        // Remove friendship in both directions
        boolean edgeRemoved = false;
        for(int i = 0; i < listSizes[index1]; i++) {
            if(adjacencyList[index1][i] == index2) { // Check if student 2 is friend of student 1
                for(int j = i; j < listSizes[index1] - 1; j++) { // Shift friendship list
                    adjacencyList[index1][j] = adjacencyList[index1][j+1];
                }
                listSizes[index1]--; // Decrease number of friends for student 1
                edgeRemoved = true;
                break;
            }
        }

        // Repeat for student 2
        for(int i = 0; i < listSizes[index2]; i++) {
            if(adjacencyList[index2][i] == index1) {
                for(int j = i; j < listSizes[index2] - 1; j++) {
                    adjacencyList[index2][j] = adjacencyList[index2][j+1];
                }
                listSizes[index2]--;
                edgeRemoved = true;
                break;
            }
        }

        // Print result of removing friendship
        if(edgeRemoved) {
            System.out.println("The edge between students " + studentName1 + " and " + studentName2 + " has been " +
                    "successfully removed");
            System.out.println("Total number of students in the graph: " + getVertexCount());
            System.out.println("Total number of edge in the graph: " + getEdgeCount());
        }
        else {
            System.out.println("Sorry.. there is no edge between the vertices " + studentName1 + " and " + studentName2);
        }
    }

    // Method to remove student from graph using their name
    public boolean removeStudent(String firstName, String lastName) {
        int studentIndex = -1;

        // Find student index based on names
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentsFirstName().equals(firstName) && students[i].getStudentsLastName().equals(lastName)) {
                studentIndex = i;
                break;
            }
        }

        // Handle case of student not found
        if (studentIndex == -1) {
            System.out.println("Sorry..");
            System.out.println(firstName + " " + lastName + " not found!");
            return false;
        }

        // Remove reference to student from adjacency lists
        for (int i = 0; i < studentCount; i++) {
            if (i != studentIndex) {
                int friendIndex = -1;

                // Find studentIndex in adjacencyList[i]
                for (int j = 0; j < listSizes[i]; i++) {
                    if (adjacencyList[i][j] == studentIndex) {
                        friendIndex = j;
                        break;
                    }
                }

                // Remove friend from list
                if (friendIndex != -1) {
                    for (int j = friendIndex; j < listSizes[i] - 1; j++) {
                        adjacencyList[i][j] = adjacencyList[i][j + 1];
                    }
                    listSizes[i]--;
                }
            }
        }
        // Shift adjacency list and students array
        for (int i = studentIndex; i < studentCount - 1; i++) {
            students[i] = students[i + 1];
            adjacencyList[i] = adjacencyList[i + 1];
            listSizes[i] = listSizes[i + 1];
        }
        studentCount--; // decrement # students

        // Adjust adjacencyList indices since each student to right of studentIndex has moved 1 to the left
        for(int i = 0; i < studentCount; i++) {
            for(int j = 0; j < listSizes[i]; j++) {
                if(adjacencyList[i][j] > studentIndex) {
                    adjacencyList[i][j]--;
                }
            }
        }
        System.out.println("Student " + firstName + " " + lastName + " has been succesfully removed..");
        return true;
    }

    // Helper method to find index of student by their ID
    private int findStudentIndexByID(long id) {
        for(int i = 0; i < studentCount; i++) {
            if (students[i].getID() == id) {
                return i;
            }
        }
        return -1; // Return -1 if student not found
    }

    // Method to get total number of students (vertices) in graph
    public int getVertexCount() {
        return studentCount;
    }

    // Method to get toal number of friendships (edges) in graph
    public int getEdgeCount() {
        int edgeCount = 0;
        for(int i = 0; i < studentCount; i++) {
            edgeCount += listSizes[i]; // Count edges for each student
        }
        return edgeCount / 2; // divide by two since graph is undirected and each edge has been counted twice
    }

    // Method to display a student's friends by their name
    public void displayFriends(String firstName, String lastName) {
        int studentIndex = -1;

        // Find student by name
        for(int i = 0; i < studentCount; i++) {
            if(students[i].getStudentsFirstName().equals(firstName) && students[i].getStudentsLastName().equals(lastName)) {
                studentIndex = i;
            }
        }

        // Display error if not found
        if(studentIndex == -1) {
            System.out.println("Sorry..");
            System.out.println(firstName + " " + lastName + " not found!");
        }

        else {
            System.out.println("Number of friends for " + firstName + " " + lastName + ": " + listSizes[studentIndex]);

            System.out.println("Friends of " + firstName + " are:");
            for(int i = 0; i < listSizes[studentIndex]; i++) {
                System.out.println(students[adjacencyList[studentIndex][i]].getStudentsFirstName() + " " +
                        students[adjacencyList[studentIndex][i]].getStudentsLastName());
            }
        }

    }

    public void displayCircle(String college) {
        boolean[] visited = new boolean[studentCount];
        System.out.println("Following are the friend circles in the " + college);

        for(int i = 0; i < studentCount; i++) {
            if(!visited[i] && students[i].getStudentCollege().equals(college)) {
                // Start BFS for current component
                bfs(i, visited, college);

            }
        }
    }
    private void bfs(int start, boolean[] visited, String college) {
        int queue[] = new int[studentCount]; // Array for queue
        int front = 0; // Queue front pointer
        int rear = 0; // Queue rear pointer

        queue[rear++] = start; // Add starting student to queue
        visited[start] = true;

        String circle = students[start].getStudentsFirstName(); // Start with first student's name

        while(front < rear) {
            int current = queue[front++]; //Dequeue current student

            for(int i = 0; i < listSizes[current]; i++) {
                int neighbor = adjacencyList[current][i];

                // Add neighbors/friends that belong to same college and aren't visited
                if(!visited[neighbor] && students[neighbor].getStudentCollege().equals(college)) {
                    visited[neighbor] = true;
                    queue[rear++] = neighbor; // Enqueue neighbor
                    circle += " - " + students[neighbor].getStudentsFirstName(); // Add to circle string
                }
            }
        }
        System.out.println(circle);
    }
    public void calculateClosenessCentrality(String firstName, String lastName) {
        int studentIndex = -1;
        // Find the student index based on their name
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentsFirstName().equals(firstName) && students[i].getStudentsLastName().equals(lastName)) {
                studentIndex = i;
                break;
            }
        }

        // Handle case where student is not found
        if (studentIndex == -1) {
            System.out.println("Sorry..");
            System.out.println(firstName + " " + lastName + " not found!");
            return;
        }

        // Dijkstra's algorithm to calculate shortest path
        double[] distances = new double[studentCount];
        boolean[] visited = new boolean[studentCount];
        for (int i = 0; i < studentCount; i++) {
            distances[i] = Double.MAX_VALUE;
            visited[i] = false;
        }
        distances[studentIndex] = 0;


        for (int i = 0; i < studentCount; i++) {
            int closest = -1;
            double closestDist = Double.MAX_VALUE;
            for (int j = 0; j < studentCount; j++) {
                if (!visited[j] && distances[j] < closestDist) {
                    closest = j;
                    closestDist = distances[j];
                }
            }

            if (closest == -1) break;
            visited[closest] = true;

            // Relax edges
            for (int j = 0; j < listSizes[closest]; j++) {
                int neighbor = adjacencyList[closest][j];
                double newDist = distances[closest] + 1; // Every edge has a weight of 1
                if (newDist < distances[neighbor]) {
                    distances[neighbor] = newDist;
                }
            }
        }

        // Calculate closeness centrality
        double totalDistance = 0;
        for (double dist : distances) {
            if (dist != Double.MAX_VALUE) {
                totalDistance += dist;
            }
        }

        if (totalDistance == 0) {
            System.out.println("Closeness centrality for " + firstName + " " + lastName + ": 0 (no connections)");
        } else {
            double closenessCentrality = (studentCount - 1) / totalDistance;
            double normalizedClosenessCentrality = closenessCentrality / (studentCount - 1);
            System.out.printf("Closeness centrality for " + firstName + " " + lastName + ": " + "%.2f\n", closenessCentrality);
            System.out.printf("Normalized closeness centrality for " + firstName + " " + lastName + ": " + "%.2f\n",
                    normalizedClosenessCentrality);
        }
    }
}
