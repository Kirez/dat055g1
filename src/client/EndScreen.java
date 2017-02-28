/*package client;


import java.awt.Button;
import java.awt.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


/*EndScreen with the options to have a rematch, etc.*/
/*public class EndScreen implements AbstractScreen {

    private GridPane layout;
    private Button menuButton;
    private Button rematchButton;
    private Button exitButton;
    private Label winner;
    private GameApplication owner;

    public EndScreen(GameApplication gameApplication) {
        owner = gameApplication;
        menuButton = new Button("Main Menu");
        rematchButton = new Button("Rematch");
        exitButton = new Button("Exit");
        winner = new Label("Winner: " + winningPlayer);
        winner.setFont(Font.font(48));
        layout = new GridPane();
        layout.addColumn(0, winner, rematchButton, menuButton, exitButton);
        rematchButton.setOnAction(this::onRematchButton);
        menuButton.setOnAction(this::onMenuButton);
        exitButton.setOnAction(this::onExitButton);
        exit();
        layout.setAlignment(Pos.CENTER);
    }

    @Override
    public void enter(Stage stage) {
        Group root = new Group();
        root.getChildren().add(layout);
        Scene scene = new Scene(root);

        layout.setPrefSize(stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
    }

    void onExitButton(ActionEvent event) {
        Platform.exit();
    }

    void onPlayButton(ActionEvent event) {
        owner.setActiveScreen(owner.playScreen);
    }

    void onMultiplayerButton(ActionEvent event) { owner.setActiveScreen(owner.connectScreen);}

    @Override
    public void exit() {

    }
}*/

