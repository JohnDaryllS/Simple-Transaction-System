import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DepositArea extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField depositAmountField;
    private JPasswordField pinField;
    private JButton confirmButton;
    private JLabel updatedBalanceLabel;
    private JButton backButton;
    private static String userID;
    private static HashMap<String,String> logininfo;
    private static double lastDepositAmount = 0; 

    public DepositArea(String userID, HashMap<String, String> logininfo) {
    	DepositArea.userID = userID;
    	DepositArea.logininfo = logininfo;
        setTitle("Deposit");
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        getContentPane().setBackground(new Color(0x21325432));

        JLabel bigTextLabel = new JLabel("DEPOSIT MONEY", SwingConstants.CENTER);
		bigTextLabel.setBounds(50, 20, 300, 50);
		bigTextLabel.setFont(new Font("Arial", Font.BOLD, 27));
		bigTextLabel.setForeground(Color.MAGENTA);
		add(bigTextLabel);

        JPanel depositPanel = new JPanel();
        depositPanel.setBackground(new Color(0x21325432));
        depositPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        depositPanel.add(new JLabel("Enter the amount to Deposit:"));
        depositAmountField = new JTextField(15);
        depositPanel.add(depositAmountField);
        add(depositPanel);

        JPanel pinPanel = new JPanel();
        pinPanel.setBackground(new Color(0x21325432));
        pinPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        pinPanel.add(new JLabel("Enter PASSWORD to Confirm:"));
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
        
        updatedBalanceLabel.setText("Updated balance: ₱" + lastDepositAmount);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomePage welcomePage = new WelcomePage(userID, logininfo, lastDepositAmount);
                welcomePage.setVisible(true);
            }
        });
        
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = depositAmountField.getText();
                String pin = new String(pinField.getPassword());
                if (logininfo.containsKey(userID)) {
                    String correctPassword = logininfo.get(userID);
                    if (pin.equals(correctPassword)) {
                        try {
                            double amount = Double.parseDouble(amountString);
                            updateBalance(amount);
                            Transaction depositTransaction = new Transaction(userID, "Deposit",amount);
                            TransactionHistory.addTransaction(depositTransaction);
                            WelcomePage welcomePage = new WelcomePage(userID, logininfo, amount);
                            welcomePage.setVisible(true);
                            dispose();
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
    	lastDepositAmount += amount;
        String updatedBalance = String.valueOf(lastDepositAmount);
        logininfo.put(userID, updatedBalance);
        updatedBalanceLabel.setText("Updated balance: ₱" + updatedBalance);
        depositAmountField.setText("");
    }
    
    public void setLastDepositAmount(double amount) {
        lastDepositAmount = amount;
    }

    public static double getLastDepositAmount() {
        return lastDepositAmount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DepositArea depositArea = new DepositArea(userID, logininfo);
            depositArea.setVisible(true);
            depositArea.setLastDepositAmount(Double.parseDouble(logininfo.get(userID)));
        });
    }
}
