package com.example.admin.musicclassroom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public abstract class mFragment extends Fragment {
	protected View rootView;
	private boolean isInitView = false;
	private boolean isVisible = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(setContentView(), container, false);
		init();
		isInitView = true;
		isCanLoadData();
		return rootView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		//isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
		if(isVisibleToUser){
			Toast.makeText(getActivity(),"可见",Toast.LENGTH_SHORT).show();
			isVisible = true;
			isCanLoadData();
		}else{
			isVisible = false;
		}
	}

	private void isCanLoadData(){
		//所以条件是view初始化完成并且对用户可见
		if(isInitView && isVisible ){
			lazyLoad();

			//防止重复加载数据
			isInitView = false;
			isVisible = false;
		}
	}

	/**
	 * 加载页面布局文件
	 * @return
	 */
	protected abstract int setContentView();

	/**
	 * 让布局中的view与fragment中的变量建立起映射
	 */
	protected abstract void init();

	/**
	 * 加载要显示的数据
	 */
	protected abstract void lazyLoad();

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
