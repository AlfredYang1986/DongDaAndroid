package com.blackmirror.dongda.ui.activity.enrol;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.UnOpenDateAdapter;
import com.blackmirror.dongda.model.UnOpenDateBean;
import com.blackmirror.dongda.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EnrolUnOpenDayActivity extends BaseActivity {

    private RecyclerView rv_cur_date;
    private RecyclerView rv_next_date;
    private ImageView iv_back;
    private TextView tv_save;
    private TextView tv_cur_month;
    private TextView tv_next_month;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_un_open_day;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_save = findViewById(R.id.tv_save);
        rv_cur_date = findViewById(R.id.rv_cur_date);
        rv_next_date = findViewById(R.id.rv_next_date);
        tv_cur_month = findViewById(R.id.tv_cur_month);
        tv_next_month = findViewById(R.id.tv_next_month);

    }

    @Override
    protected void initData() {
        initCurDate();
        initNextDate();
    }

    private void initCurDate() {
        List<String> list=new ArrayList<>();
        UnOpenDateBean bean = new UnOpenDateBean();
        bean.list = list;

        /**获取日历实例**/
        Calendar cld = Calendar.getInstance();

        int month=cld.get(Calendar.MONTH)+1;
        int day =cld.get(Calendar.DAY_OF_MONTH);

        cld.set(Calendar.DATE, 1);
        cld.roll(Calendar.DATE, -1);
        int maxDate = cld.get(Calendar.DATE);


        /**设置日历成当月的第一天**/
        cld.set(Calendar.DAY_OF_MONTH,1);
        //星期对应数字
        int i = cld.get(Calendar.DAY_OF_WEEK)-2;

        tv_cur_month.setText(month+"月");

        bean.month = month;
        bean.firstWeek = i;

        list.add("一");
        list.add("二");
        list.add("三");
        list.add("四");
        list.add("五");
        list.add("六");
        list.add("日");

        for (int j = 0; j < i; j++) {
            list.add("");
        }

        for (int j = 0; j < maxDate; j++) {
            if (day-1 == j){
                list.add("今");
                continue;
            }
            list.add(j+1+"");
        }

        rv_cur_date.setNestedScrollingEnabled(false);
        UnOpenDateAdapter adapter = new UnOpenDateAdapter(this, bean);
        GridLayoutManager manager = new GridLayoutManager(this, 7);
        rv_cur_date.setLayoutManager(manager);
        rv_cur_date.setAdapter(adapter);


        adapter.setOnDateClickListener(new UnOpenDateAdapter.OnDateClickListener() {
            @Override
            public void onItemDateClick(View view, int position,boolean isSelect) {
            }
        });
    }

    private void initNextDate() {
        List<String> list=new ArrayList<>();
        UnOpenDateBean bean = new UnOpenDateBean();
        bean.list = list;

        /**获取日历实例**/

        Calendar cld = Calendar.getInstance();
        int month=cld.get(Calendar.MONTH)+1;
        cld.set(Calendar.MONTH,month);


        cld.set(Calendar.DATE, 1);
        cld.roll(Calendar.DATE, -1);
        int maxDate = cld.get(Calendar.DATE);


        /**设置日历成下个月的第一天**/
        cld.set(Calendar.DAY_OF_MONTH,1);
        //星期对应数字
        int i = cld.get(Calendar.DAY_OF_WEEK)-2;

        list.add("一");
        list.add("二");
        list.add("三");
        list.add("四");
        list.add("五");
        list.add("六");
        list.add("日");

        tv_next_month.setText(month+"月");

        bean.month = month;
        bean.firstWeek = i;

        for (int j = 0; j < i; j++) {
            list.add("");
        }

        for (int j = 0; j < maxDate; j++) {
            list.add(j+1+"");
        }

        rv_next_date.setNestedScrollingEnabled(false);
        UnOpenDateAdapter adapter = new UnOpenDateAdapter(this, bean);
        GridLayoutManager manager = new GridLayoutManager(this, 7);
        rv_next_date.setLayoutManager(manager);
        rv_next_date.setAdapter(adapter);


        adapter.setOnDateClickListener(new UnOpenDateAdapter.OnDateClickListener() {
            @Override
            public void onItemDateClick(View view, int position,boolean isSelect) {
            }
        });
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
