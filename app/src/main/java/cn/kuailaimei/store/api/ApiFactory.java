package cn.kuailaimei.store.api;

import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kuailaimei.store.api.entity.ImageWorks;
import cn.kuailaimei.store.api.entity.Message;
import cn.kuailaimei.store.api.entity.RoleItem;
import cn.kuailaimei.store.api.request.AddOrEditEmpInfoRequest;
import cn.kuailaimei.store.api.request.DateRequest;
import cn.kuailaimei.store.api.request.PushRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.AccountInfoResponse;
import cn.kuailaimei.store.api.response.AmountListResponse;
import cn.kuailaimei.store.api.response.BankInfoResponse;
import cn.kuailaimei.store.api.response.CommentListResponse;
import cn.kuailaimei.store.api.response.ConsumeItemDetailResponse;
import cn.kuailaimei.store.api.response.ConsumeItemResponse;
import cn.kuailaimei.store.api.response.EmpInfoResponse;
import cn.kuailaimei.store.api.response.FilePathResponse;
import cn.kuailaimei.store.api.response.OrderListResponse;
import cn.kuailaimei.store.api.response.PartnerListResponse;
import cn.kuailaimei.store.api.response.PartnerTotalResponse;
import cn.kuailaimei.store.api.response.StoreInfoResponse;
import cn.kuailaimei.store.api.response.StoreServicesResponse;
import cn.kuailaimei.store.api.response.StoreTotalResponse;
import cn.kuailaimei.store.api.response.TotalOrderCountListResponse;
import cn.kuailaimei.store.api.response.TransListResponse;
import cn.kuailaimei.store.api.response.UserInfoResponse;
import cn.kuailaimei.store.api.service.AccountService;
import cn.kuailaimei.store.api.service.EmpService;
import cn.kuailaimei.store.api.service.StoreService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;


