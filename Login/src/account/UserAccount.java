package account;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UserAccount {
	private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{12,40})";
	public String name;
	public String surname;
	public String login;
	private String password;
	private String accountType;
	protected String salary;

	public String getPassword() {
		return password;
	}

	public void setPassword(String textPwd) {
		this.password = textPwd;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public boolean verification() {
		boolean error = false;
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher;
		matcher = pattern.matcher(password);

		if (Pattern.matches("[a-z]*", name.substring(1, name.length())) == false
				|| Pattern.matches("[A-Z]", name.substring(0, 1)) == false || name.equalsIgnoreCase("")) {
			JFrame frame = new JFrame("error");
			JOptionPane.showMessageDialog(frame, "erreur, prénom invalide");
			error = true;
		}
		if (Pattern.matches("[a-z]*", surname.substring(1, surname.length())) == false
				|| Pattern.matches("[A-Z]", surname.substring(0, 1)) == false || surname.equalsIgnoreCase("")) {
			JFrame frame = new JFrame("error");
			JOptionPane.showMessageDialog(frame, "erreur,  nom invalide");
			error = true;
		}
		if (Pattern.matches("^[a-z0-9-.]+@[a-z0-9]+\\.[a-z0-9-.]+$", login) == false || login.equalsIgnoreCase("")) {
			error = true;
			JFrame frame = new JFrame("error");
			JOptionPane.showMessageDialog(frame, "erreur,login invalide");
		}
		if ((matcher.matches()) == false || password.equalsIgnoreCase("")) {
			error = true;
			JFrame frame = new JFrame("error");
			JOptionPane.showMessageDialog(frame, "erreur mot de passe invalide");
		}
		if (Pattern.matches("\\d+(\\.\\d{1,2})?", salary) == false || salary.equalsIgnoreCase("")) {
			JFrame frame = new JFrame("error");
			JOptionPane.showMessageDialog(frame, "erreur, salaire invalide");
			error = true;
		}
		return error;
	}

	public String hashPassword(String chaine) {
		try {
			byte[] donneeOctet = chaine.getBytes();
			MessageDigest monHash = MessageDigest.getInstance("SHA");
			monHash.update(donneeOctet);
			byte[] condenser = monHash.digest();
			chaine = new BigInteger(condenser).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaine;
	}

	public void DatabaseConnexion(String login, String password, String type, JFrame frame) {
		if (type == "login") {
			try {
				Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/login", "root",
						"");

				PreparedStatement st = (PreparedStatement) connection
						.prepareStatement("Select * from login where login=? and password=?");

				st.setString(1, login);
				st.setString(2, getPassword());

				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					setAccountType(rs.getString("type"));
					frame.setVisible(false);
					Connected.main(login);
				} else {
					JOptionPane.showMessageDialog(frame, "erreur, login ou mot de passe invalide");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "erreur sql");
			}
		} else {
			try {
				Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/login", "root",
						"");

				PreparedStatement st = (PreparedStatement) connection
						.prepareStatement("Select * from login where login=?");

				st.setString(1, login);

				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					setAccountType(rs.getString("type"));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "erreur sql");
			}
		}
	}

}

class AdminAccount extends UserAccount {
	private Key masterKey;

	public Key getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(Key masterKey) {
		this.masterKey = masterKey;
	}

	public void decryptKey() throws IOException {
		String chaine = new String(Files.readAllBytes(Paths.get("key.cryp")));
		byte[] decodedKey = Base64.getDecoder().decode(chaine);
		setMasterKey(new SecretKeySpec(decodedKey, 0, decodedKey.length, "blowfish"));
	}

	/**
	 * Méthode qui peut Chiffrer n'importe qyu'elle donnée et le met en format
	 * octets son appel dans une autre classe se fait par ApiBlowfish.generateKey()
	 *
	 * @param textClair: tout type de donnés pouvant être codé em obctets
	 * @param clef:      la clé de chifremment à utiliser
	 * @return : retourne le cryptage sous forme d'octets
	 * @throws Exception
	 */
	public static byte[] encryptInByte(byte[] textClair, Key clef) throws Exception {

		Cipher chiffre = Cipher.getInstance("Blowfish");

		chiffre.init(Cipher.ENCRYPT_MODE, clef);

		return chiffre.doFinal(textClair); // retourne au format tableau d'octets
	}

	/**
	 * Méthode qui déchiffre les donnée déjà chiffrées son appel dans une autre
	 * classe se fait par ApiBlowfish.decryptInByte(..)
	 *
	 * @param textChiffre: les octets à déchiffrer
	 * @param clef         doit être la même clé utilisée pour chiffrer
	 * @return
	 * @throws Exception
	 */

	public static byte[] decryptInByte(byte[] textChiffre, Key clef) throws Exception {

		Cipher dechiffre = Cipher.getInstance("Blowfish");

		dechiffre.init(Cipher.DECRYPT_MODE, clef);

		byte[] textDechiffre = dechiffre.doFinal(textChiffre);

		return textDechiffre;// retourne au format octet
	}

	/**
	 * Méthode qui peut Chiffrer uniquement une chaîne de caractères son appel dans
	 * une autre classe se fait par ApiBlowfish.encryptInString(..)
	 *
	 * @param textClair
	 * @param clef
	 * @return sous un format encodé affichable dans la console
	 * @throws Exception
	 */

	public static String encryptInString(String textClair, Key clef) throws Exception {

		byte[] chiffre = textClair.getBytes();

		chiffre = encryptInByte(chiffre, clef);

		return Base64.getEncoder().encodeToString(chiffre);

	}

	/**
	 * Méthode qui peut Chiffrer uniquement une chaîne de caractères en utiliant la
	 * même clé que pour chiffrer on l'appel par ApiBlowfish.decryptInString(..)
	 *
	 * @param textChiffre
	 * @param clef        : doit être crée préalablement
	 * @return la chaine chiffrée
	 * @throws Exception
	 */

	public static String decryptInString(String textChiffre, Key clef) throws Exception {

		// doit décoder la chaine en base64

		byte[] dechiffre = Base64.getDecoder().decode(textChiffre);

		dechiffre = decryptInByte(dechiffre, clef);

		return new String(dechiffre); // retourne au format chaine normal
	}

	public void addUser(String accountLogin, JFrame frame) throws Exception {
		if (!verification()) {
			decryptKey();
			Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/login", "root",
					"");

			PreparedStatement st = (PreparedStatement) connection.prepareStatement(
					"INSERT INTO `login`(`name`, `surname`, `login`, `password`, `salary`, `type`) VALUES (" + "'"
							+ name + "','" + surname + "','" + login + "','" + hashPassword(getPassword()) + "','"
							+ encryptInString(salary, getMasterKey()) + "','" + getAccountType() + "')");
			st.executeUpdate();
			JOptionPane.showMessageDialog(frame, "utilisateur ajouté");
			frame.setVisible(false);
			Connected.main(accountLogin);
		}

	}
}
