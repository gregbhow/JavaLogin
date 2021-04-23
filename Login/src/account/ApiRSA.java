package account;

/*******************************************************************************************
 * La classe ApiRSA définit toutes les API pour signer un texte claire (chaîne de caractère) 
 * avec une paire de clés RSA (PK, SK) de 1024 bits et SHA256
 * @author Didier Samfat
 * @version 2.0
 * Date: 27/03/2021
 *******************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class ApiRSA {

	private final static Charset UTF8 = Charset.forName("UTF-8"); // encodage caractère

	/**
	 * Génére une paire de clés (PK, SK) de 1024 bits pour l'algorithme RSA son
	 * appel dans une autre classe se fait par ApiRSA.generateKeyPair()
	 * 
	 * @return : la paire de clé publique et privé
	 * @throws Exception: si tamtam
	 */

	public static KeyPair generateKeyPair() throws Exception {

		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

		generator.initialize(1024, new SecureRandom());

		KeyPair rsaKey = generator.generateKeyPair(); // la pair de clé (PK, SK) est généree

		return rsaKey;
	}

	/**
	 * Cette méthode effectue un hash sur une chaineLogin et la signe avec une clé privée
	 * SK son appel dans une autre classe se fait par ApiRSA.sign(..)
	 * 
	 * @param texteClair : le texte à signer
	 * @param priveSK    : la clé privée SK qui doit au préalable être généré
	 * @return : retourne la signature sous forme de chaineLogin
	 * @throws Exception: si tamtam
	 */

	public static String sign(String texteClair, PrivateKey priveSK) throws Exception {

		// utilise le hash SHA256 et signe en même temps
		Signature privateSignature = Signature.getInstance("SHA256withRSA");

		privateSignature.initSign(priveSK); // initialise la clé privée SK

		privateSignature.update(texteClair.getBytes(UTF8));

		byte[] signature = privateSignature.sign(); // récupère la signature sous forme d'octets

		return Base64.getEncoder().encodeToString(signature); // retourne la signature au format chaineLogin
	}

	/**
	 * Cette méthode vérifie qu'un text clair n'a pas été modifié après avoir été
	 * signé son appel dans une autre classe se fait par ApiRSA.verify(..)
	 * 
	 * @param texteClair: le texte à vérifier
	 * @param signature   : la signature du tecte calculé précédemment
	 * @param publicPK    : la clé publique PK de l'auteur
	 * @return : retourn vrai si c'est bon
	 * @throws Exception: si tamtam
	 */

	public static boolean verify(String texteClair, String signature, PublicKey publicPK) throws Exception {

		Signature publicSignature = Signature.getInstance("SHA256withRSA"); // re-hash le text clair

		publicSignature.initVerify(publicPK);

		publicSignature.update(texteClair.getBytes(UTF8)); // re-signe pour obtenir la signature candidate

		byte[] signatureOctets = Base64.getDecoder().decode(signature); // encode en octet la signature candidate

		return publicSignature.verify(signatureOctets); // vérifie les 2 signatures et retourne vrai si ok
	}
	
	public static String readFichier(String chaineLogin, String filePathLogin) throws IOException {
		try {
			chaineLogin = new String(Files.readAllBytes(Paths.get(filePathLogin)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return chaineLogin;
	}
	
	/**
	 * 
	 */

	public static void main(String Args[]) throws Exception {
		Scanner scan = new Scanner(System.in);
		String chaineLogin = "";
		String chaineConnected = "";
		String chaineUserAccount = "";
		String choix = "N";
		String filePathLogin = "src/account/Login.java";
		String filePathConnected = "src/account/Connected.java";
		String filePathUserAccount = "src/account/UserAccount.java";
		KeyPair cle;
		String signatureLogin = "";
		String signatureConnected = "";
		String signatureUserAccount = "";
		chaineLogin = readFichier(chaineLogin,filePathLogin);
		chaineConnected = readFichier(chaineConnected,filePathConnected);
		chaineUserAccount = readFichier(chaineUserAccount,filePathUserAccount);
		System.out.println("***** lancement du programme *****");
		cle = generateKeyPair();
		System.out.println("Clé generé...signature du fichier en cours...");
		signatureLogin = sign(chaineLogin,cle.getPrivate());
		signatureConnected = sign(chaineConnected,cle.getPrivate());
		signatureUserAccount = sign(chaineUserAccount,cle.getPrivate());
		System.out.println("Fichiers Signés...Place à la verification...");
		System.out.println("Souhaitez-vous verifier vos fichiers ? Y/N");
		choix = scan.next();
		switch (choix) {
		case "Y":
			chaineLogin = readFichier(chaineLogin,filePathLogin);
			if (verify(chaineLogin,signatureLogin,cle.getPublic())) {
				System.out.println("Le fichier Login est valide");
			}else {
				System.out.println("Le fichier Login est corrompu !!! Faites attention !!!");
			}
			chaineConnected = readFichier(chaineConnected,filePathConnected);
			if (verify(chaineConnected,signatureConnected,cle.getPublic())) {
				System.out.println("Le fichier Connected est valide");
			}else {
				System.out.println("Le fichier Connected est corrompu !!! Faites attention !!!");
			}
			chaineUserAccount = readFichier(chaineUserAccount,filePathUserAccount);
			if (verify(chaineUserAccount,signatureUserAccount,cle.getPublic())) {
				System.out.println("Le fichier UserAccount est valide");
			}else {
				System.out.println("Le fichier UserAccount est corrompu !!! Faites attention !!!");
			}
			System.exit(1);
		case "N":
			System.out.println("Dommage...Au revoir.");
			scan.close();
			System.exit(1);
		default:
			System.out.println("Dommage...Vous n'avez pas respecté les choix proposés...Au revoir.");

			System.exit(1);
		}			
	}

}
