package com.project.common.constants;

/**
 * Messages Constants
 *
 * @author CHAN HOONG JIAN
 * @version 1.0, Last edited on 2024-03-29
 * @since 2024-03-29
 */
public class MessageConstant {

    /* SUCCESS */
    public static final String SUCCESS_RESET_PASSWORD = "Password Reset Successful.";
    public static final String SUCCESS_PROFILE_UPDATE_SUCCESSFUL = "Profile Update Successful.";
    public static final String SUCCESS_PASSWORD_UPDATE_SUCCESSFUL = "Password Update Successful.";
    public static final String SUCCESS_SECURITY_PHRASE_UPDATE_SUCCESSFUL = "Security Phrase Update Successful.";
    public static final String SUCCESS_BOOKING_CONSULTATION = "Consultation Booked Successfully.";
    public static final String SUCCESS_CONSULTATION_CANCELLED = "Consultation Cancelled Successfully.";
    public static final String SUCCESS_BOOKED_PRESENTATION_SLOT = "Presentation Slot Booked Successfully.";
    public static final String SUCCESS_CANCELLED_PRESENTATION_SLOT = "Presentation Slot Cancelled Successfully.";

    /* ERROR */
    public static final String ERROR_NOT_LOGIN = "Unusual activity detected. Please log in.";
    public static final String ERROR_UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected. Redirected to login page.";
    public static final String ERROR_FIELD_EMPTY = "Please fill in all fields.";
    public static final String ERROR_USERNAME_INCORRECT = "Invalid username/email. Please try again.";
    public static final String ERROR_PASSWORD_INCORRECT = "Invalid password. Please try again.";
    public static final String ERROR_ACCOUNT_DEACTIVATED = "Account deactivated. Please contact administrator.";
    public static final String ERROR_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in object.";
    public static final String ERROR_JSON_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in JSON object.";
    public static final String ERROR_OBJECT_FIELD_NOT_FOUND = "Field not found in object.";
    public static final String ERROR_OBJECT_NOT_FOUND = "Object not found.";
    public static final String ERROR_JSON_OBJECT_NOT_FOUND = "JSON object not found.";
    public static final String ERROR_INVALID_JSON_FORMAT_STRING = "String is not in JSON format.";
    public static final String ERROR_INDEX_OUT_OF_BOUND = "Index out of bounds.";
    public static final String ERROR_JSON_NUM_VALUE_PARSER = "Value contains non-numeric characters: ";
    public static final String ERROR_JSON_STORE_DATA = "Unable to save data into text file.";
    public static final String ERROR_EMAIL_FORMAT_INCORRECT = "Incorrect email format. Please try again.";
    public static final String ERROR_PHONE_NUMBER_FORMAT_INCORRECT = "Incorrect phone number format. Please try again.";
    public static final String ERROR_USERNAME_FORMAT_INCORRECT = "Username length must be 4-16 characters.";
    public static final String ERROR_PASSWORD_FORMAT_INCORRECT = "Password format incorrect. Include at least one special character, number, and capital letter.";
    public static final String ERROR_DATE_FORMAT_INCORRECT = "Incorrect date format. Format: 'yyyy-MM-dd'.";
    public static final String ERROR_POSTCODE_FORMAT_INCORRECT = "Incorrect postcode format. Please try again.";
    public static final String ERROR_STATE_INCORRECT = "Invalid state. Please try again.";
    public static final String ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT = "Incorrect username/security phrase. Please try again.";
    public static final String ERROR_PASSWORD_N_CPASSWORD_NOT_MATCH = "Passwords do not match. Please try again.";
    public static final String ERROR_USER_NOT_FOUND = "User not found. Please try again.";
    public static final String ERROR_USERNAME_ALREADY_EXIST = "Username already exists. Please try another one.";
    public static final String ERROR_EMAIL_ALREADY_EXIST = "Email already exists. Please try another one.";
    public static final String UNEXPECTED_ERROR = "Update failed unexpectedly. Please contact admin.";
    public static final String ERROR_SECURITY_PHRASE_INCORRECT = "Incorrect security phrase. Please try again.";
    public static final String ERROR_SELECTION_EMPTY = "Selection is empty. Please try again.";
    public static final String ERROR_PAST_DATE_SELECTION = "Past date selection not allowed.";
    public static final String ERROR_OVER_DATE_SELECTION = "Selected date exceeds module due date.";
    public static final String ERROR_PRESENTATION_SLOT_BOOKED = "Presentation slot already booked.";


    /* CONDITION */
    public static final String CONDITION_PRESENTATION_COMBOBOX = "No presentations available to book.";
    public static final String CONDITION_EDIT_PRESENTATION_COMBOBOX = "No presentations available to edit.";
    public static final String CONDITION_REJECT_PRESENTATION_COMBOBOX = "No rejected presentations to re-schedule.";
    public static final String CONDITION_PRESENTATION_RESULT_COMBOBOX = "No marked presentations result to view.";

    /* WARNING */

}
