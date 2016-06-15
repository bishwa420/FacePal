/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import cern.colt.matrix.linalg.EigenvalueDecomposition;


public class EigenvalueDecomp extends EigenvalueDecomposition
{
  public EigenvalueDecomp(Matrix2D dmat)
  {  super(dmat); }


  public double[] getEigenValues()
  {  return diag( getD().toArray());  }


  public double[][] getEigenVectors()
  {  return getV().toArray(); }


  private double[] diag(double[][] m)
  {
    double[] diag = new double[m.length];
    for (int i = 0; i < m.length; i++)
      diag[i] = m[i][i];
    return diag;
  }  // end of diag()
	
}  // end of EigenvalueDecomp class
