package one.nem.kidshift.data.retrofit;

import one.nem.kidshift.data.retrofit.interceptor.AuthorizationInterceptor;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskAddRequest;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
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

    @POST("/parent/auth/login")
    Call<ParentLoginResponse> parentLogin(@Body ParentLoginRequest request);

    @GET("/parent/account")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ParentInfoResponse> getParentInfo();

    @GET("/parent/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> getTasks();

    @POST("/parent/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> addTask(@Body TaskAddRequest request);

    @PUT("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> updateTask(@Body TaskAddRequest request, @Path("id") String id);

    @DELETE("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> removeTask(@Path("id") String id);

    @GET("/parent/task/{id}")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> getTask(@Path("id") String id);

    @POST("/parent/task/{id}/complete")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> completeTask(@Path("id") String id);

}
