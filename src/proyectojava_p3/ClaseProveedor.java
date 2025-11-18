package proyectojava_p3;

public class ClaseProveedor extends Entidad {
	// Atributo privado específico de proveedor para almacenar información de
	// contacto
	private String contacto;

	// Inicializa un proveedor
	public ClaseProveedor(String id, String nombre, String contacto) {
		// Llama al constructor de la clase Entidad pasando id y nombre
		super(id, nombre);
		// Inicializa el atributo contacto específico de esta clase
		this.contacto = contacto;
	}

	// Getter que devuelve el valor del atributo contacto
	public String getContacto() {
		return contacto; // Retorna el contacto del proveedor
	}

	// Setter que permite modificar el valor del atributo contacto
	public void setContacto(String contacto) {
		this.contacto = contacto; // Asigna el nuevo valor al atributo contacto
	}

	// Setter que permite modificar el nombre heredado de la clase padre
	public void setNombre(String nombre) {
		this.nombre = nombre; // Accede directamente al atributo protected de la clase padre
	}

	// Anotación que indica que este método sobrescribe un método de la clase padre
	@Override
	// Implementación específica del método abstracto mostrarInformacion() de la
	// clase Entidad
	public void mostrarInformacion() {
		// Imprime información formateada del proveedor usando printf
		System.out.printf("ID: %s | Nombre: %s | Contacto: %s\n", id, nombre, contacto); // Muestra id, nombre y
																							// contacto separados por
																							// barras
	}
}