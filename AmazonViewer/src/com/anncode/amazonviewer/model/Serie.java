package com.anncode.amazonviewer.model;

import java.util.ArrayList;
import com.anncode.amazonviewer.dao.DAOManager;

public class Serie extends Film {
	
	private int id;
	private int sessionQuantity;
	private ArrayList<Chapter> chapters;
	private static int nextId = 101;  // Contador para asignar IDs únicos (100+ para diferenciar de movies)
	

	public Serie(String title, String genre, String creator, int duration, int sessionQuantity) {
		super(title, genre, creator, duration);
		// TODO Auto-generated constructor stub
		this.sessionQuantity = sessionQuantity;
		this.id = nextId++;  // Asignar ID único y incrementar el contador
	}
	
	

	public int getId() {
		return id;
	}

	public int getSessionQuantity() {
		return sessionQuantity;
	}

	public void setSessionQuantity(int sessionQuantity) {
		this.sessionQuantity = sessionQuantity;
	}

	public ArrayList<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<Chapter> chapters) {
		this.chapters = chapters;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  "\n :: SERIE ::" + 
				"\n Title: " + getTitle() +
				"\n Genero: " + getGenre() + 
				"\n Year: " + getYear() + 
				"\n Creator: " + getCreator() +
				"\n Duration: " + getDuration();
	}

	public static ArrayList<Serie> makeSeriesList() {
		ArrayList<Serie> series = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			Serie serie = new Serie("Serie "+i, "genero "+i, "creador "+i, 1200, 5);
			serie.setChapters(Chapter.makeChaptersList(serie));
			series.add(serie);
			
		}
		
		return series;
	}

	/**
	 * Verifica si todos los capítulos de esta serie han sido vistos EN LA BASE DE DATOS
	 * Este método consulta directamente la BD para evitar problemas de sincronización
	 * @return true si todos los capítulos están vistos en BD, false en caso contrario
	 */
	public boolean areAllChaptersViewedInDatabase() {
		if (chapters == null || chapters.isEmpty()) {
			return false;
		}
		
		try {
			// Construir lista de IDs de capítulos
			ArrayList<Integer> chapterIds = new ArrayList<>();
			for (Chapter chapter : chapters) {
				chapterIds.add(chapter.getId());
			}
			
			// Verificar en la BD si todos están vistos
			return DAOManager.getInstance().getViewedDAO().areAllChaptersViewedInDatabase(chapterIds);
		} catch (Exception e) {
			System.err.println("⚠️ Error al verificar capítulos en la BD");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Verifica si todos los capítulos de esta serie han sido vistos (en memoria)
	 * @return true si todos los capítulos han sido vistos en memoria, false en caso contrario
	 */
	public boolean areAllChaptersViewed() {
		if (chapters == null || chapters.isEmpty()) {
			return false;
		}
		
		for (Chapter chapter : chapters) {
			if (!chapter.getIsViewed()) {
				return false;  // Si algún capítulo no ha sido visto, retorna false
			}
		}
		
		return true;  // Todos los capítulos han sido vistos
	}

	/**
	 * Marca la serie como vista y la guarda en la base de datos
	 */
	public void markAsViewed() {
		setViewed(true);
		try {
			DAOManager.getInstance().getViewedDAO().setSerieViewed(this.id);
			System.out.println("✅ Serie guardada en la base de datos");
		} catch (Exception e) {
			System.err.println("⚠️ Advertencia: No se pudo guardar la serie en la base de datos");
		}
	}

	/**
	 * Recarga el estado de esta serie desde la base de datos
	 * Útil para sincronizar el estado en memoria con la BD
	 */
	public void refreshFromDatabase() {
		try {
			boolean isViewed = DAOManager.getInstance().getViewedDAO().getSerieViewed(this.id);
			setViewed(isViewed);
		} catch (Exception e) {
			System.err.println("⚠️ Advertencia: No se pudo recargar el estado de la serie desde BD");
		}
	}

	@Override
	public void view() {
		setViewed(true);
	}
	
}
