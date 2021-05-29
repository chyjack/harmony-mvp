package com.chy.demo.mvp.utils;

import ohos.app.Context;
import ohos.global.configuration.DeviceCapability;
import ohos.global.resource.Element;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

import java.io.IOException;

/**
 * Author: CaiHuangyu
 * Description: 鸿蒙系统资源工具类
 * Date: 2021-03-14
 */
public class ResourceUtils {

    public static int vpToPx(Context context, int vp) {
        int density = context.getResourceManager().getDeviceCapability().screenDensity / DeviceCapability.SCREEN_MDPI;
        return vp * density;
    }
}
