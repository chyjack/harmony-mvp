package com.chy.demo.mvp.utils;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

/**
 * Author: chy
 * Description: 自定义Toast
 * Date: 2021-03-14
 */
public class Toast {

    public static void show(Context context, String message) {
        obtainToastDialog(context, message).show();
    }

    private static ToastDialog obtainToastDialog(Context context, String text) {
        Text textComponent = new Text(context);
        textComponent.setText(text);
        int padding = ResourceUtils.vpToPx(context, 10);
        //设置间距为10vp
        textComponent.setPadding(padding, padding, padding, padding);
        textComponent.setTextColor(Color.WHITE);
        textComponent.setTextAlignment(TextAlignment.CENTER);
        textComponent.setTextSize(ResourceUtils.vpToPx(context, 14));
        ShapeElement shapeElement = new ShapeElement();
        shapeElement.setShape(ShapeElement.RECTANGLE);
        shapeElement.setCornerRadius(ResourceUtils.vpToPx(context, 5));
        //设置背景半透明
        shapeElement.setRgbColor(RgbColor.fromArgbInt(Color.argb(127, 0, 0, 0)));
        textComponent.setBackground(shapeElement);
        //设置文字允许多行
        textComponent.setMultipleLine(true);
        textComponent.setMinWidth(ResourceUtils.vpToPx(context, 120));

        DirectionalLayout.LayoutConfig layoutConfig = new DirectionalLayout.LayoutConfig();
        layoutConfig.width = ComponentContainer.LayoutConfig.MATCH_CONTENT;
        layoutConfig.height = ComponentContainer.LayoutConfig.MATCH_CONTENT;
        layoutConfig.alignment = LayoutAlignment.CENTER;
        textComponent.setLayoutConfig(layoutConfig);

        ToastDialog toastDialog =  new ToastDialog(context)
                .setDuration(500)
                .setComponent(textComponent)
                .setAlignment(LayoutAlignment.CENTER);
        //设置弹框背景透明
        toastDialog.setTransparent(true);
        return toastDialog;
    }
}
