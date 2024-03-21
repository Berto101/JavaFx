import javafx.application.Application;
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
    private ObservableList<String> courses = FXCollections.observableArrayList("Math", "Physics", "Chemistry"); // Example courses

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
        ComboBox<String> nameComboBox = new ComboBox<>(); // Dropdown for selecting students
        nameComboBox.setItems(getStudentNames());
        nameComboBox.setPromptText("Select student");

        Label courseLabel = new Label("Course:");
        ComboBox<String> courseComboBox = new ComboBox<>(courses);
        courseComboBox.setPromptText("Select course");

        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter grade");

        Button assignGradeButton = new Button("Assign Grade");
        assignGradeButton.setOnAction(e -> {
            String selectedStudent = nameComboBox.getValue();
            String selectedCourse = courseComboBox.getValue();
            String grade = gradeField.getText();
            try {
                if (selectedStudent == null || selectedCourse == null || grade.isEmpty()) {
                    throw new IllegalArgumentException("Please select a student, a course, and enter a grade.");
                }
                Student student = getStudent(selectedStudent);
                if (student == null) {
                    throw new IllegalArgumentException("Selected student not found.");
                }
                student.setGrade(grade);
                student.setCourse(selectedCourse);
                studentTable.refresh(); // Refresh the table to reflect the updated grade
            } catch (IllegalArgumentException ex) {
                displayError("Error", ex.getMessage());
            }
        });

        studentForm.add(nameLabel, 0, 0);
        studentForm.add(nameComboBox, 1, 0);
        studentForm.add(courseLabel, 0, 1);
        studentForm.add(courseComboBox, 1, 1);
        studentForm.add(gradeLabel, 0, 2);
        studentForm.add(gradeField, 1, 2);
        studentForm.add(assignGradeButton, 0, 3, 2, 1);

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

    private ObservableList<String> getStudentNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for (Student student : students) {
            names.add(student.getName());
        }
        return names;
    }

    private Student getStudent(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    private void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Student {
    private final String name;
    private final String id;
    private String course;
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

    public void setCourse(String course) {
        this.course = course;
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
