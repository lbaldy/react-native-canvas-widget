package pl.baldy.rncanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class CanvasView extends LinearLayout {
    Context context;

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
        init();
    }

    String commands;

    ImageView img;

    public CanvasView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init() {
        View canvasView = inflate(context, R.layout.canvas_layout, this);
        if(commands != null) {
            Log.d("img cmd has value", commands);

            //        String commandsJson = "[{\"name\":\"fillStyle\",\"value\":[\"#00eaee\"]},{\"name\":\"fillRect\",\"value\":[20,20,150,100]},{\"name\":\"strokeStyle\",\"value\":[\"#000000\"]},{\"name\":\"lineTo\",\"value\":[300,150]},{\"name\":\"fillRect\",\"value\":[120,120,250,200]}]";
//        String commandsJson = "[{\"name\":\"fillStyle\",\"value\":[\"#00eaee\"]},{\"name\":\"fillRect\",\"value\":[20,20,150,100]},{\"name\":\"strokeStyle\",\"value\":[\"#00ee1c\"]},{\"name\":\"lineTo\",\"value\":[300,150]},{\"name\":\"fillStyle\",\"value\":[\"#eee600\"]},{\"name\":\"fillRect\",\"value\":[120,120,250,200]}]";
//        String commandsJson = "[{\"name\":\"fillStyle\",\"value\":[\"#00eaee\"]},{\"name\":\"fillRect\",\"value\":[20,20,150,100]},{\"name\":\"strokeStyle\",\"value\":[\"#00ee1c\"]},{\"name\":\"moveTo\",\"value\":[0,0]},{\"name\":\"lineTo\",\"value\":[300,150]},{\"name\":\"fillStyle\",\"value\":[\"#eee600\"]},{\"name\":\"fillRect\",\"value\":[120,120,250,200]},{\"name\":\"strokeStyle\",\"value\":[\"#e600ee\"]},{\"name\":\"moveTo\",\"value\":[250,250]},{\"name\":\"lineTo\",\"value\":[600,450]}]";
//        String commandsJson = "[{\"name\":\"lineWidth\",\"value\":[15]},{\"name\":\"strokeStyle\",\"value\":[\"#00ee1c\"]},{\"name\":\"moveTo\",\"value\":[20,20]},{\"name\":\"lineTo\",\"value\":[20,100]},{\"name\":\"fillStyle\",\"value\":[\"#eee600\"]},{\"name\":\"lineTo\",\"value\":[70,100]}]";
//        String commandsJson = "[{\"name\":\"beginPath\",\"value\":[]},{\"name\":\"lineWidth\",\"value\":[5]},{\"name\":\"strokeStyle\",\"value\":[\"#00ee1c\"]},{\"name\":\"moveTo\",\"value\":[20,20]},{\"name\":\"bezierCurveTo\",\"value\":[20,100,200,100,200,20]},{\"name\":\"quadraticCurveTo\",\"value\":[20,100,200,20]},{\"name\":\"rect\",\"value\":[20,20,350,300]},{\"name\":\"arc\",\"value\":[100,75,50,0,6.283185307179586]},{\"name\":\"stroke\",\"value\":[]}]";
//        String commandsJson = "[{\"name\":\"beginPath\",\"value\":[]},{\"name\":\"lineWidth\",\"value\":[5]},{\"name\":\"strokeStyle\",\"value\":[\"#00ee1c\"]},{\"name\":\"moveTo\",\"value\":[180,129]},{\"name\":\"arcTo\",\"value\":[180,130,110,130,130]},{\"name\":\"stroke\",\"value\":[]}]";
//        String commandsJson = "[{\"name\":\"fillStyle\",\"value\":[\"#00FFbb\"]},{\"name\":\"fillRect\",\"value\":[0, 0, 300, 150]},{\"name\":\"clearRect\",\"value\":[20, 20, 100, 50]}]";
//        CanvasView canvasView = new CanvasView(context);
            Gson gson = new Gson();
            Command[] commands = gson.fromJson(getCommands(), Command[].class);
            img = (ImageView) canvasView.findViewById(R.id.image);
            View view = new MyCanvas(context, commands);

            Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            img.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("myimage", encoded);
        }

    }
}
