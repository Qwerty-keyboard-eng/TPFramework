package MolinaCorujo.main;

import MolinaCorujo.framework.MenuFramework;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java -jar tu_aplicacion.jar <ruta_archivo_configuracion>");
            return;
        }
        String configFile = args[0];
        MenuFramework framework = new MenuFramework(configFile);
        framework.iniciar();
    }
}