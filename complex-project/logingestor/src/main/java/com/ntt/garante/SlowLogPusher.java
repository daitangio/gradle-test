package com.ntt.garante;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.ntt.garante.model.ValueEvent;

public final class SlowLogPusher implements EventTranslator<ValueEvent>,Runnable {
	private Disruptor<ValueEvent> disruptor;
	private int c=0;
	protected Logger logger = Logger.getLogger(getClass());
	private int limit;

	public SlowLogPusher(Disruptor<ValueEvent> disruptor, int l) {
		this.disruptor=disruptor;
		this.limit=l;
	}

	@Override
	public void translateTo(ValueEvent event, long sequence) {	
		logger.info("Publishing:"+c+ " Remaining:"+(limit-c));
		event.setValue(c);
	}

	@Override
	public void run() {
		try {
			while(c<=limit){
				c++;
					Thread.sleep(Math.max(50, 2000-c));
					disruptor.publishEvent(this);
			}
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		
	}
}