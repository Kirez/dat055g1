package client;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import common.GamePlayer;
import java.util.*;
import java.io.*;

public class FileHandler {

  static double bHeight;
  static double bWidth;
  static double bX;
  static double bY;
  static int health;
  static double aSpool;
  static double aDuration;
  static double aCooldown;
  private GamePlayer player;


  public static void importCharacters(GamePlayer player) {

    try {

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean charHealth = false;
        boolean charSize = false;
        boolean boxHead = false;
        boolean boxBody = false;
        boolean boxHeight = false;
        boolean boxWidth = false;
        boolean boxX = false;
        boolean boxY = false;
        boolean boxRleg = false;
        boolean boxLleg = false;
        boolean charJab = false;
        boolean charKick = false;
        boolean duration = false;
        boolean spool = false;
        boolean cooldown = false;

        public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

          if (qName.equalsIgnoreCase("HEALTH")) {
            charHealth = true;
          }
          if (qName.equalsIgnoreCase("CHARSIZE")) {
            charSize = true;
          }
          if (qName.equalsIgnoreCase("JAB")) {
            charJab = true;
          }
          if (qName.equalsIgnoreCase("HEAD")) {
            boxHead = true;
          }
          if (qName.equalsIgnoreCase("BODY")) {
            boxBody = true;
          }
          if (qName.equalsIgnoreCase("RLEG")) {
            boxRleg = true;
          }
          if (qName.equalsIgnoreCase("LLEG")) {
            boxLleg = true;
          }
          if (qName.equalsIgnoreCase("HEIGHT")) {
            boxHeight = true;
          }
          if (qName.equalsIgnoreCase("WIDTH")) {
            boxWidth = true;
          }
          if (qName.equalsIgnoreCase("X")) {
            boxX = true;
          }
          if (qName.equalsIgnoreCase("Y")) {
            boxY = true;
          }
          if (qName.equalsIgnoreCase("SPOOL")) {
            spool = true;
          }
          if (qName.equalsIgnoreCase("DURATION")) {
            duration = true;
          }
          if (qName.equalsIgnoreCase("COOLDOWN")) {
            cooldown = true;
          }
          if (qName.equalsIgnoreCase("KICK")) {
            charKick = true;
          }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {

          if (qName.equalsIgnoreCase("HEALTH")) {
            charHealth = false;
            player.setHealth(health);
          }
          if (qName.equalsIgnoreCase("CHARSIZE")) {
            charSize = false;
            player.setCharsize(bHeight, bWidth);
          }
          if (qName.equalsIgnoreCase("JAB")) {
            charJab = false;
            player.addHitbox(bX, bY, bWidth, bHeight);
            player.setCycles(aSpool, aDuration, aCooldown, "jab");
          }
          if (qName.equalsIgnoreCase("HEAD")) {
            boxHead = false;
            player.addHurtbox(bX, bY, bWidth, bHeight);
          }
          if (qName.equalsIgnoreCase("BODY")) {
            boxBody = false;
            player.addHurtbox(bX, bY, bWidth, bHeight);
          }
          if (qName.equalsIgnoreCase("RLEG")) {
            boxRleg = false;
            player.addHurtbox(bX, bY, bWidth, bHeight);
          }
          if (qName.equalsIgnoreCase("LLEG")) {
            boxLleg = false;
            player.addHurtbox(bX, bY, bWidth, bHeight);
          }
          if (qName.equalsIgnoreCase("HEIGHT")) {
            boxHeight = false;
          }
          if (qName.equalsIgnoreCase("WIDTH")) {
            boxWidth = false;
          }
          if (qName.equalsIgnoreCase("X")) {
            boxX = false;
          }
          if (qName.equalsIgnoreCase("Y")) {
            boxY = false;
          }
          if (qName.equalsIgnoreCase("SPOOL")) {
            spool = false;
          }
          if (qName.equalsIgnoreCase("DURATION")) {
            duration = false;
          }
          if (qName.equalsIgnoreCase("COOLDOWN")) {
            cooldown = false;
          }
          if (qName.equalsIgnoreCase("KICK")) {
            charKick = false;
            player.addHitbox(bX, bY, bWidth, bHeight);
            player.setCycles(aSpool, aDuration, aCooldown, "kick");
          }
        }

        public void characters(char ch[], int start, int length) throws SAXException {

          if (boxHead || boxBody || boxRleg || boxLleg || charJab || charKick || charSize) {
            if (boxHeight) {
              bHeight = Double.parseDouble(new String(ch, start, length));
            }
            if (boxWidth) {
              bWidth = Double.parseDouble(new String(ch, start, length));
            }
            if (boxX) {
              bX = Double.parseDouble(new String(ch, start, length));
            }
            if (boxY) {
              bY = Double.parseDouble(new String(ch, start, length));
            }
            if (spool) {
              aSpool = Double.parseDouble(new String(ch, start, length));
            }
            if (duration) {
              aDuration = Double.parseDouble(new String(ch, start, length));
            }
            if (cooldown) {
              aCooldown = Double.parseDouble(new String(ch, start, length));
            }
          }
          if (charHealth) {
            health = Integer.parseInt(new String(ch, start, length));
          }
        }

      };

      saxParser.parse("charater.xml", handler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<String> importControls() {
    ArrayList controls = new ArrayList<String>();

    try {

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean cJump = false;
        boolean cLeft = false;
        boolean cRight = false;
        boolean cDown = false;
        boolean cJab = false;
        boolean cKick = false;
        boolean player1 = false;
        boolean player2 = false;
        String temp;

        public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
          if (qName.equalsIgnoreCase("P1")) {
            player1 = true;
          }
          if (qName.equalsIgnoreCase("P2")) {
            player2 = true;
          }
          if (qName.equalsIgnoreCase("JUMP")) {
            cJump = true;
          }
          if (qName.equalsIgnoreCase("LEFT")) {
            cLeft = true;
          }
          if (qName.equalsIgnoreCase("RIGHT")) {
            cRight = true;
          }
          if (qName.equalsIgnoreCase("DOWN")) {
            cDown = true;
          }
          if (qName.equalsIgnoreCase("JAB")) {
            cJab = true;
          }
          if (qName.equalsIgnoreCase("KICK")) {
            cKick = true;
          }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
          if (qName.equalsIgnoreCase("P1")) {
            player1 = false;
          }
          if (qName.equalsIgnoreCase("P2")) {
            player2 = false;
          }
          if (qName.equalsIgnoreCase("JUMP")) {
            cJump = false;
            controls.add(temp);
          }
          if (qName.equalsIgnoreCase("LEFT")) {
            cLeft = false;
            controls.add(temp);
          }
          if (qName.equalsIgnoreCase("RIGHT")) {
            cRight = false;
            controls.add(temp);
          }
          if (qName.equalsIgnoreCase("DOWN")) {
            cDown = false;
            controls.add(temp);
          }
          if (qName.equalsIgnoreCase("JAB")) {
            cJab = false;
            controls.add(temp);
          }
          if (qName.equalsIgnoreCase("KICK")) {
            cKick = false;
            controls.add(temp);
          }
        }

        public void characters(char ch[], int start, int length) throws SAXException {
          if (player1 || player2) {
            if (cJump) {
              temp = new String(ch, start, length);
            }
            if (cLeft) {
              temp = new String(ch, start, length);
            }
            if (cRight) {
              temp = new String(ch, start, length);
            }
            if (cDown) {
              temp = new String(ch, start, length);
            }
            if (cJab) {
              temp = new String(ch, start, length);
            }
            if (cKick) {
              temp = new String(ch, start, length);
            }
          }
        }
      };

      saxParser.parse("Settings.XML", handler);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return controls;
  }

  public static void setControls(ArrayList<String> al) {
    try {
      XMLReader xr = new XMLFilterImpl((XMLReaderFactory.createXMLReader())) {
        ArrayList<String> nControls = al;
        String tagName1 = "";
        String tagName2 = "";
        String temp;

        public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
          if (qName.equalsIgnoreCase("P1") || qName.equalsIgnoreCase("P2")) {
            tagName1 = qName;
          } else {
            tagName2 = qName;
          }
          super.startElement(uri, localName, qName, attributes);
        }

        public void endElement(String uri, String localName,
            String qName) throws SAXException {
          if (qName.equalsIgnoreCase("P1") || qName.equalsIgnoreCase("P2")) {
            tagName1 = "";
          } else {
            tagName2 = "";
          }
          super.endElement(uri, localName, qName);
        }

        public void characters(char[] ch,
            int start, int length) throws SAXException {
          if (tagName1.equalsIgnoreCase("P1")) {
            if (tagName2.equalsIgnoreCase("JUMP")) {
              temp = nControls.get(0);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("LEFT")) {
              temp = nControls.get(1);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("DOWN")) {
              temp = nControls.get(2);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("RIGHT")) {
              temp = nControls.get(3);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }

            if (tagName2.equalsIgnoreCase("JAB")) {
              temp = nControls.get(4);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("KICK")) {
              temp = nControls.get(5);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
          }
          if (tagName1.equalsIgnoreCase("P2")) {
            if (tagName2.equalsIgnoreCase("JUMP")) {
              temp = nControls.get(6);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("LEFT")) {
              temp = nControls.get(7);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("DOWN")) {
              temp = nControls.get(8);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("RIGHT")) {
              temp = nControls.get(9);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }

            if (tagName2.equalsIgnoreCase("JAB")) {
              temp = nControls.get(10);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
            if (tagName2.equalsIgnoreCase("KICK")) {
              temp = nControls.get(11);
              ch = temp.toCharArray();
              start = 0;
              length = ch.length;
            }
          }
          super.characters(ch, start, length);

        }

      };
      Source src1 = new SAXSource(xr, new InputSource("Settings.XML"));
      File fw = new File("temp.XML");
      Result res1 = new StreamResult(fw);
      TransformerFactory.newInstance().newTransformer().transform(src1, res1);

      Source src2 = new SAXSource(new InputSource("temp.XML"));
      File settings = new File("Settings.XML");
      settings.setWritable(true);
      Result res2 = new StreamResult(settings);
      TransformerFactory.newInstance().newTransformer().transform(src2, res2);
      settings.setWritable(false);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


}

