package com.ntt.garante;

import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.ntt.garante.model.ValueEvent;

public class Test {
	
	public static final int RING_SIZE=16;
	/**
     * Vedi https://github.com/LMAX-Exchange/disruptor/wiki/Disruptor-Wizard
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		BasicConfigurator.configure();
		final int availableProcessors = Runtime.getRuntime().availableProcessors();
		final Logger logger = Logger.getLogger("main");
		logger.info("Processori diposnibili:"+availableProcessors);
		Disruptor<ValueEvent> disruptor =
				  new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, RING_SIZE,Executors.newCachedThreadPool());

		EventHandler<ValueEvent> consumer1=
				new DataVerifier();
		EventHandler<ValueEvent> consumer2 = new DataEnricher();
		
		// Creiamo una "coda"
		disruptor.handleEventsWith(consumer1);
		disruptor.after(consumer1).handleEventsWith(consumer2);
		RingBuffer<ValueEvent> buffer = disruptor.start();
		
		Runnable r=new LogPusher(disruptor,80);
		(new Thread(r)).start();

		Runnable r2=new FastLogPusher(disruptor,80);
		(new Thread(r2)).start();
		
		while(true){
			Thread.sleep(200);
			logger.info("BufferCursor:"+buffer.getCursor());
		}
	}

}
