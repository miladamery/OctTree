package com.milad.api.graph.octree.nodes;

import java.util.ArrayList;

import com.milad.api.graph.octree.abstracts.Body;
import com.milad.api.graph.octree.exception.ExternalNodeIsNotDenseLeaf;

/**
 *
 * @author PC
 */
public class ExternalNode {
        
    private ArrayList<Body> body;
    private boolean isDenseLeaf;

    /**
     * Initializing class
     */
    public ExternalNode() {
        body = new ArrayList<>();
        isDenseLeaf = false;
    }
    
    /**
     * if there isn't set any body for this leaf it will return false else true
     * @return boolean
     */
    public boolean isEmpty(){
        return this.body.isEmpty();
    }
    
    /**
     * if this leaf is a dense leaf node adds new node to its array list
     * if it is not a dense leaf it will throw an ExternalNodeIsNotDenseLeaf
     * @param newBody
     * @return 
     * @throws OctQuadTree.Exceptions.ExternalNodeIsNotDenseLeaf 
     */
    public boolean addToDenseLeaf(Body newBody) throws ExternalNodeIsNotDenseLeaf{
        if(!this.isDenseLeaf)
            throw new ExternalNodeIsNotDenseLeaf("This leaf is not set as dense leaf!");
        return this.body.add(newBody);
    }
    
    /**
     * return the first node in array
     * use this for when node state is normal
     * @return T
     */
    public Body getBody(){
        return this.body.get(0);
    }
    
    /**
     * sets the first body in array
     * use this for when node state is normal
     * @param body 
     */
    public void setBody(Body body){
        this.body.add(0, body);
    }

    public boolean isIsDenseLeaf() {
        return isDenseLeaf;
    }

    public void setIsDenseLeaf(boolean isDenseLeaf) {
        this.isDenseLeaf = isDenseLeaf;
    }

    /**
     * Get all bodies set for this node
     * use this when leaf state is "dense leaf"
     * @return 
     */
    public ArrayList<Body> getAllBodies() {
        return body;
    }
    
}
