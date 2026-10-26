package Week7;

public class Problem1 {

    // Abstract base class: cannot be instantiated directly via new Shape()
    public abstract static class Shape {
        private static int shapeCounter = 0;
        private final String shapeId;

        public Shape() {
            shapeCounter++;
            this.shapeId = "SHAPE-" + shapeCounter;
        }

        public String getShapeId() {
            return shapeId;
        }

        // Abstract method implemented individually by each specific shape
        public abstract double calculateArea();

        // Concrete overloaded methods for compile-time polymorphism
        public void scale(double factor) {
            scale(factor, factor);
        }

        public void scale(double xFactor, double yFactor) {
            applyScale(xFactor, yFactor);
        }

        // Internal dimension scaling hook implemented by subclasses
        protected abstract void applyScale(double xFactor, double yFactor);
    }

    public static class CircleShape extends Shape {
        private double radius;

        public CircleShape(double radius) {
            super();
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive.");
            }
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        @Override
        public double calculateArea() {
            return Math.PI * radius * radius;
        }

        @Override
        protected void applyScale(double xFactor, double yFactor) {
            // For a circle, scaling equally doubles the radius
            this.radius *= xFactor;
        }
    }

    public static class SquareShape extends Shape {
        private double side;

        public SquareShape(double side) {
            super();
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be positive.");
            }
            this.side = side;
        }

        public double getSide() {
            return side;
        }

        @Override
        public double calculateArea() {
            return side * side;
        }

        @Override
        protected void applyScale(double xFactor, double yFactor) {
            // Doubles side length
            this.side *= xFactor;
        }
    }

    // Polymorphic method accepting any Shape reference
    public static void printArea(Shape s) {
        if (s != null) {
            System.out.printf("Shape ID: %s | Area: %.2f\n", s.getShapeId(), s.calculateArea());
        }
    }

    public static void main(String[] args) {
        System.out.println("--- CircleShape Area ---");
        CircleShape c = new CircleShape(5.0);
        System.out.printf("c.calculateArea(): %.2f\n", c.calculateArea()); // ~78.54

        System.out.println("\n--- SquareShape Area & Overloaded Scale ---");
        SquareShape sq = new SquareShape(4.0);
        System.out.println("Initial Area: " + sq.calculateArea()); // 16.0

        sq.scale(2.0); // Doubling side from 4.0 to 8.0
        System.out.println("Area after scale(2.0): " + sq.calculateArea()); // 64.0

        System.out.println("\n--- Polymorphic printArea ---");
        printArea(c);
        printArea(sq);

        // Note: new Shape() will not compile because Shape is abstract.
    }
}
