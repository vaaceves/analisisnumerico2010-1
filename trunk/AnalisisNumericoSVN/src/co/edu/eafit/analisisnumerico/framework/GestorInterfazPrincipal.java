package co.edu.eafit.analisisnumerico.framework;

import co.edu.eafit.analisisnumerico.GUI.MenuPrincipal;

public class GestorInterfazPrincipal {

	private static GestorInterfazPrincipal gestorInterfazPrincipal;
	private MenuPrincipal gui;
	
	private GestorInterfazPrincipal() {
		
	}
	
	public static GestorInterfazPrincipal getInstance(){
		if(gestorInterfazPrincipal==null){
			gestorInterfazPrincipal=new GestorInterfazPrincipal();
		}
		return gestorInterfazPrincipal;
	}
	
	public void iniciar(){
		gui = new MenuPrincipal();
		gui.setVisible(true);
	}
	
	/**
	 * inicia la interfaz y el objeto de logica para el manejo de la unidad 1
	 */
	public static void iniciarUnidad1(){
		PrincipalUnidad1.getInstance().iniciar();
	}
	
	/**inicia la interfaz y el objeto de logica de la unidad 2*/
	public static void iniciarSistemasEcuaciones(){
		GestorInterfazPrincipalSistemasEcuaciones.getInstance().iniciar();
	}

	public static void iniciarGraficador() {
		GraficadorClasico graficador = new GraficadorClasico();
		graficador.setVisible(true);
	}
}
