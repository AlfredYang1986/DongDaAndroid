package com.blackmirror.dongda.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 *  * 支持自定义下标，自定义tab宽度
 * 自定义下标    --> setmSlideIcon
 * 自定义tab宽度 --> 由COUNT_DEFAULT_VISIBLE_TAB和RATIO_DEFAULT_LAST_VISIBLE_TAB共同决定
 * Created by Ruge on 2018-04-26 下午4:19
 */
public class SlidingTabLayout extends TabLayout{
    /**
     * 每个tab的宽度
     */
    private static int tabWidth;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 自定义指示器
     */
    private Bitmap mSlideIcon;
    /**
     * 滑动过程中指示器的水平偏移量
     */
    private static int mTranslationX;
    /**
     * 指示器初始X偏移量
     */
    private int mInitTranslationX;
    /**
     * 指示器初始Y偏移量
     */
    private int mInitTranslationY;
    /**
     * 默认的页面可见的tab数量
     */
    private static final int COUNT_DEFAULT_VISIBLE_TAB = 4;
    /**
     * 默认最后一个tab露出百分比
     */
    private static final float RATIO_DEFAULT_LAST_VISIBLE_TAB = 0.55f;
    /**
     * 页面可见的tab数量，默认4个
     */
    private int mTabVisibleCount = COUNT_DEFAULT_VISIBLE_TAB;
    /**
     * 最后一个tab露出百分比
     */
    private float mLastTabVisibleRatio = RATIO_DEFAULT_LAST_VISIBLE_TAB;
    private static LinearLayout tabStrip;

    public SlidingTabLayout(Context context) {
        super(context);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSlideIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pic_tags_mark);
        this.mScreenWidth = getResources().getDisplayMetrics().widthPixels;

        //方案1：反射修改Tab宽度
        //reflectiveModifyTabWidth();

        //方案2：异步修改Tab宽度
       /* post(new Runnable() {
            @Override
            public void run() {
                resetTabParams();
            }
        });*/
    }

    private void reflectiveModifyTabWidth() {
        final Class<?> clz = TabLayout.class;
        try {
            final Field requestedTabMaxWidthField = clz.getDeclaredField("mRequestedTabMaxWidth");
            final Field requestedTabMinWidthField = clz.getDeclaredField("mRequestedTabMinWidth");

            requestedTabMaxWidthField.setAccessible(true);
            requestedTabMaxWidthField.set(this, (int) (mScreenWidth / (mTabVisibleCount + mLastTabVisibleRatio)));

            requestedTabMinWidthField.setAccessible(true);
            requestedTabMinWidthField.set(this, (int) (mScreenWidth / (mTabVisibleCount + mLastTabVisibleRatio)));
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重绘下标
     */
    public void redrawIndicator(int position, float positionOffset) {
        mTranslationX = (int) ((position + positionOffset) * tabWidth);
        invalidate();
    }

    public void setmSlideIcon(Bitmap mSlideIcon) {
        this.mSlideIcon = mSlideIcon;
    }

    /**
     * tab的父容器，注意空指针
     */
    public LinearLayout getTabStrip() {
        Class<?> tabLayout = TabLayout.class;
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        llTab.setClipChildren(false);
        return llTab;
    }

    /**
     * 绘制指示器
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mSlideIcon == null) {
            return;
        }
        canvas.save();
        // 平移到正确的位置，修正tabs的平移量
        canvas.translate(mInitTranslationX + mTranslationX, this.mInitTranslationY);
        canvas.drawBitmap(this.mSlideIcon, 0, 0, null);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 重设tab宽度
     */
    private void resetTabParams() {
        tabStrip = getTabStrip();
        if (tabStrip == null) {
            return;
        }
        /*for (int i = 0; i < tabStrip.getChildCount(); i++) {
            LinearLayout tabView = (LinearLayout) tabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (mScreenWidth / (mTabVisibleCount + mLastTabVisibleRatio)), LinearLayout.LayoutParams
                    .WRAP_CONTENT);
            tabView.setLayoutParams(params);
            //tab中的图标可以超出父容器
            tabView.setClipChildren(false);
            tabView.setClipToPadding(false);

            tabView.setPadding(0, 30, 0, 30);
        }*/
        initTranslationParams(tabStrip, mScreenWidth);
    }

    /**
     * 初始化三角下标的坐标参数
     */
    private void initTranslationParams(LinearLayout llTab, int screenWidth) {
        if (mSlideIcon == null) {
            return;
        }
        tabWidth = (int) (screenWidth / (mTabVisibleCount + mLastTabVisibleRatio));
        View firstView = llTab.getChildAt(0);
        int count = llTab.getChildCount();
        LogUtils.d("count "+count);

        if (firstView != null) {
            LogUtils.d("firstView.getLeft() "+firstView.getLeft());

            this.mInitTranslationX = (firstView.getLeft() + firstView.getRight() / 2 - firstView.getLeft() / 2-this.mSlideIcon.getWidth() / 2);

        }else {
            LogUtils.d("为空了呢 ");

        }
        LogUtils.d("getBottom "+getBottom());
        LogUtils.d("llTab.getLeft() "+llTab.getLeft());
        LogUtils.d("getBottom "+getBottom());
//        this.mInitTranslationX = (llTab.getLeft()+(llTab.getRight() - llTab.getLeft())/2);

        this.mInitTranslationY = (getBottom() -getTop()- this.mSlideIcon.getHeight());
        invalidate();
    }

    public void initIcon(){
        post(new Runnable() {
            @Override
            public void run() {
                resetTabParams();
            }
        });
    }

    public static class MyTabLayoutOnPageChangeListener extends TabLayoutOnPageChangeListener{

        private final WeakReference<TabLayout> mTabLayoutRef;
        public MyTabLayoutOnPageChangeListener(TabLayout tabLayout) {
            super(tabLayout);
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            final TabLayout tabLayout = mTabLayoutRef.get();
            LogUtils.d("position "+position);
            LogUtils.d("positionOffset "+positionOffset);
            LogUtils.d("positionOffsetPixels "+positionOffsetPixels);

            if (tabStrip == null){
                return;
            }

            View v = tabStrip.getChildAt(position);
            if (v != null){
                int left = tabStrip.getChildAt(position).getLeft();
                int right = tabStrip.getChildAt(position).getRight();
                mTranslationX = (int) (left+positionOffset*(right - left));
                tabLayout.invalidate();
            }
        }
    }
}
