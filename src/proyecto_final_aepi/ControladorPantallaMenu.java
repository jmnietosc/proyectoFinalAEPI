/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Chema
 */
public class ControladorPantallaMenu implements ActionListener{

    public ControladorPantallaMenu()
    {}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String menuPulsado = e.getActionCommand();
        
        switch(menuPulsado)
        {
            case "Alta usuario": new PantallaAltaUsuario();
                                 break;
           
            case "Listado usuarios" : new PantallaListarUsuarios();
                                      break;
            
            case "Alta libro" : new PantallaAltaLibro();
                                break;
                                         
            case "Listado libro" :  new PantallaListarLibros();
                                    break;
                                    
            case "Listado prestamos" : new PantallaListadoPrestamos();
                                           break;
        }
    }
    
}
