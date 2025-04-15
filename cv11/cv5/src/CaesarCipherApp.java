import javax.swing.*;
import java.awt.*;

public class CaesarCipherApp extends JFrame {
    private final JTextArea plainTextArea;
    private final JTextArea cipherTextArea;
    private final JLabel shiftLabel;
    private int shift = 3;

    public CaesarCipherApp() {
        setTitle("Caesarova šifra");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Soubor");
        JMenuItem exitItem = new JMenuItem("Ukončit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu actionMenu = new JMenu("Akce");
        JMenuItem encryptItem = new JMenuItem("Zašifrovat");
        encryptItem.addActionListener(e -> encrypt());
        JMenuItem decryptItem = new JMenuItem("Dešifrovat");
        decryptItem.addActionListener(e -> decrypt());
        actionMenu.add(encryptItem);
        actionMenu.add(decryptItem);


        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        setJMenuBar(menuBar);

        JPanel textPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textPanel.add(new JLabel("Nezašifrovaný text:"));
        textPanel.add(new JLabel("Zašifrovaný text:"));

        plainTextArea = new JTextArea(5, 20);
        cipherTextArea = new JTextArea(5, 20);
        textPanel.add(new JScrollPane(plainTextArea));
        textPanel.add(new JScrollPane(cipherTextArea));

        add(textPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        shiftLabel = new JLabel("Posun: " + shift);

        SpinnerNumberModel model = new SpinnerNumberModel(3, 0, 25, 1);

        JSpinner shiftSpinner = new JSpinner(model);
        shiftSpinner.addChangeListener(e -> {
            shift = (int) shiftSpinner.getValue();
            System.out.println("Shift: " + shift);
            shiftLabel.setText("Posun: " + shift);
        });
        JButton changeShiftButton = new JButton("Změnit posun");
        changeShiftButton.addActionListener(e -> {
            changeShift();
            shiftSpinner.setValue(shift);
        });

        bottomPanel.add(shiftSpinner);

        JButton encryptButton = new JButton("Zašifrovat");
        encryptButton.addActionListener(e -> encrypt());

        JButton decryptButton = new JButton("Dešifrovat");
        decryptButton.addActionListener(e -> decrypt());

        bottomPanel.add(shiftLabel);
        bottomPanel.add(changeShiftButton);
        bottomPanel.add(encryptButton);
        bottomPanel.add(decryptButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CaesarCipherApp().setVisible(true));
    }

    private void changeShift() {
        String input = JOptionPane.showInputDialog(this, "Zadejte nový posun (1-25):", shift);
        if (input != null) {
            try {
                int newShift = Integer.parseInt(input);
                if (newShift < 1 || newShift > 25) throw new NumberFormatException();
                shift = newShift;
                shiftLabel.setText("Posun: " + shift);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Neplatný vstup. Zadejte číslo mezi 1 a 25.", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void encrypt() {
        String text = plainTextArea.getText().toUpperCase();
        if (!text.matches("[A-Z .]*")) {
            showError();
            return;
        }
        cipherTextArea.setText(caesarShift(text, shift));
    }

    private void decrypt() {
        String text = cipherTextArea.getText().toUpperCase();
        if (!text.matches("[A-Z .]*")) {
            showError();
            return;
        }
        plainTextArea.setText(caesarShift(text, 26 - shift));
    }

    private void showError() {
        JOptionPane.showMessageDialog(this, "Text smí obsahovat jen velká písmena A-Z, mezery a tečky.", "Chybný vstup", JOptionPane.ERROR_MESSAGE);
    }

    private String caesarShift(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ((c - 'A' + shift) % 26 + 'A'));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
