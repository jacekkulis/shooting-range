package com.politechnika.shootingrange.constants;

/**
 * Created by Jacek on 30.10.2017.
 */

public class UrlConstants {
    private static final String ROOT_URL = "http://192.168.1.112/Shootingrange/";
    private static final String ROOT_DIR = "src/";


    //region event
    private static final String EVENT = "event/";
    private static final String INCOMING = "getIncoming.php";
    private static final String USER = "getUser.php";
    private static final String OUTDATED = "getOutdated.php";

    public static final String URL_GET_INCOMING_EVENTS = ROOT_URL + ROOT_DIR + EVENT + INCOMING;
    public static final String URL_GET_OUTDATED_EVENTS = ROOT_URL + ROOT_DIR + EVENT + OUTDATED;
    public static final String URL_GET_USER_EVENTS = ROOT_URL + ROOT_DIR + EVENT + USER;
    //endregion

    public static final String URL_THUMBNAIL = ROOT_URL + ROOT_DIR + "eventImages/";
    public static final String URL_LOGIN = ROOT_URL + ROOT_DIR + "signin.php";
    public static final String URL_REGISTER = ROOT_URL + ROOT_DIR + "signup.php";
    public static final String URL_GETCLUBLIST = ROOT_URL + ROOT_DIR + "getClubList.php";
    public static final String URL_EDIT_PROFILE = ROOT_URL + ROOT_DIR + "editProfile.php";

    public static final String URL_REGISTER_TO_EVENT = ROOT_URL + ROOT_DIR + "registerUserToEvent.php";
    public static final String URL_UNREGISTER_FROM_EVENT = ROOT_URL + ROOT_DIR + "unregisterUserFromEvent.php";




}
