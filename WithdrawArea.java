import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class WithdrawArea extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField withdrawAmountField;
    private JPasswordField pinField;
    private JButton confirmButton;
    private JLabel updatedBalanceLabel;
    private JButton backButton;
    private static String userID;
    private static HashMap<String, String> logininfo;
    private static double depositedAmount;

    public WithdrawArea(String userID, HashMap<String, String> logininfo, double depositedAmount) {
        WithdrawArea.userID = userID;
        WithdrawArea.logininfo = logininfo;
        WithdrawArea.depositedAmount = depositedAmount;

        setTitle("Withdraw");
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        getContentPane().setBackground(new Color(0x21325432));

        JLabel bigTextLabel = new JLabel("Withdraw Money", SwingConstants.CENTER);
        bigTextLabel.setBounds(50, 20, 300, 50);
        bigTextLabel.setFont(new Font("Arial", Font.BOLD, 27));
        bigTextLabel.setForeground(Color.YELLOW);
        add(bigTextLabel);

        JPanel withdrawPanel = new JPanel();
        withdrawPanel.setBackground(new Color(0x21325432));
        withdrawPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        withdrawPanel.add(new JLabel("Enter the amount to Withdraw:"));
        withdrawAmountField = new JTextField(15);
        withdrawPanel.add(withdrawAmountField);
        add(withdrawPanel);

        JPanel pinPanel = new JPanel();
        pinPanel.setBackground(new Color(0x21325432));
        pinPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        pinPanel.add(new JLabel("Enter PIN to Confirm:"));
        pinField = new JPasswordField(15);
        pinPanel.add(pinField);
        add(pinPanel);

        confirmButton = new JButton("Confirm");
        confirmButton.setBackground(Color.GREEN);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setPreferredSize(new Dimension(400, 60));
        add(confirmButton);

        setLayout(new FlowLayout());

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 20));
        add(backButton);

        updatedBalanceLabel = new JLabel("Updated balance: ", SwingConstants.LEFT);
        updatedBalanceLabel.setForeground(Color.WHITE);
        add(updatedBalanceLabel);

        updatedBalanceLabel.setText("Updated balance: ₱" + depositedAmount);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomePage welcomePage = new WelcomePage(userID, logininfo, depositedAmount);
                welcomePage.setVisible(true);
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = withdrawAmountField.getText();
                String pin = new String(pinField.getPassword());
                if (logininfo.containsKey(userID)) {
                    String correctPassword = logininfo.get(userID);
                    if (pin.equals(correctPassword)) {
                        try {
                            double amount = Double.parseDouble(amountString);
                            if (amount <= depositedAmount) {
                                updateBalance(amount);
                                Transaction withdrawTransaction = new Transaction(userID, "Withdraw", amount);
                                TransactionHistory.addTransaction(withdrawTransaction);
                                WelcomePage welcomePage = new WelcomePage(userID, logininfo, depositedAmount - amount);
                                welcomePage.setVisible(true);
                                JOptionPane.showMessageDialog(null, "Withdrawal successful.");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Insufficient balance.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect PIN. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.");
                }
            }
        });
    }

    private void updateBalance(double amount) {
        depositedAmount -= amount;
        logininfo.put(userID, String.valueOf(depositedAmount));
        updatedBalanceLabel.setText("Updated balance: ₱" + depositedAmount);
        withdrawAmountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WithdrawArea withdrawArea = new WithdrawArea(userID, logininfo, depositedAmount);
            withdrawArea.setVisible(true);
        });
    }
}
