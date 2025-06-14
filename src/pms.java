import java.util.*;
import java.util.Comparator;
import java.io.*;
import java.text.SimpleDateFormat;

/*
* - Personnel Management System
* - Peter Trinh and Ellie Carron
*/

public class Main {

  public static ArrayList<Person> people = new ArrayList<Person>();
  public static Scanner scanner = new Scanner(System.in);
  
  public static void printMenu() {
    System.out.println("Welcome to the Personnel Management System\n");
    System.out.println("Choose one of the options:");
    System.out.println("\t1- Enter Faculty Information");
    System.out.println("\t2- Enter Student Information");
    System.out.println("\t3- Print Tuition Invoice for a Student");
    System.out.println("\t4- Print Faculty Information");
    System.out.println("\t5- Enter Staff Information");
    System.out.println("\t6- Print Staff Information");
    System.out.println("\t7- Delete a Person");
    System.out.println("\t8- Exit Program");
    System.out.print("\t\nEnter your selection: ");
  }

  // Check if ID is valid
  private static boolean isValidId(String id) {
    return id.matches("[a-zA-Z]{2}\\d{4}");
  }

  // Check if ID is unique
  private static boolean isUniqueId(String id) {
    for (Person person : people) {
      if (person.getId().equalsIgnoreCase(id)) {
        return false;
      }
    }
    return true;
  }

  // Check if department is valid
  private static boolean isValidDepartment(String department) {
    String dept = department.toLowerCase();
    return dept.equals("mathematics") || dept.equals("engineering") || dept.equals("english");
  }

  // Menu option 1
  private static void addFaculty() {
    System.out.println("\nEnter faculty info:");

    String fullName = getInput("Name: ", 3);
    String id = getValidId(3);
    if (id == null) return;

    String department = getValidDepartment(3);
    if (department == null) return;

    String rank = getValidRank(3);
    if (rank == null) return;

    people.add(new Faculty(fullName, id, department, rank));

    System.out.println("\nFaculty added!");
  }

  // Menu option 2
  private static void addStudent() {
    System.out.println("\nEnter student info:");

    String fullName = getInput("Name: ", 3);
    String id = getValidId(3);
    if (id == null) return;

    double gpa = getValidGpa(3);
    if (gpa == -1) return;

    int creditHours = getValidCreditHours(3);
    if (creditHours == -1) return;

    people.add(new Student(fullName, id, gpa, creditHours));

    System.out.println("\nStudent added!");
  }

  // Menu option 3
  private static void printTuitionInvoice() {
    System.out.println("\nEnter the student's ID: ");
    String id = scanner.nextLine();

    for (Person person : people) {
      if (person instanceof Student && person.getId().equalsIgnoreCase(id)) {
        person.print();
        return;
      }
    }
    System.out.println("\nStudent not found!");
  }

  // Menu option 4
  private static void printFaculty() {
    System.out.print("\nEnter the faculty's ID: ");
    String id = scanner.nextLine();

    for (Person person : people) {
      if (person instanceof Faculty && person.getId().equalsIgnoreCase(id)) {
        person.print();
        return;
      }
    }
    System.out.println("\nFaculty not found!");
  }

  // Menu option 5
  private static void addStaff() {
    System.out.println("\nEnter staff info:");

    String fullName = getInput("Name: ", 3);
    String id = getValidId(3);
    if (id == null) return;

    String department = getValidDepartment(3);
    if (department == null) return;

    String status = getValidStatus(3);
    if (status == null) return;

    people.add(new Staff(fullName, id, department, status));
    
    System.out.println("\nStaff added!");
  }

  // Menu option 6
  private static void printStaff() {
    System.out.print("\nEnter the staff's ID: ");
    String id = scanner.nextLine();

    for (Person person : people) {
      if (person instanceof Staff && person.getId().equalsIgnoreCase(id)) {
        person.print();
        return;
      }
    }
    System.out.println("\nStaff not found!");
  }

  // Menu option 7
  private static void deletePerson() {
    System.out.print("\nEnter the person's ID to delete: ");
    String id = scanner.nextLine();

    Iterator<Person> iterator = people.iterator();

    while (iterator.hasNext()) {
      Person person = iterator.next();
      if (person.getId().equalsIgnoreCase(id)) {
        iterator.remove();
        System.out.println("\nPerson deleted!");
        return;
      }
    }
    System.out.println("\nPerson not found!");
  }

