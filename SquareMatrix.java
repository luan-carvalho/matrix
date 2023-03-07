import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SquareMatrix {

    public double[][] elements;
    public final int order;

    public SquareMatrix(int order) {

        if (order <= 0)
            throw new RuntimeException("Enter a number equals or greater than 1");

        this.order = order;
        elements = new double[order][order];
    }

    public SquareMatrix(double[][] elements) {

        if (!checkBidimensionalArray(elements))
            throw new RuntimeException("This is not a square bidimensional array");

        this.elements = new double[elements.length][elements.length];
        this.order = elements.length;

        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements.length; j++) {
                this.elements[i][j] = elements[i][j];
            }
        }

    }

    // Set the element in the given position
    public void set(double element, int row, int column) {

        if ((row > order || column > order) || (row <= 0 || column <= 0))
            throw new RuntimeException("Enter a row or column that exists");

        elements[row - 1][column - 1] = element;
    }

    // Set elements in the given row
    public void setRow(double[] elements, int row) {

        if ((row > order || row <= 0) || elements.length > order)
            throw new RuntimeException("This row doesn't exists or this array length is not equal to the matrix order");

        this.elements[row - 1] = elements;
    }

    public void setColumn(double[] elements, int column) {

        for (int i = 0; i < order; i++) {

            this.elements[i][column - 1] = elements[i];

        }

    }

    public void setAll(double[][] elements) {

        if (elements.length != this.order)
            throw new RuntimeException("This bidimensional array is not of the same order of this matrix");

        for (int i = 1; i < order; i++) {
            for (int j = 1; j < order; j++) {
                this.set(elements[i - 1][j - 1], i, j);
            }
        }

    }

    // Get the element in the given position
    public double get(int row, int column) {

        if ((row > order || column > order) || (row <= 0 || column <= 0))
            throw new RuntimeException("Enter a row or column that exists");

        return elements[row - 1][column - 1];
    }

    // Checks if the matrix contains x
    public boolean contains(double x) {
        return this.findFirst(x) != null;
    }

    // Return the position of the first occurency of x
    public int[] findFirst(double x) {
        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                if (this.get(i, j) == x) {
                    int[] position = { i, j };
                    return position;
                }
            }
        }

        return null;
    }

    // Return a list that contains the positions of all the occurrencies of x
    public List<int[]> findAll(double x) {

        List<int[]> occurrencies = new ArrayList<>();

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                if (this.get(i, j) == x) {
                    int[] position = { i, j };
                    occurrencies.add(position);
                }
            }
        }

        return occurrencies.size() == 0 ? null : occurrencies;

    }

    // Multiply all elements by scalar x
    public void scalarMultiplication(double x) {
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                elements[i][j] *= x;
            }
        }
    }

    // Return the transpose of this matrix
    public SquareMatrix transpose() {
        SquareMatrix transpose = new SquareMatrix(this.order);

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {

                transpose.set(elements[j][i], i + 1, j + 1); // Rows turn into columns and columns turn into rows

            }
        }

        return transpose;
    }

    // Returns the inverse of this matrix
    public SquareMatrix inverse() {

        if (this.det() == 0)
            throw new RuntimeException("This is not a invertible matrix");

        SquareMatrix cofactorsMatrix = new SquareMatrix(order);

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                cofactorsMatrix.set(cofactor(i, j), i, j);
            }
        }

        cofactorsMatrix.scalarMultiplication(this.det());

        return cofactorsMatrix.transpose();

    }

    // A matrix M is invertible when its determinant is not equal to 0
    public boolean isInvertible() {
        return det() != 0;
    }

    // A Matrix M is symetric when M is equivalent to its transpose
    public boolean isSymmetric() {
        return this.transpose().compare(this);
    }

    // A matrix M is skew-symmetric when its transpose is equivalent to -M
    public boolean isSkewSymmetric() {

        SquareMatrix clone = this.clone();

        clone.scalarMultiplication(-1);

        return this.transpose().compare(clone);

    }

    // A matrix M is orthogonal when its inverse is equivalent to its transpose
    public boolean isOrthogonal() {
        return this.inverse().compare(this.transpose());
    }

    // Returns the minor of an element in this matrix
    private SquareMatrix minor(int row, int column) {

        SquareMatrix minor = new SquareMatrix(order - 1);

        for (int i = 1; i <= order; i++) {
            if (i == row)
                continue;
            for (int j = 1; j <= order; j++) {

                if (j == column)
                    continue;
                else if (i < row && j < column)
                    minor.set(this.get(i, j), i, j);
                else if (i < row && j > column)
                    minor.set(this.get(i, j), i, j - 1);
                else if (i > row && j < column)
                    minor.set(this.get(i, j), i - 1, j);
                else if (i > row && j > column)
                    minor.set(this.get(i, j), i - 1, j - 1);

            }
        }

        return minor;
    }

    // Calculates the cofactor of an element in this matrix
    private double cofactor(int row, int column) {

        return Math.pow((-1), row + column) * this.minor(row, column).det();

    }

    // Calculates the determinant of this matrix using Laplace's expansion in the
    // first row
    public double det() {

        if (this.order == 1)
            return get(1, 1);

        int sum = 0;
        for (int i = 1; i <= order; i++) {

            sum += this.get(1, i) * this.cofactor(1, i);
        }
        return sum;

    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < order; i++) {
            sb.append("[");
            for (int j = 0; j < order; j++) {

                sb.append(get(i + 1, j + 1));
                if (j < order - 1)
                    sb.append("\t");

            }

            sb.append("]");
            if (i < order)
                sb.append("\n");

        }

        return sb.toString();

    }

    // Check if this matrix is equivalent to another matrix
    public boolean compare(SquareMatrix other) {

        if (this.order != other.order)
            return false;

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                if (this.get(i, j) != other.get(i, j))
                    return false;
            }
        }

        return true;

    }

    public void clear() {

        this.elements = new double[order][order];

    }

    public void forEach(Consumer<Double> x) {

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                x.accept(get(i, j));
            }
        }
    }

    public double max() {

        double max = get(1, 1);

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                max = max > get(i, j) ? max : get(i, j);
            }
        }
        return max;
    }

    public double min() {

        double min = get(1, 1);

        for (int i = 1; i <= order; i++) {
            for (int j = 1; j <= order; j++) {
                min = min < get(i, j) ? min : get(i, j);
            }
        }
        return min;
    }

    private boolean checkBidimensionalArray(double[][] m) {

        for (int i = 0; i < m.length; i++) {
            if (m[i].length != m.length)
                return false;
        }

        return true;
    }

    public SquareMatrix clone() {

        SquareMatrix clone = new SquareMatrix(order);

        for (int i = 1; i <= order; i++) {

            for (int j = 1; j <= order; j++) {
                clone.set(this.get(i, j), i, j);
            }

        }

        return clone;

    }
}