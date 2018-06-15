package mainPackage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicUtil {

	public static String todaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
