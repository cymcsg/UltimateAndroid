package com.marshalchen.common.demoofui.sampleModules;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dexafree.materialList.MaterialListViewAdapter;
import com.dexafree.materialList.controller.OnButtonPressListener;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.model.BasicButtonsCard;
import com.dexafree.materialList.model.SmallImageCard;
import com.dexafree.materialList.model.BasicImageButtonsCard;
import com.dexafree.materialList.model.BigImageButtonsCard;
import com.dexafree.materialList.model.BigImageCard;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardList;
import com.dexafree.materialList.model.WelcomeCard;
import com.dexafree.materialList.view.MaterialListView;
import com.marshalchen.common.demoofui.R;


public class MaterialListViewActivity extends ActionBarActivity {

    private MaterialListView mListView;
    private CardList cardsList;
    private Context mContext;
    private MaterialListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_list_activity_main);

        mContext = this;

        mListView = (MaterialListView) findViewById(R.id.material_listview);

        cardsList = new CardList();

        fillArray();

        adapter = new MaterialListViewAdapter(this, cardsList);

        mListView.setMaterialListViewAdapter(adapter);

        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {
                //Toast.makeText(mContext, "CARD NUMBER "+position+" dismissed", //Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fillArray(){
        for(int i=0;i<35;i++){
            Card card = getRandomCard(i);
            cardsList.add(card);
        }
    }

    private Card getRandomCard(final int position){
        String title = "Card number "+(position+1);
        String description = "Lorem ipsum dolor sit amet";

        int type = position % 5;

        final Card card;
        Drawable icon;

        switch (type){

            case 0:
                card = new SmallImageCard();
                card.setDescription(description);
                card.setTitle(title);
                icon = getResources().getDrawable(R.drawable.ic_launcher);
                card.setBitmap(icon);
                return card;

            case 1:
                card = new BigImageCard();
                card.setDescription(description);
                card.setTitle(title);
                icon = getResources().getDrawable(R.drawable.test);
                card.setBitmap(icon);
                card.setCanDismiss(false);
                return card;

            case 2:
                card = new BasicImageButtonsCard();
                card.setDescription(description);
                card.setTitle(title);
                icon = getResources().getDrawable(R.drawable.test_back);
                card.setBitmap(icon);
                ((BasicImageButtonsCard)card).setLeftButtonText("LEFT");
                ((BasicImageButtonsCard)card).setRightButtonText("RIGHT");

                if (position % 2 == 0)
                    ((BasicImageButtonsCard)card).setShowDivider(true);

                ((BasicImageButtonsCard)card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                        adapter.getItem(0).setTitle("CHANGED ON RUNTIME");
                        adapter.notifyDataSetChanged();
                    }
                });

                ((BasicImageButtonsCard)card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                    }
                });

                return card;

            case 3:
                card = new BasicButtonsCard();
                card.setDescription(description);
                card.setTitle(title);
                ((BasicButtonsCard)card).setLeftButtonText("LEFT");
                ((BasicButtonsCard)card).setRightButtonText("RIGHT");

                if (position % 2 == 0)
                    ((BasicButtonsCard)card).setShowDivider(true);

                ((BasicButtonsCard)card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BasicButtonsCard)card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                    }
                });


                return card;

            case 4:
                card = new WelcomeCard();
                card.setTitle("Welcome Card");
                card.setDescription("I am the description");
                ((WelcomeCard)card).setSubtitle("My subtitle!");
                ((WelcomeCard)card).setButtonText("Okay!");
                ((WelcomeCard)card).setOnButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "A welcome card has been dismissed", Toast.LENGTH_SHORT).show();
                    }
                });

                if(position%2 == 0)
                    ((WelcomeCard)card).setBackgroundColorFromId(mContext, R.color.background_material_dark);

                return card;

            default:
                card = new BigImageButtonsCard();
                card.setDescription(description);
                card.setTitle(title);
                icon = getResources().getDrawable(R.drawable.test_back2);
                card.setBitmap(icon);
                ((BigImageButtonsCard)card).setLeftButtonText("ADD CARD");
                ((BigImageButtonsCard)card).setRightButtonText("RIGHT BUTTON");

                if (position % 2 == 0) {
                    ((BigImageButtonsCard) card).setShowDivider(true);
                }

                ((BigImageButtonsCard)card).setOnLeftButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Log.d("ADDING", "CARD");

                        CardList newCards = new CardList();
                        newCards.add(generateNewCard());

                        mListView.addCardsToExistingAdapter(newCards);
                        Toast.makeText(mContext, "Added new card", Toast.LENGTH_SHORT).show();
                    }
                });

                ((BigImageButtonsCard)card).setOnRightButtonPressedListener(new OnButtonPressListener() {
                    @Override
                    public void onButtonPressedListener(TextView textView) {
                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                    }
                });


                return card;

        }

    }

    private Card generateNewCard(){
        Card card = new BasicImageButtonsCard();
        card.setBitmap(mContext, R.drawable.test_back1);
        card.setTitle("I'm new");
        card.setDescription("I've been generated on runtime!");

        return card;
    }
}
