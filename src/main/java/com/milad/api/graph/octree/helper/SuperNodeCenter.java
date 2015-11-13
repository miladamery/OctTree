package com.milad.api.graph.octree.helper;

import com.milad.api.utility.math.vectors.vector3D.Vector3D;

/**
 *
 * @author Milad
 */
public class SuperNodeCenter {
    
    private Vector3D sumOfAllVerticePositions;
    private int sumOfAllVertices;

    public SuperNodeCenter() {
        this.sumOfAllVerticePositions = new Vector3D();
        this.sumOfAllVertices = 0;
    }
    
    public void changeCenter(Vector3D axis){
        this.sumOfAllVerticePositions.setxAxis(this.sumOfAllVerticePositions.getxAxis() + axis.getxAxis());
        this.sumOfAllVerticePositions.setyAxis(this.sumOfAllVerticePositions.getyAxis() + axis.getyAxis());
        this.sumOfAllVerticePositions.setzAxis(this.sumOfAllVerticePositions.getzAxis() + axis.getzAxis());
        this.sumOfAllVertices++;
    }
    
    public Vector3D getCenter(){
        int xAxis = (int) (this.sumOfAllVerticePositions.getxAxis() / this.sumOfAllVertices);
        int yAxis = (int) (this.sumOfAllVerticePositions.getyAxis() / this.sumOfAllVertices);
        int zAxis = (int) (this.sumOfAllVerticePositions.getzAxis() / this.sumOfAllVertices);
        return new Vector3D(xAxis, yAxis, zAxis);
    }

    public int getSumOfAllVertices() {
        return sumOfAllVertices;
    }
    
    
}
