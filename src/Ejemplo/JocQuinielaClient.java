package Ejemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class JocQuinielaClient {

	public static void main(String[] args) {
		try {
			Socket socketCliente = new Socket("192.168.43.247", 5000);

			OutputStream os = socketCliente.getOutputStream();
			PrintWriter pw = new PrintWriter(os);

			BufferedReader bfr = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

			String identificador = "David Reinon";
			pw.println(identificador + "\n");
			pw.flush();

			String resultado = bfr.readLine();
			System.out.println("SERVIDOR >>> Envia: " + resultado);

			socketCliente.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
