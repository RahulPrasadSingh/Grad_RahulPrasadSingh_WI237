public final class MaintenanceCalculator {

    private MaintenanceCalculator() {}

    public static double calculateAmount(Site site) {
        double area = site.getArea();

        if (site.getOccupied()) {
            return area * 9;
        } else {
            return area * 6;
        }
    }
}
