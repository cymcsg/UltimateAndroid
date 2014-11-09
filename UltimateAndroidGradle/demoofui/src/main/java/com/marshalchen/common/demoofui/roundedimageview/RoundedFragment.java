package com.marshalchen.common.demoofui.roundedimageview;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.roundedimageview.RoundedImageView;

public class RoundedFragment extends Fragment {

  static final String ARG_IS_OVAL = "is_oval";

  private boolean isOval = false;

  public static RoundedFragment getInstance(boolean isOval) {
    RoundedFragment f = new RoundedFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_IS_OVAL, isOval);
    f.setArguments(args);
    return f;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      isOval = getArguments().getBoolean(ARG_IS_OVAL);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.rounded_image_view_fragment_rounded, container, false);

    StreamAdapter adapter = new StreamAdapter(getActivity());

    adapter.add(new StreamItem(getActivity(), R.drawable.test, "Tufa at night", "Mono Lake, CA",
        ScaleType.CENTER));
    adapter.add(new StreamItem(getActivity(), R.drawable.test_back, "Starry night", "Lake Powell, AZ",
        ScaleType.CENTER_CROP));
    adapter.add(
        new StreamItem(getActivity(), R.drawable.test_back1, "Racetrack playa", "Death Valley, CA",
            ScaleType.CENTER_INSIDE));
    adapter.add(
        new StreamItem(getActivity(), R.drawable.test_back2, "Napali coast", "Kauai, HI",
            ScaleType.FIT_CENTER));
    adapter.add(
        new StreamItem(getActivity(), R.drawable.test, "Delicate Arch", "Arches, UT",
            ScaleType.FIT_END));
    adapter.add(new StreamItem(getActivity(), R.drawable.test_back1, "Sierra sunset", "Lone Pine, CA",
        ScaleType.FIT_START));
    adapter.add(
        new StreamItem(getActivity(), R.drawable.test_back2, "Majestic", "Grand Teton, WY",
            ScaleType.FIT_XY));

    ((ListView) view.findViewById(R.id.main_list)).setAdapter(adapter);
    return view;
  }

  class StreamItem {
    final Bitmap mBitmap;
    final String mLine1;
    final String mLine2;
    final ScaleType mScaleType;

    StreamItem(Context c, int resid, String line1, String line2, ScaleType scaleType) {
      mBitmap = BitmapFactory.decodeResource(c.getResources(), resid);
      mLine1 = line1;
      mLine2 = line2;
      mScaleType = scaleType;
    }
  }

  class StreamAdapter extends ArrayAdapter<StreamItem> {
    private final LayoutInflater mInflater;

    public StreamAdapter(Context context) {
      super(context, 0);
      mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewGroup view;
      if (convertView == null) {
        view = (ViewGroup) mInflater.inflate(R.layout.rounded_image_view_frounded_item, parent, false);
      } else {
        view = (ViewGroup) convertView;
      }

      StreamItem item = getItem(position);

      RoundedImageView iv = ((RoundedImageView) view.findViewById(R.id.imageView1));
      iv.setOval(isOval);
      iv.setImageBitmap(item.mBitmap);
      iv.setScaleType(item.mScaleType);
      ((TextView) view.findViewById(R.id.textView1)).setText(item.mLine1);
      ((TextView) view.findViewById(R.id.textView2)).setText(item.mLine2);
      ((TextView) view.findViewById(R.id.textView3)).setText(item.mScaleType.toString());
      return view;
    }
  }
}
