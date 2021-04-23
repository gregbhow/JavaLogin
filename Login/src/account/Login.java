package account;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * class Login
 * 
 * @author gregb
 *
 */
public class Login {

	private JFrame frame;
	private JTextField textLogin;
	private JPasswordField textPwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * key listener of enter key on the login/password input | button that will send
	 * the users inputs into AdminAccount method database connection to connect to
	 * user to the application login input inserted in AdminAccount class variable
	 * login password input converted into SHA hash and inserted in AdminAccount
	 * Class method setPassword()
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 673, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTitle = new JLabel("Login");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(201, 10, 249, 64);
		frame.getContentPane().add(lblTitle);

		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(46, 132, 75, 32);
		frame.getContentPane().add(lblLogin);

		JLabel lblPwd = new JLabel("PWD");
		lblPwd.setHorizontalAlignment(SwingConstants.CENTER);
		lblPwd.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPwd.setBounds(46, 218, 75, 32);
		frame.getContentPane().add(lblPwd);
		textLogin = new JTextField();
		textLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					AdminAccount login = new AdminAccount();
					login.login = textLogin.getText();
					login.setPassword((String.copyValueOf(textPwd.getPassword())));
					login.setPassword(login.hashPassword(login.getPassword()));
					try {
						login.DatabaseConnexion(login.login, login.getPassword(), "login", Login.this.frame);
					} catch (Exception sqlException) {
						sqlException.printStackTrace();
					}
				}
			}
		});
		textLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textLogin.setBounds(168, 132, 442, 32);
		frame.getContentPane().add(textLogin);
		textLogin.setColumns(10);

		JButton btnConnexion = new JButton("connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAccount login = new AdminAccount();
				login.login = textLogin.getText();
				login.setPassword((String.copyValueOf(textPwd.getPassword())));
				login.setPassword(login.hashPassword(login.getPassword()));
				try {
					login.DatabaseConnexion(login.login, login.getPassword(), "login", Login.this.frame);
				} catch (Exception sqlException) {
					sqlException.printStackTrace();
				}
			}
		});
		btnConnexion.setBounds(272, 319, 119, 21);
		frame.getContentPane().add(btnConnexion);
		textPwd = new JPasswordField();

		textPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					AdminAccount login = new AdminAccount();
					login.login = textLogin.getText();
					login.setPassword((String.copyValueOf(textPwd.getPassword())));
					login.setPassword(login.hashPassword(login.getPassword()));
					try {
						login.DatabaseConnexion(login.login, login.getPassword(), "login", Login.this.frame);
					} catch (Exception sqlException) {
						sqlException.printStackTrace();
					}
				}
			}
		});
		textPwd.setBounds(168, 219, 442, 32);
		frame.getContentPane().add(textPwd);
	}
}
