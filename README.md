Personnel Management System (PMS)

**Overview**
- A Java-based command-line application for managing university personnel, including students, faculty, and staff. Features data validation, file persistence, and dynamic reporting with sorting capabilities.

**Features**
**User Management:**
- Add/delete students, faculty, and staff
- Validate IDs (format: LLNNNN), GPAs (0.0-4.0), and department affiliations

**Reporting:**
- Generate comprehensive reports sorted by GPA or name
- Automatic file persistence (report.txt)

**Special Functions:**
- Print tuition invoices with 25% discount for high-GPA (≥3.85) students
- View detailed personnel information

**Technologies**
Core: Java 8+

**Key Concepts:**
- Object-Oriented Programming (inheritance, polymorphism)
- File I/O (PrintWriter)
- Collections framework (ArrayList, custom Comparator)
- Input validation and error handling

**Class Structure**
Person (Abstract)
├── Employee (Abstract)
│   ├── Faculty
│   └── Staff
└── Student

**How to Run**
**Prerequisites:** JDK 8+ installed

**Compilation:**
bash
javac Main.java

**Execution:**
bash
java Main

**Usage Examples**

**Adding a Student:**
Name: John Doe
ID: AB1234
GPA: 3.9
Credit Hours: 12

**Generating Reports:**
- Sort students by GPA or name
- Auto-saves to report.txt

**Tuition Invoice:**
Tuition invoice for John Doe:
------------------------------------------------------
John Doe        AB1234
Credit Hours: 12 ($236.45/credit hour)
Fees: $52.00
Total payment (75% discount applied): $2,130.05
------------------------------------------------------

**Project Highlights:**
Input Validation: 3-attempt limit for invalid entries
Inheritance: Shared properties via Person and Employee abstract classes
Sorting: Custom comparators for GPA/name sorting
Error Handling: Robust exception handling for file operations

**Contributors:**
Peter Trinh (@petertrinh4)
Ellie Carron
