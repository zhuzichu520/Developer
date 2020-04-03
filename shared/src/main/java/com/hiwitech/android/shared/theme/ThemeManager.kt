package com.hiwitech.android.shared.theme

import android.app.Activity
import android.content.Context
import android.util.SparseIntArray
import androidx.annotation.*
import androidx.appcompat.app.AppCompatDelegate
import com.hiwitech.android.shared.R

object ThemeManager {

    private val themeOverlays = SparseIntArray()
    private val THEME_NIGHT_MODE_MAP = SparseIntArray()

    init {

        THEME_NIGHT_MODE_MAP.append(
            R.id.theme_light,
            AppCompatDelegate.MODE_NIGHT_NO
        )

        THEME_NIGHT_MODE_MAP.append(
            R.id.theme_dark,
            AppCompatDelegate.MODE_NIGHT_YES
        )

        THEME_NIGHT_MODE_MAP.append(
            R.id.theme_default,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

    }

    fun initTheme(context: Context) {

        val colorPrimary = context.resources.obtainTypedArray(getPrimaryColors())
        val primarColorIndex = ThemeStorage.primarColorIndex

        if (primarColorIndex != -1) {
            setThemeOverlay(
                R.id.theme_feature_primary_color,
                colorPrimary.getResourceId(primarColorIndex, 0)
            )
        }
        colorPrimary.recycle()

        val colorSecondary = context.resources.obtainTypedArray(getSecondaryColors())
        val secondaryColorIndex = ThemeStorage.secondaryColorIndex
        if (secondaryColorIndex != -1) {
            setThemeOverlay(
                R.id.theme_feature_secondary_color,
                colorSecondary.getResourceId(secondaryColorIndex, 0)
            )
        }
        colorSecondary.recycle()

        val cornerFamily = context.resources.obtainTypedArray(getShapes())
        val cornerFamilyIndex = ThemeStorage.cornerFamilyIndex
        if (cornerFamilyIndex != -1) {
            setThemeOverlay(
                R.id.theme_feature_corner_family,
                cornerFamily.getResourceId(cornerFamilyIndex, 0)
            )
        }
        cornerFamily.recycle()

        val cornerSize = context.resources.obtainTypedArray(getShapeSizes())
        val cornerSizeIndex = ThemeStorage.cornerSizeIndex
        if (cornerSizeIndex != -1) {
            setThemeOverlay(
                R.id.theme_feature_corner_size,
                cornerSize.getResourceId(cornerSizeIndex, 0)
            )
        }
        cornerSize.recycle()
    }

    @IdRes
    fun getCurrentThemeId(): Int {
        return convertToThemeId(getNightMode())
    }

    @IdRes
    private fun convertToThemeId(nightMode: Int): Int {
        return THEME_NIGHT_MODE_MAP.keyAt(THEME_NIGHT_MODE_MAP.indexOfValue(nightMode))
    }

    fun getNightMode(): Int {
        return ThemeStorage.uiMode
    }

    fun saveAndApplyTheme(@IdRes id: Int) {
        val nightMode: Int = convertToNightMode(id)
        saveNightMode(nightMode)
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun clearThemeOverlays(activity: Activity) {
        themeOverlays.clear()
        ThemeStorage.primarColorIndex = -1
        ThemeStorage.secondaryColorIndex = -1
        ThemeStorage.cornerFamilyIndex = -1
        ThemeStorage.cornerSizeIndex = -1
        activity.recreate()
    }

    private fun convertToNightMode(@IdRes id: Int): Int {
        return THEME_NIGHT_MODE_MAP.get(id, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }


    private fun saveNightMode(nightMode: Int) {
        ThemeStorage.uiMode = nightMode
    }

    fun setThemeOverlay(@IdRes id: Int, @StyleRes themeOverlay: Int) {
        themeOverlays.put(id, themeOverlay)
    }

    fun getThemeOverlay(@IdRes id: Int): Int {
        return themeOverlays.get(id)
    }

    fun applyThemeOverlays(activity: Activity) {
        for (i in 0 until themeOverlays.size()) {
            activity.setTheme(themeOverlays.valueAt(i))
        }
    }

    @StyleableRes
    private val PRIMARY_THEME_OVERLAY_ATTRS = intArrayOf(
        R.attr.colorPrimary, R.attr.colorPrimaryDark
    )

    @StyleableRes
    private val SECONDARY_THEME_OVERLAY_ATTRS = intArrayOf(R.attr.colorSecondary)

    @StyleableRes
    fun getPrimaryThemeOverlayAttrs(): IntArray {
        return PRIMARY_THEME_OVERLAY_ATTRS
    }

    @StyleableRes
    fun getSecondaryThemeOverlayAttrs(): IntArray {
        return SECONDARY_THEME_OVERLAY_ATTRS
    }

    @AttrRes
    fun getPrimaryColor(): Int {
        return R.attr.colorPrimary
    }

    @ArrayRes
    fun getPrimaryColors(): Int {
        return R.array.mtrl_primary_palettes
    }

    @ArrayRes
    fun getSecondaryColors(): Int {
        return R.array.mtrl_secondary_palettes
    }

    @ArrayRes
    fun getPrimaryColorsContentDescription(): Int {
        return R.array.mtrl_palettes_content_description
    }

    @ArrayRes
    fun getSecondaryColorsContentDescription(): Int {
        return R.array.mtrl_palettes_content_description
    }

    fun getShapes(): Int {
        return R.array.mtrl_shape_overlays
    }

    fun getShapesContentDescription(): Int {
        return R.array.mtrl_shapes_content_description
    }

    fun getShapeSizes(): Int {
        return R.array.mtrl_shape_size_overlays
    }

    fun getShapeSizesContentDescription(): Int {
        return R.array.mtrl_shape_size_content_description
    }


}