package client;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import common.GamePlayer;
    import java.util.*;
    import java.io.*;
public class FileHandler {
  double bHeight;
  double bWidth;
  double bX;
  double bY;
  private GamePlayer player;

  public FileHandler (GamePlayer player) {
    bHeight = 0;
    bWidth = 0;
    bX = 0;
    bY = 0;
    this.player = player;
    importCharacters();
  }

  public void importCharacters () {
    try {

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean charHealth = false;
        boolean boxHead = false;
        boolean boxBody = false;
        boolean boxHeight = false;
        boolean boxWidth = false;
        boolean boxX = false;
        boolean boxY = false;
        boolean boxRleg = false;
        boolean boxLleg = false;

        public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

          if (qName.equalsIgnoreCase("HEALTH")) {
            charHealth = true;
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
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {

          if (qName.equalsIgnoreCase("HEALTH")) {
            charHealth = false;
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
        }

        public void characters(char ch[], int start, int length) throws SAXException {

          if (boxHead || boxBody || boxRleg || boxLleg) {
            if (boxHeight) {
              bHeight = Double.parseDouble(new String(ch,start,length));
            }
            if (boxWidth) {
              bWidth = Double.parseDouble(new String(ch,start,length));
            }
            if (boxX) {
              bX = Double.parseDouble(new String(ch,start,length));
            }
            if (boxY) {
              bY = Double.parseDouble(new String(ch,start,length));
            }
          }
        }

      };

      saxParser.parse("charater.xml",handler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  }
