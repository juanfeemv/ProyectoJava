package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaActualizarProducto extends JFrame {
	// Variable para manejar los datos del sistema
	private GestorDatos gestor;
	// Referencia a la ventana padre que abrió esta ventana
	private VentanaProductos ventanaPadre;
	// El producto que se va a actualizar
	private Producto producto;
	// Campos de texto para ingresar los datos del producto
	private JTextField txtNombre, txtPrecio, txtCantidad, txtCategoria, txtCalorias;
	// Checkbox para marcar si el producto está disponible
	private JCheckBox chkDisponible;
	// ComboBox para seleccionar el proveedor
	private JComboBox<String> cmbProveedor;

	// Constructor que recibe la ventana padre y el producto a actualizar
	public VentanaActualizarProducto(VentanaProductos ventanaPadre, Producto producto) {
		getContentPane().setBackground(new Color(192, 192, 192));
		// Asigna la ventana padre a la variable de instancia
		this.ventanaPadre = ventanaPadre;
		// Asigna el producto a actualizar a la variable de instancia
		this.producto = producto;
		// Obtiene la instancia única del gestor de datos
		this.gestor = GestorDatos.getInstancia();
		// Llama al método que crea todos los componentes visuales
		componentes();
		// Carga los datos actuales del producto en los campos
		cargarDatosProducto();
		// Carga la lista de proveedores disponibles
		cargarProveedores();
	}

	// Método que crea y configura todos los componentes visuales de la ventana
	private void componentes() {
		// Establece el título de la ventana
		setTitle("Actualizar Producto");
		setSize(500, 600);
		// Centra la ventana respecto a la ventana padre
		setLocationRelativeTo(ventanaPadre);
		getContentPane().setLayout(null);

		// Título principal de la ventana
		JLabel lblTitulo = new JLabel("ACTUALIZAR PRODUCTO");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(152, 11, 206, 30);
		// Añade el título al contenedor principal
		getContentPane().add(lblTitulo);

		// Campo ID (solo lectura, no editable)
		// Crea etiqueta para el ID
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(50, 80, 100, 25);
		getContentPane().add(lblId);
		// Crea etiqueta que muestra el ID del producto (no editable)
		JLabel lblIdValue = new JLabel(producto.getId());
		lblIdValue.setBounds(150, 80, 285, 25);
		// Añade el valor del ID al contenedor
		getContentPane().add(lblIdValue);

		// Campo Nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(50, 120, 100, 25);
		getContentPane().add(lblNombre);
		// Crea campo de texto para el nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(150, 120, 285, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtNombre);

		// Campo Precio
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrecio.setBounds(50, 160, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblPrecio);
		txtPrecio = new JTextField();
		txtPrecio.setBounds(150, 160, 285, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtPrecio);

		// Campo Cantidad
		// Crea etiqueta para la cantidad
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCantidad.setBounds(50, 200, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblCantidad);
		// Crea campo de texto para la cantidad
		txtCantidad = new JTextField();
		txtCantidad.setBounds(150, 200, 285, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtCantidad);

		// Campo Disponible (checkbox)
		// Crea etiqueta para disponibilidad
		JLabel lblDisponible = new JLabel("Disponible:");
		lblDisponible.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDisponible.setBounds(50, 240, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblDisponible);
		// Crea checkbox para marcar disponibilidad
		chkDisponible = new JCheckBox();
		chkDisponible.setBounds(150, 240, 25, 25);
		// Añade el checkbox al contenedor
		getContentPane().add(chkDisponible);

		// Campo Categoría
		// Crea etiqueta para la categoría
		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCategoria.setBounds(50, 280, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblCategoria);
		// Crea campo de texto para la categoría
		txtCategoria = new JTextField();
		txtCategoria.setBounds(150, 280, 285, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtCategoria);

		// Campo Calorías
		// Crea etiqueta para las calorías
		JLabel lblCalorias = new JLabel("Calorías:");
		lblCalorias.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCalorias.setBounds(50, 320, 100, 25);
		getContentPane().add(lblCalorias);
		// Crea campo de texto para las calorías
		txtCalorias = new JTextField();
		txtCalorias.setBounds(150, 320, 285, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtCalorias);

		// Campo Proveedor (ComboBox)
		// Crea etiqueta para el proveedor
		JLabel lblProveedor = new JLabel("Proveedor:");
		lblProveedor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblProveedor.setBounds(50, 360, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblProveedor);
		// Crea ComboBox para seleccionar proveedor
		cmbProveedor = new JComboBox<String>();
		cmbProveedor.setBounds(150, 360, 285, 25);
		// Añade el ComboBox al contenedor
		getContentPane().add(cmbProveedor);

		// Botón Guardar
		// Crea el botón para guardar cambios
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBackground(new Color(255, 255, 255));
		btnGuardar.setBounds(50, 450, 178, 30);
		// Añade un listener para manejar el evento de clic
		btnGuardar.addActionListener(new ActionListener() {
			// Método que se ejecuta cuando se hace clic en el botón
			public void actionPerformed(ActionEvent e) {
				// Llama al método que actualiza el producto
				actualizarProducto();
			}
		});
		// Añade el botón al contenedor
		getContentPane().add(btnGuardar);

		// Botón Cancelar
		// Crea el botón para cancelar la operación
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(255, 255, 255));
		btnCancelar.setBounds(270, 450, 178, 30);
		// Añade un listener para manejar el evento de clic
		btnCancelar.addActionListener(new ActionListener() {
			// Método que se ejecuta cuando se hace clic en el botón
			public void actionPerformed(ActionEvent e) {
				// Cierra la ventana sin guardar cambios
				dispose();
			}
		});
		// Añade el botón al contenedor
		getContentPane().add(btnCancelar);
	}

	// Método que carga los datos actuales del producto en los campos del formulario
	private void cargarDatosProducto() {
		// Establece el nombre actual del producto en el campo de texto
		txtNombre.setText(producto.getNombre());
		// Convierte el precio a String y lo establece en el campo
		txtPrecio.setText(String.valueOf(producto.getPrecio()));
		// Convierte la cantidad a String y la establece en el campo
		txtCantidad.setText(String.valueOf(producto.getCantidad()));
		// Marca o desmarca el checkbox según la disponibilidad del producto
		chkDisponible.setSelected(producto.isDisponible());
		// Establece la categoría actual del producto en el campo
		txtCategoria.setText(producto.getCategoria());
		// Convierte las calorías a String y las establece en el campo
		txtCalorias.setText(String.valueOf(producto.getCalorias()));
	}

	// Método que carga todos los proveedores disponibles en el ComboBox
	private void cargarProveedores() {
		// Añade opción por defecto "Sin proveedor"
		cmbProveedor.addItem("Sin proveedor");
		// Obtiene todos los proveedores del gestor de datos
		ClaseProveedor[] proveedores = gestor.obtenerTodosLosProveedores();
		// Recorre el array de proveedores
		for (int i = 0; i < proveedores.length; i++) {
			// Obtiene el proveedor actual del bucle
			ClaseProveedor p = proveedores[i];
			// Añade el proveedor al ComboBox con formato "ID - Nombre"
			cmbProveedor.addItem(p.getId() + " - " + p.getNombre());

			// Selecciona el proveedor actual del producto si existe
			// Verifica si el producto tiene proveedor y si coincide con el actual
			if (producto.getProveedor() != null && producto.getProveedor().getId().equals(p.getId())) {
				// Selecciona este proveedor en el ComboBox (i+1 porque el índice 0 es "Sin
				// proveedor")
				cmbProveedor.setSelectedIndex(i + 1);
			}
		}
	}

	// Método principal que maneja la actualización del producto
	private void actualizarProducto() {
		// Bloque try-catch para manejar errores
		try {
			// Obtiene y limpia el texto del campo nombre
			String nombre = txtNombre.getText().trim();
			// Obtiene y limpia el texto del campo precio
			String precioStr = txtPrecio.getText().trim();
			// Obtiene y limpia el texto del campo cantidad
			String cantidadStr = txtCantidad.getText().trim();
			// Obtiene y limpia el texto del campo categoría
			String categoria = txtCategoria.getText().trim();
			// Obtiene y limpia el texto del campo calorías
			String caloriasStr = txtCalorias.getText().trim();

			// Validaciones de campos vacíos
			// Verifica si algún campo obligatorio está vacío
			if (nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty() || categoria.isEmpty()
					|| caloriasStr.isEmpty()) {
				// Muestra mensaje de error si hay campos vacíos
				JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
				// Sale del método sin continuar
				return;
			}

			// Conversión de tipos de datos
			// Convierte el precio de String a double
			double precio = Double.parseDouble(precioStr);
			// Convierte la cantidad de String a int
			int cantidad = Integer.parseInt(cantidadStr);
			// Convierte las calorías de String a int
			int calorias = Integer.parseInt(caloriasStr);
			// Obtiene el estado del checkbox de disponibilidad
			boolean disponible = chkDisponible.isSelected();

			// Validación de valores negativos
			// Verifica que los valores numéricos no sean negativos
			if (precio < 0 || cantidad < 0 || calorias < 0) {
				// Muestra mensaje de error si hay valores negativos
				JOptionPane.showMessageDialog(this, "Los valores numéricos no pueden ser negativos.");
				// Sale del método sin continuar
				return;
			}

			// Obtener proveedor seleccionado
			// Inicializa proveedor como null
			ClaseProveedor proveedor = null;
			// Obtiene el índice del proveedor seleccionado
			int proveedorIndex = cmbProveedor.getSelectedIndex();
			// Si se seleccionó un proveedor (índice > 0, porque 0 es "Sin proveedor")
			if (proveedorIndex > 0) {
				// Obtiene el texto del proveedor seleccionado
				String proveedorSeleccionado = (String) cmbProveedor.getSelectedItem();
				// Extrae solo el ID del proveedor (parte antes del " - ")
				String idProveedor = proveedorSeleccionado.split(" - ")[0];
				// Busca el proveedor en el gestor de datos usando su ID
				proveedor = gestor.buscarProveedor(idProveedor);
			}

			// Actualiza el objeto producto con los nuevos datos
			producto.actualizarProducto(nombre, precio, cantidad, disponible, categoria, calorias, proveedor);

			// Actualiza la tabla en la ventana padre
			ventanaPadre.actualizarTabla();
			// Cierra esta ventana
			dispose();

			// Intenta actualizar el producto en la base de datos
			boolean actualizado = gestor.actualizarProducto(producto);
			// Si la actualización fue exitosa
			if (actualizado) {
				// Muestra mensaje de éxito
				JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");
				// Actualiza la tabla en la ventana padre
				ventanaPadre.actualizarTabla();
				// Cierra esta ventana
				dispose();
			} else {
				// Muestra mensaje de error si falló la actualización en BD
				JOptionPane.showMessageDialog(this, "Error al actualizar el producto en la base de datos.");
			}

			// Manejo de errores de formato numérico
		} catch (NumberFormatException e) {
			// Muestra mensaje de error si hay problemas con los números
			JOptionPane.showMessageDialog(this, "Error en formato de números. Verifique los datos.");
			// Manejo de otros errores generales
		} catch (Exception e) {
			// Muestra mensaje de error general con detalles del error
			JOptionPane.showMessageDialog(this, "Error al actualizar producto: " + e.getMessage());
		}
	}
}