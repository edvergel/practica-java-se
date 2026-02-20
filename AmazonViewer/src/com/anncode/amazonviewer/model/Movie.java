package com.anncode.amazonviewer.model;

import java.util.ArrayList;
import java.util.Date;
import com.anncode.amazonviewer.dao.DAOManager;

/**
 * Hereda de {@link Film}
 * Implementa de {@link IVisualizable}
 */
public class Movie extends Film implements IVisualizable {
	
	private int id;
	private int timeViewed;
	private static int nextId = 1;  // Contador para asignar IDs únicos automáticamente
	
	
	public Movie(String title, String genre, String creator, int duration, short year) {
		super(title, genre, creator, duration);
		setYear(year);
		this.id = nextId++;  // Asignar ID único y incrementar el contador
	}

	
	public int getId() {
		return id;
	}
	
	
	public int getTimeViewed() {
		return timeViewed;
	}
	public void setTimeViewed(int timeViewed) {
		this.timeViewed = timeViewed;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "\n :: MOVIE ::" + 
				"\n Title: " + getTitle() +
				"\n Genero: " + getGenre() + 
				"\n Year: " + getYear() + 
				"\n Creator: " + getCreator() +
				"\n Duration: " + getDuration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date startToSee(Date dateI) {
		// TODO Auto-generated method stub
		return dateI;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopToSee(Date dateI, Date dateF) {
		// TODO Auto-generated method stub
		
		if (dateF.getTime() > dateI.getTime()) {
			setTimeViewed((int)(dateF.getTime() - dateI.getTime()));
		}else {
			setTimeViewed(0);
		}
		
		
	}
	
	public static ArrayList<Movie> makeMoviesList() {
		ArrayList<Movie> movies = new ArrayList<>();;
		
		for (int i = 1; i <= 5; i++) {
			movies.add(new Movie("Movie " + i, "Genero " + i, "Creador " + i, 120+i, (short)(2017+i)));
		}
		
		return movies;
	}

	/**
	 * Recarga el estado de esta película desde la base de datos
	 * Útil para sincronizar el estado en memoria con la BD
	 */
	public void refreshFromDatabase() {
		try {
			boolean isViewed = DAOManager.getInstance().getViewedDAO().getMovieViewed(this.id);
			setViewed(isViewed);
		} catch (Exception e) {
			System.err.println("⚠️ Advertencia: No se pudo recargar el estado de la película desde BD");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void view() {
		setViewed(true);
		Date dateI = startToSee(new Date());
		
		for (int i = 0; i < 10000; i++) {
			System.out.println("..........");
		}
		
		//Termine de verla
		stopToSee(dateI, new Date());
		System.out.println();
		System.out.println("Viste: " + toString());
		System.out.println("Por: " + getTimeViewed() + " milisegundos");
		
		// Guardar en la base de datos
		try {
			DAOManager.getInstance().getViewedDAO().setMovieViewed(this.id);
		} catch (Exception e) {
			System.err.println("⚠️ Advertencia: No se pudo guardar el estado en la base de datos");
		}
	}
	
}




