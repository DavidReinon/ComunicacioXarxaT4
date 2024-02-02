package florida.Examen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class FilServidor implements Runnable {
	private Socket clientSocket;
	private BufferedReader br;
	private PrintWriter pw;
	private int numeroLiniesContingut = 0;
	private ArrayList<String> contingutSenseNumeros = new ArrayList<String>();

	public FilServidor(Socket client) {
		super();
		this.clientSocket = client;
	}

	@Override
	public void run() {
		try {
			// Recibir
			InputStream is = clientSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			// Enviar
			OutputStream os = clientSocket.getOutputStream();
			pw = new PrintWriter(os, true);

			String usuari = br.readLine();
			String contrasenya = br.readLine();

			System.err.println("SERVIDOR >>> Comprobant credencials");
			if (comprobarCredencials(usuari, contrasenya)) {
				pw.println("204");
			} else {
				pw.println("401");
			}

			String minsatgeClient = br.readLine();
			if (minsatgeClient.equals("GET_CONTENT")) {
				System.err.println("SERVIDOR >>> Enviant contingut");

				llegirContingutAEnviar();
				pw.println(numeroLiniesContingut);
				enviarContingutLineaALinea();

				String lineaTotal = br.readLine();
				String[] arrayLineaTotal = lineaTotal.split(";");
				int numeroComprobacio = Integer.parseInt(arrayLineaTotal[0]);

				if (numeroComprobacio != 200) {
					System.err.println("SERVIDOR >>> Tancant Conexio");
					clientSocket.close();
					return;
				}

				System.err.println("SERVIDOR >>> Afegint linea en el archiu de contingut");
				String missatgeSenseNumero = arrayLineaTotal[1];
				insertarNovaLinea(missatgeSenseNumero);
				clientSocket.close();

			} else {
				System.err.println("SERVIDOR >>> Tancant conexio");
				clientSocket.close();
			}

		} catch (Exception e) {
			try {
				clientSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}

	}

	private boolean comprobarCredencials(String usuari, String Contrasenya) {
		File autenticacio = new File("Usuaris_autoritzats.txt");
		boolean resultat = false;
		try (BufferedReader br = new BufferedReader(new FileReader(autenticacio))) {
			String linea;
			String[] usuariInfo = new String[2];
			while ((linea = br.readLine()) != null) {
				usuariInfo = linea.split(":");
				if (usuariInfo[0].equals(usuari) && usuariInfo[1].equals(Contrasenya)) {
					resultat = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultat;
	}

	private void llegirContingutAEnviar() {
		File contingut = new File("Contingut_a_enviar.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(contingut))) {
			String linea;
			String[] arrayLinea = new String[2];
			while ((linea = br.readLine()) != null) {
				arrayLinea = linea.split(";");
				numeroLiniesContingut = Integer.parseInt(arrayLinea[0]);
				contingutSenseNumeros.add(arrayLinea[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void insertarNovaLinea(String novaLinea) {
		File contingut = new File("Contingut_a_enviar.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(contingut, true));
			numeroLiniesContingut++;
			bw.newLine();
			bw.write(numeroLiniesContingut + ";" + novaLinea);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void enviarContingutLineaALinea() {
		for (String string : contingutSenseNumeros) {
			pw.println(string);
			try {
				Thread.sleep(400);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		pw.println("end");
	}

}
