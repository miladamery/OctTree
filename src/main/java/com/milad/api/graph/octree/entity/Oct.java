package com.milad.api.graph.octree.entity;

import com.milad.api.utility.math.vectors.vector3D.Vector3D;

/**
 *
 * @author Milad
 */
public class Oct {
    
    private Vector3D startAxis;
    private Vector3D endAxis;

    public Oct() {
        startAxis = new Vector3D();
        endAxis = new Vector3D();
    }

    
    public Oct(int startX, int startY, int startZ ,int endX, int endY, int endZ) {
        this.startAxis = new Vector3D(startX, startY, startZ);
        this.endAxis = new Vector3D(endX, endY, endZ);
    }

    public Oct(Vector3D startAxis, Vector3D endAxis) {
        this.startAxis = startAxis;
        this.endAxis = endAxis;
    }

    
    public int getWidth(){
        return (int) Math.abs(this.startAxis.getxAxis() - this.endAxis.getxAxis());
    }
    
    public Vector3D getStartAxis() {
        return startAxis;
    }

    public void setStartAxis(Vector3D startAxis) {
        this.startAxis = startAxis;
    }

    public Vector3D getEndAxis() {
        return endAxis;
    }

    public void setEndAxis(Vector3D endAxis) {
        this.endAxis = endAxis;
    }
    
    
}