/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {

    private static Map<String, Object> mCache = new HashMap();

    private static AccountService getAccountService() {
        return getService(AccountService.class);
    }

    private static StoreService getStoreService() {
        return getService(StoreService.class);
    }

    private static EmpService getEmpService(){
        return getService(EmpService.class);
    }

    public static <T> T getService(Class cls) {
        String key = cls.getSimpleName();
        Object target = mCache.get(key);
        if (target == null) {
            target = RetrofitClient.getInstance().create(cls);
            mCache.put(key, target);
        }
        return (T) target;
    }

    /**
     * 修改我的资料
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.UpdateProfileRequest
     */
    public static Observable<ApiResponse> updateProfile(Request request) {
        return getStoreService().updateProfile(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public static Observable<FileUploadResponse<FilePathResponse>> upload(String type, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                //添加文件参数
                .addFormDataPart("files", file.getName(), fileBody)
                //添加Form参数
                .addFormDataPart("id", "type")//
                .addFormDataPart("name", "type")
                .addFormDataPart("value", "1")
                .build();
        return getStoreService().upload(type, multipartBody).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 文件上传
     *
     * @param fileList
     * @return
     */
    public static Observable<FileUploadResponse<FilePathResponse>> upload(String type,List<File> fileList) {
        Map<String,RequestBody> fileParams = new HashMap<>();
        for(File file:fileList){
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            fileParams.put("files\"; fileName=\""+System.currentTimeMillis()+file.getName(),fileBody);
        }
        fileParams.put("id",  RequestBody.create(null, "type"));
        fileParams.put("name",  RequestBody.create(null, "type"));
        fileParams.put("value",  RequestBody.create(null, "1"));
        return getStoreService().upload(type,fileParams).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.VerifyCodeRequest
     */
    public static Observable<ApiResponse> getVerifyCode(Request request) {
        return getAccountService().getVerifyCode(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 用户登录
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.LoginRequest
     */
    public static Observable<ApiResponse<UserInfoResponse>> requestLogin(Request request) {
        return getAccountService().requestLogin(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 重置密码
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.ResetPsdRequest
     */
    public static Observable<ApiResponse> requestResetPsd(Request request) {
        return getAccountService().requestResetPsd(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.ModifyPsdRequest
     */
    public static Observable<ApiResponse> requestModifyPsd(Request request) {
        return getAccountService().requestModifyPsd(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 意见反馈
     *
     * @param request
     * @return
     */
    public static Observable<ApiResponse> feedback(Request request) {
        return getStoreService().feedback(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }


    /**
     * 商家获取我的店铺信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<StoreInfoResponse>> getStoreInfo(Request request) {
        return getStoreService().getStoreInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 修改我的店铺信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.ModifyStoreInfoRequest
     */
    public static Observable<ApiResponse> modifyStoreInfo(Request request) {
        return getStoreService().modifyStoreInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取我的银行卡信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<BankInfoResponse>> getBankInfo(Request request) {
        return getStoreService().getBankInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 绑定银行卡信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.BindingCardRequest
     */
    public static Observable<ApiResponse> bindingCard(Request request) {
        return getStoreService().bindingCard(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 密码验证
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PassWordRequest
     */
    public static Observable<ApiResponse> checkPsd(Request request) {
        return getStoreService().checkPsd(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取店铺员工工种列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    public static Observable<ApiResponse<ArrayList<RoleItem>>> getRoleList(Request request) {
        return getStoreService().getRoleList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的员工信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    public static Observable<ApiResponse<EmpInfoResponse>> getEmpInfo(Request request) {
        return getStoreService().getEmpInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 添加员工信息
     *
     * @param request
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    public static Observable<ApiResponse> addEmpInfo(Request request) {
        return getStoreService().addEmpInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 编辑员工信息
     *
     * @param request
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    public static Observable<ApiResponse> editEmpInfo(Request request) {
        return getStoreService().editEmpInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 删除员工信息
     *
     * @param request
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    public static Observable<ApiResponse> deleteEmpInfo(Request request) {
        return getStoreService().deleteEmpInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取店铺平台已开通的服务列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    public static Observable<ApiResponse<StoreServicesResponse>> getStoreServices(Request request) {
        return getStoreService().getStoreServices(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取本店铺已发布的项目列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    public static Observable<ApiResponse<ConsumeItemResponse>> getConsumeItems(Request request) {
        return getStoreService().getConsumeItems(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取本店铺已发布的项目详情
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    public static Observable<ApiResponse<ConsumeItemDetailResponse>> getConsumeItemDetail(Request request) {
        return getStoreService().getConsumeItemDetail(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 修改或发布项目
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.ModifyOrPostServiceRequest
     */
    public static Observable<ApiResponse> modifyOrPostService(Request request) {
        return getStoreService().modifyOrPostService(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 删除项目
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    public static Observable<ApiResponse> deleteService(Request request) {
        return getStoreService().deleteService(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 上传店铺经纬度
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.UpdateStorePositionRequest
     */
    public static Observable<ApiResponse> updateStorePosition(Request request) {
        return getStoreService().updateStorePosition(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的店铺业绩统计
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<StoreTotalResponse>> getStoreTotal(Request request) {
        return getStoreService().getStoreTotal(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的店铺现金业绩明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<AmountListResponse>> getCashAmountList(Request request) {
        return getStoreService().getCashAmountList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的店铺现消卡业绩明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<AmountListResponse>> getXKAmountList(Request request) {
        return getStoreService().getXKAmountList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的店铺客单数统计明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<TotalOrderCountListResponse>> getTotalCountOrderList(Request request) {
        return getStoreService().getTotalCountOrderList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的合伙人统计
     *
     * @param request
     * @return
     * @see Request
     */
    public static Observable<ApiResponse<PartnerTotalResponse>> getPartnerTotal(Request request) {
        return getStoreService().getPartnerTotal(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的合伙人列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.LevelRequest
     */
    public static Observable<ApiResponse<PartnerListResponse>> getPartnerList(Request request) {
        return getStoreService().getPartnerList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 老板查看我的店铺订单
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.StatusRequest
     */
    public static Observable<ApiResponse<OrderListResponse>> getOrderList(Request request) {
        return getStoreService().getOrderList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 老板查看店铺评论列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    public static Observable<ApiResponse<CommentListResponse>> getCommentList(Request request) {
        return getStoreService().getCommentList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 我的店铺业务结算账户信息
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<AccountInfoResponse>> getAccountInfo(Request request) {
        return getStoreService().getAccountInfo(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 申请提现
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.MoneyRequest
     */
    public static Observable<ApiResponse> takeCash(Request request) {
        return getStoreService().takeCash(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 交易流水
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.DateRequest
     */
    public static Observable<ApiResponse<TransListResponse>> getTransList(Request request) {
        return getStoreService().getTransList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师订单列表
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.StatusRequest
     */
    public static Observable<ApiResponse<OrderListResponse>> getEmpOrderList(Request request) {
        return getEmpService().getEmpOrderList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师确认订单(即结束服务)
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.OrderIdRequest
     */
    public static Observable<ApiResponse> confirmEmpOrder(Request request) {
        return getEmpService().confirmEmpOrder(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师我的业绩统计
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<StoreTotalResponse>> getEmpTotal(Request request) {
        return getEmpService().getEmpTotal(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师我的现金业绩明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<AmountListResponse>> getEmpCashAmountList(Request request) {
        return getEmpService().getEmpCashAmountList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师我的现消卡业绩明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<AmountListResponse>> getEmpXKAmountList(Request request) {
        return getEmpService().getEmpXKAmountList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 技师我的订单业绩明细
     *
     * @param request
     * @return
     * @see DateRequest
     */
    public static Observable<ApiResponse<AmountListResponse>> getEmpTotalCountOrderList(Request request) {
        return getEmpService().getEmpTotalCountOrderList(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 上传我的作品
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.ImagesRequest
     */
    public static Observable<ApiResponse> uploadMyWorks(Request request) {
        return getEmpService().uploadMyWorks(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 获取我的作品
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<List<ImageWorks>>> getMyWorks(Request request) {
        return getEmpService().getMyWorks(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 上传极光推送ID
     *
     * @param request
     * @return
     * @see PushRequest
     */
    public static Observable<ApiResponse> uploadJPushId(Request request) {
        return getEmpService().uploadJPushId(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 推送极光消息
     *
     * @param request
     * @return
     * @see PushRequest
     */
    public static Observable<ApiResponse> pushMsg(Request request) {
        return getEmpService().pushMsg(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 删除我的作品
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    public static Observable<ApiResponse> deleteMyWorks(Request request) {
        return getEmpService().deleteMyWorks(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 消息接口
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse<List<Message>>> getMessages(Request request) {
        return getStoreService().getMessages(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

    /**
     * 清空消息接口
     *
     * @param request
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    public static Observable<ApiResponse> clearMessages(Request request) {
        return getStoreService().clearMessages(request).map(new HttpResultFunc()).compose(SchedulersCompat.applyIoSchedulers());
    }

}
