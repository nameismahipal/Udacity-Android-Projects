package www.androidcitizen.com.bakeit.data.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;


/**
 * Created by Mahi on 01/09/18.
 * www.androidcitizen.com
 */

public class StepsFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Step> steps;

    public StepsFragmentPagerAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int iPosition) {

        SingleStepFragment fragment = SingleStepFragment.newInstance(steps.get(iPosition));

        return fragment;
    }

    @Override
    public int getCount() {
        if(null == steps)
            return 0;
        else
            return steps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format("Step %d", position);
    }

}
