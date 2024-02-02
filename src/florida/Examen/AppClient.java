package florida.Examen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AppClient {

	public static void main(String[] args) {
		Scanner teclat = new Scanner(System.in);
		try {
			Socket socketConnexio = new Socket("localhost", 5000);

			// Escriure
			OutputStream os = socketConnexio.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);

			// Llegir
			InputStream is = socketConnexio.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			System.out.println("Usuari: ");
			String usuari = teclat.nextLine();
			System.out.println("Contrasenya: ");
			String contrasenya = teclat.nextLine();

			System.err.println("CLIENT >>> " + "Envia usuari y contrasenya");
			pw.println(usuari);
			pw.println(contrasenya);

			System.err.println("CLIENT >>> " + "Rep resposta servidor");
			String respostaAutenticacio = br.readLine();

			if (Integer.parseInt(respostaAutenticacio) == 204) {
				System.err.println("CLIENT >>> Solicita contingut");

				pw.println("GET_CONTENT");

				String numeroLinies = br.readLine();
				System.out.println("Nuero de linies del archiu: " + numeroLinies);

				System.out.println("Contingut del archiu: ");

				while (true) {
					String linea = br.readLine();
					if (linea.equals("end"))
						break;

					System.out.println(linea);
				}

				System.out.println("==================================");
				System.out.print("Vols enviar una linea ? (si/no):");
				String novaLinea = teclat.next();

				if (!novaLinea.equalsIgnoreCase("si")) {
					System.err.println("CLIENT >>> Conexio finalitzada");
					pw.println("END");
					socketConnexio.close();
					return;
				}

				// si
				System.out.print("Que vols escriure en el fitxer? :");
				String missatge = teclat.next();

				pw.println("200;" + missatge);
				System.err.println("CLIENT >>> Linea enviada y conexio finalitzada");
				socketConnexio.close();

			} else {
				System.err.println("CLIENT >>> Tanca conexio, usuari incorrecte");

				pw.println("END");
				socketConnexio.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		teclat.close();
	}

}
