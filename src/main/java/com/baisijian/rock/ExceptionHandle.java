package com.baisijian.rock;

import com.lmax.disruptor.ExceptionHandler;


public final class ExceptionHandle implements ExceptionHandler<Object> {

    public ExceptionHandle() {
    }


    public void handleEventException(Throwable ex, long sequence, Object event) {
        System.out.println("Exception processing: " + sequence + " " + event + ex);
    }

    public void handleOnStartException(Throwable ex) {
        System.out.println("Exception during onStart()"+ ex);
    }

    public void handleOnShutdownException(Throwable ex) {
        System.out.println("Exception during onShutdown()" + ex);
    }
}
