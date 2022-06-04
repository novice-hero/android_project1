package kr.hnu.android_project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveMessageActivity extends AppCompatActivity {
    // 받은 메시지를 눌렀을 때 나오는 화면
    Button btnBack_receive, btnDelete_receive, btnReply_receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        btnBack_receive = findViewById(R.id.receive_btn_back);
        btnDelete_receive = findViewById(R.id.receive_btn_delete);
        btnReply_receive = findViewById(R.id.receive_btn_reply);
        Fragment fragment_write = new WriteMessageFragment();

        Intent intent = getIntent(); // 인텐트를 받아서 정보를 각각 저장

        String sender = intent.getExtras().getString("sender");
        String receiver = intent.getExtras().getString("receiver");
        String title = intent.getExtras().getString("title");
        String content = intent.getExtras().getString("content");
        String sendDate = intent.getExtras().getString("sendDate");

        TextView tv_sender = findViewById(R.id.tv_sender_empty);
        TextView tv_receiver = findViewById(R.id.tv_receiver_empty);
        TextView tv_title = findViewById(R.id.tv_title_empty);
        TextView tv_date = findViewById(R.id.tv_date_empty);
        TextView tv_content = findViewById(R.id.tv_content_empty);

        tv_sender.setText(sender);
        tv_receiver.setText(receiver);
        tv_title.setText(title);
        tv_date.setText(sendDate);
        tv_content.setText(content);

        btnBack_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelete_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("error", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean isSuccess = jsonResponse.getBoolean("success");
                            if (isSuccess) {
                                Toast.makeText(v.getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("highero10", "failed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ReceiveDeleteRequest receiveDeleteRequest = new ReceiveDeleteRequest(tv_receiver.getText().toString(), tv_date.getText().toString(), responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                requestQueue.add(receiveDeleteRequest);
            }
        });
        btnReply_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.write_layout, WriteMessageFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
    }
}
