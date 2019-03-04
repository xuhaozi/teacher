package com.example.admin.musicclassroom.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.admin.musicclassroom.R;
import com.example.admin.musicclassroom.Utils.Constant;
import com.example.admin.musicclassroom.musicentity.beans.Measure;
import com.example.admin.musicclassroom.musicentity.beans.Note;
import com.example.admin.musicclassroom.musicentity.beans.ScorePartWise;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CY on 2017/7/29.
 */

public class MusicView extends FrameLayout {
//五线谱渲染播放的类 方法全在下面 调用就可以了
    private ViewPager viewPager;
    private int ceil;
    private Activity mActivity;
    private ScorePartWise scorPeartWise;
    private File midi;
    private View inflate;
    private int height = 0;
    private int width = 0;
    boolean isFirstSet = true;

    private MediaPlayer mediaPlayer;
    private int t, t2, t4, t8, t16, t32, t64, timePerT;
    private int measureReading = 0, noteReading = 0, lineReading = 0, pageReading = 0;
    public Boolean isPlaying = false;
    private int currentLine = 0;
    private int currentMeasure = 0;
    private boolean canmove = false;
    private List<List<Measure>> lists;
    private List<LineView> mLineViews;
    private int pages;
    private List<List<List<Measure>>> pagesMeasure;
    private int currentItem;

    public MusicView(Context context) {
        this(context, null);
    }

    public MusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        String layout_width = attrs.getAttributeValue("layout_width", "0");
        inflate = inflate(context, R.layout.music_view, null);
        LayoutParams layoutParams = new LayoutParams(context, attrs);
        height = layoutParams.height;
        width = layoutParams.width;
        inflate.setLayoutParams(layoutParams);
        addView(inflate);

        initView(inflate, context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();

        if (height != 0 && width != 0 && isFirstSet) {
            isFirstSet = false;
            setDataAndView();
        }
    }

    public void setScorePartWise(ScorePartWise scorPeartWise, Activity activity, File midi) {
        this.mActivity = activity;
        this.scorPeartWise = scorPeartWise;
        this.midi=midi;
        setDataAndView();
    }

    public void setDataAndView() {
        lists = loadLineMeasure(scorPeartWise.getPart().getMeasureList());
        int size = lists.size();
        float linesOfPerPage = height / (Constant.MUSIC_SCORE_GAP + Constant.MUSIC_SCORE_HEIGHT);
        pages = (int) Math.ceil(lists.size() / linesOfPerPage);
        pagesMeasure = loadPagesMeasure(lists, pages, linesOfPerPage);
        mLineViews = new ArrayList<>();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(mActivity, Uri.fromFile(midi));
        }

