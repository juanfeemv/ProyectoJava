package proyectojava_p3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaProveedores extends JFrame {
	private GestorDatos gestor; // Referencia al gestor de datos
	private JTable tablaProveedores; // Tabla para mostrar la lista de proveedores
	private DefaultTableModel modeloTabla; // Modelo de datos para la tabla

	public VentanaProveedores() {
		getContentPane().setBackground(new Color(192, 192, 192)); // Constructor de la ventana de proveedores
		gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
		cargarProveedores(); // Llama al método para cargar los proveedores en la tabla
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Gestión de Proveedores"); // Establece el título de la ventana
		setSize(800, 626);
		setLocationRelativeTo(null); // Centra la ventana en la pantalla
		getContentPane().setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("GESTIÓN DE PROVEEDORES");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setBounds(250, 20, 300, 30);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Botones de acción
		JButton btnAgregar = new JButton("Agregar Proveedor"); // Crea botón para agregar proveedores
		btnAgregar.setBackground(new Color(255, 255, 255));
		btnAgregar.setBounds(50, 70, 150, 30);
		btnAgregar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				abrirVentanaAgregarProveedor(); // Llama al método para abrir ventana de agregar proveedor
			}
		});
		getContentPane().add(btnAgregar); // Agrega el botón al contenedor

		JButton btnActualizar = new JButton("Actualizar"); // Crea botón para actualizar proveedores
		btnActualizar.setBackground(new Color(255, 255, 255));
		btnActualizar.setBounds(311, 70, 150, 30);
		btnActualizar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				actualizarProveedorSeleccionado(); // Llama al método para actualizar proveedor seleccionado
			}
		});
		getContentPane().add(btnActualizar); // Agrega el botón al contenedor

		JButton btnEliminar = new JButton("Eliminar"); // Crea botón para eliminar proveedores
		btnEliminar.setBackground(new Color(255, 255, 255));
		btnEliminar.setBounds(600, 70, 150, 30);
		btnEliminar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				eliminarProveedorSeleccionado(); // Llama al método para eliminar proveedor seleccionado
			}
		});
		getContentPane().add(btnEliminar); // Agrega el botón al contenedor

		// Tabla de proveedores
		String[] columnas = { "ID", "Nombre", "Contacto" }; // Define las columnas de la tabla (más simple que
															// productos)
		modeloTabla = new DefaultTableModel(columnas, 0); // Crea modelo de tabla con las columnas definidas
		tablaProveedores = new JTable(modeloTabla); // Crea tabla con el modelo de datos
		tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleccionar solo una fila a
																				// la vez

		JScrollPane scrollPane = new JScrollPane(tablaProveedores); // Crea panel con scroll para la tabla
		scrollPane.setBounds(50, 120, 700, 400);
		getContentPane().add(scrollPane); // Agrega el panel de scroll al contenedor

		// Botón cerrar
		JButton btnCerrar = new JButton("Cerrar"); // Crea botón para cerrar la ventana
		btnCerrar.setBounds(285, 546, 195, 30);
		btnCerrar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				dispose(); // Cierra la ventana actual
			}
		});
		getContentPane().add(btnCerrar); // Agrega el botón al contenedor
	}

	private void cargarProveedores() { // Método para cargar todos los proveedores en la tabla
		modeloTabla.setRowCount(0); // Limpia todas las filas existentes en la tabla
		ClaseProveedor[] proveedores = gestor.obtenerTodosLosProveedores(); // Obtiene array de todos los proveedores
		for (int i = 0; i < proveedores.length; i++) { // Repite a través del array de proveedores
			ClaseProveedor p = proveedores[i]; // Obtiene el proveedor actual
			Object[] fila = { // Crea array con los datos del proveedor para una fila
					p.getId(), // ID del proveedor
					p.getNombre(), // Nombre del proveedor
					p.getContacto() // Información de contacto del proveedor
			};
			modeloTabla.addRow(fila); // Agrega la fila al modelo de la tabla
		}
	}

	private void abrirVentanaAgregarProveedor() { // Método para abrir la ventana de agregar proveedor
		new VentanaAgregarProveedor(this).setVisible(true); // Crea y muestra la ventana de agregar proveedor pasando
															// esta ventana como padre
	}

	private void actualizarProveedorSeleccionado() { // Método para actualizar el proveedor seleccionado en la tabla
		int filaSeleccionada = tablaProveedores.getSelectedRow(); // Obtiene el índice de la fila seleccionada
		if (filaSeleccionada == -1) { // Si no hay fila seleccionada
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar."); // Muestra mensaje de error
			return; // Sale del método
		}

		String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0); // Obtiene el ID del proveedor de la fila
																			// seleccionada
		ClaseProveedor proveedor = gestor.buscarProveedor(id); // Busca el proveedor por su ID
		if (proveedor != null) { // Si el proveedor existe
			new VentanaActualizarProveedor(this, proveedor).setVisible(true); // Crea y muestra la ventana de actualizar
																				// proveedor
		}
	}

	private void eliminarProveedorSeleccionado() { // Método para eliminar el proveedor seleccionado en la tabla
		int filaSeleccionada = tablaProveedores.getSelectedRow(); // Obtiene el índice de la fila seleccionada
		if (filaSeleccionada == -1) { // Si no hay fila seleccionada
			JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar."); // Muestra mensaje de error
			return; // Sale del método
		}

		int confirmacion = JOptionPane.showConfirmDialog(this, // Muestra diálogo de confirmación
				"¿Está seguro de eliminar este proveedor?", // Mensaje de confirmación
				"Confirmar eliminación", // Título del diálogo
				JOptionPane.YES_NO_OPTION); // Opciones Sí/No

		if (confirmacion == JOptionPane.YES_OPTION) { // Si el usuario confirma la eliminación
			String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0); // Obtiene el ID del proveedor
			if (gestor.eliminarProveedor(id)) { // Intenta eliminar el proveedor
				JOptionPane.showMessageDialog(this, "Proveedor eliminado exitosamente."); // Muestra mensaje de éxito
				cargarProveedores(); // Recarga la tabla para reflejar los cambios
			} else { // Si no se pudo eliminar
				JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor."); // Muestra mensaje de error
			}
		}
	}

	public void actualizarTabla() { // Método público para actualizar la tabla (llamado desde otras ventanas)
		cargarProveedores(); // Recarga todos los proveedores en la tabla
	}
}
