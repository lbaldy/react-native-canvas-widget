package pl.baldy.rncanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;

public class CanvasView extends LinearLayout {
    Context context;
    Canvas canvas;
    MyCanvas myCanvasView;

    public void setStringCommands(String stringCommands) {
        this.stringCommands = stringCommands;
        myCanvasView.setStringCommands(stringCommands);
        canvas.drawColor(Color.BLACK);
        myCanvasView.draw(canvas);
//        myCanvasView.invalidate();
    }

    String stringCommands;

    ImageView img;

    public CanvasView(Context context) {
        super(context);
        this.context = context;
        View canvasView = inflate(context, R.layout.canvas_layout, this);
        img = canvasView.findViewById(R.id.image);
        myCanvasView = new MyCanvas(context);
//        myCanvasView.setStringCommands("[{\"name\":\"beginPath\",\"value\":[]},{\"name\":\"lineWidth\",\"value\":[5]},{\"name\":\"strokeStyle\",\"value\":[\"#aaaedd\"]},{\"name\":\"moveTo\",\"value\":[20,20]},{\"name\":\"bezierCurveTo\",\"value\":[20,100,200,100,200,20]},{\"name\":\"quadraticCurveTo\",\"value\":[20,100,200,20]},{\"name\":\"rect\",\"value\":[20,20,350,300]},{\"name\":\"arc\",\"value\":[100,75,50,0,6.283185307179586]},{\"name\":\"stroke\",\"value\":[]}]");
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        img.setImageBitmap(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        myCanvasView.draw(canvas);
    }
}
