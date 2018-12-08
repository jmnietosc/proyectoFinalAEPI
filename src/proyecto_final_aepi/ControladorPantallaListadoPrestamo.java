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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class ControladorPantallaListadoPrestamo implements ActionListener
{
    private PantallaListadoPrestamos pantalla;
    
    public ControladorPantallaListadoPrestamo(PantallaListadoPrestamos p )
    {
        pantalla = p;
    }
    
    public void actionPerformed(ActionEvent e) {
       
        String opcion = e.getActionCommand();
        
        switch(opcion)
        {
            case "Total" :  this.limpiarTabla();
                            this.mostrarListado(1);
                            break;
                            
            case "Activos" : this.limpiarTabla();
                            this.mostrarListado(2);
                            break;
                                
                            
            case "Inactivos" :  this.limpiarTabla();
                                this.mostrarListado(3);
                                break;
            
            case "Volver": this.pantalla.dispose();
                           break;
        }
    }
    
    private void mostrarListado(int opcion)
    {
        String sql ="";
        switch(opcion)
        {
            case 1 : sql = "{call listaPrestamos()}";
                     break;
                     
            case 2 : sql = "{call listaPrestamosActivos()}";
                     break ;
            
            case 3 : sql = "{call listaPrestamosInactivos()}";
                    break;
        }
        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con;
          
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                        + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
            
            
            CallableStatement cstmt = con.prepareCall (sql);
            ResultSet rs = cstmt.executeQuery();
            
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[this.pantalla.modelo.getColumnCount()];

                datosFila[0] = rs.getInt("idPrestamo");
                int estadoPrestamo = rs.getInt("estadoPrestamo");
                if(estadoPrestamo == 0)
                {
                     datosFila[1] = "ACTIVO";
                }
                else
                {
                    datosFila[1] = "INACTIVO";
                }
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String usuario = nombre + " " + apellidos;
                datosFila[2] = usuario;
                
                
                datosFila[3] = rs.getString("titulo");
                datosFila[4] = rs.getDate("fechaInicio");
                datosFila[5] = rs.getDate("fechaFin");
                datosFila[6] = rs.getDate("fechaDevolucion");
                
                this.pantalla.modelo.addRow(datosFila);
              
            }
            
            cstmt.close();
            con.close();
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaListadoPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
                Logger.getLogger(ControladorPantallaListadoPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    private void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) this.pantalla.tablaPrestamos.getModel();
        int a = this.pantalla.tablaPrestamos.getRowCount()-1;
        for (int i = a; i >= 0; i--) 
        {           
            tb.removeRow(tb.getRowCount()-1);
        }
    }
}
