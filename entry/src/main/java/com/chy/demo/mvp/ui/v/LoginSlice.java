package com.chy.demo.mvp.ui.v;

import com.chy.demo.mvp.ResourceTable;
import com.chy.demo.mvp.base.BaseAbilitySlice;
import com.chy.demo.mvp.ui.i.LoginContract;
import com.chy.demo.mvp.ui.p.LoginPresenter;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;

public class LoginSlice extends BaseAbilitySlice<LoginPresenter> implements LoginContract.View {

    @Override
    protected int getLayout() {
        return ResourceTable.Layout_ability_main;
    }

    @Override
    protected void initView(Intent intent) {
        findViewById(ResourceTable.Id_btn_login).setClickedListener(component -> {
            getPresenter().login();
        });
    }
}
