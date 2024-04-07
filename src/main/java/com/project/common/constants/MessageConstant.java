/**
 * Messages Constants
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
package com.project.common.constants;

public class MessageConstant {

    /* SUCCESS */


    /* ERROR */
    public static final String ERROR_NOT_LOGIN = "Unusual activities detected, please login.";
    public static final String ERROR_UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected, redirected to login page.";
    public static final String ERROR_FIELD_EMPTY = "Please fill in all the fields.";
    public static final String ERROR_USERNAME_INCORRECT = "Username/Email invalid, please try again.";
    public static final String ERROR_PASSWORD_INCORRECT = "Password invalid, please try again.";
    public static final String ERROR_ACCOUNT_DEACTIVATED = "Account has been deactivated, please contact administrator";
    public static final String ERROR_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in object";
    public static final String ERROR_JSON_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in Json object";
    public static final String ERROR_OBJECT_FIELD_NOT_FOUND = "Field not found in object";
    public static final String ERROR_OBJECT_NOT_FOUND = "Object not found";
    public static final String ERROR_JSON_OBJECT_NOT_FOUND = "Json object not found";
    public static final String ERROR_INVALID_JSON_FORMAT_STRING = "String is not in JSON format";
    public static final String ERROR_INDEX_OUT_OF_BOUND = "Index out of bound";
    public static final String ERROR_JSON_NUM_VALUE_PARSER = "Value contains non-numeric characters: ";
    public static final String ERROR_JSON_STORE_DATA = "Unable to save data into text file";
    public static final String ERROR_EMAIL_FORMAT_INCORRECT = "Email format incorrect, Please Try Again";
    public static final String ERROR_PHONE_NUMBER_FORMAT_INCORRECT = "Phone number format incorrect, Please Try Again";
    public static final String ERROR_USERNAME_FORMAT_INCORRECT = "Username length must be in 4-16, Please Try Again";
    public static final String ERROR_PASSWORD_FORMAT_INCORRECT = "Password format incorrect, \nplease involved at least one special character, \nnumber and Capital Letter.";
    public static final String ERROR_DATE_FORMAT_INCORRECT = "Date format incorrect. Format: 'yyyy-MM-dd'";
    public static final String ERROR_POSTCODE_FORMAT_INCORRECT = "Postcode format incorrect. Please try again";
    public static final String ERROR_STATE_INCORRECT = "Entered state invalid. Please try again";
    public static final String ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT = "Entered Username/SecurityPhrase Incorrect, Please Try Again.";
    public static final String ERROR_PASSWORD_N_CPASSWORD_NOT_MATCH = "Entered Password & Confirm Password Not Match, Please Try Again.";

    /* WARNING */
    public static final String SUCCESS_RESET_PASSWORD = "Password Reset Successful.";
}
