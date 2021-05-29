package com.chy.demo.mvp.ui.p;

import com.chy.demo.mvp.base.BasePresenter;
import com.chy.demo.mvp.ui.i.LoginContract;
import com.chy.demo.mvp.ui.v.LoginSlice;

public class LoginPresenter extends BasePresenter<LoginSlice> implements LoginContract.Presenter {
    @Override
    public void login() {
        showToast("登陆");
    }
}
