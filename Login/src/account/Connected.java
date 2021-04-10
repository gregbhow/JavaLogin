package account;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Connected {

	private JFrame frame;
	private JTextField textNom;
	private JTextField textPrenom;
	private JTextField textLogin;
	private JTextField textSalary;
	private JPasswordField textPwd;

	/**
	 * Launch the application.
	 */
	public static void main(String login) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connected window = new Connected(login);
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
	public Connected(String login) {
		initialize(login);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize(String login) {
		frame = new JFrame();
		frame.setBounds(100, 100, 649, 624);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHome = new JLabel("HOME");
		lblHome.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setBounds(202, 10, 221, 46);
		frame.getContentPane().add(lblHome);

		JLabel lblconnected = new JLabel("");
		lblconnected.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblconnected.setHorizontalAlignment(SwingConstants.CENTER);
		lblconnected.setBounds(184, 56, 261, 22);
		frame.getContentPane().add(lblconnected);
		AdminAccount account = new AdminAccount();
		account.Database(login, null, null, frame);
		lblconnected.setText("Bonjour," + account.name + " " + account.surname);

		JLabel lblsalary = new JLabel("Votre salaire est de " + account.salary);
		lblsalary.setHorizontalAlignment(SwingConstants.CENTER);
		lblsalary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblsalary.setBounds(184, 82, 261, 22);
		frame.getContentPane().add(lblsalary);

		if (account.getAccountType().contains("1")) {
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.setBounds(29, 115, 573, 445);
			frame.getContentPane().add(panel);
			panel.setLayout(null);

			JLabel lblNom = new JLabel("nom");
			lblNom.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNom.setHorizontalAlignment(SwingConstants.CENTER);
			lblNom.setBounds(10, 50, 113, 29);
			panel.add(lblNom);

			JLabel lblPrenom = new JLabel("prenom");
			lblPrenom.setHorizontalAlignment(SwingConstants.CENTER);
			lblPrenom.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblPrenom.setBounds(10, 100, 113, 29);
			panel.add(lblPrenom);

			JLabel lblLogin = new JLabel("login");
			lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
			lblLogin.setBounds(10, 150, 113, 29);
			panel.add(lblLogin);

			JLabel lblPwd = new JLabel("pwd");
			lblPwd.setHorizontalAlignment(SwingConstants.CENTER);
			lblPwd.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblPwd.setBounds(10, 200, 113, 29);
			panel.add(lblPwd);

			JLabel lblSalary = new JLabel("salary");
			lblSalary.setHorizontalAlignment(SwingConstants.CENTER);
			lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblSalary.setBounds(10, 250, 113, 29);
			panel.add(lblSalary);

			JLabel lblType = new JLabel("type");
			lblType.setHorizontalAlignment(SwingConstants.CENTER);
			lblType.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblType.setBounds(10, 300, 113, 29);
			panel.add(lblType);

			textNom = new JTextField();
			textNom.setBounds(129, 50, 390, 29);
			panel.add(textNom);
			textNom.setColumns(10);

			textPrenom = new JTextField();
			textPrenom.setColumns(10);
			textPrenom.setBounds(129, 100, 390, 29);
			panel.add(textPrenom);

			textLogin = new JTextField();
			textLogin.setColumns(10);
			textLogin.setBounds(129, 154, 390, 29);
			panel.add(textLogin);

			textSalary = new JTextField();
			textSalary.setColumns(10);
			textSalary.setBounds(129, 250, 390, 29);
			panel.add(textSalary);

			textPwd = new JPasswordField();
			textPwd.setBounds(133, 199, 386, 29);
			panel.add(textPwd);

			JComboBox textType = new JComboBox();
			textType.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textType.setBounds(129, 304, 390, 29);
			panel.add(textType);
			textType.addItem("user");
			textType.addItem("admin");

			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminAccount add = new AdminAccount();
					add.surname = textNom.getText();
					add.name = textPrenom.getText();
					add.login = textLogin.getText();
					add.setPassword(String.valueOf(textPwd.getPassword()));
					add.salary = textSalary.getText();
					if (textType.getSelectedItem().toString() == "user") {
						add.setAccountType("0");
					} else {
						add.setAccountType("1");
					}
					try {
						add.addUser(login, frame);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			btnAdd.setBounds(375, 412, 85, 21);
			panel.add(btnAdd);
			JButton btnLogout = new JButton("logout");
			btnLogout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(1);
				}
			});
			btnLogout.setBounds(144, 409, 99, 26);
			panel.add(btnLogout);
		}else {
			JButton btnLogOut = new JButton("log out");
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(1);
				}
			});
			btnLogOut.setBounds(265, 549, 99, 26);
			frame.getContentPane().add(btnLogOut);
		}
	}
}
