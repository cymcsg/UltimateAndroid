package com.marshalchen.common.uimodule.discrollview;

/**
 * Represents a View able to be discrollved.
 */
public interface Discrollvable {

    /**
     * Called to discrollve the View.
     * @param ratio discrollve ratio between 0.0 and 1.0.
     *              1.0 means the View is totally discrollved
     */
    public void onDiscrollve(float ratio);

    /**
     * Called to reset the discrollvation of the View.
     * In this method, you have to reset the View in order
     * to be discrollved again.
     */
    public void onResetDiscrollve();
}
