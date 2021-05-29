package com.chy.demo.mvp.base;

import com.chy.demo.mvp.utils.ThreadUtil;
import ohos.aafwk.ability.Ability;
import ohos.agp.components.Component;

/**
 * Author: chy
 * Description: MVP框架基础View-presenter
 * Date: 2021-03-14
 */
public abstract class BasePresenter<T extends IBaseView> implements IBasePresenter {

    private T view;

    protected void onCreate(T view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        ThreadUtil.removeRunnable(this);
    }

    protected T getView() {
        return view;
    }

    protected Ability getCurrentContext() {
        return view.getCurrentContext();
    }

    protected void showToast(String msg, Object... args) {
        view.showToast(msg, args);
    }

    protected void showToast(int resId, Object... args) {
        view.showToast(resId, args);
    }

    protected void finish() {
        view.finish();
    }

    protected <V extends Component> V findViewById(int id) {
        return view.findViewById(id);
    }
}
