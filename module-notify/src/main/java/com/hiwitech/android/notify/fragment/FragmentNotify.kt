package com.hiwitech.android.notify.fragment

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.shared.base.FragmentBase
import com.hiwitech.android.libs.tool.setOnClickListener
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.notify.R
import com.hiwitech.android.notify.BR
import com.hiwitech.android.notify.BubbleActivity
import com.hiwitech.android.notify.databinding.FragmentNotifyBinding
import com.hiwitech.android.notify.viewmodel.ViewModelNotify
import com.hiwitech.android.shared.route.RoutePath
import com.hiwitech.android.widget.notify.Notify
import kotlinx.android.synthetic.main.fragment_notify.*
import java.util.*

/**
 * desc
 * author: 朱子楚
 * time: 2020/6/17 9:30 AM
 * since: v 1.0.0
 */
@Route(path = RoutePath.FRAGMENT_NOTIFY)
class FragmentNotify : FragmentBase<FragmentNotifyBinding, ViewModelNotify, ArgDefault>(),
    View.OnClickListener {

    override fun setLayoutId(): Int = R.layout.fragment_notify

    override fun bindVariableId(): Int = BR.viewModel


    override fun initListener() {
        super.initListener()
        setOnClickListener(
            this,
            notifyDefault,
            notifyTextList,
            notifyBigText,
            notifyBigPicture,
            notifyMessage,
            notifyBubble,
            notifyIndeterminateProgress,
            notifyDeterminateProgress
        )
    }


    fun _notifyDefault() {
        Notify.with(requireContext())
            .meta {
                category=NotificationCompat.CATEGORY_CALL
            }
            .alerting("channelKey") {
                this.channelName = "弹出通知"
                this.channelDescription = "用于弹出信息"
                this.lightColor = Color.GREEN
                this.lockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            .content {
                title = "New dessert menu"
                text = "The Cheesecake Factory has a new dessert for you to try!"
            }
            .show()

    }

    fun _notifyTextList() {
        Notify
            .with(requireContext())
            .asTextList {
                lines = Arrays.asList(
                    "New! Fresh Strawberry Cheesecake.",
                    "New! Salted Caramel Cheesecake.",
                    "New! OREO Dream Dessert."
                )
                title = "New menu items!"
                text = lines.size.toString() + " new dessert menu items found."
            }
            .show()

    }

    fun _notifyBigText() {
        Notify
            .with(requireContext())
            .asBigText {
                title = "Chocolate brownie sundae"
                text = "Try our newest dessert option!"
                expandedText = "Mouthwatering deliciousness."
                bigText =
                    "Our own Fabulous Godiva Chocolate Brownie, Vanilla Ice Cream, Hot Fudge, Whipped Cream and Toasted Almonds.\n" +
                            "\n" +
                            "Come try this delicious new dessert and get two for the price of one!"
            }
            .show()
    }

    fun _notifyBigPicture() {
        Notify
            .with(requireContext())
            .asBigPicture {
                title = "Chocolate brownie sundae"
                text = "Get a look at this amazing dessert!"
                expandedText = "The delicious brownie sundae now available."
                image = BitmapFactory.decodeResource(
                    requireActivity().resources,
                    R.drawable.chocolate_brownie_sundae
                )
            }
            .show()
    }

    fun _notifyMessage() {
        Notify
            .with(requireContext())
            .asMessage {
                userDisplayName = "Karn"
                conversationTitle = "Sundae chat"
                messages = Arrays.asList(
                    NotificationCompat.MessagingStyle.Message(
                        "Are you guys ready to try the Strawberry sundae?",
                        System.currentTimeMillis() - (6 * 60 * 1000), // 6 Mins ago
                        "Karn"
                    ),
                    NotificationCompat.MessagingStyle.Message(
                        "Yeah! I've heard great things about this place.",
                        System.currentTimeMillis() - (5 * 60 * 1000), // 5 Mins ago
                        "Nitish"
                    ),
                    NotificationCompat.MessagingStyle.Message(
                        "What time are you getting there Karn?",
                        System.currentTimeMillis() - (1 * 60 * 1000), // 1 Mins ago
                        "Moez"
                    )
                )
            }
            .show()
    }

    fun _notifyBubble() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Toast.makeText(
                requireContext(),
                "Notification Bubbles are only supported on a device running Android Q or later.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Notify
            .with(requireContext())
            .content {
                title = "New dessert menu"
                text = "The Cheesecake Factory has a new dessert for you to try!"
            }
            .bubblize {
                // Create bubble intent
                val target = Intent(requireActivity(), BubbleActivity::class.java)
                val bubbleIntent =
                    PendingIntent.getActivity(requireActivity(), 0, target, 0 /* flags */)
                bubbleIcon =
                    IconCompat.createWithResource(requireActivity(), R.drawable.ic_default_notify)
                targetActivity = bubbleIntent
                suppressInitialNotification = true
            }
            .show()
    }

    fun _notifyIndeterminateProgress() {

        Notify
            .with(requireContext())
            .asBigText {
                title = "Uploading files"
                expandedText = "The files are being uploaded!"
                bigText = "Daft Punk - Get Lucky.flac is uploading to server /music/favorites"
            }
            .progress {
                showProgress = true
            }
            .show()
    }

    fun _notifyDeterminateProgress() {

        Notify
            .with(requireContext())
            .asBigText {
                title = "Bitcoin payment processing"
                expandedText = "Your payment was sent to the Bitcoin network"
                bigText = "Your payment #0489 is being confirmed 2/4"
            }
            .progress {
                showProgress = true
                enablePercentage = true
                progressPercent = 30
            }
            .show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.notifyBigPicture -> {
                _notifyBigPicture()
            }
            R.id.notifyBigText -> {
                _notifyBigText()
            }
            R.id.notifyBubble -> {
                _notifyBubble()
            }
            R.id.notifyDefault -> {
                _notifyDefault()
            }
            R.id.notifyDeterminateProgress -> {
                _notifyDeterminateProgress()
            }
            R.id.notifyIndeterminateProgress -> {
                _notifyIndeterminateProgress()
            }
            R.id.notifyMessage -> {
                _notifyMessage()
            }
            R.id.notifyTextList -> {
                _notifyTextList()
            }
        }
    }

}