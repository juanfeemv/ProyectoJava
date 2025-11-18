package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class VentanaBuscarProducto extends JFrame {
	private GestorDatos gestor;
	private VentanaProductos ventanaPadre; // Referencia a la ventana padre que abrió esta ventana
	private JRadioButton rbNombre, rbCategoria; // RadioButtons para seleccionar criterio de búsqueda
	private JTextField txtBusqueda; // Campo de texto para ingresar el término de búsqueda
	private JButton btnBuscar; // Botón para ejecutar la búsqueda

	public VentanaBuscarProducto(VentanaProductos ventanaPadre) {
		getContentPane().setBackground(new Color(192, 192, 192)); // Constructor que recibe la ventana padre
		this.ventanaPadre = ventanaPadre; // Asigna la referencia de la ventana padre
		this.gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Buscar Productos");
		setSize(400, 250);
		setLocationRelativeTo(ventanaPadre); // Centra la ventana respecto a la ventana padre
		getContentPane().setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("BUSCAR PRODUCTOS"); // Crea etiqueta para el título
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(107, 11, 200, 30);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Opciones de búsqueda
		JLabel lblBuscarPor = new JLabel("Buscar por:"); // Crea etiqueta para las opciones de búsqueda
		lblBuscarPor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBuscarPor.setBounds(50, 52, 100, 25);
		getContentPane().add(lblBuscarPor); // Agrega la etiqueta al contenedor

		rbNombre = new JRadioButton("Nombre"); // Crea RadioButton para búsqueda por nombre
		rbNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rbNombre.setBounds(156, 52, 100, 25);
		rbNombre.setSelected(true); // Establece este RadioButton como seleccionado por defecto
		getContentPane().add(rbNombre); // Agrega el RadioButton al contenedor

		rbCategoria = new JRadioButton("Categoría"); // Crea RadioButton para búsqueda por categoría
		rbCategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rbCategoria.setBounds(156, 80, 100, 25);
		getContentPane().add(rbCategoria); // Agrega el RadioButton al contenedor

		ButtonGroup grupoBusqueda = new ButtonGroup(); // Crea grupo de botones para hacer mutuamente exclusivos los
														// RadioButtons
		grupoBusqueda.add(rbNombre); // Agrega el RadioButton de nombre al grupo
		grupoBusqueda.add(rbCategoria); // Agrega el RadioButton de categoría al grupo

		// Campo de búsqueda
		JLabel lblTextoBusqueda = new JLabel("Texto a buscar:"); // Crea etiqueta para el campo de búsqueda
		lblTextoBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTextoBusqueda.setBounds(50, 124, 100, 25);
		getContentPane().add(lblTextoBusqueda); // Agrega la etiqueta al contenedor

		txtBusqueda = new JTextField(); // Crea campo de texto para ingresar el término de búsqueda
		txtBusqueda.setBounds(150, 124, 200, 25);
		getContentPane().add(txtBusqueda); // Agrega el campo de texto al contenedor

		// Botones
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(255, 255, 255));
		btnBuscar.setBounds(124, 170, 142, 30);
		btnBuscar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				buscarProductos(); // Llama al método para buscar productos
			}
		});
		getContentPane().add(btnBuscar); // Agrega el botón al contenedor
	}

	private void buscarProductos() { // Método para ejecutar la búsqueda de productos
		String textoBusqueda = txtBusqueda.getText().trim(); // Obtiene el texto de búsqueda eliminando espacios
		if (textoBusqueda.isEmpty()) { // Verifica si el campo de búsqueda está vacío
			JOptionPane.showMessageDialog(this, "Ingrese un texto para buscar."); // Muestra mensaje de error
			return; // Sale del método sin buscar
		}

		ArrayList<Producto> resultados; // Declara lista para almacenar los resultados
		if (rbNombre.isSelected()) { // Si está seleccionado buscar por nombre
			resultados = gestor.buscarProductosPorNombre(textoBusqueda); // Busca productos por nombre
		} else { // Si está seleccionado buscar por categoría
			resultados = gestor.buscarProductosPorCategoria(textoBusqueda); // Busca productos por categoría
		}

		if (resultados.isEmpty()) { // Si no se encontraron resultados
			JOptionPane.showMessageDialog(this, "No se encontraron productos."); // Muestra mensaje informativo
		} else { // Si se encontraron resultados
			mostrarResultados(resultados); // Llama al método para mostrar los resultados
		}
	}

	private void mostrarResultados(ArrayList<Producto> productos) { // Método para mostrar los resultados en una tabla
		JDialog dialog = new JDialog(this, "Resultados de búsqueda", true); // Crea diálogo modal para mostrar
																			// resultados
		dialog.setSize(800, 400); // Establece el tamaño del diálogo
		dialog.setLocationRelativeTo(this); // Centra el diálogo respecto a la ventana actual
		dialog.getContentPane().setLayout(new BorderLayout());

		String[] columnas = { "ID", "Nombre", "Precio", "Cantidad", "Disponible", "Categoría", "Calorías",
				"Proveedor" }; // Define las columnas de la tabla
		DefaultTableModel modelo = new DefaultTableModel(columnas, 0); // Crea modelo de tabla con las columnas
																		// definidas

		for (Producto p : productos) { // Itera a través de la lista de productos encontrados
			Object[] fila = { // Crea array con los datos del producto para una fila
					p.getId(), // ID del producto
					p.getNombre(), // Nombre del producto
					String.format("%.2f", p.getPrecio()), // Precio formateado a 2 decimales
					p.getCantidad(), // Cantidad del producto
					p.isDisponible() ? "Sí" : "No", // Disponibilidad como texto
					p.getCategoria(), // Categoría del producto
					p.getCalorias(), // Calorías del producto
					p.getProveedor() != null ? p.getProveedor().getNombre() : "N/A" // Nombre del proveedor o "N/A" si
																					// no tiene
			};
			modelo.addRow(fila); // Agrega la fila al modelo de la tabla
		}

		JTable tabla = new JTable(modelo); // Crea tabla con el modelo de datos
		JScrollPane scrollPane = new JScrollPane(tabla); // Crea panel con scroll para la tabla
		dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JButton btnCerrar = new JButton("Cerrar"); // Crea botón para cerrar el diálogo
		btnCerrar.addActionListener(new ActionListener() { // Agrega listener para el botón cerrar
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				dialog.dispose(); // Cierra el diálogo
			}
		});

		JPanel panelBoton = new JPanel(); // Crea panel para contener el botón
		panelBoton.add(btnCerrar); // Agrega el botón al panel
		dialog.getContentPane().add(panelBoton, BorderLayout.SOUTH);

		dialog.setVisible(true); // Hace visible el diálogo
	}
}
