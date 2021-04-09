package Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FormateoHora 
{
	public static String getFechaHoraActualEnFormatoBDD()
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	public static String getFechaHora(Timestamp fechaHora)
	{
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(fechaHora);
	}
	
	public static String getFechaActual()
	{
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	public static String getAnioActual()
	{
		return new SimpleDateFormat("yyyy").format(new Date());
	}
	
	public static String getMesActual()
	{
		return new SimpleDateFormat("MM").format(new Date());
	}
	
	public static String getFechaHoraEnFormatoBDD(Timestamp fechaHora)
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(fechaHora);
	}
	
	public static String getHoraEnFormatoBDD(Timestamp hora)
	{
		return new SimpleDateFormat("HHmmss").format(hora);
	}
	
	public static String getHora(Timestamp hora)
	{
		return new SimpleDateFormat("HH:mm").format(hora);
	}
	
	public static int getHoraInt(Timestamp hora)
	{
		return Integer.parseInt(new SimpleDateFormat("HH").format(hora));
	}
	
	public static int getMinutoInt(Timestamp hora)
	{
		return Integer.parseInt(new SimpleDateFormat("mm").format(hora));
	}
	
	public static boolean esMayorQueLaFechaActual(Timestamp fechaHora)
	{
		return fechaHora.after(new Date());
	}
}