        for (int i = 0; i < pagesMeasure.size(); i++) {
            List<List<Measure>> lists1 = pagesMeasure.get(i);
            LineView tempView = new LineView(mActivity, lists1, i, width, height, pagesMeasure, mediaPlayer, MusicView.this);
            tempView.setOnClickXiaoJieListener(new LineView.OnClickXiaoJieListener() {
                @Override
                public void setCurrentPostion(int postion, int measureReadings, int noteReadings, int lineReadings, int currentPage) {
                    mediaPlayer.pause();
                    measureReading = currentMeasure;
                    lineReading = currentLine;
                    mediaPlayer.seekTo(postion);
                    MusicView.this.measureReading = measureReadings;
                    MusicView.this.noteReading = 0;
                    MusicView.this.lineReading = lineReadings;
                    MusicView.this.pageReading = currentPage;
                    mLineViews.get(currentItem).setCurrentLine(0);
                    mLineViews.get(currentItem).setCurrentMeasure(0);
                    mLineViews.get(currentItem).setCurrentPage(0);
                    mediaPlayer.start();
                }
            });
            mLineViews.add(tempView);
        }
        MuscicAdapter muscicAdapter = new MuscicAdapter(mActivity, mLineViews);
        viewPager.setAdapter(muscicAdapter);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView(View inflate, Context context) {
        viewPager = (ViewPager) inflate.findViewById(R.id.view_pager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currentItem = viewPager.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 得到乐谱的总长度
     */
    private int getAllLength() {
        int allLength = 0;
        List<Measure> measureList = scorPeartWise.getPart().getMeasureList();
        for (int i = 0; i < measureList.size(); i++) {
            allLength += measureList.get(i).getWidth();
        }
        return allLength;
    }

    /**
     * 将所有的音符分行处理
     *
     * @param drawMeasureList
     */
    public List<List<Measure>> loadLineMeasure(List<Measure> drawMeasureList) {
//        int width =getWidth();
//        int height =getHeight();
//        int measuredHeight = getMeasuredHeight();
        //得到乐谱的总长度
        float allLength = getAllLength();
        int firstLineX = 20;
        int firstLineY = 15;
        //这张乐谱包含了两行
        ceil = (int) Math.ceil(allLength / (width - firstLineX * 2));
        List<List<Measure>> lineMeasure = new ArrayList<>();
        for (int j = 0; j < ceil; j++) {
            int total = 0;
            List<Measure> list = new ArrayList<>();
            lineMeasure.add(list);
            for (int i = 0; i < drawMeasureList.size(); i++) {
                Measure measure = drawMeasureList.get(i);
                total += measure.getWidth();
                float width;
                if (i+1<drawMeasureList.size()){
                     width= drawMeasureList.get(i + 1).getWidth();
                }else{
                    width = drawMeasureList.get(drawMeasureList.size()-1).getWidth();
                }
                if (total < (this.width - firstLineX * 2 - width) * (j + 1) && total > (this.width - firstLineX * 2 - width) * j) {
                    list.add(measure);
                }
            }
        }

        return lineMeasure;
    }
    /**
     * 加载每一页应该存放的那几行的具体的内容，也就是对于这个东西进行具体的分页操作
     *
     * @param lists          已经按行划分好的list列表
     * @param pages          一共可以分为多少页
     * @param linesOfPerPage
     */
    private List<List<List<Measure>>> loadPagesMeasure(List<List<Measure>> lists, int pages, float linesOfPerPage) {
        List<List<List<Measure>>> pagesList = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            List<List<Measure>> lists1 = new ArrayList<>();
            pagesList.add(lists1);
            for (int j = 0; j < lists.size(); j++) {
                if (j >= linesOfPerPage * i && j < linesOfPerPage * (i + 1)) {
                    lists1.add(lists.get(j));
                }
            }
        }
        return pagesList;
    }

    class MuscicAdapter extends PagerAdapter {
        List<LineView> mList = null;

        public MuscicAdapter(Context context, List<LineView> list) {
            this.mList = list;
        }


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {//实例化条目
            container.addView(mList.get(position));// 添加页卡
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//销毁条目
            container.removeView(mList.get(position));// 删除页卡

        }


    }

//    /**
//     * 不要试图读懂我这个方法在干嘛，因为我也不知道我要干嘛，反正功能能用了
//     *
//     * @param file
//     */
//    public void playMp3(File file) {
//        resetPosition();
//        needMove = true;
//        final ComposeActivity activity = (ComposeActivity) iml;
//        String name = file.getName();
//        String nameWithoutXml = name.substring(0, name.indexOf(","));
//        String filePath = file.getParent() + "/" + nameWithoutXml + ".mp3";
//        File mp3File = new File(filePath);
//        player = MediaPlayer.create(activity, Uri.fromFile(mp3File));
//        player.start();
//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                stop();
//            }
//        });
//        int duration = player.getDuration();
//        timeStackCaculate(duration);
//        final Handler handler = new Handler();
//        Runnable reRead = new Runnable() {
//            @Override
//            public void run() {
//                List<com.tjmj.musicteacher.musicentity.Measure> measures = score.getAllMeasure();
//                com.tjmj.musicteacher.musicentity.Measure measure = measures.get(measureReading);
//                List<com.tjmj.musicteacher.musicentity.Note> notes = measure.getNoteList();
//                com.tjmj.musicteacher.musicentity.Note note = notes.get(noteReading);
//                if (noteReading < notes.size() - 1 || note.getX() == 0) {
//                    noteReading++;
//                } else {
//                    noteReading = 0;
//                    if (measureReading < measures.size() - 1) {
//                        measureReading++;
//                    }
//                    measureBackWidth += measure.getMeasureWidth();
//                    final float x = measureBackWidth + note.getX();
//                }
//                int postDelay = timePerT;
//                switch (note.getType()) {
//                    case "whole":
//                        postDelay = timePerT;
//                        break;
//                    case "half":
//                        postDelay = timePerT / 2;
//                        break;
//                    case "quarter":
//                        postDelay = timePerT / 4;
//                        break;
//                    case "eighth":
//                        postDelay = timePerT / 8;
//                        break;
//                    case "16th":
//                        postDelay = timePerT / 16;
//                        break;
//                    case "32nd":
//                        postDelay = timePerT / 32;
//                        break;
//                    case "64th":
//                        postDelay = timePerT / 64;
//                        break;
//                }
//                if (canmove)
//                    handler.postDelayed(this, postDelay);
//            }
//        };
//        handler.post(reRead);
//    }
    /**
     * 开始播放音乐
     * @param isReal
     * @param mp3
     */
    public void playMusic(boolean isReal, File mp3) {
      if (isReal){
          // TODO: 2017/8/4 0004
//          playMp3(mp3);

          isPlaying = true;
          if (mediaPlayer == null) {
              mediaPlayer = MediaPlayer.create(mActivity, Uri.fromFile(mp3));
              //// TODO: 2017/7/31

              for (LineView l : mLineViews) {
                  l.setMediaPlayer(mediaPlayer);
              }
          }
          clearAll();
          if (mLineViews.get(currentItem).getCurrentLine() != 0 || mLineViews.get(currentItem).getCurrentMeasure() != 0 || mLineViews.get(currentItem).getCurrentPage() != 0) {
              //拿到当前选定的页行和节
              int currentLine = mLineViews.get(currentItem).getCurrentLine();
              int currentMeasure = mLineViews.get(currentItem).getCurrentMeasure();
              int currentPage = mLineViews.get(currentItem).getCurrentPage();
              int duration = mediaPlayer.getDuration();//音乐总长度
              iniatilateCurrentLMP();
              //计算出占用的比例
              List<Measure> list = pagesMeasure.get(currentPage).get(currentLine);
              float totalMeasureLine = getTotalMeasureLine(list, currentMeasure, currentLine, getMeasureCurrentLine(list, getTotalLine(list)));
              int current = getCurrent();
              float v = totalMeasureLine / current;
              mediaPlayer.seekTo((int) (v*duration));
              mediaPlayer.start();

              timeStackCaculate(duration);
              lineReading = currentLine;
              measureReading = currentMeasure;
              noteReading = 0;
              computeCurrentProcess();
          } else {
              mediaPlayer.start();
              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer mediaPlayer) {
                      isPlaying = false;
                      stopMusic();
                  }
              });
              int duration = mediaPlayer.getDuration();
              timeStackCaculate(duration);
              lineReading = currentLine;
              measureReading = currentMeasure;
              noteReading = 0;
              computeCurrentProcess();
          }



      }else{
          isPlaying = true;
          if (mediaPlayer == null) {
              mediaPlayer = MediaPlayer.create(mActivity, Uri.fromFile(midi));
              //// TODO: 2017/7/31

              for (LineView l : mLineViews) {
                  l.setMediaPlayer(mediaPlayer);
              }
          }
          clearAll();
          if (mLineViews.get(currentItem).getCurrentLine() != 0 || mLineViews.get(currentItem).getCurrentMeasure() != 0 || mLineViews.get(currentItem).getCurrentPage() != 0) {
              //拿到当前选定的页行和节
              int currentLine = mLineViews.get(currentItem).getCurrentLine();
              int currentMeasure = mLineViews.get(currentItem).getCurrentMeasure();
              int currentPage = mLineViews.get(currentItem).getCurrentPage();
              int duration = mediaPlayer.getDuration();//音乐总长度
              iniatilateCurrentLMP();
              //计算出占用的比例
              List<Measure> list = pagesMeasure.get(currentPage).get(currentLine);
              float totalMeasureLine = getTotalMeasureLine(list, currentMeasure, currentLine, getMeasureCurrentLine(list, getTotalLine(list)));
              int current = getCurrent();
              float v = totalMeasureLine / current;
              mediaPlayer.seekTo((int) (v*duration));
              mediaPlayer.start();

              timeStackCaculate(duration);
              lineReading = currentLine;
              measureReading = currentMeasure;
              noteReading = 0;
              computeCurrentProcess();
          } else {
              mediaPlayer.start();
              mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer mediaPlayer) {
                      isPlaying = false;
                      stopMusic();
                  }
              });
              int duration = mediaPlayer.getDuration();
              timeStackCaculate(duration);
              lineReading = currentLine;
              measureReading = currentMeasure;
              noteReading = 0;
              computeCurrentProcess();
          }

      }

