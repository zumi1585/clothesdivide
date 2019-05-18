package jp.android.imaizumiriko.clothesdivide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CodeMainActivity extends AppCompatActivity {
    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_main);
    }

    public void moveToCodeitiran(View v) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), CodeListActivity.class);
        startActivity(intent);
    }

    public void moveToTouroku(View v) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);

    }

}
