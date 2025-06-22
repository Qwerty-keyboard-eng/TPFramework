package MolinaCorujo.framework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class MenuFramework {

    private List<Accion> acciones;
    private String configFile;

    public MenuFramework(String configFile) {
        this.configFile = configFile;
        this.acciones = new ArrayList<>();
        cargarAcciones();
    }

    private void cargarAcciones() {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(configFile)) {
            properties.load(reader);
            String accionesStr = properties.getProperty("acciones");
            if (accionesStr == null || accionesStr.trim().isEmpty()) {
                System.err.println("Advertencia: No se encontraron acciones especificadas en el archivo de configuración.");
                return;
            }

            String[] clasesAccion = accionesStr.split(";");
            for (String className : clasesAccion) {
                className = className.trim();
                if (className.isEmpty()) continue; // Skip empty strings

                try {
                    Class<?> clazz = Class.forName(className);
                    if (Accion.class.isAssignableFrom(clazz)) {
                        Accion accion = (Accion) clazz.getDeclaredConstructor().newInstance();
                        acciones.add(accion);
                    } else {
                        System.err.println("La clase '" + className + "' no implementa la interfaz Accion.");
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Error: No se encontró la clase '" + className + "'. Verifique el nombre o la ruta del paquete.");
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    System.err.println("Error al instanciar la clase '" + className + "': " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de configuración '" + configFile + "': " + e.getMessage());
        }
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Ingrese su opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion > 0 && opcion <= acciones.size()) {
                    ejecutarAccion(acciones.get(opcion - 1));
                } else if (opcion == acciones.size() + 1) {
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                } else {
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                opcion = -1; // Para que el bucle continúe
            }
            System.out.println("---");
        } while (opcion != acciones.size() + 1);

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("Bienvenido, estas son sus opciones:");
        for (int i = 0; i < acciones.size(); i++) {
            Accion accion = acciones.get(i);
            System.out.println((i + 1) + ". " + accion.nombreItemMenu() + " (" + accion.descripcionItemMenu() + ")");
        }
        System.out.println((acciones.size() + 1) + ". Salir");
    }

    private void ejecutarAccion(Accion accion) {
        try {
            System.out.println("Ejecutando: " + accion.nombreItemMenu() + "...");
            accion.ejecutar();
            System.out.println("Acción '" + accion.nombreItemMenu() + "' ejecutada con éxito.");
        } catch (Exception e) {
            System.err.println("Error al ejecutar la acción '" + accion.nombreItemMenu() + "': " + e.getMessage());
        }
    }
}