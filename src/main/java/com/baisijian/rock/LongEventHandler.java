package com.baisijian.rock;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class LongEventHandler implements WorkHandler<LongEvent> {

    public int count=0;

    public void onEvent(LongEvent longEvent) throws Exception {
        count += longEvent.getValue();
        System.out.println(Thread.currentThread().getName() + " Count："+count);
        //System.out.println(Thread.currentThread().getName() + " Event："+longEvent.getValue());
    }
}

