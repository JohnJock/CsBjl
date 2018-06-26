package com.android.bjl.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.bjl.R;
import com.android.bjl.bean.MyrentBean;
import com.android.bjl.http.Constant;
import com.android.bjl.http.HttpCommon;
import com.android.bjl.http.HttpEventCallBack;
import com.android.bjl.util.FilePathManager;
import com.android.bjl.util.FileUtils;
import com.android.bjl.util.MyToastUtil;
import com.android.bjl.util.PrefUtils;
import com.android.bjl.widget.headerlistview.MyGridView;
import com.example.mjj.selectphotodemo.PhotoPickerActivity;
import com.example.mjj.selectphotodemo.PreviewPhotoActivity;
import com.example.mjj.selectphotodemo.beans.ImageItem;
import com.example.mjj.selectphotodemo.utils.Bimp;
import com.example.mjj.selectphotodemo.utils.BitmapUtils;
import com.example.mjj.selectphotodemo.utils.MPermissionUtils;
import com.example.mjj.selectphotodemo.utils.OtherUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 我要出租
 * Created by john on 2018/4/18.
 */

public class MyRentActivity extends BaseActivity implements View.OnClickListener {
    private static final int PICK_PHOTO = 1;
    private static final int REQUEST_CAMERA = 2;
    private List<Bitmap> mResults = new ArrayList<>();
    public static Bitmap bimap;
    private MyGridView noScrollgridview;
    private GridAdapter adapter;
    private EditText title, area, price, telephone, description;
    private Button done;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrent);
        showTitleLeftTvAndImg("我要出租", "返回", R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_focused);

        mResults.add(bimap);
        initView();
    }


    public void showItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题
//        builder.setTitle("请选择图片方式");
//        //设置图标
//        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(new CharSequence[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    showCamera();
                } else if (i == 1) {
                    Intent intent = new Intent(MyRentActivity.this, PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
                    // 总共选择的图片数量
                    intent.putExtra(PhotoPickerActivity.TOTAL_MAX_MUN, Bimp.tempSelectBitmap.size());
                    startActivityForResult(intent, PICK_PHOTO);
                }
            }
        });
        builder.create();
        builder.show();
    }

    /**
     * 拍照时存储拍照结果的临时文件
     */
    private File mTmpFile;

    /**
     * 选择相机
     */
    private void showCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {


                String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()
                        + "/test/photo.jpg";

                mTmpFile = new File(IMAGE_FILE_LOCATION);
                if (!mTmpFile.getParentFile().exists()) {
                    mTmpFile.getParentFile().mkdirs();
                }

                Uri imageUri = null;
                //判断是否是AndroidN以及更高的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(MyRentActivity.this, "com.android.bjl.fileProvider", mTmpFile);
                } else {
                    imageUri = Uri.fromFile(mTmpFile);
                }

                if (mTmpFile != null && mTmpFile.exists()) {

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(this, "图片格式错误", Toast.LENGTH_SHORT).show();
                }


                // 设置系统相机拍照后的输出路径
                // 创建临时文件
