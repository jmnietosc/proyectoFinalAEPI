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
public class ControladorPantallaListadoLibro extends MouseAdapter implements ActionListener {

    PantallaListarLibros pantalla;
    int indiceSeleccionado;
    
    public ControladorPantallaListadoLibro(PantallaListarLibros p)
    {
        this.pantalla = p;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
            case "Alta":    new PantallaAltaLibro();
                            this.pantalla.dispose();
                            break; 
            
            case "Modificar": 
                             int idLibroSeleccionado = Integer.valueOf(this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 0).toString());
                             String titulo = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 1).toString();
                             String autor = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 2).toString();
                             String ISBN = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 3).toString();
                             String usuario = this.obtenerUsuarioPrestamo();
                             new PantallaModificarLibro (idLibroSeleccionado ,titulo,autor,ISBN,usuario);
                             this.pantalla.dispose();
                             break;   
            
             case "Baja":    this.bajaLibro();
                             break;  
                      
             case "Volver": 
                             this.pantalla.dispose();
                             break;
                             
            case "Refrescar": this.refrescar();
                               break;
        }
       
    }
    
    public void mouseClicked(MouseEvent e)
    {
        int fila = this.pantalla.tablaLibro.rowAtPoint(e.getPoint());
        int columna = this.pantalla.tablaLibro.columnAtPoint(e.getPoint());
        if ((fila > -1) && (columna > -1))
        {
            this.pantalla.botonModificar.setEnabled(true);
            indiceSeleccionado = pantalla.tablaLibro.getSelectedRow();
            String estado = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 4).toString();
            if(estado.equalsIgnoreCase("Prestado"))
            {      
                this.pantalla.botonBaja.setEnabled(false);
            }
            else
            {

                this.pantalla.botonBaja.setEnabled(true);
            }      
        }
    }
    
    private String obtenerUsuarioPrestamo ()
    {
        String usuario = "";
        try 
        { 
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con;
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
             CallableStatement cstmt = con.prepareCall ("{call getUsuarioPrestamoLibro(?,?,?)}");
             
             String idS = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 0).toString();
             int id = Integer.valueOf(idS);
             
             //pasamo el id del libro seleccionado al procedimiento almacenado
             cstmt.setInt(1,id);    
             
             // parametros de salida del procedimiento almacenado
             cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
             cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
             
             cstmt.execute();
                
            // Se obtienen la salida del procedimineto almacenado
            String nombre = cstmt.getString(2);
            String apellidos = cstmt.getString(3);
            
            if(nombre == null)
            {
                nombre = "";
            }
            
            if(apellidos == null)
            {
                apellidos = "";
            }
            
            usuario = nombre + " " + apellidos;
                
            cstmt.close();
            con.close();
             
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaListadoLibro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usuario;
    }
    
    private void bajaLibro()
    {
        Connection con = null;
        try 
        { 
             Class.forName("com.mysql.cj.jdbc.Driver");
            
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC");
             
             con.setAutoCommit(false);
             
             CallableStatement cstmt = con.prepareCall ("{call borrarLibro(?)}");
             
             String idS = this.pantalla.tablaLibro.getValueAt(indiceSeleccionado, 0).toString();
             int id = Integer.valueOf(idS);
             
             cstmt.setInt(1,id);
             cstmt.executeUpdate();
             
             con.commit();
             con.setAutoCommit(true);
             cstmt.close();
             con.close();
              JOptionPane.showMessageDialog(this.pantalla, "Se ha dado de baja el libro", "Info", JOptionPane.INFORMATION_MESSAGE); 
             this.refrescar();
            
             
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorPantallaListadoPrestamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            try 
            {
                con.rollback();
                int cod = ex.getErrorCode();
                System.out.println(cod);
                JOptionPane.showMessageDialog(this.pantalla, "Se ha producido un error dando de baja el libro", "Error", JOptionPane.ERROR_MESSAGE); 
            } 
            catch (SQLException e1) 
            {
                System.err.println("Error");
            }   
        }                
    }
    
    
    private void refrescar()
    {
        this.limpiarTabla();
        this.rellenarTablaLibro();
        this.deshabilitarBotones();
        this.pantalla.centrarCamposColumnas();
    }
    
    private void limpiarTabla()
    {
        DefaultTableModel tb = (DefaultTableModel) this.pantalla.tablaLibro.getModel();
        int a = this.pantalla.tablaLibro.getRowCount()-1;
        for (int i = a; i >= 0; i--) 
        {           
            tb.removeRow(tb.getRowCount()-1);
        }
    }
    
    private void deshabilitarBotones()
    {
         this.pantalla.botonBaja.setEnabled(false);
         this.pantalla.botonModificar.setEnabled(false);
      
    }
    
     protected void rellenarTablaLibro()
    { 
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            ResultSet rs;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            CallableStatement cstmt = con.prepareCall ("{call getLibros()}");
            rs = cstmt.executeQuery();
            ResultSetMetaData metaDatos = rs.getMetaData();
            int numeroColumnas = metaDatos.getColumnCount();
            // Se crea un array de etiquetas para rellenar las columnas de la tabla
            Object[] etiquetas = new Object[numeroColumnas];
            for (int i = 0; i < numeroColumnas; i++)
            {           
                 etiquetas[i] = metaDatos.getColumnLabel(i + 1); 
            }
            
            this.pantalla.modeloLibro.setColumnIdentifiers(etiquetas);
    
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[ this.pantalla.modeloLibro.getColumnCount()];
       
                datosFila[0] = rs.getString(1);
                datosFila[1] = rs.getString(2);
                datosFila[2] = rs.getString(3);
                datosFila[3] = rs.getString(4);
                int tipoEstado = rs.getInt(5);
                String estado = tipoEstado == 1 ?"Prestado":"Libre"; 
                datosFila[4] = estado;
   
                 this.pantalla.modeloLibro.addRow(datosFila);
              
            }
            rs.close();
            cstmt.close();
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PantallaMenu.class.getName()).log(Level.SEVERE, null, ex);
       
        }
       
    }   
}
