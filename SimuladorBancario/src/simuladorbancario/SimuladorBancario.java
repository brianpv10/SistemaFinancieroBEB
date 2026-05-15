/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simuladorbancario;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author User
 */
public class SimuladorBancario extends JFrame {

    JTextField txtNombre, txtCedula, txtMes;
    JTextField txtCorriente, txtAhorro, txtCDT, txtTotal;
 
    double corriente = 0;
    double ahorro = 0;
    double cdt = 0;
 
    public SimuladorBancario() {
 
        setTitle("Simulador Bancario");
        setSize(750, 450);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
 
        // ── Panel Datos Personales ──────────────────────────────────────────
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(null);
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos Personales"));
        panelDatos.setBounds(10, 10, 710, 80);
        add(panelDatos);
 
        panelDatos.add(new JLabel("Nombre:")).setBounds(20, 30, 60, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(80, 30, 140, 25);
        panelDatos.add(txtNombre);
 
        panelDatos.add(new JLabel("Cédula:")).setBounds(240, 30, 60, 25);
        txtCedula = new JTextField();
        txtCedula.setBounds(300, 30, 120, 25);
        panelDatos.add(txtCedula);
 
        panelDatos.add(new JLabel("Mes:")).setBounds(440, 30, 40, 25);
        txtMes = new JTextField();
        txtMes.setBounds(480, 30, 40, 25);
        panelDatos.add(txtMes);
 
        JButton btnNext = new JButton(">>");
        btnNext.setBounds(540, 30, 60, 25);
        panelDatos.add(btnNext);
 
        // ── Panel Saldo ─────────────────────────────────────────────────────
        JPanel panelSaldo = new JPanel();
        panelSaldo.setLayout(null);
        panelSaldo.setBorder(BorderFactory.createTitledBorder("Saldo"));
        panelSaldo.setBounds(10, 100, 710, 120);
        add(panelSaldo);
 
        panelSaldo.add(new JLabel("Saldo Corriente:")).setBounds(50, 25, 120, 25);
        txtCorriente = new JTextField("$ 0.00");
        txtCorriente.setBounds(180, 25, 120, 25);
        txtCorriente.setEditable(false);
        panelSaldo.add(txtCorriente);
 
        panelSaldo.add(new JLabel("Saldo Ahorros:")).setBounds(50, 55, 120, 25);
        txtAhorro = new JTextField("$ 0.00");
        txtAhorro.setBounds(180, 55, 120, 25);
        txtAhorro.setEditable(false);
        panelSaldo.add(txtAhorro);
 
        panelSaldo.add(new JLabel("Saldo CDT:")).setBounds(50, 85, 120, 25);
        txtCDT = new JTextField("$ 0.00");
        txtCDT.setBounds(180, 85, 120, 25);
        txtCDT.setEditable(false);
        panelSaldo.add(txtCDT);
 
        panelSaldo.add(new JLabel("Total:")).setBounds(350, 85, 50, 25);
        txtTotal = new JTextField("$ 0.00");
        txtTotal.setBounds(400, 85, 120, 25);
        txtTotal.setEditable(false);
        panelSaldo.add(txtTotal);
 
        // ── Panel Cálculos ──────────────────────────────────────────────────
        JPanel panelCalculos = new JPanel();
        panelCalculos.setLayout(null);
        panelCalculos.setBorder(BorderFactory.createTitledBorder("Cálculos"));
        panelCalculos.setBounds(10, 230, 710, 170);
        add(panelCalculos);
 
        JButton btnAbrir      = new JButton("Abrir inversión CDT");
        JButton btnCerrar     = new JButton("Cerrar inversión CDT");
        JButton btnConsignarC = new JButton("Consignar corriente");
        JButton btnRetirarC   = new JButton("Retirar corriente");
        JButton btnConsignarA = new JButton("Consignar ahorro");
        JButton btnRetirarA   = new JButton("Retirar ahorro");
        JButton btnGuardar    = new JButton("Guardar BD");
        JButton btnReporte1   = new JButton("Reporte Clientes");
        JButton btnReporte2   = new JButton("Reporte Transacciones");
        JButton btnReporte3   = new JButton("Reporte Movimientos");
        JButton btnVoucher    = new JButton("Generar Voucher");
 
        btnAbrir.setBounds(20, 30, 200, 25);
        btnCerrar.setBounds(20, 70, 200, 25);
        btnConsignarC.setBounds(240, 30, 200, 25);
        btnRetirarC.setBounds(240, 70, 200, 25);
        btnConsignarA.setBounds(460, 30, 200, 25);
        btnRetirarA.setBounds(460, 70, 200, 25);
        btnGuardar.setBounds(200, 105, 150, 25);
        btnVoucher.setBounds(360, 105, 170, 25);
        btnReporte1.setBounds(20, 135, 200, 25);
        btnReporte2.setBounds(240, 135, 200, 25);
        btnReporte3.setBounds(460, 135, 200, 25);
 
        panelCalculos.add(btnAbrir);
        panelCalculos.add(btnCerrar);
        panelCalculos.add(btnConsignarC);
        panelCalculos.add(btnRetirarC);
        panelCalculos.add(btnConsignarA);
        panelCalculos.add(btnRetirarA);
        panelCalculos.add(btnGuardar);
        panelCalculos.add(btnVoucher);
        panelCalculos.add(btnReporte1);
        panelCalculos.add(btnReporte2);
        panelCalculos.add(btnReporte3);
 
        // ── EVENTOS ─────────────────────────────────────────────────────────
 
        // CORRECCIÓN: btnNext busca cliente por cédula y carga sus saldos
        btnNext.addActionListener(e -> {
            String cedula = txtCedula.getText().trim();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa una cédula primero.");
                return;
            }
            try (Connection conn = Conexion.getConexion()) {
                String sql = """
                    SELECT c.nombre, cu.tipo, cu.saldo
                    FROM cliente c
                    JOIN cuenta cu ON c.id_cliente = cu.id_cliente
                    WHERE c.cedula = ?
                """;
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, cedula);
                ResultSet rs = ps.executeQuery();
 
                boolean encontrado = false;
                corriente = 0; ahorro = 0; cdt = 0;
 
                while (rs.next()) {
                    encontrado = true;
                    txtNombre.setText(rs.getString("nombre"));
                    String tipo  = rs.getString("tipo");
                    double saldo = rs.getDouble("saldo");
                    if (tipo.equals("corriente")) corriente = saldo;
                    else if (tipo.equals("ahorro")) ahorro   = saldo;
                    else if (tipo.equals("cdt"))    cdt      = saldo;
                }
 
                if (encontrado) {
                    actualizar();
                    JOptionPane.showMessageDialog(this, "Cliente cargado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado en la BD.");
                }
 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
 
        btnConsignarC.addActionListener(e -> {
            double m = pedirMonto("Monto a consignar en corriente:");
            if (m > 0) {
                corriente += m;
                guardarTransaccion("Consignar corriente", m);
                actualizar();
            }
        });
 
        btnRetirarC.addActionListener(e -> {
            double m = pedirMonto("Monto a retirar de corriente:");
            if (m > 0 && m <= corriente) {
                corriente -= m;
                guardarTransaccion("Retirar corriente", m);
                actualizar();
            } else if (m > corriente) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente en cuenta corriente.");
            }
        });
 
        btnConsignarA.addActionListener(e -> {
            double m = pedirMonto("Monto a consignar en ahorros:");
            if (m > 0) {
                ahorro += m;
                guardarTransaccion("Consignar ahorro", m);
                actualizar();
            }
        });
 
        btnRetirarA.addActionListener(e -> {
            double m = pedirMonto("Monto a retirar de ahorros:");
            if (m > 0 && m <= ahorro) {
                ahorro -= m;
                guardarTransaccion("Retirar ahorro", m);
                actualizar();
            } else if (m > ahorro) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente en cuenta de ahorros.");
            }
        });
 
        btnAbrir.addActionListener(e -> {
            double m = pedirMonto("Monto para CDT:");
            if (m > 0) {
                cdt = m;
                guardarTransaccion("Abrir CDT", m);
                actualizar();
            }
        });
 
        btnCerrar.addActionListener(e -> {
            if (cdt <= 0) {
                JOptionPane.showMessageDialog(this, "No hay CDT abierto.");
                return;
            }
            corriente += cdt;
            guardarTransaccion("Cerrar CDT", cdt);
            cdt = 0;
            actualizar();
        });
 
        btnGuardar.addActionListener(e -> guardarBD());
 
        btnReporte1.addActionListener(e -> reporteTotalPorCliente());
        btnReporte2.addActionListener(e -> reporteTransacciones());
        btnReporte3.addActionListener(e -> reporteMovimientos());
        btnVoucher.addActionListener(e -> generarVoucher());
 
        setVisible(true);
    }
 
