import com.rancking.miradores.GestorMirador;
import com.rancking.miradores.Mirador;
import com.rancking.miradores.Utilidad;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Añadir miradores iniciales
        inicializarMiradores();

        String opcion;
        do {
            opcion = JOptionPane.showInputDialog(
                "Ranking de los mejores lugares de Tenerife\n\n" +
                "1. Añadir lugar\n" +
                "2. Editar lugar\n" +
                "3. Mostrar lugares\n" +
                "4. Eliminar lugar\n" +
                "Q. Salir"
            );

            switch (opcion) {
                case "1" -> añadir();
                case "2" -> editar();
                case "3" -> mostrar();
                case "4" -> eliminar();
                case "Q", "q" -> JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (!opcion.equalsIgnoreCase("Q"));
    }

    private static void inicializarMiradores() {
        GestorMirador.añadir(new Mirador("Mirador de Humboldt", "Orotava", true, 4, 5));
        GestorMirador.añadir(new Mirador("Mirador de La Paz", "Puerto de la Cruz", false, 5, 4));
        GestorMirador.añadir(new Mirador("Mirador de Chipeque", "La Orotava", true, 3, 5));
    }

    private static void añadir() {
        try {
            String nombre = Utilidad.pedirTexto("Nombre del lugar:");
            String ubicacion = Utilidad.pedirTexto("Ubicación:");
            boolean muyTransitado = Utilidad.pedirSiNo("¿Es muy transitado?");
            int limpieza = Utilidad.pedirEntero("Puntuación de limpieza (1-5):");
            int vistas = Utilidad.pedirEntero("Puntuación de vistas (1-5):");

            Mirador nuevo = new Mirador(nombre, ubicacion, muyTransitado, limpieza, vistas);
            GestorMirador.añadir(nuevo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al añadir lugar.");
        }
    }

    private static void mostrar() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Mirador lugar : GestorMirador.getTodosOrdenados()) {
            sb.append(i++).append(". ").append(lugar.toString()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void eliminar() {
        int index = Utilidad.pedirEntero("¿Qué número de lugar deseas eliminar?") - 1;
        if (GestorMirador.eliminar(index)) {
            JOptionPane.showMessageDialog(null, "Lugar eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "Índice no válido.");
        }
    }

    private static void editar() {
        try {
            int index = Utilidad.pedirEntero("¿Qué número de lugar deseas editar?") - 1;
            Mirador lugar = GestorMirador.get(index);
            String nuevoNombre = Utilidad.pedirTexto("Nuevo nombre [" + lugar.getNombre() + "]:");
            String nuevaUbicacion = Utilidad.pedirTexto("Nueva ubicación:");
            boolean nuevoTransito = Utilidad.pedirSiNo("¿Es muy transitado?");
            int nuevaLimpieza = Utilidad.pedirEntero("Nueva limpieza:");
            int nuevasVistas = Utilidad.pedirEntero("Nuevas vistas:");

            Mirador editado = new Mirador(
                nuevoNombre.isBlank() ? lugar.getNombre() : nuevoNombre,
                nuevaUbicacion.isBlank() ? lugar.getUbicacion() : nuevaUbicacion,
                nuevoTransito,
                nuevaLimpieza,
                nuevasVistas
            );
            GestorMirador.get(index).actualizar(
                editado.getNombre(),
                editado.getUbicacion(),
                editado.isMuyTransitado(),
                editado.getLimpieza(),
                editado.getVistas()
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar.");
        }
    }
}
