package com.teste.pipeline;

import com.teste.pipeline.frame.ComparableFrame;
import com.teste.pipeline.frame.Frame;
import com.teste.pipeline.processing.DataFilter;
import com.teste.pipeline.processing.L1Processor;
import com.teste.pipeline.processing.L2Processor;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        MyPriorityBlockingQueue<ComparableFrame> entranceQueue = new MyPriorityBlockingQueue<>(100);
        MyPriorityBlockingQueue<ComparableFrame> middleQueue = new MyPriorityBlockingQueue<>(100);

        List<DataFilter> engineDataFilters = new ArrayList<>();

        int l1Size = 4;
        for (int i=0; i<l1Size; i++) {
            engineDataFilters.add(createL1DataFilters(entranceQueue, middleQueue, l1Size, i+""));
        }

        int l2Size = 10;
        for (int i=0; i<l2Size; i++) {
            engineDataFilters.add(createL2DataFilters(middleQueue, null, l2Size, i+""));
        }

        MultiThreadDataEngine multiThreadDataEngine = new MultiThreadDataEngine(engineDataFilters);
        multiThreadDataEngine.start();

        for (int i=0; i < 50; i++) {
            System.out.println("Inserindo frame " + i + " para processamento..");
            Frame newFrame = new Frame("teste-"+i, i, false);
            entranceQueue.put(newFrame);
        }
        entranceQueue.insertLastObject(1,
            ComparableFrame::isLastFrame,
            ComparableFrame::decreaseLayerSize,
            Frame::createLastFrameWithLayerSize);
    }

    public static DataFilter createL1DataFilters(MyPriorityBlockingQueue<ComparableFrame> inQueue,
                                                 MyPriorityBlockingQueue<ComparableFrame> outQueue,
                                                 int layerSize, String id) {
        return new DataFilter(inQueue, outQueue, new L1Processor(), layerSize, "L1-"+id);
    }

    public static DataFilter createL2DataFilters(MyPriorityBlockingQueue<ComparableFrame> inQueue,
                                                 MyPriorityBlockingQueue<ComparableFrame> outQueue,
                                                 int layerSize, String id) {
        return new DataFilter(inQueue, outQueue, new L2Processor(), layerSize, "L2-"+id);
    }
}