    // ── Helpers ─────────────────────────────────────────────────────────────
 
    double pedirMonto(String msg) {
        try {
            String input = JOptionPane.showInputDialog(msg);
            if (input == null || input.trim().isEmpty()) return -1;
            return Double.parseDouble(input.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
            return -1;
        }
    }
 
    // CORRECCIÓN: formato con 2 decimales
    void actualizar() {
        txtCorriente.setText(String.format("$ %.2f", corriente));
        txtAhorro.setText(String.format("$ %.2f", ahorro));
        txtCDT.setText(String.format("$ %.2f", cdt));
        txtTotal.setText(String.format("$ %.2f", corriente + ahorro + cdt));
    }
 
    // CORRECCIÓN: try-with-resources para cerrar conexión siempre
    void guardarTransaccion(String tipo, double monto) {
        try (Connection conn = Conexion.getConexion()) {
 
            String tipoCuenta = tipo.toLowerCase().contains("corriente") ? "corriente"
                    : tipo.toLowerCase().contains("ahorro") ? "ahorro" : "cdt";
 
            String sql = """
                SELECT cu.id_cuenta
                FROM cuenta cu
                JOIN cliente c ON cu.id_cliente = c.id_cliente
                WHERE cu.tipo = ? AND c.cedula = ?
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tipoCuenta);
            ps.setString(2, txtCedula.getText());
            ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                int idCuenta = rs.getInt(1);
                String insert = "INSERT INTO transaccion(id_cuenta, tipo, monto, fecha) VALUES (?, ?, ?, NOW())";
                PreparedStatement ps2 = conn.prepareStatement(insert);
                ps2.setInt(1, idCuenta);
                ps2.setString(2, tipo);
                ps2.setDouble(3, monto);
                ps2.executeUpdate();
            }
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar transacción: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    // CORRECCIÓN: evita cuentas duplicadas con UPDATE si ya existen
    void guardarBD() {
        try (Connection conn = Conexion.getConexion()) {
 
            int id = -1;
 
            // Buscar o crear cliente
            String buscar = "SELECT id_cliente FROM cliente WHERE cedula = ?";
            PreparedStatement psBuscar = conn.prepareStatement(buscar);
            psBuscar.setString(1, txtCedula.getText());
            ResultSet rs = psBuscar.executeQuery();
 
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                String insert = "INSERT INTO cliente(nombre, cedula) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, txtNombre.getText());
                ps.setString(2, txtCedula.getText());
                ps.executeUpdate();
                ResultSet rs2 = ps.getGeneratedKeys();
                if (rs2.next()) id = rs2.getInt(1);
            }
 
            // CORRECCIÓN: actualizar si ya existe la cuenta, insertar si no
            String checkCuenta = "SELECT id_cuenta FROM cuenta WHERE id_cliente = ? AND tipo = ?";
            String updateCuenta = "UPDATE cuenta SET saldo = ? WHERE id_cliente = ? AND tipo = ?";
            String insertCuenta = "INSERT INTO cuenta(id_cliente, tipo, saldo) VALUES (?, ?, ?)";
 
            String[] tipos  = {"corriente", "ahorro", "cdt"};
            double[] saldos = {corriente, ahorro, cdt};
 
            for (int i = 0; i < tipos.length; i++) {
                PreparedStatement psCheck = conn.prepareStatement(checkCuenta);
                psCheck.setInt(1, id);
                psCheck.setString(2, tipos[i]);
                ResultSet rsCheck = psCheck.executeQuery();
 
                if (rsCheck.next()) {
                    // Ya existe → actualizar
                    PreparedStatement psUpdate = conn.prepareStatement(updateCuenta);
                    psUpdate.setDouble(1, saldos[i]);
                    psUpdate.setInt(2, id);
                    psUpdate.setString(3, tipos[i]);
                    psUpdate.executeUpdate();
                } else {
                    // No existe → insertar
                    PreparedStatement psInsert = conn.prepareStatement(insertCuenta);
                    psInsert.setInt(1, id);
                    psInsert.setString(2, tipos[i]);
                    psInsert.setDouble(3, saldos[i]);
                    psInsert.executeUpdate();
                }
            }
 
            JOptionPane.showMessageDialog(this, "Guardado correctamente.");
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error BD: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    // ── Reportes ─────────────────────────────────────────────────────────────
 
    void reporteTotalPorCliente() {
        try (Connection conn = Conexion.getConexion()) {
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT c.nombre, SUM(cu.saldo) FROM cliente c " +
                "JOIN cuenta cu ON c.id_cliente = cu.id_cliente GROUP BY c.nombre"
            );
            StringBuilder datos = new StringBuilder("TOTAL POR CLIENTE\n\n");
            while (rs.next()) {
                datos.append(rs.getString(1))
                     .append(" → ")
                     .append(String.format("$ %.2f", rs.getDouble(2)))
                     .append("\n");
            }
            JOptionPane.showMessageDialog(this, datos.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reporte clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    void reporteTransacciones() {
        try (Connection conn = Conexion.getConexion()) {
            // CORRECCIÓN: filtra por cliente actual usando la cédula
            String sql = """
                SELECT t.tipo, SUM(t.monto)
                FROM transaccion t
                JOIN cuenta cu ON t.id_cuenta = cu.id_cuenta
                JOIN cliente c ON cu.id_cliente = c.id_cliente
                WHERE c.cedula = ?
                GROUP BY t.tipo
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtCedula.getText());
            ResultSet rs = ps.executeQuery();
 
            StringBuilder datos = new StringBuilder("TRANSACCIONES — " + txtNombre.getText() + "\n\n");
            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                datos.append(rs.getString(1))
                     .append(" → ")
                     .append(String.format("$ %.2f", rs.getDouble(2)))
                     .append("\n");
            }
            if (!hayDatos) datos.append("Sin transacciones registradas.");
            JOptionPane.showMessageDialog(this, datos.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reporte transacciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    void reporteMovimientos() {
        try (Connection conn = Conexion.getConexion()) {
            // CORRECCIÓN: filtra por cliente actual usando la cédula
            String sql = """
                SELECT c.nombre, t.tipo, COUNT(t.id_transaccion)
                FROM cliente c
                JOIN cuenta cu ON c.id_cliente = cu.id_cliente
                JOIN transaccion t ON cu.id_cuenta = t.id_cuenta
                WHERE c.cedula = ?
                GROUP BY c.nombre, t.tipo
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtCedula.getText());
            ResultSet rs = ps.executeQuery();
 
            StringBuilder datos = new StringBuilder("MOVIMIENTOS — " + txtNombre.getText() + "\n\n");
            boolean hayDatos = false;
            while (rs.next()) {
                hayDatos = true;
                datos.append(rs.getString(2))   // tipo de transacción
                     .append(" → ")
                     .append(rs.getInt(3))
                     .append(" movimiento(s)\n");
            }
            if (!hayDatos) datos.append("Sin movimientos registrados.");
            JOptionPane.showMessageDialog(this, datos.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reporte movimientos: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    // ── Voucher ───────────────────────────────────────────────────────────────
 
    void generarVoucher() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
 
        if (cedula.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carga un cliente primero con el botón >>.");
            return;
        }
 
        try (Connection conn = Conexion.getConexion()) {
 
            // Obtener transacciones del día de hoy
            String sql = """
                SELECT t.tipo, t.monto, t.fecha
                FROM transaccion t
                JOIN cuenta cu ON t.id_cuenta = cu.id_cuenta
                JOIN cliente c ON cu.id_cliente = c.id_cliente
                WHERE c.cedula = ?
                ORDER BY t.fecha DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();
 
            // Construir el voucher
            StringBuilder v = new StringBuilder();
            v.append("========================================\n");
            v.append("         BANCO SIMULADOR - VOUCHER      \n");
            v.append("========================================\n");
            v.append(String.format("  Cliente : %s%n", nombre));
            v.append(String.format("  Cedula  : %s%n", cedula));
            v.append(String.format("  Mes     : %s%n", txtMes.getText()));
            v.append("----------------------------------------\n");
            v.append("  SALDOS ACTUALES\n");
            v.append(String.format("  Corriente : %s%n", txtCorriente.getText()));
            v.append(String.format("  Ahorros   : %s%n", txtAhorro.getText()));
            v.append(String.format("  CDT       : %s%n", txtCDT.getText()));
            v.append(String.format("  TOTAL     : %s%n", txtTotal.getText()));
            v.append("----------------------------------------\n");
            v.append("  TRANSACCIONES REGISTRADAS\n\n");
 
            boolean hayTrans = false;
            while (rs.next()) {
                hayTrans = true;
                v.append(String.format("  %-22s $ %10.2f%n",
                        rs.getString("tipo"),
                        rs.getDouble("monto")));
                v.append(String.format("  Fecha: %s%n", rs.getString("fecha")));
                v.append("  ......................................\n");
            }
 
            if (!hayTrans) {
                v.append("  Sin transacciones registradas.\n");
            }
 
            v.append("========================================\n");
            v.append("       Gracias por usar el sistema      \n");
            v.append("========================================\n");
 
            // Mostrar en ventana con fuente monoespaciada
            JTextArea area = new JTextArea(v.toString());
            area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new java.awt.Dimension(420, 350));
 
            JOptionPane.showMessageDialog(this, scroll, "Voucher - " + nombre, JOptionPane.PLAIN_MESSAGE);
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar voucher: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //solo para prueba
        //new SimuladorBancario();
    }
}
