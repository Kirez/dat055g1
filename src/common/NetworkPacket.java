package common;

import java.nio.ByteBuffer;

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class NetworkPacket {

  private TYPE type;
  private byte[] data;

  private NetworkPacket(TYPE type, byte[] data) {
    this.type = type;
    this.data = data;
  }

  public static byte[] otherConnect(String name) {
    byte data[] = new byte[2 + name.length()];
    data[0] = (byte) TYPE.S_OTHER_CONNECT.ordinal();
    data[1] = (byte) name.length();

    int d = 2;
    for (byte b : name.getBytes()) {
      data[d] = b;
      d++;
    }

    return data;
  }

  public static byte[] otherDisconnect() {
    byte data[] = new byte[1];
    data[0] = (byte) TYPE.S_OTHER_DISCONNECT.ordinal();
    return data;
  }

  public static byte[] sync(GamePlayer player, int playerNumber) {
    ByteBuffer buffer = ByteBuffer.allocate(23);
    byte data[];

    buffer.put((byte) TYPE.S_SYNC_PLAYER.ordinal());
    buffer.put((byte) playerNumber);

    buffer.putDouble(player.getPosition().getX());
    buffer.putDouble(player.getPosition().getY());
    buffer.putInt(player.getHP());
    if (player.isFaceRight()) {
      buffer.put((byte) 1);
    } else {
      buffer.put((byte) 0);
    }

    data = buffer.array();

    return data;
  }

  public static byte[] actionStart(GamePlayer.ACTION action) {
    byte data[] = new byte[2];
    data[0] = (byte) TYPE.C_ACTION_START.ordinal();
    data[1] = (byte) action.ordinal();
    return data;
  }

  public static byte[] actionEnd(GamePlayer.ACTION action) {
    byte data[] = new byte[2];
    data[0] = (byte) TYPE.C_ACTION_END.ordinal();
    data[1] = (byte) action.ordinal();
    return data;
  }

  public enum TYPE {
    S_OTHER_CONNECT,
    S_OTHER_DISCONNECT,
    S_ASSIGN,
    S_SYNC_PLAYER,
    C_ACTION_START,
    C_ACTION_END,
    C_CLIENT_JOIN,
    ERROR
  }
}
