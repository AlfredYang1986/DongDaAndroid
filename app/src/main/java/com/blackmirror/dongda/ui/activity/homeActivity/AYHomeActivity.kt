package com.blackmirror.dongda.ui.activity.homeActivity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.*
import com.blackmirror.dongda.adapter.itemdecoration.SpacesItemDecoration
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerAYHomeComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.presenter.HomePresenter
import com.blackmirror.dongda.ui.activity.*
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

/**
 * Created by alfredyang on 29/6/17.
 */

class AYHomeActivity : BaseActivity(), View.OnClickListener, HomeContract.HomeView {

    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var rv_featured_theme: RecyclerView
    private lateinit var rv_home_care: RecyclerView
    private lateinit var sv_head_pic: SimpleDraweeView
    private lateinit var rv_home_art: RecyclerView
    private lateinit var rv_home_sport: RecyclerView
    private lateinit var rv_home_science: RecyclerView
    private lateinit var iv_home_location: ImageView
    private lateinit var sl_home_refresh: SwipeRefreshLayout
    private lateinit var tv_home_care_more: TextView
    private lateinit var tv_home_art_more: TextView
    private lateinit var tv_home_sport_more: TextView
    private lateinit var tv_home_science_more: TextView
    private lateinit var iv_home_like: ImageView
    private var careAdapter: HomeCareAdapter? = null
    private var artAdapter: HomeArtAdapter? = null
    private var sportAdapter: HomeSportAdapter? = null
    private var scienceAdapter: HomeScienceAdapter? = null
    private var clickLikePos: Int = 0
    private var clickAdapter: Int = 0//1 art 2 sport 3 science
    private var img_uuid: String? = null
    private var presenter: HomePresenter? = null
    private var bean: HomepageDomainBean? = null

