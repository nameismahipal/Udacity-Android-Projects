package www.androidcitizen.com.bakeit.data.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import www.androidcitizen.com.bakeit.data.model.Step;
import www.androidcitizen.com.bakeit.ui.fragment.SingleStepFragment;


/**
 * Created by Mahi on 01/09/18.
 * www.androidcitizen.com
 */

class StepsFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Step> steps;

    public StepsFragmentPagerAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int iPosition) {

        return SingleStepFragment.newInstance(steps.get(iPosition));
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
