package com.teste.pipeline;

import com.teste.pipeline.processing.DataFilter;
import java.util.List;

public class MultiThreadDataEngine {
    private final List<DataFilter> dataFilters;

    public MultiThreadDataEngine( List<DataFilter> dataFilters ) {
        this.dataFilters = dataFilters;
    }

    public void start() {
        for ( DataFilter dataFilter : dataFilters) {
            runInAnotherThread( dataFilter::start );
        }
    }

    private void runInAnotherThread( Runnable runnable ) {
        Thread thread = new Thread( runnable );
        thread.start();
    }
}
