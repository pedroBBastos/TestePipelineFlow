package com.teste.pipeline.processing;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.teste.pipeline.MyPriorityBlockingQueue;
import com.teste.pipeline.frame.ComparableFrame;
import com.teste.pipeline.frame.Frame;

/**
 * This class implement the {@link DataFilter} using {@link MyPriorityBlockingQueue}
 */
public class DataFilter {

    Logger logger = LoggerFactory.getLogger(DataFilter.class);

    private final MyPriorityBlockingQueue<ComparableFrame> inDataQueue;
    private final MyPriorityBlockingQueue<ComparableFrame> outDataQueue;
    private final FrameProcessor frameProcessor;
    private final int layerSize;
    private String id;

    public DataFilter(MyPriorityBlockingQueue<ComparableFrame> inDataQueue,
                      MyPriorityBlockingQueue<ComparableFrame> outDataQueue,
                      FrameProcessor frameProcessor,
                      int layerSize,
                      String id) {
        this.inDataQueue = inDataQueue;
        this.outDataQueue = outDataQueue;
        this.frameProcessor = frameProcessor;
        this.layerSize = layerSize;
        this.id = id;
    }

    public void start() {
        boolean isActive = true;
        System.out.println("Ligando data filter " + id);

        while ( isActive ) {
            try {
                ComparableFrame inputFrame = inDataQueue.extractIfOrCopy( frame -> !frame.isLastFrame() );

                if (!inputFrame.isLastFrame())
                {
                    ComparableFrame processedFrame = frameProcessor.processFrame( (Frame) inputFrame );

                    if (outDataQueue != null) {
                        outDataQueue.put( processedFrame );
                    } else {
                        System.out.println("Fimm "+id+" -> " + ((Frame) inputFrame).getFrameContent());
                    }
                } else if (inputFrame.isLastFrameOfLayer()) {

                    if (outDataQueue != null) {
                        outDataQueue.insertLastObject(layerSize,
                            ComparableFrame::isLastFrame,
                            ComparableFrame::decreaseLayerSize,
                            Frame::createLastFrameWithLayerSize);
                    } else {
                        System.out.println("Fimm "+id+" -> " + ((Frame) inputFrame).getFrameContent());
                    }
                    isActive = false;
                } else {
                    // faltou isso aqui no código original.... se for o último e ainda tem mais do layer anterior
                    // para receber tenho que decrementar a contagem....
                    inputFrame.decreaseLayerSize();
                }
            } catch ( InterruptedException e ) {
                logger.error("Deu ruim...", e);
            }
        }
    }
}