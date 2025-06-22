package MolinaCorujo.utilizacion;

import MolinaCorujo.framework.Accion;

public class AccionUno implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Ejecutando AccionUno: ¡Trayendo los tweets de José!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String nombreItemMenu() {
        return "Accion 1";
    }

    @Override
    public String descripcionItemMenu() {
        return "Esto es para traer los tweets de José...";
    }
}