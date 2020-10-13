package net.springboot.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Tools {
	
	static String obtenerFechaYHoraActual() {
        String formato = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }
}
