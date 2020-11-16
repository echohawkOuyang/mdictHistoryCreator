package cn.echohawk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @类名称：DateUtils
 * @类描述：日期时间工具类
 * @作者：HuangSen
 * @日期：2018年10月9日
 */
public class DateUtils {

	public static Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    
    /**
     * @功能描述 获取年月日时分秒毫秒
     */
    public static String getDateTimeToMsec(){
        Calendar CD = Calendar.getInstance();
        String YY = String.valueOf(CD.get(Calendar.YEAR));
        String MM = String.valueOf(CD.get(Calendar.MONTH)+1);
        String DD = String.valueOf(CD.get(Calendar.DATE));
        String HH = String.valueOf(CD.get(Calendar.HOUR_OF_DAY));
        String mm = String.valueOf(CD.get(Calendar.MINUTE));
        String SS = String.valueOf(CD.get(Calendar.SECOND));
        String MS = String.valueOf(CD.get(Calendar.MILLISECOND));
        if(1 == MM.length()){
            MM = "0" + MM;
        }
        if(1 == DD.length()){
            DD = "0" + DD;
        }
        if(1 == HH.length()){
            HH = "0" + HH;
        }
        if(1 == mm.length()){
            mm = "0" + mm;
        }
        if(1 == SS.length()){
            SS = "0" + SS;
        }
        if(1 == MS.length()){
            MS = "00" + MS;
        }
        if(2 == MS.length()){
            MS = "0" + MS;
        }
        return YY+MM+DD+HH+mm+SS+MS;
    }
    
    public static String getNowStr(){
    	Date now = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(now);
    }

	public static String getNowDayStr(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(now);
	}
    
    public static Date getMidnight(Date time, int day) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(time);
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return calendar.getTime();
    }
    
    public static Date getMonthFirstDay(Date date, int offset)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, offset);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    
    public static Date getWeekFirstDay(Date date, int firstDayOfWeek)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    
    /**
     * 
     * @param date
     * @param flag   true 为时间的开始    false 为某天时间的结束
     * @return
     */
    public static Date getDateStartOrEnd(Date date, boolean flag) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY, flag ? 0 : 23);
    	calendar.set(Calendar.MINUTE, flag ? 0 : 59);
    	calendar.set(Calendar.SECOND, flag ? 0 : 59);
    	calendar.set(Calendar.MILLISECOND, flag ? 0 : 999);
    	return calendar.getTime();
    }
    
    /**
     * 对指定日期增加或减少多少天
     * @param sourceDate  基准日期,格式yyyyMMdd
     * @param days 增加或减少天数
     * @return 格式yyyyMMdd
     * @throws ParseException 
     */
    public static Date addDay(Date date,int days)  {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_YEAR,days);
        return calendar.getTime();
    }    
    
    public static Date addMonth(Date date,int month) throws ParseException {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH,month);
        return calendar.getTime();
    }  
    
    public static String getDateStr(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date toDate(String dateStr, String format) {
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			LOG.error("时间转换错误", e);
		}
    	return null;
	}
    
    
}
