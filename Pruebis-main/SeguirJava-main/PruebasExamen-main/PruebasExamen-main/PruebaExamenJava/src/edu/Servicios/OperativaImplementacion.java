package edu.Servicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.Subject;

import edu.Controladores.Inicio;
import edu.Dtos.Citas;
import edu.ficheros.ficherosImplementacion;
import edu.ficheros.ficherosInterfaz;

public class OperativaImplementacion implements OperativaInterfaz{

	
	String nombre = "CitasConAsistencia";
    LocalDate fechaHoy = LocalDate.now();
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    String fechaDeHoy = fechaHoy.format(formatter);
    
    String log2 = "C:\\Users\\Carlos\\Desktop\\" + fechaHoy + "log.txt";
    
    String suma = "C:\\Users\\Carlos\\Desktop\\suma" + fechaHoy + ".txt";
    
    String citasConFecha = "C:\\Users\\Carlos\\Desktop\\" + nombre + "-" + fechaHoy + ".txt";
    
    String citas = "C:\\Users\\Carlos\\Desktop\\citas.txt";
    
	Scanner sc = new Scanner(System.in);
	
	
	
	public void CitasNueva(List<Citas> listaCitas) {
		try {	
		FileWriter escribe = new FileWriter(log2, true);
		FileWriter escribeNuevaCita = new FileWriter(citas, true);
		
		Citas citasNuevas = new Citas();
		
		System.out.println("Pedir una cita nueva");
		
		System.out.println("Apellidos del paciente");
		citasNuevas.setApellidos(sc.nextLine());
		
		System.out.println("Nombre del paciente");
		citasNuevas.setNombre(sc.next());	
		
		System.out.println("DNI del paciente");
		citasNuevas.setDni(sc.next());
		
		System.out.println("Especialidad ([0] - Psicología | [1] - Traumatología | [2] - Fisioterapia)");
		int op = sc.nextInt();
		
		if(op == 0) {
			citasNuevas.setEspecialidad("Psicología");
		}
		else if(op == 1) {
			citasNuevas.setEspecialidad("Traumatología");
		}
		else if(op == 2) {
			citasNuevas.setEspecialidad("Fisioterapia");
		}
		else {
			System.out.println("opción no válida");
		}
	
		
		System.out.println("Fecha de  la cita(formato dd-MM-yyyy)");
		citasNuevas.setFechaCita(sc.next());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		
		System.out.println("Precio");
		citasNuevas.setPrecio(sc.nextDouble());
		
		citasNuevas.setIdCitas(idAuto(listaCitas));
		
		escribeNuevaCita.write(citasNuevas.getDni() + ";" + citasNuevas.getNombre() + ";" + citasNuevas.getApellidos() + ";" + citasNuevas.getEspecialidad() + ";" + citasNuevas.getFechaCita() + ";" + citasNuevas.getPrecio() + ";" + citasNuevas.getVisita() + "\n");
		escribeNuevaCita.close();
		listaCitas.add(citasNuevas);
		
		escribe.write("Cita de paciente añadida \n");
		escribe.close();
		
		for (Citas citas : listaCitas) {
			System.out.println(citas.toString());
		}
		
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al añadir la cita" + e.getMessage());
		}
		
		
	}
	public void registroLlegada(List<Citas> listaCitas) {
		
		try {
		FileWriter escribe = new FileWriter(log2, true);
		
		System.out.println("DNI del cliente con cita");
		String Dni = sc.next();
		
		LocalDate fechaHoy = LocalDate.now();
		
		int dia = fechaHoy.getDayOfMonth();
		int mes = fechaHoy.getMonthValue();
		int anyo = fechaHoy.getYear();
		
		String diaFin = String.valueOf(dia);
		String mesFin = String.valueOf(mes);
		String anyoFin = String.valueOf(anyo);
		
		String fechaFin = diaFin + "/" + mesFin + "/" + anyoFin;
		System.out.println(fechaFin);
		
		if(validarDni(Dni)) {
			
			System.out.println("El DNI es válido");
			escribe.write("Se valida el DNI \n");
			
			for (Citas citas : listaCitas) {
				if(citas.getFechaCita().equals(fechaFin) && citas.getDni().equals(Dni)) {
					System.out.println("Espere su turno para la consulta de Psicología en la sala de espera. Su especialista le avisará.");
					citas.setVisita(true);
					
				}
			}
		}
		else {
			System.out.println("El DNI no es válido");
			escribe.write("El DNI no se ha podido validar \n");
		}
		escribe.close();	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al validar la cita");
		}
	}
		
	
	public void listadoConsultasPsicologia(List<Citas> listaCitas) {
		
		try {
			FileWriter escribe = new FileWriter(log2, true);
			System.out.println("Fecha de la consulta dd/MM/yyyy");
			String fecha = sc.next();
			
			for(Citas citas : listaCitas) {
				
				if(citas.getEspecialidad().equals("Psicología") && citas.getFechaCita().equals(fecha)) {
					System.out.println(citas.getNombre()+ " " + citas.getApellidos() + " " + citas.getFechaCita());
					escribe.write("Se listan las consultas de psicología \n");
				}
				//else {
				//escribe.write("Ninguna opción valida \n");
				//}
				
			}
			
			escribe.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al listar las consultas");
		}
		
	}
	
	
public void listadoConsultasTraumatologia(List<Citas> listaCitas) {
		
		try {
			FileWriter escribe = new FileWriter(log2, true);
			System.out.println("Fecha de la consulta dd/MM/yyyy");
			String fecha = sc.next();
			
			for(Citas citas : listaCitas) {
				
				if(citas.getEspecialidad().equals("Traumatología") && citas.getFechaCita().equals(fecha)) {
					System.out.println(citas.getNombre()+ " " + citas.getApellidos() + " " + citas.getFechaCita());
					escribe.write("Se listan las consultas de traumatología \n");
				}
				//else {
				//escribe.write("Ninguna opción valida \n");
				//}
				
			}
			escribe.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al listar las consultas");
		}
		
	}

public void listadoConsultasFisioterapia(List<Citas> listaCitas) {
	
	try {
		FileWriter escribe = new FileWriter(log2, true);
		System.out.println("Fecha de la consulta dd/MM/yyyy");
		String fecha = sc.next();
		
		for(Citas citas : listaCitas) {
			
			if(citas.getEspecialidad().equals("Fisioterapia") && citas.getFechaCita().equals(fecha)) {
				System.out.println(citas.getNombre()+ " " + citas.getApellidos() + " " + citas.getFechaCita());
				escribe.write("Se listan las consultas de Fisioterapia \n");
			}
			else {
			}
			
		}
		escribe.close();
		
		
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error al listar las consultas");
	}
	
}

public void imprimirConsultasPsicologia(List<Citas> listaCitas) {
	try {
		
		System.out.println("Fecha de la consulta dd/MM/yyyy");
		String fecha = sc.next();
		FileWriter escribe = new FileWriter(citasConFecha, true);
		for(Citas citas : listaCitas) {
			
			if(citas.getEspecialidad().equals("Psicología") && citas.getFechaCita().equals(fecha)) {
				escribe.write(citas.getNombre()+ " " + citas.getApellidos()+ " " + citas.getFechaCita() + "\n");
				
			}
			//else {
				//escribe.write("Ninguna opción valida \n");
			//}
			
		
		}
		escribe.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error al imprimir las consultas" + e.getMessage());
	}
}

public void imprimirConsultasTraumatologia(List<Citas> listaCitas) {
	
try {
		
		System.out.println("Fecha de la consulta dd/MM/yyyy");
		String fecha = sc.next();
		FileWriter escribe = new FileWriter(citasConFecha, true);
		for(Citas citas : listaCitas) {
			
			if(citas.getEspecialidad().equals("Traumatología") && citas.getFechaCita().equals(fecha)) {
				escribe.write(citas.getNombre()+ " " + citas.getApellidos()+ " " + citas.getFechaCita() + "\n");
				
			}
			//else {
			//escribe.write("Ninguna opción valida \n");
		//}
		
		}
		escribe.close();
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error al imprimir las consultas" + e.getMessage());
	}
}

public void imprimirConsultasFisioterapia(List<Citas> listaCitas) {
	try {
		
	System.out.println("Fecha de la consulta dd/MM/yyyy");
	String fecha = sc.next();
	FileWriter escribe = new FileWriter(citasConFecha, true);
	for(Citas citas : listaCitas) {
		
		if(citas.getEspecialidad().equals("Fisioterapia") && citas.getFechaCita().equals(fecha)) {
			escribe.write(citas.getNombre()+ " " + citas.getApellidos()+ " " + citas.getFechaCita() + " \n");
			
		}
		//else {
		//escribe.write("Ninguna opción valida \n");
		//}
	
	}
	escribe.close();
	
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error al imprimir las consultas" + e.getMessage());
	}
}

public void mostrarCitasEntreFechas(List<Citas> listaCitas) {
	
	System.out.println("Dame la fecha de inicio dd/MM/yyyy");
	String fechaInicioS = sc.next(); 
	
	System.out.println("Dame la fecha de inicio dd/MM/yyyy");
	String fechaFinS = sc.next(); 
	
	SimpleDateFormat fechaI = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat fechaF = new SimpleDateFormat("dd/MM/yyyy");
	
	
	try {
		for (Citas citas : listaCitas) {
			FileWriter escribe = new FileWriter(log2, true);
			String fecha = citas.getFechaCita();
			SimpleDateFormat fechasTotales = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaInicio = fechasTotales.parse(fechaInicioS);
			Date fechaFin = fechasTotales.parse(fechaFinS);
			Date fechaCita = fechasTotales.parse(fecha);
			
			if(fechaInicio.before(fechaCita) && fechaFin.after(fechaCita)) {
				System.out.println("Nombre Completo: " + citas.getNombre() + " " + citas.getApellidos() + " Dia: " + citas.getFechaCita());
				escribe.write("Se imprimen las consultas entre las fechas: \n");
				escribe.close();
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error al mostrar consultas en fechas especificas " + e.getMessage());
	}
}

public void CalculoTotal(List<Citas> listaCitas) {
	
	try {System.out.println("Dame la fecha del dia que quieres calcular");
		String fechaPedida = sc.next();
		double precioTotal = 0.0;
		FileWriter escribe = new FileWriter(suma, true);
		FileWriter escribelog = new FileWriter(log2, true);
		for (Citas citas : listaCitas) {
			if(fechaPedida.equals(citas.getFechaCita())) {
			precioTotal += citas.getPrecio();
			}
			

		}
		
		String sumaTotal = String.valueOf(precioTotal);
		escribe.write("La suma del dia "+ fechaDeHoy + " " + sumaTotal + "\n");
		escribelog.write("Se calcula la suma \n");
		escribe.close();
		escribelog.close();
		System.out.println("Precio total para el día " + fechaPedida + ":" + precioTotal);
	} catch (Exception e) {
		// TODO: handle exception
	}
	
}
		
	
	private long idAuto(List<Citas> listaCitas) {
		
		long idNuevo = 0;
		
		int tamanioLista = listaCitas.size();
		
		
		if(tamanioLista > 0) {
			
			idNuevo = listaCitas.get(tamanioLista - 1).getIdCitas() + 1;
		}
		
		else {
			idNuevo = 1;
		}
		
		return idNuevo;
	}
	
	private boolean validarDni(String Dni) {
		
		if(Dni.length() != 9) {
			return false;
		}
		
		String  numeroDni = Dni.substring(0, 8);
		
		char letra = Character.toUpperCase(Dni.charAt(8));
		String letrasValida = "TRWAGMYFPDXBNJZSQVHLCKE";
		int resto = Integer.parseInt(numeroDni) % 23;
		char letraCalculada = letrasValida.charAt(resto);
		
		return letra == letraCalculada;

	}
}
