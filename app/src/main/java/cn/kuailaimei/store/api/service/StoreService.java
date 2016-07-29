package cn.kuailaimei.store.api.service;

import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kuailaimei.store.api.NetConstants;
import cn.kuailaimei.store.api.entity.Message;
import cn.kuailaimei.store.api.entity.RoleItem;
import cn.kuailaimei.store.api.request.AddOrEditEmpInfoRequest;
import cn.kuailaimei.store.api.request.DateRequest;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public interface StoreService {
    /**
     * 修改我的资料
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.UpdateProfileRequest
     */
    @POST(NetConstants.USERS_UPDATEUSERINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> updateProfile(@Body Request body);
    /**
     * 意见反馈
     *
     * @param body
     * @return
     * @see {@link cn.kuailaimei.store.api.request.Feedback }
     */
    @POST(NetConstants.FEEDBACK)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> feedback(@Body Request body);

    /**
     * 商家获取我的店铺信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.STORE_STOREINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreInfoResponse>> getStoreInfo(@Body Request body);

    /**
     * 修改我的店铺信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.ModifyStoreInfoRequest
     */
    @POST(NetConstants.STORE_UPDATESTORE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> modifyStoreInfo(@Body Request body);

    /**
     * 获取我的银行卡信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.USERS_USERBANK)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<BankInfoResponse>> getBankInfo(@Body Request body);

    /**
     * 绑定银行卡信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.BindingCardRequest
     */
    @POST(NetConstants.USERS_BINDINGBANK)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> bindingCard(@Body Request body);

    /**
     * 密码验证
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PassWordRequest
     */
    @POST(NetConstants.USERS_PASSWORDVERIFY)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> checkPsd(@Body Request body);

    /**
     * 获取店铺员工工种列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    @POST(NetConstants.STORE_EMPLOYEE_ROLELIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ArrayList<RoleItem>>> getRoleList(@Body Request body);

    /**
     * 我的员工信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    @POST(NetConstants.STORE_EMPLOYEE_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<EmpInfoResponse>> getEmpInfo(@Body Request body);

    /**
     * 添加员工信息
     *
     * @param body
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    @POST(NetConstants.STORE_EMPLOYEE_ADDEMPLOYEE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> addEmpInfo(@Body Request body);

    /**
     * 编辑员工信息
     *
     * @param body
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    @POST(NetConstants.STORE_EMPLOYEE_EDITEMPLOYEE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> editEmpInfo(@Body Request body);

    /**
     * 删除员工信息
     *
     * @param body
     * @return
     * @see AddOrEditEmpInfoRequest
     */
    @POST(NetConstants.STORE_EMPLOYEE_DELETEEMPLOYEE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> deleteEmpInfo(@Body Request body);

    /**
     * 获取店铺平台已开通的服务列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    @POST(NetConstants.STORE_STORESERVICES)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreServicesResponse>> getStoreServices(@Body Request body);

    /**
     * 获取本店铺已发布的项目列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    @POST(NetConstants.STORE_STORESERVICELIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ConsumeItemResponse>> getConsumeItems(@Body Request body);

    /**
     * 获取本店铺已发布的项目详情
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    @POST(NetConstants.STORE_SERVICEINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<ConsumeItemDetailResponse>> getConsumeItemDetail(@Body Request body);

    /**
     * 修改或发布项目
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.ModifyOrPostServiceRequest
     */
    @POST(NetConstants.STORE_SERVICEADDORUPDATE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> modifyOrPostService(@Body Request body);

    /**
     * 删除项目
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    @POST(NetConstants.STORE_DELETESERVICE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> deleteService(@Body Request body);

    /**
     * 上传店铺经纬度
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.UpdateStorePositionRequest
     */
    @POST(NetConstants.STORE_UPDATESTOREPOSITION)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> updateStorePosition(@Body Request body);

    /**
     * 我的店铺业绩统计
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.STORE_TOTAL_STORETOTAL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreTotalResponse>> getStoreTotal(@Body Request body);

    /**
     * 我的店铺现金业绩明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.STORE_TOTAL_CASHAMOUNTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AmountListResponse>> getCashAmountList(@Body Request body);

    /**
     * 我的店铺现消卡业绩明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.STORE_TOTAL_XKAMOUNTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AmountListResponse>> getXKAmountList(@Body Request body);

    /**
     * 我的店铺客单数统计明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.STORE_TOTAL_ORDERCOUNTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<TotalOrderCountListResponse>> getTotalCountOrderList(@Body Request body);

    /**
     * 我的合伙人统计
     *
     * @param body
     * @return
     * @see Request
     */
    @POST(NetConstants.STORE_TOTAL_PARTNERTOTAL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PartnerTotalResponse>> getPartnerTotal(@Body Request body);

    /**
     * 我的合伙人列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.LevelRequest
     */
    @POST(NetConstants.STORE_TOTAL_PARTNERLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<PartnerListResponse>> getPartnerList(@Body Request body);

    /**
     * 老板查看我的店铺订单
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.StatusRequest
     */
    @POST(NetConstants.STORE_TOTAL_BOSSORDERLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderListResponse>> getOrderList(@Body Request body);

    /**
     * 老板查看店铺评论列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.PageRequest
     */
    @POST(NetConstants.STORE_TOTAL_BOSSEVALUATELIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<CommentListResponse>> getCommentList(@Body Request body);

    /**
     * 我的店铺业务结算账户信息
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.STORE_STATEMENT_ACCOUNTINFO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AccountInfoResponse>> getAccountInfo(@Body Request body);

    /**
     * 申请提现
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.MoneyRequest
     */
    @POST(NetConstants.STORE_STATEMENT_ENCHASHMENT)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> takeCash(@Body Request body);

    /**
     * 交易流水
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.MoneyRequest
     */
    @POST(NetConstants.STORE_STATEMENT_TRANSLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<TransListResponse>> getTransList(@Body Request body);

    /**
     * 消息接口
     *
     * @param body
     * @return
     */
    @POST(NetConstants.MSG_LIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<Message>>> getMessages(@Body Request body);

    /**
     * 清空消息接口
     *
     * @param body
     * @return
     */
    @POST(NetConstants.MSG_CLEAN)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> clearMessages(@Body Request body);

//-------------------------文件上传------------------------------

    /**
     * 通过 Map对象 传入多个RequestBody实现多文件上传
     *
     * @param params 每个RequestBody封装一个文件
     * @return 状态信息
     */
    @Multipart
    @POST(NetConstants.UPLOAD)
    Observable<FileUploadResponse<FilePathResponse>> upload(@Query("type") String type,@PartMap Map<String, RequestBody> params);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     *
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST(NetConstants.UPLOAD)
    Observable<FileUploadResponse<FilePathResponse>> upload(@Query("type") String type, @Body MultipartBody multipartBody);

}
