package com.chy.demo.mvp.base;

import ohos.aafwk.ability.Ability;
import ohos.agp.components.Component;

public interface IBaseView {
    void finish();
    Ability getCurrentContext();
    void showToast(String message, Object... args);
    void showToast(int resId, Object... args);
    <T extends Component> T findViewById(int id);
}