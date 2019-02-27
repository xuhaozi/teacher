package com.example.admin.musicclassroom;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/1/5.
 */
public class mFragmentActivity extends FragmentActivity {

    public boolean isDataChanged = false;

//    private ProgressDialog progressDialog;
    private Dialog dialog;
    /**
     * 显示提示信息
     * @param msg
     */
    public void showMessage(String msg){
        if(msg!=null&&!msg.equals("")) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    /**
     * 取消等待框的显示
     */
    public void dismissProgressDialog(){
        //如果progressDialog不为空并且正在显示，则取消
        if(dialog != null && dialog.isShowing())dialog.dismiss();
//        if(progressDialog != null && progressDialog.isShowing())progressDialog.dismiss();
    }

    /**
     * 根据是否修改了内容，设定返回值
     * 并且返回
     */
    public void setResultAndBack(){
        //如果数据修改为true，则设定为OK
        //否则，设定为Canceled
        if(isDataChanged)setResult(RESULT_OK);
        else setResult(RESULT_CANCELED);
        this.finish();
    }

    /**
     * 手机自带返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果用户点击了返回键
        //根据是否修改了内容，设定返回值返回
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setResultAndBack();
        }
        return super.onKeyDown(keyCode, event);
    }
}
