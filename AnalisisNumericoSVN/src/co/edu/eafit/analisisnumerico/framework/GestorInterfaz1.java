package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import co.edu.eafit.analisisnumerico.GUI.MetodoGUI1;

/**
 * Esta clase controla la interfaz dinamica numero 1
 * @author Sebastian
 *
 */
public class GestorInterfaz1 {
	MetodoGUI1 interfaz = new MetodoGUI1();
	private static final String TEXTOINICIO="";
	private static final String TEXTOFIN=":";
	int numVariables=0;
	int numFunciones=0;
	String[] nombresFunciones;
	
	public void otro()throws AnalisisException{
		if(numVariables>5)throw new AnalisisException("The number of fields cannot be more than 5");
	}
	
	/**
	 * pinta en pantalla la interfaz
	 * @param tipoMetodo tipometodo
	 * @param titulo el titulo
	 * @param numFunciones el num de funciones
	 * @param funciones
	 * @param strings
	 * @throws AnalisisException
	 */
	public void pintar(int tipoMetodo, String titulo, int numFunciones, String funciones, String...strings) throws AnalisisException{
		numVariables=strings.length;
		nombresFunciones=funciones.split(",");
		if((strings.length)>8)throw new AnalisisException("Esta interfaz solo permite un maximo de 8 entradas");
		if(strings.length==0||strings==null)throw new AnalisisException("Debe ingresar como minimo una entrada");
		coordinarLabelsYTextBox(strings);
		crearTitulo(titulo);
		generarEventos();
		interfaz.setVisible(true);
	}

	private void generarEventos() {
		interfaz.btnSolucionar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					GestorInterfaz1.this.obtenerDatos();
				} catch (AnalisisException e) {	}
			}
			
		});
	}

	protected void obtenerDatos() throws AnalisisException{
		String[] resultados = new String[8];
		String[] valoresFunciones = new String[numFunciones];
		Vector<Double> valores = new Vector<Double>();
		resultados[0]=interfaz.txt1.getText();
		resultados[1]=interfaz.txt2.getText();
		resultados[2]=interfaz.txt3.getText();
		resultados[3]=interfaz.txt4.getText();
		resultados[4]=interfaz.txt5.getText();
		resultados[5]=interfaz
		.txt6.getText();
		resultados[6]=interfaz.txt7.getText();
		resultados[7]=interfaz.txt8.getText();
		
		for(int i=0;i<numVariables+numFunciones;i++){
			if(i<numFunciones){
				if(!GestorMetodos.esFuncionCorrecta(resultados[i]))throw new AnalisisException("Por favor escriba correctamente la funcion numero "+(i+1));
				else{
					valoresFunciones[i]=resultados[i];
					//GestorMetodos.textoReferencia+=nombresFunciones[i]+" = "+resultados[i];
				}
			}
			else{
				try{
					double val = Double.parseDouble(resultados[i]);
					valores.add(val);
				}
				catch(Exception e){
					throw new AnalisisException("Entrada invalida en el campo número "+(i+1));
				}
			}
		}
		double[] resul = new double[valores.size()];
		for(int i=0;i<valores.size();i++){
			resul[i]=valores.get(i);
		}
		GestorMetodos.ejecutarMetodoUnidad1(resul);
	}

	private void crearTitulo(String titulo) {
		interfaz.setTitle(titulo);
		interfaz.lblTitulo.setText(titulo);
	}
	
	public static void main(String[] args){
		GestorInterfaz1 gi = new GestorInterfaz1();
		try {
			gi.pintar(Constantes.BISECCION,"Metodo de biseccion",1,"f", "x1", "xs","funcion");
		} catch (AnalisisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void coordinarLabelsYTextBox(String[] variables) {
		String[] s = new String[variables.length];
		int i=0;
		for(int j=0;i<s.length;i++){
			s[i]=variables[j];
			j++;
		}
		switch(s.length){
			case 1:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setVisible(false);
				interfaz.txt2.setVisible(false);
				interfaz.lbl3.setVisible(false);
				interfaz.txt3.setVisible(false);
				interfaz.lbl4.setVisible(false);
				interfaz.txt4.setVisible(false);
				interfaz.lbl5.setVisible(false);
				interfaz.txt5.setVisible(false);
				interfaz.lbl6.setVisible(false);
				interfaz.txt6.setVisible(false);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 2:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setVisible(false);
				interfaz.txt3.setVisible(false);
				interfaz.lbl4.setVisible(false);
				interfaz.txt4.setVisible(false);
				interfaz.lbl5.setVisible(false);
				interfaz.txt5.setVisible(false);
				interfaz.lbl6.setVisible(false);
				interfaz.txt6.setVisible(false);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 3:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setVisible(false);
				interfaz.txt4.setVisible(false);
				interfaz.lbl5.setVisible(false);
				interfaz.txt5.setVisible(false);
				interfaz.lbl6.setVisible(false);
				interfaz.txt6.setVisible(false);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 4:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setText(TEXTOINICIO+s[3]+TEXTOFIN);
				interfaz.lbl5.setVisible(false);
				interfaz.txt5.setVisible(false);
				interfaz.lbl6.setVisible(false);
				interfaz.txt6.setVisible(false);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 5:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setText(TEXTOINICIO+s[3]+TEXTOFIN);
				interfaz.lbl5.setText(TEXTOINICIO+s[4]+TEXTOFIN);
				interfaz.lbl6.setVisible(false);
				interfaz.txt6.setVisible(false);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 6:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setText(TEXTOINICIO+s[3]+TEXTOFIN);
				interfaz.lbl5.setText(TEXTOINICIO+s[4]+TEXTOFIN);
				interfaz.lbl6.setText(TEXTOINICIO+s[5]+TEXTOFIN);
				interfaz.lbl7.setVisible(false);
				interfaz.txt7.setVisible(false);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 7:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setText(TEXTOINICIO+s[3]+TEXTOFIN);
				interfaz.lbl5.setText(TEXTOINICIO+s[4]+TEXTOFIN);
				interfaz.lbl6.setText(TEXTOINICIO+s[5]+TEXTOFIN);
				interfaz.lbl7.setText(TEXTOINICIO+s[6]+TEXTOFIN);
				interfaz.lbl8.setVisible(false);
				interfaz.txt8.setVisible(false);
				break;
			case 8:
				interfaz.lbl1.setText(TEXTOINICIO+s[0]+TEXTOFIN);
				interfaz.lbl2.setText(TEXTOINICIO+s[1]+TEXTOFIN);
				interfaz.lbl3.setText(TEXTOINICIO+s[2]+TEXTOFIN);
				interfaz.lbl4.setText(TEXTOINICIO+s[3]+TEXTOFIN);
				interfaz.lbl5.setText(TEXTOINICIO+s[4]+TEXTOFIN);
				interfaz.lbl6.setText(TEXTOINICIO+s[5]+TEXTOFIN);
				interfaz.lbl7.setText(TEXTOINICIO+s[6]+TEXTOFIN);
				interfaz.lbl8.setText(TEXTOINICIO+s[7]+TEXTOFIN);
				break;
		}
	}
}

