/*
 * Copyright 2013 Priboi Tiberiu
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marshalchen.common.demoofui.foldingDrawer;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.marshalchen.common.demoofui.R;

/**
 * Fragment that appears in the "content_frame", shows a animal
 */
public class AnimalFragment extends Fragment {

    public static final String ARG_ANIMAL_NUMBER = "animal_number";

    public AnimalFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.folding_drawer_fragment_animal, container,
                false);
        int i = getArguments().getInt(ARG_ANIMAL_NUMBER);
        String planet = getResources().getStringArray(R.array.items_name)[i];

        int imageId = getResources().getIdentifier(
                planet.toLowerCase(Locale.getDefault()), "drawable",
                getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image))
                .setImageResource(imageId);
        getActivity().setTitle(planet);
        return rootView;
    }
}
