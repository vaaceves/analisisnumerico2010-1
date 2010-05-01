package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;



import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Impresion {

    public static void mostrarResultado(double[][] resultado, double[] solucionX, int[] variables, boolean cambioVariables, String[] columnas, String metodo, int n) {

        //para matriz ppal
        Double[][] data = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Double dObj = new Double(resultado[i][j]);
                data[i][j] = dObj;
            }
            columnas[i] = " ";
        }

        //crear tablas
        DefaultTableModel dtm = new DefaultTableModel(data, columnas);
        JTable table = new JTable(data, columnas);
        table.setModel(dtm);
        JScrollPane scrollPane1 = new JScrollPane(table);

        //para soluciones
        Double[][] data2 = new Double[1][n];
        for (int i = 0; i < n; i++) {
            Double dObj = new Double(solucionX[i]);
            data2[0][i] = dObj;
            if (cambioVariables) {
                columnas[i] = "x" + variables[i];
            } else {
                columnas[i] = "x" + i;
            }
        }
        //crear tablas
        DefaultTableModel dtm2 = new DefaultTableModel(data2, columnas);
        JTable solucion = new JTable(data2, columnas);
        solucion.setModel(dtm2);
        JScrollPane scrollPane2 = new JScrollPane(solucion);
        scrollPane2.createHorizontalScrollBar();

        //crear ventana
        JFrame frame = new JFrame(metodo);
        JLabel titulo = new JLabel("La matriz resultado:");
        JLabel titulo2 = new JLabel("El resultado al sistema de ecuaciones es:");

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(titulo)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(titulo2)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20).addComponent(titulo).addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 250, GroupLayout.DEFAULT_SIZE).addGap(15).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(titulo2).addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 185, GroupLayout.DEFAULT_SIZE)).addGap(5, 5, 5)));

        //acomodar ventana
        frame.setLocation(400, 200);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(500, 280);
        frame.setVisible(true);
    }

    public static void mostrarResultadoLU(double[][] l, double[][] u, double[] solucionX, double[] solucionZ, String[] columnas, String metodo, int n) {

        //para matriz L
        Double[][] datal = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Double dObj = new Double(l[i][j]);
                datal[i][j] = dObj;
            }
            columnas[i] = " ";
        }
        //crear tabla L
        DefaultTableModel dtmL = new DefaultTableModel(datal, columnas);
        JTable tableL = new JTable(datal, columnas);
        tableL.setModel(dtmL);
        JScrollPane scrollPaneL = new JScrollPane(tableL);

        //para matriz U
        Double[][] dataU = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Double dObj = new Double(u[i][j]);
                dataU[i][j] = dObj;
            }
            columnas[i] = " ";
        }
        //crear tabla U
        DefaultTableModel dtmU = new DefaultTableModel(dataU, columnas);
        JTable tableU = new JTable(dataU, columnas);
        tableU.setModel(dtmU);
        JScrollPane scrollPaneU = new JScrollPane(tableU);

        //para solucion Z
        Double[][] dataZ = new Double[1][n];
        for (int i = 0; i < n; i++) {
            Double dObj = new Double(solucionZ[i]);
            dataZ[0][i] = dObj;
            columnas[i] = "z" + i;
        }
        //crear tabla para soluciones z
        DefaultTableModel dtmZ = new DefaultTableModel(dataZ, columnas);
        JTable solZ = new JTable(dataZ, columnas);
        solZ.setModel(dtmZ);
        JScrollPane scrollPaneZ = new JScrollPane(solZ);
        scrollPaneZ.createHorizontalScrollBar();

        //para solucion X
        Double[][] dataX = new Double[1][n];
        for (int i = 0; i < n; i++) {
            Double dObj = new Double(solucionX[i]);
            dataX[0][i] = dObj;
            columnas[i] = "x" + i;
        }
        //crear tabla para soluciones x
        DefaultTableModel dtmX = new DefaultTableModel(dataX, columnas);
        JTable solX = new JTable(dataX, columnas);
        solX.setModel(dtmX);
        JScrollPane scrollPaneX = new JScrollPane(solX);
        scrollPaneX.createHorizontalScrollBar();

        //crear ventana
        JFrame frame = new JFrame(metodo);
        JLabel tituloL = new JLabel("La matriz L:");
        JLabel tituloU = new JLabel("La matriz U:");
        JLabel tituloZ = new JLabel("El vector z es:");
        JLabel tituloX = new JLabel("El resultado al sistema de ecuaciones es:");

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(tituloL).addGap(315).addComponent(tituloU)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPaneL, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(scrollPaneU, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(tituloZ).addGap(303).addComponent(tituloX)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPaneZ, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE).addGap(30, 30, 30).addComponent(scrollPaneX, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20).addComponent(tituloL).addComponent(scrollPaneL, GroupLayout.DEFAULT_SIZE, 250, GroupLayout.DEFAULT_SIZE).addGap(10, 10, 10).addComponent(tituloZ).addComponent(scrollPaneZ, GroupLayout.DEFAULT_SIZE, 185, GroupLayout.DEFAULT_SIZE).addGap(15)).addGroup(layout.createSequentialGroup().addGap(20).addComponent(tituloU).addComponent(scrollPaneU, GroupLayout.DEFAULT_SIZE, 250, GroupLayout.DEFAULT_SIZE).addGap(10, 10, 10).addComponent(tituloX).addComponent(scrollPaneX, GroupLayout.DEFAULT_SIZE, 185, GroupLayout.DEFAULT_SIZE).addGap(15)));

        //acomodar ventana
        frame.setLocation(280, 200);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(820, 280);
        frame.setVisible(true);
    }

    public static void mostrarResultadoIterativos(double[][] resultado, double[] solucionX, String[] columnas, String metodo, int n, int ite) {
        //para ver tamaño de matriz ppal
        int cont = 0;
        for (int i = 1; i <= ite; i++) {
            if (resultado[i][0] != 0) {
                cont++;
            }
        }

        //para matriz ppal
        Double[][] data = new Double[cont][n + 2];
        for (int i = 0; i < cont; i++) {
            for (int j = 0; j < n + 2; j++) {
                Double dObj = new Double(resultado[i][j]);
                data[i][j] = dObj;
            }
        }
        //crear tabla a matriz ppal
        DefaultTableModel dtm = new DefaultTableModel(data, columnas);
        JTable table = new JTable(data, columnas);
        table.setModel(dtm);
        JScrollPane scrollPane1 = new JScrollPane(table);

        //para soluciones
        Double[][] data2 = new Double[1][n];
        String[] col = new String[n];
        for (int i = 0; i < n; i++) {
            Double dObj = new Double(solucionX[i]);
            data2[0][i] = dObj;
            col[i] = "x" + i;
        }
        //crear tabla a soluciones
        DefaultTableModel dtm2 = new DefaultTableModel(data2, col);
        JTable solucion = new JTable(data2, col);
        solucion.setModel(dtm2);
        JScrollPane scrollPane2 = new JScrollPane(solucion);
        scrollPane2.createHorizontalScrollBar();

        //crear ventana
        JFrame frame = new JFrame(metodo);
        JLabel titulo = new JLabel("Resultados parciales:");
        JLabel titulo2 = new JLabel("El resultado es:");

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(titulo)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(titulo2)).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20).addComponent(titulo).addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 250, GroupLayout.DEFAULT_SIZE).addGap(15).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(titulo2).addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 185, GroupLayout.DEFAULT_SIZE)).addGap(5, 5, 5)));

        //acomodar ventana
        frame.setLocation(400, 200);
        frame.setResizable(false);

        frame.pack();
        frame.setSize(500, 280);
        frame.setVisible(true);
    }
}
