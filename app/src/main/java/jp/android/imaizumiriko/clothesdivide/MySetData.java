package jp.android.imaizumiriko.clothesdivide;

import android.net.Uri;

import java.io.Serializable;

public class MySetData {
    String mySetTitle;
    String imageUriTopsString;
    String imageUriBottomsString;

    public MySetData(String mySetTitle, String imageUriTopsString,  String imageUriBottomsString) {
        this.mySetTitle = mySetTitle;
        this.imageUriTopsString = imageUriTopsString;
        this.imageUriBottomsString = imageUriBottomsString;
    }
}
