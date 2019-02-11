package com.enixlin.jmrc.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.SchedulingAwareRunnable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class Schedule  implements SchedulingAwareRunnable{
	
//	
//	@Scheduled(fixedRate = 3000)：定时器将在每隔3秒执行
//
//	@Schedule(fixedDelay = 3000):定时器将在延迟3秒后每隔3秒执行
//
//	@Schedule(initialDelay = 1000, fiexdDelay = 3000):定时器将在1秒后每隔3秒执行
//
//	@Schedule(cron = “* * * * * ?”)
//
//	    一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
//	    按顺序依次为
//	    秒（0~59）
//	    分钟（0~59）
//	    小时（0~23）
//	    天（月）（0~31，但是你需要考虑你月的天数）
//	    月（0~11）
//	    天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
//	    7.年份（1970－2099）


	private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Scheduled(fixedRate = 3000)
	public String TestSchedule() {
		String result="当前时间：" + dateFormat.format(new Date());
		System.out.println(result);
		return result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLongLived() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
