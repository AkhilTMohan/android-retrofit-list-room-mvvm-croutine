package com.c2info.akhil_systemtest.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c2info.akhil_systemtest.R
import com.c2info.akhil_systemtest.databinding.FragmentLoginBinding
import com.c2info.akhil_systemtest.interfaces.CallBack
import com.c2info.akhil_systemtest.models.LoginModel
import com.c2info.akhil_systemtest.models.ResponseLogin
import com.c2info.akhil_systemtest.network.APICall
import com.c2info.akhil_systemtest.utils.MyDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentLoginBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.button?.setOnClickListener {
            CoroutineScope(Main).launch {
                val email = binding?.email?.text
                val password = binding?.password?.text
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    MyDialogFragment.newInstance(MyDialogFragment.DIALOG_TYPE.ALERT,
                        getString(R.string.val_error),
                        getString(R.string.provide_valid_cred),
                        object : CallBack {
                            override fun onClick(t: Any?, t2: Any?, t3: Any?) {
                            }

                        }).show(parentFragmentManager, null)
                } else {
                    loginUser(email.toString(), password.toString())
                }
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        APICall.login(true, parentFragmentManager, LoginModel(email, password),
            object : CallBack {
                override fun onClick(t: Any?, t2: Any?, t3: Any?) {
                    t as ResponseLogin
                    if (t.token.isNotEmpty()) {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())
                    } else {
                        MyDialogFragment.newInstance(MyDialogFragment.DIALOG_TYPE.ALERT,
                            getString(R.string.error),
                            t.error,
                            object : CallBack {
                                override fun onClick(t: Any?, t2: Any?, t3: Any?) {
                                    loginUser(email, password)
                                }

                            })
                    }
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}