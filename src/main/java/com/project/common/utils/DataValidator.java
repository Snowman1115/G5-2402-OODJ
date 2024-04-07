package com.project.common.utils;

import com.project.common.constants.MessageConstant;

import java.util.List;

public class DataValidator {

    public static final String PATTERN_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PATTERN_PHONE = "^(01)[02-46-9]-[0-9]{7}$|^(01)[1]-[0-9]{8}$";
    public static final String PATTERN_USERNAME = "^[a-zA-Z0-9_-]{4,16}$";
    public static final String PATTERN_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public static final String PATTERN_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String PATTERN_POSTCODE = "^\\d{5}$";
    public static final List<String> STATE = List.of(
            "Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan",
            "Pahang", "Perak", "Perlis", "Pulau Pinang", "Sabah",
            "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur"
    );

    /**
     * Validate Email Address
     * @param email
     * @return boolean result
     */
    public static boolean validateEmail(String email) {
        if(!email.matches(PATTERN_EMAIL)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_EMAIL_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Phone Number
     * @param phoneNumber
     * @return boolean result
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches(PATTERN_PHONE)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PHONE_NUMBER_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Username
     * @param username
     * @return boolean result
     */
    public static boolean validateUsername(String username) {
        if (!username.matches(PATTERN_USERNAME)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_USERNAME_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Password
     * @param password
     * @return boolean result
     */
    public static boolean validatePassword(String password) {
        if (!password.matches(PATTERN_PASSWORD)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Date
     * @param date
     * @return boolean result
     */
    public static boolean validateDate(String date) {
        if (!date.matches(PATTERN_DATE)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_DATE_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Postcode
     * @param postalCode
     * @return boolean result
     */
    public static boolean validatePostalCode(String postalCode) {
        // Validate postal code (US format)
        if (!postalCode.matches(PATTERN_POSTCODE)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_POSTCODE_FORMAT_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate State
     * @param state
     * @return boolean result
     */
    public static boolean validateState(String state) {
        if (!STATE.contains(state)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_STATE_INCORRECT);
            return false;
        }
        return true;
    }

    /**
     * Validate Custom Pattern Data
     * @param data
     * @param pattern
     * @param errorMessage
     * @return boolean result
     */
    public static boolean validateCustomPattern(String data, String pattern, String errorMessage) {
        if (!data.matches(pattern)) {
            Dialog.ErrorDialog(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Validate Password N ConfirmPassword
     * @param password
     * @param confirmPassword
     * @return boolean result
     */
    public static boolean validatePasswordNConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            Dialog.ErrorDialog(MessageConstant.ERROR_PASSWORD_N_CPASSWORD_NOT_MATCH);
            return false;
        }
        return true;
    }

    /**
     * Validate Entered Data isEmpty
     * @param data
     * @return boolean result
     */
    public static boolean validateEmptyInput(String data) {
        if (data == null || data.trim().isEmpty()) {
            Dialog.ErrorDialog(MessageConstant.ERROR_FIELD_EMPTY);
            return false;
        }
        return true;
    }


}
