package com.milad.api.graph.octree;

import com.milad.api.graph.octree.abstracts.Body;
import com.milad.api.graph.octree.exception.ExternalNodeBodyIsSet;
import com.milad.api.graph.octree.exception.ExternalNodeIsNotDenseLeaf;
import com.milad.api.graph.octree.exception.NodeIsInternalException;
import com.milad.api.graph.octree.nodes.OcTreeNode;
import com.milad.api.utility.math.vectors.vector3D.Vec3DMath;

/**
 *
 * @author Milad
 */
public abstract class OcTree {

    private OcTreeNode root;
    private final float theta = (float) 1.2;
    private int frameX;
    private int frameY;
    private int frameZ;

    public OcTree(int frameX, int frameY, int frameZ) {
        this.frameX = frameX;
        this.frameY = frameY;
        this.frameZ = frameZ;
        this.root = new OcTreeNode(0, 0, 0, frameX, frameY, frameZ, 0);
    }

    /**
     * 
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet 
     */
    public void insert(Body body) throws ExternalNodeIsNotDenseLeaf, NodeIsInternalException, ExternalNodeBodyIsSet {
        OcTreeNode tempNode = this.root;
        this.insert(tempNode, body);
    }

    /**
     * 
     * @param node
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet 
     */
    private void insert(OcTreeNode node, Body body) throws ExternalNodeIsNotDenseLeaf, NodeIsInternalException, ExternalNodeBodyIsSet {
        int i1 = node.getNodeIndex(body);
        if (node.isExternal()) {
            // if node is a dense leaf
            if (node.isDenseLeaf()) {
                node.setThisDenseLeafNodeBody(body);
            } /*if node is external and empty*/ else if (node.isEmptyExternal()) {
                node.setThisExternalNodeBody(body);
            } /*if node is external but full*/ else {
                Body tempBody = node.makeThisNodeInternal();
                node.changeCenter(body.getPosition());
                insert(node.getChildren()[i1], body);
                int i2 = node.getNodeIndex(tempBody);
                node.changeCenter(tempBody.getPosition());
                insert(node.getChildren()[i2], tempBody);
            }
        } /*node is internal*/ else {
            node.changeCenter(body.getPosition());
            insert(node.getChildren()[i1], body);
        }
    }

    /**
     * 
     * @param body 
     */
    public void ForceOnBody(Body body) {
        OcTreeNode tempNode = this.root;
        this.calcForce(tempNode, body);
    }

    /**
     * 
     * @param node
     * @param body 
     */
    public void calcForce(OcTreeNode node, Body body) {

        if (node.isExternal()) {
            //if node is dense leaf
            if (node.isDenseLeaf()) {
                for(Body dlbody: node.getExternalNode().getAllBodies()){
                    if(body.getId() != dlbody.getId())
                        forceCalculationHelper(body, dlbody);
                }
            } /*if node is simple external but empty*/else if (node.isEmptyExternal()) {
                    //do nothing
            } /*node is simple external but full*/else {
                if(node.getExternalNode().getBody().getId() != body.getId())
                    forceCalculationHelper(node.getExternalNode().getBody(), body);
            }
        } /*node is internal*/ else {
            
            if((node.getWidth() / Vec3DMath.value(Vec3DMath.subtraction(node.getCenterOfSuperNode(), body.getPosition()))) <= this.theta){
                forceCalculationHelper(node.getSuperNode(), body);
            } else {
                calcForce(node.getChildren()[0], body);
                calcForce(node.getChildren()[1], body);
                calcForce(node.getChildren()[2], body);
                calcForce(node.getChildren()[3], body);
                calcForce(node.getChildren()[4], body);
                calcForce(node.getChildren()[5], body);
                calcForce(node.getChildren()[6], body);
                calcForce(node.getChildren()[7], body);
            }
        }
    }

    /**
     * 
     */
    public void resetTree(){
        this.root = null;
        this.root = new OcTreeNode(0, 0, 0, frameX, frameY, frameZ, 0);
    }
    
    /**
     * 
     * @param body
     * @param dlbody 
     */
    protected abstract void forceCalculationHelper(Body body, Body dlbody);
}
