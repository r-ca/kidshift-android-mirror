package one.nem.kidshift.data.retrofit;

import one.nem.kidshift.data.retrofit.interceptor.AuthorizationInterceptor;
import one.nem.kidshift.data.retrofit.model.child.ChildAddRequest;
import one.nem.kidshift.data.retrofit.model.child.ChildListResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildLoginCodeResponse;
import one.nem.kidshift.data.retrofit.model.child.ChildResponse;
import one.nem.kidshift.data.retrofit.model.child.auth.ChildAuthRequest;
import one.nem.kidshift.data.retrofit.model.child.auth.ChildAuthResponse;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.parent.ParentRenameRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentAuthResponse;
import one.nem.kidshift.data.retrofit.model.task.HistoryListResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskAddRequest;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KidShiftApiService {

    // Parent APIs
    // Auth

    /**
     * 保護者ログイン処理
     * @param request ParentLoginRequest
     * @return ParentLoginResponse
     */
    @POST("/parent/auth/login")
    Call<ParentAuthResponse> parentLogin(@Body ParentAuthRequest request);

    /**
     * 保護者登録処理
     * @param request ParentRegisterRequest
     * @return ParentRegisterResponse
     */
    @POST("/parent/auth/register")
    Call<ParentAuthResponse> parentRegister(@Body ParentAuthRequest request);

    /**
     * 保護者情報更新処理
     * @param request ParentRenameRequest
     * @return ParentInfoResponse
     */
    @PUT("/parent/account")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ParentInfoResponse> renameParent(@Body ParentRenameRequest request);

    /**
     * 子供ログイン処理
     * @param request ChildAuthRequest
     * @return ChildAuthResponse
     */
    @POST("/child/auth/login")
    Call<ChildAuthResponse> childLogin(@Body ChildAuthRequest request);

    /**
     * 保護者アカウント情報取得処理
     * @return ParentInfoResponse
     */
    @GET("/parent/account")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ParentInfoResponse> getParentInfo();

    // Task APIs

    /**
     * タスク一覧取得
     * @return TaskListResponse
     */
    @GET("/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> getTasks();

    /**
     * タスク追加
     * @param request TaskAddRequest
     * @return TaskResponse
     */
    @POST("/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> addTask(@Body TaskAddRequest request);

    /**
     * タスク更新
     * @param request TaskAddRequest
     * @param id タスクID
     * @return TaskResponse
     */
    @PUT("/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> updateTask(@Body TaskAddRequest request, @Path("id") String id);

    /**
     * タスク削除
     * @param id タスクID
     * @return Void
     */
    @DELETE("/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<Void> removeTask(@Path("id") String id); // TODO-rca: OK responseをパース

    /**
     * タスク詳細取得
     * @param id タスクID
     * @return TaskResponse
     */
    @GET("/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> getTask(@Path("id") String id);

    /**
     * タスク完了処理
     * @param id タスクID
     * @return Void
     */
    @POST("/task/{id}/complete")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<Void> completeTask(@Path("id") String id, @Query("childId") String childId);

    // Child APIs

    /**
     * 子供一覧取得
     * @return ChildListResponse
     */
    @GET("/child")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ChildListResponse> getChildList();

    /**
     * 子供情報取得
     * @param id 子供ID
     * @return ChildResponse
     */
    @GET("/child/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ChildResponse> getChild(@Path("id") String id);

    /**
     * 子供追加
     * @param request ChildAddRequest
     * @return ChildResponse
     */
    @POST("/child")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ChildResponse> addChild(@Body ChildAddRequest request);

    /**
     * 子供更新
     * @param request ChildAddRequest
     * @param id 子供ID
     * @return ChildResponse
     */
    @PUT("/child/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ChildResponse> updateChild(@Body ChildAddRequest request, @Path("id") String id);

    /**
     * 子供削除
     * @param id 子供ID
     * @return Void
     */
    @DELETE("/child/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<Void> removeChild(@Path("id") String id);

    /**
     * 子供ログインコード発行
     * @param id 子供ID
     * @return ChildLoginCodeResponse
     */
    @GET("/child/{id}/login")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ChildLoginCodeResponse> issueLoginCode(@Path("id") String id);

    @GET("/task/history/{childId}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<HistoryListResponse> getHistory(@Path("childId") String childId);

}
