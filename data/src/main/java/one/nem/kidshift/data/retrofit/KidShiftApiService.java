package one.nem.kidshift.data.retrofit;

import one.nem.kidshift.data.retrofit.interceptor.AuthorizationInterceptor;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskAddRequest;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface KidShiftApiService {

    // Parent APIs
    // Auth

    /**
     * 保護者ログイン処理
     * @param request ParentLoginRequest
     * @return ParentLoginResponse
     */
    @POST("/parent/auth/login")
    Call<ParentLoginResponse> parentLogin(@Body ParentLoginRequest request);

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
    @GET("/parent/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> getTasks();

    /**
     * タスク追加
     * @param request TaskAddRequest
     * @return TaskResponse
     */
    @POST("/parent/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> addTask(@Body TaskAddRequest request);

    /**
     * タスク更新
     * @param request TaskAddRequest
     * @param id タスクID
     * @return TaskResponse
     */
    @PUT("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> updateTask(@Body TaskAddRequest request, @Path("id") String id);

    /**
     * タスク削除
     * @param id タスクID
     * @return Void
     */
    @DELETE("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<Void> removeTask(@Path("id") String id); // TODO-rca: OK responseをパース

    /**
     * タスク詳細取得
     * @param id タスクID
     * @return TaskResponse
     */
    @GET("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskResponse> getTask(@Path("id") String id);

    /**
     * タスク完了処理
     * @param id タスクID
     * @return Void
     */
    @POST("/parent/task/{id}/complete")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<Void> completeTask(@Path("id") String id); // TODO-rca: OK responseをパース

}
