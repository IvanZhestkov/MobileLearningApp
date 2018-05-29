package com.itis.android.mobilelearningapp.activities.instruction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itis.android.mobilelearningapp.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.group,
            R.drawable.study,
            R.drawable.profile
    };

    public String[] slide_descs = {
            "Отслеживание прогресса ваших одногруппников",
            "Создание предметов и задач, заданные на изучение",
            "Индивидуальный прогресс и фиксирование решенных задач"
    };

    @Override
    public int getCount() {
        return slide_descs.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.iv_slide);
        TextView slideDescription = view.findViewById(R.id.tv_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
