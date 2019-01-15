package com.example.first_task_k__r__o__s__h.Authorization;

public class AuthorizationErrors {


    public enum TypeOfAuthManagerError{
        SERVER_ERROR("Server error"),
        USER_CHECK_ERROR("Not authorized."),
        WRONG_CREDENTIALS("Wrong credentials"),
        SUCH_EMAIL_ALREADY_REGISTERED("Such email already registered."),
        ACCESS_DENIED("Access denied"),
        EMAIL_NOT_SEND("Error! Email not send"),
        SMS_NOT_SEND("Error! SMS not send"),
        EMAIL_NOT_CONFIRM("Email not confirmed"),
        PHONE_NOT_CONFIRM("Phone not confirmed"),
        CHECK_EMAIL("Check your email"),
        CHECK_PHONE("Check your phone");

        private String description;

        TypeOfAuthManagerError(String description) {
            this.description = description;
        }

        public String getDescription() {return description;}
    }


    static TypeOfAuthManagerError convertError(String str){
        if (TypeOfAuthManagerError.ACCESS_DENIED.getDescription().equals(str))  return TypeOfAuthManagerError.ACCESS_DENIED;
        if (TypeOfAuthManagerError.SERVER_ERROR.getDescription().equals(str))  return TypeOfAuthManagerError.SERVER_ERROR;
        if (TypeOfAuthManagerError.SUCH_EMAIL_ALREADY_REGISTERED.getDescription().equals(str))  return TypeOfAuthManagerError.SUCH_EMAIL_ALREADY_REGISTERED;
        if (TypeOfAuthManagerError.USER_CHECK_ERROR.getDescription().equals(str))  return TypeOfAuthManagerError.USER_CHECK_ERROR;
        if (TypeOfAuthManagerError.WRONG_CREDENTIALS.getDescription().equals(str))  return TypeOfAuthManagerError.WRONG_CREDENTIALS;
        return null;
    }

}
