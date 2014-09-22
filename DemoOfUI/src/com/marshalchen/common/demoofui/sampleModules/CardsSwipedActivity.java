/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.marshalchen.common.uimodule.cardsSwiped.model.CardModel;
import com.marshalchen.common.uimodule.cardsSwiped.view.CardContainer;
import com.marshalchen.common.uimodule.cardsSwiped.view.SimpleCardStackAdapter;
import com.marshalchen.common.demoofui.R;


public class CardsSwipedActivity extends Activity {

    /**
     * This variable is the container that will host our cards
     */
	private CardContainer mCardContainer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.card_swiped_activity);

		mCardContainer = (CardContainer) findViewById(R.id.layoutview);

		Resources r = getResources();

		SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));
		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture3)));
		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1)));
		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture2)));

        CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.card_swipted_picture1));
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
           @Override
           public void OnClickListener() {
               Log.i("Swipeable Cards","I am pressing the card");
           }
        });

        cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards","I like the card");
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards","I dislike the card");
            }
        });

        adapter.add(cardModel);

		mCardContainer.setAdapter(adapter);
	}
}
