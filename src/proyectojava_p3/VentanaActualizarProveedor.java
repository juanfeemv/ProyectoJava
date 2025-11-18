package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaActualizarProveedor extends JFrame {
	// Variable para manejar los datos del sistema
	private GestorDatos gestor;
	// Referencia a la ventana padre que abrió esta ventana
	private VentanaProveedores ventanaPadre;
	// El proveedor que se va a actualizar
	private ClaseProveedor proveedor;
	// Campos de texto para ingresar el nombre y contacto del proveedor
	private JTextField txtNombre, txtContacto;

	// Constructor que recibe la ventana padre y el proveedor a actualizar
	public VentanaActualizarProveedor(VentanaProveedores ventanaPadre, ClaseProveedor proveedor) {
		getContentPane().setBackground(new Color(192, 192, 192));
		// Asigna la ventana padre a la variable de instancia
		this.ventanaPadre = ventanaPadre;
		// Asigna el proveedor a actualizar a la variable de instancia
		this.proveedor = proveedor;
		// Obtiene la instancia única del gestor de datos
		this.gestor = GestorDatos.getInstancia();
		// Llama al método que crea todos los componentes visuales
		componentes();
		// Carga los datos actuales del proveedor en los campos
		cargarDatosProveedor();
	}

	// Método que crea y configura todos los componentes visuales de la ventana
	private void componentes() {
		setTitle("Actualizar Proveedor");
		setSize(400, 300);
		// Centra la ventana respecto a la ventana padre
		setLocationRelativeTo(ventanaPadre);
		getContentPane().setLayout(null);

		// Título principal de la ventana
		// Crea una etiqueta para el título
		JLabel lblTitulo = new JLabel("ACTUALIZAR PROVEEDOR");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(80, 20, 240, 30);
		// Añade el título al contenedor principal
		getContentPane().add(lblTitulo);

		// Campo ID (solo lectura, no editable)
		// Crea etiqueta para el ID
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(50, 80, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblId);
		// Crea etiqueta que muestra el ID del proveedor (no editable)
		JLabel lblIdValue = new JLabel(proveedor.getId());
		lblIdValue.setBounds(150, 80, 200, 25);
		// Añade el valor del ID al contenedor
		getContentPane().add(lblIdValue);

		// Campo Nombre
		// Crea etiqueta para el nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(50, 120, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblNombre);
		// Crea campo de texto para el nombre
		txtNombre = new JTextField();
		txtNombre.setBounds(150, 120, 200, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtNombre);

		// Campo Contacto
		// Crea etiqueta para el contacto
		JLabel lblContacto = new JLabel("Contacto:");
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblContacto.setBounds(50, 160, 100, 25);
		// Añade la etiqueta al contenedor
		getContentPane().add(lblContacto);
		// Crea campo de texto para el contacto
		txtContacto = new JTextField();
		txtContacto.setBounds(150, 160, 200, 25);
		// Añade el campo de texto al contenedor
		getContentPane().add(txtContacto);

		// Botón Guardar
		// Crea el botón para guardar cambios
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBackground(new Color(255, 255, 255));
		btnGuardar.setBounds(10, 220, 184, 30);
		// Añade un listener para manejar el evento de clic
		btnGuardar.addActionListener(new ActionListener() {
			// Método que se ejecuta cuando se hace clic en el botón
			public void actionPerformed(ActionEvent e) {
				// Llama al método que actualiza el proveedor
				actualizarProveedor();
			}
		});
		// Añade el botón al contenedor
		getContentPane().add(btnGuardar);

		// Botón Cancelar
		// Crea el botón para cancelar la operación
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(255, 255, 255));
		// Posiciona el botón
		btnCancelar.setBounds(204, 220, 170, 30);
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

	// Método que carga los datos actuales del proveedor en los campos del
	// formulario
	private void cargarDatosProveedor() {
		// Establece el nombre actual del proveedor en el campo de texto
		txtNombre.setText(proveedor.getNombre());
		// Establece el contacto actual del proveedor en el campo de texto
		txtContacto.setText(proveedor.getContacto());
	}

	// Método principal que maneja la actualización del proveedor
	private void actualizarProveedor() {
		// Bloque try-catch para manejar errores
		try {
			// Obtiene y limpia el texto del campo nombre
			String nombre = txtNombre.getText().trim();
			// Obtiene y limpia el texto del campo contacto
			String contacto = txtContacto.getText().trim();

			// Validaciones de campos vacíos
			// Verifica si algún campo obligatorio está vacío
			if (nombre.isEmpty() || contacto.isEmpty()) {
				// Muestra mensaje de error si hay campos vacíos
				JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
				// Sale del método sin continuar
				return;
			}

			// Crea un nuevo objeto ClaseProveedor con el mismo ID pero datos actualizados
			proveedor = new ClaseProveedor(proveedor.getId(), nombre, contacto);

			// En un sistema real, aquí habría un método en GestorDatos para actualizar
			// Por simplicidad, eliminamos y volvemos a agregar
			// Elimina el proveedor anterior usando su ID
			gestor.eliminarProveedor(proveedor.getId());
			// Agrega el proveedor con los nuevos datos
			gestor.agregarProveedor(proveedor);

			// Muestra mensaje de éxito
			JOptionPane.showMessageDialog(this, "Proveedor actualizado exitosamente.");
			// Actualiza la tabla en la ventana padre para mostrar los cambios
			ventanaPadre.actualizarTabla();
			// Cierra esta ventana
			dispose();

			// Manejo de errores generales
		} catch (Exception e) {
			// Muestra mensaje de error general con detalles del error
			JOptionPane.showMessageDialog(this, "Error al actualizar proveedor: " + e.getMessage());
		}
	}
}