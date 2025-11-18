package proyectojava_p3;

import java.sql.Connection; // Importa la clase Connection para manejar conexiones a base de datos
import java.sql.DriverManager; // Importa DriverManager para gestionar controladores de base de datos
import java.sql.SQLException; // Importa SQLException para manejar excepciones SQL

public class BasedeDatos {

	// Atributos de la clase - configuración de conexión a MySQL
	private String database = "restaurante"; // Nombre de la base de datos a conectar
	private String hostname = "localhost"; // Dirección IP del servidor (localhost)
	private String port = "3306"; // Puerto estándar de MySQL
	private String login = "root"; // Usuario de la base de datos
	private String pwd = "nevado2005"; // Contraseña del usuario
	// URL completa de conexión con parámetros de zona horaria
	private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?serverTimezone=UTC";
	private Connection conexion; // Objeto Connection para mantener la conexión activa

	// Constructor que se ejecuta al crear una instancia de la clase
	public BasedeDatos() {
		try {
			// Carga el driver JDBC de MySQL en memoria
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establece la conexión usando DriverManager con la URL, usuario y contraseña
			conexion = DriverManager.getConnection(url, login, pwd);
			// Mensaje de confirmación de conexión exitosa
			System.out.println(" - Conexión con BD establecida -");
			// NO cerramos la conexión aquí para mantenerla disponible
		} catch (SQLException e) { // Captura errores específicos de SQL
			// Muestra detalles del error SQL: mensaje, estado y código
			System.out.println(" Error de Conexión con BD: " + e.getMessage() + " -- " + e.getSQLState() + " cod.:"
					+ e.getErrorCode());
			e.printStackTrace(); // Imprime la traza completa del error
		} catch (Exception e) { // Captura cualquier otro tipo de excepción
			System.out.println(" – Error de Conexión con BD -");
			e.printStackTrace(); // Imprime la traza del error general
		}
	}

	// Método público que devuelve la conexión activa
	public Connection getConexion() {
		try {
			// Verifica si la conexión es nula o está cerrada
			if (conexion == null || conexion.isClosed()) {
				// Si está cerrada, crea una nueva conexión
				conexion = DriverManager.getConnection(url, login, pwd);
			}
		} catch (SQLException e) { // Maneja errores al reconectar
			System.out.println("Error al reconectar: " + e.getMessage());
			e.printStackTrace(); // Imprime detalles del error
		}
		return conexion; // Retorna el objeto Connection
	}

	// Método para cerrar la conexión de forma segura
	public void cerrarConexion() {
		try {
			// Verifica que la conexión existe y no está ya cerrada
			if (conexion != null && !conexion.isClosed()) {
				conexion.close(); // Cierra la conexión
				System.out.println(" - Conexión con BD cerrada -"); // Confirma el cierre
			}
		} catch (SQLException e) { // Maneja errores al cerrar
			System.out.println("Error al cerrar conexión: " + e.getMessage());
			e.printStackTrace(); // Imprime detalles del error
		}
	}

	// Método main para probar la funcionalidad de la clase
	public static void main(String[] args) {
		BasedeDatos bd = new BasedeDatos(); // Crea una instancia y establece conexión
		// Cerrar la conexión al final del programa
		bd.cerrarConexion(); // Llama al método para cerrar la conexión
	}
}