//                mTmpFile = OtherUtils.createTmpFile(getApplicationContext());

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {

        noScrollgridview = (MyGridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        title = (EditText) findViewById(R.id.et_myrent_title);
        telephone = (EditText) findViewById(R.id.et_myrent_telephone);
        price = (EditText) findViewById(R.id.et_myrent_price);
        area = (EditText) findViewById(R.id.et_myrent_area);
        description = (EditText) findViewById(R.id.et_myrent_description);
        done = (Button) findViewById(R.id.btn_myrent_done);
        done.setOnClickListener(this);
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mResults.size() - 1 || i == Bimp.tempSelectBitmap.size()) {

                    showItemDialog();
                } else {
                    Intent intent = new Intent(MyRentActivity.this,
                            PreviewPhotoActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", i);
                    startActivity(intent);
                }
            }
        });

        MPermissionUtils.requestPermissionsResult(MyRentActivity.this, 1,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(MyRentActivity.this);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                showResult(result);
            }
        } else if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + mTmpFile.getAbsolutePath())));
                    ArrayList<String> result = new ArrayList<>();
                    result.add(mTmpFile.getAbsolutePath());
                    showResult(result);
                }
            } else {
                if (mTmpFile != null && mTmpFile.exists()) {
                    mTmpFile.delete();
                }
            }

        }

    }

    private void showResult(ArrayList<String> paths) {
        if (mResults == null) {
            mResults = new ArrayList<>();
        }
        if (paths.size() != 0) {
            mResults.remove(mResults.size() - 1);
        }
        for (int i = 0; i < paths.size(); i++) {
            // 压缩图片
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFd(paths.get(i), 500, 500);
            // 针对小图也可以不压缩
//            Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
            mResults.add(bitmap);
//            saveImage(bitmap,MyRentActivity.this,paths.get(i));
            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bitmap);
//            Uri imageUri;
//            //判断是否是AndroidN以及更高的版本
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                imageUri = FileProvider.getUriForFile(MyRentActivity.this, "com.android.bjl.fileProvider", new File(paths.get(i)));
//            } else {
//                imageUri = Uri.fromFile(new File(paths.get(i)));
//            }
            takePhoto.setImagePath(paths.get(i));

            Bimp.tempSelectBitmap.add(takePhoto);
        }
        mResults.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_focused));
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        String titleString = title.getText().toString().trim();
        String areaString = area.getText().toString().trim();
        String priceString = price.getText().toString().trim();
        String phoneString = telephone.getText().toString().trim();
        String decriptionString = description.getText().toString().trim();
        if (TextUtils.isEmpty(titleString)) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请输入正确的标题");
            return;
        }
        if (TextUtils.isEmpty(areaString)) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请输入正确的面积");
            return;
        }
        if (TextUtils.isEmpty(priceString)) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请输入正确的租金");
            return;
        }
        if (TextUtils.isEmpty(phoneString)) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请输入正确的电话");
            return;
        }
        if (TextUtils.isEmpty(decriptionString)) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请输入正确的房子简介");
            return;
        }
        if (Bimp.tempSelectBitmap.size() == 0) {
            MyToastUtil.showMEssage(MyRentActivity.this, "请至少上传一张图片");
            return;
        }
        HashMap map = new LinkedHashMap();
        map.put("title", titleString);
        map.put("area", areaString);
        map.put("price", priceString);
        map.put("telephone", phoneString);
        map.put("description", decriptionString);
        map.put("userId", PrefUtils.getString(MyRentActivity.this, "userId", ""));
        String[] keyPath = new String[Bimp.tempSelectBitmap.size()];
        String[] filePath = new String[Bimp.tempSelectBitmap.size()];
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            keyPath[i] = "img" + i;
            filePath[i] = Bimp.tempSelectBitmap.get(i).getImagePath();
        }

        new HttpCommon(MyRentActivity.this, new HttpEventCallBack() {
            @Override
            public void onHttpSuccess(int requestId, Object content) {
                loadDialogDismiss();
                try {

                    final JSONObject object = new JSONObject(content.toString());
                    if (object.getString("code").equals("00")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                MyToastUtil.showMEssage(MyRentActivity.this, "添加房屋信息成功!");
                                Bimp.tempSelectBitmap.clear();
                                Intent intent =new Intent();
                                setResult(100,intent);
                                finish();


                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    MyToastUtil.showMEssage(MyRentActivity.this, object.getString("desc"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onHttpFail(int requestId, HttpRetId httpStatus, String msg) {
                loadDialogDismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToastUtil.showMEssage(MyRentActivity.this, "数据请求失败");

                    }
                });
            }

            @Override
            public void OnHttpReceived(int requestId, long nDataLen, long totalLength) {

            }
        }).uploadFile(0, Constant.RENTOUTHOUSE, map, keyPath, filePath);
        loadDialogShow();
    }


    /**
     * 适配器
     */
    public class GridAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 6) {
                return 6;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return mResults.get(arg0);
        }

        public long getItemId(int arg0) {
            return arg0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gridview, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.imageView1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_focused));
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    /**
     * 保存图片
     *
     * @param photo
     * @return
     */
    public boolean saveImage(Bitmap photo, Context context, String path) {
        OutputStream bos = null;
        try {
            // 把文件保存到本地
            File file1 = new File(path);
            FilePathManager filePathManager = FilePathManager.getIntance(context);
            String filePath = filePathManager.getImgPath() + file1.getName();
            File file = new File(filePath);
            if (file.exists()) {
                FileUtils.createFileByDeleteOldFile(file);
            }
            bos = new FileOutputStream(filePath);
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            photo.recycle();
//            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            Uri uri = Uri.fromFile(file);
//            intent.setData(uri);
//            this.cordova.getActivity().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
