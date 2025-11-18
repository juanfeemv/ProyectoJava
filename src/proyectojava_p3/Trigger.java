package proyectojava_p3;

import javax.swing.*;

import java.awt.*;

import java.sql.*;

public class Trigger extends JFrame {
	private JTextField emailField;
	private JLabel resultLabel;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/dominiosvalidos";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "nevado2005";

	public Trigger() {
		setTitle("Validador de Emails");
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(3, 1));
		emailField = new JTextField();
		JButton submitButton = new JButton("Verificar Email");
		resultLabel = new JLabel("", SwingConstants.CENTER);
		panel.add(new JLabel("Introduce un email:"));
		panel.add(emailField);
		panel.add(submitButton);
		add(panel, BorderLayout.CENTER);
		add(resultLabel, BorderLayout.SOUTH);

		submitButton.addActionListener(e -> insertarEmail());

	}

	private void insertarEmail() {

		String email = emailField.getText().trim();
		if (email.isEmpty()) {
			resultLabel.setText("Por favor, introduce un email.");
			return;

		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			String sql = "INSERT INTO usuarios (email) VALUES (?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, email);

			stmt.executeUpdate();

			// Verificar si fue validado

			Statement query = conn.createStatement();

			ResultSet rs = query.executeQuery("SELECT email_verificado FROM usuarios ORDER BY id DESC LIMIT 1");

			if (rs.next()) {

				String estado = rs.getString("email_verificado");

				resultLabel.setText("Email verificado: " + estado);

				// Si el email es válido, ejecutar Main y cerrar ventana

				if ("SI".equals(estado)) {

					// Ejecutar la clase Main

					Main.main(new String[0]);

					// Cerrar la ventana actual

					this.dispose();

				}

			}

		} catch (SQLException ex) {

			resultLabel.setText("Error en BD: " + ex.getMessage());

		}

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			new Trigger().setVisible(true);

		});

	}

}