package Ejemplo;

import javax.swing.*;

public class InfoEnPopUp {
	public static void main(String[] args) {
		// Simula la información que obtienes de la consulta a MongoDB
		String nombrePlaneta = "Tierra";
		String imagenBase64 = "Base64StringDeLaImagen"; // Reemplaza con tu propia cadena Base64

		// Construye el mensaje a mostrar en el cuadro de diálogo
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("Información del planeta:\n");
		mensaje.append("Nombre: ").append(nombrePlaneta).append("\n");

		// Aquí puedes agregar más información según tus necesidades

		// Muestra la imagen (requiere procesamiento adicional para convertir la cadena
		// Base64 a imagen)
		ImageIcon imagen = decodeBase64ToImageIcon(imagenBase64);
		if (imagen != null) {
			JOptionPane.showMessageDialog(null, mensaje.toString(), "Información del Planeta",
					JOptionPane.INFORMATION_MESSAGE, imagen);
		} else {
			JOptionPane.showMessageDialog(null, mensaje.toString(), "Información del Planeta",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Método para obtener una imagen desde la ruta (utiliza tu lógica)
	private static ImageIcon obtenerImagenDesdeRuta() {
		try {
			// Supongamos que deseas mostrar la primera imagen en la lista de rutasDeImages
			if (!rutasDeImages.isEmpty()) {
				String primeraRuta = rutasDeImages.get(0);
				BufferedImage imagen = ImageIO.read(new File(primeraRuta));
				// Escala la imagen si es necesario
				Image imagenEscalada = imagen.getScaledInstance(-1, 400, Image.SCALE_SMOOTH);
				return new ImageIcon(imagenEscalada);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Método para decodificar la cadena Base64 y devolver un ImageIcon
	private static ImageIcon decodeBase64ToImageIcon(String base64String) {
		try {
			// Convierte la cadena Base64 a bytes
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64String);
			// Crea un ImageIcon desde los bytes
			return new ImageIcon(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
