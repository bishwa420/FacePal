/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

/**
 *
 * @author Tapos
 */
public class ImageDistanceInfo
{
  private int index;
  private double value;

  public ImageDistanceInfo(double val, int idx)
  { value = val;
    index = idx;
  }

  public int getIndex()
  { return index; }

  public double getValue()
  { return value;  }

} // end of ImageDistanceInfo class


