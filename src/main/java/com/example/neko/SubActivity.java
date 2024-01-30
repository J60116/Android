package com.example.neko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SubActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{
    //Viewの宣言
    ImageView imgCat = null;
    TextView txtPat = null;
    TextView txtTsukkomi = null;
    //選択した猫の種類
    int type = -1;
    //猫画像の配列
    int [] imageArrayMike = {R.drawable.mike0, R.drawable.mike1, R.drawable.mike2, R.drawable.mike3};
    int [] imageArraySiro = {R.drawable.siro0, R.drawable.siro1, R.drawable.siro2, R.drawable.siro3};
    int[][] imageArray = {imageArrayMike, imageArraySiro};
    //現在の画像表示要素番号 初期は0としておく
    int imgNum = 0;
    //MotionEvent.ACTION_DOWN時のxy座標値
    int dx,dy = 0;
    //MotionEvent.ACTION_UP時のxy座標値
    int ux,uy = 0;
    //愛でた回数
    int countsOfPat = 0;
    //つっこんだ回数
    int countsOfTsukkomi = 0;
    //Randomクラスの用意
    Random rand = null;
    //ツッコミ文章の配列
    int[] tsukkomiArray = {R.string.no_way, R.string.what, R.string.bash};
    //現在のツッコミ要素番号 初期は0としておく
    int tsukkomiNum = 0;

    //Activityの画面全体でのタッチイベント
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //ACTION_DOWN時のx,y座標値の取得
                dx = (int)event.getX();
                dy = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                //ACTION_UP時のx,y座標値の取得
                ux = (int)event.getX();
                uy = (int)event.getY();

                //ログキャットで動作確認
                Log.d("distance_onTouchEvent","(x:" + (dx - ux) + ", y:" + (dy - uy) + ")");

                //上下方向へフリック
                if (dy - uy > 200) {
                    //上方向
                    imgNum = 1;
                } else if (dy - uy < -200) {
                    //下方向
                    //前の画像が1のときにカウント
                    if (imgNum == 1) {
                        tsukkomiNum = rand.nextInt(3);
                        Toast.makeText(this, getString(tsukkomiArray[tsukkomiNum]), Toast.LENGTH_SHORT).show();
                        countsOfTsukkomi++;
                        Log.d("tsukkomiNum",String.valueOf(countsOfTsukkomi));
                    }
                    //要素番号が1のときは2、それ以外は0にする
                    imgNum = (imgNum == 1) ? 2 : 0;
                } else {
                    //フリック距離が足りない場合
                    imgNum = 0;
                }

                break;
        }

        //表示
        imgCat.setImageResource(imageArray[type][imgNum]);
        txtPat.setText(String.valueOf(countsOfPat));
        txtTsukkomi.setText(String.valueOf(countsOfTsukkomi));

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        //Randomインスタンスの生成
        rand = new Random();

        //Viewの取得
        imgCat = findViewById(R.id.imgMike);
        txtPat = findViewById(R.id.txtCountPat);
        txtTsukkomi = findViewById(R.id.txtCountTsukkomi);
        Button btn = findViewById(R.id.btnFinish);

        //選択した猫のデータを取得
        Intent intent = getIntent();
        if(intent.hasExtra("select type of neko")){
            type = intent.getIntExtra("select type of neko",0);
        }

        //表示
        imgCat.setImageResource(imageArray[type][0]);
        txtPat.setText(String.valueOf(countsOfPat));
        txtTsukkomi.setText(String.valueOf(countsOfTsukkomi));

        //イベントリスナの設定
        btn.setOnClickListener(this);
        imgCat.setOnTouchListener(this);
    }

    //ボタンをクリックした時
    @Override
    public void onClick(View v) {
        finish();
    }

    //猫をタッチした時
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //ACTION_DOWN時のx,y座標値の取得
                dx = (int)event.getX();
                dy = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                //ACTION_UP時のx,y座標値の取得
                ux = (int)event.getX();
                uy = (int)event.getY();

                //ログキャットで動作確認
                Log.d("distance_onTouch","(x:" + (dx - ux) + ", y:" + (dy - uy) + ")");

                //上下方向へフリック
                if (dy - uy > 200) {
                    //上方向
                    imgNum = 1;
                } else if (dy - uy < -200) {
                    //下方向
                    //前の画像が1のときにカウント
                    if (imgNum == 1) {
                        tsukkomiNum = rand.nextInt(3);
                        Toast.makeText(this, getString(tsukkomiArray[tsukkomiNum]), Toast.LENGTH_SHORT).show();
                        countsOfTsukkomi++;
                        Log.d("tsukkomiNum",String.valueOf(countsOfTsukkomi));
                    }
                    //要素番号が1のときは2、それ以外は0にする
                    imgNum = (imgNum == 1) ? 2 : 0;
                } else {
                    //フリック距離が足りない場合
                    imgNum = 0;
                }

                //左右方向へフリック
                if (Math.abs(dx - ux) > 100 && Math.abs(dy - uy) < 100 ) {
                    countsOfPat++;
                    imgNum = 3;
                }
                break;
        }
        imgCat.setImageResource(imageArray[type][imgNum]);
        txtPat.setText(String.valueOf(countsOfPat));
        txtTsukkomi.setText(String.valueOf(countsOfTsukkomi));
        return false;
    }
}
