/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class ControladorPantallaListadoUsuario extends MouseAdapter implements ActionListener,MouseListener {

    protected PantallaListarUsuarios pantalla;
    protected int idUsuarioSeleccionado; 
    
    public ControladorPantallaListadoUsuario(PantallaListarUsuarios p)
    {
        this.pantalla = p;
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
            case "Alta":    new PantallaAltaUsuario();                          
                            break; 
            
            case "Detalle":  
                             new PantallaDetalleUsuario(idUsuarioSeleccionado);
                             this.pantalla.dispose();
                             break;   
            
             case "Baja":   this.eliminarUsuario(idUsuarioSeleccionado);
                             break;  
                      
             case "Volver": 
                             this.pantalla.dispose();
                             break;
                             
            case "Refrescar":  
                               this.refrescar();
                               break;
        }
    }
    
    
    public void mouseClicked(MouseEvent e)
    {
        //para saber si se ha seleccionado algo en la tabla
        int fila = this.pantalla.tablaUsuario.rowAtPoint(e.getPoint());
        int columna = this.pantalla.tablaUsuario.columnAtPoint(e.getPoint());

        if ((fila > -1) && (columna > -1))
        {
            this.pantalla.botonDetalle.setEnabled(true);
            //obtenemos el numero de fila seleccionado
            int indiceSeleccionado = pantalla.tablaUsuario.getSelectedRow();
            //obtenemos el id de usuario seleccionado
            this.idUsuarioSeleccionado = Integer.valueOf(this.pantalla.tablaUsuario.getValueAt(indiceSeleccionado, 0).toString());
            //comprobamos el numero de libros en prestamo que tiene ese usuario
            //si no tiene libros en prestamos se podría dar de baja el usuario
            if(this.obtenerNumeroLibrosEnPrestamo(this.idUsuarioSeleccionado) == 0)
            {
                this.pantalla.botonBaja.setEnabled(true);
            }
            else
            {
                this.pantalla.botonBaja.setEnabled(false);
            }
        }
    }
    
    private void refrescar()
    {
        this.limpiarTabla();
        this.rellenarTablaUsuarios();
        this.deshabilitarBotones();
        this.pantalla.centrarCamposColumnas();
      
    }
    
    
    // método para dehabilitar botones
    private void deshabilitarBotones()
    {
         this.pantalla.botonBaja.setEnabled(false);
         this.pantalla.botonDetalle.setEnabled(false);
      
    }
    
    //método para limpiar la tabla
    private void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) this.pantalla.tablaUsuario.getModel();
        int a = this.pantalla.tablaUsuario.getRowCount()-1;
        for (int i = a; i >= 0; i--) 
        {           
            tb.removeRow(tb.getRowCount()-1);
        }
    }
    
    //método para rellenar la tabla de usuarios
    protected void rellenarTablaUsuarios()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            ResultSet rs;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            CallableStatement cstmt = con.prepareCall ("{call getUsuarios()}");
            rs = cstmt.executeQuery();
            ResultSetMetaData metaDatos = rs.getMetaData();
            int numeroColumnas = metaDatos.getColumnCount();
            // Se crea un array de etiquetas para rellenar las columnas de la tabla
            Object[] etiquetas = new Object[numeroColumnas];
            for (int i = 0; i < numeroColumnas; i++)
            {           
                 etiquetas[i] = metaDatos.getColumnLabel(i + 1); 
            }
            
            this.pantalla.modelo.setColumnIdentifiers(etiquetas);
    
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[this.pantalla.modelo.getColumnCount()];
       
                datosFila[0] = rs.getInt(1);
                datosFila[1] = rs.getString(2);
                datosFila[2] = rs.getString(3);
                datosFila[3] = rs.getString(4);
                datosFila[4] = rs.getString(5);
                datosFila[5] = rs.getString(6);
   
                this.pantalla.modelo.addRow(datosFila);
              
            }
            rs.close();
            cstmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PantallaMenu.class.getName()).log(Level.SEVERE, null, ex);
       
        }     
    }
    
    //método para obtener el numero de libros en prestamo actual que tiene un determinado usuario
    private int obtenerNumeroLibrosEnPrestamo(int idUsuarioSeleccionado)
    {
        int numLibros = 0;
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
           
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            CallableStatement cstmt = con.prepareCall ("{call getNumeroLibrosUsuario(?,?)}");
            
            
            //pasamo el id del libro seleccionado al procedimiento almacenado
            cstmt.setInt(1,idUsuarioSeleccionado);
            // parametros de salida del procedimiento almacenado
            cstmt.registerOutParameter(2, java.sql.Types.INTEGER); 
            
            cstmt.executeQuery();
            
            numLibros = cstmt.getInt(2);
             
            cstmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PantallaMenu.class.getName()).log(Level.SEVERE, null, ex);
       
        } 
        
        return numLibros;
    }
    
    //Método para eliminar un usuario que pasamos por parámetro.Transaccion
    private void eliminarUsuario(int idUsuario)
    {
        Connection con = null;
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            con.setAutoCommit(false);
            
            CallableStatement cstmt = con.prepareCall ("{call borrarUsuario(?)}");
            
            cstmt.setInt(1,idUsuario);
            cstmt.executeUpdate();
            
            con.commit();
            con.setAutoCommit(true);
            cstmt.close();
            con.close();
            JOptionPane.showMessageDialog(this.pantalla, "Se ha eliminado correctamente al usuario seleccionado", "Info", JOptionPane.INFORMATION_MESSAGE); 
            this.refrescar();

        }
        
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaListadoPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            try 
            {
                con.rollback();
                int cod = ex.getErrorCode();
                System.out.println(cod);
                JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error dando de baja al usuario", "Error", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (SQLException e1) 
            {
                System.err.println("Error");
            }   
        }
    }
}
