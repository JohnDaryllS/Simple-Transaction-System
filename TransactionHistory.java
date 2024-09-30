import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionHistory extends JFrame {
    /**
	 * 
	 */
	private static HashMap<String,String> logininfo;
	private static final long serialVersionUID = 1L;
	private JList<String> transactionList;
    private DefaultListModel<String> listModel;
    private JButton backButton;
    private static String userID;
    private static double depositedAmount;
    private static ArrayList<Transaction> transactions = new ArrayList<>();


    public TransactionHistory(String userID, double depositedAmount) {
    	TransactionHistory.userID = userID;
        setTitle("Transaction History");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0x21325432));

        listModel = new DefaultListModel<>();
        transactionList = new JList<>(listModel);
        transactionList.setBackground(Color.WHITE);
        transactionList.setSize(700, 200);
        JScrollPane scrollPane = new JScrollPane(transactionList);
        add(scrollPane, BorderLayout.CENTER);

        updateTransactionList();
        setLayout(new FlowLayout());

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(80, 20));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0x21325432));
        add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomePage welcomePage = new WelcomePage(userID, logininfo, depositedAmount);
                welcomePage.setVisible(true);
                
            }
        });
    }

    private void updateTransactionList() {
        listModel.clear();
        for (Transaction transaction : transactions) {
            listModel.addElement(transaction.toString());
        }
    }

    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TransactionHistory transactionHistory = new TransactionHistory(userID, depositedAmount);
            transactionHistory.setVisible(true);
        });
    }
}
