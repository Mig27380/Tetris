
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class VariableTimer {
	
	private ScheduledExecutorService scheduler=Executors.newSingleThreadScheduledExecutor();
	private boolean toEnd=false;
	private long interval=1000;
	
	public VariableTimer(long interval) {
		this.interval=interval;
		setScheduler();
	}
	
	public VariableTimer() {
		
	}
	
	public void setInterval(long interval) {
		this.interval=interval;
		scheduler.shutdown();
		scheduler=Executors.newSingleThreadScheduledExecutor();
		setScheduler();
	}
	
	public void setDelayedInterval(long interval) {
		this.interval=interval;
		toEnd=true;
	}
	
	public void stop() {
		scheduler.shutdown();
	}
	
	private Runnable instantRunnable(){
		return new Runnable() {

			@Override
			public void run() {
				if(toEnd) {
					setInterval(interval);
					toEnd=false;
				}
				else {
					task();
				}
			}
		};
	}
	
	public abstract void task();
	
	private void setScheduler() {
		scheduler.scheduleAtFixedRate(instantRunnable(), 0, interval, TimeUnit.MILLISECONDS);
	}
	
}