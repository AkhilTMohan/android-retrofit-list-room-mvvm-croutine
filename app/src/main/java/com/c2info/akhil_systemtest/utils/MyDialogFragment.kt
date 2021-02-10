package com.c2info.akhil_systemtest.utils

import android.annotation.SuppressLint
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.c2info.akhil_systemtest.R
import com.c2info.akhil_systemtest.interfaces.CallBack

/**
 * Created by ATM on 11,December,2018
 */

class MyDialogFragment(var layout: Int? = null, var callBack: CallBack? = null) :
    DialogFragment() {
    private lateinit var dialog_type: DIALOG_TYPE
    private var titleString: String? = null
    private var messageString: String? = null
    lateinit var alertLayout: ConstraintLayout
    lateinit var progressBar: ProgressBar
    lateinit var input: EditText

    enum class DIALOG_TYPE {
        ALERT,
        ACTION,
        PROGRESS,
        WEB_VIEW,
        ACTION_WITH_EDIT_TEXT
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layout != null) {
            inflater.inflate(layout!!, container, false)
        } else inflater.inflate(R.layout.alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (layout == null) {
            val ok: TextView = view.findViewById(R.id.action_ok)
            val cancel: TextView = view.findViewById(R.id.action_cancel)
            val message: TextView = view.findViewById(R.id.message)
            val title: TextView = view.findViewById(R.id.title)
            if (arguments != null) {
                dialog_type = requireArguments().getSerializable("dialog_type") as DIALOG_TYPE
                titleString = arguments?.getString("title")
                messageString = arguments?.getString("message")
            }
            alertLayout = view.findViewById(R.id.alert_layout)
            progressBar = view.findViewById(R.id.progress_bar)
            input = view.findViewById(R.id.input)
            title.text = titleString
            message.text = messageString
            when (dialog_type) {
                DIALOG_TYPE.ALERT -> {
                    fragment.isCancelable = false
                    cancel.visibility = GONE
                    ok.setOnClickListener { v ->
                        callBack?.onClick(v)
                        dismiss()
                    }
                }
                DIALOG_TYPE.ACTION -> {
                    ok.setOnClickListener {
                        callBack?.onClick(dialog!!)
                        Companion.callBack?.onClick(dialog!!)
                        dismiss()
                    }
                    cancel.setOnClickListener { dismiss() }
                }
                DIALOG_TYPE.PROGRESS -> {
                    if (dialog!!.window != null) {
                        dialog!!.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
                    }
                    alertLayout.visibility = GONE
                    progressBar.visibility = View.VISIBLE
                }

                DIALOG_TYPE.WEB_VIEW -> {
                    val USER_AGENT =
                        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"
                    if (dialog!!.window != null) {
                        setStyle(
                            STYLE_NORMAL,
                            android.R.style.Theme_Black_NoTitleBar_Fullscreen
                        )
                    }
                    alertLayout.visibility = GONE
                    progressBar.visibility = GONE
                }
                DIALOG_TYPE.ACTION_WITH_EDIT_TEXT -> {
                    input.visibility = View.VISIBLE
                    ok.setOnClickListener {
                        if (TextUtils.isEmpty(input.text.toString()) || Integer.parseInt(input.text.toString()) == 0) {
                            Toast.makeText(context, "Count cannot be empty/0", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            callBack?.onClick(input.text.toString())
                            dialog!!.dismiss()
                        }
                    }
                    cancel.setOnClickListener { dismiss() }
                }
            }
        } else {
            when (layout) {
                //callBack?.onClick(spinner.selectedItem)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window?.attributes = params as android.view.WindowManager.LayoutParams
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.d("ABSDIALOGFRAG", "Exception", e)
        }
    }


    companion object {
        var callBack: CallBack? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var fragment: MyDialogFragment

        fun newInstance(
            dialog_type: DIALOG_TYPE,
            title: String?,
            message: String?,
            callBack: CallBack?
        ): MyDialogFragment {
            val args = Bundle()
            args.putSerializable("dialog_type", dialog_type)
            args.putString("title", title)
            args.putString("message", message)
            fragment = MyDialogFragment()
            fragment.arguments = args
            fragment.setStyle(STYLE_NORMAL, R.style.customDialog)
            Companion.callBack = callBack
            return fragment
        }

        fun newInstance(
            dialog_type: DIALOG_TYPE,
            title: String?,
            message: String?,
            callBack: CallBack?, isCancelable: Boolean
        ): MyDialogFragment {
            val args = Bundle()
            args.putSerializable("dialog_type", dialog_type)
            args.putString("title", title)
            args.putString("message", message)
            fragment = MyDialogFragment()
            fragment.arguments = args
            fragment.isCancelable = isCancelable
            fragment.setStyle(STYLE_NORMAL, R.style.customDialog)
            Companion.callBack = callBack
            return fragment
        }

        fun dismissCustomProgressDialog() {
            try {
                fragment.dismiss()
            } catch (e: Exception) {
            }
        }
    }
}