package server;

import model.user.Profile;
import tool.DataManipulator;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerUser {
    private static int nbServerUser = 0; // number of server users

    private int ServerUserId; // id of server user
    private PrintStream streamOut; // output stream
    private InputStream streamIn; // input stream
    private String nickname; // shown servername
    private Socket client; // client socket
    private String color; // shown color of the user
    private Profile profile;

    public ServerUser(Socket client, Profile profile) throws IOException, IOException {
        this.profile = profile;
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.client = client;
        this.nickname = profile.getShownName();
        this.ServerUserId = nbServerUser;
        this.color = ColorInt.getColor(this.ServerUserId);
        nbServerUser += 1;
    }

    // change color user
    public void changeColor(String hexColor){
        if (DataManipulator.isValidColor(hexColor)){
            Color c = Color.decode(hexColor);
            // if the Color is too Bright don't change
            double luma = 0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue(); // per ITU-R BT.709
            if (luma > 160) {
                this.getOutStream().println("<b>Color Too Bright</b>");
                return;
            }
            this.color = hexColor;
            this.getOutStream().println("<b>Color changed successfully</b> " + this.toString());
            return;
        }
        this.getOutStream().println("<b>Failed to change color</b>");
    }

    // getter
    public PrintStream getOutStream(){
        return this.streamOut;
    }

    public InputStream getInputStream(){
        return this.streamIn;
    }

    public String getNickname(){
        return this.nickname;
    }

    // print user with his color
    public String toString(){

        return "<u><span style='color:"+ this.color
                +"'>" + this.getNickname() + "</span></u>";

    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
