import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class SendMoney extends JFrame {
    /**
	 * 
	 */
	private static HashMap<String,String> logininfo = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private JTextField recipientUserIdField;
    private JTextField amountField;
    private JButton sendButton;
    private JButton backButton;
	protected double newSenderBalance;
    private static String userID;
    private static double depositedAmount;
    
    public SendMoney(String userID, HashMap<String, String> logininfo, double depositedAmount, WelcomePage welcomePage) {
    	SendMoney.userID = userID;
    	SendMoney.logininfo = logininfo;
        SendMoney.depositedAmount = depositedAmount;
        setTitle("Send Money");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(4, 1));
        getContentPane().setBackground(new Color(0x21325432));

        JLabel bigTextLabel = new JLabel("Send Money", SwingConstants.CENTER);
		bigTextLabel.setBounds(50, 20, 300, 50);
		bigTextLabel.setFont(new Font("Arial", Font.BOLD, 27));
		bigTextLabel.setForeground(Color.ORANGE);
		add(bigTextLabel);

        JPanel recipientPanel = new JPanel();
        recipientPanel.setBackground(new Color(0x21325432));
        recipientPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 10, 0));
        recipientPanel.add(new JLabel("Enter the recipient's user ID:"));
        recipientUserIdField = new JTextField(15);
        recipientPanel.add(recipientUserIdField);
        add(recipientPanel);

        JPanel amountPanel = new JPanel();
        amountPanel.setBackground(new Color(0x21325432));
        amountPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        amountPanel.add(new JLabel("Enter the amount to transfer:"));
        amountField = new JTextField(15);
        amountPanel.add(amountField);
        add(amountPanel);

        sendButton = new JButton("Send");
        sendButton.setBackground(Color.GREEN);
        sendButton.setForeground(Color.WHITE);
        sendButton.setPreferredSize(new Dimension(400, 60));
        add(sendButton);
        
        setLayout(new FlowLayout());
        
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 20));
        add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomePage welcomePage = new WelcomePage(userID, logininfo, depositedAmount);
                welcomePage.setVisible(true);
            }
        });
        
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                String recipientUserID = recipientUserIdField.getText();
                String amountString = amountField.getText();
                double amount = Double.parseDouble(amountString);
                if (verifyRecipientAndProcessTransaction(recipientUserID, amount)) {
                    double newSenderBalance = depositedAmount - amount;
                    double recipientBalance = Double.parseDouble(logininfo.getOrDefault(recipientUserID, "0"));
                    recipientBalance += amount;
                    logininfo.put(userID, String.valueOf(newSenderBalance));
                    logininfo.put(recipientUserID, String.valueOf(recipientBalance));
                    WelcomePage updatedWelcomePage = new WelcomePage(userID, logininfo, newSenderBalance);
                    updatedWelcomePage.setVisible(true);
                    Transaction transaction = new Transaction(userID, recipientUserID, "Send Money", amount);
                    TransactionHistory.addTransaction(transaction);
                    JOptionPane.showMessageDialog(null, "Money sent successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Recipient user ID not found or insufficient balance.");
                }
            }
        });
    }

	private boolean verifyRecipientAndProcessTransaction(String recipientUserID, double amount) {
		if (logininfo.containsKey(recipientUserID)) {
	        double senderBalance = Double.parseDouble(logininfo.getOrDefault(userID, "0"));
	        if (amount > 0 && amount <= senderBalance) {
	            depositedAmount -= amount;
	            return true;
	        }
	    }
	    return false;
	}
    

    public static void main(String[] args) {
    	WelcomePage welcomePage = new WelcomePage(userID, logininfo, depositedAmount);
        SwingUtilities.invokeLater(() -> {
            SendMoney sendMoney = new SendMoney(userID, logininfo, depositedAmount, welcomePage);
            sendMoney.setVisible(true);
        });
    }
}