package com.mobologicplus.kotlinmvpandroid.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobologicplus.kotlinmvpandroid.R
import com.mobologicplus.kotlinmvpandroid.model.Friends
import javax.inject.Inject
import com.mobologicplus.kotlinmvpandroid.db.DbManager
import com.mobologicplus.kotlinmvpandroid.inject.component.DaggerFriendsComponent
import com.mobologicplus.kotlinmvpandroid.inject.module.FriendsModule


/**
 * Created by sunil on 5/30/2017.
 */
class FriendsListFragment : Fragment(), FriendsContract.View, FriendsAdapter.onItemClickListener{

    @Inject
    lateinit var presenter: FriendsContract.Presenter
    private lateinit var mRootView : View

    @BindView(R.id.recyclerview)
    @JvmField var recyclerView: RecyclerView? = null

    fun newInstance(): FriendsListFragment {
        return FriendsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
        presenter.attachView(this)
    }

    private fun injectDependency() {
     //   val applicationComponent = (activity.application as MainApplication).getApplicationComponent()
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
       // presenter.loadFriendsAPI();
        var count = DbManager().getCount()
        if (count == 0) {
            // call retrofit
           presenter.loadFriendsAPI();
        }else{
            // call db
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

    }

    override fun showLoadErrorMessage(error: String) {

    }

    override fun showEmptyView(visible: Boolean) {

    }

    override fun itemRemoveClick(position: Int) {

    }
}