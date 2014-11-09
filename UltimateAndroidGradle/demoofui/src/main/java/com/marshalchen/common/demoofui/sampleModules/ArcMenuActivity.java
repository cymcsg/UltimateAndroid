/*
 * Copyright (C) 2012 Capricorn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.arcmenu.ArcMenu;
import com.marshalchen.common.uimodule.arcmenu.RayMenu;

/**
 * @author Capricorn
 */
public class ArcMenuActivity extends Activity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.arc_menu_composer_camera, R.drawable.arc_menu_composer_music,
            R.drawable.arc_menu_composer_place, R.drawable.arc_menu_composer_sleep, R.drawable.arc_menu_composer_thought, R.drawable.arc_menu_composer_with};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arc_menu_activity);

        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
        ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);

        initArcMenu(arcMenu, ITEM_DRAWABLES);
        initArcMenu(arcMenu2, ITEM_DRAWABLES);

        RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            rayMenu.addItem(item, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });// Add a menu item
        }
    }

    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
