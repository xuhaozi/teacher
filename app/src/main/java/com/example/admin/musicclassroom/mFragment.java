package com.example.admin.musicclassroom;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;


public class mFragment extends Fragment {

//	public ProgressDialog progressDialog;
	private Dialog dialog;
	/**
	 * 显示ProgressDialog
	 * @param msg
	 */
	/**
	 * 取消显示ProgressDialog
	 */
	public void dismissProgressDialog(){
		if(dialog != null && dialog.isShowing())dialog.dismiss();
//		if(progressDialog!=null&&progressDialog.isShowing())progressDialog.dismiss();
	}
	/**
	 * 显示提示信息
	 * @param msg
	 */
	public void showMessage(String msg){
		Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 设置Fragment的基本参数，防止重影
	 */
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null)this.getView().setVisibility(menuVisible? View.VISIBLE: View.GONE);
	}
}
