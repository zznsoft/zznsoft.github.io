package com.example.zzn.nuomi.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zzn.nuomi.R;
import com.example.zzn.nuomi.dialog.AddHead;
import com.example.zzn.nuomi.dialog.SexMenu;
import com.example.zzn.nuomi.utils.CircleImageView;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import rx.functions.Action1;

public class AccountInfomationActivity extends AppCompatActivity {
    private EditText nickname, explain;
    private CardView img, sex;
    private TextView sextv;
    private SexMenu sexMenu;
    private AddHead addHead;
    private CircleImageView headImg;
    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_infomation);
        initview();
        init();
    }

    private void initview() {
        nickname = (EditText) findViewById(R.id.account_settings_nickname);
        explain = (EditText) findViewById(R.id.account_settings_explain);
        img = (CardView) findViewById(R.id.account_settings_headimg);
        sex = (CardView) findViewById(R.id.account_settings_sex);
        sextv = (TextView) findViewById(R.id.account_settings_sex_text);
        headImg = (CircleImageView) findViewById(R.id.img_head);
        img.setOnClickListener(listener);
        sex.setOnClickListener(listener);
    }

    private void init() {
        if (Hawk.contains("nickname")) {
            nickname.setText(Hawk.get("nickname").toString());
        }
        if (Hawk.contains("explain")) {
            explain.setText(Hawk.get("explain").toString());
        }
        if (Hawk.contains("sex")) {
            sextv.setText(Hawk.get("sex").toString());
        }
        if (Hawk.contains("headImg")){
//            mBitmap=Hawk.get("headImg");
//            headImg.setImageBitmap(mBitmap);
        }
        nickname.setSelection(nickname.getText().length());
        nickname.setCursorVisible(false);

        explain.setSelection(explain.getText().length());
        explain.setCursorVisible(false);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.account_settings_sex:
                    sexMenu = new SexMenu(AccountInfomationActivity.this, clickListener);
                    sexMenu.show();
                    break;
                case R.id.account_settings_headimg:
                    RxPermissions rxPermissions = new RxPermissions(AccountInfomationActivity.this);
                    rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(
                            new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {
                                        addHead = new AddHead(AccountInfomationActivity.this, headClickListener);
                                        addHead.show();
                                    } else {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccountInfomationActivity.this);
                                        alertDialog.setMessage("请手动开启权限！");
                                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                        alertDialog.create().show();
                                    }
                                }
                            });
                    break;
            }
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top:
                    sextv.setText("男");
                    Hawk.put("sex", "男");
                    break;
                case R.id.bottom:
                    sextv.setText("女");
                    Hawk.put("sex", "女");
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener headClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.top://打开相册拍照
                    Intent openCameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    tempUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "temp_image.jpg"));
                    // 将拍照所得的相片保存到SD卡根目录
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(openCameraIntent, TAKE_PICTURE);
                    break;
                case R.id.bottom://选择本地照片
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setType("image/*");
                    //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AccountInfomationActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:

                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            headImg.setImageBitmap(mBitmap);//显示图片
//            Hawk.put("headImg",mBitmap);
//            saveBitmap(mBitmap);
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hawk.put("nickname", nickname.getText().toString());
        Hawk.put("explain", explain.getText().toString());
    }
}
