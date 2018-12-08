/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class PantallaListadoPrestamos extends JFrame
{
    JPanel jpanel,jpanel2 , contenedor;
    ButtonGroup botones;
    JRadioButton total,activo,inactivo;
    JTable tablaPrestamos;
    DefaultTableModel modelo;
    JScrollPane scPrestamos;
    String[] cabecera =  {"ID.PRÉSTAMO", "ESTADO", "USUARIO","TITULO","FEC.INICIO","FEC.FIN","FEC.DEVOL"};
    DefaultTableCellRenderer tcr;
    JButton botonVolver;
    
    public PantallaListadoPrestamos()
    {
        contenedor = new JPanel(new BorderLayout(3,3)); 
        jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jpanel2 = new JPanel();
        
        modelo = new DefaultTableModel();
        tablaPrestamos =  new JTable(modelo);
        this.modelo.setColumnIdentifiers(cabecera);
        botones = new ButtonGroup();
        total = new JRadioButton("Total");
        activo = new JRadioButton("Activos");
        inactivo = new JRadioButton("Inactivos"); 
        botonVolver = new JButton("Volver");
        
        this.initComponents();
    }
    
    public void initComponents()
    {
        setSize(600,500);
        setLocationRelativeTo(null);
        setTitle("Listado de préstamos");
        
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //Definimos scroll y tamano
        scPrestamos = new JScrollPane(
                tablaPrestamos,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
          //añadimos el contenedor principal al frame
        add(contenedor);
        
        //metemos los radioButton en el boton group
        botones.add(total);
        botones.add(activo);
        botones.add(inactivo);
        
        //metemos los botones en el jpanel
        jpanel.add(total);
        jpanel.add(activo);
        jpanel.add(inactivo);
        
        jpanel2.add(botonVolver);;
        
        
        //añadimos los eventos a los
        ControladorPantallaListadoPrestamo controlador = new ControladorPantallaListadoPrestamo(this);
        total.addActionListener(controlador);
        activo.addActionListener(controlador);
        inactivo.addActionListener(controlador);
        botonVolver.addActionListener(controlador);
        

        // colocamos los contenedores
        contenedor.add(jpanel,BorderLayout.NORTH);
        contenedor.add(scPrestamos,BorderLayout.CENTER);
        contenedor.add(jpanel2,BorderLayout.SOUTH);
        
        //para centrar el texto de las columnas 
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0 ; i < cabecera.length ; i++)
        {
            this.tablaPrestamos.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        
        setVisible(true);
    }
    
}
