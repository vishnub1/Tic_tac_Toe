package org.example.tic_tac_toe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Tic_Tac_Toe extends Application {

    private Button buttons[][] = new Button[3][3];  // greed of button
    private  Label playerXScoreLabel, playerOScoreLabel;  // runtime changing
    private int playerXScore = 0, playerOScore = 0;
    private boolean playerXTurn = true;

    private BorderPane createContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        BackgroundFill backgroundFill = new BackgroundFill(
                Color.valueOf("#0ca6ed"),
                new CornerRadii(10),
                new Insets(10)
        );
        Background background = new Background(backgroundFill);
        root.setBackground(background);


        // Title
        Label titlLabel = new Label("TIC-TAC-TOE");
        titlLabel.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
        root.setTop(titlLabel);
        BorderPane.setAlignment(titlLabel, Pos.CENTER);


        // Game Board
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        // Generating 9 button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(150, 150);
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(actionEvent -> buttonClicked(button));
                buttons[i][j] = button;

                // Add button to grid pain
                gridPane.add(button, j, i);  // in GridPane first (col and then row)
            }
        }

        root.setCenter(gridPane);  // setting button to center

        // Score
        HBox scoreBoard = new HBox(20);
        scoreBoard.setAlignment(Pos.CENTER);
        playerXScoreLabel = new Label("Player 'X' : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");

        playerOScoreLabel = new Label("Player 'O' : 0");
        playerOScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");

        scoreBoard.getChildren().addAll(playerXScoreLabel, playerOScoreLabel);
        root.setBottom(scoreBoard);


        return root;
    }



    private void buttonClicked(Button button) {
        // if button is not having any text else return
        if(button.getText().equals("")) {
            if(playerXTurn) {
                button.setText("X");
                button.setTextFill(Color.RED);
            }
            else {
                button.setText("O");
            }

            // change the turn variable
            playerXTurn = !playerXTurn;

            checkWinner();
        }
    }

    private void checkWinner() {
        // row
        for (int row = 0; row < 3; row++) {
            if(buttons[row][0].getText().equals(buttons[row][1].getText())
                && buttons[row][1].getText().equals(buttons[row][2].getText())
                    && !buttons[row][0].getText().isEmpty()
            ) {
                // we will have a winner
                String winner = buttons[row][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // col
        for (int col = 0; col < 3; col++) {
            if(buttons[0][col].getText().equals(buttons[1][col].getText())
                    && buttons[1][col].getText().equals(buttons[2][col].getText())
                    && !buttons[0][col].getText().isEmpty()
            ) {
                // we will have a winner
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        // diagonal
        // First diagonal
        if(buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty()
        ) {
            // we will have a winner
            String winner = buttons[0][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        // Second diagonal
        if(buttons[2][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[0][2].getText())
                && !buttons[2][0].getText().isEmpty()
        ) {
            // we will have a winner
            String winner = buttons[2][0].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        // Tie
        boolean tie = true;
        for(Button row[] : buttons) {
            for(Button button : row) {
                if(button.getText().isEmpty()) {
                    tie = false;
                    break;
                }
            }
        }
        if(tie) {
            showTieDialog();
            resetBoard();
        }
    }

    private void showWinnerDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner ");
        alert.setContentText("Congratulation.! "+ winner +" ! You Won The Game");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void showTieDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setContentText("Game Over, It's a Tie.");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void updateScore(String winner) {
        if(winner.equals("X")) {
            playerXScore++;
            playerXScoreLabel.setText("Player X :" + playerXScore);
        }
        else {
            playerOScore++;
            playerOScoreLabel.setText("Player O :"+ playerOScore);
        }

    }

    private void resetBoard() {
        for(Button row[] : buttons) {
            for(Button button : row) {
                button.setText("");
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}