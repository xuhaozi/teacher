package com.example.admin.musicclassroom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.example.admin.musicclassroom.Utils.Constact;
import com.example.admin.musicclassroom.Utils.Constant;
import com.example.admin.musicclassroom.musicentity.beans.Articulations;
import com.example.admin.musicclassroom.musicentity.beans.BarLine;
import com.example.admin.musicclassroom.musicentity.beans.Beam;
import com.example.admin.musicclassroom.musicentity.beans.BeamXY;
import com.example.admin.musicclassroom.musicentity.beans.Direction;
import com.example.admin.musicclassroom.musicentity.beans.DirectionType;
import com.example.admin.musicclassroom.musicentity.beans.DirectionXY;
import com.example.admin.musicclassroom.musicentity.beans.Dot;
import com.example.admin.musicclassroom.musicentity.beans.Dynamics;
import com.example.admin.musicclassroom.musicentity.beans.Fermata;
import com.example.admin.musicclassroom.musicentity.beans.Forward;
import com.example.admin.musicclassroom.musicentity.beans.Lyric;
import com.example.admin.musicclassroom.musicentity.beans.Measure;
import com.example.admin.musicclassroom.musicentity.beans.Notations;
import com.example.admin.musicclassroom.musicentity.beans.Note;
import com.example.admin.musicclassroom.musicentity.beans.Ornaments;
import com.example.admin.musicclassroom.musicentity.beans.Pitch;
import com.example.admin.musicclassroom.musicentity.beans.SlurXY;
import com.example.admin.musicclassroom.musicentity.beans.Sound;
import com.example.admin.musicclassroom.musicentity.beans.Time;
import com.example.admin.musicclassroom.musicentity.beans.Words;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CY on 2017/7/29.
 */


public class LineView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private List<List<List<Measure>>> pageMeasure;
    private int currentPage;
    private Context context;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private float Leftx;
    private int width = 0;
    private int firstLineX;
    private int firstLineY;
    private List<Beam> beamList;
    //    private Typeface customFontMusical;
    private Typeface customFont;
    private ArrayList<SlurXY> slurXYList;
    private float tempX;
    private int ceil;
    private double incrementX;
    private List<Dot> isDot;
    private Notations notations;
    private List<Direction> direction;
    private MediaPlayer mediaPlayer;
    private int measureReading = 0, noteReading = 0, lineReading = 0;
    private float measureBackWidth = 0;
    private Paint paint;
    private int height;
    private int currentLine = 0;
    private int currentMeasure = 0;
    private boolean canmove = false;
    List<List<Measure>> mList;

    private OnClickXiaoJieListener mListener;
    private MusicView mMusicView = null;
    private ComposeContainer container;
    private Forward forward;

    public LineView(Context context, List<List<Measure>> lists, int i, int width, int height, List<List<List<Measure>>> pagesMeasure, MediaPlayer mediaPlayer, MusicView musicView) {
        super(context);
        this.context = context;
        this.mList = lists;
        this.currentPage = i;
        this.width = width;
        this.height = height;
        this.pageMeasure = pagesMeasure;
        this.mediaPlayer = mediaPlayer;
        this.mMusicView = musicView;
        initView();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setMediaPlayer(MediaPlayer play) {
        this.mediaPlayer = play;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        Draw();
    }


    /**
     * 初始化三个对象
     */
    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        container = new ComposeContainer(context);
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "Fonts/FreeSerifMscore.ttf");
//        customFontMusical = Typeface.createFromAsset(context.getAssets(), "Fonts/MUSICAL(1).ttf");
//        customFontMusical = Typeface.createFromAsset(context.getAssets(), "Fonts/MUSICAL.TTF");

//                Typeface customFont = Typeface.createFromAsset(context.getAssets(), "Fonts/mscore1-20.ttf");
        customFont = Typeface.createFromAsset(context.getAssets(), "Fonts/mscore-20.ttf");
        paint = new Paint();
        paint.setTypeface(customFont);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(50);
        paint.setTextSize(55);

    }


    /**
     * 开始绘制
     */
    private void Draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
//            canvas.drawColor(Color.parseColor("#00FFFFFF"));
            canvas.drawColor(Color.parseColor("#ffffff"));
            drawBlackScore();
            incrementX = pageMeasure.get(0).get(0).get(0).getNotes().get(0).getDefaultX();
//


            /**
             * 用在外面
             */
