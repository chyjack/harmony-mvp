package com.chy.demo.mvp.ui.v;

import com.chy.demo.mvp.base.BaseAbility;
import com.chy.demo.mvp.ui.i.MainContract;
import com.chy.demo.mvp.ui.p.MainPresenter;
import ohos.aafwk.content.Intent;

public class MainAbility extends BaseAbility<MainPresenter> implements MainContract.View {

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView(Intent intent) {
        setMainRoute(LoginSlice.class.getName());
    }
}
