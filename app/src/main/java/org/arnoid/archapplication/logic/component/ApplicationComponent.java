package org.arnoid.archapplication.logic.component;

import org.arnoid.archapplication.ui.step1.ActivityStep1;
import org.arnoid.archapplication.ui.step2.ActivityStep2;
import org.arnoid.archapplication.ui.step3.ActivityStep3;
import org.arnoid.archapplication.ui.step4.ActivityStep4;
import org.arnoid.archapplication.ui.step5.ActivityStep5;
import org.arnoid.archapplication.ui.step5.ButtonsPresenter;

import dagger.Component;

@Component(modules = {
        NetworkModule.class,
        ControllerModule.class
})
public interface ApplicationComponent {

    void inject(ActivityStep1 abstractActivityStep);

    void inject(ActivityStep2 activityStep2);

    void inject(ActivityStep3 activityStep3);

    void inject(ActivityStep4 activityStep4);

    void inject(ActivityStep5 activityStep5);

    //this can be moved to presenter component
    void inject(ButtonsPresenter buttonsPresenter);

}
