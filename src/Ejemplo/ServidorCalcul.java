package Ejemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.text.AbstractDocument.BranchElement;

public class ServidorCalcul {

	public static void main(String[] args) throws IOException {
		System.err.println("SERVIDOR >>> Arranca el servidor, espera peticiones");

		ServerSocket socketEscolta = null;

		try {
			socketEscolta = new ServerSocket(9876);
		} catch (IOException e) {
			System.err.println("SERVIDOR >>> Error");
			return;
		}

		while (true) {
			Socket connexio = socketEscolta.accept();
			System.err.println("SERVIDOR >>> Conexión recibida!");

			InputStream is = connexio.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(isr);

			System.err.println("SERVIDOR >>> Lee datos para la operación");
			String signCalcul = bf.readLine();
			String num1 = bf.readLine();
			String num2 = bf.readLine();

			System.err.println("SERVIDOR >>> Realiza la operación");
			Integer result = calcular(signCalcul, num1, num2);

			System.err.println("SERVIDOR >>> Retorna resultado");
			OutputStream os = connexio.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			pw.println(result.toString());
			String nomClientString = "Antonio";
			pw.println(nomClientString);

			System.err.println("SERVIDOR >>> Espera nueva petición");
		}
	}

	public static int extraerNumero(String linea) {
		int numero;
		try {
			numero = Integer.parseInt(linea);
		} catch (NumberFormatException e) {
			numero = 0;
		}

		if (numero >= 100000000) {
			numero = 0;
		}

		return numero;
	}

	public static int calcular(String operador, String num1, String num2) {
		int resultado = 0;
		char simbolo = operador.charAt(0);

		int numero1 = extraerNumero(num1);
		int numero2 = extraerNumero(num2);

		if (simbolo == '+') {
			resultado = numero1 + numero2;
		}

		return resultado;
	}

}
