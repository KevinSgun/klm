package cn.kuailaimei.store;

/**
 * Created by lu on 2016/6/16.
 */
public class Constants {

    public static final int VALIDATE_CODE_LENGTH = 6;

    public static final String BAI_DU_MAP = "baidumap";
    public static final String BAI_DU_SPLIT = "@";
    public static final String APP_ID = "";
    public static final int TYPE_BOSS = 1;
    public static final int TYPE_EMPLOYEE = 0;
    public static String IS_BINDING_JPUSH_ID = "is_binding_jpush";

    public interface ActivityExtra{

        String CHECK_POSITION = "check_position";

        String LOGIN_ABOUT = "login_about";
        String LOGIN_TYPE = "login_type";

        String SCORE_COUNT = "score_count";

        String SID ="sid";

        String TITLE ="title";
        String ID = "id";
        String STATUS = "status";

        String ORDER_ID = "order_id";
        String ORDER = "order";

        String MANAGER_NAME = "manager_name";
        String IS_NEED_AREA = "is_need_area";
        int SELECT_CITY_NAME = 0x11;
        int SELECT_AREA_NAME = 0x12;
        int SELECT_PROVINCE_NAME = 0X13;
        String HINT_CONTENT = "hint_content";
        String CONTENT = "content";
        String EDIT_DATA = "edit_data";
    }
    public interface FragmentExtra{

    }

    public interface ImageDefault {

//        int RECTANGLE_LANDSCAPE = R.drawable.;
    }
}
