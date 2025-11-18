package proyectojava_p3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
	private GestorDatos gestor;

	public VentanaPrincipal() {
		getContentPane().setBackground(new Color(215, 215, 215)); // Constructor de la ventana principal
		gestor = GestorDatos.getInstancia(); // Obtiene la instancia única del gestor de datos
		componentes(); // Llama al método para inicializar los componentes gráficos
	}

	private void componentes() { // Método para configurar y agregar todos los componentes gráficos
		setTitle("Sistema de Gestión de Restaurante"); // Establece el título de la ventana principal
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura que la aplicación se cierre al cerrar la ventana
		setLocationRelativeTo(null); // Centra la ventana en la pantalla
		getContentPane().setLayout(null);
		// Título
		JLabel lblTitulo = new JLabel("GESTIÓN DE RESTAURANTE"); // Crea etiqueta para el título principal
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitulo.setBounds(10, 68, 764, 40);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(lblTitulo); // Agrega la etiqueta al contenedor principal

		// Botones principales
		JButton btnProductos = new JButton("Gestionar Productos"); // Crea botón para gestionar productos
		btnProductos.setBackground(new Color(255, 255, 255));
		btnProductos.setBounds(211, 142, 374, 80);
		btnProductos.setFont(new Font("Arial", Font.PLAIN, 14));
		btnProductos.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			@Override
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				new VentanaProductos().setVisible(true); // Crea y muestra la ventana de gestión de productos
			}
		});
		getContentPane().add(btnProductos); // Agrega el botón al contenedor

		JButton btnProveedores = new JButton("Gestionar Proveedores"); // Crea botón para gestionar proveedores
		btnProveedores.setBackground(new Color(255, 255, 255));
		btnProveedores.setIcon(null); // Establece que no tenga icono
		btnProveedores.setBounds(211, 246, 374, 80);
		btnProveedores.setFont(new Font("Arial", Font.PLAIN, 14));
		btnProveedores.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			@Override
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				new VentanaProveedores().setVisible(true); // Crea y muestra la ventana de gestión de proveedores
			}
		});
		getContentPane().add(btnProveedores); // Agrega el botón al contenedor

		JButton btnSalir = new JButton("Salir"); // Crea botón para salir de la aplicación
		btnSalir.setBackground(new Color(255, 255, 255));
		btnSalir.setBounds(211, 350, 374, 80);
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSalir.addActionListener(new ActionListener() { // Agrega listener para manejar el clic
			@Override
			public void actionPerformed(ActionEvent e) { // Método que se ejecuta al hacer clic
				System.exit(0); // Termina la ejecución de la aplicación
			}
		});
		getContentPane().add(btnSalir); // Agrega el botón al contenedor

		// Información del sistema
		JLabel lblInfo = new JLabel("Sistema desarrollado para gestión de restaurantes - Hecho por Juan Felipe Mena©");
		lblInfo.setBounds(10, 520, 467, 30);
		lblInfo.setHorizontalAlignment(JLabel.CENTER);
		lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
		getContentPane().add(lblInfo); // Agrega la etiqueta al contenedor
	}
}
