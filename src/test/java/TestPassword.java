import com.homeTest.exception.InvalidPasswordException;
import com.homeTest.impl.PasswordValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestPassword {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = InvalidPasswordException.class)
    /***
     * Test password throws if password length is not greater than 18 Character
     */
    public void testPasswordLength() throws InvalidPasswordException {
        String oldPassword = "test123";
        String newPassword = "Test12345";
        PasswordValidator passwordValidator = PasswordValidator.getInstance();
        passwordValidator.changePassword(oldPassword, newPassword);
        expectedException.expect(InvalidPasswordException.class);
        expectedException.expectMessage("Password length cannot be less than 18");
    }

    @Test(expected = InvalidPasswordException.class)
    /***
     * Test password throws if password does not have one upper case
     * one lower case one digit and one special character
     */
    public void testSpecialChars() throws InvalidPasswordException {
        String oldPassword = "oldpassword1234";
        String newPassword = "newpassword123!@#$&*";
        PasswordValidator passwordValidator = PasswordValidator.getInstance();
        passwordValidator.changePassword(oldPassword, newPassword);
        expectedException.expect(InvalidPasswordException.class);
        expectedException.expectMessage("Password must contain one upper case, one lower case, one numeric and one special character");
    }

    @Test(expected = InvalidPasswordException.class)
    /***
     *  Test password cannot have more than 50% of digits
     */
    public void testPasswordDigitCompare() throws InvalidPasswordException {
        String oldPassword = "test";
        String newPassword = "Newpass012345678911&";
        PasswordValidator passwordValidator = PasswordValidator.getInstance();
        passwordValidator.changePassword(oldPassword, newPassword);
        expectedException.expect(InvalidPasswordException.class);
        expectedException.expectMessage("50% of password should not be a number");
    }

    @Test(expected = InvalidPasswordException.class)
    /***
     *  Test old password and new password cannot have more than 80% match.
     */
    public void testPasswordMatch() throws InvalidPasswordException {
        String oldPassword = "oldpass012345678911&";
        String newPassword = "Newpass012345678911&";
        PasswordValidator passwordValidator = PasswordValidator.getInstance();
        passwordValidator.changePassword(oldPassword, newPassword);
        expectedException.expect(InvalidPasswordException.class);
        expectedException.expectMessage("New password cannot match more than 80%");
    }

    @Test
    /***
     * Positive test passing all the password conditions
     */
    public void testPasswordSuccess() throws InvalidPasswordException {
        String oldPassword = "oldpass012345678911&";
        String newPassword = "Newpassword213!@#$";
        PasswordValidator passwordValidator = PasswordValidator.getInstance();
        var result = passwordValidator.changePassword(oldPassword, newPassword);
        assert (result);
    }

}

