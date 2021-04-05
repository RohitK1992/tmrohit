package com.tickledmedia.tmrohit.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tickledmedia.tmrohit.R
import com.tickledmedia.tmrohit.adapter.TrendingRepoAdapter
import com.tickledmedia.tmrohit.constants.Constants
import com.tickledmedia.tmrohit.constants.ScreenConstants
import com.tickledmedia.tmrohit.models.ResponseItem
import com.tickledmedia.tmrohit.network.datahelper.DataResponse
import com.tickledmedia.tmrohit.utils.AppUtils
import com.tickledmedia.tmrohit.utils.AppUtils.removeItemDecorations
import com.tickledmedia.tmrohit.utils.AppUtils.showShortToastMessage
import com.tickledmedia.tmrohit.viewmodel.MainViewModel
import com.tickledmedia.tmrohit.widget.LinearLayoutSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_something_went_wrong.*

class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private var trendingRepoAdapter: TrendingRepoAdapter? = null
    private var isSwipedRefreshed: Boolean = false

    companion object{
        private var orientationChange : Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        initListeners()
    }

    override fun getStatusBarType(): Constants.StatusBar? {
        return Constants.StatusBar.LIGHT
    }

    override fun getScreenName(): String {
        return ScreenConstants.MAIN_SCREEN
    }

    override fun init(savedInstanceState: Bundle?) {
        initViewModel()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if(orientationChange){
            fetchLocalData(false)
        }else{
            doNetworkWork()
        }
    }

    private fun initListeners() {
        swipeRefresh.setOnRefreshListener(this)
        btnRetry.setOnClickListener(this)
    }

    private fun setAdapter(repoList: List<ResponseItem>) {
        trendingRepoAdapter = TrendingRepoAdapter(repoList)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvTrendingRepo.layoutManager = linearLayoutManager
        rvTrendingRepo.removeItemDecorations()
        rvTrendingRepo.addItemDecoration(
                LinearLayoutSpaceItemDecoration(
                        resources.getDimensionPixelOffset(
                                R.dimen.dp_16
                        )
                )
        )
        rvTrendingRepo.setHasFixedSize(false)
        rvTrendingRepo.adapter = trendingRepoAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        orientationChange = true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun doNetworkWork() {
        mainViewModel.getListFromServer().observe(this, Observer { responseData ->

            when (responseData.status) {
                DataResponse.Status.LOADING -> {
                    if (!isSwipedRefreshed) {
                        showLoadingState(true)
                    }
                }
                DataResponse.Status.ERROR -> {
                    nullifySwipeRefresh()
                    showLoadingState(false)
                    fetchLocalData(true)
                }

                DataResponse.Status.SUCCESS -> {
                    showLoadingState(false)
                    if (responseData.data != null && responseData.data.isNotEmpty()) {
                        setAdapter(responseData.data)
                        mainViewModel.insertAllRepositories(responseData.data)
                        nullifySwipeRefresh()
                    }
                }
            }
        })
    }

    private fun showLoadingState(mFlag: Boolean) {
        if (mFlag) {
            pbLoading.visibility = View.VISIBLE
            swipeRefresh.visibility = View.GONE
            vgSomethingWentWrong.visibility = View.GONE
        } else {
            pbLoading.visibility = View.GONE
            swipeRefresh.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        isSwipedRefreshed = true
        doNetworkWork()
    }

    private fun nullifySwipeRefresh() {
        isSwipedRefreshed = false
        swipeRefresh.isRefreshing = false
    }

    private fun showSomethingWentWrong() {
        vgSomethingWentWrong.visibility = View.VISIBLE
        swipeRefresh.visibility = View.GONE
        pbLoading.visibility = View.GONE
    }


    private fun fetchLocalData(mFlag : Boolean) {
        mainViewModel.getSingleRepoFromDatabase().observe(this, { responseItem ->
            if(responseItem != null){
                if (AppUtils.hourDifference(responseItem.lastUpdatedTime, System.currentTimeMillis()) <= Constants.MAX_CACHE_HOUR) {
                    mainViewModel.getLocalRepoList().observe(this, { lstResponseItem ->
                        if (lstResponseItem != null && lstResponseItem.isNotEmpty()) {
                            setAdapter(lstResponseItem.sortedWith(compareBy({it.id})))
                            if(mFlag){
                                AppUtils.showLongToastMessage(this, getString(R.string.fetching_local_data))
                            }
                        } else {
                            showSomethingWentWrong()
                        }
                    })
                } else {
                    mainViewModel.deleteLocalRepo()
                    showSomethingWentWrong()
                }
            }else{
                showSomethingWentWrong()
            }

        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRetry -> doNetworkWork()
        }
    }



}