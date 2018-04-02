package com.example.wudelin.smartbutler.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.ToastUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/4/2 11:31
 * 描述：    下载
 * Uri权限问题
 * 1.在mainfest下声明FileProvider
 *      <provider
 *          android:name="android.support.v4.content.FileProvider"
 *          android:authorities="自定义.fileprovider"
 *          android:grantUriPermissions="true"   表示临时授予Uri权限
 *          android:exported="false">
 *          <meta-data
 *              android:name="android.support.FILE_PROVIDER_PATHS"
 *              android:resource="@xml/filepaths" />
 *      </provider>
 * 2.在xml文件夹下创建相应的文件
 * <paths>
        <external-path path="." name="camera_photos" />
    </paths>
 <files-path/>代表的根目录： Context.getFilesDir()
 <external-path/>代表的根目录: Environment.getExternalStorageDirectory()
 <cache-path/>代表的根目录: getCacheDir()
 3、生成content://类型的Uri

 4、给Uri授予临时权限
 */

public class DownloadActivity extends BaseActivity {
    private TextView rate;
    private String url;
    private String path;

    private NumberProgressBar progressBar;
    //下载状态
    private static final int HANDLE_DOWNLOADING = 100;
    private static final int HANDLE_SUCCESS = 101;
    private static final int HANDLE_FAIL = 102;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_DOWNLOADING:
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    rate.setText(transferredBytes + " / " + totalSize);
                    //设置进度
                    progressBar.setProgress((int)(((float) transferredBytes / (float) totalSize)*100));
                    break;
                case HANDLE_SUCCESS:
                    rate.setText("下载已完成");
                    //启动安装
                    startInstallApp();
                    break;
                case HANDLE_FAIL:
                    rate.setText("下载失败");
                    break;
                default:
                    break;
            }
        }
    };

    private void startInstallApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        File file = new File(path);
        Uri uri = FileProvider.getUriForFile(this,
                "com.example.wudelin.smartbutler" + ".provider",
                file);
        //////////////////////申请权限///////////////////////////
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }

    private void initView() {
        rate = findViewById(R.id.rate);
        progressBar = findViewById(R.id.number_progress_bar);
        progressBar.setMax(100);
        url = getIntent().getStringExtra("apk_url");
        //download
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            download();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    download();
                } else {
                    ToastUtil.toastBy(this, "您拒绝了此权限！");
                    finish();
                }
                break;
        }
    }

    private void download() {
        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";
        if (!TextUtils.isEmpty(url)) {
            Logger.i("downloadurl", url
            );
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    Logger.i("download", "transferredBytes:" + transferredBytes
                            + "totalSize:" + totalSize);
                    Message message = new Message();
                    message.what = HANDLE_DOWNLOADING;
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    Logger.i("download", t);
                    handler.sendEmptyMessage(HANDLE_SUCCESS);
                }

                @Override
                public void onFailure(VolleyError error) {
                    Logger.i("download", error.getMessage());
                    handler.sendEmptyMessage(HANDLE_FAIL);
                }
            });

        }
    }
}
