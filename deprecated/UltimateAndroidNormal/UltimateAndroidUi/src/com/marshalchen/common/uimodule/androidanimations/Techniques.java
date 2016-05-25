
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marshalchen.common.uimodule.androidanimations;

import com.marshalchen.common.uimodule.androidanimations.attention.BounceAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.FlashAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.PulseAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.RubberBandAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.ShakeAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.StandUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.SwingAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.TadaAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.WaveAnimator;
import com.marshalchen.common.uimodule.androidanimations.attention.WobbleAnimator;
import com.marshalchen.common.uimodule.androidanimations.bouncing_entrances.BounceInAnimator;
import com.marshalchen.common.uimodule.androidanimations.bouncing_entrances.BounceInDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.bouncing_entrances.BounceInLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.bouncing_entrances.BounceInRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.bouncing_entrances.BounceInUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_entrances.FadeInAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_entrances.FadeInDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_entrances.FadeInLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_entrances.FadeInRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_entrances.FadeInUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_exits.FadeOutAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_exits.FadeOutDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_exits.FadeOutLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_exits.FadeOutRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.fading_exits.FadeOutUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.flippers.FlipInXAnimator;
import com.marshalchen.common.uimodule.androidanimations.flippers.FlipOutXAnimator;
import com.marshalchen.common.uimodule.androidanimations.flippers.FlipOutYAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_entrances.RotateInAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_entrances.RotateInDownLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_entrances.RotateInDownRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_entrances.RotateInUpLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_entrances.RotateInUpRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_exits.RotateOutAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_exits.RotateOutDownLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_exits.RotateOutDownRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_exits.RotateOutUpLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.rotating_exits.RotateOutUpRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideInDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideInLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideInRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideInUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideOutDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideOutLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideOutRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.sliders.SlideOutUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.HingeAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.RollInAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.RollOutAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.in.DropOutAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.in.LandingAnimator;
import com.marshalchen.common.uimodule.androidanimations.specials.outs.TakingOffAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_entrances.ZoomInAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_entrances.ZoomInDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_entrances.ZoomInLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_entrances.ZoomInRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_entrances.ZoomInUpAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_exits.ZoomOutAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_exits.ZoomOutDownAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_exits.ZoomOutLeftAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_exits.ZoomOutRightAnimator;
import com.marshalchen.common.uimodule.androidanimations.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {

    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    TakingOff(TakingOffAnimator.class),

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    Hinge(HingeAnimator.class),
    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),

    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);



    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
