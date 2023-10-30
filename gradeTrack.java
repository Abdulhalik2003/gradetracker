import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java .util.List;

 class Student {
    private String name;
    private List<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    void addGrade( int grade) {
        grades.add(grade);
    }
     public void clearGrades() {
         grades.clear();
     }


     public String format() {
   // return name + "," + String.join(",", grades.stream().map(String::valueOf).toList());
         StringBuilder result = new StringBuilder();
         result.append(name);
         result.append(",");
         for (int i = 0; i < grades.size(); i++) {
            if (i > 0) {
               result.append(",");
          }
          result.append(grades.get(i).toString());
        }
         return result.toString();
   }

     public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }
 }
public  class gradeTrack{
    private static final String DATA_FILE = "data.txt";
    private static List<Student> students = new ArrayList<>();

        public static void main(String[]args)
        {

            while(true) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Student Grade Tracker Menu:");
                System.out.println("1. To Add the Student with Grade details");
                System.out.println("2. Delete Student Name and Grade");
                System.out.println("3. To the Search Student by Name");
                System.out.println("4. Modify Student Grades by Name");
                System.out.println("5. Class Statistics(Highest,Lowest,Average)");
                System.out.println("6. Exit");
                System.out.print("Enter your Option: ");



                int option = sc.nextInt();
                sc.nextLine();


                switch (option) {
                    case 1:
                        addStudent(sc);
                        break;
                    case 2:
                        deleteDetails(sc);
                        break;
                    case 3:
                        searchtheStudent(sc);

                        break;
                    case 4:
                        modifyGrades(sc);
                        break;
                    case 5:
                        displayavaerge();
                        break;
                    case 6:
                        saveData();
                        System.out.println("Exit");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");

                }
            }

        }
        private static  void loadData(){
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    Student student = new Student(name);
                    for (int i = 1; i < parts.length; i++) {
                        try {
                            int grade = Integer.parseInt(parts[i]);
                            student.addGrade(grade);
                        } catch (NumberFormatException e) {

                        }
                    }
                    students.add(student);
                }
            } catch (IOException e) {
                System.out.println("Error loading data file: " + e.getMessage());
            }
        }
 private static void saveData(){

     try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
         for (Student student : students) {
             writer.write(student.format());
             writer.newLine();
         }
     } catch (IOException e) {
         System.out.println("Error saving data file: " + e.getMessage());
     }
 }

     private static void addStudent(Scanner sc) {
         System.out.print("Enter the Student Name: ");
         String studentName = sc.nextLine();


         Student student = new Student(studentName);

         System.out.print("Enter the number of Subjects: ");
         int numberOfSubjects =   Integer.parseInt(sc.nextLine());


         for (int i = 0; i < numberOfSubjects; i++) {
             System.out.print("Enter the grade for Subject " + (i + 1) + ": ");
             int grade = sc.nextInt();
             student.addGrade(grade);
         }


         students.add(student);

         System.out.println("Student added.");
     }
     private static void deleteDetails( Scanner sc){
         System.out.print("Enter student name to delete: ");
         String searchName = sc.nextLine().toLowerCase();
         boolean found = false;
         for(Student student:students){
             if(student.getName().toLowerCase().contains(searchName)){
                 students.remove(student);
                 System.out.print("deleted sucessfully");
                 found = true;
             } if(!found){
                 System.out.print("Student not found");
             }
         }
     }
    private static void searchtheStudent( Scanner sc) {
        loadData();
        System.out.print("Enter student name to search: ");
        String searchName = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                System.out.println("Found student: " + student.getName());
                System.out.println("Grades: " + student.format());
                System.out.println("Average Grade: " + student.getAverageGrade());
               // System.out.print("student details" + student.format());
              found = true;
              break;
            } if(!found){
                System.out.print("Student not found");
            }


        }}
    private static void modifyGrades( Scanner sc) {
        System.out.print("Enter student name to modify: ");
        String searchName = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {

                System.out.println("Current grades for " + student.getName() + ": " + student.format());
                student.clearGrades();boolean validGrade;
                do {
                    System.out.print("Enter a new grade (or 0 to finish): ");
                    int grade = Integer.parseInt(sc.nextLine());
                    if (grade == -1) {
                        break;
                    }
                    student.addGrade(grade);
                    validGrade = true;
                } while (validGrade);

                System.out.println("Grades modified.");
                found = true;
            }  if(!found) {
                System.out.print("Student not found");
            }


        }}
    private static void displayavaerge( ){
            if(students.isEmpty()){
                System.out.println("no students in class");
                return;
            }
        double highestGrade = Double.MAX_VALUE;
        double lowestGrade = Double.MIN_VALUE;
        double totalGrades = 0;

        for (Student student : students) {
            double averageGrade = student.getAverageGrade();
            double averageClassGrade = totalGrades / students.size();

        totalGrades += averageGrade;

        if (averageGrade > highestGrade) {
            highestGrade = highestGrade;
        }

        if (averageGrade < lowestGrade) {
            lowestGrade = averageGrade;
        }
    }

        double averageClassGrade = totalGrades / students.size();

            System.out.println("Class Statistics:");
            System.out.println("Number of Students: " + students.size());
            System.out.println("Highest Average Grade: " + highestGrade);
            System.out.println("Lowest Average Grade: " + lowestGrade);
            System.out.println("Average Class Grade: " + averageClassGrade);
    }

 }




