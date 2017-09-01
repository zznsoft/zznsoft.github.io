package com.huasky.elderyun.common.utils.httpClient;


import com.google.gson.JsonObject;
import com.huasky.elderyun.bean.BaseListBean;
import com.huasky.elderyun.bean.ContactBean;
import com.huasky.elderyun.bean.LoginBean;
import com.huasky.elderyun.bean.mediaBean.CommentInfo;
import com.huasky.elderyun.bean.mediaBean.MediaInfo;
import com.huasky.elderyun.bean.mediaBean.PlayInfo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * api 接口
 * Created by pokermman on 2017/1/25.
 * + * Created by pokermman on 2017/2/6.
 */


public interface ApiService {
    //    @POST("/airportBus/startStation/findStartStation.do")

    /**
     * 测试接口
     */
    @POST("/member/login/login.do")
    Observable<HttpResult<Object>> getLogin();

            /*
     * 文件图片上传接口测试
     * */

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意：使用{@code @Multipart}注解方法，必须使用{@code @Part}/<br>
     * {@code @PartMap}注解其参数<br>
     * 本接口中将文本数据和文件数据分为了两个参数，是为了方便将封装<br>
     * {@link MultipartBody.Part}的代码抽取到工具类中<br>
     * 也可以合并成一个{@code @Part}参数
     *
     * @param params 用于封装文本数据
     * @param parts  用于封装文件数据
     * @return HttpResult<Object> 为服务器返回的基本Json数据的Model类
     */
    @Multipart
    @POST("")
    Observable<HttpResult<Object>> requestUploadWork(@PartMap Map<String, RequestBody> params,
                                                     @Part List<MultipartBody.Part> parts);

    /**
     * 注意1：必须使用{@code @POST}注解为post请求<br>
     * 注意2：使用{@code @Body}注解参数，则不能使用{@code @Multipart}注解方法了<br>
     * 直接将所有的{@link MultipartBody.Part}合并到一个{@link MultipartBody}中
     */
    @POST("")
    Observable<HttpResult<Object>> requestUploadWork(@Body MultipartBody body);


    /**
     * 登录
     *
     * @param idCard   身份证号码
     * @param password 密码
     */
    @POST("/mgr/elder/doLogin.do")
    Observable<HttpResult<LoginBean>> doLogin(@Query("idCard")String idCard,@Query("password") String password);


    /**
     * 获取短信验证码
     *
     * @param mobile 手机号
     * @param type   1-注册，2忘记密码
     */
    @POST("/mgr/member/register/getSmsCode.do")
    Observable<HttpResult<String>> getSmsCode(@Query("mobile") String mobile, @Query("type") String type);


    /**
     * 注册
     *
     * @param mobile   手机号
     * @param password 密码
     * @param smsCode  短信验证码
     */
    @POST("/mgr/member/register/register.do")
    Observable<HttpResult<Object>> getRegister(@Query("mobile") String mobile, @Query("password") String password, @Query("smsCode") String smsCode);


    /**
     * 养生视频媒体播放列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @POST("/mgr/vod/media/searchMedia.do?Action=SearchMedia")
    Observable<HttpResult<MediaInfo>> searchMedia(@Query("PageNumber")String pageNumber, @Query("PageSize")String pageSize, @Query("KeyWord")String keyword);

    /**
     * 养生视频MediaId获取
     * @param MediaIds
     * @return
     */
    @POST("/mgr/vod/media/queryMediaList.do?Action=QueryMediaList&IncludePlayList=true")
    Observable<HttpResult<PlayInfo>> searchMediaList(@Query("MediaIds")String MediaIds);

    /**
     * 添加评论
     * @param mediaId
     * @param comment  要发送的评论
     * @return
     */
    @POST("/mgr/vod/addVodComment.do")
    Observable<HttpResult<JsonObject>> addVodComment(@Query("mediaId")String mediaId, @Query("comment")String comment);

    /**
     * 获取评论列表
     * @param mediaId
     * @param offset 偏移
     * @param size  大小
     * @return
     */
    @POST("/mgr/vod/getVodCommentList.do")
    Observable<HttpResult<CommentInfo>> getVodCommentList(@Query("mediaId")String mediaId, @Query("offset")String offset, @Query("size")String size);


    /**
     * 退出登陆
     * @return
     */
    @POST("/mgr/elder/doLogout.do")
    Observable<HttpResult<Object>> doLogout();

    /**
     * 3.2      紧急联络（求助）
     *
     * @param elderId 老人id
     */
    @POST("/mgr/emergency/getEmergencyContactList.do")
    Observable<HttpResult<BaseListBean<ContactBean>>> getEmergencyContactList(@Query("elderId") String elderId);


    /**
     * 报警
     * @param elderId 老人id
     * @param deviceType 0
     * @param longitude 空
     * @param latitude 空
     * @return
     */
    @POST("/mgr/emergency/emergency.do")
    Observable<HttpResult<Object>> emergency(@Query("deviceSerialNo")String elderId,@Query("deviceType")String deviceType,@Query("longitude")String longitude,@Query("latitude")String latitude);
}



