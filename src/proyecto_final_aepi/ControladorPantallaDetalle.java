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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chema
 */
public class ControladorPantallaDetalle implements ActionListener{

    PantallaDetalleUsuario pantalla;
    
    public ControladorPantallaDetalle(PantallaDetalleUsuario p)
    {
        pantalla = p;
        this.rellenarCampos();
        this.rellenarTabla();
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String opcion = e.getActionCommand();
        
        switch(opcion)
        {
            case "Modificar": 
                              new PantallaModificarUsuario(this.pantalla.idUsuario);
                              this.pantalla.dispose(); 
                              break;
            case "Volver":
                           new PantallaListarUsuarios();
                           this.pantalla.dispose();                          
                           break;
        }
       
    }
    
    private void rellenarCampos()
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
                int id = rs.getInt(1);
                this.pantalla.textoId.setText(String.valueOf(id));
                this.pantalla.textoNombre.setText(rs.getString(2));
                this.pantalla.textoApellidos.setText(rs.getString(3));
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
    
    private void rellenarTabla()
    {
        int idUsuario = this.pantalla.idUsuario;
        
         try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            ResultSet rs;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            CallableStatement cstmt = con.prepareCall ("{call getListaLibrosUsuario(?)}");
            cstmt.setInt(1,idUsuario);
            
            rs = cstmt.executeQuery();  
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[ this.pantalla.modelo.getColumnCount()];
       
                datosFila[0] = rs.getString(1);
                datosFila[1] = rs.getString(2);
                datosFila[2] = rs.getString(3);

   
                 this.pantalla.modelo.addRow(datosFila);
              
            }
            rs.close();
            cstmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PantallaMenu.class.getName()).log(Level.SEVERE, null, ex);
       
        }
       
    }   
    
    
}
