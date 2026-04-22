package rmi;

import java.io.Serializable;

public record CalculationResult(
        String operation,
        double a,//            ← premier opérande
        double b,//            ← second opérande
        double result,//       ← résultat calculé
        long durationNanos//   ← durée du calcul côté serveur
) implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Représentation lisible du résultat. */
    public String formatted() {
        return String.format(
                "%s(%.2f, %.2f) = %.4f  [%d µs]",
                operation, a, b, result,
                durationNanos / 1_000L
        );
    }
}
