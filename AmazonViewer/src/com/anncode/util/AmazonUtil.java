package com.anncode.util;

//import java.sql.Connection;
//import java.sql.DriverManager;
import java.util.Scanner;

public class AmazonUtil {
	
	public static int validateUserResponseMenu(int min, int max) {
		//Leer la respuesta del usuario
		/*Scanner sc = new Scanner(System.in);
		
		// Valida si la respuesta es un numero entero
		while(!sc.hasNextInt()) { 				// Mientras no sea un numero entero, pide de nuevo que lo intennte
			sc.next();
			System.out.println("No ingresaste una opción válida");
			System.out.println("Intenta otra vez");
		}
		
		int response = sc.nextInt();				// obtiene el valor ingresado
		
		//Validar rango de respuesta
		while(response < min || response > max) {
			//Solicitar de nuevo la respuesta
			System.out.println("No ingresaste una opción válida");
			System.out.println("Intenta otra vez");
			
			while(!sc.hasNextInt()) {
				sc.next();
				System.out.println("No ingresaste una opción válida");
				System.out.println("Intenta otra vez");
			}
			response = sc.nextInt();
		}
		System.out.println("Tu Respuesta fue: " + response + "\n");
		return response;
	}*/

		Scanner sc = new Scanner(System.in); // Lo ideal sería recibirlo por parámetro
		int response;

		do {
			System.out.println("Ingrese una opción entre " + min + " y " + max);
			while (!sc.hasNextInt()) {
				System.out.println("Eso no es un número. Intenta de nuevo.");
				sc.next(); // Limpia la basura
			}
			response = sc.nextInt();
		} while (response < min || response > max);

		return response;
	}
}
