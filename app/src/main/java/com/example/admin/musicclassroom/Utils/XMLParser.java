package com.example.admin.musicclassroom.Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import com.example.admin.musicclassroom.musicentity.beans.Articulations;
import com.example.admin.musicclassroom.musicentity.beans.Attributes;
import com.example.admin.musicclassroom.musicentity.beans.BarLine;
import com.example.admin.musicclassroom.musicentity.beans.Beam;
import com.example.admin.musicclassroom.musicentity.beans.Clef;
import com.example.admin.musicclassroom.musicentity.beans.Credit;
import com.example.admin.musicclassroom.musicentity.beans.CreditWords;
import com.example.admin.musicclassroom.musicentity.beans.Defaults;
import com.example.admin.musicclassroom.musicentity.beans.Direction;
import com.example.admin.musicclassroom.musicentity.beans.DirectionType;
import com.example.admin.musicclassroom.musicentity.beans.Dot;
import com.example.admin.musicclassroom.musicentity.beans.Dynamics;
import com.example.admin.musicclassroom.musicentity.beans.Encoding;
import com.example.admin.musicclassroom.musicentity.beans.Fermata;
import com.example.admin.musicclassroom.musicentity.beans.Forward;
import com.example.admin.musicclassroom.musicentity.beans.Identification;
import com.example.admin.musicclassroom.musicentity.beans.Key;
import com.example.admin.musicclassroom.musicentity.beans.Lyric;
import com.example.admin.musicclassroom.musicentity.beans.LyricFont;
import com.example.admin.musicclassroom.musicentity.beans.Measure;
import com.example.admin.musicclassroom.musicentity.beans.MidiDevice;
import com.example.admin.musicclassroom.musicentity.beans.MidiInstrument;
import com.example.admin.musicclassroom.musicentity.beans.Notations;
import com.example.admin.musicclassroom.musicentity.beans.Note;
import com.example.admin.musicclassroom.musicentity.beans.Ornaments;
import com.example.admin.musicclassroom.musicentity.beans.PageLayout;
import com.example.admin.musicclassroom.musicentity.beans.PageMargins;
import com.example.admin.musicclassroom.musicentity.beans.Part;
import com.example.admin.musicclassroom.musicentity.beans.PartList;
import com.example.admin.musicclassroom.musicentity.beans.Pitch;
import com.example.admin.musicclassroom.musicentity.beans.Print;
import com.example.admin.musicclassroom.musicentity.beans.Repeat;
import com.example.admin.musicclassroom.musicentity.beans.Scaling;
import com.example.admin.musicclassroom.musicentity.beans.ScoreInstrument;
import com.example.admin.musicclassroom.musicentity.beans.ScorePart;
import com.example.admin.musicclassroom.musicentity.beans.ScorePartWise;
import com.example.admin.musicclassroom.musicentity.beans.Slur;
import com.example.admin.musicclassroom.musicentity.beans.Sound;
import com.example.admin.musicclassroom.musicentity.beans.Supports;
import com.example.admin.musicclassroom.musicentity.beans.SystemLayout;
import com.example.admin.musicclassroom.musicentity.beans.SystemMargins;
import com.example.admin.musicclassroom.musicentity.beans.Time;
import com.example.admin.musicclassroom.musicentity.beans.Wedge;
import com.example.admin.musicclassroom.musicentity.beans.Words;
import com.example.admin.musicclassroom.musicentity.beans.Work;
import com.example.admin.musicclassroom.musicentity.beans.WorkFont;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class XMLParser {
    private InputStream inputStream;
    private Context context;
    private File file;
    public XMLParser(File file, Context context) {
        this.file = file;
        this.context = context;
    }

    public XMLParser(InputStream inputStream, Context context) {
        this.inputStream = inputStream;
        this.context = context;
    }

    /**
     * 读取xml文件
     */
    public ScorePartWise readFromXml() {

        SAXReader reader = new SAXReader();
        String xml=loadFromSDFile(file);
        Document document = null;
        try {
            document = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        Document document= reader.read(new FileInputStream(file));
        //得到根节点
        Element rootElement = document.getRootElement();
        ScorePartWise scorePartWise = getScorePartWise(rootElement);
        return scorePartWise;
    }
    private String loadFromSDFile(File file) {
        String result=null;
        try {
            int length=(int)file.length();
            byte[] buff=new byte[length];
            FileInputStream fin=new FileInputStream(file);
            fin.read(buff);
            fin.close();
            result=new String(buff,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param rootElement
     * @return
     */
    private ScorePartWise getScorePartWise(Element rootElement) {
        ScorePartWise scorePartWise = null;
        Element workelement = rootElement.element(Constact.WORK);
        Element work_titleelement = workelement.element(Constact.WORKTITLE);
        Element identificationelement = rootElement.element(Constact.IDENTIFICATION);
        List creditelementslist = rootElement.elements(Constact.CREDIT);
        //创建根目录的对象
        if (rootElement != null) {
            scorePartWise = new ScorePartWise();
        }
        getTitle(scorePartWise, workelement, work_titleelement);
        getIdentification(rootElement, scorePartWise, identificationelement);
        getCredit(scorePartWise, creditelementslist);
        Element partlistelement = rootElement.element(Constact.PARTLIST);
        getPartList(scorePartWise, partlistelement);
        Element partelement = rootElement.element(Constact.PART);
        getPart(scorePartWise, partelement);

        return scorePartWise;
    }

    /**
     * 得到最后一部分对象
     *
     * @param scorePartWise
     * @param partelement
     */

    private void getPart(ScorePartWise scorePartWise, Element partelement) {
        if (partelement != null) {
            Part part = new Part();
            scorePartWise.setPart(part);
            List measureelements = partelement.elements(Constact.MEASURE);
            if (measureelements != null && measureelements.size() > 0) {
                List<Measure> mlist = new ArrayList<>();
                part.setMeasureList(mlist);
                for (Iterator it = measureelements.iterator(); it.hasNext(); ) {
                    Element meanext = (Element) it.next();
                    Measure measure = new Measure();
                    mlist.add(measure);
                    if (meanext.attribute(Constact.NUMBER) != null) {
                        measure.setNumber(Integer.parseInt(meanext.attribute(Constact.NUMBER).getText()));
                    }

                    if (meanext.attribute(Constact.WIDTH) != null) {
                        measure.setWidth(Float.parseFloat(meanext.attribute(Constact.WIDTH).getText()));
                    }
                    Element printelement = meanext.element(Constact.PRINT);
                    if (printelement != null) {
                        Print print = new Print();
                        measure.setPrint(print);
                        Element systemlayoutelement = printelement.element(Constact.SYSTEMLAYOUT);
                        if (systemlayoutelement != null) {
                            SystemLayout systemLayout = new SystemLayout();
                            print.setSystemLayout(systemLayout);
                            if (systemlayoutelement.element(Constact.TOPSYSTEMDISTANCE) != null) {
                                systemLayout.setTopSystemDistance(Float.parseFloat(systemlayoutelement.element(Constact.TOPSYSTEMDISTANCE).getText()));
                            }
                            Element systemMarginselement = systemlayoutelement.element(Constact.SYSTEMMARGINS);
                            if (systemMarginselement != null) {
                                SystemMargins systemMargins = new SystemMargins();
                                systemLayout.setSystemMargins(systemMargins);
                                systemMargins.setLeftMargin(Float.parseFloat(systemMarginselement.element(Constact.LEFTMARGIN).getText()));
                                systemMargins.setRightMargin(Float.parseFloat(systemMarginselement.element(Constact.RIGHTMARGIN).getText()));
                            }
                        }
                    }

                    Element barlineelement = meanext.element(Constact.BARLINE);
                    if (barlineelement != null) {
                        BarLine barLine = new BarLine();
                        measure.setBarLine(barLine);
//                        Element barlineelement = meanext.element(Constact.BARLINE);
                        Attribute locationattribute = barlineelement.attribute(Constact.LOCATION);
                        if (locationattribute != null) {
                            barLine.setLocation(locationattribute.getText());
                        }
                        Element barstyleelement = barlineelement.element(Constact.BARSTYLE);
                        if (barstyleelement != null) {
                            barLine.setBarStyle(barstyleelement.getText());
                        }
                        Element repeatelement = barlineelement.element(Constact.REPEAT);
                        if (repeatelement != null) {
                            Repeat repeat = new Repeat();
                            barLine.setRepeat(repeat);
                            Attribute directionattribute = repeatelement.attribute(Constact.DIRECTION);
                            if (directionattribute != null) {
                                repeat.setDirection(directionattribute.getText());
                            }
                        }
                    }

                    if (meanext.element(Constact.FORWARD) != null) {
                        Forward forward = new Forward();
                        measure.setForward(forward);
                        Element forwardelement = meanext.element(Constact.FORWARD);
                        if (forwardelement.element(Constact.DURATION) != null) {
                            forward.setDuration(Integer.parseInt(forwardelement.element(Constact.DURATION).getText()));
                        }
                    }

                    List<Element> directionelements = meanext.elements(Constact.DIRECTION);
                    if (directionelements != null && directionelements.size() > 0) {
                        List<Direction> directionList = new ArrayList<>();
                        measure.setDirection(directionList);
                        for (Iterator itera = directionelements.iterator(); itera.hasNext(); ) {
                            Element directmentnext = (Element) itera.next();
                            if (directmentnext != null) {
                                Direction direction = new Direction();
                                directionList.add(direction);
                                if (directmentnext.element(Constact.SOUND) != null) {
                                    Element soundelement = directmentnext.element(Constact.SOUND);
                                    Sound sound = new Sound();
                                    direction.setSound(sound);
                                    if (soundelement.attribute(Constact.DYNAMICS) != null) {
                                        sound.setDynamics(soundelement.attribute(Constact.DYNAMICS).getText());
                                    }
                                    if (soundelement.attribute(Constact.SEGNO) != null) {
                                        sound.setSegno(soundelement.attribute(Constact.SEGNO).getText());
                                    }
                                    if (soundelement.attribute(Constact.CODA) != null) {
                                        sound.setCoda(soundelement.attribute(Constact.CODA).getText());
                                    }
                                    if (soundelement.attribute(Constact.FINE) != null) {
                                        sound.setFine(soundelement.attribute(Constact.FINE).getText());
                                    }
                                    if (soundelement.attribute(Constact.TOCODA) != null) {
                                        sound.setTocoda(soundelement.attribute(Constact.TOCODA).getText());
                                    }
                                    if (soundelement.attribute(Constact.DACAPO) != null) {
                                        sound.setDacapo(soundelement.attribute(Constact.DACAPO).getText());
                                    }
                                    if (soundelement.attribute(Constact.DALSEGNO) != null) {
                                        sound.setDalsegno(soundelement.attribute(Constact.DALSEGNO).getText());
                                    }
                                }
                                if (directmentnext.elements(Constact.DIRECTIONTYPE) != null) {
                                    Element directionTypeelements = directmentnext.element(Constact.DIRECTIONTYPE);
                                    DirectionType directionType = new DirectionType();
                                    direction.setDirectionType(directionType);
                                    if (directmentnext.attribute(Constact.PLACEMENT) != null) {
                                        direction.setPlacement(directmentnext.attribute(Constact.PLACEMENT).getText());
                                    }
                                    if (directionTypeelements.element(Constact.SEGNO) != null) {
                                        directionType.setSegno(true);
                                    }
                                    if (directionTypeelements.element(Constact.CODA) != null) {
                                        directionType.setCoda(true);
                                    }
                                    if (directionTypeelements.element(Constact.WORDS) != null) {
                                        Words words = new Words();
                                        directionType.setWords(words);
                                        Element wordselement = directionTypeelements.element(Constact.WORDS);
                                        if (wordselement!=null){
                                            words.setWords(wordselement.getText());
                                        }
                                    }


                                    Element wedgeElements = directionTypeelements.element(Constact.WEDGE);
                                    if (wedgeElements != null) {
                                        Wedge wedge = new Wedge();
                                        directionType.setWedge(wedge);
                                        if (wedgeElements.attribute(Constact.TYPE) != null) {
                                            wedge.setType(wedgeElements.attribute(Constact.TYPE).getText());
                                        }
                                        if (wedgeElements.attribute(Constact.NUMBER) != null) {
                                            wedge.setNumber(Integer.parseInt(wedgeElements.attribute(Constact.NUMBER).getText()));
                                        }
                                    }


                                    Element dynamicselement = directionTypeelements.element(Constact.DYNAMICS);
                                    if (dynamicselement != null) {
                                        Dynamics dynamics = new Dynamics();
                                        directionType.setDynamics(dynamics);
                                        Element PPPelement = dynamicselement.element(Constact.PPP);
                                        Element PPelement = dynamicselement.element(Constact.PP);
                                        Element Pelement = dynamicselement.element(Constact.P);
                                        Element MPelement = dynamicselement.element(Constact.MP);
                                        Element MFelement = dynamicselement.element(Constact.MF);
                                        Element Felement = dynamicselement.element(Constact.F);
                                        Element FFelement = dynamicselement.element(Constact.FF);
                                        Element FFFelement = dynamicselement.element(Constact.FFF);
                                        Element FPelement = dynamicselement.element(Constact.FP);
                                        Element SFelement = dynamicselement.element(Constact.SF);
                                        Element SFZelement = dynamicselement.element(Constact.SFZ);
                                        Element SFFelement = dynamicselement.element(Constact.SFF);
                                        Element SFFZelement = dynamicselement.element(Constact.SFFZ);
                                        Element SFPelement = dynamicselement.element(Constact.SFP);
                                        Element SFPPelement = dynamicselement.element(Constact.SFPP);
                                        Element RFZelement = dynamicselement.element(Constact.RFZ);
                                        Element RFelement = dynamicselement.element(Constact.RF);
                                        Element FZelement = dynamicselement.element(Constact.FZ);
                                        if (FPelement!=null) {
                                            dynamics.setFP(true);
                                        }
                                        if (SFelement!=null) {
                                            dynamics.setSF(true);
                                        }
                                        if (SFZelement!=null) {
                                            dynamics.setRFZ(true);
                                        }
                                        if (SFFelement!=null) {
                                            dynamics.setSFF(true);
                                        }
                                        if (SFFZelement!=null) {
                                            dynamics.setSFFZ(true);
                                        }
                                        if (SFPelement!=null) {
                                            dynamics.setSFP(true);
                                        }
                                        if (SFPPelement!=null) {
                                            dynamics.setSFPP(true);
                                        }
                                        if (RFZelement!=null) {
                                            dynamics.setRFZ(true);
                                        }
                                        if (RFelement!=null) {
                                            dynamics.setRF(true);
                                        }
                                        if (FZelement!=null) {
                                            dynamics.setFZ(true);
                                        }
                                        if (PPPelement != null) {
                                            dynamics.setPPP(true);
                                        }
                                        if (PPelement != null) {
                                            dynamics.setPP(true);
                                        }
                                        if (Pelement != null) {
                                            dynamics.setP(true);
                                        }
                                        if (MPelement != null) {
                                            dynamics.setMP(true);
                                        }
                                        if (MFelement != null) {
                                            dynamics.setMF(true);
                                        }
                                        if (Felement != null) {
                                            dynamics.setF(true);
                                        }
                                        if (FFelement != null) {
                                            dynamics.setFF(true);
                                        }
                                        if (FFFelement != null) {
                                            dynamics.setFFF(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Element attributeselement = meanext.element(Constact.ATTRIBUTES);
                    if (attributeselement != null) {
                        Attributes attributes = new Attributes();
                        measure.setAttributes(attributes);
                        if (attributeselement.element(Constact.DIVISIONS) != null) {
                            attributes.setDivisions(Integer.parseInt(attributeselement.element(Constact.DIVISIONS).getText()));
                        }
                        Element keyelement = attributeselement.element(Constact.KEY);
                        if (keyelement != null) {
                            Key key = new Key();
                            attributes.setKey(key);
                            key.setFifths(Integer.parseInt(keyelement.element(Constact.FIFTHS).getText()));
                        }
                        Element timeelement = attributeselement.element(Constact.TIME);
                        if (timeelement != null) {
                            Time time = new Time();
                            attributes.setTime(time);
                            time.setBeats(Integer.parseInt(timeelement.element(Constact.BEATS).getText()));
                            time.setBeatType(Integer.parseInt(timeelement.element(Constact.BEATTYPE).getText()));
                        }
                        Element clefelement = attributeselement.element(Constact.CLEF);
                        if (clefelement != null) {
                            Clef clef = new Clef();
                            attributes.setClef(clef);
                            clef.setLine(Integer.parseInt(clefelement.element(Constact.LINE).getText()));
                            clef.setSign(clefelement.element(Constact.SIGN).getText());
                        }
                    }

                    List noteselements = meanext.elements(Constact.NOTE);
                    if (noteselements != null) {
                        List<Note> noteList = new ArrayList<>();
                        measure.setNotes(noteList);
                        for (Iterator iterator = noteselements.iterator(); iterator.hasNext(); ) {
                            Element notenext = (Element) iterator.next();
                            if (notenext != null) {
                                Note note = new Note();
                                noteList.add(note);
                                if (notenext.attribute(Constact.DEFAULTX) != null) {
                                    note.setDefaultX(Float.parseFloat(notenext.attribute(Constact.DEFAULTX).getText()));
                                    note.setDefaultY(Float.parseFloat(notenext.attribute(Constact.DEFAULTY).getText()));
                                }
                                if (notenext.element(Constact.PITCH) != null) {
                                    Pitch pitch = new Pitch();
                                    note.setPitch(pitch);
                                    pitch.setStep(notenext.element(Constact.PITCH).element(Constact.STEP).getText());
                                    if (notenext.element(Constact.PITCH).element(Constact.ALTER) != null) {
                                        pitch.setAlter(Integer.parseInt(notenext.element(Constact.PITCH).element(Constact.ALTER).getText()));
                                    }
                                    pitch.setOctave(Integer.parseInt(notenext.element(Constact.PITCH).element(Constact.OCTAVE).getText()));
                                }


                                if (notenext.element(Constact.NOTATIONS) != null) {
                                    Notations notations = new Notations();
                                    note.setNotations(notations);
                                    Element notationselement = notenext.element(Constact.NOTATIONS);
                                    if (notationselement.element(Constact.FERMATA) != null) {
                                        Fermata fermata = new Fermata();
                                        notations.setFermata(fermata);
                                        fermata.setType(notationselement.element(Constact.FERMATA).attribute(Constact.TYPE).getText());
                                    }
                                    if (notationselement.element(Constact.ARTICULATIONS) != null) {
                                        Articulations articulations = new Articulations();
                                        notations.setArticulations(articulations);
                                        Element articulationselement = notationselement.element(Constact.ARTICULATIONS);
                                        if (articulationselement.element(Constact.ACCENT) != null) {
                                            articulations.setAccent(true);
                                        }
                                        if (articulationselement.element(Constact.STACCATO) != null) {
                                            articulations.setStaccato(true);
                                        }
                                    }
                                    if (notationselement.element(Constact.ORNAMENTS) != null) {
                                        Ornaments ornaments = new Ornaments();
                                        notations.setOrnaments(ornaments);
                                        Element ornamentselement = notationselement.element(Constact.ORNAMENTS);
                                        if (ornamentselement.element(Constact.INVERTEDMORDENT) != null) {
                                            ornaments.setInvertedMordent(true);
                                        }
                                        if (ornamentselement.element(Constact.MORDENT) != null) {
                                            ornaments.setMordent(true);
                                        }
                                        if (ornamentselement.element(Constact.INVERTEDTURN) != null) {
                                            ornaments.setInvertedTurn(true);
                                        }
                                        if (ornamentselement.element(Constact.TURN) != null) {
                                            ornaments.setTurn(true);
                                        }
                                        if (ornamentselement.element(Constact.TRILLMARK) != null) {
                                            ornaments.setTrillMark(true);
                                        }

                                    }
                                }
                                if (notenext.element(Constact.ACCIDENTAL) != null) {
                                    note.setAccidental(notenext.element(Constact.ACCIDENTAL).getText());
                                }
                                if (notenext.element(Constact.REST) != null) {
                                    note.setRest(true);
                                } else {
                                    note.setRest(false);
                                }
                                if (notenext.element(Constact.NOTATIONS) != null) {
                                    Element notationselement = notenext.element(Constact.NOTATIONS);
                                    if (notationselement.element(Constact.SLUR) != null) {
                                        Slur slur = new Slur();
                                        note.setSlur(slur);
                                        slur.setType(notationselement.element(Constact.SLUR).attribute(Constact.TYPE).getText());
                                        slur.setNumber(Integer.parseInt(notationselement.element(Constact.SLUR).attribute(Constact.NUMBER).getText()));
                                    }




                                }
                                if (notenext.element(Constact.DURATION) != null) {

                                    note.setDuration(Integer.parseInt(notenext.element(Constact.DURATION).getText()));
                                }
                                if (notenext.element(Constact.VOICE) != null) {
                                    note.setVoice(Integer.parseInt(notenext.element(Constact.VOICE).getText()));
                                }
                                if (notenext.element(Constact.TYPE) != null) {
                                    note.setType(notenext.element(Constact.TYPE).getText());
                                }
                                if (notenext.element(Constact.STEM) != null) {
                                    note.setStem(notenext.element(Constact.STEM).getText());
                                }

                                List lyricelements = notenext.elements(Constact.LYRIC);
                                if (lyricelements != null && lyricelements.size() > 0) {
                                    List<Lyric> lyricslist = new ArrayList<>();
                                    note.setLyricList(lyricslist);
                                    for (Iterator itera = lyricelements.iterator(); itera.hasNext(); ) {
                                        Element lynext = (Element) itera.next();
                                        if (lynext != null) {
                                            Lyric lyric = new Lyric();
                                            lyricslist.add(lyric);
                                            if (lynext.attribute(Constact.NUMBER) != null) {
                                                lyric.setNumber(Integer.parseInt(lynext.attribute(Constact.NUMBER).getText()));
                                            }
                                            if (lynext.element(Constact.SYLLABIC) != null) {
                                                lyric.setSyllabic(lynext.element(Constact.SYLLABIC).getText());
                                            }
                                            if (lynext.element(Constact.TEXT) != null) {
                                                lyric.setText(lynext.element(Constact.TEXT).getText());
                                            }
                                        }
                                    }
                                }

                                List<Element> dotelements = notenext.elements(Constact.DOT);
                                if (dotelements != null && dotelements.size() > 0) {
                                    List<Dot> dotlist = new ArrayList<>();
                                    note.setIsDot(dotlist);
                                    for (int i = 0; i < dotelements.size(); i++) {
                                        Dot dot = new Dot();
                                        dotlist.add(dot);
                                        dot.setDot(true);
                                    }
                                }
                                List<Element> beamelements = notenext.elements(Constact.BEAM);
                                if (beamelements != null && beamelements.size() > 0) {
                                    List<Beam> beamList = new ArrayList<>();
                                    note.setBeamList(beamList);
                                    for (Iterator itera = beamelements.iterator(); itera.hasNext(); ) {
                                        Element beannext = (Element) itera.next();
                                        if (beannext != null) {
                                            Beam beam = new Beam();
                                            beamList.add(beam);
                                            beam.setContent(beannext.getText());
                                            if (beannext.attribute(Constact.NUMBER) != null) {
                                                beam.setNumber(Integer.parseInt(beannext.attribute(Constact.NUMBER).getText()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    /**
     * 拿到下一个标签
     *
     * @param scorePartWise
     * @param partlistelement
     */
    private void getPartList(ScorePartWise scorePartWise, Element partlistelement) {
        if (partlistelement != null) {
            PartList partList = new PartList();
            scorePartWise.setPartList(partList);
            List scorePartelements = partlistelement.elements(Constact.SCOREPART);
//            partList.setScorePartList(scorePartelements);
            if (scorePartelements != null && scorePartelements.size() > 0) {
                List<ScorePart> scorePartList = new ArrayList<>();
                partList.setScorePartList(scorePartList);
                for (Iterator it = scorePartelements.iterator(); it.hasNext(); ) {
                    ScorePart scorePart = new ScorePart();
                    Element spelement = (Element) it.next();
                    scorePartList.add(scorePart);
                    scorePart.setId(spelement.attribute(Constact.ID).getText());
                    scorePart.setPartname(spelement.element(Constact.PARTNAME).getText());
                    scorePart.setPartAbbreviation(spelement.element(Constact.PARTABBREVIATION).getText());
                    if (spelement.element(Constact.SCOREINSTRUMENT) != null) {
                        ScoreInstrument scoreInstrument = new ScoreInstrument();
                        scoreInstrument.setId(spelement.element(Constact.SCOREINSTRUMENT).getText());
                        scoreInstrument.setInstrumenyName(spelement.element(Constact.SCOREINSTRUMENT).element(Constact.INSTRUMENTNAME).getText());
                    }
                    if (spelement.element(Constact.MIDIDEVICE) != null) {
                        MidiDevice midiDevice = new MidiDevice();
                        scorePart.setMidiDevice(midiDevice);
                        midiDevice.setId(spelement.element(Constact.MIDIDEVICE).attribute(Constact.ID).getText());
                        midiDevice.setPort(Integer.parseInt(spelement.element(Constact.MIDIDEVICE).attribute(Constact.PORT).getText()));
                    }
                    if (spelement.element(Constact.MIDIINSTRUMENT) != null) {
                        MidiInstrument midiInstrument = new MidiInstrument();
                        scorePart.setMidiInstrument(midiInstrument);
                        midiInstrument.setId(spelement.element(Constact.MIDIINSTRUMENT).attribute(Constact.ID).getText());
                        midiInstrument.setMidiChannel(Integer.parseInt(spelement.element(Constact.MIDIINSTRUMENT).element(Constact.MIDICHANNEL).getText()));
                        midiInstrument.setMidiProgram(Integer.parseInt(spelement.element(Constact.MIDIINSTRUMENT).element(Constact.MIDIPROGRAM).getText()));
                        midiInstrument.setVolume(Double.parseDouble(spelement.element(Constact.MIDIINSTRUMENT).element(Constact.VOLUME).getText()));
                        midiInstrument.setPan(Integer.parseInt(spelement.element(Constact.MIDIINSTRUMENT).element(Constact.PAN).getText()));

                    }
                }
            }


        }
    }

    /**
     * 得到第三个标签
     *
     * @param scorePartWise
     * @param creditelementslist
     */
    private void getCredit(ScorePartWise scorePartWise, List creditelementslist) {
        if (creditelementslist != null && creditelementslist.size() > 0) {
            List<Credit> creditList = new ArrayList<>();
            scorePartWise.setCreditList(creditList);
            for (Iterator it = creditelementslist.iterator(); it.hasNext(); ) {

                Element creditelements = (Element) it.next();
                if (creditelements != null) {
                    Credit credit = new Credit();
                    creditList.add(credit);
                    Element creditwordselement = creditelements.element(Constact.CREDITWORDS);
                    if (creditwordselement != null) {
                        CreditWords creditWords = new CreditWords();
                        credit.setCreditWords(creditWords);
                        if (creditwordselement.attribute(Constact.DEFAULTX) != null) {
                            creditWords.setDefaultX(Double.parseDouble(creditwordselement.attribute(Constact.DEFAULTX).getText()));
                        }
                        if (creditwordselement.attribute(Constact.DEFAULTY) != null) {
                            creditWords.setDefaultY(Double.parseDouble(creditwordselement.attribute(Constact.DEFAULTY).getText()));
                        }
                        if (creditwordselement.attribute(Constact.JUSTIFY) != null) {
                            creditWords.setJustify(creditwordselement.attribute(Constact.JUSTIFY).getText());
                        }
                        if (creditwordselement.attribute(Constact.VALIGN) != null) {
                            creditWords.setValign(creditwordselement.attribute(Constact.VALIGN).getText());
                        }
                        if (creditwordselement.attribute(Constact.FONTSIZE) != null) {
                            creditWords.setFontSize(Integer.parseInt(creditwordselement.attribute(Constact.FONTSIZE).getText()));
                        }
                        creditWords.setContext(creditwordselement.getText());
                    }
                }


            }
        }
    }

    /**
     * 得到第2个标签
     *
     * @param rootElement
     * @param scorePartWise
     * @param identificationelement
     */
    private void getIdentification(Element rootElement, ScorePartWise scorePartWise, Element identificationelement) {
        if (identificationelement != null) {
            Identification identification = new Identification();
            scorePartWise.setIdentification(identification);
            Element encodingelement = identificationelement.element(Constact.ENCODING);

            if (encodingelement != null) {
                Encoding encoding = new Encoding();
                identification.setEncoding(encoding);
                Element softwareelement = encodingelement.element(Constact.SOFTWARE);
                if (softwareelement != null) {
                    encoding.setSoftware(softwareelement.getText());
                }
                Element encodingdateelement = encodingelement.element(Constact.ENCODINGDATE);
                if (encodingdateelement != null) {
                    encoding.setEncoding_date(encodingdateelement.getText());
                }
                List supportsList = encodingelement.elements(Constact.SUPPORTS);
                if (supportsList != null && supportsList.size() > 0) {
                    List<Supports> supportList = new ArrayList<>();
                    encoding.setSupportsList(supportList);
                    for (Iterator it = supportsList.iterator(); it.hasNext(); ) {
                        Element supportselement = (Element) it.next();
                        if (supportselement != null) {
                            Supports supports = new Supports();
                            supportList.add(supports);
                            if (supportselement.attribute(Constact.ELEMENT).getText() != null) {
                                supports.setElement(supportselement.attribute(Constact.ELEMENT).getText());
                            }
                            if (supportselement.attribute(Constact.ATTRIBUTE) != null) {
                                supports.setAttribute(supportselement.attribute(Constact.ATTRIBUTE).getText());
                            }
                            if (supportselement.attribute(Constact.TYPE).getText() != null) {
                                supports.setType(supportselement.attribute(Constact.TYPE).getText());
                            }
                            if (supportselement.attribute(Constact.VALUE) != null) {
                                supports.setValue(supportselement.attribute(Constact.VALUE).getText());
                            }
                        }
                    }
                }
                Element defaultelement = rootElement.element(Constact.DEFAULTS);
                if (defaultelement != null) {
                    Defaults defaults = new Defaults();
                    scorePartWise.setDefaults(defaults);
                    Element scalingelement = defaultelement.element(Constact.SCALING);
                    if (scalingelement != null) {
                        Scaling scaling = new Scaling();
                        defaults.setScaling(scaling);
                        Element millimeterselement = scalingelement.element(Constact.MILLIMETERS);
                        if (millimeterselement != null) {
                            scaling.setMillimeters(Double.parseDouble(millimeterselement.getText()));
                        }
                        Element tenthselement = scalingelement.element(Constact.TENTHS);
                        if (tenthselement != null) {
                            scaling.setTenths(Integer.parseInt(tenthselement.getText()));
                        }
                    }
                    Element pagelayoutelement = defaultelement.element(Constact.PAGELAYOUT);
                    if (pagelayoutelement != null) {
                        PageLayout pageLayout = new PageLayout();
                        defaults.setPageLayout(pageLayout);
                        Element pageheightelement = pagelayoutelement.element(Constact.PAGEHEIGHT);
                        if (pageheightelement != null) {
                            pageLayout.setPageHeight(Double.parseDouble(pageheightelement.getText()));
                        }
                        Element pagewidthelement = pagelayoutelement.element(Constact.PAGEWIDTH);
                        if (pagewidthelement != null) {
                            pageLayout.setPageWidth(Double.parseDouble(pagewidthelement.getText()));
                        }
                        List pagemarginselementslist = pagelayoutelement.elements(Constact.PAGEMARGINS);
                        if (pagemarginselementslist != null && pagemarginselementslist.size() > 0) {
                            List<PageMargins> pageMarginsList = new ArrayList<>();
                            pageLayout.setPageMarginsList(pageMarginsList);
                            for (Iterator it = pagemarginselementslist.iterator(); it.hasNext(); ) {
                                Element pagemarginselement = (Element) it.next();
                                PageMargins pageMargins = new PageMargins();
                                pageMarginsList.add(pageMargins);
                                Element leftmarginelement = pagemarginselement.element(Constact.LEFTMARGIN);
                                if (leftmarginelement != null) {
                                    pageMargins.setLeftMargin(Double.parseDouble(leftmarginelement.getText()));
                                }
                                Element rightmarginelement = pagemarginselement.element(Constact.RIGHTMARGIN);
                                if (rightmarginelement != null) {
                                    pageMargins.setLeftMargin(Double.parseDouble(rightmarginelement.getText()));
                                }
                                Element topmarginelement = pagemarginselement.element(Constact.TOPMARGIN);
                                if (topmarginelement != null) {
                                    pageMargins.setLeftMargin(Double.parseDouble(topmarginelement.getText()));
                                }
                                Element bottommarginelement = pagemarginselement.element(Constact.BOTTOMMARGIN);
                                if (bottommarginelement != null) {
                                    pageMargins.setLeftMargin(Double.parseDouble(bottommarginelement.getText()));
                                }
                                if (pagemarginselement.attribute(Constact.TYPE) != null) {
                                    pageMargins.setType(pagemarginselement.attribute(Constact.TYPE).getText());
                                }
                            }
                        }

                    }
                    Element wordfontelement = defaultelement.element(Constact.WORDFONT);
                    if (wordfontelement != null) {
                        WorkFont workFont = new WorkFont();
                        defaults.setWorkFont(workFont);
                        if (wordfontelement.attribute(Constact.FONTFAMILY) != null) {
                            workFont.setFontFamily(wordfontelement.attribute(Constact.FONTFAMILY).getText());
                        }
                        if (wordfontelement.attribute(Constact.FONTSIZE) != null) {
                            workFont.setFontSize(Integer.parseInt(wordfontelement.attribute(Constact.FONTSIZE).getText()));
                        }
                    }
                    Element lyricfontelement = defaultelement.element(Constact.LYRICFONT);
                    if (lyricfontelement != null) {
                        LyricFont lyricFont = new LyricFont();
                        defaults.setLyricFont(lyricFont);
                        if (lyricfontelement.attribute(Constact.FONTFAMILY) != null) {
                            lyricFont.setFontFamily(lyricfontelement.attribute(Constact.FONTFAMILY).getText());
                        }
                        if (lyricfontelement.attribute(Constact.FONTSIZE) != null) {
                            lyricFont.setFontSize(Integer.parseInt(lyricfontelement.attribute(Constact.FONTSIZE).getText()));
                        }
                    }
                }

            }
        }
    }

    /**
     * 得到第一个标签
     *
     * @param scorePartWise
     * @param workelement
     * @param work_titleelement
     */
    private void getTitle(ScorePartWise scorePartWise, Element workelement, Element work_titleelement) {
        //创建word的对象，并拿到title
        if (workelement != null && work_titleelement != null) {
            Work w = new Work();
            scorePartWise.setWork(w);
            w.setWork_title(work_titleelement.getText());
        }
    }

}

