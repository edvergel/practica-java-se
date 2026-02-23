package com.anncode.amazonviewer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.anncode.amazonviewer.model.Movie;

/**
 * <h1>ViewedDAO</h1>
 * Esta clase gestiona la persistencia de los elementos vistos en la base de datos.
 * Proporciona métodos para verificar, guardar y eliminar registros de visualización.
 * 
 * @author anncode
 * @version 1.0
 * @since 2026
 */
public class ViewedDAO {
    
    private Connection connection;
    private static final int MATERIAL_MOVIE = 1;
    private static final int MATERIAL_SERIE = 2;
    private static final int MATERIAL_CHAPTER = 3;
    private static final int MATERIAL_BOOK = 4;
    private static final int MATERIAL_MAGAZINE = 5;
    private static final int DEFAULT_USER_ID = 1;
    
    /**
     * Constructor que recibe una conexión activa a la base de datos
     * @param connection Conexión a la base de datos Oracle
     */
    public ViewedDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Verifica si una película ha sido vista por el usuario
     * @param movieId ID de la película
     * @return true si la película ha sido vista, false en caso contrario
     */
    public boolean getMovieViewed(int movieId) {
        return getElementViewed(MATERIAL_MOVIE, movieId);
    }

    /**
     * Obtiene la lista de peliculas de la BD
     * @return Lista de peliculas de la BD
     */
    public List<Movie> getListMovies() {
        return getElementListMovie();
    }
    
    /**
     * Verifica si una serie ha sido vista por el usuario
     * @param serieId ID de la serie
     * @return true si la serie ha sido vista, false en caso contrario
     */
    public boolean getSerieViewed(int serieId) {
        return getElementViewed(MATERIAL_SERIE, serieId);
    }
    
    /**
     * Verifica si un capítulo ha sido visto por el usuario
     * @param chapterId ID del capítulo
     * @return true si el capítulo ha sido visto, false en caso contrario
     */
    public boolean getChapterViewed(int chapterId) {
        return getElementViewed(MATERIAL_CHAPTER, chapterId);
    }
    
    /**
     * Verifica si un libro ha sido leído por el usuario
     * @param bookId ID del libro
     * @return true si el libro ha sido leído, false en caso contrario
     */
    public boolean getBookViewed(int bookId) {
        return getElementViewed(MATERIAL_BOOK, bookId);
    }
    
    /**
     * Verifica si una revista ha sido vista por el usuario
     * @param magazineId ID de la revista
     * @return true si la revista ha sido vista, false en caso contrario
     */
    public boolean getMagazineViewed(int magazineId) {
        return getElementViewed(MATERIAL_MAGAZINE, magazineId);
    }

    /**
     * Metodo que permite consultar la lista de peliculas desde BD
     * @return Lista de peliculas de la BD
     */
    /*private ArrayList<Movie> getElementListMovie(){
        List<Movie> movies = new ArrayList<>();
        
        String query = "select * from movie";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                isViewed = true;
            }
            
        } catch (SQLException e) {
            System.err.println("⚠️ Error verificando elemento visto (tipo: " + materialType + ", id: " + elementId + ")");
            e.printStackTrace();
        }
        return movies;
    }*/
    
    private List<Movie> getElementListMovie() { // Cambiado a List (mejor práctica)
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movie";

        // Try-with-resources: Cierra automáticamente el PreparedStatement y el ResultSet
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery()) {
            
            // El bucle WHILE es la clave: se repite por cada fila de la tabla
            while (rs.next()) {
                // 1. Extraes los datos de la fila actual del ResultSet
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String creator = rs.getString("creator");
                int duration = rs.getInt("duration");
                short year = rs.getShort("year");

                // 2. Creas el objeto Movie con esos datos
                Movie movie = new Movie(id, title, genre, creator, duration, year);
                //movie.setId(id); // Importante setear el ID que viene de la DB
                
                // 3. Agregas la película a la lista
                movies.add(movie);
            }
            
        } catch (SQLException e) {
            System.err.println("⚠️ Error al obtener la lista de películas");
            e.printStackTrace();
        }
        
