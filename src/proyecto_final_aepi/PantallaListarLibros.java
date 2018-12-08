/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class PantallaListarLibros extends JFrame
{
 
    Dimension dimension;
    JPanel botones,contenedor;
    JTable tablaLibro;
    DefaultTableModel modeloLibro;
    JScrollPane scLibro;
    JButton botonVolver,botonModificar,botonBaja,botonAlta,botonRefrescar;
    DefaultTableCellRenderer tcr;
    
    public PantallaListarLibros()
    {
       contenedor = new JPanel(new BorderLayout(3,3)); 
       botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
       
       modeloLibro = new DefaultTableModel();
       tablaLibro =  new JTable(modeloLibro);
       
       botonAlta = new JButton("Alta");
       botonModificar = new JButton("Modificar");
       botonBaja = new JButton("Baja");
       botonVolver = new JButton("Volver");
       botonRefrescar = new JButton("Refrescar");

       this.initComponents();
    }
    
    public void initComponents() 
    {
        
        setSize(500,400);
        setLocationRelativeTo(null);
        setTitle("Listado de libros");

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        botonModificar.setEnabled(false);
        botonBaja.setEnabled(false);

        //Definimos scroll y tamano
        scLibro = new JScrollPane(
                tablaLibro,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //añadimos el contenedor principal al frame
        add(contenedor);
        
        ControladorPantallaListadoLibro controlador = new ControladorPantallaListadoLibro (this);
         
        //rellenamos la tabla
        controlador.rellenarTablaLibro();
        
        //centramos las campos de las celdas
        
        this.centrarCamposColumnas();
        //introducimos los botones en el contenedor 
        botones.add(botonAlta);
        botones.add(botonModificar);
        botones.add(botonBaja);
        botones.add(botonRefrescar);
        botones.add(botonVolver);
        

        
        //añadimos los listener a los botones
        botonAlta.addActionListener(controlador);
        botonModificar.addActionListener(controlador);
        botonVolver.addActionListener(controlador);
        botonBaja.addActionListener(controlador);
        botonRefrescar.addActionListener(controlador);
        
        tablaLibro.addMouseListener(controlador);
        
        contenedor.add(scLibro,BorderLayout.CENTER);
        contenedor.add(botones,BorderLayout.SOUTH);
            
        setVisible(true);
    }
    
    protected void centrarCamposColumnas()
    {
         //para centrar el texto de las columnas 
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0 ; i < tablaLibro.getColumnCount(); i++)
        {
            this.tablaLibro.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }
      
}
