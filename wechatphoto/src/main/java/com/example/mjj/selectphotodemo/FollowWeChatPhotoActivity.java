package com.example.mjj.selectphotodemo;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mjj.selectphotodemo.beans.ImageItem;
import com.example.mjj.selectphotodemo.utils.Bimp;
import com.example.mjj.selectphotodemo.utils.BitmapUtils;
import com.example.mjj.selectphotodemo.utils.MPermissionUtils;
import com.example.mjj.selectphotodemo.utils.OtherUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：仿微信朋友圈图片选择功能
 * <p>
 * Created by Mjj on 2016/12/2.
 */

public class FollowWeChatPhotoActivity extends AppCompatActivity {

    private static final int PICK_PHOTO = 1;
    private static final int REQUEST_CAMERA=2;
    private List<Bitmap> mResults = new ArrayList<>();

    public static Bitmap bimap;
    private GridView noScrollgridview;
    private GridAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    Intent intent = new Intent(FollowWeChatPhotoActivity.this, PhotoPickerActivity.class);
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
                // 设置系统相机拍照后的输出路径
                // 创建临时文件
                mTmpFile = OtherUtils.createTmpFile(getApplicationContext());

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mTmpFile != null && mTmpFile.exists()) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(this, "图片格式错误", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_follow_wechat_photo);

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mResults.size() - 1 || i == Bimp.tempSelectBitmap.size()) {
//                    Intent intent = new Intent(FollowWeChatPhotoActivity.this, PhotoPickerActivity.class);
//                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
//                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
//                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
//                    // 总共选择的图片数量
//                    intent.putExtra(PhotoPickerActivity.TOTAL_MAX_MUN, Bimp.tempSelectBitmap.size());
//                    startActivityForResult(intent, PICK_PHOTO);
                    showItemDialog();
                } else {
                    Intent intent = new Intent(FollowWeChatPhotoActivity.this,
                            PreviewPhotoActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", i);
                    startActivity(intent);
                }
            }
        });

        MPermissionUtils.requestPermissionsResult(FollowWeChatPhotoActivity.this, 1,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(FollowWeChatPhotoActivity.this);
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
        }else if (requestCode ==REQUEST_CAMERA){
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    ArrayList<String> result=new ArrayList<>();
                    result.add( mTmpFile.getAbsolutePath());
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
            Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFd(paths.get(i), 400, 500);
            // 针对小图也可以不压缩
//            Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
            mResults.add(bitmap);

            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bitmap);
            Bimp.tempSelectBitmap.add(takePhoto);
        }
        mResults.add(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_focused));
        adapter.notifyDataSetChanged();
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
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
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
                if (position == 9) {
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


}
