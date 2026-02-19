package com.anncode.amazonviewer.model;

import java.util.Date;

public interface IVisualizable {

	/**
	 * Este método captura el tiempo exacto de visualizacion
	 * 
	 * @param dateI Es un obeto {@code Date} con el tiempo de inicio exacto.
	 * @return Devuelve la fecha y hora capturada
	 */
	Date startToSee(Date dateI);

	/**
	 * Este es un método que captura el tiempo exacto de inicio y final de visualización.
	 * 
	 * @param dateI Es un objeto{@code Date} con el tiempo inicio de visualizacion
	 * @param dateF Es un objeto{@code Date} con el tiempo final de visualizacion
	 */
	void stopToSee(Date dateI, Date dateF);
	
}
