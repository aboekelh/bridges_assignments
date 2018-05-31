import bridges.connect.Bridges;
import bridges.base.Color;
import bridges.base.ColorGrid;

import java.util.ArrayList;

public class GridSquareFill {
    public static void main(String[] args) throws Exception {
        Bridges bridges = new Bridges(6, "bridges_workshop", "1298385986627");

        int rows = 480;
        int columns = 640;

        ColorGrid grid = new ColorGrid(rows, columns, new Color("white"));

        int filledPixels = 0;
        int totalPixels = rows * columns;

        ArrayList<Point> freePoints = new ArrayList<>();

	// initialize an ArrayList of Uncolored points
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                freePoints.add(new Point(i, j));
            }
        }

        while (filledPixels < totalPixels) {
            int index = (int) (Math.random() * (totalPixels - filledPixels));

            Point origin = freePoints.remove(index);
            filledPixels++;
            grid.set(origin.x, origin.y, new Color((int)(Math.random() * 255),
                        (int)(Math.random() * 255),(int)(Math.random() * 255)));

            int layers = 1;
            while (true) {

                Point botLeft = new Point(origin.x - layers, origin.y - layers);
                Point topRight = new Point(origin.x + layers, origin.y + layers);

                if (botLeft.outOfBounds(rows, columns) || topRight.outOfBounds(rows, columns)) {
                    break;
                }

                boolean filled = false;

                int sideLength = 1 + 2 * layers;

                ArrayList<Point> toBeFilled = new ArrayList<>();

                for (int i = 0; i < sideLength; ++i) {
                    Point north = new Point(topRight.x - i, topRight.y);
                    Point east = new Point(topRight.x, topRight.y - i);
                    Point south = new Point(botLeft.x + i, botLeft.y);
                    Point west = new Point(botLeft.x, botLeft.y + i);

                    if (freePoints.contains(north) && freePoints.contains(east) &&
                            freePoints.contains(south) && freePoints.contains(west)) {
                        toBeFilled.add(north);
                        toBeFilled.add(south);
                        toBeFilled.add(west);
                        toBeFilled.add(east);
                    } else {
                        filled = true;
                        break;
                    }
                }

                if (filled)
                    break;

                Color col = new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));

                for (Point pt : toBeFilled) {
                    if (freePoints.contains(pt)) {
                        grid.set(pt.x, pt.y, col);
                        filledPixels++;
                        freePoints.remove(pt);
                    }
                }

                System.out.println("Remaining points to fill:" +  freePoints.size());
                layers++;
            }
        }

        bridges.setDataStructure(grid);
        bridges.visualize();
    }
}

class Point {
    int x;
    int y;

    Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean outOfBounds(int horzSize, int vertSize) {
        return (this.x < 0 || this.x >= horzSize ||
                this.y < 0 || this.y >= vertSize);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o instanceof Point) {
            Point other = (Point) o;
            return (this.x == other.x) && (this.y == other.y);
        }

        return false;
    }
}
