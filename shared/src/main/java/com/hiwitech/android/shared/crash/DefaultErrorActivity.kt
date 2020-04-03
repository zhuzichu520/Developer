package com.hiwitech.android.shared.crash

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.fileChooser
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.autoDispose
import com.hiwitech.android.libs.tool.getFileByPath
import com.hiwitech.android.shared.R
import com.hiwitech.android.shared.ext.toast
import com.hiwitech.android.shared.global.CacheGlobal
import com.hiwitech.android.shared.tools.OpenAnyFileTool


class DefaultErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_error)
        val restartButton: Button = findViewById(R.id.error_activity_restart_button)

        val config: CrashConfig? = CrashManager.getConfigFromIntent(intent)
        if (config == null) {
            finish()
            return
        }

        if (config.showRestartButton && config.restartActivityClass != null) {
            restartButton.setText(R.string.error_activity_restart_app)
            restartButton.setOnClickListener {
                CrashManager.restartApplication(this@DefaultErrorActivity, config)
            }
        } else {
            restartButton.setOnClickListener {
                CrashManager.closeApplication(this@DefaultErrorActivity, config)
            }
        }

        val moreInfoButton: Button = findViewById(R.id.error_activity_more_info_button)

        if (config.showErrorDetails) {
            moreInfoButton.setOnClickListener {
                val dialog: AlertDialog = AlertDialog.Builder(this@DefaultErrorActivity)
                    .setTitle(R.string.error_activity_error_details_title)
                    .setMessage(
                        CrashManager.getAllErrorDetailsFromIntent(
                            this@DefaultErrorActivity,
                            intent
                        )
                    )
                    .setPositiveButton(
                        R.string.error_activity_error_details_close,
                        null
                    )
                    .setNeutralButton(R.string.error_activity_error_details_copy) { _, _ ->
                        copyErrorToClipboard()
                    }.show()
                dialog.findViewById<TextView>(R.id.message)?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.error_activity_error_details_text_size)
                )
            }
        } else {
            moreInfoButton.visibility = View.GONE
        }

        val defaultErrorActivityDrawableId: Int? = config.errorDrawable
        val errorImageView: ImageView = findViewById(R.id.error_activity_image)

        if (defaultErrorActivityDrawableId != null) {
            errorImageView.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    defaultErrorActivityDrawableId,
                    theme
                )
            )
        }

        findViewById<Button>(R.id.error_activity_file_button).setOnClickListener {
            MaterialDialog(this).show {
                fileChooser(
                    this@DefaultErrorActivity,
                    initialDirectory = getFileByPath(CacheGlobal.getLogCacheDir())
                ) { _, file ->
                    RxPermissions(this@DefaultErrorActivity).request(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).autoDispose(this@DefaultErrorActivity)
                        .subscribe { granted ->
                            if (granted) {
                                OpenAnyFileTool.openFile(this@DefaultErrorActivity, file)
                            } else {
                                "权限被拒绝".toast()
                            }
                        }

                }
            }
        }

    }


    private fun copyErrorToClipboard() {
        val errorInformation: String? = CrashManager.getAllErrorDetailsFromIntent(
            this@DefaultErrorActivity,
            intent
        )
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //Are there any devices without clipboard...?
        val clip: ClipData = ClipData.newPlainText(
            getString(R.string.error_activity_error_details_clipboard_label),
            errorInformation
        )
        clipboard.setPrimaryClip(clip)
        Toast.makeText(
            this@DefaultErrorActivity,
            R.string.error_activity_error_details_copied,
            Toast.LENGTH_SHORT
        ).show()
    }
}