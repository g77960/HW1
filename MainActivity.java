package com.robot.asus.ZenboHelloWorld;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.robot.asus.robotactivity.RobotActivity;
import com.asus.robotframework.API.results.RoomInfo;
import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotFace;


import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public RobotAPI robotAPI;
    RobotCallback robotCallback;
    RobotCallback.Listen robotListenCallback;

    private Button button01;
    private Button button02;
    private Button button03;
    private Button button04;
    private Button button05;
    private Button button06;

    public MainActivity(RobotCallback robotCallback, RobotCallback.Listen robotListenCallback) {
        this.robotCallback = robotCallback;
        this.robotListenCallback = robotListenCallback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.robotAPI = new RobotAPI(getApplicationContext(), robotCallback);
        button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast;
                int intCmdID;
                try{
                    java.util.ArrayList<RoomInfo> dataRooms = robotAPI.contacts.room.getAllRoomInfo();
                    if(dataRooms.size()>0){
                        {
                            toast = Toast.makeText(MainActivity.this,
                                    "前往系上辦公室", Toast.LENGTH_LONG);
                            //顯示Toast
                            toast.show();

                            intCmdID=robotAPI.motion.goTo("系辦公室");
                            toast = Toast.makeText(MainActivity.this,
                                    "Goto button " + intCmdID, Toast.LENGTH_LONG);
                            //顯示Toast
                            toast.show();
                        }
                    }else{
                        toast = Toast.makeText(MainActivity.this,
                                "尚未建置地圖，請先建置地圖", Toast.LENGTH_LONG);
                        //顯示Toast
                        toast.show();
                    }

                }catch (Exception e){

                    toast = Toast.makeText(MainActivity.this,
                            "Error" + e.toString(), Toast.LENGTH_LONG);
                    //顯示Toast
                    toast.show();
                }
                // robotAPI.robot.speak("歡迎來到系公室");
                // Intent intent = new Intent();
                //intent.setClass(MainActivity.this  ,a1.class);
                // startActivity(intent);
                // MainActivity.this.finish();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        robotAPI.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        robotAPI.robot.unregisterListenCallback();
    }



    @Override
    protected void onResume() {
        super.onResume();
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);
        if (robotListenCallback != null) robotAPI.robot.registerListenCallback(robotListenCallback);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}

//    @Override
//    protected void onResume() {
//        super.onResume();
//        robotAPI.robot.speak("Hello world");
//
//    }

