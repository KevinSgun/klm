package cn.kuailaimei.store.api.service;

import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;

import cn.kuailaimei.store.api.NetConstants;
import cn.kuailaimei.store.api.request.LoginRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.UserInfoResponse;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author makk
 * @version V1.0
 * @Project: PersonalAccount
 * @Package com.zitech.personalaccount.data.network.service
 * @Description:(用一句话描述该文件做什么)
 * @date 2016/5/13 15:05
 */
public interface AccountService {

    /**
     * 获取验证码
     *
     * @param body
     * @return
     * @see {@link cn.kuailaimei.store.api.request.VerifyCodeRequest}
     */
    @POST(NetConstants.USERS_CODE)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> getVerifyCode(@Body Request body);

    /**
     * 用户登录
     *
     * @param body
     * @return
     * @see {@link LoginRequest}
     */
    @POST(NetConstants.USERS_LOGIN)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<UserInfoResponse>> requestLogin(@Body Request body);

    /**
     * 忘记密码
     *
     * @param body
     * @return
     * @see {@link cn.kuailaimei.store.api.request.ResetPsdRequest}
     */
    @POST(NetConstants.USERS_FORGET)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> requestResetPsd(@Body Request body);

    /**
     * 修改密码
     *
     * @param body
     * @return
     * @see {@link cn.kuailaimei.store.api.request.ModifyPsdRequest}
     */
    @POST(NetConstants.USERS_UPDATEPASSWORD)
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse> requestModifyPsd(@Body Request body);


}
