package project.matthew.booster.Helper;

/**
 * Created by Matthew on 4/05/2018.
 */

public class FormHelper {
    public static boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
