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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Viewの宣言
    TextView txtL = null;
    TextView txtR = null;
    ImageView imgMike = null;
    ImageView imgSiro = null;
    //選択した猫の種類（三毛猫は0、白猫は1）
    int type = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Viewの取得
        txtL = findViewById(R.id.txtLeft);
        txtR = findViewById(R.id.txtRight);
        imgMike = findViewById(R.id.imgMike);
        imgSiro = findViewById(R.id.imgSiro);
        Button btn = findViewById(R.id.btnStart);

        //イベントリスナの設定
        imgMike.setOnClickListener(this);
        imgSiro.setOnClickListener(this);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //画像をクリック
        if(id == R.id.imgMike){
            //左の猫をクリックした場合
            type = 0;
            txtL.setText(R.string.mike);
            txtR.setText("");
            imgMike.setImageResource(R.drawable.mike1);
            imgSiro.setImageResource(R.drawable.siro0);
            Log.d("select","Mike");
            Toast.makeText(this, getString(R.string.choose_mike), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.imgSiro) {
            //右の猫をクリックした場合
            type = 1;
            txtL.setText("");
            txtR.setText(R.string.siro);
            imgMike.setImageResource(R.drawable.mike0);
            imgSiro.setImageResource(R.drawable.siro1);
            Log.d("select","Siro");
            Toast.makeText(this, getString(R.string.choose_siro), Toast.LENGTH_SHORT).show();
        }

        //ボタンをクリック
        if(id == R.id.btnStart) {
            //猫が選択されていない場合
            if(type == -1){
                Toast.makeText(this, getString(R.string.no_choice), Toast.LENGTH_SHORT).show();
                return;
            }
            //猫の情報を次の画面に渡す
            Intent intent = new Intent(this, SubActivity.class);
            intent.putExtra("select type of neko", type);
            startActivity(intent);

        }


    }


}