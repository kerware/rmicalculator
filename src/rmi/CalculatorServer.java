package rmi;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer {

    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "Calculator";

    public static void main(String[] args) {
        try {
            // ÉTAPE A — Créer l'implémentation
            CalculatorImpl impl = new CalculatorImpl();

            // ÉTAPE B — Démarrer le RMI Registry dans ce process
            Registry registry = LocateRegistry.createRegistry(PORT);

            // ÉTAPE C — Publier l'objet sous un nom
            registry.rebind(SERVICE_NAME, impl);
            //        rebind() remplace si le nom existe déjà (plus sûr que bind())

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║  Calculator RMI Server — port " + PORT + "   ║");
            System.out.println("║  Service : " + SERVICE_NAME + "                ║");
            System.out.println("╚══════════════════════════════════════╝");

            // ÉTAPE D — Shutdown hook : arrêt propre sur Ctrl+C
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    registry.unbind(SERVICE_NAME);
                    UnicastRemoteObject.unexportObject(impl, true);
                    System.out.println("[Serveur] Arrêt propre effectué.");
                } catch (Exception e) {
                    System.err.println("[Erreur shutdown] " + e.getMessage());
                }
            }, "shutdown-hook"));

            // La JVM reste active grâce au thread RMI interne
            System.out.println("En attente de connexions... (Ctrl+C pour arrêter)");

        } catch (Exception e) {
            System.err.println("[ERREUR] Démarrage serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}