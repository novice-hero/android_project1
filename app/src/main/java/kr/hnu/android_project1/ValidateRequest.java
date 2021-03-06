package kr.hnu.android_project1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    // 아이디 중복 검사 리퀘스트
    final static private String URL = "http://highero10.dothome.co.kr/AndroidProject/ValidateUser.php";
    private Map<String, String> parameters;
    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ValidateRequest", error.getMessage());
            }
        });
        // 중복 검사할 아이디를 해시맵에 넣어줌
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
