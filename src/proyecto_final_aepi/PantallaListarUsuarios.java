/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class PantallaListarUsuarios extends JFrame
{
    JPanel botones,contenedor;
    JTable tablaUsuario;
    DefaultTableModel modelo;
    JScrollPane scAutor;
    JButton botonVolver,botonDetalle,botonBaja,botonAlta,botonRefrescar;
    DefaultTableCellRenderer tcr;
    
    public PantallaListarUsuarios ()
    {
       contenedor = new JPanel(new BorderLayout(3,3)); 
       botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
       
       modelo = new DefaultTableModel();
       tablaUsuario =  new JTable(modelo);
       
       botonAlta = new JButton("Alta");
       botonDetalle = new JButton("Detalle");
       botonBaja = new JButton("Baja");
       botonVolver = new JButton("Volver");
       botonRefrescar = new JButton("Refrescar");

       this.initComponents();
    }
    
     public void initComponents() 
    {
        
        setSize(500,400);
        setLocationRelativeTo(null);
        setTitle("Listado de usuarios");

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        botonDetalle.setEnabled(false);
        botonBaja.setEnabled(false);

        //Definimos scroll y tamano
        scAutor = new JScrollPane(
                tablaUsuario,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //añadimos el contenedor principal al frame
        add(contenedor);
        
        ControladorPantallaListadoUsuario controlador = new ControladorPantallaListadoUsuario(this);
        controlador.rellenarTablaUsuarios();
        
        //para centrar el texto de las columnas 
        this.centrarCamposColumnas();
        
        //introducimos los botones en el contenedor 
        botones.add(botonAlta);
        botones.add(botonBaja);
        botones.add(botonDetalle);
        botones.add(botonRefrescar);
        botones.add(botonVolver);
         
        //añadimos los listener a los botones
        botonAlta.addActionListener(controlador);
        botonDetalle.addActionListener(controlador);
        botonVolver.addActionListener(controlador);
        botonBaja.addActionListener(controlador);
        botonRefrescar.addActionListener(controlador);
        
        tablaUsuario.addMouseListener(controlador);
        
        contenedor.add(scAutor,BorderLayout.CENTER);
        contenedor.add(botones,BorderLayout.SOUTH);
            
        setVisible(true);
    }
    
    protected void centrarCamposColumnas()
    {
         //para centrar el texto de las columnas 
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0 ; i < tablaUsuario.getColumnCount(); i++)
        {
            this.tablaUsuario.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
}
