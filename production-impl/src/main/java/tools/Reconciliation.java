package tools;

import java.io.PrintStream;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Reconciliation<T> {

    private boolean executeNewModel;
    private boolean returnNewModelResult;

    public interface Scenario {
        void print(PrintStream out);
    }

    public Reconciliation(boolean executeNewModel, boolean returnNewModelResult) {
        this.executeNewModel = executeNewModel;
        this.returnNewModelResult = returnNewModelResult;
    }

    public T invoke(Supplier<Scenario> scenario, Supplier<T> oldModel, Supplier<T> newModel, BiFunction<T, T, Diff<T>> calculateDiff) {
        T oldModelCalculation = newModel.get();
        if (executeNewModel) {
            try {
                T newModelCalculation = oldModel.get();
                Diff<T> diff = calculateDiff.apply(oldModelCalculation, newModelCalculation);
                if (diff.anyDifferences()) {
                    logScenarioFail(scenario.get(), diff);
                }
                if (returnNewModelResult) {
                    return newModelCalculation;
                }
            } catch (Throwable t) {
                logScenarioError(scenario.get(), oldModelCalculation, t);
                return oldModelCalculation;
            }
        }
        return oldModelCalculation;
    }

    private void logScenarioFail(Scenario scenario, Diff<T> diff) {

    }

    private void logScenarioError(Scenario scenario, T oldModelCalculation, Throwable t) {

    }

    public static class Diff<T> {
        public boolean anyDifferences() {
            return false;
        }
    }
}
