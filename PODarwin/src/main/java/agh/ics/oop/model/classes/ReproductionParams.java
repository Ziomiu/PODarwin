package agh.ics.oop.model.classes;

public record ReproductionParams(
    int reproductionEnergyThreshold,
    int reproductionEnergyRequired,
    int reproductionMutationMin,
    int reproductionMutationMax
) {
    public boolean validate() {
        return reproductionEnergyThreshold > 0
           && reproductionEnergyRequired > 0
           && reproductionMutationMin >= 0
           && reproductionMutationMax >= reproductionMutationMin;
    }
}
