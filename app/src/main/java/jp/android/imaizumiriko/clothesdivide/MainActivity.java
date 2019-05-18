package jp.android.imaizumiriko.clothesdivide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void moveToTouroku(View v) {
        // 明示的Intent
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);

        // ←→ 暗黙的Intent
    }
}
