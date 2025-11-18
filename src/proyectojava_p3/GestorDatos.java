package proyectojava_p3;

import java.sql.*;
import java.util.ArrayList; // Importa ArrayList para manejo de listas

public class GestorDatos {
	// Variable estática que mantiene la única instancia de la clase
	private static GestorDatos instancia;
	// Instancia de BasedeDatos para manejar conexiones
	private BasedeDatos baseDatos;

	// Constructor privado - impide crear instancias desde fuera de la clase
	private GestorDatos() {
		baseDatos = new BasedeDatos(); // Inicializa la conexión a base de datos
	}

	public static GestorDatos getInstancia() {
		// Si no existe instancia, crea una nueva
		if (instancia == null) {
			instancia = new GestorDatos();
		}
		return instancia; // Retorna la única instancia existente
	}

	// Método que inserta un nuevo producto en la base de datos
	public boolean agregarProducto(Producto producto) {
		// Query preparada para insertar producto con todos sus campos
		String sql = "INSERT INTO productos (id, nombre, precio, cantidad, disponible, categoria, calorias, proveedor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		// manejo de recursos
		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara la consulta

			// Asigna valores a los parámetros de la consulta preparada
			pstmt.setString(1, producto.getId()); // Asigna ID del producto
			pstmt.setString(2, producto.getNombre()); // Asigna nombre del producto
			pstmt.setDouble(3, producto.getPrecio()); // Asigna precio del producto
			pstmt.setInt(4, producto.getCantidad()); // Asigna cantidad del producto
			pstmt.setBoolean(5, producto.isDisponible()); // Asigna disponibilidad
			pstmt.setString(6, producto.getCategoria()); // Asigna categoría
			pstmt.setInt(7, producto.getCalorias()); // Asigna calorías
			// Asigna ID del proveedor si existe, sino null
			pstmt.setString(8, producto.getProveedor() != null ? producto.getProveedor().getId() : null);

			// Ejecuta la consulta y retorna true si afectó al menos una fila
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) { // Captura errores SQL
			System.out.println("Error al agregar producto: " + e.getMessage());
			return false; // Retorna false en caso de error
		}
	}

	// Método que busca un producto por su ID
	public Producto buscarProducto(String id) {
		// Query con JOIN para obtener producto y datos de su proveedor
		String sql = "SELECT p.*, pr.nombre as proveedor_nombre, pr.contacto as proveedor_contacto "
				+ "FROM productos p LEFT JOIN proveedores pr ON p.proveedor_id = pr.id WHERE p.id = ?";

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			pstmt.setString(1, id); // Asigna el ID a buscar
			ResultSet rs = pstmt.executeQuery(); // Ejecuta la consulta

			// Si encuentra el producto
			if (rs.next()) {
				ClaseProveedor proveedor = null; // Inicializa proveedor como null
				// Si el producto tiene proveedor asignado
				if (rs.getString("proveedor_id") != null) {
					// Crea objeto proveedor con datos del JOIN
					proveedor = new ClaseProveedor(rs.getString("proveedor_id"), rs.getString("proveedor_nombre"),
							rs.getString("proveedor_contacto"));
				}

				// Crea y retorna objeto Producto con todos los datos
				return new Producto(rs.getString("id"), rs.getString("nombre"), rs.getDouble("precio"),
						rs.getInt("cantidad"), rs.getBoolean("disponible"), rs.getString("categoria"),
						rs.getInt("calorias"), proveedor);
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al buscar producto: " + e.getMessage());
		}

		return null; // Retorna null si no encuentra el producto o hay error
	}

	// Método que elimina un producto de la base de datos
	public boolean eliminarProducto(String id) {
		String sql = "DELETE FROM productos WHERE id = ?"; // Query para eliminar por ID

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			pstmt.setString(1, id); // Asigna ID del producto a eliminar
			int rowsAffected = pstmt.executeUpdate(); // Ejecuta eliminación
			return rowsAffected > 0; // Retorna true si eliminó al menos una fila

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al eliminar producto: " + e.getMessage());
			return false; // Retorna false en caso de error
		}
	}

	// Método que obtiene todos los productos de la base de datos
	public Producto[] obtenerTodosLosProductos() {
		ArrayList<Producto> lista = new ArrayList<>(); // Lista para almacenar productos
		// Query con JOIN para obtener todos los productos y sus proveedores
		String sql = "SELECT p.*, pr.nombre as proveedor_nombre, pr.contacto as proveedor_contacto "
				+ "FROM productos p LEFT JOIN proveedores pr ON p.proveedor_id = pr.id";

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				Statement stmt = conn.createStatement(); // Crea statement
				ResultSet rs = stmt.executeQuery(sql)) { // Ejecuta consulta

			// Repite sobre todos los resultados
			while (rs.next()) {
				ClaseProveedor proveedor = null; // Inicializa proveedor
				// Si el producto tiene proveedor
				if (rs.getString("proveedor_id") != null) {
					// Crea objeto proveedor
					proveedor = new ClaseProveedor(rs.getString("proveedor_id"), rs.getString("proveedor_nombre"),
							rs.getString("proveedor_contacto"));
				}

				// Crea objeto producto y lo agrega a la lista
				Producto producto = new Producto(rs.getString("id"), rs.getString("nombre"), rs.getDouble("precio"),
						rs.getInt("cantidad"), rs.getBoolean("disponible"), rs.getString("categoria"),
						rs.getInt("calorias"), proveedor);
				lista.add(producto); // Agrega producto a la lista
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al obtener productos: " + e.getMessage());
		}

		// Convierte ArrayList a array y lo retorna
		return lista.toArray(new Producto[0]);
	}

	// Método que busca productos por nombre usando LIKE (búsqueda parcial)
	public ArrayList<Producto> buscarProductosPorNombre(String nombre) {
		ArrayList<Producto> resultados = new ArrayList<>(); // Lista para resultados
		// Query con LIKE para búsqueda parcial de nombre
		String sql = "SELECT p.*, pr.nombre as proveedor_nombre, pr.contacto as proveedor_contacto "
				+ "FROM productos p LEFT JOIN proveedores pr ON p.proveedor_id = pr.id " + "WHERE p.nombre LIKE ?";

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			// Agrega % para búsqueda parcial (contiene el texto)
			pstmt.setString(1, "%" + nombre + "%");
			ResultSet rs = pstmt.executeQuery(); // Ejecuta consulta

			// Procesa todos los resultados encontrados
			while (rs.next()) {
				ClaseProveedor proveedor = null; // Inicializa proveedor
				// Si tiene proveedor asignado
				if (rs.getString("proveedor_id") != null) {
					// Crea objeto proveedor
					proveedor = new ClaseProveedor(rs.getString("proveedor_id"), rs.getString("proveedor_nombre"),
							rs.getString("proveedor_contacto"));
				}

				// Crea producto y lo agrega a resultados
				Producto producto = new Producto(rs.getString("id"), rs.getString("nombre"), rs.getDouble("precio"),
						rs.getInt("cantidad"), rs.getBoolean("disponible"), rs.getString("categoria"),
						rs.getInt("calorias"), proveedor);
				resultados.add(producto); // Agrega a la lista de resultados
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al buscar productos por nombre: " + e.getMessage());
		}

		return resultados; // Retorna lista de productos encontrados
	}

	// Método que busca productos por categoría usando LIKE
	public ArrayList<Producto> buscarProductosPorCategoria(String categoria) {
		ArrayList<Producto> resultados = new ArrayList<>(); // Lista para resultados
		// Query con LIKE para búsqueda parcial de categoría
		String sql = "SELECT p.*, pr.nombre as proveedor_nombre, pr.contacto as proveedor_contacto "
				+ "FROM productos p LEFT JOIN proveedores pr ON p.proveedor_id = pr.id " + "WHERE p.categoria LIKE ?";

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			// Agrega % para búsqueda parcial
			pstmt.setString(1, "%" + categoria + "%");
			ResultSet rs = pstmt.executeQuery(); // Ejecuta consulta

			// Procesa todos los resultados
			while (rs.next()) {
				ClaseProveedor proveedor = null; // Inicializa proveedor
				// Si tiene proveedor
				if (rs.getString("proveedor_id") != null) {
					// Crea objeto proveedor
					proveedor = new ClaseProveedor(rs.getString("proveedor_id"), rs.getString("proveedor_nombre"),
							rs.getString("proveedor_contacto"));
				}

				// Crea producto y lo agrega a resultados
				Producto producto = new Producto(rs.getString("id"), rs.getString("nombre"), rs.getDouble("precio"),
						rs.getInt("cantidad"), rs.getBoolean("disponible"), rs.getString("categoria"),
						rs.getInt("calorias"), proveedor);
				resultados.add(producto); // Agrega a la lista
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al buscar productos por categoría: " + e.getMessage());
		}

		return resultados; // Retorna productos encontrados
	}

	// Método que agrega un nuevo proveedor a la base de datos
	public boolean agregarProveedor(ClaseProveedor proveedor) {
		String sql = "INSERT INTO proveedores (id, nombre, contacto) VALUES (?, ?, ?)"; // Query INSERT

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			// Asigna valores a los parámetros
			pstmt.setString(1, proveedor.getId()); // ID del proveedor
			pstmt.setString(2, proveedor.getNombre()); // Nombre del proveedor
			pstmt.setString(3, proveedor.getContacto()); // Contacto del proveedor

			int filas = pstmt.executeUpdate(); // Ejecuta inserción
			return filas > 0; // Retorna true si insertó al menos una fila

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al agregar proveedor: " + e.getMessage());
			return false; // Retorna false en caso de error
		}
	}

	// Método que busca un proveedor por su ID
	public ClaseProveedor buscarProveedor(String id) {
		String sql = "SELECT * FROM proveedores WHERE id = ?"; // Query SELECT por ID

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			pstmt.setString(1, id); // Asigna ID a buscar
			ResultSet rs = pstmt.executeQuery(); // Ejecuta consulta

			// Si encuentra el proveedor
			if (rs.next()) {
				// Crea y retorna objeto ClaseProveedor
				return new ClaseProveedor(rs.getString("id"), rs.getString("nombre"), rs.getString("contacto"));
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al buscar proveedor: " + e.getMessage());
		}

		return null; // Retorna null si no encuentra o hay error
	}

	// Método que elimina un proveedor por su ID
	public boolean eliminarProveedor(String id) {
		String sql = "DELETE FROM proveedores WHERE id = ?"; // Query DELETE

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta

			pstmt.setString(1, id); // Asigna ID a eliminar
			int rowsAffected = pstmt.executeUpdate(); // Ejecuta eliminación
			return rowsAffected > 0; // Retorna true si eliminó al menos una fila

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al eliminar proveedor: " + e.getMessage());
			return false; // Retorna false en caso de error
		}
	}

	// Método que obtiene todos los proveedores de la base de datos
	public ClaseProveedor[] obtenerTodosLosProveedores() {
		ArrayList<ClaseProveedor> lista = new ArrayList<>(); // Lista para proveedores
		String sql = "SELECT * FROM proveedores"; // Query para obtener todos

		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				Statement stmt = conn.createStatement(); // Crea statement
				ResultSet rs = stmt.executeQuery(sql)) { // Ejecuta consulta

			// Itera sobre todos los resultados
			while (rs.next()) {
				// Crea objeto proveedor y lo agrega a la lista
				ClaseProveedor proveedor = new ClaseProveedor(rs.getString("id"), rs.getString("nombre"),
						rs.getString("contacto"));
				lista.add(proveedor); // Agrega a la lista
			}

		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al obtener proveedores: " + e.getMessage());
		}

		// Convierte ArrayList a array y lo retorna
		return lista.toArray(new ClaseProveedor[0]);
	}

	// Método que actualiza los datos de un producto existente
	public boolean actualizarProducto(Producto producto) {
		// Query UPDATE que modifica todos los campos excepto el ID
		String sql = "UPDATE productos SET nombre=?, precio=?, cantidad=?, disponible=?, categoria=?, calorias=?, proveedor_id=? WHERE id=?";
		try (Connection conn = baseDatos.getConexion(); // Obtiene conexión
				PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepara consulta
			// Asigna nuevos valores a los parámetros
			pstmt.setString(1, producto.getNombre()); // Nuevo nombre
			pstmt.setDouble(2, producto.getPrecio()); // Nuevo precio
			pstmt.setInt(3, producto.getCantidad()); // Nueva cantidad
			pstmt.setBoolean(4, producto.isDisponible()); // Nueva disponibilidad
			pstmt.setString(5, producto.getCategoria()); // Nueva categoría
			pstmt.setInt(6, producto.getCalorias()); // Nuevas calorías
			// Nuevo proveedor (ID si existe, null si no)
			pstmt.setString(7, producto.getProveedor() != null ? producto.getProveedor().getId() : null);
			pstmt.setString(8, producto.getId()); // ID del producto a actualizar (condición WHERE)
			return pstmt.executeUpdate() > 0; // Retorna true si actualizó al menos una fila
		} catch (SQLException e) { // Maneja errores SQL
			System.out.println("Error al actualizar producto: " + e.getMessage());
			return false; // Retorna false en caso de error
		}
	}

	// Método para cerrar la conexión de base de datos de forma segura
	public void cerrarConexion() {
		// Verifica que baseDatos no sea null antes de cerrar
		if (baseDatos != null) {
			baseDatos.cerrarConexion(); // Llama al método de cierre de BasedeDatos
		}
	}
}