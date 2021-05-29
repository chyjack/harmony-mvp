package com.chy.demo.mvp.base;

import com.chy.demo.mvp.utils.Logger;
import com.chy.demo.mvp.utils.ThreadUtil;
import com.chy.demo.mvp.utils.Toast;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Author: chy
 * Description: MVP框架基础View-AbilitySlice
 * Date: 2021-03-14
 */
@SuppressWarnings("unchecked")
public abstract class BaseAbilitySlice<T extends BasePresenter> extends AbilitySlice implements IBaseView {

    private T presenter;

    // region lifecycle
    // AbilitySlice创建完成，对应Android Fragment的onCreate
    @Override
    protected final void onStart(Intent intent) {
        super.onStart(intent);
        Logger.d(getLocalClassName() + " onStart");
        getPresenter().onCreate(this);
        int layout = getLayout();
        if (layout > 0) {
            setUIContent(layout);
        }
        initView(intent);
    }

    // AbilitySlice处于前台，对应Android Fragment的onStart
    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        Logger.d(getLocalClassName() + " onForeground");
    }

    // AbilitySlice处于前台可交互，对应Android Fragment的onResume
    @Override
    protected void onActive() {
        super.onActive();
        Logger.d(getLocalClassName() + " onActive");
    }

    // AbilitySlice由前台可交互切换为不可交互，对应Android Fragment的onPause
    @Override
    protected void onInactive() {
        super.onInactive();
        Logger.d(getLocalClassName() + " onInactive");
    }

    // AbilitySlice由前台切换为后台，对应Android Fragment的onStop
    @Override
    protected void onBackground() {
        super.onBackground();
        Logger.d(getLocalClassName() + " onBackground");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(getLocalClassName() + " onStop");
        ThreadUtil.removeRunnable(this);
        getPresenter().onDestroy();
    }
    // endregion

    @Override
    public void finish() {
        terminate();
    }

    protected T getPresenter() {
        if (presenter == null) {
            try {
                Type type = getClass().getGenericSuperclass();
                if (type instanceof ParameterizedType) {
                    Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
                    presenter = ((Class<T>) generics[0]).newInstance();
                } else {
                    throw new RuntimeException("BaseAbilitySlice子类必须指定泛型类型");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return presenter;
    }

    @Override
    public void showToast(final String message, final Object... args) {
        ThreadUtil.runOnUiThread(this, () -> {
            String msg;
            if (args == null || args.length == 0) {
                msg = message;
            } else {
                msg = String.format(message, args);
            }
            Toast.show(getCurrentContext(), msg);
        });
    }

    @Override
    public void showToast(final int resId, final Object... args) {
        ThreadUtil.runOnUiThread(this, () -> {
            String msg = getString(resId, args);
            Toast.show(getCurrentContext(), msg);
        });
    }

    @Override
    public Ability getCurrentContext() {
        return this.getAbility();
    }

    @Override
    public <V extends Component> V findViewById(int id) {
        return (V) findComponentById(id);
    }

    @Override
    protected final void onBackPressed() {
        if (!interceptBackPressed())
            super.onBackPressed();
    }

    protected boolean interceptBackPressed() {
        return false;
    }

    protected abstract int getLayout();
    protected abstract void initView(Intent intent);
}
