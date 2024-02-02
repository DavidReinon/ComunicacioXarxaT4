package Ejemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientCalcul {
	public static void main(String[] args) {
		System.out.println("CLIENT >>> Arranca client");

		try {
			System.out.println("CLIENT >>> Conexión al servidor");
			InetSocketAddress direccion = new InetSocketAddress("localhost", 9876);
			// Socket socket = new Socket("localhost", 9876);
			Socket socket = new Socket();
			socket.connect(direccion);

			System.out.println("CLIENT >>> Preparado canal para recibir resultado");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bfr = new BufferedReader(isr);

			System.out.println("CLIENT >>> Envío de datos para el cálculo");

			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println("+");
			pw.println("100");
			pw.println("50");
			// pw.flush();

			String resultado = bfr.readLine();
			String nombreString = bfr.readLine();
			System.out.println("CLIENT >>> Recibe resultado: " + resultado);
			System.out.println("CLIENT >>> Recibe nombre asigando: " + nombreString);

			// Cerrar el socket cuando hayas terminado
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
