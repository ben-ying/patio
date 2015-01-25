package com.yjh.qinyuan.main.aroundme;

import android.app.FragmentTransaction;
import android.os.Bundle;
import com.yjh.qinyuan.R;
import com.yjh.qinyuan.common.TabActivity;

public class AroundMeActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        init();
    }

    @Override
    public void init() {
        super.init();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        AroundMeFragment fragment = new AroundMeFragment();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
}
