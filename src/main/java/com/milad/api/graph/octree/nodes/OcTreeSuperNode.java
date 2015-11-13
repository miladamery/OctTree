package com.milad.api.graph.octree.nodes;

import com.milad.api.graph.octree.abstracts.Body;
import com.milad.api.graph.octree.helper.SuperNodeCenter;
import com.milad.api.utility.math.vectors.vector3D.Vector3D;

/**
 *
 * @author Milad
 */
public class OcTreeSuperNode implements Body {

    private SuperNodeCenter center;

    public OcTreeSuperNode() {
        center = new SuperNodeCenter();
    }

    @Override
    public Long getId() {
        return -1l;
    }

    @Override
    public Vector3D getPosition() {
        return this.center.getCenter();
    }

    public void changeCenter(Vector3D axis) {
        this.center.changeCenter(axis);
    }

    public int getSuperNodeBodySize() {
        return this.center.getSumOfAllVertices();
    }
}
