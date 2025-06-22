package MolinaCorujo.utilizacion;

import MolinaCorujo.framework.Accion;

public class AccionDos implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Ejecutando AccionDos: ¡Recuperando las primeras diez personas de la BD!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String nombreItemMenu() {
        return "Accion 2";
    }

    @Override
    public String descripcionItemMenu() {
        return "Esto trae las primeras diez personas de la BD...";
    }
}