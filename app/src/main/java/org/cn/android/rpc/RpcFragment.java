package org.cn.android.rpc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.cn.android.BaseFragment;
import org.cn.android.R;
import org.cn.android.databinding.FragmentRpcBinding;
import org.cn.core.utils.JSONUtil;
import org.cn.rpc.ApiEngine;
import org.cn.rpc.Response;
import org.cn.rpc.ResponseListener;

public class RpcFragment extends BaseFragment {

    FragmentRpcBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rpc, container, false);
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.methodGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData("GET");
            }
        });
        mBinding.methodPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData("POST");
            }
        });
    }

    public void requestData(String method) {
        String url = mBinding.requestUrl.getText().toString();
        String body = mBinding.requestBody.getText().toString();

        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getActivity(), "URL must not be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!url.toUpperCase().startsWith("HTTP://") && !url.toUpperCase().startsWith("HTTPS://")) {
            Toast.makeText(getActivity(), "Bad url.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (method) {
            case "POST": {
                ApiEngine.post(url, body, listener);
                break;
            }
            default:
                ApiEngine.get(url, listener);
                break;
        }
    }

    ResponseListener listener = new ResponseListener<Response>() {
        @Override
        public void onResponse(Response response) {
            Log.d("RF", response.toString());
            mBinding.setResult(JSONUtil.format(response.toString()));
        }
    };

}
