import bridges.connect.Bridges;
import bridges.base.Color;
import bridges.base.ColorGrid;

import java.util.ArrayList;

public class GridSquareFill {

    public static void main(String[] args) throws Exception {
        Bridges bridges = new Bridges(6, "bridges_workshop", "1298385986627");

        // max color grid size, may take a minute or two
        int rows = 480;
        int columns = 640;

        ColorGrid grid = new ColorGrid(rows, columns, new Color("white"));

        int filledPixels = 0;
        int totalPixels = rows * columns;

        ArrayList<Point> freePoints = new ArrayList<>();

        // initializes the ArrayList of free points
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                freePoints.add(new Point(i, j));
            }
        }

        while (filledPixels < totalPixels) {
            // generates an index for the origin of the next square
            int index = (int) (Math.random() * (totalPixels - filledPixels));

            Point origin = freePoints.remove(index);
            filledPixels++;

            // generate a color for the origin
            grid.set(origin.x, origin.y, new Color((int)(Math.random() * 255),
                        (int)(Math.random() * 255),(int)(Math.random() * 255)));

            int layers = 1;

            // this loop will continue until there is a collision between squares or the edge
            while (true) {

                Point botLeft = new Point(origin.x - layers, origin.y - layers);
                Point topRight = new Point(origin.x + layers, origin.y + layers);

                // check the corners to see if they are out of bounds
                if (botLeft.outOfBounds(rows, columns) || topRight.outOfBounds(rows, columns)) {
                    break;
                }

                boolean collision = false;

                int sideLength = 1 + 2 * layers;

                ArrayList<Point> toBeFilled = new ArrayList<>();

                for (int i = 0; i < sideLength; ++i) {
                    Point north = new Point(topRight.x - i, topRight.y);
                    Point east = new Point(topRight.x, topRight.y - i);
                    Point south = new Point(botLeft.x + i, botLeft.y);
                    Point west = new Point(botLeft.x, botLeft.y + i);

                    // check all 4 sides of the square for a collision
                    if (freePoints.contains(north) && freePoints.contains(east) &&
                            freePoints.contains(south) && freePoints.contains(west)) {
                        // if no collision, begin adding points to a list for later
                        if (!toBeFilled.contains(north))
                            toBeFilled.add(north);

                        if (!toBeFilled.contains(south))
                            toBeFilled.add(south);

                        if (!toBeFilled.contains(west))
                            toBeFilled.add(west);

                        if (!toBeFilled.contains(east))
                            toBeFilled.add(east);
                    } else {
                        // if there is a collision, escape the loops and pick a new origin
                        collision = true;
                        break;
                    }
                }

                if (collision)
                    break;

                // if there was no collision for the next layer, go ahead and generate a random color and fill it
                Color col = new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));

                for (Point pt : toBeFilled) {
                    grid.set(pt.x, pt.y, col);
                    filledPixels++;
                    freePoints.remove(pt);
                }

                // useful for inspiring hope and building suspense
                System.out.println("Remaining points to fill:" +  freePoints.size());

                // start next layer
                layers++;
            }
        }

        bridges.setDataStructure(grid);
        bridges.visualize();
    }
}

// lightweight point class useful for representing the points on the grid
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

    /*
     *  used for the contains and remove methods
     */
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
