package co.edu.eafit.analisisnumerico.framework;


import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.nfunk.jep.JEP;

import co.edu.eafit.analisisnumerico.GUI.ResultadosInterfaz1;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.cincopuntos.DiferenciacionCincoPuntosCentrada;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.cincopuntos.DiferenciacionCincoPuntosProgresiva;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.cincopuntos.DiferenciacionCincoPuntosRegresiva;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.dospuntos.DiferenciacionDosPuntosProgresiva;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.dospuntos.DiferenciacionDosPuntosRegresiva;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.trespuntos.DiferenciacionTresPuntosCentrada;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.trespuntos.DiferenciacionTresPuntosProgresiva;
import co.edu.eafit.analisisnumerico.metodos.diferenciacion.trespuntos.DiferenciacionTresPuntosRegresiva;
import co.edu.eafit.analisisnumerico.metodos.integracion.SimpsonTresOctavos;
import co.edu.eafit.analisisnumerico.metodos.integracion.SimpsonUnTercio;
import co.edu.eafit.analisisnumerico.metodos.integracion.SimpsonUnTercioGeneralizado;
import co.edu.eafit.analisisnumerico.metodos.integracion.TrapecioGeneralizado;
import co.edu.eafit.analisisnumerico.metodos.integracion.TrapecioSencillo;
import co.edu.eafit.analisisnumerico.metodos.interpolacion.Lagrange;
import co.edu.eafit.analisisnumerico.metodos.interpolacion.Neville;
import co.edu.eafit.analisisnumerico.metodos.interpolacion.NewtonDiferenciasDivididas;
import co.edu.eafit.analisisnumerico.metodos.iterativos.Biseccion;
import co.edu.eafit.analisisnumerico.metodos.iterativos.BusquedasIncrementales;
import co.edu.eafit.analisisnumerico.metodos.iterativos.Newton;
import co.edu.eafit.analisisnumerico.metodos.iterativos.PuntoFijo;
import co.edu.eafit.analisisnumerico.metodos.iterativos.RaicesMultiples;
import co.edu.eafit.analisisnumerico.metodos.iterativos.ReglaFalsa;
import co.edu.eafit.analisisnumerico.metodos.iterativos.Secante;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.Cholesky;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.Croult;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.Dolytle;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.GaussSeidel;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.GaussSimple;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.Jacoby;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.LUParcial;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.LUSimple;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.MatrizBanda;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.PivoteoEscalonado;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.PivoteoParcial;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.PivoteoTotal;
import co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones.Relajacion;

/**
 * Clase Gestor Metodos. Posee la fabrica de metodos y la ejecucion dinamica de los mismos para todas las unidades
 * @author Sebastian
 *
 */
public class GestorMetodos {
	static MetodoUnidad1 mPadreU1;
	static MetodoUnidad2 mPadreU2;
	static MetodoUnidad3 mPadreU3;
	private static String funciones;
	private static int modo;
	private static final String TEXTOINICIO="";
	private static final String TEXTOFIN=":";

	/**Variable global que contiene el metodo activo en el momento*/
	public static int metodoEjecutandose;

	/**Nombres y orden de las variables (delta, iteraciones, etc) que pueden modificarse en la salida*/
	public static String[] variablesModificables; 


