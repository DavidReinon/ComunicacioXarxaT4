package Ejemplo;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GestorDescarregues {
	public void descarregarArxiu(String url_descarregar, String nomArxiu) {
		System.out.println(" Descarregant " + url_descarregar);
		try {
			URL laUrl = new URL(url_descarregar);
			InputStream is = laUrl.openStream();
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader bReader = new BufferedReader(reader);
			FileWriter escriptorFitxer = new FileWriter(nomArxiu);
			String linia;
			while ((linia = bReader.readLine()) != null) {
				escriptorFitxer.write(linia);
			}
			escriptorFitxer.close();
			bReader.close();
			reader.close();
			is.close();
		} catch (MalformedURLException e) {
			System.out.println("URL mal escrita!");
		} catch (IOException e) {
			System.out.println("Error en la lectura del fitxer");
		}
	}

	public static void main(String[] args) {
		GestorDescarregues gd = new GestorDescarregues();
		String url = "http://localhost:80" + "/Web/fichero.txt";
		String fitxeroDescargado = "fitxeroDescargado.txt";
		gd.descarregarArxiu(url, fitxeroDescargado);
	}
}