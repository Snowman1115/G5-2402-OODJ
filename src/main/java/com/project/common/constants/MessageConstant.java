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
    public static final String SUCCESS_BOOKING_CONSULTATION = "Consultation Successfully Booked.";
    public static final String SUCCESS_CONSULTATION_CREATED = "Consultation Successfully Created.";
    public static final String SUCCESS_CONSULTATION_CANCELLED = "Consultation Successfully Cancelled.";
    public static final String SUCCESS_CONSULTATION_COMPLETED = "Consultation Successfully Update To Completed.";
    public static final String SUCCESS_BOOKED_PRESENTATION_SLOT = "Presentation Slot Successfully Booked.";
    public static final String SUCCESS_CONFIRMED_PRESENTATION_SLOT = "Presentation Slot Successfully Confirmed.";
    public static final String SUCCESS_CANCELLED_PRESENTATION_SLOT = "Presentation Slot Successfully Cancelled.";
    public static final String SUCCESS_REJECTED_PRESENTATION_SLOT = "Presentation Slot Successfully Rejected.";
    public static final String SUCCESS_UPDATED_PRESENTATION_MARKS = "Presentation Marks Successfully Updated.";
    public static final String SUCCESS_UPDATED_SUBMISSION_MARKS = "Submission Marks Successfully Updated.";
    public static final String SUCCESS_SUBMITTED_PROJECT = "Project File Submitted Successfully.";
    public static final String SUCCESS_ASSIGNED_SUPERVISOR = "Supervisor And Second Marker Assigned Successfully.";
    public static final String SUCCESS_RESET_FIELD = "Field has been reset to default.";
    public static final String SUCCESS_UPDATE_ASSESSMENT_TYPE = "Assessment Type Has Been Updated Successfully.";
     public static final String SUCCESS_UPDATE_DATE = "The module date, submission due date, and presentation due date UPDATED SUCCESSFULLY.";
    public static final String SUCCESS_SUBMIT_FEEDBACK = "Module Feedback Submitted Successfully.";
    public static final String SUCCESS_GENERATE_PDF = "Student Feedback PDF has been generated successfully.";

    /* ERROR */
    public static final String ERROR_NOT_LOGIN = "Unusual activity detected. Please log in.";
    public static final String ERROR_UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected. Redirected to login page.";
    public static final String ERROR_FIELD_EMPTY = "Please fill in all fields.";
    public static final String ERROR_USERNAME_INCORRECT = "Invalid username/email. Please try again.";
    public static final String ERROR_PASSWORD_INCORRECT = "Invalid password. Please try again.";
    public static final String ERROR_ACCOUNT_DEACTIVATED = "Account deactivated. Please contact the administrator.";
    public static final String ERROR_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in the object.";
    public static final String ERROR_JSON_ATTRIBUTE_NOT_FOUND = "Attribute does not exist in the JSON object.";
    public static final String ERROR_OBJECT_FIELD_NOT_FOUND = "Field not found in the object.";
    public static final String ERROR_OBJECT_NOT_FOUND = "Object not found.";
    public static final String ERROR_JSON_OBJECT_NOT_FOUND = "JSON object not found.";
    public static final String ERROR_INVALID_JSON_FORMAT_STRING = "String is not in JSON format.";
    public static final String ERROR_INDEX_OUT_OF_BOUND = "Index out of bounds.";
    public static final String ERROR_JSON_NUM_VALUE_PARSER = "Value contains non-numeric characters: ";
    public static final String ERROR_JSON_STORE_DATA = "Unable to save data into a text file.";
    public static final String ERROR_EMAIL_FORMAT_INCORRECT = "Incorrect email format. Please try again.";
    public static final String ERROR_PHONE_NUMBER_FORMAT_INCORRECT = "Incorrect phone number format. Please try again.";
    public static final String ERROR_USERNAME_FORMAT_INCORRECT = "Username length must be 4-16 characters.";
    public static final String ERROR_PASSWORD_FORMAT_INCORRECT = "Password format is incorrect. Please include at least one special character, number, and capital letter.";
    public static final String ERROR_DATE_FORMAT_INCORRECT = "Incorrect date format. The format should be 'yyyy-MM-dd'.";
    public static final String ERROR_POSTCODE_FORMAT_INCORRECT = "Incorrect postcode format. Please try again.";
    public static final String ERROR_MARKS_FORMAT_INCORRECT = "Incorrect marks format. Please try again and enter numbers only.";
    public static final String ERROR_MARKS_EXCEED_RANGE = "Marks must be between 0 and 100. Please enter again.";
    public static final String ERROR_STATE_INCORRECT = "Invalid state. Please try again.";
    public static final String ERROR_SECURITY_PHRASE_N_ACCOUNT_INCORRECT = "Incorrect username/security phrase. Please try again.";
    public static final String ERROR_PASSWORD_N_CPASSWORD_NOT_MATCH = "Passwords do not match. Please try again.";
    public static final String ERROR_USER_NOT_FOUND = "User not found. Please try again.";
    public static final String ERROR_USERNAME_ALREADY_EXIST = "Username already exists. Please try another one.";
    public static final String ERROR_EMAIL_ALREADY_EXIST = "Email already exists. Please try another one.";
    public static final String UNEXPECTED_ERROR = "Update failed unexpectedly. Please contact the admin.";
    public static final String ERROR_SECURITY_PHRASE_INCORRECT = "Incorrect security phrase. Please try again.";
    public static final String ERROR_SELECTION_EMPTY = "Selection is empty. Please try again.";
    public static final String ERROR_PAST_DATE_SELECTION = "Past date selection is not allowed.";
    public static final String ERROR_OVER_DATE_SELECTION = "The selected date exceeds the module due date.";
    public static final String ERROR_PRESENTATION_SLOT_BOOKED = "The presentation slot is already booked.";
    public static final String ERROR_ONLY_PDF_SUPPORTED = "Only PDF format is supported. Please try again.";
    public static final String ERROR_EMPTY_FILE = "Please upload a file to submit.";
    public static final String ERROR_NO_SUBMISSION_SELECTED = "Please select a submission to be marked.";
    public static final String ERROR_NO_MODULES_SELECTED = "Please select a module and fetch all the submissions first.";
    public static final String ERROR_SUBMISSION_DUE_DATE_IS_OVER = "Submission Due Date is Over, modification are not allowed.";
    public static final String ERROR_CREATE_CONSULTATION_FAILED = "Failed to create a new consultation. Please try again";
    public static final String ERROR_CONSULTATION_DATETIME_CLASHED = """
                                                                     The DateTime selected is clashing with other existing consultation DateTime.
                                                                     Please select another DateTime.
                                                                     """;
    public static final String ERROR_PRESENTATION_DATETIME_CLASHED = """
                                                                     The DateTime requested is clashing with other booked presentation DateTime. 
                                                                     Please contact your student to choose another DateTime and this presentation is automatically rejected.
                                                                     """;
    public static final String ERROR_EMPTY_MODULE = "No Module Found! Please select a module first.";
    public static final String ERROR_DATE_INVALID = "The start date must be earlier than the end date.";
    public static final String ERROR_NO_DATETIME_SELECTED = "Please select a date & time.";
    public static final String ERROR_EMPTY_FEEDBACK = "No Feedback Found! Please select a feedback first.";

    /* CONDITION */
    public static final String CONDITION_PRESENTATION_COMBOBOX = "No presentations are available to book.";
    public static final String CONDITION_EDIT_PRESENTATION_COMBOBOX = "No presentations are available to edit.";
    public static final String CONDITION_REJECT_PRESENTATION_COMBOBOX = "No rejected presentations to reschedule.";
    public static final String CONDITION_PRESENTATION_RESULT_COMBOBOX = "No marked presentation results to view.";

    public static final String CONDITION_PROJECT_COMBOBOX = "No projects are available to submit.";
    public static final String CONDITION_EDIT_PROJECT_COMBOBOX = "No projects are available to edit.";
    public static final String CONDITION_PROJECT_RESULT_COMBOBOX = "No marked project results to view.";
    public static final String CONDITION_NO_SUBMITTED_PROJECT_COMBOBOX = "No marked project results to view.";
    public static final String CONDITION_NO_MODULE_CAN_FEEDBACK = "No module can feedback.";
    public static final String CONDITION_NO_MODULES_UNDER_LECTURER = "No modules to show.";
    public static final String CONDITION_NO_DATA_CHANGES = "No data changes detected.";
    public static final String CONDITION_NO_SUBMISSIONS_UNDER_MODULES = "No submissions to show.";
    public static final String SAME_MARKER_SUPERVISOR = "The supervisor and the second marker could not be same";

    /* WARNING */
    public static final String WARNING_REMOVE_CONFIRMATION = "Are you sure you want to delete ?";
    public static final String WARNING_MARK_PRESENTATION = """
                                                           Are you sure you want to give the presentation marks?
                                                           Once updated, you are not allowed to modify the marks !
                                                           """;
    public static final String WARNING_MARK_REPORT = "Are you sure you want to give the report marks and commment?";
    public static final String WARNING_DEFAULT_SECURITY_PHRASE = "A default security phrase has been detected. Please change your security phrase immediately.";

}
