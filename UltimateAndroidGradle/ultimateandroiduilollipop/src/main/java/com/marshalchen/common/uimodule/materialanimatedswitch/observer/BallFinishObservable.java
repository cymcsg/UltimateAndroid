package com.marshalchen.common.uimodule.materialanimatedswitch.observer;

import java.util.Observable;

/**
 * @author Adrián García Lomas
 */
public class BallFinishObservable extends Observable {

  private BallState state;

  public void setBallState(BallState state) {
    this.state = state;
    setChanged();
    notifyObservers();
  }

  public BallState getState() {
    return state;
  }

  public static enum BallState {
    RELEASE, PRESS, MOVE
  }
}
