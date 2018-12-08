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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Chema
 */
public class ControladorPantallaAcceso implements ActionListener,KeyListener{

    private PantallaAcceso pantalla;
    
    public ControladorPantallaAcceso(PantallaAcceso p)
    {
        this.pantalla = p;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {      
        
       // llamamos al método privado aceptar
       this.aceptar();
                   
    }
    
    public void keyTyped(KeyEvent e) 
    {
        //controlamos que sólo puedan introducirse 9 caracteres
       if (this.pantalla.textUser.getText().length()== 9)
       {
             e.consume();
       }
        
    }
 
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
         
    }
    
    
    private String devolverPass(String idEmp)
    {
        String passDep = null;
        
        try {
            
            //vamos a acceder a la base de dato.
            //primero establecemos la conexion
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trabajo_final?&user=root&password=admin"
            + "&useLegacyDatetimeCode=false&serverTimezone=UTC"); 
            
            //llamamos al procedimiento almacenado que obtiene la pass de un id
            CallableStatement cstmt = con.prepareCall ("{call getPass (?,?)}");
            
            //pasamos el id de entrada
            cstmt.setString(1,idEmp);
            
            //obtenemos el campo pass , que es de salida de nuestro procedimiento almacenado
            cstmt.registerOutParameter("pass",java.sql.Types.VARCHAR);//autor es el parametro de salida del procedimiento
            cstmt.execute();
            
            //guardamos la pass es la variable y la devolvemos
            passDep = cstmt.getString("pass"); // pass es el parametro de salida del procedimiento 
            cstmt.close();
            con.close(); 
            
        } catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(ControladorPantallaAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        return passDep;
    }
    
    private void aceptar()
    {
        //tomamos los valores de los campos de texto para ver si están en la base de datos
        String id = this.pantalla.textUser.getText();
        char arrayChar[] = this.pantalla.textPass.getPassword();
        String pass = "";
        //a partir del arrar de caracteres montamos la pass
       
        pass = String.valueOf(arrayChar);
        
        if(pass.equalsIgnoreCase("") || id.equalsIgnoreCase("") )
        {
          JOptionPane.showMessageDialog(null, "Alguno de los campos no ha sido rellenado", "Aviso", JOptionPane.WARNING_MESSAGE);   
        }
        else
        {
            
            String passEmp = this.devolverPass(id);

            //si la pass devuelta es distinto de null , quitamos espacios en blanco (si los hubiera)
            if(passEmp != null)
            {
                passEmp = passEmp.trim();
            }

            if(pass.equalsIgnoreCase(passEmp))
            {
                //si el usuario introducido no es el admin se opera
                if(!id.equalsIgnoreCase("admin")) 
                {
                    //PantallaMenu pantallaMenu = new PantallaMenu();
                     new PantallaMenu();
                }
                else // si el usuario es el administrador se abre la pantalla para dar de alta nuevos dependientes
                {
                    new PantallaAltaDependiente();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Usuario y/o password incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }  
}