	/**
	 * Ejecuta un metodo dinamicamente, de acuerdo a los parametros recibidos
	 * @param numMetodo: Constante del metodo a ejecutar
	 * @param modo: Indica si es modo consola o modo grafico
	 * @param valoresPredefinidos: Array con valores por defecto, o null, para pedirselos al usuario
	 * @param strings: Lista de valores a solicitarle al usuario
	 */
	public static void ejecutar(int numMetodo, int modo, String titulo, String funciones, String[] funcionesPredeterminadas, double[] valoresPredefinidos, String...strings){
		variablesModificables=strings;
		metodoEjecutandose=numMetodo;
		MetodoUnidad1 mp = GestorMetodos.fabricaMetodosUnidad1(numMetodo);
		GestorMetodos.modo=modo;
		GestorMetodos.mPadreU1=mp;
		GestorMetodos.funciones=funciones;
		if(modo==Constantes.MODOCONSOLA){
			if(funciones!=null&&funciones!=""){
				crearFunciones(funcionesPredeterminadas);
			}
			double[] entradas=null;
			if(valoresPredefinidos==null){
				try {
					entradas = solicitarListaDoubleConsola(strings);
				} catch (AnalisisException e) {	

				}
			}
			else{
				entradas=valoresPredefinidos;
			}
			ejecutarMetodoUnidad1(entradas);
		}
		else if(modo==Constantes.MODOGRAFICOINTERFAZ1){
			boolean resul = validarFuncionesNecesarias();
			if(resul){
				GestorInterfaz1 gi1 = new GestorInterfaz1();
				try {
					gi1.pintar(numMetodo,titulo, funciones.split(",").length, funciones,  strings);
				} catch (AnalisisException e) {}
			}

		}
	}

	public static void ejecutarIntegracion(int metodo, JEP parser,
			double delta, double limiteInferior, double limiteSuperior,
			int particiones, TextArea campoResultado) {
		MetodoUnidad5 mp = null;
		mp = GestorMetodos.fabricaMetodosUnidad5(metodo);
		mp.inicializarParser(parser); 
		String resultado ="";
		DecimalFormat resulFormat = new DecimalFormat("###.####");
		try {
			resultado = "Resultado de la diferenciación:" + 
				resulFormat.format(
						mp.metodoIntegracion(delta, limiteInferior, limiteSuperior, particiones));
		} catch (AnalisisException e) {
			e.printStackTrace();
			return;
		}
		campoResultado.setText(resultado);
	}

	public static void ejecutarDiferenciacion(int metodo, JEP parser,
			double delta, double valor, TextArea campoResultado) {
		MetodoUnidad4 mp = null;
		mp = GestorMetodos.fabricaMetodosUnidad4(metodo);
		mp.inicializarParser(parser);
		String resultado ="";
		DecimalFormat resulFormat = new DecimalFormat("###.####");
		try {
			resultado = "Resultado de la diferenciación para "+valor
							+"\ncon un delta de "+delta+"\n = "+resulFormat.format(mp.metodoDiferenciacion(valor, delta));
		} catch (AnalisisException e) {
			e.printStackTrace();
			return;
		}
		campoResultado.setText(resultado);
	}

