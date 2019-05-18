package jp.android.imaizumiriko.clothesdivide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class StartActivity extends AppCompatActivity {

    // onCreate　の役割
    // その画面が起動した時に、一番最初に実行される部分
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    // ここまで onCreate

    // ボタンが押されたら
//    public void ...(View v) {
//
//    }
    public void moveToCode(View v) {
        Intent intent = new Intent(getApplication(), CodeMainActivity.class);
        startActivity(intent);
    }

}