    override val layoutResId: Int
        get() = R.layout.activity_home


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        img_uuid = intent.getStringExtra("img_uuid")
    }

    override fun initInject() {
        presenter = DaggerAYHomeComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .homePresenter
    }

    override fun initView() {
        ctl_root = findViewById(R.id.ctl_root)
        sv_head_pic = findViewById(R.id.sv_head_pic)
        rv_featured_theme = findViewById(R.id.rv_featured_theme)
        rv_home_care = findViewById(R.id.rv_home_care)
        rv_home_art = findViewById(R.id.rv_home_art)
        rv_home_sport = findViewById(R.id.rv_home_sport)
        rv_home_science = findViewById(R.id.rv_home_science)
        iv_home_location = findViewById(R.id.iv_home_location)
        sl_home_refresh = findViewById(R.id.sl_home_refresh)
        tv_home_care_more = findViewById(R.id.tv_home_care_more)
        tv_home_art_more = findViewById(R.id.tv_home_art_more)
        tv_home_sport_more = findViewById(R.id.tv_home_sport_more)
        tv_home_science_more = findViewById(R.id.tv_home_science_more)
        iv_home_like = findViewById(R.id.iv_home_like)
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_home_location.elevation = DensityUtils.dp2px(6).toFloat()
        }
        showProcessDialog()
        //精选主题
        initSubject()
        initHomeData()
    }

    override fun initListener() {
        tv_home_care_more.setOnClickListener(this)
        tv_home_art_more.setOnClickListener(this)
        tv_home_sport_more.setOnClickListener(this)
        tv_home_science_more.setOnClickListener(this)
        iv_home_location.setOnClickListener(this)
        iv_home_like.setOnClickListener(this)
        sv_head_pic.setOnClickListener(this)

        sl_home_refresh.setOnRefreshListener { initHomeData() }
    }

    private fun initHomeData() {
        presenter?.getHomePageData()
    }

    private fun initSubject() {
        val featuredList = ArrayList<Int>()
        featuredList.add(R.drawable.home_cover_00)
        featuredList.add(R.drawable.home_cover_01)
        featuredList.add(R.drawable.home_cover_02)
        featuredList.add(R.drawable.home_cover_03)
        featuredList.add(R.drawable.home_cover_04)
        val manager = LinearLayoutManager(this@AYHomeActivity)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        val adapter = FeaturedThemeAdapter(this@AYHomeActivity,
                featuredList)
        rv_featured_theme.isNestedScrollingEnabled = false
        rv_featured_theme.layoutManager = manager
        rv_featured_theme.adapter = adapter
        rv_featured_theme.addItemDecoration(SpacesItemDecoration(10))

        adapter.setOnItemClickListener { view, position ->
            val intent = Intent(this@AYHomeActivity, FeaturedDetailActivity::class.java)
            intent.putExtra("pos", position)
            startActivityForResult(intent, AppConstant.FEATURE_DETAIL_REQUEST_CODE)
        }
    }

    private fun initCare(bean: HomepageDomainBean.HomepageServicesBean) {
        if (careAdapter == null) {
            val manager = LinearLayoutManager(this@AYHomeActivity)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            careAdapter = HomeCareAdapter(this@AYHomeActivity, bean)
            rv_home_care.isNestedScrollingEnabled = false
            rv_home_care.layoutManager = manager
            rv_home_care.adapter = careAdapter
            rv_home_care.addItemDecoration(SpacesItemDecoration(8))

            careAdapter?.setOnCareClickListener { view, position, service_id ->
                //                    startActivityForResult(new Intent(AYHomeActivity.this, CareListActivity.class));
                val intent = Intent(this@AYHomeActivity, ServiceDetailInfoActivity::class.java)
                intent.putExtra("service_id", service_id)
                startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE)
            }
        } else {
            careAdapter!!.setRefreshData(bean.services)
        }
    }

    private fun initArt(bean: HomepageDomainBean.HomepageServicesBean) {

        if (artAdapter == null) {
            val manager = LinearLayoutManager(this@AYHomeActivity)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            artAdapter = HomeArtAdapter(this@AYHomeActivity, bean)
            rv_home_art.isNestedScrollingEnabled = false
            rv_home_art.layoutManager = manager
            rv_home_art.adapter = artAdapter
            rv_home_art.addItemDecoration(SpacesItemDecoration(8))
            artAdapter!!.setOnItemClickListener({ view, position, service_id ->
                val intent = Intent(this@AYHomeActivity, ServiceDetailInfoActivity::class.java)
                intent.putExtra("service_id", service_id)
                startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE)
            }, { view, position, ServicesBean ->
                clickLikePos = position
                clickAdapter = AppConstant.HOME_ART_ADAPTER
                sendLikeData(ServicesBean)
            })
        } else {
            artAdapter!!.setRefreshData(bean.services)
        }
    }

    private fun initSport(bean: HomepageDomainBean.HomepageServicesBean) {

        if (sportAdapter == null) {
            val manager = LinearLayoutManager(this@AYHomeActivity)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            sportAdapter = HomeSportAdapter(this@AYHomeActivity, bean)
            rv_home_sport.isNestedScrollingEnabled = false
            rv_home_sport.layoutManager = manager
            rv_home_sport.adapter = sportAdapter
            rv_home_sport.addItemDecoration(SpacesItemDecoration(8))
            sportAdapter?.setOnItemClickListener({ view, position, service_id ->
                val intent = Intent(this@AYHomeActivity, ServiceDetailInfoActivity::class.java)
                intent.putExtra("service_id", service_id)
                startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE)
            }, { view, position, servicesBean ->
                sendLikeData(servicesBean)
                clickLikePos = position
                clickAdapter = AppConstant.HOME_SPORT_ADAPTER
            })
        } else {
            sportAdapter!!.setRefreshData(bean.services)
        }
    }

    private fun initScience(bean: HomepageDomainBean.HomepageServicesBean) {
        if (scienceAdapter == null) {
            val manager = LinearLayoutManager(this@AYHomeActivity)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            scienceAdapter = HomeScienceAdapter(this@AYHomeActivity, bean)
            rv_home_science.isNestedScrollingEnabled = false
            rv_home_science.layoutManager = manager
            rv_home_science.adapter = scienceAdapter
            rv_home_science.addItemDecoration(SpacesItemDecoration(8))
            scienceAdapter?.setOnItemClickListener({ view, position, service_id ->
                val intent = Intent(this@AYHomeActivity, ServiceDetailInfoActivity::class.java)
                intent.putExtra("service_id", service_id)
                startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE)
            }, { view, position, servicesBean ->
                clickLikePos = position
                clickAdapter = AppConstant.HOME_SCIENCE_ADAPTER
                sendLikeData(servicesBean)
            })
        } else {
            sportAdapter!!.setRefreshData(bean.services)
        }
    }

    private fun sendLikeData(bean: HomepageDomainBean.HomepageServicesBean.ServicesBean) {
        showProcessDialog()

        if (bean.is_collected) {//已收藏 点击取消
            presenter?.likePop(bean.service_id!!)
        } else {
            presenter?.likePush(bean.service_id!!)
        }

    }

    override fun onClick(v: View) {
        val intent = Intent(this@AYHomeActivity, ArtListActivity::class.java)
        when (v.id) {
            R.id.tv_home_care_more -> {

                val careIntent = Intent(this@AYHomeActivity, CareListActivity::class.java)
                if (bean != null && bean!!.homepage_services != null) {
                    val m = bean!!.homepage_services!![0].totalCount
                    careIntent.putExtra("totalCount", m)
                }
                startActivityForResult(careIntent, AppConstant.CARE_MORE_REQUEST_CODE)
            }
            R.id.tv_home_art_more -> {
                if (bean != null && bean!!.homepage_services != null) {
                    val m = bean!!.homepage_services!![1].totalCount
                    intent.putExtra("totalCount", m)
                }
                intent.putExtra("serviceType", getString(R.string.type_art))
                intent.putExtra("title", getString(R.string.type_art))
                startActivityForResult(intent, AppConstant.ART_MORE_REQUEST_CODE)
            }
            R.id.tv_home_sport_more -> {
                if (bean != null && bean!!.homepage_services != null) {
                    val m = bean!!.homepage_services!![2].totalCount
                    intent.putExtra("totalCount", m)
                }
                intent.putExtra("serviceType", getString(R.string.type_sport))
                intent.putExtra("title", getString(R.string.title_sport))
                startActivityForResult(intent, AppConstant.SPORT_MORE_REQUEST_CODE)
            }
            R.id.tv_home_science_more -> {
                if (bean != null && bean!!.homepage_services != null) {
                    val m = bean!!.homepage_services!![3].totalCount
                    intent.putExtra("totalCount", m)
                }
                intent.putExtra("serviceType", getString(R.string.type_science))
                intent.putExtra("title", getString(R.string.title_science))
                startActivityForResult(intent, AppConstant.SCIENCE_REQUEST_CODE)
            }
            R.id.iv_home_location -> startActivity(Intent(this@AYHomeActivity, NearServiceActivity::class.java))
            R.id.iv_home_like -> startActivityForResult(Intent(this@AYHomeActivity, MyLikeActivity::class.java), AppConstant.MY_LIKE_REQUEST_CODE)
            R.id.sv_head_pic -> {
                //                startActivityForResult(new Intent(AYHomeActivity.this, UserAboutMeActivity.class), AppConstant.ABOUT_USER_REQUEST_CODE);
                AYApplication.addActivity(this)
                startActivityForResult(Intent(this@AYHomeActivity, UserInfoActivity::class.java), AppConstant.ABOUT_USER_REQUEST_CODE)
            }
        }//                SnackbarUtils.show(ctl_root,"hahaha ");
    }

    private fun needsRefreshHomeData(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AppConstant.CARE_MORE_REQUEST_CODE,
            AppConstant.ART_MORE_REQUEST_CODE,
            AppConstant.SPORT_MORE_REQUEST_CODE,
            AppConstant.SCIENCE_REQUEST_CODE,
            AppConstant.MY_LIKE_REQUEST_CODE,
            AppConstant.FEATURE_DETAIL_REQUEST_CODE,
            AppConstant.SERVICE_DETAIL_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                //                    sl_home_refresh.setRefreshing(false);
                sl_home_refresh.isRefreshing = true
                initHomeData()
            }
            AppConstant.ABOUT_USER_REQUEST_CODE -> refreshHeadPhoto(resultCode, data)
        }
    }

    override fun onResume() {
        super.onResume()
        AYApplication.addActivity(this)
    }

    private fun refreshHeadPhoto(resultCode: Int, data: Intent?) {
        if (data != null) {
            LogUtils.d("img_url ayhome " + data.getStringExtra("img_url"))
        }
        if (resultCode == Activity.RESULT_OK) {
            val img_url = data!!.getStringExtra("img_url")
            img_uuid = img_url
            sv_head_pic.setImageURI(OSSUtils.getSignedUrl(img_url))
        }
    }


    override fun onGetHomePageData(bean: HomepageDomainBean) {
        this.bean = bean
        closeProcessDialog()
        val url = OSSUtils.getSignedUrl(img_uuid)
        LogUtils.d("pic url $url")
        sv_head_pic.setImageURI(url)
        sl_home_refresh.isRefreshing = false
        initCare(bean.homepage_services!![0])
        initArt(bean.homepage_services!![1])
        initSport(bean.homepage_services!![2])
        initScience(bean.homepage_services!![3])
    }

    override fun onLikePushSuccess(bean: LikePushDomainBean) {
        closeProcessDialog()

        when (clickAdapter) {
            AppConstant.HOME_ART_ADAPTER -> artAdapter!!.notifyItemChanged(clickLikePos, true)
            AppConstant.HOME_SPORT_ADAPTER -> sportAdapter!!.notifyItemChanged(clickLikePos, true)
            AppConstant.HOME_SCIENCE_ADAPTER -> scienceAdapter!!.notifyItemChanged(clickLikePos, true)
        }

        /*if (clickAdapter == AppConstant.HOME_ART_ADAPTER) {
            artAdapter!!.notifyItemChanged(clickLikePos, true)
        } else if (clickAdapter == AppConstant.HOME_SPORT_ADAPTER) {
            sportAdapter!!.notifyItemChanged(clickLikePos, true)
        } else if (clickAdapter == AppConstant.HOME_SCIENCE_ADAPTER) {
            scienceAdapter!!.notifyItemChanged(clickLikePos, true)
        }*/

    }

    override fun onLikePopSuccess(bean: LikePopDomainBean) {
        closeProcessDialog()

        when (clickAdapter) {
            AppConstant.HOME_ART_ADAPTER -> artAdapter!!.notifyItemChanged(clickLikePos, false)
            AppConstant.HOME_SPORT_ADAPTER -> sportAdapter!!.notifyItemChanged(clickLikePos, false)
            AppConstant.HOME_SCIENCE_ADAPTER -> scienceAdapter!!.notifyItemChanged(clickLikePos, false)
        }

        /*if (clickAdapter == AppConstant.HOME_ART_ADAPTER) {
            artAdapter!!.notifyItemChanged(clickLikePos, false)
        } else if (clickAdapter == AppConstant.HOME_SPORT_ADAPTER) {
            sportAdapter!!.notifyItemChanged(clickLikePos, false)
        } else if (clickAdapter == AppConstant.HOME_SCIENCE_ADAPTER) {
            scienceAdapter!!.notifyItemChanged(clickLikePos, false)
        }*/

    }

    override fun onGetHomeDataError(bean: BaseDataBean) {
        closeProcessDialog()
        sl_home_refresh.isRefreshing = false
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        needsRefreshHomeData(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        AYApplication.removeActivity(this)
        moveTaskToBack(true)
    }
}
