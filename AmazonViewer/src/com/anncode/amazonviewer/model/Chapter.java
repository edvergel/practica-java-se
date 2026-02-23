package com.anncode.amazonviewer.model;

import java.util.ArrayList;
import com.anncode.amazonviewer.dao.DAOManager;

/**
 * Hereda de {@link Movie}
 * @see Film
 */
public class Chapter extends Serie {
	
	
	private int id;
	private int sessionNumber;
	private Serie serie;
	private static int nextId = 201;  // Contador para asignar IDs únicos (200+ para diferenciar de series y movies)

	public Chapter(String title, String genre, String creator, int duration, short year, int sessionNumber, Serie serie) {
		super(title, genre, creator, duration, year);
		// TODO Auto-generated constructor stub
		this.setSessionNumber(sessionNumber);
		this.setSerie(serie);
		this.id = nextId++;  // Asignar ID único y incrementar el contador
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public int getSessionNumber() {
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber) {
		this.sessionNumber = sessionNumber;
	}
	
	
	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "\n :: SERIE ::" + 
				"\n Title: " + getSerie().getTitle() +
				"\n :: CHAPTER ::" + 
				"\n Title: " + getTitle() +
				"\n Year: " + getYear() + 
				"\n Creator: " + getCreator() +
				"\n Duration: " + getDuration();
	}
	
	
	public static ArrayList<Chapter> makeChaptersList(Serie serie) {
		ArrayList<Chapter> chapters = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			chapters.add(new Chapter("Capituo "+i, "genero "+i, "creator" +i, 45, (short)(2017+i), i, serie));
		}
		
		return chapters;
	}

	@Override
	public void view() {
		
		super.view(); // Super Invoca al metodo de la clase padre (no es obligatorio)

		// Guardar capítulo visto en la base de datos
		try {
			DAOManager.getInstance().getViewedDAO().setChapterViewed(this.id);
		} catch (Exception e) {
			System.err.println("⚠️ Advertencia: No se pudo guardar el capítulo en la base de datos");
		}

		// ✅ CORREGIDO: Verificar en BD si TODOS los capítulos están vistos
		// (No verificar en memoria, que puede estar desincronizada)
		if (getSerie().areAllChaptersViewedInDatabase()) {
			getSerie().markAsViewed();
		}
	}	

}






