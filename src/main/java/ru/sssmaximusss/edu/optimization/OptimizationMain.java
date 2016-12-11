package ru.sssmaximusss.edu.optimization;

import org.apache.commons.math3.linear.*;

/**
 * Created by Maxim on 11.12.2016.
 */
public class OptimizationMain {

    private static final RealMatrix A = new Array2DRowRealMatrix(new double[][]{{1,2,3},{4,5,6}});

    private double bound(RealMatrix y) {
        RealMatrix inverseA = new LUDecomposition(A.multiply(A.transpose())).getSolver().getInverse();
        printMatrix(inverseA);
        RealMatrix QA = A.transpose().multiply(inverseA).multiply(A);
        printMatrix(QA);
        RealMatrix tmpV = QA.multiply(y);
        RealVector v = tmpV.getRowDimension() > tmpV.getColumnDimension() ? tmpV.getColumnVector(0) : tmpV.getRowVector(0);
        printVector(v);
        RealVector vplus = new ArrayRealVector(v.getDimension());
        for (int i = 0; i<vplus.getDimension(); i++) {
            if (vplus.getEntry(i) < 0) {
                vplus.setEntry(i, 0);
            }
        }
        printVector(vplus);

        RealVector vminus = new ArrayRealVector(v.getDimension());
        for (int i = 0; i<vminus.getDimension(); i++) {
            if (vminus.getEntry(i) > 0) {
                vminus.setEntry(i, 0);
            }
        }
        printVector(vminus);

        RealVector one = new ArrayRealVector(v.getDimension(), 1);


        if (one.dotProduct(v) > 0) {
            return (-1.) * one.dotProduct(vminus) / vplus.getMaxValue();
        } else {
            return (-1.) * one.dotProduct(vplus) / vminus.getMinValue();
        }
    }

    private void modifiedBasicProcedure(RealMatrix PA, RealMatrix y) {
        RealMatrix tmpZ = PA.multiply(y);
        RealVector z = tmpZ.getRowDimension() > tmpZ.getColumnDimension() ? tmpZ.getColumnVector(0) : tmpZ.getRowVector(0);
        printVector(z);

        RealVector one = new ArrayRealVector(z.getDimension(), 1);
        double zSum = one.dotProduct(z);

        int ys = 0;
        int step = 0;
        double J = 0;

        while ( bound(y) - 0.5 > 0 && step == 0 ) {
            if (zSum > 0) {
                step = 1;
                return;
            } else {
                if (zSum == 0) {
                    step = 2;
                    return;
                }
                else {

                }
            }

        }

    }

    public static void main(String[] args) {

    }

    private void printVector(RealVector v) {
        System.out.println("Print real vector with dim: " + v.getDimension());
        for (int i = 0; i<v.getDimension(); ++i) {
            System.out.print(v.getEntry(i) + ", ");
        }
        System.out.println();
    }

    private void printMatrix(RealMatrix a) {
        System.out.println("Print real matrix with rows: " + a.getRowDimension() + " and columns: " + a.getColumnDimension());
        for (int i = 0; i<a.getRowDimension(); ++i) {
            for (int j = 0; j<a.getColumnDimension(); ++j) {
                System.out.print(a.getEntry(i,j) + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