        return movies; // Retorna la lista llena (o vacía si hubo error)
    }
    
    /**
     * Método genérico para verificar si un elemento ha sido visualizado
     * @param materialType Tipo de material (1=película, 2=serie, 3=capítulo, 4=libro, 5=revista)
     * @param elementId ID del elemento
     * @return true si el elemento ha sido visualizado, false en caso contrario
     */
    private boolean getElementViewed(int materialType, int elementId) {
        boolean isViewed = false;
        String query = "SELECT * FROM viewed WHERE id_material = ? AND id_element = ? AND id_user = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, materialType);
            preparedStatement.setInt(2, elementId);
            preparedStatement.setInt(3, DEFAULT_USER_ID);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                isViewed = true;
            }
            
        } catch (SQLException e) {
            System.err.println("⚠️ Error verificando elemento visto (tipo: " + materialType + ", id: " + elementId + ")");
            e.printStackTrace();
        }
        return isViewed;
    }
    
    /**
     * Marca una película como vista
     * @param movieId ID de la película
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean setMovieViewed(int movieId) {
        return setElementViewed(MATERIAL_MOVIE, movieId);
    }
    
    /**
     * Marca una serie como vista
     * @param serieId ID de la serie
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean setSerieViewed(int serieId) {
        return setElementViewed(MATERIAL_SERIE, serieId);
    }
    
    /**
     * Marca un capítulo como visto
     * @param chapterId ID del capítulo
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean setChapterViewed(int chapterId) {
        return setElementViewed(MATERIAL_CHAPTER, chapterId);
    }
    
    /**
     * Marca un libro como leído
     * @param bookId ID del libro
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean setBookViewed(int bookId) {
        return setElementViewed(MATERIAL_BOOK, bookId);
    }
    
    /**
     * Marca una revista como vista
     * @param magazineId ID de la revista
     * @return true si se guardó correctamente, false en caso contrario
     */
    public boolean setMagazineViewed(int magazineId) {
        return setElementViewed(MATERIAL_MAGAZINE, magazineId);
    }
    
    /**
     * Método genérico para marcar un elemento como visualizado
     * Inserta un nuevo registro en la tabla viewed si no existe
     * @param materialType Tipo de material
     * @param elementId ID del elemento
     * @return true si se guardó correctamente, false en caso contrario
     */
    private boolean setElementViewed(int materialType, int elementId) {
        // Primero verificamos si ya existe el registro
        if (getElementViewed(materialType, elementId)) {
            return true; // Ya estaba marcado como visto
        }
        
        String insertQuery = "INSERT INTO viewed (id_material, id_element, id_user) VALUES (?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, materialType);
            preparedStatement.setInt(2, elementId);
            preparedStatement.setInt(3, DEFAULT_USER_ID);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Elemento visto guardado en la base de datos (tipo: " + materialType + ", id: " + elementId + ")");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error guardando elemento visto (tipo: " + materialType + ", id: " + elementId + ")");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Verifica si todos los capítulos de una serie están marcados como vistos en la BD
     * @param chapterIds Lista de IDs de capítulos a verificar
     * @return true si todos los capítulos están vistos en BD, false en caso contrario
     */
    public boolean areAllChaptersViewedInDatabase(ArrayList<Integer> chapterIds) {
        if (chapterIds == null || chapterIds.isEmpty()) {
            return false;
        }
        
        // Contar cuántos capítulos están marcados como vistos en la BD
        for (int chapterId : chapterIds) {
            boolean isViewed = getChapterViewed(chapterId);
            if (!isViewed) {
                return false;  // Si alguno no está visto, retorna false
            }
        }
        
        return true;  // Todos están vistos en la BD
    }

    /**
     * Elimina un registro de visualización
     * @param materialType Tipo de material
     * @param elementId ID del elemento
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean removeElementViewed(int materialType, int elementId) {
        String deleteQuery = "DELETE FROM viewed WHERE id_material = ? AND id_element = ? AND id_user = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, materialType);
            preparedStatement.setInt(2, elementId);
            preparedStatement.setInt(3, DEFAULT_USER_ID);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Elemento no visto eliminado de la base de datos");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error eliminando elemento visto");
            e.printStackTrace();
        }
        return false;
    }
}
