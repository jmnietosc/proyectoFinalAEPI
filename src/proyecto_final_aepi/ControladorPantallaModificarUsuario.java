/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaModificarUsuario implements ActionListener {

   PantallaModificarUsuario pantalla;
    
   public ControladorPantallaModificarUsuario(PantallaModificarUsuario p)
   {
       pantalla = p;
       this.pintarCampos();
   }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        String boton = e.getActionCommand();
        
        switch(boton)
        {
            case "Aceptar": this.modificarUsuario();
                            break;
            case "Volver": 
                            new PantallaDetalleUsuario (this.pantalla.idUsuario);
                            this.pantalla.dispose();
                            break;
            case "Menu":
                            new PantallaMenu();
                            this.pantalla.dispose();
                            break;
        }
    }
    
    private void pintarCampos()
    {
         int idUsuario = this.pantalla.idUsuario;
        
         try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            ResultSet rs;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            CallableStatement cstmt = con.prepareCall ("{call getDetalleUsuario(?)}");
            cstmt.setInt(1,idUsuario);
            
            rs = cstmt.executeQuery();  
            
            //aunque solo va a devolver un registro hay que hacer el while , si no da error al ejecutar
            while (rs.next())
            {
                this.pantalla.textoNombre.setText(rs.getString(2));
                this.pantalla.textoApellidos.setText(rs.getString(3));
                this.pantalla.textoTelefono.setText(rs.getString(4));
                this.pantalla.textoEmail.setText(rs.getString(5));
                this.pantalla.textoDireccion.setText(rs.getString(6));
            }
            
            //cerramos
            rs.close();
            cstmt.close();
            con.close();
            
        } catch (ClassNotFoundException ex ) {
            System.out.println(ex.getMessage());
       
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    private void modificarUsuario()
    {
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
                //llamamos al procedimiento almacenado de modificar usuario
                CallableStatement cstmt = con.prepareCall ("{call modificarUsuario (?,?,?,?,?,?)}");
                
                cstmt.setInt(1, this.pantalla.idUsuario);
                
                //pasamos el segundo parametro, nombre
                cstmt.setString(2,nombre);
                //pasamos el tecer parametro,apellidos
                cstmt.setString(3,apellidos);
                //pasamos el cuarto parametro, telefono
                cstmt.setString(4,telefono);

                cstmt.setString(5,email);

                cstmt.setString(6,direccion);

                cstmt.executeUpdate();
                
                //commit y cerramos conexiones
                con.commit();
                con.setAutoCommit(true);
                cstmt.close();
                con.close();
                
                //sacamos mensaje por pantalla
                JOptionPane.showMessageDialog(this.pantalla, "Usuario modificado correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 
           
                } catch (ClassNotFoundException ex) {
                    
                    JOptionPane.showMessageDialog(this.pantalla, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                
                } catch (SQLException ex) 
                {
                    try 
                    {
                        con.rollback();
                        JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error realizando la modificación", "Error", JOptionPane.ERROR_MESSAGE); 
                    } 
                    catch (SQLException e1) 
                    {
                        System.err.println("Error");
                    } 
                }  
            }else
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
}
