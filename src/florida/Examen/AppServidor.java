package florida.Examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.sql.rowset.JoinRowSet;

public class AppServidor {

	public static void main(String[] args) {
		System.err.println("SERVIDOR >>> Arranca el servidor");
		ServerSocket socketEscolta = null;
		int port = llegirConfig();

		try {
			socketEscolta = new ServerSocket(port);

			while (true) {
				Socket conexio = socketEscolta.accept();
				System.err.println("SERVIDOR >>> Llança fil nou");
				FilServidor filServidor = new FilServidor(conexio);
				Thread fil = new Thread(filServidor);
				fil.start();

			}
		} catch (IOException e) {
			System.err.println("SERVIDOR >>> Error");

		}

	}

	private static int llegirConfig() {
		File autenticacio = new File("Configuracio.txt");
		int port = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(autenticacio))) {

			String linea = br.readLine().split(":")[1];

			port = Integer.parseInt(linea);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return port;
	}

}
