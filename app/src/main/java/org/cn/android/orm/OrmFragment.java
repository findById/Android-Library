package org.cn.android.orm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.cn.android.BaseFragment;
import org.cn.android.R;
import org.cn.orm.Query;
import org.cn.orm.SimpleOrmHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrmFragment extends BaseFragment {
    public static final String ID = OrmFragment.class.getSimpleName();

    public static final String DATABASE_NAME = "test.db";

    private SimpleOrmHelper mOrmHelper;

    private EditText mCountView;
    private TextView mResultView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mResultView = (TextView) view.findViewById(R.id.result);
        mCountView = (EditText) view.findViewById(R.id.count_view);
        view.findViewById(R.id.find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });
        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOrm();
    }

    private void initOrm() {
        SimpleOrmHelper.Builder builder = SimpleOrmHelper.create().addAnnotatedClass(City.class);
        mOrmHelper = builder.build(getActivity(), DATABASE_NAME, 1);
    }

    public void save() {
        showLoading(null, "Loading...", false);
        List<City> list = new ArrayList<>();
        int size = 1000;
        String count = mCountView.getText().toString();
        if (!TextUtils.isEmpty(count)) {
            size = new BigDecimal(count).intValue();
        }

        for (int i = 0; i < size; i++) {
            list.add(new City("name" + i, "code" + i));
        }

        long beginTime = System.currentTimeMillis();
        mOrmHelper.saveAll(list.toArray());
        long total = System.currentTimeMillis() - beginTime;
        Log.d("ORM", "saved " + list.size() + " item, used " + total + "ms.");

        mResultView.append("saved " + list.size() + " item, used " + total + "ms.\r\n");
        hideLoading();
    }

    public void find() {
        showLoading("Title", "Loading...", false);
        int size = 1000;

        String count = mCountView.getText().toString();
        if (!TextUtils.isEmpty(count)) {
            size = new BigDecimal(count).intValue();
        }

        long beginTime = System.currentTimeMillis();
        Query query = mOrmHelper.createQuery("SELECT * FROM city LIMIT 0," + size).addEntity(City.class);
        List<City> list = query.list();
        long total = System.currentTimeMillis() - beginTime;
        Log.d("ORM", "find " + list.size() + " item, used " + total + "ms.");
        Log.e("ORM", JSON.toJSONString(list));
        mResultView.append("find " + list.size() + " item, used " + total + "ms.\r\n");
        hideLoading();
    }

    public void clear() {
        showLoading(null, "Loading...", false);
        long beginTime = System.currentTimeMillis();
        Query query = mOrmHelper.createQuery("DELETE FROM city").addEntity(City.class);
        query.executeUpdate();
        long total = System.currentTimeMillis() - beginTime;
        Log.d("ORM", "clear all item, used " + total + "ms.");

        mResultView.setText("clear all item, used " + total + "ms.\r\n");
        hideLoading();
    }
}