  // Menu option 8
  private static void exitProgram() {
    System.out.print("\nWould you like to create the report? (Y/N): ");
    String answer = scanner.nextLine();

    if (answer.equalsIgnoreCase("Y")) {
      System.out.print("Would you like to sort your students by GPA or name? (Enter 1 for GPA. Enter 2 for name): ");
      int sortChoice = scanner.nextInt();

      generateReport(sortChoice == 1);
      System.out.println("Report created and saved to report.txt!");
    }
    System.out.println("Goodbye!");
  }

  // Generate and sort report
  private static void generateReport(boolean sortByGpa) {
    try {
      PrintWriter writer = new PrintWriter("report.txt");
      String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

      writer.println("Report generated on " + date);
      writer.println("-------------------------------");

      writer.println("\n\nFaculty Members");
      writer.println("-------------------------------");

      //Faculty
      int count = 1;
      for(Person person : people) {
        if(person instanceof Faculty) {
          Faculty f = (Faculty) person;
          writer.printf("%d. %s%n", count++, f.getFullName());
          writer.printf("ID: %s%n", f.getId());
          writer.printf("%s, %s%n\n", f.getRank(), f.getDepartment());
        }
      }
      //Staff
      writer.println("Staff Members");
      writer.println("-------------------------------");
      count = 1;
      for (Person person : people) {
        if (person instanceof Staff) {
          Staff s = (Staff) person;
          writer.printf("%d. %s%n", count++, s.getFullName());
          writer.printf("ID: %s%n", s.getId());
          writer.printf("%s, %s%n\n", s.getDepartment(), s.getStatus());
        }
      }
      //Students
      writer.print("Students (Sorted by ");
      writer.print(sortByGpa ? "GPA" : "name");
      writer.println(")");
      writer.println("-------------------------------");

      ArrayList<Student> students = new ArrayList<Student>();
      for (Person person : people) {
        if (person instanceof Student) {
          students.add((Student) person);
        }
      }
      if (sortByGpa) {
        Collections.sort(students, new Comparator<Student>() {
          public int compare(Student s1, Student s2) {
            return Double.compare(s2.getGpa(), s1.getGpa());
          }
        });

      } 
      else {
        Collections.sort(students, new Comparator<Student>() {
          public int compare(Student s1, Student s2) {
            return s1.getFullName().compareToIgnoreCase(s2.getFullName());
          }
        });

      }

      count = 1;
      for (Student s : students) {
        writer.printf("%d. %s%n", count++, s.getFullName());
        writer.printf("ID: %s%n", s.getId());
        writer.printf("GPA: %.2f%n", s.getGpa());
        writer.printf("Credit hours: %d%n\n", s.getCreditHours());
      }
      writer.close();
    }
      catch (IOException e) {
        System.out.println("An error occurred while generating the report.");
      }
  }

