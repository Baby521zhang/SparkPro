package top.zcg.test;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.source.AbstractSource;

/**
 * 自定义flume数据源
 * @author zhangchenguang
 *
 */
public class MySource extends AbstractSource implements PollableSource, Configurable {

	@Override
	public void configure(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getBackOffSleepIncrement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMaxBackOffSleepInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Status process() throws EventDeliveryException {
		// TODO Auto-generated method stub
		return null;
	}

}
