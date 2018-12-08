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
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaAltaLibro implements ActionListener,KeyListener
{
    private PantallaAltaLibro pantalla;
    
    public ControladorPantallaAltaLibro(PantallaAltaLibro p)
    {
        this.pantalla = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        String botonPulsado = e.getActionCommand();
        
        switch(botonPulsado)
        {
            case "Aceptar": 
                            this.insertarLibro();
                            break;
            
            case "Limpiar": 
                            this.pantalla.textoId.setText("");
                            this.pantalla.textoTitulo.setText("");
                            this.pantalla.textoAutor.setText("");
                            this.pantalla.textoISBN.setText("");
                            break;
            
            case "Volver": 
                             this.pantalla.dispose();
                             break;   
                
        }
    }
    
    private void insertarLibro()
    {
        String id = this.pantalla.textoId.getText();
        String titulo = this.pantalla.textoTitulo.getText();
        String autor = this.pantalla.textoAutor.getText();
        String ISBN = this.pantalla.textoISBN.getText();
        
        if(!titulo.equals("") && !autor.equals("") && !ISBN.equals("") )
        {
            try {
            //vamos a acceder a la base de dato.
            //primero establecemos la conexion
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            //llamamos al procedimiento almacenado de alta de libros
            CallableStatement cstmt = con.prepareCall ("{call altaLibro (?,?,?,?,?)}");
            //pasamos el primer campo
            
            if(id.equals(""))
            {
                cstmt.setNull(1, Types.INTEGER);
            }
            else
            {
                cstmt.setInt(1, Integer.valueOf(id));
            }
            //pasamos el segundo parametro, titulo
            cstmt.setString(2,titulo);
            //pasamos el tecer parametro,autor
            cstmt.setString(3,autor);
            //pasamos el cuarto parametro, ISBN
            cstmt.setString(4,ISBN);
            cstmt.setInt(5,0);
            
            int retorno = cstmt.executeUpdate();
            
            if(retorno > 0)
            {
                this.pantalla.textoId.setText("");
                this.pantalla.textoTitulo.setText(""); 
                this.pantalla.textoAutor.setText("");
                this.pantalla.textoISBN.setText("");
                JOptionPane.showMessageDialog(this.pantalla, "Libro dado de alta correctamente", "Info", JOptionPane.INFORMATION_MESSAGE); 

            }
            else
            {
              JOptionPane.showMessageDialog(this.pantalla, "Error dando de alta el nuevo libro", "Error", JOptionPane.ERROR_MESSAGE); 

            }
            cstmt.close();
            con.close(); 
             
             
            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(this.pantalla, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
            } 
        }
        else
        {
           JOptionPane.showMessageDialog(this.pantalla, "Alguno de los campos está vacío", "Aviso", JOptionPane.WARNING_MESSAGE); 
        }
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
       //controlamos que sólo puedan introducirse 9 caractes
       if (this.pantalla.textoId.getText().length()== 9)
       {
             e.consume();
       }
       else // controlamos que solo se introduzcan números
       {
            char caracter = e.getKeyChar();          
            if(((caracter < '0') || (caracter > '9')))
            {
               e.consume();  // ignorar el evento de teclado
            }
        }  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
}
