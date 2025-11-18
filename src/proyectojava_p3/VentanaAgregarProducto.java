package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAgregarProducto extends JFrame {
	private GestorDatos gestor; // Referencia al gestor de datos
	private VentanaProductos ventanaPadre; // Referencia a la ventana padre que abrió esta ventana
	private JTextField txtId, txtNombre, txtPrecio, txtCantidad, txtCategoria, txtCalorias; // Campos de texto para
																							// ingresar datos del
																							// producto
	private JCheckBox chkDisponible; // Checkbox para marcar si el producto está disponible
	private JComboBox<String> cmbProveedor; // ComboBox para seleccionar el proveedor del producto

	public VentanaAgregarProducto(VentanaProductos ventanaPadre) { // Constructor que recibe la ventana padre
		this.ventanaPadre = ventanaPadre; // Asigna la referencia de la ventana padre
		this.gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
		cargarProveedores(); // Llama al método para cargar los proveedores en el ComboBox
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Agregar Producto");
		setSize(500, 600);
		setLocationRelativeTo(ventanaPadre); // Centra la ventana respecto a la ventana padre
		getContentPane().setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("AGREGAR NUEVO PRODUCTO"); // Crea etiqueta para el título
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(130, 20, 240, 30);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Campos del formulario
		JLabel lblId = new JLabel("ID:"); // Crea etiqueta para el campo ID
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(50, 80, 100, 25);
		getContentPane().add(lblId); // Agrega la etiqueta al contenedor
		txtId = new JTextField(); // Crea campo de texto para el ID
		txtId.setBounds(150, 80, 278, 25);
		getContentPane().add(txtId); // Agrega el campo de texto al contenedor

		JLabel lblNombre = new JLabel("Nombre:"); // Crea etiqueta para el campo Nombre
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(50, 120, 100, 25);
		getContentPane().add(lblNombre); // Agrega la etiqueta al contenedor
		txtNombre = new JTextField(); // Crea campo de texto para el nombre
		txtNombre.setBounds(150, 120, 278, 25);
		getContentPane().add(txtNombre); // Agrega el campo de texto al contenedor

		JLabel lblPrecio = new JLabel("Precio:"); // Crea etiqueta para el campo Precio
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrecio.setBounds(50, 160, 100, 25);
		getContentPane().add(lblPrecio); // Agrega la etiqueta al contenedor
		txtPrecio = new JTextField(); // Crea campo de texto para el precio
		txtPrecio.setBounds(150, 160, 278, 25);
		getContentPane().add(txtPrecio); // Agrega el campo de texto al contenedor

		JLabel lblCantidad = new JLabel("Cantidad:"); // Crea etiqueta para el campo Cantidad
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCantidad.setBounds(50, 200, 100, 25);
		getContentPane().add(lblCantidad); // Agrega la etiqueta al contenedor
		txtCantidad = new JTextField(); // Crea campo de texto para la cantidad
		txtCantidad.setBounds(150, 200, 278, 25);
		getContentPane().add(txtCantidad); // Agrega el campo de texto al contenedor

		JLabel lblDisponible = new JLabel("Disponible:"); // Crea etiqueta para el campo Disponible
		lblDisponible.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDisponible.setBounds(50, 240, 100, 25);
		getContentPane().add(lblDisponible); // Agrega la etiqueta al contenedor
		chkDisponible = new JCheckBox(); // Crea checkbox para indicar disponibilidad
		chkDisponible.setBounds(150, 240, 25, 25);
		chkDisponible.setSelected(true); // Establece el checkbox como seleccionado por defecto
		getContentPane().add(chkDisponible); // Agrega el checkbox al contenedor

		JLabel lblCategoria = new JLabel("Categoría:"); // Crea etiqueta para el campo Categoría
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCategoria.setBounds(50, 280, 100, 25);
		getContentPane().add(lblCategoria); // Agrega la etiqueta al contenedor
		txtCategoria = new JTextField(); // Crea campo de texto para la categoría
		txtCategoria.setBounds(150, 280, 278, 25);
		getContentPane().add(txtCategoria); // Agrega el campo de texto al contenedor

		JLabel lblCalorias = new JLabel("Calorías:"); // Crea etiqueta para el campo Calorías
		lblCalorias.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCalorias.setBounds(50, 320, 100, 25);
		getContentPane().add(lblCalorias); // Agrega la etiqueta al contenedor
		txtCalorias = new JTextField(); // Crea campo de texto para las calorías
		txtCalorias.setBounds(150, 320, 278, 25);
		getContentPane().add(txtCalorias); // Agrega el campo de texto al contenedor

		JLabel lblProveedor = new JLabel("Proveedor:"); // Crea etiqueta para el campo Proveedor
		lblProveedor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblProveedor.setBounds(50, 360, 100, 25);
		getContentPane().add(lblProveedor); // Agrega la etiqueta al contenedor
		cmbProveedor = new JComboBox<String>(); // Crea ComboBox para seleccionar proveedor
		cmbProveedor.setBounds(150, 360, 278, 25);
		getContentPane().add(cmbProveedor); // Agrega el ComboBox al contenedor

		// Botones
		JButton btnGuardar = new JButton("Guardar"); // Crea botón para guardar el producto
		btnGuardar.setBounds(50, 450, 178, 30);
		btnGuardar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				guardarProducto(); // Llama al método para guardar el producto
			}
		});
		getContentPane().add(btnGuardar); // Agrega el botón al contenedor

		JButton btnCancelar = new JButton("Cancelar"); // Crea botón para cancelar la operación
		btnCancelar.setBounds(270, 450, 158, 30); // Posiciona el botón
		btnCancelar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				dispose(); // Cierra la ventana actual
			}
		});
		getContentPane().add(btnCancelar); // Agrega el botón al contenedor
	}

	private void cargarProveedores() { // Método para cargar la lista de proveedores en el ComboBox
		cmbProveedor.addItem("Sin proveedor"); // Agrega opción por defecto sin proveedor
		ClaseProveedor[] proveedores = gestor.obtenerTodosLosProveedores(); // Obtiene array de todos los proveedores
		for (int i = 0; i < proveedores.length; i++) { // Itera a través del array de proveedores
			ClaseProveedor p = proveedores[i]; // Obtiene el proveedor actual
			cmbProveedor.addItem(p.getId() + " - " + p.getNombre()); // Agrega el proveedor al ComboBox con formato "ID
																		// - Nombre"
		}
	}

	private void guardarProducto() { // Método para validar y guardar el nuevo producto
		try { // Bloque try-catch para manejar excepciones
			String id = txtId.getText().trim(); // Obtiene el ID eliminando espacios en blanco
			String nombre = txtNombre.getText().trim(); // Obtiene el nombre eliminando espacios en blanco
			String precioStr = txtPrecio.getText().trim(); // Obtiene el precio como string eliminando espacios
			String cantidadStr = txtCantidad.getText().trim(); // Obtiene la cantidad como string eliminando espacios
			String categoria = txtCategoria.getText().trim(); // Obtiene la categoría eliminando espacios en blanco
			String caloriasStr = txtCalorias.getText().trim(); // Obtiene las calorías como string eliminando espacios

			// Validaciones
			if (id.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || // Verifica si algún campo está vacío
					cantidadStr.isEmpty() || categoria.isEmpty() || caloriasStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios."); // Muestra mensaje de error
				return; // Sale del método sin guardar
			}

			if (id.contains(" ")) { // Verifica si el ID contiene espacios
				JOptionPane.showMessageDialog(this, "El ID no debe contener espacios."); // Muestra mensaje de error
				return; // Sale del método sin guardar
			}

			double precio = Double.parseDouble(precioStr); // Convierte el precio de string a double
			int cantidad = Integer.parseInt(cantidadStr); // Convierte la cantidad de string a int
			int calorias = Integer.parseInt(caloriasStr); // Convierte las calorías de string a int
			boolean disponible = chkDisponible.isSelected(); // Obtiene el estado del checkbox

			if (precio < 0 || cantidad < 0 || calorias < 0) { // Verifica que los valores no sean negativos
				JOptionPane.showMessageDialog(this, "Los valores numéricos no pueden ser negativos."); // Muestra
																										// mensaje de
																										// error
				return; // Sale del método sin guardar
			}

			// Obtener proveedor
			ClaseProveedor proveedor = null; // Inicializa proveedor como null
			int proveedorIndex = cmbProveedor.getSelectedIndex(); // Obtiene el índice del proveedor seleccionado
			if (proveedorIndex > 0) { // Si se seleccionó un proveedor (no "Sin proveedor")
				String proveedorSeleccionado = (String) cmbProveedor.getSelectedItem(); // Obtiene el texto del
																						// proveedor seleccionado
				String idProveedor = proveedorSeleccionado.split(" - ")[0]; // Extrae el ID del proveedor del texto
				proveedor = gestor.buscarProveedor(idProveedor); // Busca el objeto proveedor por su ID
			}

			Producto producto = new Producto(id, nombre, precio, cantidad, disponible, categoria, calorias, proveedor); // Crea
																														// nuevo
																														// objeto
																														// Producto

			if (gestor.agregarProducto(producto)) { // Intenta agregar el producto al gestor
				JOptionPane.showMessageDialog(this, "Producto agregado exitosamente."); // Muestra mensaje de éxito
				ventanaPadre.actualizarTabla(); // Actualiza la tabla en la ventana padre
				dispose(); // Cierra la ventana actual
			} else { // Si no se pudo agregar el producto
				JOptionPane.showMessageDialog(this, "Error: Ya existe un producto con este ID."); // Muestra mensaje de
																									// error
			}

		} catch (NumberFormatException e) { // Captura errores de conversión de números
			JOptionPane.showMessageDialog(this, "Error en formato de números. Verifique los datos."); // Muestra mensaje
																										// de error
		} catch (Exception e) { // Captura cualquier otra excepción
			JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage()); // Muestra mensaje de
																									// error con
																									// detalles
		}
	}
}