  private static String getInput(String prompt, int maxAttempts) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  private static String getValidId(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("ID: ");
      String id = scanner.nextLine();

      if (!isValidId(id)) {
        System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");

        if (i < maxAttempts - 1) continue;

        return null;
      }
      if (!isUniqueId(id)) {
        System.out.println("ID already exists. Please enter a unique ID.");

        if (i < maxAttempts - 1) continue;

        return null;
      }
      return id;
    }
    return null;
  }

  private static double getValidGpa(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("GPA: ");
      
      try {
        double gpa = scanner.nextDouble();
        scanner.nextLine();

        if (gpa < 0 || gpa > 4) {
          System.out.println("GPA must be between 0.0 and 4.0");

          if (i < maxAttempts - 1) continue;

          return -1;
        }
        return gpa;
      } catch (InputMismatchException e) {
        System.out.println("Please enter a valid number for GPA.");
        scanner.nextLine();

        if (i < maxAttempts - 1) continue;

        return -1;
      }
    }
    return -1;
  }

  private static int getValidCreditHours(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("Credit Hours: ");

      try {
        int creditHours = scanner.nextInt();
        scanner.nextLine();

        if (creditHours < 0) {
          System.out.println("Credit hours cannot be negative");

          if (i < maxAttempts - 1) continue;

          return -1;
        }
        return creditHours;
      } catch (InputMismatchException e) {
          System.out.println("Please enter a valid number for credit hours.");
          scanner.nextLine();

          if (i < maxAttempts - 1) continue;

          return -1;
      }
    }
    return -1;
  }

  private static String getValidDepartment(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("Department (Mathematics, Engineering, English): ");
      String department = scanner.nextLine();

      if (!isValidDepartment(department)) {
        System.out.println("Invalid department. Must be Mathematics, Engineering, or English");

        if (i < maxAttempts - 1) continue;

        return null;
      }
      return department;
    }
    return null;
  }

  private static String getValidRank(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("Rank (Professor or Adjunct): ");
      String rank = scanner.nextLine();

      if (!rank.equalsIgnoreCase("Professor") && !rank.equalsIgnoreCase("Adjunct")) {
        System.out.println("Invalid rank. Must be Professor or Adjunct");

        if (i < maxAttempts - 1) continue;

        return null;
      }
      return rank;
    }
    return null;
  }  

  private static String getValidStatus(int maxAttempts) {
    for (int i = 0; i < maxAttempts; i++) {
      System.out.print("Status (Full-time or Part-time): ");
      String status = scanner.nextLine();

      if (!status.equalsIgnoreCase("Full-time") && !status.equalsIgnoreCase("Part-time")) {
        System.out.println("Invalid status. Must be Full-time or Part-time");

        if (i < maxAttempts - 1) continue;

        return null;
      }
      return status;
    }
    return null;
  }

  //PERSON CLASS
  abstract static class Person {
    private String fullName;
    private String id;
    public abstract void print();

    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public void setFullName(String fullName) {
      this.fullName = fullName;
    }
    public String getFullName() {
      return fullName;
    }
  }
  
  //EMPLOYEE CLASS
  abstract static class Employee extends Person {
    private String department;

  public void setDepartment(String department) {
    this.department = department;
  }
  public String getDepartment() {
    return department;
  }
}
  
  //STAFF CLASS
  static class Staff extends Employee {
    private String status;
    
    public void setStatus(String status) {
      this.status = status;
    }
    public String getStatus() {
      return status;
    }

    public Staff(String fullName, String id, String department, String status) {
      setFullName(fullName);
      setId(id);
      setDepartment(department);
      setStatus(status);
    }
    
    @Override
    public void print() {
      System.out.println("\nStaff member " + getFullName() + " is in the " + getDepartment() + " department and has the status of " + getStatus() + ".");
    }
    
  }

  //FACULTY CLASS
  static class Faculty extends Employee {
    private String rank;

    public void setRank(String rank) {
      this.rank = rank;
    }
    public String getRank() {
      return rank;
    }

    public Faculty(String fullName, String id, String department, String rank) {
      setFullName(fullName);
      setId(id);
      setDepartment(department);
      setRank(rank);
    }
    
    @Override
    public void print() {
      System.out.println("\nFaculty member " + getFullName() + " is in the " + getDepartment() + " department and has the rank of " + getRank() + ".");
    }
    
  }
  
  //STUDENT CLASS
  static class Student extends Person {
    private double gpa;
    private int creditHours;
    public static final double RATE = 236.45;
    public static final double FEE = 52;

    public void setCreditHours(int creditHours) {
      this.creditHours = creditHours;
    }
    public int getCreditHours() {
      return creditHours;
    }
    public void setGpa(double gpa) {
      this.gpa = gpa;
    }
    public double getGpa() {
      return gpa;
    }

    public Student(String fullName, String id, double gpa, int creditHours) {
      setFullName(fullName);
      setId(id);
      setGpa(gpa);
      setCreditHours(creditHours);
    }
    
    @Override
    public void print() {
      double tuition = RATE * creditHours;
      double total = tuition + FEE;
      if (gpa >= 3.85) {
        tuition *= 0.75;
        total = tuition + FEE;
      }
        System.out.println("\nTuition invoice for " + getFullName() + ":");
        System.out.println("------------------------------------------------------");
        System.out.printf("%s\t\t%s%n", getFullName(), getId());
        System.out.printf("Credit Hours: %d ($%.2f/credit hour)%n", creditHours, RATE);
        System.out.printf("Fees: $%.2f%n", FEE);
        if (gpa >= 3.85) {
            System.out.printf("Total payment (75%% discount applied): $%.2f%n", total);
        } else {
            System.out.printf("Total payment: $%.2f%n", total);
        }
        System.out.println("------------------------------------------------------");
    }
  }

  //MAIN METHOD
  public static void main(String[] args) {
    int choice;

    do {
      printMenu();
      try {
        choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
          case 1:
            addFaculty();
            break;
          case 2:
            addStudent();
            break;
          case 3:
            printTuitionInvoice();
            break;
          case 4:
            printFaculty();
            break;
          case 5: 
            addStaff();
            break;
          case 6:
            printStaff();
            break;
          case 7:
            deletePerson();
            break;
          case 8:
            exitProgram();
            return;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } 
      catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number between 1 and 8.");
        scanner.nextLine(); // clear invalid input
        choice = 0;
      }
    } 
    while (true);
  }
}