//        if (lineReading != 0 || measureReading != 0) {
//            List<Measure> list = lists.get(lineReading);//计算出当前行
//            mediaPlayer.seekTo((int) (getTotalMeasure(list, measureReading, lineReading, getMeasureCurrentLine(list, getTotalLine(list))) / getCurrent() * duration));
//        }
    }

    /**
     * 初始化各种值
     */
    private void iniatilateCurrentLMP() {
        mLineViews.get(currentItem).setCurrentLine(0);
        mLineViews.get(currentItem).setCurrentMeasure(0);
        mLineViews.get(currentItem).setCurrentPage(0);
    }

    /**
     * @return
     */
    private int getCurrent() {
        float total = 0;
            //先计算出已经读过去的页码
            for (int i = 0; i < pagesMeasure.size(); i++) {
                List<List<Measure>> lists = pagesMeasure.get(i);
                for (int j = 0; j < lists.size(); j++) {
                    List<Measure> list = lists.get(j);
                    for (int k = 0; k < list.size(); k++) {
                        float width = list.get(k).getWidth();
                        total += width;
                    }
                }
            }
        return (int) total;
    }

    /**
     * 得到所有的长度
     *
     * @param list
     * @param i
     * @param currentLine
     * @param measureCurrentLine
     * @return
     */
    private float getTotalMeasureLine(List<Measure> list, int i, int currentLine, float measureCurrentLine) {
        float total = 0;
        if (currentItem == 0) {
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
     * 将所有的数据都恢复最初的位置
     */
    private void clearAll() {
        lineReading = 0;
        noteReading = 0;
        measureReading = 0;
    }

    /**
     * 停止播放，释放所有的资源
     */
    public void stopMusic() {

        if (isPlaying) {
            isPlaying = false;
            mediaPlayer.stop();
//            releaseMediaPlayer();
//            mediaPlayer = null;
//            initializeTimeT();
        } else {
            isPlaying = false;
            currentMeasure =0;
            currentLine = 0;
//            lineReading = -1;
//            noteReading = -1;
//            measureReading = -1;
            pageReading = 0;
        }
        initializeTimeT();
        releaseMediaPlayer();
        mediaPlayer = null;
        lineReading = -1;
        noteReading = -1;
        measureReading = -1;
        invalidate();

        viewPager.setCurrentItem(0);
    }

    /**
     * 初始化播放过程中的所有的变量
     */
    private void initializeTimeT() {
        timePerT = 0;
        t = 0;
        t2 = 0;
        t4 = 0;
        t8 = 0;
        t16 = 0;
        t32 = 0;
        t64 = 0;
    }

    /**
     * 释放播放器的所有的资源
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer!=null){
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }


    private void timeStackCaculate(int duration) {
        List<Measure> measures = scorPeartWise.getPart().getMeasureList();
        for (Measure measure : measures) {
            List<Note> notes = measure.getNotes();
            for (Note note : notes) {
                switch (note.getType()) {
                    case "whole":
                        t += 1;
                        break;
                    case "half":
                        t2 += 1;
                        break;
                    case "quarter":
                        t4 += 1;
                        break;
                    case "eighth":
                        t8 += 1;
                        break;
                    case "16th":
                        t16 += 1;
                        break;
                    case "32nd":
                        t32 += 1;
                        break;
                    case "64th":
                        t64 += 1;
                        break;
                }
            }
        }
        timePerT = (duration) / (t + t2 / 2 + t4 / 4 + t8 / 8 + t16 / 16 + t32 / 32 + t64 / 64);//这个公式长度还可以吧
    }
    /**
     * 计算当前的进度
     */
    private void computeCurrentProcess() {
        if (isPlaying) {
            final Handler handler = new Handler();
            Runnable reRead = new Runnable() {
                @Override
                public void run() {
                    Log.e("test", "linereading--->" + lineReading + "--->measureading--->" + measureReading + "--->noteReading--->" + noteReading + "-->page-->" + pageReading);
                    if (lineReading > -1) {
                        List<Measure> measures = lists.get(lineReading);//得到行数
                        Measure measure = measures.get(measureReading);//得到小节
                        List<Note> notes = measure.getNotes();//得到小节里面的音符
                        Note note = notes.get(noteReading);
                        if (noteReading < notes.size() - 1) {
                            noteReading++;
                        } else {
                            noteReading = 0;
                            if (measureReading < measures.size() - 1) {
                                measureReading++;
                            } else {
                                measureReading = 0;
                                if (lineReading < pagesMeasure.get(pageReading).size() - 1) {
                                    lineReading++;
//                                    Log.e("COMPUTECURRENTPROCESS", "linereading--->" + lineReading + "--->measureading--->" + measureReading + "--->noteReading--->" + noteReading);
                                } else {
                                    lineReading = 0;
                                    if ((pageReading < pagesMeasure.size() - 1)) {
                                        pageReading++;
                                        viewPager.setCurrentItem(pageReading);
//                                        Log.e("COMPUTECURRENTPROCESS", "linereading--->" + lineReading + "--->measureading--->" + measureReading + "--->noteReading--->" + noteReading+"-->page-->"+pageReading);
//                                        System.out.println("当前："+pageReading);
                                    } else {
                                        lineReading = -1;
                                        measureReading = -1;
                                        noteReading = -1;
                                        pageReading = 0;
                                    }
                                }
                            }
                        }
//                      postInvalidate();
                        //// TODO: 2017/7/30
                        //重绘

                        LineView lineView = mLineViews.get(pageReading);
                        lineView.invalidate(measureReading, noteReading, lineReading);

                        int postDelay = timePerT;
                        switch (note.getType()) {
                            case "whole":
                                postDelay = timePerT;
                                break;
                            case "half":
                                postDelay = timePerT / 3;
                                break;
                            case "quarter":
                                postDelay = timePerT / 5;
                                break;
                            case "eighth":
                                postDelay = timePerT / 9;
                                break;
                            case "16th":
                                postDelay = timePerT / 16;
                                break;
                            case "32nd":
                                postDelay = timePerT / 33;
                                break;
                            case "64th":
                                postDelay = timePerT / 68;
                                break;
                        }
                        handler.postDelayed(this, postDelay);
                    }
                }
            };

            handler.post(reRead);

        }
    }

    /**
     * @param list
     * @param i
     * @param currentLine
     * @param measureCurrentLine
     */
    private float getTotalMeasure(List<Measure> list, int i, int currentLine, float measureCurrentLine) {
        float total = 0;
        for (int j = 0; j < i; j++) {
            float width = list.get(j).getWidth();
            total += width + measureCurrentLine;
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


//    private int getCurrent() {
//        int total = 0;
//        for (int i = 0; i < ceil - 1; i++) {
//            total += width;
//        }
//        List<Measure> list = lists.get(ceil - 1);
//        for (int i = 0; i < list.size(); i++) {
//            float width = (float) (list.get(i).getWidth() + lists.get(0).get(0).getNotes().get(0).getDefaultX());
//            total = (int) (total + width);
//        }
//        return total;
//    }


}
