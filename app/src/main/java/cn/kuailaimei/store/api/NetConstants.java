package cn.kuailaimei.store.api;

import com.zitech.framework.data.network.Constants;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class NetConstants extends Constants {

    /**
     * 普通api后缀
     */
    public static final String COMMON_SUFFIX = "klm/api/";

    /**
     * 文件上传相关后缀
     */
    public static final String MANAGE_SUFFIX = "klm/manage/";

    /**************************************************************************/

    /**
     * 用户登陆
     */
    public static final String USERS_LOGIN = COMMON_SUFFIX + "users/login";

    /**
     * 获取验证码
     */

    public static final String USERS_CODE = COMMON_SUFFIX + "users/code";
    /**
     * 修改密码
     */
    public static final String USERS_UPDATEPASSWORD = COMMON_SUFFIX + "users/updatePassword";

    /**
     * 找回密码
     */
    public static final String USERS_FORGET = COMMON_SUFFIX + "users/forget";

    /**
     * 意见反馈
     */
    public static final String FEEDBACK = COMMON_SUFFIX + "feedback";

    /**
     * 上传文件
     */
    public static final String UPLOAD = MANAGE_SUFFIX + "upload";

    /**
     * 修改我的资料
     */
    public static final String USERS_UPDATEUSERINFO = COMMON_SUFFIX + "users/updateUserInfo";

    /**
     * 1.商家获取我的店铺信息
     */
    public static final String STORE_STOREINFO = COMMON_SUFFIX + "store/storeInfo";

    /**
     * 2.修改我的店铺信息
     */
    public static final String STORE_UPDATESTORE = COMMON_SUFFIX + "store/updateStore";

    /**
     * 3.获取我的银行卡信息
     */
    public static final String USERS_USERBANK = COMMON_SUFFIX + "users/userBank";

    /**
     * 4.绑定银行卡信息
     */
    public static final String USERS_BINDINGBANK = COMMON_SUFFIX + "users/bindingBank";

    /**
     * 5.密码验证
     */
    public static final String USERS_PASSWORDVERIFY = COMMON_SUFFIX + "users/passwordVerify";

    /**
     * 6.获取店铺员工工种列表
     */
    public static final String STORE_EMPLOYEE_ROLELIST = COMMON_SUFFIX + "store/employee/roleList";

    /**
     * 7.我的员工信息
     */
    public static final String STORE_EMPLOYEE_LIST = COMMON_SUFFIX + "store/employee/list";

    /**
     * 8.添加员工信息
     */
    public static final String STORE_EMPLOYEE_ADDEMPLOYEE = COMMON_SUFFIX + "store/employee/addEmployee";

    /**
     * 9.编辑员工信息
     */
    public static final String STORE_EMPLOYEE_EDITEMPLOYEE = COMMON_SUFFIX + "store/employee/editEmployee";

    /**
     * 10.删除员工信息
     */
    public static final String STORE_EMPLOYEE_DELETEEMPLOYEE = COMMON_SUFFIX + "store/employee/deleteEmployee";

    /**
     * 11.获取店铺平台已开通的服务列表
     */
    public static final String STORE_STORESERVICES = COMMON_SUFFIX + "store/storeServices";

    /**
     * 12.获取本店铺已发布的项目列表
     */
    public static final String STORE_STORESERVICELIST = COMMON_SUFFIX + "store/storeServiceList";

    /**
     * 13.获取本店铺已发布的项目详情
     */
    public static final String STORE_SERVICEINFO = COMMON_SUFFIX + "store/serviceInfo";

    /**
     * 14 获取修改或发布项目
     */
    public static final String STORE_SERVICEADDORUPDATE = COMMON_SUFFIX + "store/saveOrUpdateService";

    /**
     * 15 删除项目
     */
    public static final String STORE_DELETESERVICE = COMMON_SUFFIX + "store/deleteService";

    /**
     * 16.上传店铺经纬度
     */
    public static final String STORE_UPDATESTOREPOSITION = COMMON_SUFFIX + "store/updateStorePosition";

    /**
     * 17.我的店铺业绩统计
     */
    public static final String STORE_TOTAL_STORETOTAL = COMMON_SUFFIX + "store/total/storeTotal";

    /**
     * 18.我的店铺现金业绩明细
     */
    public static final String STORE_TOTAL_CASHAMOUNTLIST = COMMON_SUFFIX + "store/total/cashAmountList";

    /**
     * 19.我的店铺现消卡业绩明细
     */
    public static final String STORE_TOTAL_XKAMOUNTLIST = COMMON_SUFFIX + "store/total/xkAmountList";

    /**
     * 20.我的店铺客单数统计明细
     */
    public static final String STORE_TOTAL_ORDERCOUNTLIST = COMMON_SUFFIX + "store/total/orderCountList";

    /**
     * 21.我的合伙人统计
     */
    public static final String STORE_TOTAL_PARTNERTOTAL = COMMON_SUFFIX + "store/total/partnerTotal";

    /**
     * 22.我的合伙人列表
     */
    public static final String STORE_TOTAL_PARTNERLIST = COMMON_SUFFIX + "store/total/partnerList";

    /**
     * 23.老板查看我的店铺订单
     */
    public static final String STORE_TOTAL_BOSSORDERLIST = COMMON_SUFFIX + "store/total/bossOrderlist";

    /**
     * 24.老板查看店铺评论列表
     */
    public static final String STORE_TOTAL_BOSSEVALUATELIST = COMMON_SUFFIX + "store/total/bossEvaluateList";

    /**
     * 25.我的店铺业务结算账户信息
     */
    public static final String STORE_STATEMENT_ACCOUNTINFO = COMMON_SUFFIX + "store/statement/accountInfo";

    /**
     * 26.申请提现
     */
    public static final String STORE_STATEMENT_ENCHASHMENT = COMMON_SUFFIX + "store/statement/enchashment";

    /**
     * 27.交易流水
     */
    public static final String STORE_STATEMENT_TRANSLIST = COMMON_SUFFIX + "store/statement/transList";

    /**
     * 28.技师订单列表
     */
    public static final String STORE_TOTAL_DESIGNERORDERLIST = COMMON_SUFFIX + "store/total/designerOrderlist";

    /**
     * 29.技师确认订单
     */
    public static final String STORE_TOTAL_ORDERCONFIRM = COMMON_SUFFIX + "store/total/orderConfirm";

    /**
     * 30.技师我的业绩统计
     */
    public static final String USERS_TOTAL_USERTOTAL = COMMON_SUFFIX + "users/total/userTotal";

    /**
     * 31.技师我的现金业绩明细
     */
    public static final String USERS_TOTAL_CASHAMOUNTLIST = COMMON_SUFFIX + "users/total/cashAmountList";

    /**
     * 32.技师我的现消卡业绩明细
     */
    public static final String USERS_TOTAL_XKAMOUNTLIST = COMMON_SUFFIX + "users/total/xkAmountList";

    /**
     * 32.技师我的订单业绩明细
     */
    public static final String USERS_TOTAL_ORDERLIST = COMMON_SUFFIX + "users/total/orderList";


    /**
     * 34.上传我的作品
     */
    public static final String USERS_UPLOADPHOTOS = COMMON_SUFFIX + "users/upLoadPhotos";

    /**
     * 35.获取我的作品
     */
    public static final String USERS_GETPHOTOS = COMMON_SUFFIX + "users/getPhotos";

    /**
     * 36.上传极光推送ID
     */
    public static final String HOME_UPLOADPUSHID = COMMON_SUFFIX + "home/uploadPushId";

    /**
     * 37.推送极光消息
     */
    public static final String MSG_PUSHMESSAGE = COMMON_SUFFIX + "msg/pushMessage";

    /**
     * 38.删除我的作品
     */
    public static final String USERS_DELETEPHOTO = COMMON_SUFFIX + "users/deletePhoto";

    /**
     * 消息接口
     */
    public static final String MSG_LIST = COMMON_SUFFIX+"msg/list";

    /**
     * 清空消息接口
     */
    public static final String MSG_CLEAN = COMMON_SUFFIX+"msg/clean";
}
