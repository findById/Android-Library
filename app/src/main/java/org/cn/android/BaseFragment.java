package org.cn.android;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;

    public boolean onBackPressed() {
        return false;
    }

    protected void showLoading(String title, String message, boolean cancelable) {
        hideLoading();
        mProgressDialog = ProgressDialog.show(getActivity(), title, message, true, cancelable);
    }

    protected void hideLoading() {
        if (mProgressDialog != null && !isDetached() && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
