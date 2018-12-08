/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import javax.swing.*;


/**
 *
 * @author Chema
 */
public class PantallaMenu extends JFrame
{
    JMenuBar menuBar;
    JMenu menuUser, menuLibro,menuPrestamo;
    JMenuItem menuItemUserAlta,menuItemUserListado;
    JMenuItem menuItemLibroAlta,menuItemLibroListado;
    JMenuItem menuItemPrestamoTotal;
    
    
    public PantallaMenu()
    {
           
        menuBar = new JMenuBar();
        menuUser = new JMenu("Usuarios");
        menuLibro = new JMenu("Libros");
        menuPrestamo = new JMenu("Préstamos");
        
        menuItemUserAlta = new JMenuItem("Alta usuario");
        menuItemUserListado = new JMenuItem("Listado usuarios");
        
        menuItemLibroAlta = new JMenuItem("Alta libro");
        menuItemLibroListado = new JMenuItem("Listado libro");
        
        menuItemPrestamoTotal =  new JMenuItem("Listado prestamos");
       
        
        
        this.initComponents();
    }
    
    public void initComponents()
    {
        setSize(400,300);
        setLocationRelativeTo(null);
        setTitle("Pantalla Inicial");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        setJMenuBar(menuBar);
        

        // MENU USUARIOS          
        //introducimos la primera opcion en el submenu usuarios
        menuUser.add(menuItemUserAlta);
        //añadimos el listener para el evento
        menuItemUserAlta.addActionListener(new ControladorPantallaMenu());
                 
        //introducimos la tercera opcion en el submenu usuarios
        menuUser.add(menuItemUserListado);
        menuItemUserListado.addActionListener(new ControladorPantallaMenu());
        
        // MENU LIBROS
        //introducimos la primera opcion en el submenu libros
        menuLibro.add(menuItemLibroAlta);
        menuItemLibroAlta.addActionListener(new ControladorPantallaMenu());
                    
        //introducimos la tercera opcion en el submenu libros
        menuLibro.add(menuItemLibroListado);
        menuItemLibroListado.addActionListener(new ControladorPantallaMenu());
        
        //opciones del submenu prestamos
        menuPrestamo.add(menuItemPrestamoTotal);
        menuItemPrestamoTotal.addActionListener(new ControladorPantallaMenu());
              
        //introducimos los menu en la barra de menu
        menuBar.add(menuUser);
        menuBar.add(menuLibro);
        menuBar.add(menuPrestamo);
       
       setVisible(true);
    }
    
   
}
