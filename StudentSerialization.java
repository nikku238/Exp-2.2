import java.io.*;

// Step 1: Define Student class
class Student implements Serializable {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("GPA: " + gpa);
    }
}

public class StudentSerialization {
    public static void main(String[] args) {
        String filename = "student.ser";

        // Create a student object
        Student s1 = new Student(101, "Alice", 9.1);

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(s1);
            System.out.println("Student serialized successfully!");
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }

        // Deserialization
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Student s2 = (Student) in.readObject();
            System.out.println("\nStudent deserialized:");
            s2.display();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("IOException occurred!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found!");
        }
    }
}
