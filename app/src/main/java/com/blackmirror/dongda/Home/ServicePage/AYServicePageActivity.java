package com.blackmirror.dongda.Home.ServicePage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYScreenSingleton;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYNavBarFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYServicePageActivity extends AYActivity {

    final private String TAG = "AYServicePageActivity";
    private JSONObject js_service_info;

    private ArrayList dotArr;
    private View dotViewTmp;

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        //新页面接收数据
        String info = this.getIntent().getStringExtra("service_info");
        Log.d(TAG, "onCreate: "+info);
        //接收name值
        try {
            js_service_info = new JSONObject(info);
            Log.d(TAG, "onCreate: " + js_service_info.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextWithString("服务详情");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setBottomLineInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setBackgroundWithColor(getResources().getColor(R.color.colorAlpha));
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R.drawable.home_icon_love_select);
        ((AYServicePageBottomFragment)this.fragments.get("frag_servpage_bottom")).setServPageBottomInfo(js_service_info);

        JSONArray imagesArr = null;
        List viewList = new ArrayList<View>();
        try {
            imagesArr = js_service_info.getJSONArray("images");
            if (imagesArr.length() > 0) {
                dotArr = new ArrayList();
                View dotDivView = new View(this);
                float screenScale = (new AYScreenSingleton()).getScreenDensity(this);
                float screenwidth = (new AYScreenSingleton()).getScreenWidth(this);
                int dotWidth = 5*(int)screenScale;
                int dotMargin = 5*(int)screenScale;
                RelativeLayout dotLayout = (RelativeLayout)findViewById(R.id.servinfo_group_cover);

                for (int i = 0; i < imagesArr.length(); ++i) {

                    View dotView = new View(this);
                    if (i==0){
                        dotView.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                        dotViewTmp = dotView;
                    } else
                        dotView.setBackgroundColor(getResources().getColor(R.color.colorAlphaBorder));

                    RelativeLayout.LayoutParams dotLP = new RelativeLayout.LayoutParams(dotWidth, dotWidth);
                    dotLP.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.viewpage_servpage_cover);
                    dotView.setLayoutParams(dotLP);
                    dotLayout.addView(dotView);
                    dotLP.setMargins((int)((screenwidth*screenScale-imagesArr.length() *(dotMargin+dotWidth))/2 + (dotMargin+dotWidth)*i), 0, 0, 20*(int)screenScale);
                    dotArr.add(dotView);

                    ImageView imageView = new ImageView(this);
                    FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(lytp);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewList.add(imageView);

//                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    String imageUrl = "http://altlys.com:9000/query/downloadFile/" + imagesArr.getString(i);
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.default_image)
                            .showImageOnFail(R.drawable.default_image)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
//                            .imageScaleType(ImageScaleType.EXACTLY) // default
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();
                    ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ViewPager imagesViewPager = ((AYServicePageInfoFragment)this.fragments.get("frag_servpage_info")).mViewPager;
        imagesViewPager.setAdapter(new AYServiceImagesAdapter(viewList));
        imagesViewPager.setCurrentItem(0);
        imagesViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotViewTmp.setBackgroundColor(getResources().getColor(R.color.colorAlphaBorder));
                View dot = (View)dotArr.get(position);
                dot.setBackgroundColor(getResources().getColor(R.color.colorTheme));
                dotViewTmp = dot;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void bindingFragments() {

        Log.d(TAG, "bindingFragments: " + this.fragments);
        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_servpage_info"));
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_servpage_bottom"));
        task.commit();
    }

    public void didNavLeftBtnClickNotify (JSONObject args) {
//        Intent intent = getIntent();
//        //这里使用bundle绷带来传输数据
//        Bundle bundle = new Bundle();
//        //传输的内容仍然是键值对的形式
//        bundle.putString("second","hello world from secondActivity!");//回发的消息,hello world from secondActivity!
//        intent.putExtras(bundle);
//        setResult(RESULT_OK,intent);
        this.finish();
    }

    public void didNavRightBtnClickNotify (JSONObject args) {
        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }
}
