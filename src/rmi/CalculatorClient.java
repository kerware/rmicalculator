package rmi;

import java.io.ObjectInputFilter;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {

    public static void main(String[] args) {
        // Configurable via argument CLI ou valeur par défaut
        String host = (args.length > 0) ? args[0] : "localhost";
        int    port = (args.length > 1) ? Integer.parseInt(args[1]) : 1099;

        try {
            // ÉTAPE A — Localiser le Registry distant
            ObjectInputFilter filter = ObjectInputFilter.Config
                    .createFilter(
                            "rmi.*;java.lang.*;java.util.*;!*"
                    );
            Registry registry = LocateRegistry.getRegistry(host, port);

            // ÉTAPE B — Récupérer le stub (proxy distant)
            Calculator calc = (Calculator) registry.lookup("Calculator");
            //        Cast vers l'INTERFACE — jamais vers CalculatorImpl !

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║   Calculator RMI Client              ║");
            System.out.printf ("║   Serveur : %s:%d%n", host, port);
            System.out.println("╚══════════════════════════════════════╝");

            // ÉTAPE C — Appels distants
            CalculationResult r1 = calc.add(10.0, 3.0);
            System.out.println(r1.formatted());
            // TODO : faire de même pour sub(10, 3), mul(10, 3), div(10, 3)
            CalculationResult r2 = calc.sub(10.0, 3.0);
            System.out.println(r2.formatted());

            CalculationResult r3 = calc.mul(10.0, 3.0);
            System.out.println(r3.formatted());

            CalculationResult r4 = calc.div(10.0, 3.0);
            System.out.println(r4.formatted());


            // ÉTAPE D — Tester la division par zéro
            try {
                calc.div(5.0, 0.0); }
            catch (RemoteException e) {
               System.out.println("Erreur capturée : " + e.getCause().getMessage());
            }

        } catch (ConnectException e) {
            System.out.println("Serveur inaccessible sur "+host+":"+port);
        } catch (NotBoundException e) {
            System.out.println("'Calculator' non enregistré dans le registry");
        } catch (RemoteException e) {
            System.out.println("Erreur générale :" + e.getCause() );
        }
    }
}