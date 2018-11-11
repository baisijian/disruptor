package com.baisijian.rock;

import com.lmax.disruptor.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A {
    public static void main(String[] args) throws InterruptedException {

        LongEventFactory longEventFactory = new LongEventFactory();

        int bufferSize = 1024;

        RingBuffer<LongEvent> ringBuffer = RingBuffer.createSingleProducer(longEventFactory, bufferSize, new BlockingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        WorkHandler<LongEvent>[] workHandlers = new WorkHandler[3];
        for (int i = 0; i <= 2; i++) {
            workHandlers[i] = new LongEventHandler();
        }

        WorkerPool<LongEvent> workerPool = new WorkerPool<LongEvent>(ringBuffer, sequenceBarrier, new ExceptionHandle(), workHandlers);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        workerPool.start(executor);

        for (int x = 1; x <= 100; x++) {
            long sequence = ringBuffer.next();
            try {
                LongEvent event = ringBuffer.get(sequence);
                event.setValue(x);
                //System.out.println("sAet:"+x);
            } finally {
                ringBuffer.publish(sequence);
            }
        }

        for (int i = 0; i <= 2; i++) {
            System.out.println(((LongEventHandler)workHandlers[i]).count);
        }
        //workerPool.halt();
        //executor.shutdown();
    }
}
