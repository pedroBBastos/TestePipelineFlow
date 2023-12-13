package com.teste.pipeline.processing;

import static java.lang.Thread.sleep;

import com.teste.pipeline.frame.Frame;

public class L1Processor implements FrameProcessor {
    @Override
    public Frame processFrame(Frame comparableFrame) throws InterruptedException {
        System.out.println("L1: Processando frame " + comparableFrame.getFrameContent());
        comparableFrame.addProcessingMark("-PassouL1");
        sleep( 1000 );
        return comparableFrame;
    }
}
