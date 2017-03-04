package client;

import common.GamePlayer;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Reads and writes data from XML files
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class FileHandler {

  //Variables used for the imported characters
  private static double bHeight;
  private static double bWidth;
  private static double bX;
  private static double bY;
  private static int health;
  private static double aSpool;
  private static double aDuration;
  private static double aCooldown;

  /**
   * Imports the character data specified in an XML file using the SAX parser.
   *
   * @param player the player that receives the data
   */
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

        /**
         * Sets a specific boolean to true when a certain element starts.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @param attributes are the attributes in the file
         * @throws SAXException is a specific exception for the SAX parser
         */
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

        /**
         * Sets a specific boolean to false and uses the corresponding variable to set a character value.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @throws SAXException is a specific exception for the SAX parser
         */
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

        /**
         * Sets the corresponding variable when the specific elements are set to true.
         *
         * @param ch characters array of the content of a certain parsed element
         * @param start start of the element content
         * @param length length of the element content
         * @throws SAXException is a specific exception for the SAX parser
         */
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

  /**
   *  Reads the controls specified in the Settings.xml file and returns an ArrayList with the controls.
   */
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
        /**
         * Sets a specific boolean to true when a certain element starts.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @param attributes are the attributes in the file
         * @throws SAXException is a specific exception for the SAX parser
         */
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
        /**
         * Sets a specific boolean to false and adds the fetched control to the ArrayList.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @throws SAXException is a specific exception for the SAX parser
         */
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
        /**
         * Sets the temp String when the specific elements are set to true.
         *
         * @param ch characters array of the content of a certain parsed element
         * @param start start of the element content
         * @param length length of the element content
         * @throws SAXException is a specific exception for the SAX parser
         */
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

  /**
   * Writes the content of the received ArrayList to the Settings.XML file.
   *
   * @param al the received ArrayList of controls
   */
  public static void setControls(ArrayList<String> al) {
    try {
      XMLReader xr = new XMLFilterImpl((XMLReaderFactory.createXMLReader())) {
        ArrayList<String> nControls = al;
        String tagName1 = "";
        String tagName2 = "";
        String temp;
        /**
         * Sets one of strings to correspond the current element. The first String is only set when it parses in a player element.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @param attributes are the attributes in the file
         * @throws SAXException is a specific exception for the SAX parser
         */
        public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
          if (qName.equalsIgnoreCase("P1") || qName.equalsIgnoreCase("P2")) {
            tagName1 = qName;
          } else {
            tagName2 = qName;
          }
          super.startElement(uri, localName, qName, attributes);
        }
        /**
         * Sets the current element string name to nothing.
         *
         * @param uri is the Namespace of the attribute
         * @param localName is the local name of the attribute
         * @param qName is the qualified name of the attribute
         * @throws SAXException is a specific exception for the SAX parser
         */
        public void endElement(String uri, String localName,
            String qName) throws SAXException {
          if (qName.equalsIgnoreCase("P1") || qName.equalsIgnoreCase("P2")) {
            tagName1 = "";
          } else {
            tagName2 = "";
          }
          super.endElement(uri, localName, qName);
        }
        /**
         * When on the corresponding element this function writes the new control
         *
         * @param ch characters array of the content of a certain parsed element
         * @param start start of the element content
         * @param length length of the element content
         * @throws SAXException is a specific exception for the SAX parser
         */
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


