package com.milad.api.graph.octree.nodes;

import com.milad.api.graph.octree.abstracts.Body;
import com.milad.api.graph.octree.entity.Oct;
import com.milad.api.graph.octree.exception.ExternalNodeBodyIsSet;
import com.milad.api.graph.octree.exception.ExternalNodeIsNotDenseLeaf;
import com.milad.api.graph.octree.exception.NodeIsInternalException;
import com.milad.api.utility.math.vectors.vector3D.Vector3D;

/**
 *
 * @author Milad
 */
public class OcTreeNode {
    
    private OcTreeNode[] children;
    private OcTreeSuperNode superNode;
    private ExternalNode externalNode;
    private Oct oct;
    private int depth;
    private final int denseLeafThreshHold = 11;

    public OcTreeNode(int startX, int startY, int startZ ,int endX ,int endY ,int endZ, int depth) {
        this.children = null;
        this.superNode = null;
        this.oct = new Oct(startX, startY, startZ, endX, endY, endZ);
        this.depth = depth;
        if (depth == this.denseLeafThreshHold) {
            externalNode.setIsDenseLeaf(true);
        }
    }        
    
    /**
     * 
     * @return
     * @throws NodeIsInternalException 
     */
    public Body makeThisNodeInternal() throws NodeIsInternalException{
        if(this.isInternal())
            throw new NodeIsInternalException("Node Is Already Internal!");
        this.children = new OcTreeNode[8];
        this.superNode = new OcTreeSuperNode();
        int splitX = (int) (oct.getStartAxis().getxAxis() + oct.getEndAxis().getxAxis());
        int splitY = (int) (oct.getStartAxis().getyAxis() + oct.getEndAxis().getyAxis());
        int splitZ = (int) (oct.getStartAxis().getzAxis() + oct.getEndAxis().getzAxis());
        this.children[0] = new OcTreeNode(
                (int) oct.getStartAxis().getxAxis(),
                (int) oct.getStartAxis().getyAxis(),
                0,
                splitX, splitY, 0, depth + 1);
        this.children[1] = new OcTreeNode(
                splitX, (int) oct.getStartAxis().getyAxis(), 0, (int) oct.getEndAxis().getxAxis(), splitY, 0, depth + 1);
        this.children[2] = new OcTreeNode((int) oct.getStartAxis().getxAxis(), splitY, 0,
                splitX, (int) oct.getEndAxis().getyAxis(), 0, depth + 1);
        this.children[3] = new OcTreeNode(splitX, splitY, 0,
                (int) oct.getEndAxis().getxAxis(), (int) oct.getEndAxis().getyAxis(), 0, depth + 1);
        this.children[0] = new OcTreeNode(
                (int) oct.getStartAxis().getxAxis(), (int) oct.getStartAxis().getyAxis(), splitZ,
                splitX, splitY, splitZ, depth + 1);
        this.children[1] = new OcTreeNode(
                splitX, (int) oct.getStartAxis().getyAxis(), splitZ,
                (int) oct.getEndAxis().getxAxis(), splitY, splitZ, depth + 1);
        this.children[2] = new OcTreeNode((int) oct.getStartAxis().getxAxis(), splitY, splitZ,
                splitX, (int) oct.getEndAxis().getyAxis(), splitZ, depth + 1);
        this.children[3] = new OcTreeNode(splitX, splitY, splitZ,
                (int) oct.getEndAxis().getxAxis(), (int) oct.getEndAxis().getyAxis(), splitZ, depth + 1);
        Body tempBody = this.externalNode.getBody();
        this.externalNode = null;
        return tempBody;
    }
    
    /**
     * 
     * @param body
     * @throws NodeIsInternalException
     * @throws ExternalNodeBodyIsSet 
     */
    public void setThisExternalNodeBody(Body body) throws NodeIsInternalException, ExternalNodeBodyIsSet{
        if(this.isInternal())
            throw new NodeIsInternalException("Node is internal. Can not set body for this Node!");
        if(!this.isEmptyExternal())
            throw new ExternalNodeBodyIsSet("External node body is set!");
        this.externalNode.setBody(body);
    }
    
    /**
     * 
     * @param body
     * @throws ExternalNodeIsNotDenseLeaf 
     */
    public void setThisDenseLeafNodeBody(Body body) throws ExternalNodeIsNotDenseLeaf{
        this.externalNode.addToDenseLeaf(body);
    }
    
    /**
     * 
     * @param body
     * @return node index in tree
     */
    public int getNodeIndex(Body body){
        int splitX = (int) (oct.getStartAxis().getxAxis() + oct.getEndAxis().getxAxis()) / 2;
        int splitY = (int) (oct.getStartAxis().getyAxis() + oct.getEndAxis().getyAxis()) / 2;
        int splitZ = (int) (oct.getStartAxis().getzAxis() + oct.getEndAxis().getzAxis()) / 2;
        int i = (body.getPosition().getxAxis()>= splitX ? 1 : 0) + (body.getPosition().getyAxis()>= splitY ? 2 : 0) + 
                (body.getPosition().getzAxis()>= splitZ ? 4 : 0);
        return i;
    }
    
    /**
     * checks if node is simple external
     *
     * @return boolean
     */
    public boolean isExternal() {
        return this.children == null;
    }

    /**
     * checks if node is internal
     *
     * @return boolean
     */
    public boolean isInternal() {
        return this.children != null;
    }

    /**
     * checks if simple external node is empty
     *
     * @return boolean
     */
    public boolean isEmptyExternal() {
        return this.externalNode.isEmpty();
    }

    /**
     * checks if node is denseLeaf
     *
     * @return boolean
     */
    public boolean isDenseLeaf() {
        return this.externalNode.isIsDenseLeaf();
    }
    
    /**
     * 
     * @param axis 
     */
    public void changeCenter(Vector3D axis){
        this.superNode.changeCenter(axis);
    }
    
    /**
     * 
     * @return 
     */
    public Vector3D getCenterOfSuperNode(){
        return this.superNode.getPosition();
    }
    
    /**
     * 
     * @return 
     */
    public int getWidth(){
        return this.oct.getWidth();
    }
    
    /**
     * 
     * @return 
     */
    public int getSize(){
        return this.superNode.getSuperNodeBodySize();
    }

    public OcTreeNode[] getChildren() {
        return children;
    }

    public OcTreeSuperNode getSuperNode() {
        return superNode;
    }

    public ExternalNode getExternalNode() {
        return externalNode;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    
}
