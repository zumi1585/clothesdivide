package jp.android.imaizumiriko.clothesdivide;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    final String SAVE_KEY = "clothe_app_key";
    ArrayList<MySetData> allData;

    // 保存する２つのデータ
    private Uri imageUriTops;
    private Uri imageUriBottoms;
    private String mySetTitle;

//    private static final int REQUEST_CHOOSER = 1000;

    private static final int REQUEST_CHOOSER_TOPS = 1001;
    private static final int REQUEST_CHOOSER_BOTTOMS = 1002;


    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button = (Button) findViewById(R.id.buttonPanel);
        editText = (EditText) findViewById(R.id.edittext_title);

        allData = load();
//        showAllData();
    }

    private void showAllData() {
        if (allData == null) {
            Log.d("test", "空っぽだよ");
            return;
        }

        for (int i = 0; i < allData.size(); i++) {
            Log.d("test", allData.get(i).mySetTitle);
        }
    }

    public void pickImageTops(View v) {
        showGallery(REQUEST_CHOOSER_TOPS);
    }

    public void pickImageBottoms(View v) {
        showGallery(REQUEST_CHOOSER_BOTTOMS);
    }

    public void register(View v) {
        // imageUri の Tops と Bottoms は onActivityResult で値が入る

        // mySetTitle はまだ
        // TODO EditText から文字列を読み込んで来て、
        //     mySetTitle に代入する
        mySetTitle = editText.getText().toString();

        // mySetData に保存するデータのセットが入る（はず）
        MySetData mySetData = new MySetData(mySetTitle, imageUriTops.toString(), imageUriBottoms.toString());

        // TODO mySetData を他の画面に送る
        //     or ここで、保存する
        allData.add(mySetData);
        save();

        moveToCodeList();
    }

    private void moveToCodeList() {
        Intent intent = new Intent(this, CodeMainActivity.class);
        startActivity(intent);
    }


    private ArrayList<MySetData> load(){
        ArrayList<MySetData> arrayList;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(SAVE_KEY, "");
        if(json.equals("[]"))
        {
            arrayList = new ArrayList<MySetData>();
        }
        else
        {
            arrayList = gson.fromJson(json, new TypeToken<ArrayList<MySetData>>(){}.getType());
        }

        if (arrayList == null) arrayList = new ArrayList<MySetData>();

        return arrayList;
    }

    private void save() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        pref.edit().putString(SAVE_KEY, gson.toJson(allData)).apply();
    }

    // 「欲しい」って要求するプログラム
    // Android のシステムに画像が欲しい、と要求するプログラム
    private void showGallery(int which) {
        //カメラの起動Intentの用意
        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUriTops = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUriTops);

        // ギャラリー用のIntent作成
        Intent intentGallery;
        if (Build.VERSION.SDK_INT < 19) {
            intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
            intentGallery.setType("image/*");
        } else {
            intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
            intentGallery.setType("image/jpeg");
        }
        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentGallery});
        Log.d("Touroku", "登録するよー");
        // startActivity は投げっぱなし。
        // 例: URL をクリックすると、ブラウザが開く
        // これとは startActivityForResult は違う。
        // 「画像が欲しい」と要求して、結果が帰ってくるようにするプログラム

        if (which == REQUEST_CHOOSER_TOPS) {
            startActivityForResult(intent, REQUEST_CHOOSER_TOPS);
        } else if (which == REQUEST_CHOOSER_BOTTOMS) {
            startActivityForResult(intent, REQUEST_CHOOSER_BOTTOMS);
        }
    }

    // 結果が帰って来た時にうごくプログラム
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Touroku", "登録するよー");
        if (requestCode == REQUEST_CHOOSER_TOPS || requestCode == REQUEST_CHOOSER_BOTTOMS) {

            if (resultCode != RESULT_OK) {
                // キャンセル時
                Log.d("Touroku", "ダメだったよー");
            } else {
                // data から、（画像の）Uri を取り出す。
                // resultUri:「画像のデータがある場所」
                Uri resultUri = (data != null ? data.getData() : imageUriTops);

                if (resultUri == null) {
                    // 取得失敗
                    return;
                }

                // ギャラリーへスキャンを促す
//                MediaScannerConnection.scanFile(
//                        this,
//                        new String[]{resultUri.getPath()},
//                        new String[]{"image/jpeg"},
//                        null
//                );

                if (requestCode == REQUEST_CHOOSER_TOPS) {
                    putTopsImage(resultUri);
                }
                if (requestCode == REQUEST_CHOOSER_BOTTOMS) {
                    putButtomsImage(resultUri);
                }

            }
        }
    }


    private void putTopsImage(Uri resultUri) {
        // トップスの画像を設定
        // imageView に resultUri にある画像を設定する
        ImageView imageView = (ImageView) findViewById(R.id.image_view_tops);
        imageView.setImageURI(resultUri);
        imageUriTops = resultUri;
    }


    private void putButtomsImage(Uri resultUri) {
        // トップスの画像を設定
        // imageView に resultUri にある画像を設定する
        ImageView imageView = (ImageView) findViewById(R.id.image_view_buttoms);
        imageView.setImageURI(resultUri);
        imageUriBottoms = resultUri;
    }


}



