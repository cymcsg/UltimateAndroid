package com.marshalchen.common.uimodule.flowtextview.helpers;


import com.marshalchen.common.uimodule.flowtextview.models.Area;
import com.marshalchen.common.uimodule.flowtextview.models.Line;
import com.marshalchen.common.uimodule.flowtextview.models.Obstacle;

import java.util.ArrayList;


/**
 * Created by Dean on 24/06/2014.
 */
public class CollisionHelper {

    // static for performance
    // this method is being smashed multiple times in onDraw sequences
    // we don't want these to be instatianted for every call to calculateLineSpaceForGivenYOffset, we'll just reuse them
    private final static ArrayList<Area> mAreas = new ArrayList<Area>();

    public static Line calculateLineSpaceForGivenYOffset(float lineYbottom, int lineHeight, float viewWidth, ArrayList<Obstacle> obstacles){

        Line line = new Line();
        line.leftBound = 0;
        line.rightBound = viewWidth;

        float lineYtop = lineYbottom - lineHeight;
        mAreas.clear();

        for (Obstacle obstacle : obstacles) {

            if(obstacle.topLefty > lineYbottom || obstacle.bottomRighty < lineYtop){

            }else{

                Area leftArea = new Area();
                leftArea.x1 = 0;

                for (Obstacle innerObstacle : obstacles) {
                    if(innerObstacle.topLefty > lineYbottom || innerObstacle.bottomRighty < lineYtop){

                    }else{
                        if(innerObstacle.topLeftx < obstacle.topLeftx){
                            leftArea.x1 = innerObstacle.bottomRightx;
                        }
                    }
                }

                leftArea.x2 = obstacle.topLeftx;
                leftArea.width = leftArea.x2 - leftArea.x1;

                Area rightArea = new Area();
                rightArea.x1 = obstacle.bottomRightx;
                rightArea.x2 = viewWidth;

                for (Obstacle innerObstacle : obstacles) {
                    if(innerObstacle.topLefty > lineYbottom || innerObstacle.bottomRighty < lineYtop){

                    }else{
                        if(innerObstacle.bottomRightx > obstacle.bottomRightx){
                            rightArea.x2 = innerObstacle.topLeftx;
                        }
                    }
                }

                rightArea.width = rightArea.x2 - rightArea.x1;

                mAreas.add(leftArea);
                mAreas.add(rightArea);
            }
        }
        Area mLargestArea = null;

        if(mAreas.size()>0){ // if there is no areas then the whole line is clear, if there is areas, return the largest (it means there is one or more boxes colliding with this line)
            for (Area area : mAreas) {
                if(mLargestArea == null){
                    mLargestArea = area;
                }else{
                    if(area.width > mLargestArea.width){
                        mLargestArea = area;
                    }
                }
            }

            line.leftBound = mLargestArea.x1;
            line.rightBound = mLargestArea.x2;
        }

        return line;
    }
}
