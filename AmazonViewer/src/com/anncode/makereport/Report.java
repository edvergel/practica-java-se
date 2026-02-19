package com.anncode.makereport;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * <h1>Make Report</h1>
 * Clase que se encarga de escribir archivos de texto
 * <br>
 * Escribe documentos de texto a partir de los atributos {@code nameFile}, {@code title}
 * y {@code content}, donde {@code nameFile} es el nombre del archivo, {@code title} es el titulo del mismo 
 * y {@code content} el contenido de este.
 * 
 * @author Francisco Casales (codecasales)
 * @version 1.0
 * @since Enero 2019
 * */

public class Report {
	
	private String nameFile;
	private String title;
	private String content;
	private String extension;
	
	/**
	 * Metodo que obtiene el nombre del archivo a generar
	 * 
	 * @return nameFile es un objeto {@code String} con el nombre del archivo sin extencion.
	 * */
	public String getNameFile() {
		return nameFile;
	}
	/**
	 * Metodo que establece el nombre del archivo sin extencion
	 * 
	 * @param nameFile es un objeto de tipo {@code String} que contiene el nombre del archivo sin extencion.
	 * */
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	/**
	 * Metodo que obtiene el titulo del archivo.
	 * 
	 * @return title es un objeto {@code String} con el titulo del archivo.
	 * */
	public String getTitle() {
		return title;
	}
	/**
	 * Metodo que establece el titulo del archivo sin extencion
	 * 
	 * @param title es un objeto de tipo {@code String} que contiene el titulo del archivo.
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Metodo que obtiene el contenido del archivo a generar
	 * 
	 * @return content es un objeto {@code String} con el contenido del archivo sin incluir el titulo.
	 * */
	public String getContent() {
		return content;
	}
	/**
	 * Metodo que establece el contenido del archivo sin extencion
	 * 
	 * @param content es un objeto de tipo {@code String} que tiene el contenido del archivo sin titulo.
	 * */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * <h1>Metodo makeReport</h1>
	 * Metodo que se encarga de generar los archivos .txt utilizando los datos establecidos por los setters
	 * <br>
	 * Hace una comprobacion, si {@code nameFile}, {@code title} o {@code content} son nulos no genera el archivo
	 * y le pide al usuario proporcionar los datos necesarios.
	 * */
	public void makeReport() {
		if ( (getNameFile() != null) && (getTitle() != null) && (getContent() != null) ) {
			//Crear el archivo
			try {
				
				File file = new File(getNameFile()+"."+getExtension());
				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write(getContent());
				bw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		} else {
			System.out.println("Ingresa los datos del archivo");
		}
	}
	/**
	 * Metodo que obtiene la extension del archivo a generar
	 * 
	 * @return extension es un objeto {@code String} con la extension del archivo 
	 * sin incluir el nombre del archivo.
	 * */
	public String getExtension() {
		return extension;
	}
	/**
	 * Metodo que establece la extension del archivo
	 * 
	 * @param extension es un objeto de tipo {@code String} que contiene la extension del archivo 
	 * sin incluir el nombre del archivo.
	 * */
	public void setExtension(String extension) {
		this.extension = extension;
	}

}