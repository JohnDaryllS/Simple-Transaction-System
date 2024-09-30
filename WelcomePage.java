import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class WelcomePage extends JFrame{
	private static HashMap<String,String> logininfo;
	private static final long serialVersionUID = 1L;
	private JButton sendMoneyButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transactionHistoryButton;
    private JButton exitButton;
    private JLabel userIdLabel;
    private JLabel depositedLabel;
    private static double depositedAmount;
    
    public WelcomePage(String userID, HashMap<String,String> logininfo, double depositedAmount) {
		WelcomePage.logininfo = logininfo;
		WelcomePage.depositedAmount = depositedAmount;
	    setTitle("Select Transaction Method");
	    setSize(500, 400);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setLayout(new GridLayout(4, 1));
	    getContentPane().setBackground(new Color(0x21325432));

	    JLabel titleLabel = new JLabel("Select:", SwingConstants.CENTER);
	    titleLabel.setForeground(Color.white);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
	    add(titleLabel);

	    sendMoneyButton = createButton("Send Money");
	    depositButton = createButton("Deposit");
	    withdrawButton = createButton("Withdraw");
	    transactionHistoryButton = createButton("Transaction History");
	    exitButton = createButton("Logout");

	    userIdLabel = new JLabel("User ID: " + userID, SwingConstants.LEFT);
	    userIdLabel.setForeground(Color.white);
	    add(userIdLabel);
	    
	    depositedLabel = new JLabel("Balance: ₱" + depositedAmount, SwingConstants.LEFT);
        depositedLabel.setForeground(Color.white);
	    add(depositedLabel);

	    sendMoneyButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	            SendMoney sendMoneyWindow = new SendMoney(userID, logininfo, depositedAmount, WelcomePage.this);
	            sendMoneyWindow.setVisible(true);
	        }
	    });

	    depositButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	            DepositArea depositArea = new DepositArea(userID, logininfo);
	            depositArea.setVisible(true);
	        }
	    });

	    withdrawButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	            WithdrawArea withdrawArea = new WithdrawArea(userID, logininfo, depositedAmount);
	            withdrawArea.setVisible(true);
	        }
	    });

	    transactionHistoryButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	dispose();
	            TransactionHistory transactionHistory = new TransactionHistory(userID, depositedAmount);
	            transactionHistory.setVisible(true);
	        }
	    });

	    exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            dispose();
	            LoginPage loginPage = new LoginPage(logininfo);
	            loginPage.frame.setVisible(true);
	        }
	    });
	}

	private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        add(button);
        return button;
    }
	
	public void updateBalance(double newBalance) {
        depositedAmount = newBalance;
        depositedLabel.setText("Balance: ₱" + depositedAmount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage("dummyUserID", logininfo, depositedAmount);
            welcomePage.setVisible(true);
        });
	}
}