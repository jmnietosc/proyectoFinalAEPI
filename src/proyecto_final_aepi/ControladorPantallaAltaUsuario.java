/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaAltaUsuario implements ActionListener , KeyListener{

    PantallaAltaUsuario pantalla;
    
    public ControladorPantallaAltaUsuario(PantallaAltaUsuario p)
    {
        this.pantalla = p;
    }
    
    public void actionPerformed(ActionEvent e) {
        
         String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
            case "Aceptar": 
                            this.insertarUsuario();;
                            break;
            
            case "Limpiar": 
                            this.pantalla.textoId.setText("");
                            this.pantalla.textoNombre.setText("");
                            this.pantalla.textoApellidos.setText("");
                            this.pantalla.textoTelefono.setText("");
                            this.pantalla.textoEmail.setText("");
                            this.pantalla.textoDireccion.setText("");
                            
                            break;
            
            case "Volver": 
                             this.pantalla.dispose();
                             break;   
                
        }
       
    }
    
    private void insertarUsuario()
    {
        String id = this.pantalla.textoId.getText();
        String nombre = this.pantalla.textoNombre.getText();
        String apellidos = this.pantalla.textoApellidos.getText();
        String telefono = this.pantalla.textoTelefono.getText();
        String email = this.pantalla.textoEmail.getText();
        String direccion = this.pantalla.textoDireccion.getText();
        Connection con = null;
        
        if(!nombre.equals("") && !apellidos.equals("") && !telefono.equals("") 
            && !email.equals("")&& !direccion.equals("") )
        {
            if(this.validarEmail(email))
            {
               try 
               {
                //vamos a acceder a la base de dato.
                //primero establecemos la conexion
            
                Class.forName("com.mysql.cj.jdbc.Driver");
            
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
                
                //transaccion 
                 con.setAutoCommit(false);
                
                //llamamos al procedimiento almacenado de alta de usuarios
                CallableStatement cstmt = con.prepareCall ("{call altaUsuario (?,?,?,?,?,?)}");
  
                //pasamos el primer campo     
                if(id.equals(""))
                {
                    cstmt.setNull(1, Types.INTEGER);
                }
                else
                {
                    cstmt.setInt(1, Integer.valueOf(id));
                }
                //pasamos el segundo parametro, nombre
                cstmt.setString(2,nombre);
                //pasamos el tecer parametro,apellidos
                cstmt.setString(3,apellidos);
                //pasamos el cuarto parametro, telefono
                cstmt.setString(4,telefono);
                //pasamos el quinto parametro, email
                cstmt.setString(5,email);
                //pasamos el sexto parametro, direccion
                cstmt.setString(6,direccion);
                //se ejecuta el procedimiento;
                cstmt.executeUpdate();

                //si va todo bien se limpia los campos y se lanza una ventana de mensaje
                this.pantalla.textoId.setText("");
                this.pantalla.textoNombre.setText(""); 
                this.pantalla.textoApellidos.setText("");
                this.pantalla.textoTelefono.setText("");
                this.pantalla.textoEmail.setText("");
                this.pantalla.textoDireccion.setText("");
                JOptionPane.showMessageDialog(this.pantalla, "Usuario dado de alta correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 

                //commit y cerramos conexiones
                con.commit();
                con.setAutoCommit(true);
                cstmt.close();
                con.close(); 
           
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this.pantalla, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                } catch (SQLException ex) 
                {
                    try 
                    {
                        con.rollback();   
                        JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error realizando el alta", "Error", JOptionPane.ERROR_MESSAGE); 
                    } 
                    catch (SQLException e1) 
                    {
                        System.err.println("Error");
                    }    
                } 
               
            } else
            {
              JOptionPane.showMessageDialog(this.pantalla, "Formato de email inválido", "Error", JOptionPane.ERROR_MESSAGE); 
            }
      
        }
        else
        {
           JOptionPane.showMessageDialog(this.pantalla, "Alguno de los campos está vacío", "Aviso", JOptionPane.WARNING_MESSAGE); 
        }
       
    }
    
    private boolean validarEmail(String email)
    {
        //expresion regular con el patron de email a comprobar
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {       
        char caracter = e.getKeyChar();          
        if(caracter < '0' || caracter > '9')
        {
            e.consume();  // ignorar el evento de teclado            
        }    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
