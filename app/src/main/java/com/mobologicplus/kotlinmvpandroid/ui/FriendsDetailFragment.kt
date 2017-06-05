package com.mobologicplus.kotlinmvpandroid.ui

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.mobologicplus.kotlinmvpandroid.R
import com.mobologicplus.kotlinmvpandroid.inject.component.DaggerFriendsDetailComponent
import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsDetailModule
import com.mobologicplus.kotlinmvpandroid.model.Friends
import javax.inject.Inject

/**
 * Created by sunil on 6/5/2017.
 */
class FriendsDetailFragment : Fragment(), FriendsDetailContract.View{

    @Inject
    lateinit var presenter: FriendsDetailContract.Presenter
    private lateinit var mRootView : View

    @BindView(R.id.name)
    @JvmField var nameTextView: TextView? = null

    @BindView(R.id.email)
    @JvmField var emailTextView : TextView? = null

    private var friendId : String? = null


    fun newInstance(): FriendsDetailFragment {
        return FriendsDetailFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        presenter.attachView(this)

        val bundle = this.arguments
        if (bundle != null) {
            friendId = bundle.getString("UserId")
        }
    }

    private fun injectDependency() {
        val friendsDetailComponent = DaggerFriendsDetailComponent.builder()
                .friendsDetailModule(FriendsDetailModule())
                .build()
        friendsDetailComponent.inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unSubscribe()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater!!.inflate(R.layout.fragment_user_detail, container, false)
        ButterKnife.bind(this, mRootView)

        initView()

        return mRootView
    }

    private fun initView(){
        presenter.loadUserDetail(friendId!!)
    }

    override fun onLoadUserDetailOk(friends: Friends.User) {
        nameTextView!!.text = friends.name  // property concept
        emailTextView!!.text = friends.email
    }

    override fun showLoadErrorMessage(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }
}