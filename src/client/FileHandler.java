package client;

import common.GamePlayer;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
    for (Object c : controls) {
      System.out.println("Controls: " + c);
    }
    return controls;
  }

  public void setControls(ArrayList<String> al) {
    al = new ArrayList<>();

  }
}
