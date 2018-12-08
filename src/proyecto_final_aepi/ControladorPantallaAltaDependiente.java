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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaAltaDependiente implements ActionListener{

    PantallaAltaDependiente pantalla;
    
    public ControladorPantallaAltaDependiente(PantallaAltaDependiente p)
    {
        this.pantalla = p;
    }
    
    
    public void actionPerformed(ActionEvent e) {
        
        String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
            case "Aceptar": 
                            this.darAltaDependiente();
                            break;
            
            case "Salir":
                         this.pantalla.dispose();
                         new PantallaAcceso();
                         break;
        }
       
    }
    
    private void darAltaDependiente()
    {
        String id = this.pantalla.textoId.getText().trim();
        String pass = this.pantalla.textoPass.getText().trim();
        
        if( id.equalsIgnoreCase("") || pass.equalsIgnoreCase(""))
        {
          JOptionPane.showMessageDialog(this.pantalla, "Alguno de los campos está vacío", "Aviso", JOptionPane.WARNING_MESSAGE); 

        }
        else
        {
            this.altaDependienteBD(id, pass);
        }
    }
    
    private void altaDependienteBD(String id, String pass)
    {
        Connection con = null; 
        try {
            //vamos a acceder a la base de dato.
            //primero establecemos la conexion
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            con.setAutoCommit(false);
            
            //llamamos al procedimiento almacenado de alta de dependientes
            CallableStatement cstmt = con.prepareCall ("{call altaDependiente (?,?)}");
            
            cstmt.setString(1,id);
            cstmt.setString(2,pass);
            
            //se ejecuta el procedimiento
            cstmt.executeUpdate();      
            
            //se ha ejecutado correctamente el procedimiento y procedemos a hacer el commit
            con.commit();
            con.setAutoCommit(true);
            
            //cerramos la conexión
            cstmt.close();
            con.close();
            
            JOptionPane.showMessageDialog(this.pantalla, "Nuevo dependiente dado de alta correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 
            
        
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error en la conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE); 
        } catch (SQLException ex) {
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
    }
    
}
