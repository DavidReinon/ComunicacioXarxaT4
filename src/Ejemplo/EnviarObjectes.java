package Ejemplo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.TrustAnchor;
import java.sql.SQLClientInfoException;

public class EnviarObjectes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (ServerSocket serverSocket = new ServerSocket(1234);) {

			Socket cliente = serverSocket.accept();
			ObjectOutputStream outObject = new ObjectOutputStream(cliente.getOutputStream());

			Coche cocheEnviar = new Coche();
			outObject.writeObject(cocheEnviar);

			ObjectInputStream inObject = new ObjectInputStream(cliente.getInputStream());
			Coche cocheLeer = (Coche) inObject.readObject();
			// cocheLeer.toString() //o lo que sea
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}

class Coche implements Serializable {

	public Coche() {
		super();
		// TODO Auto-generated constructor stub
	}
	// Cosas

}
