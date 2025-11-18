package proyectojava_p3;

public class Producto extends Entidad {
	// Atributos privados específicos de un producto
	private double precio; // Precio del producto en formato decimal
	private int cantidad; // Cantidad disponible en stock
	private boolean disponible; // Estado de disponibilidad del producto
	private String categoria; // Categoría a la que pertenece el producto
	private int calorias; // Valor calórico del producto
	private ClaseProveedor proveedor; // Referencia al proveedor del producto

	// Constructor que inicializa todos los atributos del producto
	public Producto(String id, String nombre, double precio, int cantidad, boolean disponible, String categoria,
			int calorias, ClaseProveedor proveedor) {
		super(id, nombre); // Llama al constructor de la clase padre (Entidad)
		this.precio = precio; // Asigna el precio al atributo
		this.cantidad = cantidad; // Asigna la cantidad al atributo
		this.disponible = disponible; // Asigna el estado de disponibilidad
		this.categoria = categoria; // Asigna la categoría al atributo
		this.calorias = calorias; // Asigna las calorías al atributo
		this.proveedor = proveedor; // Asigna el proveedor al atributo
	}

	// Método que permite actualizar todos los datos del producto
	public void actualizarProducto(String nombre, double precio, int cantidad, boolean disponible, String categoria,
			int calorias, ClaseProveedor proveedor) {
		this.nombre = nombre; // Actualiza el nombre (accede al atributo protected de la clase padre)
		this.precio = precio; // Actualiza el precio
		this.cantidad = cantidad; // Actualiza la cantidad
		this.disponible = disponible; // Actualiza el estado de disponibilidad
		this.categoria = categoria; // Actualiza la categoría
		this.calorias = calorias; // Actualiza las calorías
		this.proveedor = proveedor; // Actualiza el proveedor
	}

	// Getters que devuelven los valores de los atributos privados
	public double getPrecio() {
		return precio;
	} // Retorna el precio del producto

	public int getCantidad() {
		return cantidad;
	} // Retorna la cantidad disponible

	public boolean isDisponible() {
		return disponible;
	} // Retorna el estado de disponibilidad

	public String getCategoria() {
		return categoria;
	} // Retorna la categoría del producto

	public int getCalorias() {
		return calorias;
	} // Retorna las calorías del producto

	public ClaseProveedor getProveedor() {
		return proveedor;
	} // Retorna el objeto proveedor

	@Override
	// Implementación específica del método mostrarInformacion para productos
	public void mostrarInformacion() {
		// Imprime información completa del producto usando printf para formato
		System.out.printf(
				"ID: %s | Nombre: %s | Precio: %.2f | Stock: %d | Disponible: %s | Categoría: %s | Calorías: %d | Proveedor: %s\n",
				id, nombre, // ID y nombre heredados de la clase padre
				precio, cantidad, // Precio y cantidad del producto
				(disponible ? "Sí" : "No"), // Convierte boolean a texto legible
				categoria, calorias, // Categoría y calorías del producto
				(proveedor != null ? proveedor.getNombre() : "N/A")); // Nombre del proveedor o "N/A" si es null
	}
}