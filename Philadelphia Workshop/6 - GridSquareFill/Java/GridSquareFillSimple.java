import bridges.base.Color;
import bridges.base.ColorGrid;
import bridges.connect.Bridges;


public class GridSquareFillSimple {

    public static void main(String[] args) throws Exception {
        Bridges bridges = new Bridges(6, "bridges_workshop", "1298385986627");

        int rows = 100;
        int columns = 100;

        int totalPixels = rows * columns;
        int filledPixels = 0;

        float free = 0.0f;

        ColorGrid grid = new ColorGrid(rows, columns, new Color(255, 255, 255, free));

        bridges.setDataStructure(grid);

        while (filledPixels < totalPixels) {
            int originX = (int)(Math.random() * columns);
            int originY = (int)(Math.random() * rows);

            if (grid.get(originX, originY).getAlpha() != free)
                continue;

            grid.set(originX, originY, new Color((int)(Math.random() * 255),
                        (int)(Math.random() * 255),(int)(Math.random() * 255)));

            filledPixels++;

            boolean collision = false;

            int layers = 1;

            while (!collision) {
                int bottomLeftX = originX - layers;
                int bottomLeftY = originY - layers;
                int topRightX = originX + layers;
                int topRightY = originY + layers;

                if (bottomLeftX < 0 || bottomLeftY < 0 ||
                        topRightX >= columns || topRightY >= rows)
                    break;

                int sideLength = 1 + layers * 2;

                for (int i = 0; i < sideLength; ++i) {
                    int northX = topRightX - i;
                    int eastY = topRightY - i;
                    int southX = bottomLeftX + i;
                    int westY = bottomLeftY + i;

                    if (!(grid.get(northX, topRightY).getAlpha() == free &&
                            grid.get(topRightX, eastY).getAlpha() == free &&
                            grid.get(bottomLeftX, westY).getAlpha() == free &&
                            grid.get(southX, bottomLeftY).getAlpha() == free) ){
                        collision = true;
                        break;
                    }
                }

                if (collision)
                    continue;

                Color col = new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));

                for (int i = 0; i < sideLength; ++i) {
                    int northX = topRightX - i;
                    int eastY = topRightY - i;
                    int southX = bottomLeftX + i;
                    int westY = bottomLeftY + i;

                    grid.set(northX, topRightY, col);
                    grid.set(topRightX, eastY, col);
                    grid.set(bottomLeftX, westY, col);
                    grid.set(southX, bottomLeftY, col);
                }

                filledPixels += sideLength * 4 - 4;
                layers++;
            }
        }

        bridges.visualize();
    }
}
