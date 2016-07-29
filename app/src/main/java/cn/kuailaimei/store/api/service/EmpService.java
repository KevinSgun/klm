package cn.kuailaimei.store.api.service;

import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.store.api.NetConstants;
import cn.kuailaimei.store.api.entity.ImageWorks;
import cn.kuailaimei.store.api.request.DateRequest;
import cn.kuailaimei.store.api.request.PushRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.AmountListResponse;
import cn.kuailaimei.store.api.response.OrderListResponse;
import cn.kuailaimei.store.api.response.StoreTotalResponse;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ymh on 2016/7/20 0020.
 */
public interface EmpService {

    /**
     * 技师订单列表
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.StatusRequest
     */
    @POST(NetConstants.STORE_TOTAL_DESIGNERORDERLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<OrderListResponse>> getEmpOrderList(@Body Request body);

    /**
     * 技师确认订单
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.OrderIdRequest
     */
    @POST(NetConstants.STORE_TOTAL_ORDERCONFIRM)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> confirmEmpOrder(@Body Request body);

    /**
     * 技师我的业绩统计
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.USERS_TOTAL_USERTOTAL)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<StoreTotalResponse>> getEmpTotal(@Body Request body);


    /**
     * 技师我的现金业绩明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.USERS_TOTAL_CASHAMOUNTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AmountListResponse>> getEmpCashAmountList(@Body Request body);

    /**
     * 技师我的现消卡业绩明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.USERS_TOTAL_XKAMOUNTLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AmountListResponse>> getEmpXKAmountList(@Body Request body);

    /**
     * 技师我的订单业绩明细
     *
     * @param body
     * @return
     * @see DateRequest
     */
    @POST(NetConstants.USERS_TOTAL_ORDERLIST)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<AmountListResponse>> getEmpTotalCountOrderList(@Body Request body);

    /**
     * 上传我的作品
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.ImagesRequest
     */
    @POST(NetConstants.USERS_UPLOADPHOTOS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> uploadMyWorks(@Body Request body);

    /**
     * 获取我的作品
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.Request
     */
    @POST(NetConstants.USERS_GETPHOTOS)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<ImageWorks>>> getMyWorks(@Body Request body);

    /**
     * 上传极光推送ID
     *
     * @param body
     * @return
     * @see PushRequest
     */
    @POST(NetConstants.HOME_UPLOADPUSHID)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> uploadJPushId(@Body Request body);

    /**
     * 推送极光消息
     *
     * @param body
     * @return
     * @see PushRequest
     */
    @POST(NetConstants.MSG_PUSHMESSAGE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> pushMsg(@Body Request body);

    /**
     * 删除我的作品
     *
     * @param body
     * @return
     * @see cn.kuailaimei.store.api.request.IdRequest
     */
    @POST(NetConstants.USERS_DELETEPHOTO)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> deleteMyWorks(@Body Request body);
}
