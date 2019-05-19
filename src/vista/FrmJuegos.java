package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controlador.JuegosController;
import excepciones.CampoVacioException;
import excepciones.CodigoDeBarrasException;
import excepciones.PrecioException;
import modelo.Juego;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




	public class FrmJuegos extends JFrame {
		private JPanel panel;
		private JTextField textTitulo, textEstudio, textFecha, textCodigo,textPrecio;
		private JButton btnPrimero, btnAtras, btnAdelante, btnUltimo;
		private JButton btnNuevo, btnEditar, btnGuardar, btnDeshacer, btnBorrar,btnFiltrar,btnMostrarTodo;
		private JPanel panelMantenimiento;
		private JPanel panelGrid;
		private JScrollPane scrollPane;
		private List<Juego> juegos;
		private int puntero=0;
		private DefaultTableModel dtm;
		private JPanel panelLibros,panelNavegador;
		private JLabel lblTitutlo,lblEstudio,lblFechaDeLanzamiento,lblCodigoDeBarras,lblPrecio;
		private JScrollPane scrollPane_1;
		private JTable table;
		private JLabel labelGenero;
		private JTextField textGenero;
		private Juego juego;
		private boolean nuevo=false;
		private JFrame frame;
		private JTextField textBuscar;
		private JComboBox comboBox;
		
		public FrmJuegos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1182, 476);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		definirVentana();
		cargarDatos();
		mostrarDatos();
		definirEventos();
		this.setVisible(true);
		}
		private void cargarDatos() {
			
			try {
				JuegosController juegosbiblio = new JuegosController();
				juegosbiblio.abrirConexion();
				juegos=juegosbiblio.getConsultaJuegos("select * from juegos");
				cargarGrid(juegos);
				juegosbiblio.cerrarConexion();
			} catch (ClassNotFoundException | SQLException | CampoVacioException | CodigoDeBarrasException | PrecioException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			
		}
		private void cargarGrid(List<Juego> juegos) {
			dtm.setRowCount(0);
			dtm.setColumnCount(0);
			String[]titulos= {"Titulo","Estudio","Fecha de Lanzamiento","Codigo de Barras","Precio","Género"};
			dtm.setColumnIdentifiers(titulos);
			for(int contador=0;contador<juegos.size();contador++) {
				juego=juegos.get(contador);
				dtm.addRow(juego.toStringGrid());
			}
			
		}
		private void mostrarDatos() {
			if(puntero>=juegos.size()) {
				puntero=juegos.size()-1;
			}
			juego=juegos.get(puntero);
			textTitulo.setText(juego.getTitulo());
			textCodigo.setText(juego.getCodigobarras());
			textEstudio.setText(juego.getEstudio());
			textFecha.setText(juego.getFechalanzamiento().toString());
			textPrecio.setText(Integer.toString(juego.getPrecio()));
			textGenero.setText(juego.getGenero());
			juego=null;
			
			
		}
		//E V E N T O S
		private void definirEventos() {
			btnPrimero.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					puntero=0;
					mostrarDatos();
				}
			});
			btnAtras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(puntero>0) {
						puntero--;
						mostrarDatos();
					}
				}
			});
			btnAdelante.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(puntero<juegos.size()-1) {
						puntero++;
						mostrarDatos();
					}
				}
			});
			btnUltimo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					puntero=juegos.size()-1;
					mostrarDatos();
				}
			});
			btnNuevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textTitulo.setText("");
					textTitulo.setText("");
					textCodigo.setText("");
					textEstudio.setText("");
					textFecha.setText("");
					textPrecio.setText("");
					textGenero.setText("");
					habilitarPanelJuegosNuevo(true);
					habilitarNavegador(false);
					habilitarPanelMantenimiento(false);
					nuevo=true;
				}
			});
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					habilitarPanelJuegos(true);
					habilitarNavegador(false);
					habilitarPanelMantenimiento(false);
				}
			});
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JuegosController juegosbiblio=new JuegosController();
					try {
						juegosbiblio.abrirConexion();
						juego=juegos.get(puntero);
						int confirmar=JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres borrar?");
						if(JOptionPane.OK_OPTION==confirmar) {
							juegosbiblio.borraJuego(juego);
						}
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					cargarDatos();
					mostrarDatos();
				}
			});
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(nuevo) {
						juego=null;
						String titulo=textTitulo.getText();
						String estudio=textEstudio.getText();
						String fecha=textFecha.getText();
						Date fechau=null;
						SimpleDateFormat formateador=new SimpleDateFormat("yyyy-MM-dd");
						formateador.setLenient(false);
						try {
							fechau=formateador.parse(fecha);
						} catch (ParseException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						java.sql.Date fechalanzamiento=new java.sql.Date(fechau.getTime());
						String codigobarras=textCodigo.getText();
						int precio=Integer.parseInt(textPrecio.getText());
						String genero=textGenero.getText();
						try {
							juego=new Juego(titulo, estudio, codigobarras, genero, precio, fechalanzamiento);
						} catch (CampoVacioException | CodigoDeBarrasException | PrecioException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						JuegosController juegobiblio=new JuegosController();
						try {
							juegobiblio.abrirConexion();
							juegobiblio.agregaJuego(juego);
							juegobiblio.cerrarConexion();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						cargarDatos();
						mostrarDatos();
						habilitarNavegador(true);
						habilitarPanelJuegos(false);
						habilitarPanelMantenimiento(true);
						nuevo=false;
						
					}
					else {
						juego=null;
						String titulo=textTitulo.getText();
						String estudio=textEstudio.getText();
						String fecha=textFecha.getText();
						Date fechau=null;
						SimpleDateFormat formateador=new SimpleDateFormat("yyyy-MM-dd");
						formateador.setLenient(false);
						try {
							fechau=formateador.parse(fecha);
						} catch (ParseException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						java.sql.Date fechalanzamiento=new java.sql.Date(fechau.getTime());
						String codigobarras=textCodigo.getText();
						int precio=Integer.parseInt(textPrecio.getText());
						String genero=textGenero.getText();
						try {
							juego=new Juego(titulo, estudio, codigobarras, genero, precio, fechalanzamiento);
						} catch (CampoVacioException | CodigoDeBarrasException | PrecioException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						JuegosController bibliojuegoseditado=new JuegosController();
						try {
							bibliojuegoseditado.abrirConexion();
							bibliojuegoseditado.modificado(juego);
							bibliojuegoseditado.cerrarConexion();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						cargarDatos();
						mostrarDatos();
						habilitarNavegador(true);
						habilitarPanelJuegos(false);
						habilitarPanelMantenimiento(true);
					}
				}
			});
			btnDeshacer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarDatos();
					habilitarNavegador(true);
					habilitarPanelJuegos(false);
					habilitarPanelMantenimiento(true);
				}
			});
			btnFiltrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String buscar=textBuscar.getText();
					String campo=comboBox.getSelectedItem().toString();
					JuegosController juegosbiblioteca=new JuegosController();
					try {
						juegosbiblioteca.abrirConexion();
						juegos=juegosbiblioteca.buscaLibros(campo, buscar);
						juegosbiblioteca.cerrarConexion();
					} catch (ClassNotFoundException | SQLException | CampoVacioException | CodigoDeBarrasException | PrecioException e1) {
						// TODO Bloque catch generado automáticamente
						e1.printStackTrace();
					}
					cargarGrid(juegos);
					mostrarDatos();
				}
			});
			btnMostrarTodo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cargarDatos();
					mostrarDatos();
				}
			});
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					puntero=table.getSelectedRow();	
					mostrarDatos();
				}
			});
		}
		// D E F I N I R V E N T A N A
		private void definirVentana() {
		// TODO Apéndice de método generado automáticamente
		panelLibros = new JPanel();
		panelLibros.setLayout(null);
		panelLibros.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Libros",
		TitledBorder.LEADING,
		TitledBorder.TOP, null, Color.BLUE));
		panelLibros.setBounds(28, 118, 298, 227);
		panel.add(panelLibros);
		textTitulo = new JTextField();
		textTitulo.setEditable(false);
		textTitulo.setColumns(10);
		textTitulo.setBounds(82, 29, 186, 20);
		panelLibros.add(textTitulo);
		
		lblTitutlo = new JLabel("Titulo");
		lblTitutlo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitutlo.setBounds(26, 32, 46, 14);
		panelLibros.add(lblTitutlo);
		
		lblEstudio = new JLabel("Estudio");
		lblEstudio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstudio.setBounds(26, 60, 46, 14);
		panelLibros.add(lblEstudio);
		
		textEstudio = new JTextField();
		textEstudio.setEditable(false);
		textEstudio.setColumns(10);
		textEstudio.setBounds(82, 57, 186, 20);
		panelLibros.add(textEstudio);
		
		lblFechaDeLanzamiento = new JLabel("Fecha de Lanzamiento");
		lblFechaDeLanzamiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDeLanzamiento.setBounds(26, 91, 127, 14);
		panelLibros.add(lblFechaDeLanzamiento);
		
		textFecha = new JTextField();
		textFecha.setEditable(false);
		textFecha.setColumns(10);
		textFecha.setBounds(163, 88, 105, 20);
		panelLibros.add(textFecha);
		
		lblCodigoDeBarras = new JLabel("Codigo de Barras");
		lblCodigoDeBarras.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigoDeBarras.setBounds(26, 121, 96, 14);
		panelLibros.add(lblCodigoDeBarras);
		
		textCodigo = new JTextField();
		textCodigo.setEditable(false);
		textCodigo.setColumns(10);
		textCodigo.setBounds(136, 119, 132, 20);
		panelLibros.add(textCodigo);
		
		lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPrecio.setBounds(26, 150, 46, 14);
		panelLibros.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setBounds(82, 150, 105, 20);
		textPrecio.setEditable(false);
		panelLibros.add(textPrecio);
		textPrecio.setColumns(10);
		
		labelGenero = new JLabel("G\u00E9nero");
		labelGenero.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelGenero.setBounds(26, 185, 46, 14);
		panelLibros.add(labelGenero);
		
		textGenero = new JTextField();
		textGenero.setEditable(false);
		textGenero.setColumns(10);
		textGenero.setBounds(82, 181, 105, 20);
		panelLibros.add(textGenero);
		
		panelNavegador = new JPanel();
		panelNavegador.setLayout(null);
		panelNavegador.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Navegador",
		TitledBorder.LEADING,
		TitledBorder.TOP, null, Color.BLUE));
		panelNavegador.setBounds(28, 356, 218, 74);
		panel.add(panelNavegador);
		// NAVEGADOR
		ImageIcon imaNav = new ImageIcon("imagenes/navPri.jpg");
		btnPrimero = new JButton("", imaNav);
		btnPrimero.setBounds(15, 15, 40, 40);
		panelNavegador.add(btnPrimero);
		
		imaNav = new ImageIcon("imagenes/navIzq.jpg");
		btnAtras = new JButton("", imaNav);

		btnAtras.setBounds(65, 15, 40, 40);
		panelNavegador.add(btnAtras);
		
		imaNav = new ImageIcon("imagenes/navDer.jpg");
		btnAdelante = new JButton("", imaNav);
		btnAdelante.setBounds(115, 15, 40, 40);
		panelNavegador.add(btnAdelante);
		
		imaNav = new ImageIcon("imagenes/navUlt.jpg");
		btnUltimo = new JButton("", imaNav);
		btnUltimo.setBounds(165, 15, 40, 40);
		panelNavegador.add(btnUltimo);
		
		panelMantenimiento = new JPanel();
		panelMantenimiento.setLayout(null);
		panelMantenimiento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Mantenimiento Libros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panelMantenimiento.setBounds(28, 21, 266, 74);
		panel.add(panelMantenimiento);
		
		imaNav = new ImageIcon("imagenes/botonAgregar.jpg");
		imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
		btnNuevo = new JButton("", imaNav);

		btnNuevo.setToolTipText("Insertar Nuevo Libro");
		btnNuevo.setBounds(15, 15, 40, 40);
		panelMantenimiento.add(btnNuevo);
		
		imaNav = new ImageIcon("imagenes/botonEditar.jpg");
		imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
		btnEditar = new JButton("", imaNav);
		btnEditar.setToolTipText("Editar");
		
		btnEditar.setBounds(65, 15, 40, 40);
		panelMantenimiento.add(btnEditar);
		imaNav = new ImageIcon("imagenes/botonGuardar.jpg");
		
		imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
		btnGuardar = new JButton("", imaNav);
		btnGuardar.setEnabled(false);
		btnGuardar.setToolTipText("Guardar");
		btnGuardar.setBounds(166, 15, 40, 40);
		panelMantenimiento.add(btnGuardar);
		
		imaNav = new ImageIcon("imagenes/botonDeshacer.jpg");
		imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
		btnDeshacer = new JButton("", imaNav);
		btnDeshacer.setEnabled(false);
		btnDeshacer.setToolTipText("Deshacer");
		btnDeshacer.setBounds(216, 15, 40, 40);
		panelMantenimiento.add(btnDeshacer);
		
		imaNav = new ImageIcon("imagenes/borrar.jpg");
		imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
		btnBorrar = new JButton("", imaNav);

		btnBorrar.setToolTipText("Borrar Registro");
		btnBorrar.setBounds(116, 15, 40, 40);
		panelMantenimiento.add(btnBorrar);
		
		panelGrid = new JPanel();
		panelGrid.setBounds(363, 98, 766, 305);
		panel.add(panelGrid);
		panelGrid.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		panelGrid.add(scrollPane_1, BorderLayout.CENTER);
		dtm=new DefaultTableModel();
		table = new JTable(dtm);
		scrollPane_1.setViewportView(table);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"titulo", "codigobarras", "genero"}));
		comboBox.setBounds(363, 31, 151, 36);
		panel.add(comboBox);
		
		textBuscar = new JTextField();
		textBuscar.setBounds(524, 39, 244, 28);
		panel.add(textBuscar);
		textBuscar.setColumns(10);
		
		btnMostrarTodo = new JButton("Mostrar Todo");
		btnMostrarTodo.setBounds(778, 38, 131, 28);
		panel.add(btnMostrarTodo);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(919, 38, 138, 28);
		panel.add(btnFiltrar);
		


		}
		private void habilitarPanelMantenimiento(boolean b) {
			btnNuevo.setEnabled(b);
			btnEditar.setEnabled(b);
			btnGuardar.setEnabled(!b);
			btnDeshacer.setEnabled(!b);
			btnBorrar.setEnabled(b);
			
		}
		private void habilitarPanelJuegos(boolean b) {
			textTitulo.setEditable(!b);
			textCodigo.setEditable(!b);
			textEstudio.setEditable(!b);
			textFecha.setEnabled(!b);
			textPrecio.setEditable(b);
			textGenero.setEditable(!b);
			
		}

		private void habilitarNavegador(boolean b) {
			btnAdelante.setEnabled(b);
			btnAtras.setEnabled(b);
			btnPrimero.setEnabled(b);
			btnUltimo.setEnabled(b);
			
		}
		private void habilitarPanelJuegosNuevo(boolean b) {
			textTitulo.setEditable(b);
			textTitulo.setEditable(b);
			textCodigo.setEditable(b);
			textEstudio.setEditable(b);
			textFecha.setEditable(b);
			textPrecio.setEditable(b);
			textGenero.setEditable(b);
			
		}
	}

