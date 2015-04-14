package com.marshalchen.common.uimodule.niftymodaldialogeffects;


import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.BaseEffects;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.FadeIn;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.Fall;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.FlipH;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.FlipV;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.NewsPaper;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.RotateBottom;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.RotateLeft;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.Shake;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.SideFall;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.SlideBottom;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.SlideLeft;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.SlideRight;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.SlideTop;
import com.marshalchen.common.uimodule.niftymodaldialogeffects.effects.Slit;

/**
 * Created by lee on 2014/7/30.
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
