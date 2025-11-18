package proyectojava_p3;

// Clase abstracta que sirve como plantilla base para otras entidades del sistema
public abstract class Entidad {
	// Atributo protected para el identificador - accesible por clases hijas
	protected String id;
	// Atributo protected para el nombre - accesible por clases hijas
	protected String nombre;

	// Constructor que inicializa los atributos básicos de cualquier entidad
	public Entidad(String id, String nombre) {
		this.id = id; // Asigna el parámetro id al atributo id de la clase
		this.nombre = nombre; // Asigna el parámetro nombre al atributo nombre de la clase
	}

	// Getter público que devuelve el valor del atributo id
	public String getId() {
		return id; // Retorna el identificador de la entidad
	}

	// Getter público que devuelve el valor del atributo nombre
	public String getNombre() {
		return nombre; // Retorna el nombre de la entidad
	}

	// Método abstracto que debe ser implementado por todas las clases hijas
	public abstract void mostrarInformacion();
}