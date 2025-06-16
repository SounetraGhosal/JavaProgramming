import java.io.*;
import java.util.*;

class ZapStudent // Creating The ZapStudent Class
{
    String n; // A String Variable For Name
    String id; // A String Variable For ID
    ArrayList<Integer> gds; // An ArrayList Variable For Grades

    // Function To Take Inputs From User
    public ZapStudent(String n, String id, ArrayList<Integer> gds) 
    {
        this.n = n;
        this.id = id;
        this.gds = gds;
    }
    
    // Function To Display The Details Of The Students
    public void show() 
    {
        System.out.println("Name Of Student: " + n);
        System.out.println("Student ID: " + id);
        System.out.println("Received Grades Are: " + gds);
        System.out.println("----------------------");
    }
}

class Zapbook // Creating The Zapbook Class For Gradebook
{

    // Function To Take Input From The File (Read Operation)
    public static ArrayList<ZapStudent> Read(String fn) 
    {
        ArrayList<ZapStudent> s = new ArrayList<>(); // Creating A New Student ArrayList
        
        // We Are Trying To Read Values Using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(fn))) 
        {
            String l;
            while ((l = br.readLine()) != null) // Checking The Lines Of The File
            {
                String[] d = l.split("->"); // Spliting The Data In The File Using "->"
                
                // Storing The Values In The File
                String name = d[0];
                String id = d[1];
                ArrayList<Integer> gra = new ArrayList<>();
                
                for (int i = 2; i < d.length; i++) 
                {
                    gra.add(Integer.parseInt(d[i])); // Adding All The Grades
                }
                s.add(new ZapStudent(name, id, gra)); // Adding All The Values For A Student
            }
            // We Will Use Try-Catch Block For Some Exceptions
        } catch (FileNotFoundException e) {
            System.out.println("We Are Starting With An Empty Gradebook");
        } catch (IOException e) {
            System.out.println("We Can't Read The File");
        }
        return s;
    }

    // Function To Take Output From The File (Write Operation)
    public static void Write(String fn, ArrayList<ZapStudent> s) 
    {
        // We Are Trying To Write Values Using BufferedWriter
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fn))) 
        {
            for (ZapStudent sg : s) 
            {
                // Joining Every Values Of The Student Class
                String gstr = String.join(",", sg.gds.stream().map(String::valueOf).toArray(String[]::new));
                bw.write(sg.n + "->" + sg.id + "->" + gstr + "\n");
            }
            // Implementing The Catch Function For Exceptions
        } catch (IOException e) {
            System.out.println("We Can't Write The File");
        }
    }

    // Function To Add New Students In The GradeBook
    public static void Add(ArrayList<ZapStudent> s, String n, String id, ArrayList<Integer> g) 
    {
        s.add(new ZapStudent(n, id, g)); // We Are Calling The ZapStudent Function
        System.out.println("A New Student Has Added Successfully In The GradeBook");
    }

    // Function To Update Students In The GradeBook
    public static void Update(ArrayList<ZapStudent> s, String id, ArrayList<Integer> g) 
    {
        for (ZapStudent sg : s) // Checking Every Student In The GradeBook
        {
            if (sg.id.equals(id)) // If The Studnet With Similar ID Present
            {
                sg.gds = g; // We Are Providing The New Grades
                System.out.println("The Grade Updated Successfully");
                return;
            }
        }
        System.out.println("We Can't Find The Student");
    }

    // Function To Display The Student Data
    public static void Display(ArrayList<ZapStudent> s) 
    {
        for (ZapStudent sg : s) 
        {
            sg.show();
        }
    }
}

public class Main 
{
    public static void main(String[] args) 
    {
        String File = "students.txt"; // Creating The File To Do File I/O
        ArrayList<ZapStudent> zap = Zapbook.Read(File); // Reading The File
        Scanner sc = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\n1. Add Student To GradeBook");
            System.out.println("2. Update Student To GradeBook");
            System.out.println("3. Display Student Data From GradeBook");
            System.out.println("4. Exit From The Application");
            System.out.print("Enter Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            System.out.println();

            if (ch == 1) // If The Choice Is 1 
            {
                // Taking The Inputs From The User
                System.out.print("Enter Student Name: ");
                String name = sc.nextLine();
                System.out.print("Enter Student ID: ");
                String id = sc.nextLine();
                System.out.print("Enter The Grades (Line-Separated): ");
                String[] ginput = sc.nextLine().split("-");
                
                ArrayList<Integer> grades = new ArrayList<>();
                
                for (String gd : ginput) // Adding The Grade Values
                {
                    grades.add(Integer.parseInt(gd.trim()));
                }
                Zapbook.Add(zap, name, id, grades); // Calling The Add() Function
            } 
            
            else if (ch == 2) // If The Choice Is 2
            {
                // Taking The Inputs From The Users
                System.out.print("Enter Student ID To Update: ");
                String sid = sc.nextLine();
                System.out.print("Enter New Grades (Line-Separated): ");
                String[] ginput = sc.nextLine().split("-");
                
                // Taking The New Grades
                ArrayList<Integer> newg = new ArrayList<>();
                for (String grade : ginput) 
                {
                    newg.add(Integer.parseInt(grade.trim()));
                }
                Zapbook.Update(zap, sid, newg); // Calling The Update() Function
            } 
            
            else if (ch == 3) // If The Choice Is 3
            {
                Zapbook.Display(zap);
            }
            
            else if (ch == 4) // If The Choice Is 4
            {
                Zapbook.Write(File, zap); // Calling The Write() Function
                System.out.println("We Can Exit, Thanks For Visiting");
                System.out.println("Data Saved Successfully");
                break;
            } 
            
            else // For Any Other Choices
            {
                System.out.println("Invalid Choice");
            }
        }
        sc.close();
    }
}
