package proyectojava_p3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaProductos extends JFrame {
	private GestorDatos gestor; // Referencia al gestor de datos
	private JTable tablaProductos; // Tabla para mostrar la lista de productos
	private DefaultTableModel modeloTabla; // Modelo de datos para la tabla

	public VentanaProductos() {
		getContentPane().setBackground(new Color(192, 192, 192)); // Constructor de la ventana de productos
		gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
		cargarProductos(); // Llama al método para cargar los productos en la tabla
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Gestión de Productos"); // Establece el título de la ventana
		setSize(1000, 700);
		setLocationRelativeTo(null); //
		getContentPane().setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("GESTIÓN DE PRODUCTOS"); // Crea etiqueta para el título
		lblTitulo.setBounds(350, 20, 300, 30);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Botones de acción
		JButton btnAgregar = new JButton("Agregar Producto"); // Crea botón para agregar productos
		btnAgregar.setBackground(new Color(255, 255, 255));
		btnAgregar.setBounds(81, 70, 150, 30);
		btnAgregar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				abrirVentanaAgregarProducto(); // Llama al método para abrir ventana de agregar producto
			}
		});
		getContentPane().add(btnAgregar); // Agrega el botón al contenedor

		JButton btnActualizar = new JButton("Actualizar"); // Crea botón para actualizar productos
		btnActualizar.setBackground(new Color(255, 255, 255));
		btnActualizar.setBounds(342, 70, 150, 30);
		btnActualizar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				actualizarProductoSeleccionado(); // Llama al método para actualizar producto seleccionado
			}
		});
		getContentPane().add(btnActualizar); // Agrega el botón al contenedor

		JButton btnEliminar = new JButton("Eliminar"); // Crea botón para eliminar productos
		btnEliminar.setBackground(new Color(255, 255, 255));
		btnEliminar.setBounds(561, 70, 150, 30);
		btnEliminar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				eliminarProductoSeleccionado(); // Llama al método para eliminar producto seleccionado
			}
		});
		getContentPane().add(btnEliminar); // Agrega el botón al contenedor

		JButton btnBuscar = new JButton("Buscar"); // Crea botón para buscar productos
		btnBuscar.setBackground(new Color(255, 255, 255));
		btnBuscar.setBounds(779, 70, 150, 30);
		btnBuscar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				abrirVentanaBusqueda(); // Llama al método para abrir ventana de búsqueda
			}
		});
		getContentPane().add(btnBuscar); // Agrega el botón al contenedor

		// Tabla de productos
		String[] columnas = { "ID", "Nombre", "Precio", "Cantidad", "Disponible", "Categoría", "Calorías",
				"Proveedor" }; // Define las columnas de la tabla
		modeloTabla = new DefaultTableModel(columnas, 0); // Crea modelo de tabla con las columnas definidas
		tablaProductos = new JTable(modeloTabla); // Crea tabla con el modelo de datos
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solo una fila a la
																				// vez

		JScrollPane scrollPane = new JScrollPane(tablaProductos); // Crea panel con scroll para la tabla
		scrollPane.setBounds(50, 120, 900, 400);
		getContentPane().add(scrollPane); // Agrega el panel de scroll al contenedor

		// Botón cerrar
		JButton btnCerrar = new JButton("Cerrar"); // Crea botón para cerrar la ventana
		btnCerrar.setBackground(new Color(255, 255, 255));
		btnCerrar.setBounds(451, 544, 100, 30);
		btnCerrar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				dispose(); // Cierra la ventana actual
			}
		});
		getContentPane().add(btnCerrar); // Agrega el botón al contenedor
	}

	private void cargarProductos() { // Método para cargar todos los productos en la tabla
		modeloTabla.setRowCount(0); // Limpia todas las filas existentes en la tabla
		Producto[] productos = gestor.obtenerTodosLosProductos(); // Obtiene array de todos los productos
		for (int i = 0; i < productos.length; i++) { // Itera a través del array de productos
			Producto p = productos[i]; // Obtiene el producto actual
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
			modeloTabla.addRow(fila); // Agrega la fila al modelo de la tabla
		}
	}

	private void abrirVentanaAgregarProducto() { // Método para abrir la ventana de agregar producto
		new VentanaAgregarProducto(this).setVisible(true); // Crea y muestra la ventana de agregar producto pasando esta
															// ventana como padre
	}

	private void actualizarProductoSeleccionado() { // Método para actualizar el producto seleccionado en la tabla
		int filaSeleccionada = tablaProductos.getSelectedRow(); // Obtiene el índice de la fila seleccionada
		if (filaSeleccionada == -1) { // Si no hay fila seleccionada
			JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar."); // Muestra mensaje de error
			return; // Sale del método
		}

		String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0); // Obtiene el ID del producto de la fila
																			// seleccionada
		Producto producto = gestor.buscarProducto(id); // Busca el producto por su ID
		if (producto != null) { // Si el producto existe
			new VentanaActualizarProducto(this, producto).setVisible(true); // Crea y muestra la ventana de actualizar
																			// producto
		}
	}

	private void eliminarProductoSeleccionado() { // Método para eliminar el producto seleccionado en la tabla
		int filaSeleccionada = tablaProductos.getSelectedRow(); // Obtiene el índice de la fila seleccionada
		if (filaSeleccionada == -1) { // Si no hay fila seleccionada
			JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar."); // Muestra mensaje de error
			return; // Sale del método
		}

		int confirmacion = JOptionPane.showConfirmDialog(this, // Muestra diálogo de confirmación
				"¿Está seguro de eliminar este producto?", // Mensaje de confirmación
				"Confirmar eliminación", // Título del diálogo
				JOptionPane.YES_NO_OPTION); // Opciones Sí/No

		if (confirmacion == JOptionPane.YES_OPTION) { // Si el usuario confirma la eliminación
			String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0); // Obtiene el ID del producto
			if (gestor.eliminarProducto(id)) { // Intenta eliminar el producto
				JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente."); // Muestra mensaje de éxito
				cargarProductos(); // Recarga la tabla para reflejar los cambios
			} else { // Si no se pudo eliminar
				JOptionPane.showMessageDialog(this, "Error al eliminar el producto."); // Muestra mensaje de error
			}
		}
	}

	private void abrirVentanaBusqueda() { // Método para abrir la ventana de búsqueda
		new VentanaBuscarProducto(this).setVisible(true); // Crea y muestra la ventana de búsqueda pasando esta ventana
															// como padre
	}

	public void actualizarTabla() { // Método público para actualizar la tabla (llamado desde otras ventanas)
		cargarProductos(); // Recarga todos los productos en la tabla
	}
}
