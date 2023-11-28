package com.example.standard_huffman;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

public class HelloApplication extends Application {
    private String selectedFilePath = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Standard Huffman Compressor");

        // Create the first scene (full screen before the delay)
        VBox firstScene = new VBox();
        firstScene.setStyle(
                "-fx-background-image: url('logo.jpg'); " +
                        "-fx-background-size: cover; " +
                        "-fx-alignment: center;"
        );
        Label welcomeLabel = new Label("Welcome to Standard Huffman Compressor");
        welcomeLabel.setStyle(
                "-fx-font-size: 45px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white;"
        );
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Empty string to hide the exit hint

        firstScene.getChildren().add(welcomeLabel);

        // Create the second scene (normal screen after the delay)
        VBox secondScene = new VBox();
        secondScene.setStyle(
                "-fx-background-image: url('logo.jpg'); " +
                        "-fx-background-size: 1200 675; " +
                        "-fx-alignment: center;"
        );

        Button browseButton = new Button("Browse");
        browseButton.setStyle(
                "-fx-margin: 45 0 0 47%;" +
                        "-fx-font-size: 1.2em;" +
                        "-fx-min-width: 100px;" +
                        "-fx-min-height: 40px;"
        );
        browseButton.setAlignment(Pos.CENTER);
        Label fileLabel = new Label("");
        Label fileLabel2 = new Label("");
        fileLabel.setStyle(
                "-fx-text-fill: white; " +
                        "-fx-margin: 40 0 0 49%;" +
                        "-fx-font-size: 1.2em;"
        );

        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a File");

            java.io.File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                selectedFilePath = selectedFile.getAbsolutePath();
                fileLabel2.setText(selectedFilePath);
                System.out.println(selectedFilePath);
                fileLabel.setText(selectedFile.getName());
            }
        });
        selectedFilePath = fileLabel2.getText();


        HBox buttonsContainer = new HBox();
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setStyle(
                "-fx-margin: 140 0 60 0;"
        );

        Button compressButton = new Button("Compress");
//        scaleButtonAndLabel(compressButton);
        compressButton.setStyle(
                "-fx-margin: 0 0 0 35%;" +
                        "-fx-min-width: 100px;" +
                        "-fx-min-height: 40px;"
        );
        compressButton.setOnAction(event -> {
            String inputFileName = fileLabel2.getText();
            // Perform compression
            Compressor compressor = new Compressor();
            String compressedString = null;
            try {
                compressedString = compressor.compress(HuffmanFileHandler.readTextFile(inputFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Output the compressed string (as binary) to the console for debugging
            System.out.println("Compressed String (Binary):");
            System.out.println(compressedString);

            // Specify output file name for the compressed data
            String compressedFileName = "/run/media/phantom/New Volume/University/OS/Assignments/Router_Simulator/Standard_Huffman/src/main/resources/com/example/standard_huffman/compressed.txt";

            // Save the compressed string to a binary file
            HuffmanFileHandler.compressToFile(inputFileName,compressedFileName);

            System.out.println("Compression complete. Compressed file saved to: " + compressedFileName);


        });

        Button decompressButton = new Button("Decompress");
//        scaleButtonAndLabel(decompressButton);
        decompressButton.setStyle(
                "-fx-margin: 0 35 0 0;" +
                        "-fx-min-width: 100px;" +
                        "-fx-min-height: 40px;"
        );
        decompressButton.setOnAction(event -> {
            String compressedFileName = fileLabel2.getText();
            String decompressedFileName = "/run/media/phantom/New Volume/University/OS/Assignments/Router_Simulator/Standard_Huffman/src/main/resources/com/example/standard_huffman/decompressed.txt";

//            BitSet compressedBitSet = null;
//            try {
//                compressedBitSet = HuffmanFileHandler.readBitSetFromFile(compressedFileName);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            //String compressedString = HuffmanFileHandler.bitSetToString(compressedBitSet);

            DeCompressor deCompressor = new DeCompressor();
            //String decompressedString = deCompressor.decompress(compressedString);

            // Output the decompressed string to the console
            //System.out.println("Decompressed String:");
            //System.out.println(decompressedString);
            // Perform decompression and save the result to a text file
            HuffmanFileHandler.decompressToFile(compressedFileName, decompressedFileName);

            System.out.println("Decompression complete. Decompressed file saved to: " + decompressedFileName);


        });

        buttonsContainer.getChildren().addAll(compressButton, decompressButton);

        secondScene.getChildren().addAll(browseButton, fileLabel, buttonsContainer);

        // Create the scenes
        Scene scene1 = new Scene(firstScene, 1200, 675);
        Scene scene2 = new Scene(secondScene, 1200, 675);

        // Transition to the second scene after a delay of 3500 ms
        PauseTransition delay = new PauseTransition(Duration.millis(3500));
        delay.setOnFinished(e -> {
            primaryStage.setFullScreen(false); // Return to normal screen
            primaryStage.setScene(scene2);
            primaryStage.setResizable(false);
        });
        delay.play();

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private void scaleButtonAndLabel(Button button) {
        Scale scale = new Scale(1.5, 1.5);
        button.getTransforms().add(scale);
        Label label = (Label) button.getGraphic();
        if (label != null) {
            label.getTransforms().add(scale);
        }
    }
}
