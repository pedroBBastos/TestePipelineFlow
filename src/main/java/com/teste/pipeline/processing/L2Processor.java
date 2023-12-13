package com.teste.pipeline.processing;

import static java.lang.Thread.sleep;

import com.teste.pipeline.frame.Frame;

public class L2Processor implements FrameProcessor {
    @Override
    public Frame processFrame(Frame comparableFrame) throws InterruptedException {
        System.out.println("L2: Processando frame " + comparableFrame.getFrameContent());
        comparableFrame.addProcessingMark("-PassouL2");
        sleep( 1000 );
//        sleep( FrameProcessor.getRandomDelay() );
        return null;
    }
}
