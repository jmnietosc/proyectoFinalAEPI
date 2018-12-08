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
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaModificarLibro implements ActionListener{
    
    PantallaModificarLibro pantalla;
    
    public ControladorPantallaModificarLibro (PantallaModificarLibro  p)
    {
        this.pantalla = p;
    }
   
    public void actionPerformed(ActionEvent e) {
        
        String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
             case "Modificar": this.modificarLibro();
                             break;
             case "Tomar": 
                            String idUsuarioS = JOptionPane.showInputDialog("Introduce el identificador del usuario");
                            int idUsuario = Integer.valueOf(idUsuarioS);
                            this.tomarLibro(idUsuario);
                            break;   
             
             case "Devolver": 
                             this.devolverLibro();               
                             break;   
            
             case "Deshacer": 
                             this.pantalla.textoTitulo.setText(this.pantalla.tituloInicio);
                             this.pantalla.textoAutor.setText(this.pantalla.autorInicio);
                             this.pantalla.textoISBN.setText(this.pantalla.ISBNInicio);
                             break;  

             
             case "Volver":  new PantallaListarLibros ();
                             this.pantalla.dispose();
                             break;   
        }
       
    }
    
    private void modificarLibro()
    {
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
                     
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                         + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
             
            
            
            CallableStatement cstmt = con.prepareCall ("{call modificarLibro(?,?,?,?)}");
            
            con.setAutoCommit(false);
            //pasamos el id del libro seleccionado al procedimiento almacenado
            int id = this.pantalla.idLibro;
            String nuevoTitulo = this.pantalla.textoTitulo.getText();
            String nuevoAutor = this.pantalla.textoAutor.getText();
            String nuevoIsbn = this.pantalla.textoISBN.getText();
            
            cstmt.setInt(1,id);
            cstmt.setString(2,nuevoTitulo);
            cstmt.setString(3,nuevoAutor);
            cstmt.setString(4,nuevoIsbn);
            
            cstmt.executeUpdate();
            
            con.commit();
            con.setAutoCommit(true);
            cstmt.close();
            con.close();
            JOptionPane.showMessageDialog(this.pantalla, "Modificación realizada correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 
          
         } catch (ClassNotFoundException ex) 
         {
            Logger.getLogger(ControladorPantallaModificarLibro.class.getName()).log(Level.SEVERE, null, ex);
         }
         catch (SQLException ex) {
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
    }
    
    private void devolverLibro()
    {
        int resultado;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
            CallableStatement cstmt = con.prepareCall ("{call devolverLibro(?)}");

            //pasamos el id del libro seleccionado al procedimiento almacenado
            cstmt.setInt(1,this.pantalla.idLibro);    
            resultado =  cstmt.executeUpdate();
            
            if(resultado > 0)
            {
                JOptionPane.showMessageDialog(this.pantalla, "Libro devuelto correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 
            }
  
            cstmt.close();
            con.close();
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaModificarLibro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorPantallaModificarLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tomarLibro(int idUsuario)
    {
        int resultado;
        Connection con = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");     
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
            
            CallableStatement cstmt0 = con.prepareCall ("{call getNumeroLibrosUsuario(?,?)}");

            //pasamo el id del libro seleccionado al procedimiento almacenado
            cstmt0.setInt(1,idUsuario);
            // parametros de salida del procedimiento almacenado
            cstmt0.registerOutParameter(2, java.sql.Types.INTEGER); 
            
            cstmt0.executeQuery();
            
            int numLibros = cstmt0.getInt(2);
            //limitamos el numero de libros que puede tomar un usuario
            if(numLibros > 3)
            {
                cstmt0.close();
                JOptionPane.showMessageDialog(this.pantalla, "El usuario " + idUsuario+" no puede tomar prestado más libros", "Info", JOptionPane.INFORMATION_MESSAGE); 
            }
            else
            {
                con.setAutoCommit(false);

                CallableStatement cstmt = con.prepareCall ("{call tomarLibro(?,?,?,?)}");

                cstmt.setNull(1,Types.INTEGER);    
                //pasamos el id del libro seleccionado al procedimiento almacenado
                cstmt.setInt(2,idUsuario);
                cstmt.setInt(3,this.pantalla.idLibro);

                cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
                cstmt.executeUpdate();         
                resultado = cstmt.getInt(4);

                if(resultado == 0)
                {
                    JOptionPane.showMessageDialog(this.pantalla, "Libro prestado", "Info", JOptionPane.INFORMATION_MESSAGE); 
                }
                else
                {
                   JOptionPane.showMessageDialog(this.pantalla, "No es posible realizar la operación", "Error", JOptionPane.ERROR_MESSAGE); 

                }

                con.commit();
                con.setAutoCommit(true);
                cstmt.close();
             
            }
             
            con.close();
         
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaModificarLibro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) 
        {
            try 
            {
                con.rollback();
                int cod = ex.getErrorCode();
                System.out.println(cod);
                JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error realizando el préstamos", "Error", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (SQLException e1) 
            {
                System.err.println("Error");
            }
           
        }
    }
    
}
