package Orders;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	public static boolean validateEmail(String email)
	{
		String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		if(matcher.matches())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
