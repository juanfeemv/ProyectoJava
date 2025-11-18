package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAgregarProveedor extends JFrame {
	private GestorDatos gestor;
	private VentanaProveedores ventanaPadre; // Referencia a la ventana padre que abrió esta ventana
	private JTextField txtId, txtNombre, txtContacto; // Campos de texto para ingresar datos del proveedor

	public VentanaAgregarProveedor(VentanaProveedores ventanaPadre) { // Constructor que recibe la ventana padre
		this.ventanaPadre = ventanaPadre; // Asigna la referencia de la ventana padre
		this.gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Agregar Proveedor");
		setSize(401, 302);
		setLocationRelativeTo(ventanaPadre); // Centra la ventana respecto a la ventana padre
		getContentPane().setLayout(null);

		// Título
		JLabel lblTitulo = new JLabel("AGREGAR NUEVO PROVEEDOR"); // Crea etiqueta para el título
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(68, 24, 259, 30);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Campos del formulario
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(50, 80, 100, 25);
		getContentPane().add(lblId); // Agrega la etiqueta al contenedor
		txtId = new JTextField(); // Crea campo de texto para el ID del proveedor
		txtId.setBounds(150, 80, 200, 25);
		getContentPane().add(txtId); // Agrega el campo de texto al contenedor

		JLabel lblNombre = new JLabel("Nombre:"); // Crea etiqueta para el campo Nombre
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(50, 120, 100, 25);
		getContentPane().add(lblNombre); // Agrega la etiqueta al contenedor
		txtNombre = new JTextField(); // Crea campo de texto para el nombre del proveedor
		txtNombre.setBounds(150, 120, 200, 25);
		getContentPane().add(txtNombre); // Agrega el campo de texto al contenedor

		JLabel lblContacto = new JLabel("Contacto:"); // Crea etiqueta para el campo Contacto
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblContacto.setBounds(50, 160, 100, 25);
		getContentPane().add(lblContacto); // Agrega la etiqueta al contenedor
		txtContacto = new JTextField(); // Crea campo de texto para la información de contacto
		txtContacto.setBounds(150, 160, 200, 25);
		getContentPane().add(txtContacto); // Agrega el campo de texto al contenedor

		// Botones
		JButton btnGuardar = new JButton("Guardar"); // Crea botón para guardar el proveedor
		btnGuardar.setBounds(10, 220, 180, 30);
		btnGuardar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				guardarProveedor(); // Llama al método para guardar el proveedor
			}
		});
		getContentPane().add(btnGuardar); // Agrega el botón al contenedor

		JButton btnCancelar = new JButton("Cancelar"); // Crea botón para cancelar la operación
		btnCancelar.setBounds(200, 220, 175, 30);
		btnCancelar.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				dispose(); // Cierra la ventana actual
			}
		});
		getContentPane().add(btnCancelar); // Agrega el botón al contenedor
	}

	private void guardarProveedor() { // Método para validar y guardar el nuevo proveedor
		try { // Bloque try-catch para manejar excepciones
			String id = txtId.getText().trim(); // Obtiene el ID eliminando espacios en blanco
			String nombre = txtNombre.getText().trim(); // Obtiene el nombre eliminando espacios en blanco
			String contacto = txtContacto.getText().trim(); // Obtiene el contacto eliminando espacios en blanco

			// Validaciones
			if (id.isEmpty() || nombre.isEmpty() || contacto.isEmpty()) { // Verifica si algún campo está vacío
				JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios."); // Muestra mensaje de error
				return; // Sale del método sin guardar
			}

			if (id.contains(" ")) { // Verifica si el ID contiene espacios
				JOptionPane.showMessageDialog(this, "El ID no debe contener espacios."); // Muestra mensaje de error
				return; // Sale del método sin guardar
			}

			ClaseProveedor proveedor = new ClaseProveedor(id, nombre, contacto); // Crea nuevo objeto ClaseProveedor

			if (gestor.agregarProveedor(proveedor)) { // Intenta agregar el proveedor al gestor
				JOptionPane.showMessageDialog(this, "Proveedor agregado exitosamente."); // Muestra mensaje de éxito
				ventanaPadre.actualizarTabla(); // Actualiza la tabla en la ventana padre
				dispose(); // Cierra la ventana actual
			} else { // Si no se pudo agregar el proveedor
				JOptionPane.showMessageDialog(this, "Error: Ya existe un proveedor con este ID."); // Muestra mensaje de
																									// error
			}

		} catch (Exception e) { // Captura cualquier excepción
			JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + e.getMessage()); // Muestra mensaje de
																									// error con
																									// detalles
		}
	}
}
