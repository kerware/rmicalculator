package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.DoubleSupplier;

/**
 * Implémentation distante de Calculator.
 *
 * Règle 1 : extends UnicastRemoteObject
 *   → gère l'export réseau + stub dynamique
 * Règle 2 : constructeur throws RemoteException
 *   → super() peut échouer (port réseau)
 * Règle 3 : implements Calculator
 *   → satisfait le contrat Remote
 */
public class CalculatorImpl
        extends UnicastRemoteObject implements Calculator {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    // ── Méthode utilitaire — NE PAS MODIFIER ──────────────────────
    /**
     * Encapsule le calcul en mesurant le temps d'exécution.
     * @param op   nom de l'opération
     * @param a    premier opérande
     * @param b    second opérande
     * @param calc lambda exécutant le calcul
     */
    private CalculationResult compute(
            String op, double a, double b,
            DoubleSupplier calc) {
        long start = System.nanoTime();
        double result = calc.getAsDouble();
        long dur = System.nanoTime() - start;
        System.out.printf("  [Server] %s(%.2f, %.2f) = %.4f%n", op, a, b, result);
        return new CalculationResult(op, a, b, result, dur);
    }

    // ── add ─────────────────────────────────────────────────────────
    @Override
    public CalculationResult add(double a, double b) throws RemoteException {
             return compute("add", a, b, () -> a + b);
    }

    // ── sub ─────────────────────────────────────────────────────────
    @Override
    public CalculationResult sub(double a, double b) throws RemoteException {
        return compute("sub", a, b, () -> a - b);
    }
    // ── mul ─────────────────────────────────────────────────────────
    @Override
    public CalculationResult mul(double a, double b) throws RemoteException {
        return compute("mul", a, b, () -> a * b);
    }
    // ── div ─────────────────────────────────────────────────────────
    @Override
    public CalculationResult div(double a, double b) throws RemoteException, ArithmeticException {
        if ( b==0) {
            throw new ArithmeticException("Division by zero");
        }
        return compute("div", a, b, () -> a / b);
    }

}
