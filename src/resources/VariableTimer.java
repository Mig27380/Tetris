package resources;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class VariableTimer {
	
	private ScheduledExecutorService scheduler=Executors.newSingleThreadScheduledExecutor();
	
	public VariableTimer(long interval) {
		setScheduler(interval);
	}
	
	public VariableTimer() {
		
	}
	
	public void setInterval(long interval) {
		scheduler.shutdown();
		scheduler=Executors.newSingleThreadScheduledExecutor();
		setScheduler(interval);
	}
	
	public void stop() {
		scheduler.shutdown();
	}
	
	private Runnable runnable(){
		return new Runnable() {

			@Override
			public void run() {
				task();
			}
		};
	}
	
	public abstract void task();
	
	private void setScheduler(long interval) {
		scheduler.scheduleAtFixedRate(runnable(), 0, interval, TimeUnit.MILLISECONDS);
	}
	
}