	public static void ejecutarInterpolacion(final int numMetodo, final String titulo, final double[][] matriz, final double valorAInterpolar){
		MetodoUnidad3 mp = null;
		mp = GestorMetodos.fabricaMetodosUnidad3(numMetodo);
		if(mp==null)return;
		GestorMetodos.mPadreU3=mp;
		String resultado="";
		try {
			//ejecuta el metodo
			resultado = mp.metodoInterpolacion(matriz,valorAInterpolar);
		} catch (AnalisisException e) {}
		String[] titulos;
		if(mp.datos!=null){
			titulos = new String[mp.datos.size()];
			for(int i=0;i<titulos.length;i++)titulos[i]="";	
		}
		else{
			titulos = new String[mp.matrizResultado[0].length];
			for(int i=0;i<titulos.length;i++)titulos[i]="";
		}
		/*MUESTRA LA INTERFAZ DE SALIDA*/
		final ResultadosInterfaz1 ri1= new ResultadosInterfaz1();
		ri1.tablaResultados.setModel(new DefaultTableModel(mp.generarMatrizString(), titulos));
		ri1.txtDatosGenerales.setText(generarValoresEnString(matriz));
		ri1.lblResultado.setText("Resultado: "+resultado);
		ri1.lblVariable2.setVisible(false);
		ri1.lblVariable3.setVisible(false);
		ri1.lblVariable4.setVisible(false);
		ri1.txtVariable2.setVisible(false);
		ri1.txtVariable3.setVisible(false);
		ri1.txtVariable4.setVisible(false);
		ri1.jScrollPane3.setVisible(false);
		ri1.tblB.setVisible(false);
		ri1.lblVectorB.setVisible(false);
		ri1.lblVariable1.setText("Valor a interpolar:");
		ri1.btnRecalcular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double nuevoValorAInterpolar;
				try{
				nuevoValorAInterpolar=Double.parseDouble(ri1.txtVariable1.getText());	
				}
				catch(Exception e){
					new AnalisisException("Valor inválido");
					return;
				}
				ri1.setVisible(false);
				mPadreU3.resetDatos();
				ejecutarInterpolacion(numMetodo, titulo, matriz, nuevoValorAInterpolar);
			}	
		});

		ri1.setVisible(true);
	}
	
	
	private static String generarValoresEnString(double[][] matriz) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Valores dados: \n");
		for(int i=0;i<matriz.length;i++){
			buffer.append("f("+matriz[i][0]+") = "+matriz[i][1]);
			buffer.append("\n");
		}
		return buffer.toString();
	}

	private static MetodoUnidad5 fabricaMetodosUnidad5(int metodo) {
		if(metodo==Constantes.SIMPSONTRESOCTAVOS)return new SimpsonTresOctavos();
		if(metodo==Constantes.SIMPSONUNTERCIO)return new SimpsonUnTercio();
		if(metodo==Constantes.SIMPSONUNTERCIOGENERALIZADO)return new SimpsonUnTercioGeneralizado();
		if(metodo==Constantes.TRAPECIOSENCILLO)return new TrapecioSencillo();
		if(metodo==Constantes.TRAPECIOGENERALIZADO)return new TrapecioGeneralizado();
		return null;
	}
	
	private static MetodoUnidad4 fabricaMetodosUnidad4(int metodo) {
		if(metodo==Constantes.DIFERENCIACION2PTOSPROGRESIVA)return new DiferenciacionDosPuntosProgresiva();
		if(metodo==Constantes.DIFERENCIACION2PTOSREGRESIVA)return new DiferenciacionDosPuntosRegresiva();
		if(metodo==Constantes.DIFERENCIACION3PTOSCENTRADA)return new DiferenciacionTresPuntosCentrada();
		if(metodo==Constantes.DIFERENCIACION3PTOSPROGRESIVA)return new DiferenciacionTresPuntosProgresiva();
		if(metodo==Constantes.DIFERENCIACION3PTOSREGRESIVA)return new DiferenciacionTresPuntosRegresiva();
		if(metodo==Constantes.DIFERENCIACION5PTOSCENTRADA)return new DiferenciacionCincoPuntosCentrada();
		if(metodo==Constantes.DIFERENCIACION5PTOSPROGRESIVA)return new DiferenciacionCincoPuntosProgresiva();
		if(metodo==Constantes.DIFERENCIACION5PTOSREGRESIVA)return new DiferenciacionCincoPuntosRegresiva();
		return null;
	}
	
	private static MetodoUnidad3 fabricaMetodosUnidad3(int metodo) {
		if(metodo==Constantes.NEWTONDIFERENCIASDIVIDIDAS) return new NewtonDiferenciasDivididas();
		if(metodo==Constantes.LAGRANGE) return new Lagrange();
		if(metodo==Constantes.NEVILLE) return new Neville();
		new AnalisisException("ERROR DE PROGRAMACION: DEBE ADICIONAR EL METODO EN GESTOR METODOS: FABRICAMETODOS UNIDAD 3");
		return null;
	}

	public static void ejecutarSistemaEcuacion(final int numMetodo, final String titulo, final Object[][] matriz, final double... valores){
		if(!validarMatriz(matriz)){
			return;
		}
		MetodoUnidad2 mp = null;
		try {
			mp = GestorMetodos.fabricaMetodosUnidad2(numMetodo, matriz);
		} catch (AnalisisException e1) {
			return;
		}
		if(mp==null)return;
		GestorMetodos.mPadreU2=mp;
		String resultado="";
		try {
			/*ejecuta el metodo*/
			resultado = mPadreU2.metodoSistema(valores);
		} catch (AnalisisException e) {
			e.printStackTrace();
		}
		String[] titulos;
		if(mp.datos!=null){
			titulos = new String[mp.datos.size()];
			for(int i=0;i<titulos.length;i++)titulos[i]="";	
		}
		else{
			titulos = new String[1];
			for(int i=0;i<titulos.length;i++)titulos[i]="";
		}
		/*MUESTRA LA INTERFAZ DE SALIDA*/
		final ResultadosInterfaz1 ri1= new ResultadosInterfaz1();
		ri1.tablaResultados.setModel(new DefaultTableModel(mp.generarMatrizSinTitulos(), titulos));
		ri1.txtDatosGenerales.setText(generarMatrizEnString(mp.matrizOriginal));
		ri1.lblResultado.setText("Resultado: "+resultado);
		//si los  metodos no tienen parametros, suerte,	
		if(numMetodo!=Constantes.JACOBI&&numMetodo!=Constantes.GAUSSSEIDEL&&numMetodo!=Constantes.RELAJACION){
			ri1.lblVariable1.setVisible(false);
			ri1.lblVariable2.setVisible(false);
			ri1.lblVariable3.setVisible(false);
			ri1.lblVariable4.setVisible(false);
			ri1.txtVariable1.setVisible(false);
			ri1.txtVariable2.setVisible(false);
			ri1.txtVariable3.setVisible(false);
			ri1.txtVariable4.setVisible(false);
			ri1.jScrollPane3.setVisible(false);
			ri1.tblB.setVisible(false);
			ri1.lblVectorB.setVisible(false);
			ri1.btnRecalcular.setVisible(false);
		}
		else{
			if(numMetodo!=Constantes.RELAJACION){

				ri1.lblVariable1.setText("Tolerancia: ");
				ri1.lblVariable2.setText("Iteraciones: ");
				ri1.lblVariable3.setVisible(false);
				ri1.lblVariable4.setVisible(false);
				ri1.txtVariable3.setVisible(false);
				ri1.txtVariable4.setVisible(false);
				Object[][] datos = new Object[1][matriz.length];
				String[] titulosX = new String[matriz.length];
				for(int i=0;i<titulosX.length;i++){
					titulosX[i]="X"+(i+1);
				}
				ri1.tblB.setModel(new DefaultTableModel(datos, titulosX));
				ri1.btnRecalcular.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						double[] nuevosValores = new double[valores.length];
						try{
							nuevosValores[0]=Integer.parseInt(ri1.txtVariable1.getText());
							nuevosValores[1]=Integer.parseInt(ri1.txtVariable2.getText());
							for(int i=0;i<matriz.length;i++){
								nuevosValores[i+2]=Double.parseDouble(ri1.tblB.getValueAt(0, i).toString());
							}
						}catch(Exception ex){
							new AnalisisException("ENTRADA INVALIDA");
							return;
						}
						ri1.setVisible(false);
						mPadreU2.resetDatos();
						ejecutarSistemaEcuacion(numMetodo, titulo, matriz, nuevosValores);
					}	
				});

			}
			else{

				ri1.lblVariable1.setText("Tolerancia: ");
				ri1.lblVariable2.setText("Iteraciones: ");
				ri1.lblVariable3.setText("Lambda: ");
				ri1.lblVariable4.setVisible(false);
				ri1.txtVariable4.setVisible(false);
				Object[][] datos = new Object[1][matriz.length];
				String[] titulosX = new String[matriz.length];
				for(int i=0;i<titulosX.length;i++){
					titulosX[i]="X"+(i+1);
				}
				ri1.tblB.setModel(new DefaultTableModel(datos, titulosX));
				ri1.btnRecalcular.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						double[] nuevosValores = new double[valores.length];
						try{
							nuevosValores[0]=Integer.parseInt(ri1.txtVariable1.getText());
							nuevosValores[1]=Integer.parseInt(ri1.txtVariable2.getText());
							nuevosValores[2]=Double.parseDouble(ri1.txtVariable3.getText());
							for(int i=0;i<matriz.length;i++){
								nuevosValores[i+3]=Double.parseDouble(ri1.tblB.getValueAt(0, i).toString());
							}
						}catch(Exception ex){
							new AnalisisException("ENTRADA INVALIDA");
							return;
						}
						ri1.setVisible(false);
						mPadreU2.resetDatos();
						ejecutarSistemaEcuacion(numMetodo, titulo, matriz, nuevosValores);
					}	
				});

			}
		}
		ri1.setVisible(true);
	}

	public static boolean validarMatriz(Object[][] matriz) {
		if(matriz==null){
			new AnalisisException("Matriz nula");
			return false;
		}
		if(matriz.length==0||matriz[0].length==0){
			new AnalisisException("Matriz nula");
			return false;
		}
		int i=0;
		int j=0;
		try{
			for(i=0;i<matriz.length;i++){
				for(j=0;j<matriz.length;j++){
					if(matriz[i][j]==null||matriz[i][j].toString()==""){
						new AnalisisException("Valor invalido en la posición "+(i+1+", "+(j+1)));
						return false;
					}
					Double.parseDouble(matriz[i][j].toString());
				}
			}
		}
		catch(Exception e){
			new AnalisisException("Valor invalido en la posición "+(i+1+", "+(j+1)));
			return false;
		}
		return true;
	}

	private static String generarMatrizEnString(DatoMatriz[][] matrizOriginal) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Matriz Original: \n");
		for(int i=0;i<matrizOriginal.length;i++){
			for(int j=0;j<matrizOriginal[0].length;j++){
				buffer.append(matrizOriginal[i][j].getValor()+"\t");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	private static String generarMatrizEnString(double[][] matrizOriginal) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Matriz Original: \n");
		for(int i=0;i<matrizOriginal.length;i++){
			for(int j=0;j<matrizOriginal[0].length;j++){
				buffer.append(matrizOriginal[i][j]+"\t");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}

	private static boolean validarFuncionesNecesarias() {
		PrincipalUnidad1 p = PrincipalUnidad1.getInstance();
		boolean fOk = p.crearFuncionesNuevas();
		if(!fOk)return false;
		String[] funcionesNecesarias = funciones.split(",");
		for(String s : funcionesNecesarias){
			if(s.equals("f")&&MetodoUnidad1.getParserF()==null){
				new AnalisisException("La Funcion f(x) es necesaria para este método y no se ha inicializado");
				return false;
			}
			else if(s.equals("g")&&MetodoUnidad1.getParserG()==null){
				new AnalisisException("La Funcion g(x) es necesaria para este método y no se ha inicializado");
				return false;
			}
			else if(s.equals("fdev")&&MetodoUnidad1.getParserFdev()==null){
				new AnalisisException("La primera derivada de f(x) es necesaria para este método y no se ha inicializado");
				return false;
			}
			else if(s.equals("fdd")&&MetodoUnidad1.getParserFdd()==null){
				new AnalisisException("La segunda derivada de f(x) es necesaria para este método y no se ha inicializado");
				return false;
			}
		}
		return true;
	}

	private static void ejecutarMetodoConsola(double... entradas) {
		String resultado="";
		try {
			resultado = mPadreU1.metodo(entradas);
		} catch (AnalisisException e) {}
		System.out.println("-------------------------------");
		System.out.println(resultado);
		System.out.println("-------------------------------");
		Object[][] resul = mPadreU1.generarMatriz();
		UtilConsola.imprimir(resul);
	}

	@SuppressWarnings("static-access")
	public static void crearFunciones(String[] funcionesPredeterminadas) {
		String[] misFunciones = funciones.split(",");
		for(int i=0;i<misFunciones.length;i++){
			if(misFunciones[i].equalsIgnoreCase("f")){
				boolean pedir=true;
				JEP parser = new JEP();
				parser.setImplicitMul(true);
				parser.addStandardConstants();
				parser.addStandardFunctions();
				if(funcionesPredeterminadas!=null){
					parser.addVariable("x", 0);
					parser.parseExpression(funcionesPredeterminadas[i]);
					mPadreU1.setParserF(parser, funcionesPredeterminadas[i]);
				}else{
					try {
						String funcion="";
						while(pedir){
							pedir=false;
							funcion = UtilConsola.leerString("Ingrese f(x): ");
							parser.addVariable("x", 0);
							parser.parseExpression(funcion);
							if(parser.hasError()){
								new AnalisisException("Por favor ingrese una funcion correcta");
								pedir=true;
							}
						}
						mPadreU1.setParserF(parser, funcion);
					} catch (AnalisisException e) {}
				}
			}
			else if(misFunciones[i].equalsIgnoreCase("g")){
				boolean pedir=true;
				JEP parser = new JEP();
				parser.setImplicitMul(true);
				parser.addStandardConstants();
				parser.addStandardFunctions();
				if(funcionesPredeterminadas!=null){
					parser.addVariable("x", 0);
					parser.parseExpression(funcionesPredeterminadas[i]);
					mPadreU1.setParserG(parser, funcionesPredeterminadas[i]);
				}else{
					try {
						String funcion="";
						while(pedir){
							pedir=false;
							funcion = UtilConsola.leerString("Ingrese g(x): ");
							parser.addVariable("x", 0);
							parser.parseExpression(funcion);
							if(parser.hasError()){
								new AnalisisException("Por favor ingrese una funcion correcta");
								pedir=true;
							}
						}
						mPadreU1.setParserG(parser, funcion);
					} catch (AnalisisException e) {}
				}
			}
			else if(misFunciones[i].equalsIgnoreCase("fd")||misFunciones[i].equalsIgnoreCase("fdev")){
				boolean pedir=true;
				JEP parser = new JEP();
				parser.setImplicitMul(true);
				parser.addStandardConstants();
				parser.addStandardFunctions();
				if(funcionesPredeterminadas!=null){
					parser.addVariable("x", 0);
					parser.parseExpression(funcionesPredeterminadas[i]);
					mPadreU1.setParserFdev(parser, funcionesPredeterminadas[i]);
				}else{
					try {
						String funcion="";
						while(pedir){
							pedir=false;
							funcion = UtilConsola.leerString("Ingrese la primer derivada de f(x): ");
							parser.addVariable("x", 0);
							parser.parseExpression(funcion);
							if(parser.hasError()){
								new AnalisisException("Por favor ingrese una funcion correcta");
								pedir=true;
							}
						}
						mPadreU1.setParserFdev(parser, funcion);
					} catch (AnalisisException e) {}
				}
			}
			else if(misFunciones[i].equalsIgnoreCase("fdd")){
				boolean pedir=true;
				JEP parser = new JEP();
				parser.setImplicitMul(true);
				parser.addStandardConstants();
				parser.addStandardFunctions();
				if(funcionesPredeterminadas!=null){
					parser.addVariable("x", 0);
					parser.parseExpression(funcionesPredeterminadas[i]);
					mPadreU1.setParserFdd(parser, funcionesPredeterminadas[i]);
				}else{
					try {
						String funcion="";
						while(pedir){
							pedir=false;
							funcion = UtilConsola.leerString("Ingrese la segunda derivada de f(x): ");
							parser.addVariable("x", 0);
							parser.parseExpression(funcion);
							if(parser.hasError()){
								new AnalisisException("Por favor ingrese una funcion correcta");
								pedir=true;
							}
						}
						mPadreU1.setParserFdd(parser, funcion);
					} catch (AnalisisException e) {}
				}
			}
		}
	}

	/**
	 * Fabrica de metodos 
	 * @param metodo: Identificador del metodo a crear
	 * @return
	 */
	private static MetodoUnidad1 fabricaMetodosUnidad1(int metodo) {
		if(metodo==Constantes.BISECCION) return new Biseccion();
		if(metodo==Constantes.BUSQUEDASINCREMENTALES) return new BusquedasIncrementales();
		if(metodo==Constantes.REGLAFALSA) return new ReglaFalsa();
		if(metodo==Constantes.NEWTON) return new Newton();
		if(metodo==Constantes.PUNTOFIJO) return new PuntoFijo();
		if(metodo==Constantes.SECANTE) return new Secante();
		if(metodo==Constantes.RAICESMULTIPLES) return new RaicesMultiples();
		return null;
	}

	/**
	 * Fabrica de metodos 
	 * @param metodo: Identificador del metodo a crear
	 * @return
	 */
	private static MetodoUnidad2 fabricaMetodosUnidad2(int metodo, Object[][] matriz) throws AnalisisException {
		if(metodo==Constantes.GAUSSSIMPLE) return new GaussSimple(matriz);
		if(metodo==Constantes.PIVOTEOPARCIAL) return new PivoteoParcial(matriz);
		if(metodo==Constantes.PIVOTEOTOTAL) return new PivoteoTotal(matriz);
		if(metodo==Constantes.LUSIMPLE) return new LUSimple(matriz);
		if(metodo==Constantes.LUPIVOTEO) return new LUParcial(matriz);
		if(metodo==Constantes.PIVOTEOESCALONADO) return new PivoteoEscalonado(matriz);
		if(metodo==Constantes.CROULT) return new Croult(matriz);
		if(metodo==Constantes.CHOLESKY) return new Cholesky(matriz);
		if(metodo==Constantes.DOOLITTLE) return new Dolytle(matriz);
		if(metodo==Constantes.GAUSSSEIDEL) return new GaussSeidel(matriz);
		if(metodo==Constantes.MATRIZBANDA) return new MatrizBanda(matriz);
		if(metodo==Constantes.RELAJACION) return new Relajacion(matriz);
		if(metodo==Constantes.JACOBI) return new Jacoby(matriz);
		new AnalisisException("ERROR DE PROGRAMACION: DEBE ADICIONAR EL METODO EN GESTOR METODOS: FABRICAMETODOS UNIDAD 2");
		return null;
	}

	/**
	 * Solicita por consola una serie de parametros Double
	 * @param strings: Lista de parametros a solicitar al usuario
	 * @return: La lista de valores que el usuario ingresa
	 * @throws AnalisisException: En caso de entrada invalida
	 */

	private static double[] solicitarListaDoubleConsola(String[] strings) throws AnalisisException {
		double[] entradas = new double[strings.length];
		for(int i=0;i<strings.length;i++){
			entradas[i]=UtilConsola.leerDouble("Ingrese "+strings[i]+": ");
		}
		return entradas;
	}

	public static boolean esFuncionCorrecta(String funcion){
		JEP parser = new JEP();
		parser.setImplicitMul(true);
		parser.addStandardConstants();
		parser.addStandardFunctions();
		parser.addVariable("x", 0);
		parser.parseExpression(funcion);
		if(parser.hasError())return false;
		return true;

	}


	public static void ejecutarMetodoUnidad1(double[] d) {
		if(modo==Constantes.MODOCONSOLA)ejecutarMetodoConsola(d);
		else if(modo==Constantes.MODOGRAFICOINTERFAZ1||modo==Constantes.MODOGRAFICOINTERFAZ2)ejecutarMetodoInterfaz(d);

	}

	private static void ejecutarMetodoInterfaz(double... entradas) {
		String resultado="";
		try {
			resultado = mPadreU1.metodo(entradas);
		} catch (AnalisisException e) {}
		final ResultadosInterfaz1 ri1 = new ResultadosInterfaz1();
		ri1.lblResultado.setText("Resultado: "+resultado);
		Object[][] resul = mPadreU1.generarMatrizSinTitulos();
		Object[] titulos = mPadreU1.getTitulos();
		//ri1.tablaResultados = new JTable(resul,titulos);

		ri1.tablaResultados.setModel(new DefaultTableModel(resul, titulos));
		ri1.txtDatosGenerales.setText(generarTextoReferenciaUnidad());
		ri1.setVisible(true);
		/*seteo variables modificables en interfaz*/
		if(variablesModificables!=null&&variablesModificables.length>0){
			coordinarLabelsYTextBoxVariablesModificables(ri1);
		}
		ri1.tblB.setVisible(false);
		ri1.jScrollPane3.setVisible(false);
		ri1.lblVectorB.setVisible(false);
		/*en caso de recalcular metodo, ejecuto la funcion again*/
		ri1.btnRecalcular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				double[] d= new double[variablesModificables.length];
				for(int i=0;i<variablesModificables.length;i++){
					try{
						if(i==0){
							d[0] = Double.parseDouble(ri1.txtVariable1.getText());
						}
						else if(i==1){
							d[1] = Double.parseDouble(ri1.txtVariable2.getText());
						}
						else if(i==2){
							d[2] = Double.parseDouble(ri1.txtVariable3.getText());
						}
						else if(i==3){
							d[3] = Double.parseDouble(ri1.txtVariable4.getText());
						}
					}
					catch(Exception ex){
						new AnalisisException("ENTRADA INVALIDA EN LA POSICION "+(i+1));
						return;
					}
				}
				ri1.setVisible(false);
				mPadreU1.resetDatos();
				ejecutarMetodoInterfaz(d);
			}
		});
	}

	private static void coordinarLabelsYTextBoxVariablesModificables(
			ResultadosInterfaz1 ri1) {
		switch(variablesModificables.length){
		case 1:
			ri1.lblVariable1.setText(TEXTOINICIO+variablesModificables[0]+TEXTOFIN);
			ri1.lblVariable2.setVisible(false);
			ri1.txtVariable2.setVisible(false);
			ri1.lblVariable3.setVisible(false);
			ri1.txtVariable3.setVisible(false);
			ri1.lblVariable4.setVisible(false);
			ri1.txtVariable4.setVisible(false);
			break;
		case 2:
			ri1.lblVariable1.setText(TEXTOINICIO+variablesModificables[0]+TEXTOFIN);
			ri1.lblVariable2.setText(TEXTOINICIO+variablesModificables[1]+TEXTOFIN);
			ri1.lblVariable3.setVisible(false);
			ri1.txtVariable3.setVisible(false);
			ri1.lblVariable4.setVisible(false);
			ri1.txtVariable4.setVisible(false);
			break;
		case 3:
			ri1.lblVariable1.setText(TEXTOINICIO+variablesModificables[0]+TEXTOFIN);
			ri1.lblVariable2.setText(TEXTOINICIO+variablesModificables[1]+TEXTOFIN);
			ri1.lblVariable3.setText(TEXTOINICIO+variablesModificables[2]+TEXTOFIN);
			ri1.lblVariable4.setVisible(false);
			ri1.txtVariable4.setVisible(false);
			break;
		case 4:
			ri1.lblVariable1.setText(TEXTOINICIO+variablesModificables[0]+TEXTOFIN);
			ri1.lblVariable2.setText(TEXTOINICIO+variablesModificables[1]+TEXTOFIN);
			ri1.lblVariable3.setText(TEXTOINICIO+variablesModificables[2]+TEXTOFIN);
			ri1.lblVariable4.setText(TEXTOINICIO+variablesModificables[3]+TEXTOFIN);
			break;

		}
	}

	private static String generarTextoReferenciaUnidad() {
		String salida = "";
		if(MetodoUnidad1.getParserF()!=null){
			salida+="f(x)= "+MetodoUnidad1.funcionF+"\n";
		}
		if(MetodoUnidad1.getParserG()!=null){
			salida+="g(x)= "+MetodoUnidad1.funcionF+"\n";
		}
		if(MetodoUnidad1.getParserFdev()!=null){
			salida+="f'(x)= "+MetodoUnidad1.funcionFdev+"\n";
		}
		if(MetodoUnidad1.getParserFdd()!=null){
			salida+="f''(x)= "+MetodoUnidad1.funcionFdd+"\n";
		}
		return salida;
	}
}