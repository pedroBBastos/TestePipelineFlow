package com.teste.pipeline.processing;

import com.teste.pipeline.frame.Frame;

public interface FrameProcessor {
    Frame processFrame(Frame comparableFrame) throws InterruptedException;

    static int getRandomDelay() {
        // this give a random number between and 4
        int random = ( int ) ( Math.random() * 4 );
        return 100 * ( random + 1 );
    }
}
