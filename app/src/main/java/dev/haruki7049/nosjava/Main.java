package dev.haruki7049.nosjava;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nostr.client.springwebsocket.NostrRelayClient;
import nostr.base.Kinds;
import nostr.event.filter.EventFilter;
import nostr.event.filter.Filters;
import nostr.event.message.ReqMessage;


public class Main extends Application {

  private ListView<String> timeline;

  private NostrRelayClient relayClient;

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
    timeline = new ListView<>();
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

    // Start listening to Nostr events
    connectToNostr();
  }

  private void connectToNostr() {
    // Add a loading message
    timeline.getItems().add("Connecting to relay...");

    EventFilter filter = EventFilter.builder().kinds(List.of(Kinds.TEXT_NOTE)).limit(100).build();

    Filters filters = new Filters(filter);
    String subscriptionId = "my-sub-" + System.currentTimeMillis();
    ReqMessage req = new ReqMessage(subscriptionId, filters);

    NostrRelayClient.connectAsync("wss://yabu.me")
        .thenCompose(
            client ->
                client.subscribeAsync(
                    req.encode(),
                    message -> System.out.println("Event: " + message),
                    error -> System.err.println("Error: " + error),
                    () -> System.out.println("Closed")))
        .thenAccept(subscription -> {});
  }

  @Override
  public void stop() throws Exception {
    // Clean up resources and close connection when the app is closed
    if (relayClient != null) {
      relayClient.close();
    }
    super.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