//            lists = loadLineMeasure(drawMeasureList);

            for (int i = 0; i < mList.size(); i++) {
                List<Measure> list = mList.get(i);//得到第i行应该展示的小节
                float totalLine = 0;
                totalLine = getTotalLine(list);
                drawNote(list, paint, i, totalLine);
            }
            if (canmove) {
                Paint paint1 = new Paint();
                paint1.setColor(Color.YELLOW);
                paint1.setStyle(Paint.Style.STROKE);
                paint1.setStrokeWidth(6);
                RectF rect = new RectF();
                List<Measure> list = mList.get(currentLine);
                canmove = false;
                if (mMusicView.isPlaying) {
                    int duration = mediaPlayer.getDuration();
                    measureReading = currentMeasure;
                    lineReading = currentLine;
                    //// TODO: 2017/7/30
                    float totalLine = getTotalLine(list);
                    float measureCurrentLine = getMeasureCurrentLine(list, totalLine);
                    float totalMeasure = getTotalMeasure(list, measureReading, lineReading, measureCurrentLine);
                    int current = getCurrent();
                    float v = current / totalMeasure;
                    int postion = (int) (v * duration);
                    mListener.setCurrentPostion(postion, measureReading, noteReading, lineReading, currentPage);
                } else {
                    if (currentLine == 0) {
                        if (currentMeasure == 0) {
                            float width = list.get(0).getWidth();

                            rect.left = (float) list.get(0).getNotes().get(0).getDefaultX();
                            rect.top = 40 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            rect.right = width;
                            rect.bottom = 130 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            canvas.drawRect(rect, paint1);
                        } else {
                            float measureCurrentLine = getMeasureCurrentLine(list, getTotalLine(list));
                            float totalMeasure = getTotalMeasureLine(list, currentMeasure, currentLine, measureCurrentLine);
                            rect.left = totalMeasure + measureCurrentLine;
                            rect.top = 40 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            rect.right = totalMeasure + list.get(currentMeasure).getWidth() + measureCurrentLine;
                            rect.bottom = 130 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            canvas.drawRect(rect, paint1);
                        }
                    } else {
                        if (currentMeasure == 0) {
                            float width = list.get(0).getWidth();
                            rect.left = (float) (list.get(0).getNotes().get(0).getDefaultX() + incrementX);
                            rect.top = 40 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            rect.right = (float) (width + incrementX);
                            rect.bottom = 130 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            canvas.drawRect(rect, paint1);
                        } else {
                            float measureCurrentLine = getMeasureCurrentLine(list, getTotalLine(list));
                            float totalMeasure = getTotalMeasureLine(list, currentMeasure, currentLine, measureCurrentLine);
                            rect.left = (float) (totalMeasure) + measureCurrentLine;
                            rect.top = 40 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            rect.right = (float) (totalMeasure) + list.get(currentMeasure).getWidth() + measureCurrentLine;
                            rect.bottom = 130 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * currentLine;
                            canvas.drawRect(rect, paint1);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //对于已经绘制的东西进行提交
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    private float getTotalMeasureLine(List<Measure> list, int i, int currentLine, float measureCurrentLine) {
        float total = 0;
        if (currentPage == 0) {
            for (int j = 0; j < i; j++) {
                float width = list.get(j).getWidth();
                total += width + measureCurrentLine;
            }
        } else {
            for (int j = 0; j < i; j++) {
                float width = list.get(j).getWidth();
                total += width;
            }
        }

        return total;
    }


    /**
     * @param list
     * @return
     */
    private float getTotalLine(List<Measure> list) {
        float totalLine = 0;
        for (int j = 0; j < list.size(); j++) {
            Measure measure = list.get(j);
            totalLine += measure.getWidth();
        }
        return totalLine;
    }


    /**
     * 绘制符杠
     *
     * @param paint
     * @param drawMeasureList
     * @param totalLine
     * @param lines
     */
    private void drawBeam(Paint paint, List<Measure> drawMeasureList, float totalLine, int lines) {
        paint.setColor(Color.WHITE);
        float measureCurrentLine = getMeasureCurrentLine(drawMeasureList, totalLine);
        double total = 0;
        for (int i = 0; i < drawMeasureList.size(); i++) {
            List<Note> notes = drawMeasureList.get(i).getNotes();
            ArrayList<BeamXY> beamXYList = null;
            if (i == 0) {
                total = 0;
            } else {
                total += drawMeasureList.get(i - 1).getWidth() + measureCurrentLine;
            }
            for (int j = 0; j < notes.size(); j++) {
                float realX;
                Note note = notes.get(j);
                double defaultX = note.getDefaultX();
                List<Beam> beamList = note.getBeamList();
                if (beamList != null && beamList.size() > 0) {
                    for (int k = 0; k < beamList.size(); k++) {
                        Beam beam = beamList.get(k);
                        if (beam != null) {
                            String beamContent = beam.getContent();
                            if (beamContent.equalsIgnoreCase("begin")) {
                                beamXYList = new ArrayList<>();
                                BeamXY beamXY = new BeamXY();
                                beamXYList.add(beamXY);
                                float realY = getRealY(note.getPitch(), lines);
                                if (lines != 0) {
                                    realX = (float) (getRealX(defaultX, total) + incrementX);
                                } else {
                                    realX = (float) getRealX(defaultX, total);
                                }
                                if (lines == ceil - 1) {
                                    tempX = realX;
                                } else {
                                    tempX = (realX + measureCurrentLine);
                                }
                                beamXY.setRealX(tempX);
                                beamXY.setRealY(realY);
                            } else if (beamContent.equalsIgnoreCase("end")) {
                                BeamXY beamXY = new BeamXY();
                                beamXYList.add(beamXY);
                                float realY = getRealY(note.getPitch(), lines);
                                if (lines != 0) {
                                    realX = (float) (getRealX(defaultX, total) + incrementX);
                                } else {
                                    realX = (float) getRealX(defaultX, total);
                                }
                                if (lines == ceil - 1) {
                                    tempX = realX;
                                } else {
                                    tempX = (realX + measureCurrentLine);
                                }
                                beamXY.setRealX(tempX);
                                beamXY.setRealY(realY);
                                drawbeam(paint, beamXYList, note, k);
                            } else if (beamContent.equalsIgnoreCase("continue")) {
                                BeamXY beamXY = new BeamXY();
                                beamXYList.add(beamXY);
                                float realY = getRealY(note.getPitch(), lines);
                                if (lines != 0) {
                                    realX = (float) (getRealX(defaultX, total) + incrementX);
                                } else {
                                    realX = (float) getRealX(defaultX, total);
                                }
                                if (lines == ceil - 1) {
                                    tempX = realX;
                                } else {
                                    tempX = (realX + measureCurrentLine);
                                }
                                beamXY.setRealX(tempX);
                                beamXY.setRealY(realY);
                            } else if (beamContent.equalsIgnoreCase("backward hook")) {
                                paint.setStrokeWidth(5);
                                float realY = getRealY(note.getPitch(), lines);
                                if (lines != 0) {
                                    realX = (float) (getRealX(defaultX, total) + incrementX);
                                } else {
                                    realX = (float) getRealX(defaultX, total);
                                }
                                if (lines == ceil - 1) {
                                    tempX = realX;
                                } else {
                                    tempX = (realX + measureCurrentLine);
                                }
                                switch (note.getStem()) {
                                    case "up":
                                        canvas.drawLine(tempX + 5, realY + 10 - 45, tempX + 17, realY + 10 - 45, paint);
                                        break;
                                    case "down":
                                        canvas.drawLine(tempX - 10, realY - 10 + 45, tempX, realY - 10 + 45, paint);
                                        break;
                                }
                                paint.setStrokeWidth(3);
                            } else if (beamContent.equalsIgnoreCase("forward hook")) {
                                paint.setStrokeWidth(5);
                                float realY = getRealY(note.getPitch(), lines);
                                if (lines != 0) {
                                    realX = (float) (getRealX(defaultX, total) + incrementX);
                                } else {
                                    realX = (float) getRealX(defaultX, total);
                                }
                                if (lines == ceil - 1) {
                                    tempX = realX;
                                } else {
                                    tempX = (realX + measureCurrentLine);
                                }
                                switch (note.getStem()) {
                                    case "up":
                                        canvas.drawLine(realX + 15, realY + 10 - 45, realX + 25, realY + 10 - 45, paint);
                                        break;
                                    case "down":
                                        canvas.drawLine(realX, realY - 10 + 45, realX + 10, realY - 10 + 45, paint);
                                        break;
                                }
                                paint.setStrokeWidth(3);
                            }
                        }
                    }
                }


                if (note != null && note.getSlur() != null && note.getSlur().getType() != null) {
                    String type = note.getSlur().getType();
                    if (type.equalsIgnoreCase("start")) {
                        slurXYList = new ArrayList<>();
                        SlurXY slurXY = new SlurXY();
                        slurXYList.add(slurXY);
                        float realY = getRealY(note.getPitch(), lines);
                        if (lines != 0) {
                            realX = (float) (getRealX(defaultX, total) + incrementX);
                        } else {
                            realX = (float) getRealX(defaultX, total);
                        }
                        if (lines == ceil - 1) {
                            tempX = realX;
                        } else {
                            tempX = (realX + measureCurrentLine);
                        }
                        slurXY.setRealX(tempX);
                        slurXY.setRealY(realY);
                    } else if (type.equalsIgnoreCase("stop")) {
                        SlurXY slurXY = new SlurXY();
                        slurXYList.add(slurXY);
                        float realY = getRealY(note.getPitch(), lines);
                        if (lines != 0) {
                            realX = (float) (getRealX(defaultX, total) + incrementX);
                        } else {
                            realX = (float) getRealX(defaultX, total);
                        }
                        if (lines == ceil - 1) {
                            tempX = realX;
                        } else {
                            tempX = (realX + measureCurrentLine);
                        }
                        slurXY.setRealX(tempX);
                        slurXY.setRealY(realY);
                        drawSlur(paint, slurXYList, note);
                    }
                }
            }
        }
    }


    /**
     * 开始进行正式的连音线的绘制
     *
     * @param paint
     * @param slurXYList
     * @param note
     */
    private void drawSlur(Paint paint, ArrayList<SlurXY> slurXYList, Note note) {
        Paint paint1 = new Paint();
        paint1.setStrokeWidth(1);
        paint1.setDither(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.BLACK);
        if (!note.isRest() && !note.getType().equals("whole") && slurXYList != null) {
            SlurXY startslurXY = slurXYList.get(0);
            float startrealX = startslurXY.getRealX();
            float startrealY = startslurXY.getRealY();
            SlurXY endslurXY = slurXYList.get(slurXYList.size() - 1);
            float endsrealX = endslurXY.getRealX();
            float endsrealY = endslurXY.getRealY();
            String stem = note.getStem();
            switch (stem) {
                case "up":
                    RectF oval = new RectF();
                    if (startrealY < endsrealY) {
                        oval.left = startrealX + 10;
                        oval.top = startrealY + 5;
                        oval.right = endsrealX + 15;
                        oval.bottom = endsrealY + 20;
                        canvas.drawArc(oval, 25, 150, false, paint1);
                    } else if (startrealY > endsrealY) {
                        oval.left = startrealX + 5;
                        oval.top = startrealY;
                        oval.right = endsrealX + 20;
                        oval.bottom = endsrealY + 25;
                        canvas.drawArc(oval, 20, 125, false, paint1);
                    }
                    break;
                case "down":
                    RectF oval1 = new RectF();
                    oval1.left = startrealX;
                    oval1.top = startrealY - 25;
                    oval1.right = endsrealX + 15;
                    oval1.bottom = endsrealY - 2;
                    canvas.drawArc(oval1, -150, 150, false, paint1);
                    break;
            }
        }

    }

    /**
     * 开始进行绘制
     *
     * @param paint
     * @param beamXYList
     * @param note
     * @param num
     */
    private void drawbeam(Paint paint, ArrayList<BeamXY> beamXYList, Note note, int num) {
        Path path = null;
        Paint paint1 = new Paint();
        paint1.setStrokeWidth(5);
        paint1.setDither(true);
        paint1.setColor(Color.BLACK);
        float k = 0;
        BeamXY startbeamXY = beamXYList.get(0);
        float startrealX = startbeamXY.getRealX();
        float startrealY = startbeamXY.getRealY();
        BeamXY endbeamXY = beamXYList.get(beamXYList.size() - 1);
        float endrealX = endbeamXY.getRealX();
        float endrealY = endbeamXY.getRealY();
        if (beamXYList != null && beamXYList.size() == 2) {
            if (!note.isRest() && !note.getType().equals("whole") && beamXYList != null) {
                String stem = note.getStem();
                switch (stem) {
                    case "up":
                        canvas.drawLine(startrealX + 15, startrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), endrealX + 15, endrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), paint1);
                        paint.setStrokeWidth(2);
                        canvas.drawLine(startrealX + 15, startrealY, startrealX + 15, startrealY - 49, paint);
                        canvas.drawLine(endrealX + 15, endrealY, endrealX + 15, endrealY - 49, paint);
                        break;
                    case "down":
                        canvas.drawLine(startrealX + 1, startrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), endrealX + 1, endrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), paint1);
                        paint.setStrokeWidth(2);
                        canvas.drawLine(startrealX + 1, startrealY, startrealX + 1, startrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), paint);
                        canvas.drawLine(endrealX + 1, endrealY, endrealX + 1, endrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), paint);
                        break;
                }

            }

        } else {
            String stem = note.getStem();
            float a = 0;
            float a1 = 0;
            float b = 0;
            if (startrealY == endrealY) {
                switch (stem) {
                    case "up":
                        b = (startrealY - 49) - k * (startrealX + 15);
                        canvas.drawLine(startrealX + 15, startrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), endrealX + 15, endrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), paint1);
                        break;
                    case "down":
                        b = (startrealY + 49) - k * (startrealX + 1);
                        canvas.drawLine(startrealX + 1, startrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), endrealX + 1, endrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), paint1);
                        break;
                }
            } else {
                if (endrealY > startrealY) {
                    a = endrealY - startrealY;
                    a1 = endrealX - startrealX;
                } else if (startrealY > endrealY) {
                    a = startrealY - endrealY;
                    a1 = startrealX - endrealX;
                }
                k = a / a1;//根据y=kx+b求解出b的值
                switch (stem) {
                    case "up":
                        b = (startrealY - 49) - k * (startrealX + 15);
                        canvas.drawLine(startrealX + 15, startrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), endrealX + 15, endrealY - 49 + (Constant.MUSIC_BEAM_GAP * num), paint1);
                        break;
                    case "down":
                        b = (startrealY + 49) - k * (startrealX + 1);
                        canvas.drawLine(startrealX + 1, startrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), endrealX + 1, endrealY + 49 - (Constant.MUSIC_BEAM_GAP * num), paint1);
                        break;
                }
            }
            for (int i = 0; i < beamXYList.size(); i++) {
                BeamXY beamXY = beamXYList.get(i);
                float realX = beamXY.getRealX();
                float realY = beamXY.getRealY();
                int y = 0;
                switch (stem) {
                    case "up":
                        y = (int) (k * (realX + 15) + b);
                        paint.setStrokeWidth(2);
                        canvas.drawLine(realX + 15, realY, realX + 15, y, paint);
                        break;
                    case "down":
                        y = (int) (k * (realX + 1) + b);
                        paint.setStrokeWidth(2);
                        canvas.drawLine(realX + 1, realY, realX + 1, y, paint);
                        break;
                }
            }
        }
    }

    /**
     * 绘制空白的乐谱
     */
    private void drawBlackScore() {

        //得到乐谱的总长度
        float allLength = getAllLength();
        firstLineX = 20;
        firstLineY = 55;
        //这张乐谱包含了两行
        ceil = (int) Math.ceil(allLength / (width - firstLineX * 2));
        for (int i = 0; i < ceil; i++) {
            for (int j = 0; j < 5; j++) {
                paint.setStrokeWidth(1);
                paint.setColor(Color.BLACK);
                canvas.drawLine(firstLineX, firstLineY, width - firstLineX, firstLineY, paint);
                firstLineY = firstLineY + 15;
            }
            paint.setColor(Color.BLACK);
            canvas.drawLine(width - firstLineX, 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, width - firstLineX, 75 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            firstLineY = (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * (i + 1) + 55;
            if (mList.get(0) != null && mList.get(0).get(0) != null && mList.get(0).get(0).getAttributes() != null) {

                if (mList.get(0).get(0).getAttributes().getKey() != null && mList.get(0).get(0).getAttributes().getKey().getFifths() != 0) {
                    int fifths = mList.get(0).get(0).getAttributes().getKey().getFifths();
                    drawFifths(fifths, paint, i);
                }
                if (mList.get(0).get(0).getAttributes().getClef() != null && mList.get(0).get(0).getAttributes().getClef().getSign() != null) {
                    String sign = mList.get(0).get(0).getAttributes().getClef().getSign();
                    drawSign(sign, paint, firstLineX, i);
                }
            }
        }
    }

    /**
     * @param
     * @param sign
     * @param firstLineX
     * @param i
     */
    private void drawSign(String sign, Paint paint, int firstLineX, int i) {
        switch (sign) {
            case "G":
                canvas.drawText("\uE19E", firstLineX, 100 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * (i), paint);
                Leftx = firstLineX + 20;
                break;
            case "C":
                canvas.drawText("\uE19A", firstLineX, 100 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * (i), paint);
                Leftx = firstLineX + 20;
                break;
            case "F":
                canvas.drawText("\uE19C", firstLineX, 65 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * (i), paint);
                Leftx = firstLineX + 20;
                break;
        }
    }

    /**
     * 绘制调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifths(int fifths, Paint paint, int i) {
        switch (Math.abs(fifths)) {
            case 1:
                drawFifthOne(fifths, paint, i);
                break;
            case 2:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                break;
            case 3:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                drawFifthThree(fifths, paint, i);
                break;
            case 4:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                drawFifthThree(fifths, paint, i);
                drawFifthFour(fifths, paint, i);
                break;
            case 5:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                drawFifthThree(fifths, paint, i);
                drawFifthFour(fifths, paint, i);
                drawFifthFive(fifths, paint, i);
                break;
            case 6:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                drawFifthThree(fifths, paint, i);
                drawFifthFour(fifths, paint, i);
                drawFifthFive(fifths, paint, i);
                drawFifthSix(fifths, paint, i);
                break;
            case 7:
                drawFifthOne(fifths, paint, i);
                drawFifthTwo(fifths, paint, i);
                drawFifthThree(fifths, paint, i);
                drawFifthFour(fifths, paint, i);
                drawFifthFive(fifths, paint, i);
                drawFifthSix(fifths, paint, i);
                drawFifthSeven(fifths, paint, i);
                break;
        }
    }

    /**
     * 绘制7的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthSeven(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_SEVEN_UP_X, Constant.MUSIC_FIFTHS_SEVEN_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_SEVEN_X, Constant.MUSIC_FIFTHS_SEVEN_DOWN_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_SEVEN_UP_X + 5 : Constant.MUSIC_FIFTHS_SEVEN_X + 5;
    }

    /**
     * 绘制6的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthSix(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_SIX_UP_X, Constant.MUSIC_FIFTHS_SIX_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_SIX_X, Constant.MUSIC_FIFTHS_FIVE_SIX_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_SIX_UP_X + 5 : Constant.MUSIC_FIFTHS_FIVE_SIX_Y + 5;
    }

    /**
     * 绘制5的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthFive(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_FIVE_X, Constant.MUSIC_FIFTHS_FIVE_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_FIVE_DOWM_X, Constant.MUSIC_FIFTHS_FIVE_DOWN_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_FIVE_X + 5 : Constant.MUSIC_FIFTHS_FIVE_X + 5;
    }

    /**
     * 绘制4的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthFour(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_FOUR_UP_X, Constant.MUSIC_FIFTHS_FOUR_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_FOUR_X, Constant.MUSIC_FIFTHS_FOUR_DOWN_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_FOUR_UP_X + 5 : Constant.MUSIC_FIFTHS_FOUR_X + 5;
    }

    /**
     * 绘制3的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthThree(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_THIRD_UP_X, Constant.MUSIC_FIFTHS_THIRD_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_THIRD_X, Constant.MUSIC_FIFTHS_THIRD_DOWN_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_THIRD_UP_X + 5 : Constant.MUSIC_FIFTHS_THIRD_X + 5;
    }

    /**
     * 升降2的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthTwo(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_SECOND_X, Constant.MUSIC_FIFTHS_SECOND_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_SECOND_X, Constant.MUSIC_FIFTHS_SECOND_DOWN_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_SECOND_X + 5 : Constant.MUSIC_FIFTHS_SECOND_X + 5;
    }

    /**
     * 绘制升降一的调号
     *
     * @param fifths
     * @param paint
     * @param i
     */
    private void drawFifthOne(int fifths, Paint paint, int i) {
        if (fifths > 0) {
            //升调
            canvas.drawText(Constact.RISING_TUNE, Constant.MUSIC_FIFTHS_FIRST_DOWN_X, Constant.MUSIC_FIFTHS_FIRST_UP_Y + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        } else {
            //降调
            canvas.drawText(Constact.FALLING_TUNE, Constant.MUSIC_FIFTHS_FIRST_X, Constant.MUSIC_FIFTHS_FIRST_DOWN_Y + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
        }
        Leftx = fifths > 0 ? Constant.MUSIC_FIFTHS_FIRST_X + 5 : Constant.MUSIC_FIFTHS_FIRST_X + 5;
    }

    /**
     * 得到乐谱的总长度
     */
    private int getAllLength() {
        int allLength = 0;
        for (int i = 0; i < mList.size(); i++) {
            List<Measure> measures = mList.get(i);
            for (int j = 0; j < measures.size(); j++) {
                allLength += measures.get(j).getWidth();
            }
        }
        return allLength;
    }


    /**
     * 绘制音节一共含有8个度，分别是：15,23,30,38,45,53,60,68,75
     *
     * @param drawMeasureList 第i行对应的小节
     * @param paint           画笔
     * @param i               行数
     * @param totalLine
     */
    private void drawNote(List<Measure> drawMeasureList, Paint paint, int i, float totalLine) {
        float measureCurrentLine = getMeasureCurrentLine(drawMeasureList, totalLine);
        int total = 0;
        for (int j = 0; j < drawMeasureList.size(); j++) {
            Measure measure = drawMeasureList.get(j);
            if (measure.getDirection() != null) {
                direction = measure.getDirection();
            } else {
                direction = null;
            }
            drawDirection(direction, i, j, paint, drawMeasureList);
            if (measure.getForward() != null) {
                forward = measure.getForward();
            } else {
                forward = null;
            }
            drawForward(forward, i, j, paint, drawMeasureList);
            if (drawMeasureList.get(j).getAttributes() != null && drawMeasureList.get(j).getAttributes().getTime() != null) {
                Time time = drawMeasureList.get(j).getAttributes().getTime();
                float X = 0;
                if (i == 0) {//第一行
                    if (j == 0) {
                        X = (float) (drawMeasureList.get(0).getNotes().get(0).getDefaultX() - 40);
                        drawTimeOther(X, paint, time, i);
                    } else {
                        for (int k = 0; k < j; k++) {
                            float width = drawMeasureList.get(k).getWidth();
                            X += width + measureCurrentLine;
                        }
                        drawTimeOther(X, paint, time, i);
                        Log.e("这是", "这是第-->" + j + "-->个变音的音节-->" + "‘X周的位置是：-->" + X);
                    }
                } else if (i == pageMeasure.get(currentPage).size() - 1) {
                    if (j == 0) {
                        X = (float) (pageMeasure.get(0).get(0).get(0).getNotes().get(0).getDefaultX() - 30);
                        drawTimeOther(X, paint, time, i);
                    } else {
                        float measureCurrentLine1 = getMeasureCurrentLine(drawMeasureList, getTotalLine(drawMeasureList));
                        for (int k = 0; k < j; k++) {
                            float width = drawMeasureList.get(k).getWidth();
                            X += width + measureCurrentLine1;
                        }
                        drawTimeOther((float) (X), paint, time, i);
                    }
                } else {
                    if (j == 0) {
                        X = (float) (pageMeasure.get(0).get(0).get(0).getNotes().get(0).getDefaultX() - 30);
                        drawTimeOther(X, paint, time, i);
                    } else {
                        for (int k = 0; k < j; k++) {
                            float width = drawMeasureList.get(k).getWidth();
                            X += width + measureCurrentLine;
                        }
                        drawTimeOther((float) (X + incrementX), paint, time, i);
                    }
                }

            }

            List<Note> notes = measure.getNotes();
            if (j == 0) {
                total = 0;
            } else {
                total += drawMeasureList.get(j - 1).getWidth() + measureCurrentLine;
            }
            for (int k = 0; k < notes.size(); k++) {

                double realX;
                Note note = notes.get(k);
                //不是休止符
                double defaultX = note.getDefaultX();
                int duration = note.getDuration();
                Pitch pitch = note.getPitch();
                String stem = note.getStem();
                String type = note.getType();
                int voice = note.getVoice();
                if (note.getNotations() != null) {
                    notations = note.getNotations();
                } else {
                    notations = null;
                }
                List<Lyric> lyricList = note.getLyricList();

                if (note.getIsDot() != null) {
                    isDot = note.getIsDot();

                } else {
                    isDot = null;
                }
                if (note.isRest()) {
                    drawRest(paint, note, i);
                } else {
                    if (note.getBeamList() != null) {
                        beamList = note.getBeamList();
                    } else {
                        beamList = null;
                    }


                    double realY = getRealY(pitch, i);
                    if (i != 0) {
                        realX = (getRealX(defaultX, total) + incrementX);
                    } else {
                        realX = getRealX(defaultX, total);
                    }
                    if (i == ceil - 1) {
                        Leftx = (float) realX;
                    } else {
                        Leftx = (float) (realX + measureCurrentLine);
                    }
                    if (note.getAccidental() != null) {

                        switch (note.getAccidental()) {
                            case "sharp":
                                canvas.drawText("\uE10E", Leftx - 17, (float) realY, paint);
                                break;
                            case "flat":
                                canvas.drawText("\uE114", Leftx - 12, (float) realY, paint);
                                break;
                            case "double-sharp":
                                Paint paint1 = new Paint();
                                paint1.setTypeface(Typeface.DEFAULT_BOLD);
                                paint1.setDither(true);
                                paint1.setStrokeWidth(30);
                                paint1.setTextSize(22);
                                canvas.drawText("x", Leftx - 12, (float) (realY + 5), paint1);
                                break;
                            case "natural":
                                canvas.drawText("\uE113", Leftx - 12, (float) realY, paint);
                                break;
                            case "flat-flat":
                                canvas.drawText("\uE11A", Leftx - 20, (float) realY, paint);
                                break;
                        }
                    }

                    if (i == lineReading && measureReading == j && k == noteReading) {
                        paint.setColor(Color.RED);
                    } else {
                        paint.setColor(Color.BLACK);
                    }
                    if (type.equals("whole")) {
                        canvas.drawText("\uE12B", Leftx, (float) realY, paint);
                    } else {
                        if (duration == 256) {
                            canvas.drawText("\uE12B", Leftx, (float) realY, paint);
                        } else if (duration == 128) {
                            canvas.drawText("\uE12C", Leftx, (float) realY, paint);
                        } else if (duration == 36) {
                            canvas.drawText("\uE12C", Leftx, (float) realY, paint);
                        } else if (type.equals("half")) {
                            canvas.drawText("\uE12C", Leftx, (float) realY, paint);
                        } else {
                            canvas.drawText("\uE12D", Leftx, (float) realY, paint);
                        }
                        drawNotations(notations, Leftx, realY, paint, i, stem);
                        drawBeamAndStem(paint, realX, realY, stem, drawMeasureList, measureCurrentLine, i, totalLine);
                        drawDot(paint, isDot, realY);
                    }
                }
                drawText(lyricList, Leftx, i, paint);
            }
            drawLine(drawMeasureList, measureCurrentLine, i, paint);
        }

    }

    /**
     * @param forward
     * @param i
     * @param j
     * @param paint
     * @param drawMeasureList
     */
    private void drawForward(Forward forward, int i, int j, Paint paint, List<Measure> drawMeasureList) {
        if (forward != null) {
            float forwardY = getFordwardsY(i);
            float forwardX = getFordwardsX(i, j, drawMeasureList);
            paint.setTextSize(40);
            canvas.drawText("%", forwardX, forwardY, paint);
            paint.setTextSize(55);
        }
    }

    /**
     * 计算forward的X轴
     *
     * @param i
     * @param j
     * @param drawMeasureList
     * @return
     */
    private float getFordwardsX(int i, int j, List<Measure> drawMeasureList) {
        float width = 0;
        if (i == 0) {
            if (j == 0) {
                width = drawMeasureList.get(0).getWidth() / 2;
            } else {
                for (int k = 0; k < j; k++) {
                    width += drawMeasureList.get(k).getWidth();
                }
                width = width + drawMeasureList.get(j).getWidth() / 2;
            }
        } else {

        }
        return width;
    }

    /**
     * 计算forwards的Y轴值
     *
     * @param i
     * @return
     */
    private float getFordwardsY(int i) {
        float total = 0;
        total = 60 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
        return total;
    }

    /**
     * 绘制其他的节拍
     *
     * @param x
     * @param paint
     * @param time
     * @param i
     */
    private void drawTimeOther(float x, Paint paint, Time time, int i) {
        canvas.drawText(time.getBeats() + "", x + 15, 85 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
        canvas.drawText(time.getBeatType() + "", x + 15, 115 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
    }

    /**
     * 绘制小节结束的时候的那条线
     *
     * @param drawMeasureList
     * @param measureCurrentLine
     * @param i
     * @param paint
     */
    private void drawLine(List<Measure> drawMeasureList, float measureCurrentLine, int i, Paint paint) {
        paint.setColor(Color.BLACK);
        float total = 0;


        for (int j = 0; j < drawMeasureList.size() - 1; j++) {

            Measure measure = drawMeasureList.get(j);
            if (j > drawMeasureList.size() - 1) {
                j = drawMeasureList.size() - 1;
            }
//            if (j == drawMeasureList.size() - 1) {
//                Measure measure1 = drawMeasureList.get(j);
//                if (measure1.getBarLine() != null) {
//
//                }
//            }
            if (measure.getBarLine() != null || drawMeasureList.get(j + 1).getBarLine() != null) {
                if (measure.getBarLine() != null) {
                    BarLine barLine = measure.getBarLine();
                    if (barLine.getLocation() != null) {
                        String location = barLine.getLocation();
                        switch (location) {
                            case "right":
                                total = BarLineRight(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                break;
                            case "left":
//                                total = BarLeft(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                total = BarLineNull(drawMeasureList, measureCurrentLine, i, paint, total, j);
                                break;
                        }
                    }
                } else {
                    if (drawMeasureList.get(j + 1).getBarLine() != null) {
                        BarLine barLine = drawMeasureList.get(j + 1).getBarLine();
                        if (barLine.getLocation() != null) {
                            String location = barLine.getLocation();
                            switch (location) {
                                case "right":
//                                    total = BarRight(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                    total = BarLineNullRight(drawMeasureList, measureCurrentLine, i, paint, total, j);
//                                    total = BarLineRight(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                    break;
                                case "left":
//                                    total = BarLineLeft(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                    total = BarLeft(drawMeasureList, measureCurrentLine, i, paint, total, j, barLine);
                                    break;
                            }
                        }
                    }
                }
            } else {
                total = BarLineNull(drawMeasureList, measureCurrentLine, i, paint, total, j);
//
//                if (i == ceil - 1) {
//                    if (j == drawMeasureList.size() - 1 && i == ceil - 1) {
//                        canvas.drawLine((float) (total - 20), startY, (float) (total - 20), stopY, paint);
////                                            canvas.drawLine((float) (total + incrementX - 20), startY, (float) (total + incrementX - 20), stopY, paint);
//                        paint.setStrokeWidth(8);
////                                            canvas.drawLine((float) (total - 9 + incrementX), startY, (float) (total - 9 + incrementX), stopY, paint);
//                        canvas.drawLine((float) (total - 9), startY, (float) (total - 9), stopY, paint);
//                    } else {
//                        paint.setStrokeWidth(3);
//                        canvas.drawLine(total, startY, total, stopY, paint);
//                    }
//                }
//                else {
//                    if (i == 0) {
//                        if (j != drawMeasureList.size() - 1) {
//                        }
//                    } else {
//                        if (j != drawMeasureList.size() - 1) {
//                            paint.setStrokeWidth(3);
//                            canvas.drawLine((float) (total + incrementX), startY, (float) (total + incrementX), stopY, paint);
//                        }
//                        if (j == drawMeasureList.size() - 1 && i == ceil - 1) {
//                            canvas.drawLine((float) (total + incrementX - 20), startY, (float) (total + incrementX - 20), stopY, paint);
//                            paint.setStrokeWidth(8);
//                            canvas.drawLine((float) (total - 9 + incrementX), startY, (float) (total - 9 + incrementX), stopY, paint);
//                        }
//                    }
//                }
//                }
            }
//            drawTime(paint,drawMeasureList.get(j).getAttributes());
        }
    }

    private float BarLineNullRight(List<Measure> drawMeasureList, float measureCurrentLine, int i, Paint paint, float total, int j) {
        total = 0;
        String location = null;
        String barStyle = null;
        if (drawMeasureList.get(j + 1).getBarLine() != null) {
            BarLine barLine = drawMeasureList.get(j + 1).getBarLine();

            if (barLine.getRepeat() == null) {
                if (barLine.getBarStyle() != null) {
                    barStyle = barLine.getBarStyle();
                }
                if (barLine.getLocation() != null) {
                    location = barLine.getLocation();
                }

                if (barStyle.equals("light-heavy") && location.equals("right")) {
                    int startYll = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    int stopYll = 115 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    paint.setStrokeWidth(1);
                    for (int k = 0; k < drawMeasureList.size(); k++) {
                        total += drawMeasureList.get(k).getWidth() + measureCurrentLine;

                    }

//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
//                    canvas.drawLine((float) (total +drawMeasureList.get(j+1).getWidth()+ incrementX), startYll, (float) (total +drawMeasureList.get(j+1).getWidth()+ incrementX), stopYll, paint);
                    canvas.drawLine((float) (total + incrementX), startYll, (float) (total + incrementX), stopYll, paint);
//                                       paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    paint.setStrokeWidth(3);

//                    canvas.drawLine((float) (total + 4+drawMeasureList.get(j+1).getWidth() + incrementX), startYll, (float) (total +drawMeasureList.get(j+1).getWidth()+ 4 + incrementX), stopYll, paint);
                    canvas.drawLine((float) (total + 4 + incrementX), startYll, (float) (total + 4 + incrementX), stopYll, paint);
                    int temp = 0;
                    for (int k = 0; k < drawMeasureList.size() - 1; k++) {

                        temp += drawMeasureList.get(k).getWidth() + measureCurrentLine;
                    }
                    if (currentPage != 0) {
                        canvas.drawLine((float) (temp), startYll, (float) (temp), stopYll, paint);
                        Leftx = (total + 4);
                    } else {
                        canvas.drawLine((float) (temp + incrementX), startYll, (float) (temp + incrementX), stopYll, paint);
                        Leftx = (float) (total + 4 + incrementX);
                    }
                }
            }
        }
        return total;
    }

    private float BarLineNull(List<Measure> drawMeasureList, float measureCurrentLine, int i, Paint paint, float total, int j) {
        total = getTotal(drawMeasureList, measureCurrentLine, total, j, i);
        int startY = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
        int stopY = 115 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
        paint.setStrokeWidth(3);
        canvas.drawLine(total, startY, total, stopY, paint);
        return total;
    }


    private float BarLineRight(List<Measure> drawMeasureList, float measureCurrentLine, int i, Paint paint, float total, int j, BarLine barLine) {
        if (barLine.getRepeat() != null && barLine.getRepeat().getDirection() != null) {
            String direction = barLine.getBarStyle();
            switch (direction) {
                case "light-light":
                    total = getTotal(drawMeasureList, measureCurrentLine, total, j, i);
                    int startYll = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    int stopYll = 115 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + incrementX), startYll, (float) (total + incrementX), stopYll, paint);
//                                       paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + 4 + incrementX), startYll, (float) (total + 4 + incrementX), stopYll, paint);
                    Leftx = (float) (total + 4 + incrementX);
                    break;
                case "light-heavy":
                    total = getTotal(drawMeasureList, measureCurrentLine, total, j, i);
                    int startYlh = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    int stopYlh = 145 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    canvas.drawText(":", (float) (total + incrementX - 3), startYlh, paint);
                    paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + incrementX), startYlh, (float) (total + incrementX), stopYlh, paint);
                    paint.setStrokeWidth(3);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + 4 + incrementX), startYlh, (float) (total + 4 + incrementX), stopYlh, paint);
                    Leftx = (float) (total + 4 + incrementX);
                    Log.e("LIGHTLIGHT", "目前的值是：total--->" + total + "--->startY--->" + startYlh + "--->stop--->" + stopYlh);
                    break;
                case "heavy-light":
                    total = getTotal(drawMeasureList, measureCurrentLine, total, j, i);
                    int startYhl = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    int stopYhl = 145 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    paint.setStrokeWidth(3);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + incrementX), startYhl, (float) (total + incrementX), stopYhl, paint);
                    paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + 4 + incrementX), startYhl, (float) (total + 4 + incrementX), stopYhl, paint);
                    paint.setStrokeWidth(3);
                    canvas.drawText(":", (float) (total + 7 + incrementX), startYhl, paint);
                    Leftx = (float) (total + 7 + incrementX);
                    Log.e("LIGHTLIGHT", "目前的值是：total--->" + total + "--->startY--->" + startYhl + "--->stop--->" + stopYhl);
                    break;
            }
        }
        return total;
    }

    private float BarLeft(List<Measure> drawMeasureList, float measureCurrentLine, int i, Paint paint, float total, int j, BarLine barLine) {
        if (barLine.getRepeat() != null && barLine.getRepeat().getDirection() != null) {
            String direction = barLine.getBarStyle();
            switch (direction) {
                case "heavy-light":
                    total = getTotal(drawMeasureList, measureCurrentLine, total, j, i);
                    int startYhl = 55 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    int stopYhl = 115 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
                    paint.setStrokeWidth(3);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
//                    canvas.drawLine((float) (total + 4 + incrementX), startYhl, (float) (total + 4 + incrementX), stopYhl, paint);
                    canvas.drawLine((float) (total), startYhl, (float) (total), stopYhl, paint);
                    paint.setStrokeWidth(1);
//                                    canvas.drawLine((float) (total+incrementX),startYlh,(float) (total+incrementX),stopYlh,paint);
                    canvas.drawLine((float) (total + 4), startYhl, (float) (total + 4), stopYhl, paint);
//                    canvas.drawLine((float) (total + 7 + incrementX), startYhl, (float) (total + 7 + incrementX), stopYhl, paint);
//                    paint.setStrokeWidth(1);
                    paint.setTextSize(30);
                    canvas.drawText(".", (total + 7), startYhl + 25, paint);
                    canvas.drawText(".", (total + 7), startYhl + 35, paint);
                    paint.setTextSize(55);
                    Leftx = (float) (total + 10);
                    Log.e("LIGHTLIGHT", "目前的值是：total--->" + total + "--->startY--->" + startYhl + "--->stop--->" + stopYhl);
                    break;
            }
        }
        return total;
    }

    /**
     * 绘制direction
     *
     * @param direction
     * @param i               当前所在的行数
     * @param j               小节的个数
     * @param paint
     * @param drawMeasureList
     */
    private void drawDirection(List<Direction> direction, int i, int j, Paint paint, List<Measure> drawMeasureList) {
        float measureCurrentLine = getMeasureCurrentLine(drawMeasureList, getTotalLine(drawMeasureList));
        String soundposition = null;
        boolean f = false;
        boolean ff = false;
        boolean fff = false;
        boolean mf = false;
        boolean mp = false;
        boolean p = false;
        boolean pp = false;
        boolean ppp = false;
        if (direction != null) {
            for (int k = 0; k < direction.size(); k++) {
//                drawtemp(direction, i, j, paint, drawMeasureList, k);
                Direction direction1 = direction.get(k);
                //拿到sound值
                if (direction1.getSound() != null) {
                    Sound sound = direction1.getSound();
                    if (sound != null) {
                        soundposition = sound.getDynamics();//拿到的是位置
                    }
                }
                float LDJHY = getLDJHy(i);
                float WordsY = getWordY(i);
                //拿到力度值：ppp  pp p mp mf f ff fff
                if (direction1.getDirectionType() != null) {
                    DirectionType directionType = direction1.getDirectionType();
                    if (directionType.getDynamics() != null) {
                        drawDynamics(i, j, paint, drawMeasureList, soundposition, LDJHY, directionType);
                    }
                    if (directionType.getWords() != null) {
                        Words words = directionType.getWords();
                        if (words.getWords() != null) {
                            String words1 = words.getWords();
                            float wordsX = getWordsX(drawMeasureList, i, j, words1);
                            paint.setTextSize(20);
                            canvas.drawText(words1, wordsX, WordsY, paint);
                            paint.setTextSize(55);
                        }
                    }
                }

            }
        }
    }

    /**
     * 求出当前需要输入的值的X轴
     *
     * @param drawMeasureList
     * @param i
     * @param j
     * @param words1
     * @return
     */
    private float getWordsX(List<Measure> drawMeasureList, int i, int j, String words1) {
        float total = 0;
        if (i == 0) {
            for (int k = 0; k < j + 1; k++) {
                total += drawMeasureList.get(k).getWidth();
            }
        } else {
            total = (float) incrementX;
            for (int k = 0; k < j + 1; k++) {
                total += drawMeasureList.get(k).getWidth();
            }
            total = total - getWordsTempX(words1);
        }
        return total;
    }

    /**
     * 根据这个需要添加的文字的大小，我们进行设置X轴应该偏移的方向
     *
     * @param words1
     * @return
     */
    private float getWordsTempX(String words1) {
        float total = 0;
        switch (words1) {
            case "Fine":
                total = 40;
                break;
            case "To Coda":
                total = 90;
                break;
            case "D.C.":
                total = 30;
                break;
            case "D.C. al Fine":
                total = 100;
                break;
            case "D.C. al Coda":
                total = 100;
                break;
            case "D.S. al Coda":
                total = 100;
                break;
            case "D.S. al Fine":
                total = 100;
                break;
            case "D.S.":
                total = 30;
                break;
        }
        return total;
    }

    /**
     * 求出当前需要输入的这个值的Y轴值
     *
     * @param i
     * @return
     */
    private float getWordY(int i) {
        float wordY = 5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
        return wordY;
    }

    private void drawDynamics(int i, int j, Paint paint, List<Measure> drawMeasureList, String soundposition, float LDJHY, DirectionType directionType) {
        boolean f;
        boolean ff;
        boolean fff;
        boolean mf;
        boolean mp;
        boolean p;
        boolean pp;
        boolean ppp;
        Dynamics dynamics = directionType.getDynamics();
        f = dynamics.isF();
        ff = dynamics.isFF();
        fff = dynamics.isFFF();
        mf = dynamics.isMF();
        mp = dynamics.isMP();
        p = dynamics.isP();
        pp = dynamics.isPP();
        ppp = dynamics.isPPP();
        drawMFP(i, j, paint, drawMeasureList, soundposition, f, ff, fff, mf, mp, p, pp, ppp, LDJHY);
    }

    private void drawMFP(int i, int j, Paint paint, List<Measure> drawMeasureList, String soundposition, boolean f, boolean ff, boolean fff, boolean mf, boolean mp, boolean p, boolean pp, boolean ppp, float LDJHY) {
        if (f) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0066", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (ff) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0066\u0066", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (fff) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0066\u0066\u0066", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (mf) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u006D\u0066", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (mp) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u006D\u0070", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (p) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0070", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (pp) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0070\u0070", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
        if (ppp) {
            float LDJHx = getLDJHX(i, j, drawMeasureList, soundposition);
            paint.setTextSize(30);
            canvas.drawText("\u0070\u0070\u0070", LDJHx, LDJHY, paint);
            paint.setTextSize(55);
        }
    }

    /**
     * 求得力度记号的X轴
     * //todo 位置计算有问题
     *
     * @param i               行数
     * @param j               小节数
     * @param drawMeasureList
     * @param soundposition
     * @return
     */
    private float getLDJHX(int i, int j, List<Measure> drawMeasureList, String soundposition) {
        float measureCurrentLine = getMeasureCurrentLine(drawMeasureList, getTotalLine(drawMeasureList));
        double defaultX = pageMeasure.get(0).get(0).get(0).getNotes().get(0).getDefaultX();
        float v = Float.parseFloat(soundposition);
        float width = 0;
        if (i == 0) {//是第一行
            if (j == 0) {//第一个小节
                width = (float) (defaultX);
            } else {//不是第一个小节
                for (int k = 0; k < j; k++) {
                    width += drawMeasureList.get(k).getWidth() + measureCurrentLine;
                }
            }
        } else {//不是第一行
            if (j == 0) {//第一个小节
                width = (float) (defaultX);
            } else {//不是第一个小节
                for (int k = 0; k < j; k++) {
                    width += drawMeasureList.get(k).getWidth() + measureCurrentLine;
                }
            }
        }
        Log.e("LDJH", "ldjh-->" + (width + v) + "-->sound-->" + soundposition);
        return width + v;
    }

    private float getLDJHy(int i) {
        return Constant.MUSIC_SCORE_HEIGHT + 20 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i;
    }

    private void drawtemp(List<Direction> direction, int i, int j, Paint paint, List<Measure> drawMeasureList, int k) {
        Direction direction1 = direction.get(k);
        String placement = direction1.getPlacement();
        String type = direction1.getDirectionType().getWedge().getType();
        switch (type) {
            case "crescendo"://<
                float total = 0;
                List<DirectionXY> directionXYList = new ArrayList<>();
                DirectionXY directionXY = new DirectionXY();
                for (int l = 0; l < drawMeasureList.size() - 1; l++) {
                    total = total + drawMeasureList.get(l).getWidth();
                }
                directionXY.setDirectionX(total);
                directionXY.setDirectionY(100 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i);
                canvas.drawText("\uE163", (int) directionXY.getDirectionX(), (int) directionXY.getDirectionY(), directionXY.getDirectionX() + drawMeasureList.get(j).getWidth(), directionXY.getDirectionY(), paint);
                break;
            case "stop":
//                        float total= 0;
//                        List<DirectionXY> directionXYList = new ArrayList<>();
//                        DirectionXY directionXY = new DirectionXY();
//                        for (int l = 0; l < drawMeasureList.size() - 1; l++) {
//                            total= total + drawMeasureList.get(l).getWidth();
//                        }
//                        directionXY.setDirectionX(total);
//                        directionXY.setDirectionY(100+(Constant.MUSIC_SCORE_HEIGHT+Constant.MUSIC_SCORE_GAP)*i);
                break;
            case "diminuendo"://>
                float total1 = 0;
                List<DirectionXY> directionXYList1 = new ArrayList<>();
                DirectionXY directionXY1 = new DirectionXY();
                for (int l = 0; l < drawMeasureList.size() - 1; l++) {
                    total = total1 + drawMeasureList.get(l).getWidth();
                }
                directionXY1.setDirectionX(total1);
                directionXY1.setDirectionY(100 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i);
                canvas.drawText("\uE163", (int) directionXY1.getDirectionX(), (int) directionXY1.getDirectionY(), directionXY1.getDirectionX() + drawMeasureList.get(j).getWidth(), directionXY1.getDirectionY(), paint);
                break;
        }
    }

    /**
     * 绘制装饰音和演奏记号 notations
     *
     * @param notations
     * @param leftx
     * @param realY
     * @param paint
     * @param i
     * @param stem
     */
    private void drawNotations(Notations notations, float leftx, double realY, Paint paint, int i, String stem) {
        paint.setColor(Color.BLACK);
        if (notations != null) {
            drawFermata(notations, leftx, paint, i);
            drawArticulation(notations, leftx, realY, paint, stem);
            drawOrnaments(notations, leftx, paint, i);
        }
    }

    /**
     * 绘制Ornaments
     *
     * @param notations
     * @param leftx
     * @param paint
     * @param i
     */
    private void drawOrnaments(Notations notations, float leftx, Paint paint, int i) {
        if (notations.getOrnaments() != null) {
            Ornaments ornaments = notations.getOrnaments();
            if (ornaments.isInvertedMordent()) {
                canvas.drawText("\uE183", leftx + 15, (float) (5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i), paint);
            }
            if (ornaments.isInvertedTurn()) {
                canvas.drawText("\uE16F", leftx + 15, (float) (5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i), paint);
            }
            if (ornaments.isMordent()) {
                canvas.drawText("\uE184", leftx + 15, (float) (5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i), paint);
            }
            if (ornaments.isTrillMark()) {
                canvas.drawText("\uE171", leftx + 10, (float) (5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i), paint);
            }
            if (ornaments.isTurn()) {
                canvas.drawText("\uE170", leftx + 15, (float) (5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i), paint);

            }
        }
    }

    /**
     * 绘制Articulation
     *
     * @param notations
     * @param leftx
     * @param realY
     * @param paint
     * @param stem
     */
    private void drawArticulation(Notations notations, float leftx, double realY, Paint paint, String stem) {
        if (notations.getArticulations() != null) {
            Articulations articulations = notations.getArticulations();
            if (articulations.isAccent()) {
                switch (stem) {
                    case "up":
                        canvas.drawText("\uE161", leftx + 10, (float) (realY + 20), paint);
                        break;
                    case "down":
                        canvas.drawText("\uE161", leftx + 10, (float) (realY - 20), paint);
                        break;
                }
            }
            if (articulations.isStaccato()) {
                switch (stem) {
                    case "up":
                        canvas.drawText("\uE163", leftx + 10, (float) (realY + 20), paint);
                        break;
                    case "down":
                        canvas.drawText("\uE163", leftx + 10, (float) (realY - 20), paint);
                        break;
                }
            }

        }
    }

    /**
     * 绘制Fermata
     *
     * @param notations
     * @param leftx
     * @param paint
     * @param i
     */
    private void drawFermata(Notations notations, float leftx, Paint paint, int i) {
        if (notations.getFermata() != null) {
            Fermata fermata = notations.getFermata();
            if (fermata != null) {
                String type = fermata.getType();
                switch (type) {
                    case "upright":
                        canvas.drawText("\uE158", leftx + 20, 5 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i, paint);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 绘制文字信息
     *
     * @param lyricList
     * @param leftx
     * @param lines
     * @param paint
     */
    private void drawText(List<Lyric> lyricList, float leftx, int lines, Paint paint) {
        paint.setColor(Color.BLACK);
        int j = 0;
        if (lyricList != null) {
            for (int i = 0; i < lyricList.size(); i++) {
                Lyric lyric = lyricList.get(i);
                String text = lyric.getText();
                int number = lyric.getNumber();
                switch (number) {
                    case 2:
                        j = 0;
                        break;
                    case 4:
                        j = 1;
                        break;
                    case 6:
                        j = 2;
                        break;

                }
                paint.setTextSize(25);
                canvas.drawText(text, leftx, (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * lines + 150 + (30 * j), paint);
                paint.setTextSize(55);
            }
        }
    }


    private float getTotal(List<Measure> drawMeasureList, float measureCurrentLine, float total, int j, int i) {
        if (i == 0) {
            total += drawMeasureList.get(j).getWidth();
            total = total + measureCurrentLine + 2;
        } else {
            total = 0;
            if (j == 0) {
                total = (float) (total + drawMeasureList.get(0).getWidth() + incrementX);
            } else {
                for (int k = 0; k < j; k++) {
                    float width = drawMeasureList.get(k).getWidth();
                    total += width;
                }
                total = (float) ((float) (total + drawMeasureList.get(j).getWidth()) + incrementX + measureCurrentLine + 10);
            }

//            total = (float) (total + incrementX + measureCurrentLine);
        }
        return total;
    }


    /**
     * 求出当前行的总长度，用于在当前行的所有的音符部署完之后，将最后剩余的空白的位置进行平均分给每一个音符
     *
     * @param drawMeasureList
     * @param totalLine
     */
    private float getMeasureCurrentLine(List<Measure> drawMeasureList, float totalLine) {
        int notenum = getNoteNum(drawMeasureList);
        float i = width - totalLine;
        return i / notenum;
    }

    /**
     * 得到某一行的音符的个数
     *
     * @param drawMeasureList
     * @return
     */
    private int getNoteNum(List<Measure> drawMeasureList) {
        int total = 0;
        for (int i = 0; i < drawMeasureList.size(); i++) {
            Measure measure = drawMeasureList.get(i);
            List<Note> notes = measure.getNotes();
            for (int j = 0; j < notes.size(); j++) {
                if (!notes.get(j).isRest()) {
                    total += 1;
                }
            }
        }
        return total;
    }

    private void drawRest(Paint paint, Note note, int i) {
        String type = note.getType();

        Leftx = Leftx + 35;
        if (type.equals("")) {
            canvas.drawText("\uE101", Leftx, 78 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
        } else {
            if (type.equals("half")) {
                canvas.drawText("\uE101", Leftx, 85 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            } else if (type.equals("quarter")) {
                canvas.drawText("\uE107", Leftx, 85 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            } else if (type.equals("eighth")) {
                canvas.drawText("\uE109", Leftx, 80 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            } else if (type.equals("16th")) {
                canvas.drawText("\uE10A", Leftx, 80 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            } else if (type.equals("32th")) {
                canvas.drawText("\uE10B", Leftx, 85 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            } else if (type.equals("64th")) {
                canvas.drawText("\uE10C", Leftx, 85 + (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * i, paint);
            }
        }

    }

    /**
     * 绘制附点
     *
     * @param paint
     * @param dot
     * @param realY
     */
    private void drawDot(Paint paint, List<Dot> dot, double realY) {
        paint.setColor(Color.BLACK);
        if (dot != null) {
            for (int i = 0; i < dot.size(); i++) {
                if (dot.get(i).isDot()) {
                    canvas.drawText("\uE163", Leftx + 25 + 10 * i, (float) realY, paint);
                }
            }
        }

    }


    /**
     * 绘制符杆和符尾
     *
     * @param paint
     * @param realX
     * @param realY
     * @param stem
     * @param drawMeasureList    表示的是某一行的列表
     * @param measureCurrentLine
     * @param i
     * @param totalLine
     */
    private void drawBeamAndStem(Paint paint, double realX, double realY, String stem, List<Measure> drawMeasureList, float measureCurrentLine, int i, float totalLine) {
        paint.setStrokeWidth(2);
        drawStem(paint, realX, realY, stem, measureCurrentLine, i);
        drawBeam(paint, drawMeasureList, totalLine, i);


    }

    private void drawStem(Paint paint, double realX, double realY, String stem, float measureCurrentLine, int i) {
        paint.setColor(Color.BLACK);
        if (beamList != null) {
            return;
        } else {
            switch (stem) {
                case "up":
                    if (i == ceil - 1) {
                        canvas.drawLine((float) realX + 18, (float) realY-5, (float) realX + 18, (float) realY - 49, paint);
                    } else {
                        canvas.drawLine((float) realX + 18 + measureCurrentLine, (float) realY-5, (float) realX + 18 + measureCurrentLine, (float) realY - 49, paint);
                    }
                    break;
                case "down":
                    if (i == ceil - 1) {
                        canvas.drawLine((float) realX + 1, (float) realY+5, (float) realX + 1, (float) realY + 49, paint);
                    } else {
                        canvas.drawLine((float) realX + 1 + measureCurrentLine, (float) realY+5, (float) realX + 1 + measureCurrentLine, (float) realY + 49, paint);
                    }
                    break;
            }
        }


    }


    /**
     * 得到真正的X轴
     *
     * @param defaultX
     * @param x
     * @return
     */
    private double getRealX(double defaultX, double x) {
        x = x + defaultX;
        return x;
    }

    /**
     * 得到真正的高度也就是Y轴的值
     *
     * @param pitch
     * @param i
     * @return
     */
    private int getRealY(Pitch pitch, int i) {
        int y = 0;
        int octave = pitch.getOctave();
        String step = pitch.getStep();
        y = getYFirdtLines(y, octave, step, i);
        return y;
    }

    private int getYFirdtLines(int y, int octave, String step, int i) {
        if (octave == 1) {
            switch (step) {
                case "A":
                    break;
                case "B":
                    break;
                case "C":
                    break;
                case "D":
                    break;
                case "E":
                    break;
                case "F":
                    break;
            }
        } else if (octave == 2) {

        } else if (octave == 3) {
            switch (step) {
                case "A":
                    y = 138 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "B":
                    y = 130 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "C":
                    y = 175 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "D":
                    y = 168 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "E":
                    y = 160 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "F":
                    y = 153 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "G":
                    y = 145 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
            }
        } else if (octave == 4) {
            switch (step) {
                case "A":
                    y = 93 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "B":
                    y = 86 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "C":
                    y = 131 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "D":
                    y = 123 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "E":
                    y = 116 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "F":
                    y = 108 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "G":
                    y = 101 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
            }
        } else if (octave == 5) {
            switch (step) {
                case "A":
                    y = 18 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "B":
                    y = 3 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "C":
                    y = 78 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "D":
                    y = 71 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "E":
                    y = 63 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "F":
                    y = 56 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
                case "G":
                    y = 33 + (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i;
                    break;
            }
        } else if (octave == 6) {

        } else if (octave == 7) {

        }
        return y;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Draw();
    }


    private int getCurrent() {
        int total = 0;
        if (currentPage != 0) {
            float totalMeasureReading = getTotalMeasureReading();
            for (int i = 0; i < currentLine; i++) {
//                total += width;
                List<Measure> measures = mList.get(i);
                for (int j = 0; j < measures.size(); j++) {
                    total += measures.get(j).getWidth();
                }
            }
//            List<Measure> list = mList.get(ceil - 1);
            List<Measure> list = mList.get(currentLine);
            for (int i = 0; i < currentMeasure; i++) {
                total += list.get(i).getWidth();
            }
//            for (int i = 0; i < list.size(); i++) {
//                float width = (float) (list.get(i).getWidth() + mList.get(0).get(0).getNotes().get(0).getDefaultX());
//                total = (int) (total + width);
//            }
            total = (int) (total + totalMeasureReading);
        } else {
//            for (int i = 0; i < ceil - 1; i++) {
//                total += width;
//            }
//            List<Measure> list = mList.get(ceil - 1);
//            for (int i = 0; i < list.size(); i++) {
//                float width = (float) (list.get(i).getWidth() + mList.get(0).get(0).getNotes().get(0).getDefaultX());
//                total = (int) (total + width);
//            }
            for (int i = 0; i < currentLine; i++) {
//                total += width;
                List<Measure> measures = mList.get(i);
                for (int j = 0; j < measures.size(); j++) {
                    total += measures.get(j).getWidth();
                }
            }
//            List<Measure> list = mList.get(ceil - 1);
            List<Measure> list = mList.get(currentLine);
            for (int i = 0; i < currentMeasure; i++) {
                total += list.get(i).getWidth();
            }

        }
        return total;
    }

    private float getTotalMeasureReading() {
        float total = 0;
        for (int i = 0; i < currentPage; i++) {
            List<List<Measure>> lists = pageMeasure.get(i);
            for (int j = 0; j < lists.size(); j++) {
                List<Measure> measures = lists.get(j);
                for (int k = 0; k < measures.size(); k++) {
                    float width = measures.get(k).getWidth();
                    total += width;
                }
            }
        }
        return total;
    }


    /**
     * 点击事件，点到哪里就把那个小节圈起来
     *
     * @param event
     * @return
     */
    float downx = 0;
    float downy = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float upx = event.getX();
                float upy = event.getY();
                float a = upx - downx;
                if (Math.abs(a) < 200) {
                    ComputeMeasure(upx, upy);
                }
                break;
        }
        return true;//把这个事件消费掉
    }

    /**
     * 计算出当前的小节，顺便用其他颜色的线给框起来
     *
     * @param lineX
     * @param lineY
     */
    private void ComputeMeasure(float lineX, float lineY) {
        canmove = true;
        List<Measure> list = null;
        //先计算出是哪一行
        int lines = height / (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT);
        if (lines - 1 <= 0) {
            lines = 0;
            list = mList.get(lines);
        }
        for (int i = 0; i < lines - 1; i++) {
            if (lineY > (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * (lines - 1)) {
                currentLine = lines - 1;
                if (currentLine > ceil - 1) {
                    currentLine = ceil - 1;
                }
                list = mList.get(currentLine);
            }
            if (lineY > (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT) * i && lineY < (Constant.MUSIC_SCORE_HEIGHT + Constant.MUSIC_SCORE_GAP) * (i + 1)) {
                currentLine = i;
                if (currentLine > ceil - 1) {
                    currentLine = ceil - 1;
                }
                list = mList.get(currentLine);
                //拿到当前行的所有的小节
            }
        }
        //计算出当前的小节的个数
        for (int i = 0; i < list.size() - 1; i++) {
            float measureCurrentLine = getMeasureCurrentLine(list, getTotalLine(list));
            float totalMeasureLine = getTotalMeasureLine(list, list.size() - 1, currentLine, measureCurrentLine);
            if (totalMeasureLine < lineX) {
                currentMeasure = list.size() - 1;
            } else {
                //计算出当前小节和下一个小节之间的距离，从而计算在哪一个小节
                float tempmeasure1 = getTotalMeasureLine(list, i, currentLine, measureCurrentLine);
                float tempmeasure2 = getTotalMeasureLine(list, (i + 1), currentLine, measureCurrentLine);
                if (tempmeasure1 < lineX && tempmeasure2 > lineX) {
                    currentMeasure = i;
                }
            }
        }
        invalidate();
    }

    /**
     * @param list
     * @param i
     * @param currentLine
     * @param measureCurrentLine
     */
    private float getTotalMeasure(List<Measure> list, int i, int currentLine, float measureCurrentLine) {
        float total = 0;
        for (int j = 0; j < pageMeasure.size(); j++) {
            List<List<Measure>> lists = pageMeasure.get(j);
            for (int k = 0; k < lists.size(); k++) {
                List<Measure> measures = lists.get(k);
                for (int l = 0; l < measures.size(); l++) {
                    float width = measures.get(l).getWidth();
                    total += width + measureCurrentLine;
                }
            }
        }
//        for (int j = 0; j < i; j++) {
//            float width = list.get(j).getWidth();
//            total += width + measureCurrentLine;
//        }

        invalidate();
        return total;
    }

    //private int measureReading = 0, noteReading = 0, lineReading = 0;
    public void invalidate(int measureReading, int noteReading, int lineReading) {
        this.measureReading = measureReading;
        this.noteReading = noteReading;
        this.lineReading = lineReading;
        super.postInvalidate();
    }


    interface OnClickXiaoJieListener {
        public void setCurrentPostion(int postion, int measureReading, int noteReading, int lineReading, int currentPage);
    }

    public void setOnClickXiaoJieListener(OnClickXiaoJieListener listener) {
        mListener = listener;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentMeasure() {
        return currentMeasure;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public void setCurrentMeasure(int currentMeasure) {
        this.currentMeasure = currentMeasure;
    }
}
