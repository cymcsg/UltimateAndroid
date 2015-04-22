package com.marshalchen.common.demoofui.snackbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.marshalchen.common.demoofui.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.ActionSwipeListener;
import com.nispok.snackbar.listeners.EventListener;


public class SnackbarSampleActivity extends ActionBarActivity {

    private static final String TAG = SnackbarSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_bar_activity_sample);

        Button singleLineButton = (Button) findViewById(R.id.single_line);
        singleLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("Single-line snackbar"));
            }
        });

        Button singleLineWithActionButton = (Button) findViewById(R.id.single_line_with_action);
        singleLineWithActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("Something has been done")
                                .actionLabel("Undo")
                                .swipeListener(new ActionSwipeListener() {
                                    @Override
                                    public void onSwipeToDismiss() {
                                        Toast.makeText(SnackbarSampleActivity.this,
                                                "swipe to dismiss",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .actionListener(new ActionClickListener() {
                                    @Override
                                    public void onActionClicked(Snackbar snackbar) {
                                        Toast.makeText(SnackbarSampleActivity.this,
                                                "Action undone",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }));
            }
        });

        Button multiLineButton = (Button) findViewById(R.id.multi_line);
        multiLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .type(SnackbarType.MULTI_LINE)
                                .text("This is a multi-line snackbar. Keep in mind that snackbars" +
                                        " are meant for VERY short messages"));
            }
        });

        Button multiLineWithActionButton = (Button) findViewById(R.id.multi_line_with_action);
        multiLineWithActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .type(SnackbarType.MULTI_LINE)
                                .text("This is a multi-line snackbar with an action button. Note " +
                                        "that multi-line snackbars are 2 lines max")
                                .actionLabel("Action")
                                .actionListener(new ActionClickListener() {
                                    @Override
                                    public void onActionClicked(Snackbar snackbar) {
                                        Toast.makeText(SnackbarSampleActivity.this,
                                                "Action clicked",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }));
            }
        });

        Button noAnimationButton = (Button) findViewById(R.id.no_animation);
        noAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("No animation :(")
                                .animation(false)
                                .duration(2500l));
            }
        });

        Button eventListenerButton = (Button) findViewById(R.id.event_listener);
        eventListenerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("I'm showing a toast on exit")
                                .eventListener(new EventListener() {
                                    @Override
                                    public void onShow(Snackbar snackbar) {
                                        Log.i(TAG, String.format(
                                                "Snackbar will show. Width: %d Height: %d Offset: %d",
                                                snackbar.getWidth(), snackbar.getHeight(),
                                                snackbar.getOffset()));
                                    }

                                    @Override
                                    public void onShowByReplace(Snackbar snackbar) {
                                        Log.i(TAG, String.format(
                                                "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                                snackbar.getWidth(), snackbar.getHeight(),
                                                snackbar.getOffset()));
                                    }

                                    @Override
                                    public void onShown(Snackbar snackbar) {
                                        Log.i(TAG, String.format(
                                                "Snackbar shown. Width: %d Height: %d Offset: %d",
                                                snackbar.getWidth(), snackbar.getHeight(),
                                                snackbar.getOffset()));
                                    }

                                    @Override
                                    public void onDismiss(Snackbar snackbar) {
                                        Log.i(TAG, String.format(
                                                "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                                snackbar.getWidth(), snackbar.getHeight(),
                                                snackbar.getOffset()));
                                    }

                                    @Override
                                    public void onDismissByReplace(Snackbar snackbar) {
                                        Log.i(TAG, String.format(
                                                "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                                snackbar.getWidth(), snackbar.getHeight(),
                                                snackbar.getOffset()));
                                    }

                                    @Override
                                    public void onDismissed(Snackbar snackbar) {
                                        Toast.makeText(SnackbarSampleActivity.this, String.format(
                                                        "Snackbar dismissed. Width: %d Height: %d Offset: %d",
                                                        snackbar.getWidth(), snackbar.getHeight(),
                                                        snackbar.getOffset()),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }));
            }
        });

        Button customColorsButton = (Button) findViewById(R.id.custom_colors);
        customColorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("Different colors!!!")
                                .textColor(Color.parseColor("#ff9d9d9c"))
                                .color(Color.parseColor("#ff914300"))
                                .actionLabel("Action")
                                .actionColor(Color.parseColor("#ff5a2900"))
                                .actionListener(new ActionClickListener() {
                                    @Override
                                    public void onActionClicked(Snackbar snackbar) {
                                        Log.i(TAG, "Action touched");
                                    }
                                })
                                .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
            }
        });

        Button unswipeableButton = (Button) findViewById(R.id.unswipeable);
        unswipeableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .text("Try to swipe me off from the screen")
                                .swipeToDismiss(false));
            }
        });

        Button indefiniteButton = (Button) findViewById(R.id.indefinite);
        indefiniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                                .type(SnackbarType.MULTI_LINE)
                                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                                .text("Indefinite duration, ideal for communicating errors"));
            }
        });

        Button listSampleButton = (Button) findViewById(R.id.list_example);
        listSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sampleIntent = new Intent(SnackbarSampleActivity.this,
                        SnackbarListViewSampleActivity.class);
                startActivity(sampleIntent);
            }
        });

        Button recyclerSampleButton = (Button) findViewById(R.id.recycler_example);
        recyclerSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sampleIntent = new Intent(SnackbarSampleActivity.this,
                        SnackbarRecyclerViewSampleActivity.class);
                startActivity(sampleIntent);
            }
        });

        Button customTypefaceButton = (Button) findViewById(R.id.typeface_example);
        customTypefaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-LightItalic.ttf");
                SnackbarManager.show(
                        Snackbar.with(SnackbarSampleActivity.this)
                        .text("Custom font!")
                        .textTypeface(tf)
                        .actionLabel("Cool")
                        .actionLabelTypeface(tf));
            }
        });

        Button navigationBarTranslucentButton = (Button) findViewById(R.id.navigation_bar_translucent);
        navigationBarTranslucentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SnackbarNavigationBarTranslucentSampleActivity.isTranslucentSystemBarsCapable()) {
                    Intent sampleIntent = new Intent(SnackbarSampleActivity.this,
                            SnackbarNavigationBarTranslucentSampleActivity.class);
                    startActivity(sampleIntent);
                } else {
                    Toast.makeText(SnackbarSampleActivity.this,
                            "Translucent System bars only available for KITKAT or newer",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button immersiveModeButton = (Button) findViewById(R.id.immersive_mode_example);
        immersiveModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SnackbarImmersiveModeSampleActivity.isImmersiveModeCapable()) {
                    Intent sampleIntent = new Intent(SnackbarSampleActivity.this,
                            SnackbarImmersiveModeSampleActivity.class);
                    startActivity(sampleIntent);
                } else {
                    Toast.makeText(SnackbarSampleActivity.this,
                            "Immersive mode only available for KITKAT or newer",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button showInDialogButton = (Button) findViewById(R.id.show_in_dialog_example);
        showInDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sampleIntent = new Intent(SnackbarSampleActivity.this,
                        SnackbarShowInDialogSampleActivity.class);
                startActivity(sampleIntent);
            }
        });

	    Button singleLineMarginsButton = (Button) findViewById(R.id.single_line_margins);
	    singleLineMarginsButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    SnackbarManager.show(
					    Snackbar.with(SnackbarSampleActivity.this)
					            .margin(25)
					            .text("Single-line Margins"));
		    }
	    });

	    Button singleLineTopButton = (Button) findViewById(R.id.single_line_top);
	    singleLineTopButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    SnackbarManager.show(
					    Snackbar.with(SnackbarSampleActivity.this).position(Snackbar.SnackbarPosition.TOP)
					            .text("Single-line Top"));
		    }
	    });


	    Button singleLineButtonInside = (Button) findViewById(R.id.single_line_inside);
	    singleLineButtonInside.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    SnackbarManager.show(
					    Snackbar.with(SnackbarSampleActivity.this)
					            .text("Single-line Inside RelativeLayout")
					    , (android.view.ViewGroup) findViewById(R.id.view_relative_layout));

		    }
	    });

	    Button singleLineTopButtonInside = (Button) findViewById(R.id.single_line_top_inside);
	    singleLineTopButtonInside.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    SnackbarManager.show(
					    Snackbar.with(SnackbarSampleActivity.this)
					            .position(Snackbar.SnackbarPosition.TOP)
					            .margin(25, 15)
					            .text("Single-line TOP Inside LinearLayout")
					    , (android.view.ViewGroup) findViewById(R.id.view_linear_layout));
		    }
	    });

	    Button singleLineButtonCustomShape = (Button) findViewById(R.id.single_line_shape);
	    singleLineButtonCustomShape.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    SnackbarManager.show(
					    Snackbar.with(SnackbarSampleActivity.this)
					            .position(Snackbar.SnackbarPosition.TOP)
					            .margin(15, 15)
					            .backgroundDrawable(R.drawable.snack_bar_custom_shape)
					            .text("Single-line Custom Shape"));
            }
        });
    }


}
