package com.example.dialogdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    Button singleDialog;
    Button multiDialog;
    Button loadDialog;
    Button downloadDialog;
    Button editDialog;
    Button diyDialog;
    final String[] items = {"男", "女", "不告诉你"};
    final String[] hobby = {"吃", "喝", "玩", "乐", "学习", "睡觉"};
    int choice;
    List<Integer> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btn.setOnClickListener(this);
        singleDialog.setOnClickListener(this);
        multiDialog.setOnClickListener(this);
        loadDialog.setOnClickListener(this);
        downloadDialog.setOnClickListener(this);
        editDialog.setOnClickListener(this);
        diyDialog.setOnClickListener(this);
    }

    private void init() {
        btn = findViewById(R.id.dialog);
        singleDialog = findViewById(R.id.singleDialog);
        multiDialog = findViewById(R.id.multiDialog);
        loadDialog = findViewById(R.id.loadDialog);
        downloadDialog = findViewById(R.id.downloadDialog);
        editDialog = findViewById(R.id.editDialog);
        diyDialog = findViewById(R.id.diyDialog);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog:
                dialog();
                break;
            case R.id.singleDialog:
                singleDialog();
                break;
            case R.id.multiDialog:
                multiDialog();
                break;
            case R.id.loadDialog:
                loadDialog();//圆圈加载等待的dialog
                break;
            case R.id.downloadDialog:
                downloadDialog();//带有下载进度的dialog
                break;
            case R.id.editDialog:
                editDialog();
                break;
            case R.id.diyDialog:
                diyDialog();
                break;
        }
    }

    private void diyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setView(R.layout.questionnaire);
        builder.setTitle("调查问卷");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "感谢你们完成了调查问卷", Toast.LENGTH_SHORT).show();
            }
        }).setNeutralButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "真遗憾", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private void editDialog() {
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入个性签名").setView(editText);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "您的个性签名为：" + editText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }).setNeutralButton("让我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void downloadDialog() {
        final int MAX_VALUE = 100;
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgress(0);//设置初始进度
        progressDialog.setTitle("文件下载中......");//设置标题
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置样式(水平进度条)
        progressDialog.setMax(MAX_VALUE);//设置进度条最大值
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_VALUE) {
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.cancel();//下载完毕自动关闭dialog
            }
        }).start();
    }

    private void loadDialog() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIcon(R.drawable.csdn);
        progressDialog.setMessage("加载中......");
        progressDialog.setIndeterminate(true);//是否形成一个加载动画  true表示不明确加载进度形成转圈动画  false表示明确加载进度
        progressDialog.setCancelable(true);//点击返回键或者dialog四周是否关闭dialog  true表示可以关闭   false表示不可关闭
        progressDialog.show();
    }

    private void multiDialog() {
        choices = new ArrayList<>();
        AlertDialog.Builder multiBuilder = new AlertDialog.Builder(MainActivity.this);
        multiBuilder.setIcon(R.drawable.csdn);
        multiBuilder.setTitle("爱好");
        boolean[] checkedItems = {false, true, false, false, false, false};//android会自动根据你选择的改变该数组的值。

        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i] == true) {
                choices.add(i);
            }
        }

        multiBuilder.setMultiChoiceItems(hobby, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    choices.add(which);
                    Toast.makeText(MainActivity.this, "选择了" + hobby[which], Toast.LENGTH_SHORT).show();
                } else {
                    choices.remove(choices.indexOf(which));
                    Toast.makeText(MainActivity.this, "取消了" + hobby[which], Toast.LENGTH_SHORT).show();
                }
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = "您选择了：";
                for (Integer item : choices) {
                    s += hobby[item] + "、";
                }
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        }).setNeutralButton("再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "让我再想想", Toast.LENGTH_LONG).show();
            }
        }).show();
    }

    private void singleDialog() {
        choice = 0;//默认选择第一个
        AlertDialog.Builder singleBuilder = new AlertDialog.Builder(MainActivity.this);
        singleBuilder.setIcon(R.drawable.csdn);//图标
        singleBuilder.setTitle("性别");
        /**
         * 1.items：定义的单选选项数组
         * 2.checkedItem：默认被选中的选项。默认不选中为-1，选中数组第一个为0，选中数组第二个为1，以此类推
         * 3.listener：监听器
         */
        singleBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
                Toast.makeText(MainActivity.this, "点击了" + items[which], Toast.LENGTH_SHORT).show();
            }
        });
        //交互按钮栏
        //确定按钮（标签，监听器）
        singleBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "选择了" + items[choice], Toast.LENGTH_SHORT).show();
            }
        });
        //添加取消按钮
        singleBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        singleBuilder.create().show();
    }

    private void dialog() {
        //通过AlertDialog.Builder实例化一个AlertDialog对象
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //设置标题
        builder.setTitle("警告");
        //设置图标
        builder.setIcon(R.drawable.csdn);
        //设置提示信息内容
        builder.setMessage("确定要退出登录吗？");
        //确定按钮（标签，监听器）
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        //取消按钮（标签，监听器）
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "点击了取消", Toast.LENGTH_SHORT).show();
            }
        });
        //忽略按钮（标签，监听器）
        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "点击了忽略", Toast.LENGTH_SHORT).show();
            }
        });
        //显示弹窗
        builder.create().show();
    }
}
