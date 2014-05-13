package com.ntt.garante;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.ntt.garante.model.ValueEvent;

public final class DataEnricher implements EventHandler<ValueEvent> {
	Logger logger=Logger.getLogger(getClass());

	@Override
	public void onEvent(ValueEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		logger.info("Enrichment of:"+event.getValue());
		
	}
}