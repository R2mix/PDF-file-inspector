package hellofx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class App extends Application {
    PDFInscpector pdf;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PDF file inspector");

        // appel de la classe pdf inspector

        // Création du texte et du bouton
        Label label = new Label("Cliquez sur le bouton pour ouvrir un fichier");
        Button button = new Button("Ouvrir un fichier");
        Label label2 = new Label("");
        label2.setText("Aucun fichier sélectionné.");
        // Définition de l'action lors du clic sur le bouton
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Création de la boîte de dialogue de sélection de fichier
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner un fichier");

                // Affichage de la boîte de dialogue et récupération du fichier sélectionné
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                // Vérification si un fichier a été sélectionné
                if (selectedFile != null) {
                    label.setText("Fichier sélectionné : " + selectedFile.getAbsolutePath());
                    pdf = new PDFInscpector(selectedFile.getAbsolutePath());
                    if (pdf.numberOfJavascriptObject > 0) {

                        label2.setText("Le pdf contient des scripts, plus d'info dans le fichier log.txt");
                        label2.setTextFill(Color.rgb(255, 0, 0));
                    } else {
                        label2.setText("Le pdf ne contient aucun scripts");
                        label2.setTextFill(Color.rgb(0, 255, 0));
                    }
                } else {
                    label.setText("Aucun fichier sélectionné.");
                }
            }
        });

        // Création de la mise en page et ajout des composants
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10);
        vbox.getChildren().addAll(label, button, label2);
        vbox.setPadding(new javafx.geometry.Insets(10));

        // Affichage de la scène
        Scene scene = new Scene(vbox, 350, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
