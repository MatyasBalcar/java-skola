import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CaesarCipherApp extends Application {

    private int shift = 3;  // Výchozí posun
    private Label shiftLabel;
    private TextArea inputText;
    private TextArea outputText;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Caesarova šifra");

        // Textová pole
        inputText = new TextArea();
        outputText = new TextArea();
        inputText.setPromptText("Vložte nezašifrovaný text (A-Z, mezera, tečka)");
        outputText.setEditable(false);

        // Popisky
        Label inputLabel = new Label("Nezašifrovaný text:");
        Label outputLabel = new Label("Zašifrovaný text:");
        shiftLabel = new Label("Posun: " + shift);

        // Tlačítka
        Button encryptButton = new Button("Zašifrovat");
        Button decryptButton = new Button("Dešifrovat");
        Button changeShiftButton = new Button("Změnit posun");

        encryptButton.setOnAction(e -> encryptText());
        decryptButton.setOnAction(e -> decryptText());
        changeShiftButton.setOnAction(e -> changeShift());

        // Menu
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("Soubor");
        MenuItem exitItem = new MenuItem("Ukončit");
        exitItem.setOnAction(e -> primaryStage.close());

        Menu menuAction = new Menu("Akce");
        MenuItem encryptItem = new MenuItem("Zašifrovat");
        MenuItem decryptItem = new MenuItem("Dešifrovat");

        encryptItem.setOnAction(e -> encryptText());
        decryptItem.setOnAction(e -> decryptText());

        menuFile.getItems().add(exitItem);
        menuAction.getItems().addAll(encryptItem, decryptItem);
        menuBar.getMenus().addAll(menuFile, menuAction);

        // Rozložení
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10, encryptButton, decryptButton, changeShiftButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(menuBar, inputLabel, inputText, outputLabel, outputText, shiftLabel, buttonBox);

        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }

    private void encryptText() {
        String text = inputText.getText().toUpperCase();
        if (!isValidInput(text)) {
            showAlert("Chyba", "Neplatné znaky! Používejte pouze A-Z, mezery a tečky.");
            return;
        }
        outputText.setText(caesarCipher(text, shift));
    }

    private void decryptText() {
        String text = outputText.getText().toUpperCase();
        if (!isValidInput(text)) {
            showAlert("Chyba", "Neplatné znaky! Používejte pouze A-Z, mezery a tečky.");
            return;
        }
        inputText.setText(caesarCipher(text, -shift));
    }

    private void changeShift() {
        TextInputDialog dialog = new TextInputDialog(Integer.toString(shift));
        dialog.setTitle("Změna posunu");
        dialog.setHeaderText("Zadejte nový posun (1-25):");
        dialog.setContentText("Posun:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                int newShift = Integer.parseInt(input);
                if (newShift < 1 || newShift > 25) throw new NumberFormatException();
                shift = newShift;
                shiftLabel.setText("Posun: " + shift);
            } catch (NumberFormatException e) {
                showAlert("Chyba", "Zadejte platné číslo mezi 1 a 25.");
            }
        });
    }

    private boolean isValidInput(String text) {
        return text.matches("[A-Z .]*");
    }

    private String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                char shifted = (char) ('A' + (ch - 'A' + shift + 26) % 26);
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
