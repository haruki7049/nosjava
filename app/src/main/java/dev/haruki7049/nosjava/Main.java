package dev.haruki7049.nosjava;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();

        // Left sidebar for navigation
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        Button homeBtn = new Button("Home");
        Button exploreBtn = new Button("Explore");
        Button notificationsBtn = new Button("Notifications");
        Button messagesBtn = new Button("Messages");
        sidebar.getChildren().addAll(homeBtn, exploreBtn, notificationsBtn, messagesBtn);
        root.setLeft(sidebar);

        // Center timeline for posts
        ListView<String> timeline = new ListView<>();
        for (int i = 1; i <= 20; i++) {
            timeline.getItems().add("Tweet content " + i + "\nThis is a sample post.");
        }
        root.setCenter(timeline);

        // Right sidebar for trends
        VBox rightSidebar = new VBox(10);
        rightSidebar.setPadding(new Insets(15));
        rightSidebar.setPrefWidth(250);
        rightSidebar.getChildren().add(new Label("Trends for you"));
        root.setRight(rightSidebar);

        // Set scene and show
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Twitter-like UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
