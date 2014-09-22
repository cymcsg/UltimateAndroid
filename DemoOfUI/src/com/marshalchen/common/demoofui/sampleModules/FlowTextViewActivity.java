package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.flowtextview.FlowTextView;


public class FlowTextViewActivity extends ActionBarActivity implements View.OnClickListener {

    private static final float defaultFontSize = 20.0f;

    private FlowTextView flowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_text_view_main);
        flowTextView = (FlowTextView) findViewById(R.id.ftv);
        String content = "<![CDATA[<html>Lorem <b>ipsum dolor</b> sit amet, consectetur adipiscing elit. In sed massa diam. Integer leo tortor, sagittis eget blandit et, malesuada id odio. Proin gravida dui imperdiet, tempor augue eu, euismod odio. Vivamus nec euismod neque. Nam sed turpis ac arcu consectetur tristique sed et odio. Vivamus posuere aliquet mi vitae bibendum. Suspendisse potenti. Morbi mattis sed nisl sit amet tristique. Nunc mattis porta sem, nec eleifend metus pellentesque non. Mauris tincidunt justo eu magna bibendum feugiat. Suspendisse semper risus dui, non ullamcorper metus scelerisque eu. Proin suscipit nisi non arcu egestas, in adipiscing neque pharetra. Praesent ullamcorper hendrerit enim. Donec in est eget ligula fringilla dictum. Maecenas sodales sem in lacus imperdiet convallis.\n" +
                "\n" +
                "Vestibulum urna mi, eleifend vel massa ut, facilisis lacinia lacus. Mauris sodales dolor eu ligula placerat, et interdum lectus mattis. Nulla tempor hendrerit odio, et suscipit enim convallis in. Morbi vehicula risus lectus. Integer mattis, lacus tincidunt tristique egestas, orci lorem tristique dolor, quis aliquam sem arcu nec eros. Aenean dui est, adipiscing quis massa id, scelerisque euismod orci. Fusce in lacus id nisi hendrerit elementum. In nec lacus luctus, volutpat odio ut, ullamcorper enim. Pellentesque dapibus massa ut magna rutrum, in accumsan lorem feugiat. Vivamus luctus elit tellus.\n" +
                "\n" +
                "Fusce sit amet tempus libero. Nunc feugiat dignissim purus vitae tempus. Nam adipiscing nulla ac urna congue, sed pulvinar lacus sagittis. Curabitur venenatis elit eget scelerisque ultrices. Ut porta magna pellentesque, convallis metus at, laoreet leo. Sed ac cursus mi. Maecenas mauris diam, luctus ac tortor aliquet, pretium laoreet odio. Nunc tincidunt a sem ut ornare. Donec suscipit tincidunt lacus, in consectetur neque facilisis eget.\n" +
                "\n" +
                "Nullam imperdiet quam in dolor venenatis, sit amet venenatis nisi <b>eleifend</b>. Maecenas ligula lorem, egestas ut adipiscing in, gravida a est. Nam luctus massa in quam viverra, et suscipit nulla cursus. Ut ut vulputate enim. Mauris vulputate felis a quam varius sodales sit amet et augue. Proin sagittis mollis tempor. Curabitur nec quam in risus volutpat sagittis quis placerat sapien. Proin vulputate, ante vitae commodo auctor, quam nisi convallis lectus, eget sodales ligula arcu ac nulla. Praesent vulputate nec libero vel pulvinar.\n" +
                "\n" +
                "Donec feugiat ac nunc vel laoreet. In laoreet pretium fringilla. Suspendisse potenti. Praesent nec justo sagittis, scelerisque sem in, <b>egestas</b> massa. Vestibulum sed massa quis velit vehicula hendrerit vitae eget dui. Ut fringilla, lectus eget tristique tristique, orci urna consequat ligula, vitae hendrerit ipsum dui non sem. Duis lacinia arcu at libero dignissim facilisis. Quisque in ullamcorper dui.</html>]]>";
        Spanned html = Html.fromHtml(content);
        flowTextView.setText(html);


        Button btnIncreasefontSize = (Button) findViewById(R.id.btn_increase_font_size);
        btnIncreasefontSize.setOnClickListener(this);
        Button btnDecreasefontSize = (Button) findViewById(R.id.btn_decrease_font_size);
        btnDecreasefontSize.setOnClickListener(this);
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_increase_font_size:
                increaseFontSize();
                break;
            case R.id.btn_decrease_font_size:
                decreaseFontSize();
                break;
            case R.id.btn_reset:
                reset();
                break;
            default:
                break;
        }
    }

    private void increaseFontSize(){
        float currentFontSize = flowTextView.getTextsize();
        flowTextView.setTextSize(currentFontSize+1);
    }

    private void decreaseFontSize(){
        float currentFontSize = flowTextView.getTextsize();
        flowTextView.setTextSize(currentFontSize-1);
    }

    private void reset(){
        flowTextView.setTextSize(defaultFontSize);
    }
}
