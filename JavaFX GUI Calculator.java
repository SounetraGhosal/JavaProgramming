package abc;

// We Will Import Necessary Packages For JavaFX
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloJav extends Application // Creation Of Application Class
{

    private TextField tf; // TextField Will Be Used To Display I/O

    // Variables to store the first number and the selected operator
    private double n1 = 0; // Variable To Store First Number
    private String opr = ""; // Variable To Store Operator

    private boolean NewN = false; // A Flag To Start A New Operation 

    @Override
    public void start(Stage primaryStage) // Start Function Of The JavaFX
    {
        tf = new TextField(); // A TextField Will Be Created
        
        tf.setEditable(false); // The User Can't Edit It Manually
        
        // We Are Aligning The TextField With Design
        tf.setAlignment(Pos.CENTER_RIGHT);
        tf.setStyle("-fx-font-size: 20px; -fx-background-color: #E8F8F5; -fx-border-color: #117A65; -fx-border-width: 2px;");

        GridPane gd = new GridPane(); // We Will Create A GridPane For The Buttons
        gd.setAlignment(Pos.CENTER);  
        
        gd.setHgap(10); // The Horizontal Gap Between Buttons
        gd.setVgap(10); // The Vertical Gap Between Buttons
        gd.setStyle("-fx-padding: 20px; -fx-background-color: #D6DBDF;");

        // THe Button Texts Will Be Taken In Array
        String[] bt = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        int rw = 1; // Varaible For Row
        int cl = 0; // Variable For Column

        // We Will Use A Loop To Create And Add Buttons
        for (String txt : bt) 
        {
            Button zap = new Button(txt); // The Button Will be Created 
            
            // The Size And Style Will Be Provided
            zap.setPrefSize(60, 60); 
            zap.setStyle("-fx-font-size: 18px; -fx-background-color: #AED6F1; -fx-text-fill: #154360; -fx-background-radius: 10;");

            // This Is The Normal Styles For Buttons
            final String Normal = "-fx-font-size: 18px; -fx-background-color: #AED6F1; -fx-text-fill: #154360; -fx-background-radius: 10;";
            // This Is The Style When Buttons Are Pressed
            final String Pressed = "-fx-font-size: 18px; -fx-background-color: #5DADE2; -fx-text-fill: white; -fx-background-radius: 10;";

            zap.setOnAction(e -> Click(txt)); // We Will Call The Click Function

            // The Button Color Will Change With Mouse Press
            zap.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> zap.setStyle(Pressed));
            zap.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> zap.setStyle(Normal));

            gd.add(zap, cl, rw); // The Button Will Be Added To The Grid

            // Logic To Move To The Next Column And Row
            cl++;
            if (cl > 3) 
            {
                cl = 0;
                rw++;
            }
        }

        // We Will Create A VBox To Take The TextField And Buttons
        VBox layout = new VBox(15, tf, gd);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px; -fx-background-color: #EBF5FB;");

        // The Scene Will Be Created And Called To The Stage
        Scene sc = new Scene(layout, 300, 400);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(sc);
        
        primaryStage.show(); // The Window Will Display With Show()
    }

    private void Click(String txt) // Method Will Handle The All Button Clicks
    {
        if ("0123456789".contains(txt)) // If Any Number Digit Is Clicked
        {
            if (NewN) 
            {
                tf.clear(); // The Previous Result Will Be Cleared
                NewN = false;
            }
            tf.appendText(txt); // The Digit Will Come To The Display
        }
        else if (txt.equals("C")) // If The Clear (C) Button Is Clicked
        {
            tf.clear();
            n1 = 0;
            opr = "";
            NewN = false;
        }
        else if (txt.equals("=")) // If The Equal (=) Button Is Clicked
        {
            Calculate(); // The Calculate() Method Will Be Called
        }
        else // If Any Operator Is Clicked
        {
            if (!tf.getText().isEmpty()) 
            {
                // Calculation Of The Value
                n1 = Double.parseDouble(tf.getText()); 
                opr = txt; 
                NewN = true; //
            }
        }
    }

    // Method To Perform Arithmetic Calculation
    private void Calculate() 
    {
        if (opr.isEmpty() || tf.getText().isEmpty()) // If No Operator Is Pressed
        {
            return;
        }

        double n2 = Double.parseDouble(tf.getText()); // Variable For The Second Number
        double res = 0;

        // Switch Case For Different Operators
        switch (opr) 
        {
            case "+": res = n1 + n2; break;
            case "-": res = n1 - n2; break;
            case "*": res = n1 * n2; break;
            case "/":
                if (n2 != 0) // Handling The Zero Division Error
                    res = n1 / n2;
                else 
                {
                    tf.setText("Arithmetic Exception Error"); // Handle divide by zero
                    opr = "";
                    return;
                }
                break;
        }

        // The Result Will Be Displayed
        tf.setText(String.valueOf(res));
        opr = "";
        
        NewN = true; // The Calculator Will Be Ready For Next Input
    }

    // Main Method To Launch The JavaFX Application
    public static void main(String[] args) 
    {
        launch(args);
    }
}
