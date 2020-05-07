package com.hiwitech.android.widget.qmui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;

import com.hiwitech.android.libs.qmui.QMUIDisplayHelper;

/**
 * @author cginechen
 * @date 2016-09-22
 */
public class QMUIResHelper {

    public static float getAttrFloatValue(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return context.getColorStateList(typedValue.resourceId);
    }

    public static int getAttrDimen(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, QMUIDisplayHelper.getDisplayMetrics(context));
    }

}
