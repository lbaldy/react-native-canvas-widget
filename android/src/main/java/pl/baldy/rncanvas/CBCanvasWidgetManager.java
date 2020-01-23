package pl.baldy.rncanvas;

import android.app.ActionBar;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;

import java.util.Map;


public class CBCanvasWidgetManager extends SimpleViewManager<View> {

    public static final String REACT_CLASS = "CBCanvasWidget";

    @ReactProp(name = "commands")
    public void setCommands(CanvasView canvas, String commands) {
        canvas.setStringCommands(commands);
    }

    ImageView img;

    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put(
                        "topChange",
                        MapBuilder.of(
                                "phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onChange")))
                .build();
    }


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public View createViewInstance(ThemedReactContext c) {
        return new CanvasView(c);
    }
}
