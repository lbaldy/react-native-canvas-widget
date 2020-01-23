package pl.baldy.rncanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MyCanvas extends View {

    Paint paint;
    ArrayList<Rect> rects = new ArrayList<>();
    Path path;
    ArrayList<Command> commands;

    public String getStringCommands() {
        return stringCommands;
    }

    public void setStringCommands(String stringCommands) {
        this.stringCommands = stringCommands;
        Gson gson = new Gson();
        Command[] html5Commands = gson.fromJson(stringCommands, Command[].class);
        if (html5Commands != null) {
            commands = new ArrayList<>(Arrays.asList(html5Commands));
        }

    }

    String stringCommands;
    Path.Direction direction = Path.Direction.CW;

    Integer[] startingPoint = new Integer[2];

    public MyCanvas(Context context) {
        super(context);
    }

    public void executeCommands(Canvas canvas) {
        if (commands != null) {
            Iterator<Command> it;
            it = commands.iterator();
            paint = new Paint();

            Double[] doubleValues;
            String[] stringValues;


            while (it.hasNext()) {
                Command command = it.next();

                switch (command.getName()) {
                    case "save":
                        canvas.save();
                        break;
                    case "restore":
                        canvas.restore();
                        break;
                    case "beginPath":
                        path = new Path();
                        paint = new Paint();
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(6);
                        break;
                    case "closePath":
                        path.close();
                        break;
                    case "rect":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        path.addRect(
                                new RectF(
                                        doubleValues[0].floatValue(),
                                        doubleValues[1].floatValue(),
                                        doubleValues[2].floatValue(),
                                        doubleValues[3].floatValue()),
                                direction);
                        break;
                    case "lineTo":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        path.lineTo(doubleValues[0].floatValue(), doubleValues[1].floatValue());
                        break;
                    case "strokeStyle":
                        stringValues = command.getValue().toArray(new String[0]);
                        paint.setColor(Color.parseColor(stringValues[0]));
                        break;
                    case "fill":
                        paint.setStyle(Paint.Style.FILL);
                        break;
                    case "stroke":
                        canvas.drawPath(path, paint);
                        break;
                    case "moveTo":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        startingPoint[0] = doubleValues[0].intValue();
                        startingPoint[1] = doubleValues[1].intValue();
                        path.moveTo(doubleValues[0].intValue(), doubleValues[1].intValue());
                        break;
                    case "bezierCurveTo":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        path.cubicTo(
                                doubleValues[0].floatValue(),
                                doubleValues[1].floatValue(),

                                doubleValues[2].floatValue(),
                                doubleValues[3].floatValue(),

                                doubleValues[4].floatValue(),
                                doubleValues[5].floatValue()


                        );
                        break;
                    case "quadraticCurveTo":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        path.quadTo(
                                doubleValues[0].floatValue(),
                                doubleValues[1].floatValue(),
                                doubleValues[2].floatValue(),
                                doubleValues[3].floatValue()
                        );
                        break;
                    case "arcTo":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        float angleMultiplier = 1;
                        if (startingPoint[1] > doubleValues[1].floatValue()) {
                            angleMultiplier = -1;
                        }

                        path.arcTo(
                                (doubleValues[0].floatValue() - doubleValues[4].floatValue()) - doubleValues[4].floatValue(),
                                (doubleValues[1].floatValue() - doubleValues[4].floatValue()) - doubleValues[4].floatValue(),
                                (doubleValues[0].floatValue() - doubleValues[4].floatValue()) + doubleValues[4].floatValue(),
                                (doubleValues[1].floatValue() + doubleValues[4].floatValue()) + doubleValues[4].floatValue(),
                                0,
                                74 * angleMultiplier,
                                false
                        );
                        break;
                    case "arc":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        path.addArc(doubleValues[0].floatValue() - doubleValues[2].floatValue() / 2,
                                doubleValues[1].floatValue() - doubleValues[2].floatValue() / 2,
                                doubleValues[0].floatValue() + doubleValues[2].floatValue() / 2,
                                doubleValues[1].floatValue() + doubleValues[2].floatValue() / 2,
                                doubleValues[3].floatValue() * ((Double) (180 / Math.PI)).floatValue(),
                                doubleValues[4].floatValue() * ((Double) (180 / Math.PI)).floatValue());
                        break;
                    // TRANSFORM
                    case "scale":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        canvas.scale(doubleValues[0].floatValue(), doubleValues[1].floatValue());
                        break;
                    case "rotate":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        canvas.rotate(doubleValues[0].floatValue() * ((Double) (180 / Math.PI)).floatValue());
                        break;
                    case "translate":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        canvas.translate(doubleValues[0].floatValue(), doubleValues[1].floatValue());
                        break;
                    case "setTransform":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        canvas.scale(doubleValues[0].floatValue(), doubleValues[3].floatValue());
                        canvas.translate(doubleValues[4].floatValue(), doubleValues[5].floatValue());
                        canvas.skew(doubleValues[2].floatValue(), doubleValues[1].floatValue());
                        break;
                    // PROPERTIES
                    case "fillStyle":
                        String[] values = command.getValue().toArray(new String[0]);
                        paint.setColor(Color.parseColor(values[0]));
                        break;

                    // RECTANGLES
                    case "fillRect":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        canvas.drawRect(doubleValues[0].floatValue(), doubleValues[1].floatValue(), doubleValues[2].floatValue(), doubleValues[3].floatValue(), paint);
                        break;
                    case "strokeRect":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        Path rectPath = new Path();
                        Paint rectPaint = new Paint();
                        rectPaint.setColor(paint.getColor());
                        rectPaint.setStrokeWidth(paint.getStrokeWidth());
                        rectPaint.setStyle(Paint.Style.STROKE);
                        rectPath.addRect(
                                new RectF(
                                        doubleValues[0].floatValue(),
                                        doubleValues[1].floatValue(),
                                        doubleValues[2].floatValue(),
                                        doubleValues[3].floatValue()),
                                direction);
                        canvas.drawPath(rectPath, rectPaint);
                    case "clearRect":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        Paint white = new Paint();
                        white.setColor(Color.WHITE);
                        Rect clearRect = new Rect(doubleValues[0].intValue(), doubleValues[1].intValue(), doubleValues[0].intValue() + doubleValues[2].intValue(), doubleValues[1].intValue() + doubleValues[3].intValue());

                        canvas.drawRect(clearRect, white);
                        break;
                    case "drawImage":
                        doubleValues = command.getValue().toArray(new Double[0]);
                        byte[] byteArray = Base64.decode("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", Base64.DEFAULT);
                        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                        if (doubleValues.length > 2) {
                            Rect dst = new Rect();
                            Rect src = new Rect();
                            src.set(doubleValues[0].intValue(), doubleValues[1].intValue(), doubleValues[0].intValue() + doubleValues[2].intValue(), doubleValues[1].intValue() + doubleValues[3].intValue());
                            dst.set(doubleValues[4].intValue(), doubleValues[5].intValue(), doubleValues[4].intValue() + doubleValues[6].intValue(), doubleValues[5].intValue() + doubleValues[7].intValue());
                            canvas.drawBitmap(image, src, dst, paint);
                        } else {
                            canvas.drawBitmap(image, doubleValues[0].floatValue(), doubleValues[1].floatValue(), paint);
                        }

                        break;
                }
            }


        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        executeCommands(canvas);


    }

}
