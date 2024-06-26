package one.nem.kidshift.data.retrofit;

import one.nem.kidshift.data.retrofit.interceptor.AuthorizationInterceptor;
import one.nem.kidshift.data.retrofit.model.parent.ParentInfoResponse;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginRequest;
import one.nem.kidshift.data.retrofit.model.parent.auth.ParentLoginResponse;
import one.nem.kidshift.data.retrofit.model.task.TaskListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface KidShiftApiService {

    @POST("/parent/auth/login")
    Call<ParentLoginResponse> parentLogin(@Body ParentLoginRequest request);

    @GET("/parent/account")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<ParentInfoResponse> getParentInfo();

    @GET("/parent/task")
    @Headers(AuthorizationInterceptor.HEADER_PLACEHOLDER)
    Call<TaskListResponse> getTasks();
}
