/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final_aepi;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Chema
 */
public class PantallaDetalleUsuario extends JFrame
{
    JPanel contenedor;
    JLabel labelId,labelNombre,labelApellidos;
    JTextField textoId,textoNombre,textoApellidos;
    JScrollPane scLibrosUsuario;
    JTable tablaLibrosUsuario;
    DefaultTableModel modelo;
    DefaultTableCellRenderer tcr;
    JButton botonVolver , botonModificar;
    String[] cabecera =  {"ID.LIBRO", "TÍTULO", "AUTOR"};
    int idUsuario;
    
    public PantallaDetalleUsuario(int idUsuario)
    {
        contenedor = new JPanel();
        labelId = new JLabel("Id.Usuario");
        textoId = new JTextField(20);
        textoId.setEditable(false);
        labelNombre = new JLabel("Nombre");
        textoNombre = new JTextField(45);
        textoNombre.setEditable(false);
        labelApellidos = new JLabel("Apellidos");
        textoApellidos = new JTextField(45);
        textoApellidos.setEditable(false);
        scLibrosUsuario = new JScrollPane();
        modelo = new DefaultTableModel();
        tablaLibrosUsuario =  new JTable(modelo);
        botonVolver = new JButton("Volver");
        botonModificar = new JButton("Modificar");
        
        this.idUsuario = idUsuario;
        
        this.initComponents();
       
    }
    
    public void initComponents()
    {
     
	setBounds(100, 100, 450, 300);				
	setTitle("Detalle usuario");
        setLocationRelativeTo(null);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	//Contenedor JPanel
	contenedor = new JPanel();
	getContentPane().add(contenedor);
        
        //Tipo de layout a usar
        SpringLayout sp = new SpringLayout();
	contenedor.setLayout(sp);
        
        //Añadimos las etiquetas
        contenedor.add(labelId);
        sp.putConstraint(SpringLayout.NORTH, labelId, 10, SpringLayout.NORTH, contenedor);
	sp.putConstraint(SpringLayout.WEST, labelId,  10, SpringLayout.WEST, contenedor);
        
        contenedor.add(labelNombre);
        sp.putConstraint(SpringLayout.NORTH, labelNombre, 50, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST,labelNombre,  10, SpringLayout.WEST, contenedor);
     
        contenedor.add(labelApellidos);
        sp.putConstraint(SpringLayout.NORTH, labelApellidos, 90, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, labelApellidos,  10, SpringLayout.WEST, contenedor);
        
        //añadimos los campos de texto
        contenedor.add(textoId);					
        sp.putConstraint(SpringLayout.NORTH, textoId, 10, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, textoId, 100, SpringLayout.WEST, contenedor);
        sp.putConstraint(SpringLayout.EAST, textoId, 300, SpringLayout.WEST, contenedor);
     

        contenedor.add(textoNombre);				
        sp.putConstraint(SpringLayout.NORTH, textoNombre, 50, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, textoNombre, 100, SpringLayout.WEST, contenedor);
        sp.putConstraint(SpringLayout.EAST, textoNombre, 300, SpringLayout.WEST, contenedor);
        //CUADRO DE TEXTO PARA LOS APELLIDOS

        contenedor.add(textoApellidos);					//añadir al contenedor
        sp.putConstraint(SpringLayout.NORTH, textoApellidos, 90, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, textoApellidos, 100, SpringLayout.WEST, contenedor);
        sp.putConstraint(SpringLayout.EAST, textoApellidos, 300, SpringLayout.WEST, contenedor);
        
        // Colocamos la tabla
        modelo.setColumnIdentifiers(cabecera);
        scLibrosUsuario.setViewportView(tablaLibrosUsuario);     
        contenedor.add(scLibrosUsuario);					//añadir al contenedor
        sp.putConstraint(SpringLayout.NORTH, scLibrosUsuario, 120, SpringLayout.NORTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, scLibrosUsuario,   10, SpringLayout.WEST, contenedor);
        sp.putConstraint(SpringLayout.EAST, scLibrosUsuario,  -10, SpringLayout.EAST, contenedor);
        sp.putConstraint(SpringLayout.SOUTH, scLibrosUsuario, -50, SpringLayout.SOUTH, contenedor);
        
        // Por último , colocamos los botones
        contenedor.add(botonModificar);					
	sp.putConstraint(SpringLayout.SOUTH, botonModificar, -10, SpringLayout.SOUTH, contenedor);	
	sp.putConstraint(SpringLayout.WEST, botonModificar,   90, SpringLayout.WEST, contenedor);
	
        contenedor.add(botonVolver);
        sp.putConstraint(SpringLayout.SOUTH, botonVolver, -10, SpringLayout.SOUTH, contenedor);
        sp.putConstraint(SpringLayout.WEST, botonVolver,  220, SpringLayout.WEST, contenedor);
        
        
        // Añadimos el controlador a los botones
        ControladorPantallaDetalle controlador = new  ControladorPantallaDetalle(this);
        botonModificar.addActionListener(controlador);
        botonVolver.addActionListener(controlador);
        
        //para centrar el texto de las columnas 
        tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0 ; i < tablaLibrosUsuario.getColumnCount(); i++)
        {
            this.tablaLibrosUsuario.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
        
        //hacemos visible la pantalla
        setVisible(true);
    }
    
            
}
