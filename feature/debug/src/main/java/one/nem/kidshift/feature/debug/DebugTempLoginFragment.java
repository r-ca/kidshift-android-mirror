package one.nem.kidshift.feature.debug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import one.nem.kidshift.utils.KSLogger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebugTempLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DebugTempLoginFragment extends Fragment {

    @Inject
    KSLogger logger;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DebugTempLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebugTempLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebugTempLoginFragment newInstance(String param1, String param2) {
        DebugTempLoginFragment fragment = new DebugTempLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debug_temp_login, container, false);

        logger.setTag("Login");


        //xmlレイアウトからid取得
        EditText id = (EditText) view.findViewById(R.id.idtext);
        EditText pass = (EditText) view.findViewById(R.id.pass);

        CronetEngine.Builder engineBuilder = new CronetEngine.Builder(getContext().getApplicationContext());
        engineBuilder.enableHttp2(true);
        CronetEngine engine = engineBuilder.build();

        //ログインボタンを押したときの処理
        view.findViewById(R.id.button).setOnClickListener(
                view1 -> {
                    //入力された値（id,pass）を取得
                    Editable getText = id.getText();
                    Editable getPass = pass.getText();
//                    //入力された値（id,pass）をログに表示
//                    logger.debug(getText.toString());
//                    logger.debug(getPass.toString());

                    Executor executor = Executors.newSingleThreadExecutor();
                    CronetCallback callback = new CronetCallback();
                    UrlRequest.Builder requestBuilder = engine.newUrlRequestBuilder(
                            "https://kidshift-beta.nem.one/debug/hello", callback, executor);
                    UrlRequest request = requestBuilder.build();

                    request.start();
                }
        );




        return view;
    }




    private class CronetCallback extends UrlRequest.Callback{

        private ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream(); // 追加
        private WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived); // 追加

        @Override
        public void onRedirectReceived(UrlRequest request,UrlResponseInfo info,String newLocationUrl){
            request.followRedirect();

            // リダイレクトを許可しない場合
            // request.cancel();
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
            request.read(ByteBuffer.allocateDirect(1024));
        }

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
            byteBuffer.flip();
            try {
                receiveChannel.write(byteBuffer);
            } catch (IOException e){

            }
            byteBuffer.clear();
            request.read(byteBuffer);

            String body = StandardCharsets.UTF_8.decode(byteBuffer).toString();
            logger.debug(body);
        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            byte[] byteArray = bytesReceived.toByteArray();
            String json = new String(byteArray);
        }

        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            error.printStackTrace();
        }


    }

}