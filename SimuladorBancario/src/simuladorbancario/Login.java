/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simuladorbancario;
//importar las librerias
import javax.swing.*;
/**
 *
 * @author USER
 */
public class Login extends JFrame{
    //declarando variables
    JTextField txtusuario;
    JPasswordField txtClave;
    JButton btnLogin;
    
    public Login(){
        setTitle("Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //declaramos usuario
        JLabel lbl1=new JLabel("Usuario: ");
        lbl1.setBounds(50, 50, 100, 25);
        add(lbl1);
        
        txtusuario=new JTextField();
        txtusuario.setBounds(150, 50, 150, 25);
        add(txtusuario);
        
        //***************
        //declaramos clave
        JLabel lbl2=new JLabel("Clave: ");
        lbl2.setBounds(50, 80, 100, 25);
        add(lbl2);
        
        txtClave=new JPasswordField("");
        txtClave.setBounds(150, 80, 150, 25);
        add(txtClave);
        
        //*************
        //declaramos boton
        btnLogin=new JButton("Ingresar");
        btnLogin.setBounds(150, 120, 150, 25);
        add(btnLogin);
        
        btnLogin.addActionListener(e -> btnLoginActionPerformed());
          
    }
    private void btnLoginActionPerformed() {
        //throw new UnsupportedOperationException("Not supported yet.");
        String user=txtusuario.getText();
        String pass=txtClave.getText();
        //declarando condicion
        if(user.equals("admin") && pass.equals("1234")){
            JOptionPane.showMessageDialog(this, "Bienvido al sistema");
            //declarando mi siguiente formulario
            
            new SimuladorBancario().setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "credenciales incorrectas");
        }
    }
}
