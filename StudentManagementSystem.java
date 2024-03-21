import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StudentManagementSystem extends Application {

    private TableView<Student> studentTable = new TableView<>();
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<String> courses = FXCollections.observableArrayList("Math", "Physics", "Chemistry");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Create student table
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Student, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(cellData -> cellData.getValue().courseProperty());

        TableColumn<Student, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());

        studentTable.getColumns().addAll(nameCol, idCol, courseCol, gradeCol);
        studentTable.setItems(students);

        // Create student details form
        GridPane studentForm = new GridPane();
        studentForm.setHgap(10);
        studentForm.setVgap(5);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter student's name");

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        idField.setPromptText("Enter student's ID");

        Label courseLabel = new Label("Course:");
        ComboBox<String> courseComboBox = new ComboBox<>(courses);
        courseComboBox.setPromptText("Select course");

        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter student's grade");

        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> {
            Student student = new Student(nameField.getText(), idField.getText(), courseComboBox.getValue(), gradeField.getText());
            students.add(student);
            clearFields(nameField, idField, gradeField);
        });

        Button updateButton = new Button("Update Student");
        updateButton.setOnAction(e -> {
            // Add code to handle updating student information
            // This could involve selecting a student from the table and displaying their information in the form for editing
        });

        Button enrollButton = new Button("Enroll Student");
        enrollButton.setOnAction(e -> {
            // Implement enrollment functionality
            // Get the selected student and course from the table and ComboBox, and enroll the student in the course
        });

        Button assignGradeButton = new Button("Assign Grade");
        assignGradeButton.setOnAction(e -> {
            // Implement functionality to assign grade to the selected student for the selected course
            // Update the GUI to reflect the changes
        });

        studentForm.add(nameLabel, 0, 0);
        studentForm.add(nameField, 1, 0);
        studentForm.add(idLabel, 0, 1);
        studentForm.add(idField, 1, 1);
        studentForm.add(courseLabel, 0, 2);
        studentForm.add(courseComboBox, 1, 2);
        studentForm.add(gradeLabel, 0, 3);
        studentForm.add(gradeField, 1, 3);
        studentForm.add(addButton, 0, 4);
        studentForm.add(updateButton, 1, 4);
        studentForm.add(enrollButton, 0, 5);
        studentForm.add(assignGradeButton, 1, 5);

        // Menu bar
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        VBox topContainer = new VBox(menuBar);
        root.setTop(topContainer);

        // Center layout
        VBox centerContainer = new VBox(10, new Label("Student Records"), studentTable);
        root.setCenter(centerContainer);

        // Bottom layout
        root.setBottom(studentForm);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Management System");
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Student {
    private final String name;
    private final String id;
    private final String course;
    private String grade;

    public Student(String name, String id, String course, String grade) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty idProperty() {
        return new SimpleStringProperty(id);
    }

    public StringProperty courseProperty() {
        return new SimpleStringProperty(course);
    }

    public StringProperty gradeProperty() {
        return new SimpleStringProperty(grade);
    }
}
