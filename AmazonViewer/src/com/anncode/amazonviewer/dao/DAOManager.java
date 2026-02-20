package com.anncode.amazonviewer.dao;

import java.sql.Connection;

/**
 * <h1>DAOManager</h1>
 * Singleton para gestionar los DAOs y la conexión a la base de datos.
 * Proporciona un acceso centralizado a todos los Data Access Objects de la aplicación.
 * 
 * @author anncode
 * @version 1.0
 * @since 2026
 */
public class DAOManager {
    
    // 1. Variable estática para guardar LA ÚNICA instancia
    // Esto hace que solo se pueda realizar una unica referencia
    private static DAOManager instance;


    private Connection connection;
    private ViewedDAO viewedDAO;
    
    /**
     * Constructor privado para implementar el patrón Singleton
     */
    private DAOManager() {
    }
    
    /**
     * Obtiene la instancia única de DAOManager
     * @return instancia singleton de DAOManager
     */
    public static synchronized DAOManager getInstance() {
        if (instance == null) {
            instance = new DAOManager();
        }
        return instance;
    }
    
    /**
     * Inicializa el DAOManager con una conexión activa
     * @param connection Conexión a la base de datos
     */
    public void initialize(Connection connection) {
        this.connection = connection;                           // Guarda la conexion como un ATRIBUTO DE DAOManager 
        this.viewedDAO = new ViewedDAO(connection);             // Instancia ViewedDAO pasando como parametro la conexion
    }
    
    /**
     * Obtiene el ViewedDAO
     * @return instancia de ViewedDAO
     */
    public ViewedDAO getViewedDAO() {
        return viewedDAO;
    }
    
    /**
     * Obtiene la conexión activa
     * @return Connection a la base de datos
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Conexión cerrada correctamente");
            } catch (Exception e) {
                System.err.println("❌ Error cerrando la conexión");
                e.printStackTrace();
            }
        }
    }
}
