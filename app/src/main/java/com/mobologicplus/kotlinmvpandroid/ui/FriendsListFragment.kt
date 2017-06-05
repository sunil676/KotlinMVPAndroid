package com.mobologicplus.kotlinmvpandroid.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.mobologicplus.kotlinmvpandroid.R
import com.mobologicplus.kotlinmvpandroid.model.Friends
import javax.inject.Inject
import com.mobologicplus.kotlinmvpandroid.db.DbManager
import com.mobologicplus.kotlinmvpandroid.inject.component.DaggerFriendsComponent
import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsModule
import com.mobologicplus.kotlinmvpandroid.util.ActivityUtil
import android.R.attr.fragment




/**
 * Created by sunil on 5/30/2017.
 */
class FriendsListFragment : Fragment(), FriendsContract.View, FriendsAdapter.onItemClickListener{

    @Inject
    lateinit var presenter: FriendsContract.Presenter
    private lateinit var mRootView : View

    @BindView(R.id.recyclerview)
    @JvmField var recyclerView: RecyclerView? = null

    @BindView(R.id.progressbar)
    @JvmField var progressbar : ProgressBar? = null

    fun newInstance(): FriendsListFragment {
        return FriendsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        presenter.attachView(this)
    }

    private fun injectDependency() {
        val friendsComponent = DaggerFriendsComponent.builder()
                .friendsModule(FriendsModule())
                .build()
        friendsComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater!!.inflate(R.layout.fragment_list, container, false)
        ButterKnife.bind(this, mRootView)

        initView()

        return mRootView
    }

    fun initView(){
        // check the data available in db then do not call retrofit for data
        var count = DbManager().getCount()
        if (count == 0) {
            // call retrofit
            progressbar!!.setVisibility(View.VISIBLE)
            presenter.loadFriendsAPI();
        }else{
            // call db
            progressbar!!.setVisibility(View.GONE)
            presenter.loadFriendsDb()
        }

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unSubscribe()
    }

    override fun onLoadFriendsOk(friends: List<Friends.User>) {
       // Here you can get data ans show on UI
        var adapter = FriendsAdapter(activity, friends.toMutableList(), this);
        recyclerView!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerView!!.setAdapter(adapter)
    }

    override fun showProgress(show: Boolean) {
        if (!show){
            progressbar!!.setVisibility(View.GONE)
        }
    }

    override fun showLoadErrorMessage(error: String) {
        showToast(error)
    }

    override fun showEmptyView(visible: Boolean) {
        showToast("No Item")
    }

    override fun deletedItem(isDeleted: Boolean) {
        if (isDeleted){
            showToast("Item Deleted")
        }else{
            showToast("Did not Item Deleted")
        }
    }

    override fun itemRemoveClick(user: Friends.User) {
       presenter.deleteItem(user)
    }

    fun showToast(message : String){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun itemDetail(userId: String) {
        val friendsDetailFragment = FriendsDetailFragment().newInstance()
        val bundle = Bundle()
        bundle.putString("UserId", userId)
        friendsDetailFragment.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(fragmentManager, friendsDetailFragment, R.id.frame, "FriendsDetailFragment")
    }
}