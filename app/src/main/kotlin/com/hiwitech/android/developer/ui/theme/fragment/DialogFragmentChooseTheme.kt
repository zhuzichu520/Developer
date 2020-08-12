package com.hiwitech.android.developer.ui.theme.fragment

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.app.ActivityCompat
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.DialogFragmentChooseThemeBinding
import com.hiwitech.android.developer.ui.theme.viewmodel.ViewModelTheme
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseBottomDialogFragment
import com.hiwitech.android.shared.route.RoutePath
import com.hiwitech.android.shared.theme.ThemeManager
import com.hiwitech.android.shared.theme.ThemeStorage
import kotlinx.android.synthetic.main.dialog_fragment_choose_theme.*

@Route(path = RoutePath.DIALOGFRAGMENT_CHOOSETHEME)
class DialogFragmentChooseTheme :
    BaseBottomDialogFragment<DialogFragmentChooseThemeBinding, ViewModelTheme, ArgDefault>(),
    RadioGroup.OnCheckedChangeListener {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.dialog_fragment_choose_theme

    override fun initView() {
        super.initView()
        initializeThemingValues(
            primary_colors,
            ThemeManager.getPrimaryColors(),
            ThemeManager.getPrimaryColorsContentDescription(),
            ThemeManager.getPrimaryThemeOverlayAttrs(),
            R.id.theme_feature_primary_color,
            ThemingType.COLOR
        )

        initializeThemingValues(
            secondary_colors,
            ThemeManager.getSecondaryColors(),
            ThemeManager.getSecondaryColorsContentDescription(),
            ThemeManager.getSecondaryThemeOverlayAttrs(),
            R.id.theme_feature_secondary_color,
            ThemingType.COLOR
        )

        initializeThemingValues(
            shape_families,
            ThemeManager.getShapes(),
            ThemeManager.getShapesContentDescription(),
            R.id.theme_feature_corner_family,
            ThemingType.SHAPE_CORNER_FAMILY
        )

        initializeThemingValues(
            shape_corner_sizes,
            ThemeManager.getShapeSizes(),
            ThemeManager.getShapeSizesContentDescription(),
            R.id.theme_feature_corner_size,
            ThemingType.SHAPE_CORNER_SIZE
        )
    }

    override fun initListener() {
        viewModel.checkedButton.observe(
            viewLifecycleOwner,
            Observer {
                ThemeManager.saveAndApplyTheme(it)
            }
        )

        primary_colors.setOnCheckedChangeListener(this)
        secondary_colors.setOnCheckedChangeListener(this)
        shape_families.setOnCheckedChangeListener(this)
        shape_corner_sizes.setOnCheckedChangeListener(this)

        clear_button.setOnClickListener {
            dismiss()
            ThemeManager.clearThemeOverlays(requireActivity())
        }
    }

    private fun initializeThemingValues(
        group: RadioGroup,
        @ArrayRes overlays: Int,
        @ArrayRes contentDescriptions: Int,
        @IdRes overlayId: Int,
        themingType: ThemingType
    ) {
        initializeThemingValues(
            group, overlays, contentDescriptions, intArrayOf(), overlayId, themingType
        )
    }

    private fun initializeThemingValues(
        group: RadioGroup,
        @ArrayRes overlays: Int,
        @ArrayRes contentDescriptions: Int,
        @StyleableRes themeOverlayAttrs: IntArray,
        @IdRes overlayId: Int,
        themingType: ThemingType
    ) {
        val themingValues = resources.obtainTypedArray(overlays)
        val contentDescriptionArray = resources.obtainTypedArray(contentDescriptions)
        require(themingValues.length() == contentDescriptionArray.length()) { "Feature array length doesn't match its content description array length." }
        for (i in 0 until themingValues.length()) {
            @StyleRes val valueThemeOverlay = themingValues.getResourceId(i, 0)
            var themeAttributeValues: ThemeAttributeValues?
            themeAttributeValues = when (themingType) {
                ThemingType.COLOR -> ColorPalette(valueThemeOverlay, themeOverlayAttrs, i)
                ThemingType.SHAPE_CORNER_FAMILY -> ThemeAttributeValues(valueThemeOverlay, i)
                ThemingType.SHAPE_CORNER_SIZE -> ThemeAttributeValuesWithContentDescription(
                    valueThemeOverlay,
                    contentDescriptionArray.getString(i).toString(),
                    i
                )
            }
            val button =
                if (themingType.radioButtonType == RadioButtonType.XML) group.getChildAt(i) as AppCompatRadioButton else createCompatRadioButton(
                    group,
                    contentDescriptionArray.getString(i).toString()
                )
            button.tag = themeAttributeValues
            themeAttributeValues.customizeRadioButton(button)
            val currentThemeOverlay: Int = ThemeManager.getThemeOverlay(overlayId)
            if (themeAttributeValues.themeOverlay == currentThemeOverlay) {
                group.check(button.id)
            }
        }
        themingValues.recycle()
        contentDescriptionArray.recycle()
    }

    private fun createCompatRadioButton(
        group: RadioGroup,
        contentDescription: String
    ): AppCompatRadioButton {
        val button = AppCompatRadioButton(context)
        button.contentDescription = contentDescription
        group.addView(button)
        return button
    }

    private fun applyThemeOverlays() {
        val themesMap = arrayOf(
            intArrayOf(
                R.id.theme_feature_primary_color,
                getThemeOverlayResId(primary_colors, R.id.theme_feature_primary_color)
            ),
            intArrayOf(
                R.id.theme_feature_secondary_color,
                getThemeOverlayResId(secondary_colors, R.id.theme_feature_secondary_color)
            ),
            intArrayOf(
                R.id.theme_feature_corner_family,
                getThemeOverlayResId(shape_families, R.id.theme_feature_corner_family)
            ),
            intArrayOf(
                R.id.theme_feature_corner_size,
                getThemeOverlayResId(shape_corner_sizes, R.id.theme_feature_corner_size)
            )
        )
        for (i in themesMap.indices) {
            ThemeManager.setThemeOverlay(themesMap[i][0], themesMap[i][1])
        }
        ActivityCompat.recreate(requireActivity())
    }

    private fun getThemeOverlayResId(
        radioGroup: RadioGroup,
        overlayId: Int
    ): Int {
        if (radioGroup.checkedRadioButtonId == View.NO_ID) {
            return 0
        }
        val overlayFeature =
            requireDialog().findViewById<View>(radioGroup.checkedRadioButtonId).tag as ThemeAttributeValues
        val index = overlayFeature.index
        when (overlayId) {
            R.id.theme_feature_primary_color -> {
                ThemeStorage.primarColorIndex = index
            }
            R.id.theme_feature_secondary_color -> {
                ThemeStorage.secondaryColorIndex = index
            }
            R.id.theme_feature_corner_family -> {
                ThemeStorage.cornerFamilyIndex = index
            }
            R.id.theme_feature_corner_size -> {
                ThemeStorage.cornerSizeIndex = index
            }
        }
        return overlayFeature.themeOverlay
    }

    open class ThemeAttributeValues constructor(
        @StyleRes internal val themeOverlay: Int,
        internal val index: Int
    ) {
        open fun customizeRadioButton(button: AppCompatRadioButton) {}
    }

    class ThemeAttributeValuesWithContentDescription constructor(
        @StyleRes themeOverlay: Int,
        private val contentDescription: String,
        index: Int
    ) : ThemeAttributeValues(themeOverlay, index) {
        override fun customizeRadioButton(button: AppCompatRadioButton) {
            button.text = contentDescription
            val size = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            MarginLayoutParamsCompat.setMarginEnd(
                size,
                button.resources.getDimensionPixelSize(R.dimen.theme_switcher_radio_spacing)
            )
            button.layoutParams = size
        }
    }

    inner class ColorPalette constructor(
        @StyleRes themeOverlay: Int,
        @StyleableRes themeOverlayAttrs: IntArray,
        index: Int
    ) :
        ThemeAttributeValues(themeOverlay, index) {
        @ColorInt
        private val main: Int

        override fun customizeRadioButton(button: AppCompatRadioButton) {
            CompoundButtonCompat.setButtonTintList(
                button, ColorStateList.valueOf(convertToDisplay(main))
            )
        }

        @ColorInt
        private fun convertToDisplay(@ColorInt color: Int): Int {
            return if (color == Color.WHITE) Color.BLACK else color
        }

        init {
            val a: TypedArray =
                requireContext().obtainStyledAttributes(themeOverlay, themeOverlayAttrs)
            main = a.getColor(0, Color.TRANSPARENT)
            a.recycle()
        }
    }

    private enum class RadioButtonType {
        DEFAULT, XML
    }

    private enum class ThemingType(type: RadioButtonType) {

        COLOR(RadioButtonType.DEFAULT), SHAPE_CORNER_FAMILY(
            RadioButtonType.XML
        ),

        SHAPE_CORNER_SIZE(RadioButtonType.DEFAULT);

        internal val radioButtonType: RadioButtonType = type
    }

    override fun onCheckedChanged(radioGroup: RadioGroup?, checkedId: Int) {
        applyThemeOverlays()
    }
}
