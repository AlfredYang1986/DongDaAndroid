package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter;
import com.blackmirror.dongda.model.TestFeaturedDetailBean;

import java.util.ArrayList;
import java.util.List;

public class FeaturedDetailActivity extends AppCompatActivity {

    final static String TAG = "FeaturedDetailActivity";
    private ImageView iv_featured_detail_back;
    private TextView tv_featured_tb_title;
    private RecyclerView rv_featured_detail;
    private Toolbar toolbar;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_detail);
        OtherUtils.setStatusBarColor(FeaturedDetailActivity.this);
        pos = getIntent().getIntExtra("pos", 0);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        iv_featured_detail_back = findViewById(R.id.iv_featured_detail_back);
        tv_featured_tb_title = findViewById(R.id.tv_featured_tb_title);
        rv_featured_detail = findViewById(R.id.rv_featured_detail);
    }

    private void initData() {

        List<String> titleList=new ArrayList<>();
        titleList.add("准备好爱与耐心，自律才能自由");
        titleList.add("语言，是一种思考方式");
        titleList.add("跳出温室，体验非凡张力");
        titleList.add("触摸与众不同的感受");
        titleList.add("身临其境，体验科学的力量");

        List<String> cl=new ArrayList<>();
        cl.add("「妈妈，帮帮我，让我可以自己做」\n" +
                "「孩子能做，就别插手」\n" +
                "追随孩子，Aid to life，蒙特梭利教育（Montessori），是意大利心理学家玛丽亚 · " +
                "蒙特梭利发展的教育方法。强调独立，有限度的自由和对孩子天然的心理、生理及社会性发展的尊重。");
        cl.add("Water 就是 Water，不是水。Apple就是圆圆的果子，咬一口。  习得语言，而非学习语言，是对孩子至关重要的概念。ESL（English as a second language）,给孩子们创造时时刻刻应用的契机，无论是学科中还是生活里。");
        cl.add("惊喜，孩子们无时无刻不在创造惊喜给我们。在这里，突破的不仅仅是身体，还有心理。更重要的是在注视和保护下，用科学的训练方式，探索能力的界限，进行体能的尝试。");
        cl.add("善琴者通达从容，善棋者筹谋睿智，善书者至情至性，善画者至善至美，善诗者韵至心声，善酒者情逢知己，善茶者陶冶情操，善花者品性怡然。琴棋书画，诗酒花茶。心灵中充满的情趣，将伴随孩子始终，让他们成为生活的欣赏者和创造者。");
        cl.add("科学Science，技术Technology，工程Engineering，艺术Arts，数学Math。以Project-based learning的学习方式，帮助孩子们将知识与兴趣连接。了解世界上正在发生的事儿，自然而然地共同面对和动手解决实际的问题。");

        List<Integer> rl=new ArrayList<>();
        rl.add(R.drawable.coverlist_bg_theme);
        rl.add(R.drawable.coverlist_bg_01);
        rl.add(R.drawable.coverlist_bg_03);
        rl.add(R.drawable.coverlist_bg_02);
        rl.add(R.drawable.coverlist_bg_04);

        List<TestFeaturedDetailBean> tl=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestFeaturedDetailBean bean = new TestFeaturedDetailBean();
            bean.title = titleList.get(i);
            bean.content = cl.get(i);
            bean.bg_redId=rl.get(i);
            tl.add(bean);
        }



        initTbTitle();

        List<Integer> list= new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        FeaturedDetailAdapter adapter = new FeaturedDetailAdapter(FeaturedDetailActivity.this,list);
        adapter.title=pos;
        adapter.bean=tl.get(pos);
        LinearLayoutManager manager = new LinearLayoutManager(FeaturedDetailActivity.this);
        rv_featured_detail.setLayoutManager(manager);
        rv_featured_detail.setAdapter(adapter);
    }

    private void initTbTitle() {
        switch(pos){
            case 0://蒙特俊利
                tv_featured_tb_title.setText("蒙特俊利");
                break;
            case 1://浸入式英语
                tv_featured_tb_title.setText("浸入式英语");
                break;
            case 2://极限运动
                tv_featured_tb_title.setText("极限运动");
                break;
            case 3://修身养性
                tv_featured_tb_title.setText("修身养性");
                break;
            case 4://STEAM
                tv_featured_tb_title.setText("STEAM");
                break;

        }
    }

    private void initListener() {
        iv_featured_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

   /* @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }*/
}
