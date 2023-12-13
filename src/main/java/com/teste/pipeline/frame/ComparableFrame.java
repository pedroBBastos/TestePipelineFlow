package com.teste.pipeline.frame;

public interface ComparableFrame extends Comparable< ComparableFrame > {

    /**
     * @return the raw data of the frame
     */
    byte[] getData();

    /**
     * @return the sequence number of the frame
     */
    long getSequenceNumber();

    /**
     * @return true if the frame is a LastFrame
     */
    boolean isLastFrame();

    /**
     * @return true if the frame is a LastFrame for the previous layer
     */
    boolean isLastFrameOfLayer();

    /**
     * It modifies the current frame and reduces the layer size
     */
    Void decreaseLayerSize();
}