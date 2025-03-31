import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class NacciCalculator extends JFrame {
    private JComboBox<String> typeComboBox;
    private JTextField indexField;
    private JButton calculateButton;
    private JButton cancelButton;
    private JLabel statusLabel;
    private DefaultListModel<String> resultsModel;
    private JList<String> resultsList;
    private SwingWorker<Long, Void> worker;

    public NacciCalculator() {
        super("n-nacci kalkulačka");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        typeComboBox = new JComboBox<>(new String[]{
                "Fibonacci", "Tribonacci", "Tetranacci", "Pentanacci", "Hexanacci", "Octanacci"
        });

        indexField = new JTextField();
        calculateButton = new JButton("Spočítej");
        cancelButton = new JButton("Zruš");
        statusLabel = new JLabel("-");
        resultsModel = new DefaultListModel<>();
        resultsList = new JList<>(resultsModel);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Typ posloupnosti:"));
        inputPanel.add(typeComboBox);
        inputPanel.add(new JLabel("Index (n):"));
        inputPanel.add(indexField);
        inputPanel.add(new JLabel("Stav:"));
        inputPanel.add(statusLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultsList), BorderLayout.SOUTH);

        calculateButton.addActionListener(e -> startCalculation());
        cancelButton.addActionListener(e -> cancelCalculation());
    }

    private void startCalculation() {
        String type = Objects.requireNonNull(typeComboBox.getSelectedItem()).toString().toLowerCase();
        System.out.println(type);
        int order = switch (type) {
            case "fibonacci" -> 2;
            case "tribonacci" -> 3;
            case "tetranacci" -> 4;
            case "pentanacci" -> 5;
            case "hexanacci" -> 6;
            case "octanacci" -> 8;
            default -> {
                JOptionPane.showMessageDialog(this, "Neplatný typ posloupnosti.");
                yield -1;
            }
        };

        if (order == -1) return;

        int n;
        try {
            n = Integer.parseInt(indexField.getText().trim());
            if (n < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Zadej platné nezáporné číslo.");
            return;
        }

        statusLabel.setText("Probíhá…");
        calculateButton.setEnabled(false);
        cancelButton.setEnabled(true);

        worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                return computeNacci(order, n);
            }

            @Override
            protected void done() {
                try {
                    if (isCancelled()) {
                        statusLabel.setText("Zrušeno");
                    } else {
                        long result = get();
                        String label = String.format("%s(%d) = %d", capitalize(type), n, result);
                        resultsModel.addElement(label);
                        statusLabel.setText("Hotovo");
                    }
                } catch (InterruptedException | CancellationException ex) {
                    statusLabel.setText("Zrušeno");
                } catch (ExecutionException ex) {
                    statusLabel.setText("Chyba");
                    ex.printStackTrace();
                } finally {
                    calculateButton.setEnabled(true);
                    cancelButton.setEnabled(false);
                }
            }
        };

        worker.execute();
    }

    private void cancelCalculation() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    private long computeNacci(int order, int n) {
        if (n < order - 1) return 0;
        if (n == order - 1) return 1;

        long[] arr = new long[n + 1];
        for (int i = 0; i < order - 1; i++) arr[i] = 0;
        arr[order - 1] = 1;

        for (int i = order; i <= n; i++) {
            arr[i] = 0;
            for (int j = 1; j <= order; j++) {
                arr[i] += arr[i - j];
            }

            if (Thread.currentThread().isInterrupted()) {
                throw new CancellationException();
            }
        }
        return arr[n];
    }

    private String capitalize(String str) {
        if (str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NacciCalculator().setVisible(true));
    }
}
