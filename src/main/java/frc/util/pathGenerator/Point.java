package frc.util.pathGenerator;

public class Point {
    public double pos;
    public double vel;
    public double acc;
    public Point(String acc, String pos, String vel){
        this.pos = Double.parseDouble(pos);
        this.vel = Double.parseDouble(vel);
        this.acc = Double.parseDouble(acc);
    }
}
