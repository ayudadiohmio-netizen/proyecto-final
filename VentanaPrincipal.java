import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.PrintStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class VentanaPrincipal extends JFrame {

    // paleta de colores 
    private static final Color COLOR_FONDO = new Color(28, 26, 43);
    private static final Color COLOR_PANEL = new Color(40, 37, 61);
    private static final Color COLOR_ACENTO = new Color(230, 168, 55);   // dorado
    private static final Color COLOR_ACENTO_SUAVE = new Color(180, 140, 230); // morado claro
    private static final Color COLOR_TEXTO = new Color(235, 232, 245);
    private static final Color COLOR_BOTON = new Color(94, 63, 156);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_CONSOLA_FONDO = new Color(18, 17, 28);
    private static final Color COLOR_CONSOLA_TEXTO = new Color(140, 235, 170); // verde tipo terminal

    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.ITALIC, 13);
    private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FUENTE_CONSOLA = new Font("Consolas", Font.PLAIN, 13);

    private SistemaGestionJuego sistema = new SistemaGestionJuego();
    private JTextArea areaSalida;

    public VentanaPrincipal() {
        super("Sistema de gestion de un videojuego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());

        // ---------- Encabezado ----------
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new java.awt.GridLayout(2, 1));
        encabezado.setBackground(COLOR_FONDO);
        encabezado.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel titulo = new JLabel("\u2694  Sistema de Gestion de Quests  \u2694", SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_ACENTO);

        JLabel subtitulo = new JLabel("Cola de prioridad . Arbol binario . Recursividad . Divide y venceras . Hash . Grafos", SwingConstants.CENTER);
        subtitulo.setFont(FUENTE_SUBTITULO);
        subtitulo.setForeground(COLOR_ACENTO_SUAVE);

        encabezado.add(titulo);
        encabezado.add(subtitulo);

        // ---------- Pestañas ----------
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setBackground(COLOR_PANEL);
        pestañas.setForeground(COLOR_TEXTO);
        pestañas.setFont(FUENTE_BOTON);

        pestañas.addTab("\u2694 Misiones", crearPanelMisiones());
        pestañas.addTab("\u23F3 Cola de prioridad", crearPanelColaPrioridad());
        pestañas.addTab("\uD83E\uDDD9 Personajes", crearPanelPersonajes());
        pestañas.addTab("\uD83D\uDCCA Estadisticas", crearPanelEstadisticas());
        pestañas.addTab("\uD83D\uDD00 Distribucion", crearPanelDistribucion());
        pestañas.addTab("\uD83D\uDD78 Dependencias", crearPanelGrafo());
        pestañas.addTab("\uD83E\uDDEA Pruebas", crearPanelPruebas());

        // ---------- Consola de resultados ----------
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        areaSalida.setFont(FUENTE_CONSOLA);
        areaSalida.setBackground(COLOR_CONSOLA_FONDO);
        areaSalida.setForeground(COLOR_CONSOLA_TEXTO);
        areaSalida.setCaretColor(COLOR_CONSOLA_TEXTO);
        areaSalida.setBorder(new EmptyBorder(10, 10, 10, 10));

        // aqui esta el truco: de ahora en adelante, todo System.out.println
        // termina escrito en areaSalida en vez de en la consola
        System.setOut(new PrintStream(new TextAreaOutputStream(areaSalida), true));

        JScrollPane scrollSalida = new JScrollPane(areaSalida);
        scrollSalida.setBorder(crearBordeTitulado("Resultado"));
        scrollSalida.setPreferredSize(new Dimension(900, 250));

        JButton botonLimpiar = crearBoton("Limpiar resultado");
        botonLimpiar.addActionListener(e -> areaSalida.setText(""));
        JPanel panelBotonLimpiar = new JPanel();
        panelBotonLimpiar.setBackground(COLOR_FONDO);
        panelBotonLimpiar.setBorder(new EmptyBorder(8, 0, 0, 0));
        panelBotonLimpiar.add(botonLimpiar);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(new EmptyBorder(10, 20, 15, 20));
        panelInferior.add(scrollSalida, BorderLayout.CENTER);
        panelInferior.add(panelBotonLimpiar, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(COLOR_FONDO);
        panelCentro.setBorder(new EmptyBorder(0, 20, 0, 20));
        panelCentro.add(pestañas, BorderLayout.CENTER);

        add(encabezado, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        sistema.agregarPersonaje("Aldric", "Guerrero", 5);
        sistema.agregarPersonaje("Merin", "Mago", 4);
        sistema.agregarPersonaje("Sylas", "Arquero", 6);
        sistema.agregarPersonaje("Nyra", "Clerigo", 3);

        sistema.agregarMision("Derrotar al dragon del norte", "Principal", 1, 3, 500);
        sistema.agregarMision("Recolectar hierbas curativas", "Secundaria", 3, 5, 50);
        sistema.agregarMision("Entregar la carta al rey", "Principal", 2, 2, 200);
        sistema.agregarMision("Matar 10 lobos", "Diaria", 4, 1, 30);
        sistema.agregarMision("Escoltar a la caravana", "Secundaria", 1, 1, 150);

        // "Entregar la carta al rey" depende de "Derrotar al dragon del norte"
        // y "Matar 10 lobos" depende de "Entregar la carta al rey"
        sistema.agregarDependencia(3, 1);
        sistema.agregarDependencia(4, 3);

        areaSalida.append("\n\u2728 Se cargaron datos de ejemplo. Listo para usarse.\n");
        areaSalida.append("------------------------------------------------------\n");
    }

    // ---------- Pestaña: Misiones ----------

    private JPanel crearPanelMisiones() {
        JPanel panel = crearPanelBase(new BorderLayout());

        JPanel formulario = crearPanelBase(new GridLayout(0, 2, 8, 10));
        formulario.setBorder(crearBordeTitulado("Agregar mision"));

        JTextField campoNombre = crearCampoTexto();
        JComboBox<String> campoTipo = new JComboBox<>(new String[]{"Principal", "Secundaria", "Diaria"});
        JTextField campoPrioridad = crearCampoTexto("1");
        JTextField campoDias = crearCampoTexto("3");
        JTextField campoXp = crearCampoTexto("100");

        formulario.add(crearEtiqueta("Nombre de la mision:"));
        formulario.add(campoNombre);
        formulario.add(crearEtiqueta("Tipo:"));
        formulario.add(campoTipo);
        formulario.add(crearEtiqueta("Prioridad (1 muy urgente - 5 poco urgente):"));
        formulario.add(campoPrioridad);
        formulario.add(crearEtiqueta("Dias para expirar:"));
        formulario.add(campoDias);
        formulario.add(crearEtiqueta("XP de recompensa:"));
        formulario.add(campoXp);

        JButton botonAgregar = crearBoton("\u2795 Agregar mision");
        botonAgregar.addActionListener(e -> {
            try {
                String nombre = campoNombre.getText().trim();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ponle un nombre a la mision.");
                    return;
                }
                String tipo = (String) campoTipo.getSelectedItem();
                int prioridad = Integer.parseInt(campoPrioridad.getText().trim());
                int dias = Integer.parseInt(campoDias.getText().trim());
                int xp = Integer.parseInt(campoXp.getText().trim());
                sistema.agregarMision(nombre, tipo, prioridad, dias, xp);
                campoNombre.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prioridad, dias y xp deben ser numeros.");
            }
        });

        JPanel panelBusqueda = crearPanelBase(new GridLayout(0, 2, 8, 10));
        panelBusqueda.setBorder(crearBordeTitulado("Buscar / consultar"));

        JTextField campoIdBuscar = crearCampoTexto();
        JButton botonBuscarHash = crearBoton("\uD83D\uDD0D Buscar por id (tabla hash)");
        JButton botonBuscarBinario = crearBoton("\uD83D\uDD0E Buscar por id (busqueda binaria)");
        JButton botonMostrarOrdenadas = crearBoton("\uD83D\uDCCB Ver todas ordenadas");

        botonBuscarHash.addActionListener(e -> {
            Integer id = leerEntero(campoIdBuscar.getText());
            if (id != null) sistema.buscarMisionPorIdHash(id);
        });
        botonBuscarBinario.addActionListener(e -> {
            Integer id = leerEntero(campoIdBuscar.getText());
            if (id != null) sistema.buscarMisionPorIdBinario(id);
        });
        botonMostrarOrdenadas.addActionListener(e -> sistema.mostrarMisionesOrdenadas());

        panelBusqueda.add(crearEtiqueta("Id de la mision:"));
        panelBusqueda.add(campoIdBuscar);
        panelBusqueda.add(botonBuscarHash);
        panelBusqueda.add(botonBuscarBinario);
        panelBusqueda.add(botonMostrarOrdenadas);
        panelBusqueda.add(new JLabel(""));

        JPanel arriba = crearPanelBase(new GridLayout(2, 1, 0, 12));
        arriba.add(envolver(formulario, botonAgregar));
        arriba.add(panelBusqueda);

        panel.add(arriba, BorderLayout.NORTH);
        return panel;
    }

    // ---------- Pestaña: Cola de prioridad ----------

    private JPanel crearPanelColaPrioridad() {
        JPanel panel = crearPanelBase(new GridLayout(0, 1, 10, 12));
        panel.setBorder(crearBordeTitulado("Cola de prioridad de misiones"));

        JButton botonAtender = crearBoton("\u26A1 Atender la mision mas urgente");
        JButton botonMostrar = crearBoton("\uD83D\uDCCB Mostrar toda la cola");

        botonAtender.addActionListener(e -> sistema.atenderSiguienteUrgente());
        botonMostrar.addActionListener(e -> sistema.mostrarColaPrioridad());

        panel.add(envolverBotonSolo(botonAtender));
        panel.add(envolverBotonSolo(botonMostrar));
        return panel;
    }

    // ---------- Pestaña: Personajes ----------

    private JPanel crearPanelPersonajes() {
        JPanel panel = crearPanelBase(new BorderLayout());

        JPanel formulario = crearPanelBase(new GridLayout(0, 2, 8, 10));
        formulario.setBorder(crearBordeTitulado("Agregar personaje"));

        JTextField campoNombre = crearCampoTexto();
        JComboBox<String> campoClase = new JComboBox<>(new String[]{"Guerrero", "Mago", "Arquero", "Picaro", "Clerigo"});
        JTextField campoNivel = crearCampoTexto("1");

        formulario.add(crearEtiqueta("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(crearEtiqueta("Clase:"));
        formulario.add(campoClase);
        formulario.add(crearEtiqueta("Nivel:"));
        formulario.add(campoNivel);

        JButton botonAgregar = crearBoton("\u2795 Agregar personaje");
        botonAgregar.addActionListener(e -> {
            try {
                String nombre = campoNombre.getText().trim();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ponle un nombre al personaje.");
                    return;
                }
                String clase = (String) campoClase.getSelectedItem();
                int nivel = Integer.parseInt(campoNivel.getText().trim());
                sistema.agregarPersonaje(nombre, clase, nivel);
                campoNombre.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El nivel debe ser un numero.");
            }
        });

        JPanel panelConsulta = crearPanelBase(new GridLayout(0, 2, 8, 10));
        panelConsulta.setBorder(crearBordeTitulado("Buscar / consultar"));

        JTextField campoIdBuscar = crearCampoTexto();
        JButton botonBuscar = crearBoton("\uD83D\uDD0D Buscar por id");
        JButton botonMostrarTodos = crearBoton("\uD83D\uDCCB Mostrar agrupados por clase");

        botonBuscar.addActionListener(e -> {
            Integer id = leerEntero(campoIdBuscar.getText());
            if (id != null) sistema.buscarPersonajePorId(id);
        });
        botonMostrarTodos.addActionListener(e -> sistema.mostrarPersonajesPorClase());

        panelConsulta.add(crearEtiqueta("Id del personaje:"));
        panelConsulta.add(campoIdBuscar);
        panelConsulta.add(botonBuscar);
        panelConsulta.add(botonMostrarTodos);

        JPanel arriba = crearPanelBase(new GridLayout(2, 1, 0, 12));
        arriba.add(envolver(formulario, botonAgregar));
        arriba.add(panelConsulta);

        panel.add(arriba, BorderLayout.NORTH);
        return panel;
    }

    // ---------- Pestaña: Estadisticas ----------

    private JPanel crearPanelEstadisticas() {
        JPanel panel = crearPanelBase(new GridLayout(0, 1, 10, 12));
        panel.setBorder(crearBordeTitulado("Estadisticas de las misiones (con recursividad)"));

        JButton boton = crearBoton("\uD83D\uDCCA Calcular estadisticas");
        boton.addActionListener(e -> sistema.mostrarEstadisticas());

        panel.add(envolverBotonSolo(boton));
        return panel;
    }

    // ---------- Pestaña: Distribucion ----------

    private JPanel crearPanelDistribucion() {
        JPanel panel = crearPanelBase(new GridLayout(0, 1, 10, 12));
        panel.setBorder(crearBordeTitulado("Distribuir misiones entre personajes (divide y venceras)"));

        JButton boton = crearBoton("\uD83D\uDD00 Distribuir misiones");
        boton.addActionListener(e -> sistema.distribuirMisionesEntrePersonajes());

        panel.add(envolverBotonSolo(boton));
        return panel;
    }

    // ---------- Pestaña: Grafo de dependencias ----------

    private JPanel crearPanelGrafo() {
        JPanel panel = crearPanelBase(new BorderLayout());

        JPanel formulario = crearPanelBase(new GridLayout(0, 2, 8, 10));
        formulario.setBorder(crearBordeTitulado("Agregar dependencia entre misiones"));

        JTextField campoIdMision = crearCampoTexto();
        JTextField campoIdRequisito = crearCampoTexto();

        formulario.add(crearEtiqueta("Id de la mision:"));
        formulario.add(campoIdMision);
        formulario.add(crearEtiqueta("Id de la mision de la que depende:"));
        formulario.add(campoIdRequisito);

        JButton botonAgregar = crearBoton("\u2795 Agregar dependencia");
        botonAgregar.addActionListener(e -> {
            Integer idMision = leerEntero(campoIdMision.getText());
            Integer idRequisito = leerEntero(campoIdRequisito.getText());
            if (idMision != null && idRequisito != null) {
                sistema.agregarDependencia(idMision, idRequisito);
            }
        });

        JPanel panelBotones = crearPanelBase(new GridLayout(0, 1, 8, 10));
        JButton botonVerGrafo = crearBoton("\uD83D\uDD78 Ver el grafo completo");
        JButton botonVerOrden = crearBoton("\u2705 Ver orden sugerido");
        botonVerGrafo.addActionListener(e -> sistema.mostrarGrafo());
        botonVerOrden.addActionListener(e -> sistema.mostrarOrdenSugerido());
        panelBotones.add(botonVerGrafo);
        panelBotones.add(botonVerOrden);

        JPanel arriba = crearPanelBase(new GridLayout(2, 1, 0, 12));
        arriba.add(envolver(formulario, botonAgregar));
        arriba.add(panelBotones);

        panel.add(arriba, BorderLayout.NORTH);
        return panel;
    }

    // ---------- Pestaña: Pruebas automaticas ----------

    private JPanel crearPanelPruebas() {
        JPanel panel = crearPanelBase(new GridLayout(0, 1, 10, 12));
        panel.setBorder(crearBordeTitulado("Pruebas automaticas de las 7 estructuras"));

        JButton boton = crearBoton("\uD83E\uDDEA Correr pruebas automaticas");
        boton.addActionListener(e -> PruebasAutomaticas.correr());

        panel.add(envolverBotonSolo(boton));
        return panel;
    }

    // ---------- Utilidades de estilo ----------

    private JPanel crearPanelBase(java.awt.LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(COLOR_PANEL);
        return panel;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_ETIQUETA);
        etiqueta.setForeground(COLOR_TEXTO);
        return etiqueta;
    }

    private JTextField crearCampoTexto() {
        return crearCampoTexto("");
    }

    private JTextField crearCampoTexto(String valorInicial) {
        JTextField campo = new JTextField(valorInicial);
        campo.setFont(FUENTE_ETIQUETA);
        campo.setBackground(Color.WHITE);
        campo.setForeground(new Color(30, 30, 30));
        campo.setCaretColor(new Color(30, 30, 30));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO_SUAVE, 1),
                new EmptyBorder(4, 6, 4, 6)
        ));
        return campo;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO, 1),
                new EmptyBorder(8, 14, 8, 14)
        ));
        return boton;
    }

    private TitledBorder crearBordeTitulado(String titulo) {
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO_SUAVE, 1),
                titulo
        );
        borde.setTitleFont(FUENTE_BOTON);
        borde.setTitleColor(COLOR_ACENTO);
        return borde;
    }

    private JPanel envolver(JPanel formulario, JButton boton) {
        JPanel contenedor = crearPanelBase(new BorderLayout());
        JPanel panelBoton = crearPanelBase(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        panelBoton.add(boton);
        contenedor.add(formulario, BorderLayout.CENTER);
        contenedor.add(panelBoton, BorderLayout.SOUTH);
        return contenedor;
    }

    private JPanel envolverBotonSolo(JButton boton) {
        JPanel contenedor = crearPanelBase(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        contenedor.add(boton);
        return contenedor;
    }

    private Integer leerEntero(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Escribe un numero valido.");
            return null;
        }
    }
}
